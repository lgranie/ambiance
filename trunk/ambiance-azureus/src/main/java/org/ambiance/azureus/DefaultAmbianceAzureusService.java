/*
   Copyright (C) 2007 Laurent GRANIE.

   This program is free software; you can redistribute it and/or modify it
   under the terms of the GNU General Public License as published by the
   Free Software Foundation; either version 3, or (at your option) any later
   version.

   This program is distributed in the hope that it will be useful, but
   WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General
   Public License for more details.

   You should have received a copy of the GNU General Public License along
   with this program; if not, write to the Free Software Foundation, Inc.,
   51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.ambiance.azureus;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.ambiance.azureus.notifier.DownloadNotifier;
import org.ambiance.chain.AmbianceChain;
import org.ambiance.transport.Transporter;
import org.ambiance.transport.TransporterException;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Startable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.StartingException;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.StoppingException;
import org.codehaus.plexus.util.FileUtils;
import org.gudy.azureus2.core3.download.DownloadManager;
import org.gudy.azureus2.core3.global.GlobalManager;

import com.aelitis.azureus.core.AzureusCore;
import com.aelitis.azureus.core.AzureusCoreFactory;

/**
 * @plexus.component role="org.ambiance.azureus.AmbianceAzureusService" role-hint="default"
 */
public class DefaultAmbianceAzureusService extends AbstractLogEnabled implements AmbianceAzureusService, Startable {

	private GlobalManager globalManager;

	/**
	 * @plexus.requirement role="org.ambiance.transport.Transporter" role-hint="default"
	 */
	private Transporter transporter;

	/**
	 * @plexus.requirement role="org.ambiance.chain.AmbianceChain" role-hint="catalog"
	 */
	private AmbianceChain ac;
	
	/**
	 * @plexus.configuration default-value="C:\\temp\\ambiance-azureus"
	 */
	private String torrentHome;

	private File downloadedTorrentDir = null;

	private File downloadDirectory = null;

	public void start() throws StartingException {
		
		// Initialze torrent client directories
		String downloadedTorrentDirName = torrentHome + File.separatorChar + "torrents";
		downloadedTorrentDir = new File(downloadedTorrentDirName);
		try {
			FileUtils.forceMkdir(downloadedTorrentDir);
		} catch (IOException e) {
			getLogger().error("Could not create downloadedTorrentDir : " + downloadedTorrentDirName, e);
			throw new StartingException("Could not create downloadedTorrentDir : " + downloadedTorrentDirName, e);
		}

		String downloadsDirName = torrentHome + File.separatorChar + "downloads";
		downloadDirectory = new File(downloadsDirName);
		try {
			FileUtils.forceMkdir(downloadDirectory);
		} catch (IOException e) {
			getLogger().error("Could not create downloadDirectory : " + downloadsDirName, e);
			throw new StartingException("Could not create downloadDirectory : " + downloadsDirName, e);
		}

		// Start Azureus Core API
		AzureusCore core = AzureusCoreFactory.create();
		core.start();
		globalManager = core.getGlobalManager();
		globalManager.startAllDownloads();

		// Start a new daemon thread periodically check
		// the progress of the upload and print it out
		// to the command line
		try {
			DownloadNotifier dr = new DownloadNotifier(ac, globalManager);
			Thread progressChecker = new Thread(dr);
			progressChecker.setDaemon(true);
			progressChecker.start();
		} catch (AmbianceAzureusException e) {
			getLogger().error(e.getMessage());
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<DownloadManager> getDownloadManagers() {
		return globalManager.getDownloadManagers();
	}

	public void stop() throws StoppingException {
		getLogger().info("Stopping Azureus...");
		globalManager.stopAllDownloads();
		globalManager.stopGlobalManager();
		AzureusCoreFactory.getSingleton().stop();
		getLogger().info("Azureus stoped.");
	}
	
	public void addDownload(String url) throws AmbianceAzureusException {
		// Initialize torrent file into downloadedTorrentDir
		File downloadedTorrentFile = FileUtils.createTempFile("ambiance-azureus-", ".torrent", downloadedTorrentDir);

		try {
			transporter.get(url, downloadedTorrentFile);
		} catch (TransporterException e) {
			getLogger().info("Could not download torrent file", e);
			throw new AmbianceAzureusException("Could not download torrent file", e);
		}

		globalManager.addDownloadManager(downloadedTorrentFile.getAbsolutePath(), downloadDirectory.getAbsolutePath());

	}

	
}
