package org.ambiance.iserver.batik;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.ambiance.iserver.AmbianceIserver;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

/**
 * @plexus.component role="org.ambiance.iserver.AmbianceIserver"
 *                   role-hint="batik"
 */
public class AmbianceIserverBatik extends AbstractLogEnabled implements
		Initializable, AmbianceIserver {

	private GraphicsDevice device = null;

	private JFrame frame = null;

	private boolean isFullScreen = false;

	// The SVG canvas.
	private JSVGCanvas svgCanvas;

	public void initialize() throws InitializationException {
		// LGE - Init device and frame
		device = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		frame = new JFrame("Ambiance", device.getDefaultConfiguration());
		//frame.getContentPane().setLayout(null);

		// LGE - Initialize SVG container
		svgCanvas = new JSVGCanvas();
		svgCanvas.addGVTTreeBuilderListener(new GVTTreeBuilderAdapter() {

			public void gvtBuildStarted(GVTTreeBuilderEvent e) {
				getLogger().info("Build Started...");
			}

			public void gvtBuildCompleted(GVTTreeBuilderEvent e) {
				getLogger().info("Build Done.");
				refreshScreen();
			}
		});
		
		svgCanvas.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
            public void gvtRenderingPrepare(GVTTreeRendererEvent e) {
            	getLogger().info("Rendering Started...");
            }
            public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
            	getLogger().info("Rendering completed.");
            }
        });

		frame.getContentPane().add(svgCanvas);

		// LGE - set fullscreen
		isFullScreen = device.isFullScreenSupported();
		getLogger().info("Is fullscreen supported : " + isFullScreen);

		if (isFullScreen) {
			// Full-screen mode
			frame.setUndecorated(isFullScreen);
			frame.setResizable(!isFullScreen);
			device.setFullScreenWindow(frame);
		} else {
	        frame.setSize(400, 400);
		}
	}

	protected void refreshScreen() {
		getLogger().info("Refresh screen.");
		if (isFullScreen) {
			frame.validate();
		} else {
			// Windowed mode
			frame.pack();
			frame.setVisible(true);
		}
	}

	public void setScreen(String uri) {
		svgCanvas.setURI(uri);
	}

}
