package org.ambiance.desktop;

import org.codehaus.plexus.PlexusTestCase;

/**
 * @plexus.component role="org.ambiance.desktop.AmbianceDesktop" role-hint="default"
 */
public class GLAmbianceDesktopTest extends PlexusTestCase {

	public void setUp() {
		Exception e = null;
		
		GLAmbianceDesktop dad = null;
		
		try {
			super.setUp();
			dad = (GLAmbianceDesktop) lookup("org.ambiance.desktop.AmbianceDesktop", "gl");
			Thread.sleep(10000);
		} catch (Exception e1) {
			e = e1;
			e.printStackTrace();
		}
		
		assertNull(e);
		assertNotNull(dad);
	}
	
}