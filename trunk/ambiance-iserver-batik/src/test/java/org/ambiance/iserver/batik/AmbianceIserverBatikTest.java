package org.ambiance.iserver.batik;

import org.codehaus.plexus.PlexusTestCase;

public class AmbianceIserverBatikTest extends PlexusTestCase {
	
	private AmbianceIserverBatik iserver = null;
	
	public void setUp() {
		
		Exception e = null;
		try {
			super.setUp();
			
			iserver = (AmbianceIserverBatik) lookup("org.ambiance.iserver.AmbianceIserver","batik");
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}   
		
		assertNull(e);
		assertNotNull(iserver);    
		
	}
	
	public void testTetris() {
		Exception e = null;
		try {
			iserver.setScreen(this.getClass().getResource("/svgtetris.svg").toString());
			
			Thread.sleep(60000);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}   
		
		assertNull(e);
	}

	public void testAntike() {
		Exception e = null;
		try {
			iserver.setScreen(this.getClass().getResource("/antike-alexander.svg").toString());
			
			Thread.sleep(60000);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}   
		
		assertNull(e);
	}

	public void testFlower() {
		Exception e = null;
		try {
			iserver.setScreen(this.getClass().getResource("/flower.svg").toString());
			
			Thread.sleep(60000);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}   
		
		assertNull(e);
	}
	
	public void testTiger() {
		Exception e = null;
		try {
			iserver.setScreen(this.getClass().getResource("/tiger.svg").toString());
			
			Thread.sleep(30000);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}   
		
		assertNull(e);
	}
	
	public void testCroix() {
		Exception e = null;
		try {
			iserver.setScreen(this.getClass().getResource("/svg/croix.svg").toString());
			Thread.sleep(10000);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}   
		
		assertNull(e);
	}
	
}
