package org.ambiance.desktop;

import org.ambiance.desktop.DefaultAmbianceDesktop;
import org.codehaus.plexus.PlexusTestCase;

/**
 * @plexus.component role="org.ambiance.desktop.AmbianceDesktop" role-hint="default"
 */
public class DefaultAmbianceDesktopTest extends PlexusTestCase {

	public void setUp() {
		Exception e = null;
		
		DefaultAmbianceDesktop dad = null;
		
		try {
			super.setUp();
			dad = (DefaultAmbianceDesktop) lookup("org.ambiance.desktop.AmbianceDesktop", "default");
			Thread.sleep(10000);
		} catch (Exception e1) {
			e = e1;
			e.printStackTrace();
		}
		
		assertNull(e);
		assertNotNull(dad);
	}
	
}
