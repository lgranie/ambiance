package org.ambiance.yahoo.image;

import org.ambiance.yahoo.QueryYahooService;
import org.codehaus.plexus.PlexusTestCase;


public class AmbianceYahooImageTest extends PlexusTestCase {

	public void testRequest() {
		Exception e = null;
		QueryYahooService yahoo = null;
		Object result = null;
		try {
			yahoo = (QueryYahooService) lookup(
					"org.ambiance.yahoo.AmbianceYahooService", "image");
			 result = yahoo.query("Take me to your leader");
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}
		assertNull(e);
		assertNotNull(yahoo);
		assertNotNull(result);
	}

	
}
