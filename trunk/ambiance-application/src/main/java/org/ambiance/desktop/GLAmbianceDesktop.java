package org.ambiance.desktop;

import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_MODELVIEW;

import java.awt.BorderLayout;
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

    private float rquad = 0.0f;
    private float rtri = 0.0f;
    
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
    private boolean displayFps;
    private FPSText fpsText;
	
	public void start() throws StartingException {
		// LGE - Init device and frame
		device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		frame = new JFrame(title, device.getDefaultConfiguration());

        frame.setLocationRelativeTo(null);
        
        // LGE - init 3D
        GLCapabilities caps = new GLCapabilities();
       
          
        GLCanvas canvas = new GLCanvas(caps);
        final com.sun.opengl.util.Animator animator = new com.sun.opengl.util.Animator(canvas);

        frame.getContentPane().setLayout( new BorderLayout() );
        frame.getContentPane().add(canvas, BorderLayout.CENTER );
		
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

		if(displayFps)
			fpsText = new FPSText();
		
        animator.start();
	}

	public void stop() throws StoppingException {
		// TODO Auto-generated method stub		
	}

	public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.setSwapInterval(1);
        
        gl.glShadeModel(GL.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glEnable(GL.GL_DEPTH_TEST);							// Enables Depth Testing
        gl.glDepthFunc(GL.GL_LEQUAL);								// The Type Of Depth Testing To Do
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);	// Really Nice Perspective Calculations
	}

	public void display(GLAutoDrawable drawable) {        
        GL gl = drawable.getGL();
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();

        camera.setup(gl, glu);
        
        if(displayFps) {
        	fpsText.render(drawable);
        }
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(-1.5f, 0.0f, -6.0f);
        gl.glRotatef(rtri, 0.0f, 1.0f, 0.0f);
        gl.glBegin(GL.GL_TRIANGLES);		    // Drawing Using Triangles
        gl.glColor3f(1.0f, 0.0f, 0.0f);   // Set the current drawing color to red
        gl.glVertex3f(0.0f, 1.0f, 0.0f);	// Top
        gl.glColor3f(0.0f, 1.0f, 0.0f);   // Set the current drawing color to green
        gl.glVertex3f(-1.0f, -1.0f, 0.0f);	// Bottom Left
        gl.glColor3f(0.0f, 0.0f, 1.0f);   // Set the current drawing color to blue
        gl.glVertex3f(1.0f, -1.0f, 0.0f);	// Bottom Right
        gl.glEnd();				// Finished Drawing The Triangle
        gl.glLoadIdentity();
        gl.glTranslatef(1.5f, 0.0f, -6.0f);
        gl.glRotatef(rquad, 1.0f, 0.0f, 0.0f);
        gl.glBegin(GL.GL_QUADS);           	// Draw A Quad
        gl.glColor3f(0.5f, 0.5f, 1.0f);   // Set the current drawing color to light blue
        gl.glVertex3f(-1.0f, 1.0f, 0.0f);	// Top Left
        gl.glVertex3f(1.0f, 1.0f, 0.0f);	// Top Right
        gl.glVertex3f(1.0f, -1.0f, 0.0f);	// Bottom Right
        gl.glVertex3f(-1.0f, -1.0f, 0.0f);	// Bottom Left
        gl.glEnd();				// Done Drawing The Quad
        gl.glFlush();
        rtri += 0.2f;
        rquad += 0.15f;
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();

//        gl.glViewport(0, 0, width, height);
//        gl.glMatrixMode(GL_PROJECTION);
//        gl.glLoadIdentity();
//        double aspectRatio = (double)width / (double)height;
//        glu.gluPerspective(45.0, aspectRatio, 1.0, 400.0);
//        
//        gl.glMatrixMode(GL_MODELVIEW);
//        gl.glLoadIdentity();
        
        
        if (height <= 0) // avoid a divide by zero error!
            height = 1;
        final float h = (float) width / (float) height;

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// TODO Auto-generated method stub
	}
	
}
