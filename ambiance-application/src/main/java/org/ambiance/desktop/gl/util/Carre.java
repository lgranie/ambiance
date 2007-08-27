package org.ambiance.desktop.gl.util;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.ambiance.desktop.gl.renderable.Renderable;

public class Carre implements Renderable {

	private float angle = 0.0f;
	
	public Carre() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void render(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
		
        gl.glLoadIdentity();
        gl.glTranslatef(1.5f, 0.0f, -6.0f);
        gl.glRotatef(angle, 1.0f, 1.0f, 1.0f);
        gl.glBegin(GL.GL_QUADS);           	// Draw A Quad
        gl.glColor3f(0.5f, 0.5f, 1.0f);   // Set the current drawing color to light blue
        gl.glVertex3f(-1.0f, 1.0f, 0.0f);	// Top Left
        gl.glVertex3f(1.0f, 1.0f, 0.0f);	// Top Right
        gl.glVertex3f(1.0f, -1.0f, 0.0f);	// Bottom Right
        gl.glVertex3f(-1.0f, -1.0f, 0.0f);	// Bottom Left
        gl.glEnd();				// Done Drawing The Quad
	}

	public float getAngle() {
        return angle;
    }
    
    public void setAngle(float angle) {
        this.angle = angle;
    }
}
