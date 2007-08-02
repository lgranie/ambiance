package org.ambiance.iserver.swing;

import org.codehaus.plexus.PlexusTestCase;

public class AmbianceIserverSwingTest extends PlexusTestCase {
	
	private AmbianceIserverSwing ais = null;
	
	public void setUp() {
		Exception e = null;
		
		try {
			ais = (AmbianceIserverSwing) lookup("org.ambiance.iserver.AmbianceIserver", "swing");
		} catch (Exception e1) {
			e = e1;
			e.printStackTrace();
		}
		assertNull(e);
		assertNotNull(ais);
	}
	
}