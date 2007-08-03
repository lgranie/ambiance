package org.ambiance.iserver.swing;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;

import org.ambiance.iserver.AmbianceIserver;
import org.ambiance.iserver.AmbianceIserverException;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

/**
 * @plexus.component role="org.ambiance.iserver.AmbianceIserver"
 *                   role-hint="swing"
 */
public class AmbianceIserverSwing extends AbstractLogEnabled implements Initializable, AmbianceIserver {
	
	private GraphicsDevice device = null;

	private JFrame frame = null;

	/**
	 * @plexus.configuration default-value="true"
	 */
	private boolean isFullScreen;

	public void initialize() throws InitializationException {
		// LGE - Init device and frame
		device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		frame = new JFrame("Ambiance", device.getDefaultConfiguration());

		// LGE - Init 3D Canvas
        GLCapabilities caps = new GLCapabilities();
        caps.setSampleBuffers(true);
        caps.setNumSamples(4);
        GLCanvas canvas = new GLCanvas(caps);
        frame.add(canvas);
		
		// LGE - set fullscreen
        isFullScreen = isFullScreen && device.isFullScreenSupported();  
		if (isFullScreen) {
			// Full-screen mode
			frame.setUndecorated(isFullScreen);
			frame.setResizable(!isFullScreen);
			device.setFullScreenWindow(frame);
		} else {
	        frame.setSize(640, 480);
	        canvas.setPreferredSize(new Dimension(640, 480));
	        frame.setLocationRelativeTo(null);
		}
		
		
	}

	public void start() throws AmbianceIserverException {
		if (isFullScreen) {
            frame.validate();
        } else {
        	frame.pack();
        	frame.setVisible(true);
        }
	}

}
