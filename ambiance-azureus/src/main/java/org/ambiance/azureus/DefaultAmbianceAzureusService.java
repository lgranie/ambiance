package org.ambiance.azureus;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.ambiance.azureus.client.DownloadStateListener;
import org.ambiance.transport.Transporter;
import org.ambiance.transport.TransporterException;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Startable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.StartingException;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.StoppingException;
import org.codehaus.plexus.util.FileUtils;
import org.gudy.azureus2.core3.download.DownloadManager;
import org.gudy.azureus2.core3.download.DownloadManagerListener;
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
	 * @plexus.configuration default-value="C:\temp\ambiance-azureus"
	 */
	private String torrentHome;
	
	/**
	 * @plexus.configuration default-value="100"
	 */
	private int shareRatioLimit;

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
		Runnable checkAndPrintProgress = new Runnable() {
			@SuppressWarnings("unchecked")
			public void run() {
				try {
					while(true) {
						List<DownloadManager> managers = globalManager.getDownloadManagers();						
						for (DownloadManager manager : managers) {
							String torrentName = manager.getDisplayName() + " : ";
							switch (manager.getState()) {
							case DownloadManager.STATE_CHECKING:
								getLogger().info(torrentName + "Checking....");
								break;
							case DownloadManager.STATE_DOWNLOADING:
								getLogger().debug(manager.getStats().getCompleted() + " " + new Long(Calendar.getInstance().getTimeInMillis()-manager.getCreationTime()));
								getLogger().info(torrentName + "Download is " + (manager.getStats().getCompleted() / 10.0) + " % complete");
								if(manager.getStats().getCompleted() < 1 
										&& (Calendar.getInstance().getTimeInMillis()-manager.getCreationTime()) > 120000) {
									getLogger().info(torrentName + "Download is too long : stop it");
									manager.stopIt(DownloadManager.STATE_STOPPED, false, false);
								}
									
								break;
							case DownloadManager.STATE_FINISHING:
								getLogger().info(torrentName + "Finishing Download....");
								break;
							case DownloadManager.STATE_SEEDING:
								getLogger().info(torrentName + "Download Complete - Seeding for other users - Share Ratio = " + manager.getStats().getShareRatio());
								if(manager.getStats().getShareRatio() > shareRatioLimit)
									manager.stopIt(DownloadManager.STATE_STOPPED, true, false);
								break;
							case DownloadManager.STATE_STOPPED:
								getLogger().info(torrentName + "Download Stopped.");
								break;
							case DownloadManager.STATE_CLOSED:
								FileUtils.removePath(manager.getTorrentFileName());
								break;
							}
						}
						// Check every 10 seconds on the progress
						Thread.sleep(10000);
					}

				} catch (Exception e) {
					throw new RuntimeException(e);
				}

			}
		};

		Thread progressChecker = new Thread(checkAndPrintProgress);
		progressChecker.setDaemon(true);
		progressChecker.start();
		
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
	
	public void downloadFile(String url) throws AmbianceAzureusException {
		// Initialize torrent file into downloadedTorrentDir
		File downloadedTorrentFile = FileUtils.createTempFile("ambiance-azureus-", ".torrent", downloadedTorrentDir);

		try {
			transporter.get(url, downloadedTorrentFile);
		} catch (TransporterException e) {
			getLogger().info("Could not download torrent file", e);
			throw new AmbianceAzureusException("Could not download torrent file", e);
		}

		DownloadManager manager = globalManager.addDownloadManager(downloadedTorrentFile.getAbsolutePath(),
				downloadDirectory.getAbsolutePath());

		DownloadManagerListener listener = new DownloadStateListener(getLogger(), shareRatioLimit);
		manager.addListener(listener);
	}

	
}
