package org.ambiance.desktop.gl.util;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.ambiance.desktop.gl.renderable.Renderable;

public class DrawAxis implements Renderable {

	public void render(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();

        gl.glLoadIdentity();
        gl.glBegin(GL.GL_LINES);		    // Drawing Axes

        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glVertex2i(0,-100);
        gl.glVertex2i(0,100);
        
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex2i(-100,0);
        gl.glVertex2i(100,0);
        
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3i(0,0,-100);
        gl.glVertex3i(0,0,100);
 
        
        // Draw grid
        gl.glColor3f(0.5f, 0.5f, 1.0f);
        for(int i=-1000; i<1000;i +=10){
          gl.glVertex3f(-1000, -0.09f, i);
          gl.glVertex3f(1000, -0.09f, i);
        }
        for(int i=-1000; i<1000;i +=10){
          gl.glVertex3f(i, -0.09f, -1000);
          gl.glVertex3f(i, -0.09f, 1000);
        }
        
        gl.glEnd();				            // Finished Drawing

	}

}
