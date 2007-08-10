package org.ambiance.desktop;

import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_MODELVIEW;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
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

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

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
    
    private Animator animator;
    
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
        canvas.addGLEventListener(this);
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
			DisplayMode dm = catchDisplayMode(device.getDisplayModes(), 1024, 768, 32, 60);
			device.setDisplayMode(dm);
			canvas.setSize(dm.getWidth(), dm.getHeight());
			frame.validate();
		} else {
	        canvas.setSize(dimension.width, dimension.height);
	        frame.setSize(dimension.width, dimension.height);
	        
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        frame.setLocation(
                    ( screenSize.width  - frame.getWidth() ) / 2,
                    ( screenSize.height - frame.getHeight() ) / 2
            );
			frame.pack();
			frame.setVisible(true);
			System.out.println(device.getDisplayMode().toString());
		}

		if(displayFps)
			fpsText = new FPSText();
		
		canvas.requestFocus();
		
		animator = new FPSAnimator( canvas, 60 );
        animator.setRunAsFastAsPossible(false);
        animator.start();
	}

	public void stop() throws StoppingException {
		if(isFullScreen) {
            GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            graphicsDevice.setFullScreenWindow(null);
            graphicsDevice = null;
        }		
	}

	public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        
        gl.glEnable(GL.GL_TEXTURE_2D);                              // Enable Texture Mapping
        gl.glShadeModel(GL.GL_SMOOTH);                              // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);                    // Black Background
        gl.glClearDepth(1.0f);                                      // Depth Buffer Setup
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  // Really Nice Perspective Calculations
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);                  // Set The Blending Function For Translucency
        gl.glEnable(GL.GL_BLEND);
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

        height = (height == 0) ? 1 : height;

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(45, (float) width / height, 1, 1000);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// TODO Auto-generated method stub
	}
	
	/**
     * Catch the display mode in the displayModes array corresponding to the parameter of the method
     *
     * If zero displayMode match with the parameter, we take the first display mode in the array
     *
     * @author Pepijn Van Eeckhoudt
     */
    private DisplayMode catchDisplayMode(DisplayMode[] displayModes, int requestedWidth, int requestedHeight,
            int requestedDepth, int requestedRefreshRate)
    {
        //Try to find the exact match
        DisplayMode displayMode = searchDisplayMode(displayModes, requestedWidth, requestedHeight,
                                                    requestedDepth, requestedRefreshRate);
       
        //If the previous trial has failled, try again ignoring the requested bit depth and refresh rate
        if(displayMode == null)
        {
            displayMode = searchDisplayMode(displayModes, requestedWidth, requestedHeight,
                                            -1, -1);
           
            //Try again, and again ignoring the requested bit depth and height
            if(displayMode == null)
            {
                displayMode = searchDisplayMode(displayModes, requestedWidth,
                                                -1, -1, -1);
               
                //If all else fails try to get any display mode
                if(displayMode == null)
                    displayMode = searchDisplayMode(displayModes, -1,
                                                    -1, -1, -1);
            }
        }
        else
        {
            System.out.println("Perfect DisplayMode Found !!!");
        }
       
        return displayMode;
    }
   
    /**
     * Search the DisplayMode in the displayModes array corresponding to the parameter of the method
     *
     * @author Pepijn Van Eeckhoudt
     */
    private DisplayMode searchDisplayMode(DisplayMode[] displayModes, int requestedWidth, int requestedHeight,
            int requestedDepth, int requestedRefreshRate)
    {
        //the display mode to use
        DisplayMode displayModeToUse = null;
       
        /*
         * Search the display mode to use in the displayMode array
         *
         * If this methods is called with -1 for all parameters, we take the first displayMode
         */
        for(int i = 0; i < displayModes.length; i++)
        {
            DisplayMode displayMode = displayModes[i];
           
            if((requestedWidth == -1 || displayMode.getWidth() == requestedWidth) &&
                (requestedHeight == -1 || displayMode.getHeight() == requestedHeight) &&
                (requestedHeight == -1 || displayMode.getRefreshRate() == requestedRefreshRate) &&
                (requestedDepth == -1 || displayMode.getBitDepth() == requestedDepth))
                    displayModeToUse = displayMode;
        }
       
        return displayModeToUse;
    }
}
	
