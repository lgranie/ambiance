package org.ambiance.azureus.client;

import java.io.File;

import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.util.FileUtils;
import org.gudy.azureus2.core3.disk.DiskManagerFileInfo;
import org.gudy.azureus2.core3.download.DownloadManager;
import org.gudy.azureus2.core3.download.DownloadManagerListener;

/**
 * A listener class that will print out information to the system console on the current status of the torrent data file
 * download.
 */
public class DownloadStateListener implements DownloadManagerListener {

	private Logger logger = null;

	public DownloadStateListener(Logger logger) {
		this.logger = logger;
	}

	public void stateChanged(DownloadManager manager, int state) {
		String torrentName = manager.getDisplayName() + " : ";
		
		switch (state) {
		case DownloadManager.STATE_CHECKING:
			logger.info(torrentName + "Checking....");
			break;
		case DownloadManager.STATE_DOWNLOADING:
			logger.info(torrentName + "Download is " + (manager.getStats().getCompleted() / 10.0) + " % complete");
			break;
		case DownloadManager.STATE_FINISHING:
			logger.info(torrentName + "Finishing Download....");
			break;
		case DownloadManager.STATE_SEEDING:
			logger.info(torrentName + "Download Complete - Seeding for other users....");
			break;
		case DownloadManager.STATE_STOPPED:
			logger.info(torrentName + "Download Stopped.");
			break;
		case DownloadManager.STATE_CLOSED:
			FileUtils.removePath(manager.getTorrentFileName());
			break;
		}
	}

	public void downloadComplete(DownloadManager manager) {
		String torrentName = manager.getDisplayName() + " : ";
		logger.info(torrentName + "Download Completed");
	}

	public void filePriorityChanged(DownloadManager download, DiskManagerFileInfo file) {
	}

	public void positionChanged(DownloadManager download, int oldPosition, int newPosition) {
	}

	public void completionChanged(DownloadManager manager, boolean bCompleted) {
	}
}
