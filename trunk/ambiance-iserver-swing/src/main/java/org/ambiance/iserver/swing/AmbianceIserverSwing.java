package org.ambiance.iserver.swing;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import org.ambiance.iserver.AmbianceIserver;
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
		//frame.getContentPane().setLayout(null);

		// LGE - set fullscreen
		if (isFullScreen && device.isFullScreenSupported()) {
			// Full-screen mode
			frame.setUndecorated(isFullScreen);
			frame.setResizable(!isFullScreen);
			device.setFullScreenWindow(frame);
		} else {
	        frame.setSize(640, 480);
		}
	}

}
