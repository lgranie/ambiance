package org.ambiance.iserver.batik.tests;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import org.codehaus.plexus.PlexusTestCase;

public class FullscreenTest extends PlexusTestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testGraphicsDevice() {
		Exception e = null;

		GraphicsDevice device = null;
		JFrame frame = null;

		try {
			// LGE - Init device
			device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			frame = new JFrame("Ambiance", device.getDefaultConfiguration());
			
		    // LGE - set fullscreen
		    boolean isFullScreen = device.isFullScreenSupported();
		    System.out.println("Is fullscreen supported : " + isFullScreen);
		    frame.setUndecorated(isFullScreen);
		    frame.setResizable(!isFullScreen);
	        
		    if (isFullScreen) {
	            // Full-screen mode
	            device.setFullScreenWindow(frame);
	            frame.validate();
	        } else {
	            // Windowed mode
	        	frame.pack();
	        	frame.setVisible(true);
	        }

		    
		    // LGE - 10 secondes and go 
		    Thread.sleep(10000);
		} catch (InterruptedException ie) {
			e = ie;
			e.printStackTrace();
		} finally {
		    device.setFullScreenWindow(null);
		}
		
		assertNull(e);
		assertNotNull(device);
		assertNotNull(frame);
	}
	
}
