package org.ambiance.nntpclient;

import org.codehaus.plexus.PlexusTestCase;

public class DefaultNntpClientTest extends PlexusTestCase {

	public void testLookup() {
		Exception e = null;
		AmbianceNntpClient nntpclient = null;
		try {
			nntpclient = (AmbianceNntpClient) lookup(
					"org.ambiance.nntpclient.AmbianceNntpClient", "default");
			nntpclient.refreshGroup("alt.binaries.sounds.mp3");
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}
		assertNull(e);
		assertNotNull(nntpclient);
	}

}
