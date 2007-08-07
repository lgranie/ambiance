package org.ambiance.iserver.batik;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

import javax.swing.JFrame;

import org.ambiance.iserver.AmbianceIserver;
import org.ambiance.iserver.AmbianceIserverException;
import org.ambiance.transport.Transporter;
import org.ambiance.transport.TransporterException;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.apache.batik.util.XMLResourceDescriptor;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

/**
 * @plexus.component role="org.ambiance.iserver.AmbianceIserver"
 *                   role-hint="batik"
 */
public class AmbianceIserverBatik extends AbstractLogEnabled implements Initializable, AmbianceIserver {

	/**
	 * @plexus.requirement role="org.ambiance.transport.Transporter" role-hint="default"
	 */
	private Transporter transporter;
	
	private GraphicsDevice device = null;
	
	private SAXSVGDocumentFactory documentFactory;

	private JFrame frame = null;

	private boolean isFullScreen = false;

	// The SVG canvas.
	private JSVGCanvas svgCanvas;

	public void initialize() throws InitializationException {
		// LGE - Init device and frame
		device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		frame = new JFrame("Ambiance", device.getDefaultConfiguration());
		//frame.getContentPane().setLayout(null);

	    String parser = XMLResourceDescriptor.getXMLParserClassName();
		documentFactory = new SAXSVGDocumentFactory(parser);
		
		// LGE - Initialize SVG container
		svgCanvas = new JSVGCanvas();
		
		// Forces the canvas to always be dynamic even if the current
        // document does not contain scripting or animation.
		svgCanvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
		
		svgCanvas.addGVTTreeBuilderListener(new GVTTreeBuilderAdapter() {

			public void gvtBuildStarted(GVTTreeBuilderEvent e) {
				getLogger().debug("Build Started...");
			}

			public void gvtBuildCompleted(GVTTreeBuilderEvent e) {
				getLogger().debug("Build Done.");
				refreshScreen();
			}
		});
		
		svgCanvas.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
            public void gvtRenderingPrepare(GVTTreeRendererEvent e) {
            	getLogger().debug("Rendering Started...");
            }
            public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
            	getLogger().debug("Rendering completed.");
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
	        frame.setSize(640, 480);
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
		try {
			svgCanvas.setDocument(documentFactory.createDocument(uri, transporter.getAsStream(uri)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransporterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start() throws AmbianceIserverException {
		refreshScreen();
		
	}

}
