package org.ambiance.desktop;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import org.ambiance.desktop.view.AmbianceView;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

/**
 * @plexus.component role="org.ambiance.desktop.AmbianceDesktop" role-hint="default"
 */
public class DefaultAmbianceDesktop extends AbstractLogEnabled implements Initializable, AmbianceDesktop {

	private GraphicsDevice device = null;

	private JFrame frame = null;

	/**
	 * @plexus.configuration default-value="Ambiance Desktop"
	 */
	private String title = null;
	
	/**
	 * @plexus.configuration default-value="true"
	 */
	private boolean isFullScreen = false;
	
	/**
	 * @plexus.configuration
	 */
	private Dimension dimension;
	
	/**
	 * @plexus.requirement role="org.ambiance.desktop.view.AmbianceView"
	 *                     role-hint="Carroussel menu"
	 */
	private AmbianceView view;
	
	public void initialize() throws InitializationException {
		// LGE - Init device and frame
		device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		frame = new JFrame(title, device.getDefaultConfiguration());

		// LGE - set fullscreen
        isFullScreen = isFullScreen && device.isFullScreenSupported();  
		if (isFullScreen) {
			// Full-screen mode
			frame.setUndecorated(isFullScreen);
			frame.setResizable(!isFullScreen);
			device.setFullScreenWindow(frame);
		} else {
	        frame.setSize(dimension.width, dimension.height);
	        frame.setLocationRelativeTo(null);
		}
		
		setView(view);
	}
	
	public void setView(AmbianceView view) {
		view.setFrame(frame);	
	}

}
