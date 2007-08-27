package org.ambiance.desktop.gl;

import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import org.ambiance.desktop.AmbianceDesktop;
import org.ambiance.desktop.gl.carousel.GLCarousel;
import org.ambiance.desktop.gl.carousel.GLCarouselItem;
import org.ambiance.desktop.gl.renderable.Renderable;
import org.ambiance.desktop.gl.util.Camera;
import org.ambiance.desktop.gl.util.DrawAxis;
import org.ambiance.desktop.gl.util.FPSText;
import org.ambiance.desktop.gl.util.Point3f;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Startable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.StartingException;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.StoppingException;
import org.jdesktop.animation.timing.Animator;


/**
 * @plexus.component role="org.ambiance.desktop.AmbianceDesktop" role-hint="gl"
 */
public class GLAmbianceDesktop extends AbstractLogEnabled implements Startable, AmbianceDesktop, GLEventListener  {

	private static final Point3f cameraStart = new Point3f(0f, 0f, 40f);
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
	private Dimension resolution;
    
    private Camera camera;
    private com.sun.opengl.util.Animator animator;
    
    /**
	 * @plexus.configuration default-value="false"
	 */
    private boolean debug;
    
    private float position;
    private Animator ssAnimator;
    
    private GLCarousel carousel;
    private List<Renderable> renderables;
    
	public void start() throws StartingException {
		// LGE - Init device and frame
		device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		frame = new JFrame(title, device.getDefaultConfiguration());
        frame.setLocationRelativeTo(null);
        
        // LGE - Init 3D
        GLCapabilities caps = new GLCapabilities();
        caps.setSampleBuffers(true);
        caps.setNumSamples(4);
        
        // LGE - Init GLCanvas
        GLCanvas canvas = new GLCanvas(caps);
        canvas.addGLEventListener(this);
        
        // LGE - Attach GLCanvas to Frame
        frame.getContentPane().setLayout( new BorderLayout() );
        frame.getContentPane().add(canvas, BorderLayout.CENTER );
        
		// LGE - Closing window
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
        
        // LGE - Init camera
        camera = new Camera();
        camera.setLocation(cameraStart);
        
		// LGE - Set fullscreen
        isFullScreen = isFullScreen && device.isFullScreenSupported();  
		if (isFullScreen) {
			// Full-screen mode
			frame.setUndecorated(isFullScreen);
			frame.setResizable(!isFullScreen);
			device.setFullScreenWindow(frame);
			DisplayMode dm = catchDisplayMode(device.getDisplayModes(), (int) resolution.getWidth(), (int) resolution.getHeight(), 32, 60);
			device.setDisplayMode(dm);
			canvas.setSize(dm.getWidth(), dm.getHeight());
					
			frame.validate();
		} else {
	        canvas.setSize(resolution.width, resolution.height);
	        frame.setSize(resolution.width, resolution.height);
	        
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        frame.setLocation(
                ( screenSize.width  - frame.getWidth() ) / 2,
                ( screenSize.height - frame.getHeight() ) / 2
            );
			frame.pack();
			frame.setVisible(true);
			getLogger().debug(device.getDisplayMode().toString());
		}

		// LGE - Init renderables and attach debug renderables if necessary
		renderables = new LinkedList<Renderable>();
		if(debug) {
			renderables.add(new FPSText());
			renderables.add(new DrawAxis());
		}
		
		// LGE - Init Carousel
		carousel = new GLCarousel(new Point3f(0.0f,  20.0f, -60.0f), 
				                  new Point3f(35.0f, 25.0f,  60.0f), 
				                  4.0f);
		carousel.addItem(new GLCarouselItem("Game"));
		carousel.addItem(new GLCarouselItem("Movie"));
		carousel.addItem(new GLCarouselItem("Music"));
		carousel.addItem(new GLCarouselItem("Web"));
		carousel.addItem(new GLCarouselItem("Quit"));
		
	    canvas.addKeyListener(carousel);
		renderables.add(carousel);
		
		canvas.requestFocus();
		
		// LGE - Init and start Animator
		animator = new com.sun.opengl.util.Animator(canvas);
        animator.setRunAsFastAsPossible(true);
        animator.start();
	}

	public void stop() throws StoppingException {
        animator.stop();
		
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
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST); // Really Nice Perspective Calculations
        gl.glHint(GL.GL_POINT_SMOOTH_HINT, GL.GL_NICEST);
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);
        gl.glHint(GL.GL_POLYGON_SMOOTH_HINT, GL.GL_NICEST);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);                 // Set The Blending Function For Translucency
        gl.glEnable(GL.GL_BLEND);
        
	}

	public void display(GLAutoDrawable drawable) {        
        GL gl = drawable.getGL();
        
        // clear screen
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        // load identity matrix
        gl.glLoadIdentity();
        camera.setup(gl, glu);

        for (Renderable renderable : renderables) {
            renderable.render(drawable);
		}
        
        gl.glFlush();
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL gl = drawable.getGL();

        height = (height == 0) ? 1 : height;

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(45, (float) width / height, 1, 1000);

        camera.setup(gl, glu);

        gl.glMatrixMode(GL.GL_MODELVIEW);
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
            getLogger().info("Perfect DisplayMode Found !!!");
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

	public float getPosition() {
		return position;
	}

	public void setPosition(float position) {
		this.position = position;
	}
    
    
}
	
