package org.ambiance.desktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Map;

import javax.swing.JFrame;

import org.ambiance.desktop.view.AmbianceView;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Startable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.StartingException;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.StoppingException;

import com.blogofbug.swing.components.JCarosel;

/**
 * @plexus.component role="org.ambiance.desktop.AmbianceDesktop" role-hint="default"
 */
public class DefaultAmbianceDesktop extends AbstractLogEnabled implements Startable, AmbianceDesktop {

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
	
	public void start() throws StartingException {
		// LGE - Init device and frame
		device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		frame = new JFrame(title, device.getDefaultConfiguration());

		carosel = new JCarosel();
        carosel.setBackground(Color.BLACK, Color.LIGHT_GRAY);
        
		for (AmbianceView view : views.values()) {
			carosel.add(view.getImage(), view.getLabel());
		}
		
		frame.add(carosel);
		
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

	public void stop() throws StoppingException {
		// TODO Auto-generated method stub
		
	}
	
}
