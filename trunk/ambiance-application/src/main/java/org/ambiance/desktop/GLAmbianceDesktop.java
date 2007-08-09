package org.ambiance.desktop;

import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_TEST;
import static javax.media.opengl.GL.GL_MODELVIEW;
import static javax.media.opengl.GL.GL_PROJECTION;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import org.ambiance.desktop.gl.Camera;
import org.ambiance.desktop.gl.Point3f;
import org.ambiance.desktop.gl.util.FPSText;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Startable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.StartingException;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.StoppingException;

/**
 * @plexus.component role="org.ambiance.desktop.AmbianceDesktop" role-hint="gl"
 */
public class GLAmbianceDesktop extends AbstractLogEnabled implements Startable, AmbianceDesktop, GLEventListener  {

	private static final Point3f cameraStart = new Point3f(0f, 0f, 70f);
    private static final GLU glu = new GLU();
	
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
    
    private Camera camera;
    
    /**
	 * @plexus.configuration default-value="false"
	 */
    private boolean displayFPS;
    private FPSText fpsText;
	
	public void start() throws StartingException {
		// LGE - Init device and frame
		device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		frame = new JFrame(title, device.getDefaultConfiguration());

        frame.setLocationRelativeTo(null);
        
        // LGE - init 3D
        GLCapabilities caps = new GLCapabilities();
        caps.setSampleBuffers(true);
        caps.setNumSamples(4);
          
        GLCanvas canvas = new GLCanvas(caps);
        final com.sun.opengl.util.Animator animator =
            new com.sun.opengl.util.Animator(canvas);
        frame.add(canvas);
		
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        
        // LGE - init camera
        camera = new Camera();
        camera.setLocation(cameraStart);
        
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
			frame.pack();
			frame.setVisible(true);
		}

		if(displayFPS)
			fpsText = new FPSText();
		
        animator.start();
	}

	public void stop() throws StoppingException {
		// TODO Auto-generated method stub		
	}

	public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.setSwapInterval(1);
        gl.glEnable(GL_DEPTH_TEST);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);		
	}

	public void display(GLAutoDrawable drawable) {        
        GL gl = drawable.getGL();
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();

        camera.setup(gl, glu);
        
        if(displayFPS) {
        	fpsText.render(drawable);
        }
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();
        double aspectRatio = (double)width / (double)height;
        glu.gluPerspective(45.0, aspectRatio, 1.0, 400.0);
        
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// TODO Auto-generated method stub
	}
	
}
