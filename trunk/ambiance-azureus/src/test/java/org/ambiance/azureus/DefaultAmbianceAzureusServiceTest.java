package org.ambiance.azureus;

import java.net.URL;

import org.codehaus.plexus.PlexusTestCase;

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
//		URL torrentFileName = this.getClass().getResource("/KNOPPIX_V5.0.1CD-2006-06-01-DE.torrent");
//		try {
//			aas.downloadFile(torrentFileName.toString());
//		} catch (AmbianceAzureusException e1) {
//			e = e1;
//		}
//		assertNull(e);
//	}
	
	public void testDistantTorrent() {
		Exception e = null;
		
		String torrentUrl = "http://torrent.unix-ag.uni-kl.de/torrents/KNOPPIX_V5.0.1CD-2006-06-01-DE.torrent";
		try {
			aas.downloadFile(torrentUrl);
		} catch (AmbianceAzureusException e1) {
			e = e1;
		}
		assertNull(e);
	}
	
}
