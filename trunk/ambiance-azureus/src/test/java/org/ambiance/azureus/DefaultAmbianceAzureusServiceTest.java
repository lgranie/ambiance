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
			aas.addDownload(torrentUrl);
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
