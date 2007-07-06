package org.ambiance.azureus;

import java.util.List;

import org.codehaus.plexus.PlexusTestCase;
import org.gudy.azureus2.core3.download.DownloadManager;

public class DefaultAmbianceAzureusServiceTest extends PlexusTestCase {

	AmbianceAzureusService aas = null;
	
	public void setUp() {
		try {        
			super.setUp();
			aas = (AmbianceAzureusService) lookup("org.ambiance.azureus.AmbianceAzureusService", "default");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
//	public void testLocalTorrent() {
//		Exception e = null;
//		
//		URL torrentFileName = this.getClass().getResource("/Spirituality___Osho___This_is_a_test_torrent_to_see_if_this_torrent_thing_works.torrent");
//		try {
//			aas.downloadFile(torrentFileName.toString());
//		} catch (AmbianceAzureusException e1) {
//			e = e1;
//		}
//		assertNull(e);
//	}
	
	public void testDistantTorrent() {
		Exception e = null;
		
		String torrentUrl = "http://www.mininova.org/get/503959";
		try {
			aas.downloadFile(torrentUrl);
			boolean stop = false;
			while(!stop) {
				List<DownloadManager> managers = aas.getDownloadManagers();						
				for (DownloadManager manager : managers) {
					if(manager.getState() == DownloadManager.STATE_STOPPED) {
						stop = true;
					}
				}
				// Check every 10 seconds on the progress
				Thread.sleep(10000);
			}
		} catch (Exception e1) {
			e = e1;
		}
		assertNull(e);
	}
	
}
