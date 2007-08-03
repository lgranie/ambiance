package org.ambiance.iserver.swing;

import org.codehaus.plexus.PlexusTestCase;

public class AmbianceIserverSwingTest extends PlexusTestCase {
	
	private AmbianceIserverSwing ais = null;
	
	public void setUp() {
		Exception e = null;
		
		try {
			super.setUp();
			ais = (AmbianceIserverSwing) lookup("org.ambiance.iserver.AmbianceIserver", "swing");
		} catch (Exception e1) {
			e = e1;
			e.printStackTrace();
		}
		assertNull(e);
		assertNotNull(ais);
	}
	
	public void testFirst() {
		try {
			ais.start();
			Thread.sleep(10000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}