package org.ambiance.azureus;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.config.ConfigParser;
import org.apache.commons.chain.impl.CatalogFactoryBase;

public class DownloadRulers implements Runnable {

	private Catalog catalog;
	
	public DownloadRulers() throws AmbianceAzureusException {
		ConfigParser parser = new ConfigParser();
		try {
			parser.parse(this.getClass().getResource("/org/ambiance/azureus/catalog.xml"));
			catalog = CatalogFactoryBase.getInstance().getCatalog();
		} catch (Exception e) {
			throw new AmbianceAzureusException("Unable to load rules catalog", e);
		}
	}

	public void run() {
//		try {
//			while (true) {
//				List<DownloadManager> managers = globalManager.getDownloadManagers();
//				for (DownloadManager manager : managers) {
//					String torrentName = manager.getDisplayName() + " : ";
//					switch (manager.getState()) {
//					case DownloadManager.STATE_CHECKING:
//						getLogger().info(torrentName + "Checking....");
//						break;
//					case DownloadManager.STATE_DOWNLOADING:
//						getLogger().info(
//								torrentName + "Download is " + (manager.getStats().getCompleted() / 10.0)
//										+ " % complete");
//
//						boolean ko = false;
//
//						// Test - Too long to start
//						if (manager.getNbSeeds() == 0 && manager.getStats().getCompleted() < 1
//								&& (Calendar.getInstance().getTimeInMillis() - manager.getCreationTime()) > 120000) {
//							getLogger().info(torrentName + "Download is too long : stop it");
//							ko = true;
//						}
//
//						// Test - Incomplete file, not enough seeders
//						if (manager.getNbSeeds() > 0
//								&& manager.getStats().getAvailability() <= manager.getStats().getCompleted() / 1000.0) {
//							getLogger().info(torrentName + "Incompleted file, not enough seeders");
//							ko = true;
//						}
//
//						if (ko)
//							manager.stopIt(DownloadManager.STATE_STOPPED, false, false);
//
//						break;
//					case DownloadManager.STATE_FINISHING:
//						getLogger().info(torrentName + "Finishing Download....");
//						break;
//					case DownloadManager.STATE_SEEDING:
//						getLogger().info(
//								torrentName + "Download Complete - Seeding for other users - Share Ratio = "
//										+ manager.getStats().getShareRatio());
//						if (manager.getStats().getShareRatio() > shareRatioLimit
//								|| (manager.getNbPeers() == 0 && Calendar.getInstance().getTimeInMillis()
//										- manager.getCreationTime() > 1200000))
//							manager.stopIt(DownloadManager.STATE_STOPPED, true, false);
//						break;
//					case DownloadManager.STATE_STOPPED:
//						getLogger().info(torrentName + "Download Stopped.");
//						break;
//					case DownloadManager.STATE_CLOSED:
//						FileUtils.removePath(manager.getTorrentFileName());
//						break;
//					}
//				}
//				// Check every 10 seconds on the progress
//				Thread.sleep(10000);
//			}
//
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}

	}

}
