package org.ambiance.desktop;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Map;

import javax.swing.JFrame;

import org.ambiance.desktop.view.AmbianceView;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

import com.blogofbug.swing.components.JCarosel;

/**
 * @plexus.component role="org.ambiance.desktop.AmbianceDesktop" role-hint="default"
 */
public class DefaultAmbianceDesktop extends AbstractLogEnabled implements Initializable, AmbianceDesktop {

	private GraphicsDevice device = null;

	private JFrame frame = null;

	private JCarosel carosel;
	
	/**
	 * @plexus.requirement role="org.ambiance.desktop.view.AmbianceView"
	 */
	private Map<String, AmbianceView> views;
	
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
	
	
	public void initialize() throws InitializationException {
		// LGE - Init device and frame
		device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		frame = new JFrame(title, device.getDefaultConfiguration());

		carosel = new JCarosel(128);
		
		for (String key : views.keySet()) {
			System.out.println(key);
			//carosel.add(view.getImage(), key);
		}
		
		// LGE - set fullscreen
        isFullScreen = isFullScreen && device.isFullScreenSupported();  
		if (isFullScreen) {
			// Full-screen mode
			frame.setUndecorated(isFullScreen);
			frame.setResizable(!isFullScreen);
			device.setFullScreenWindow(frame);
			frame.validate();
		} else {
	        frame.setSize(dimension.width, dimension.height);
	        frame.setLocationRelativeTo(null);
			frame.pack();
			frame.setVisible(true);
		}
		
	}
	
}
