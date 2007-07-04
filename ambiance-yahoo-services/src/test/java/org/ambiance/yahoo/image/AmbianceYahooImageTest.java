package org.ambiance.yahoo.image;

import org.ambiance.yahoo.QueryYahooService;
import org.codehaus.plexus.PlexusTestCase;


public class AmbianceYahooImageTest extends PlexusTestCase {

	private QueryYahooService yahoo = null;
	
	public void setUp() {
	    try {        
			super.setUp();
			yahoo = (QueryYahooService)  lookup("org.ambiance.yahoo.AmbianceYahooService", "image");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
	}
	
	public void testRequest() {
		Exception e = null;
		Object result = null;
		try {
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
