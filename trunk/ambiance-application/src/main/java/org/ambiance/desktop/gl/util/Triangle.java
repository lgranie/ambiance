package org.ambiance.desktop.gl.util;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.ambiance.desktop.gl.renderable.Renderable;

public class Triangle implements Renderable {

    public float angle = 0.0f;
	
	public Triangle() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void render(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
		
		gl.glLoadIdentity();
        gl.glTranslatef(-1.5f, 0.0f, -6.0f);
        gl.glRotatef(angle, 1.0f, 0.0f, 0.0f);
        gl.glBegin(GL.GL_TRIANGLES);		    // Drawing Using Triangles
        gl.glColor3f(1.0f, 0.0f, 0.0f);   // Set the current drawing color to red
        gl.glVertex3f(0.0f, 1.0f, 0.0f);	// Top
        gl.glColor3f(0.0f, 1.0f, 0.0f);   // Set the current drawing color to green
        gl.glVertex3f(-1.0f, -1.0f, 0.0f);	// Bottom Left
        gl.glColor3f(0.0f, 0.0f, 1.0f);   // Set the current drawing color to blue
        gl.glVertex3f(1.0f, -1.0f, 0.0f);	// Bottom Right
        gl.glEnd();				// Finished Drawing The Triangle		
	}

	public float getAngle() {
        return angle;
    }
    
    public void setAngle(float angle) {
        this.angle = angle;
    }
}
