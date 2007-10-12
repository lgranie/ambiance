/**
 * Copyright (C) 2007 Laurent GRANIE.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 3, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.ambiance.desktop.gl.transition;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.jdesktop.animation.timing.interpolation.PropertySetter;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureCoords;
import com.sun.opengl.util.texture.TextureIO;

public class Reduce extends Transition {

    private BufferedImage screenshot;
	
    private float position;
    
	public Reduce(Dimension dimension) {
		super();
		// Take a screenshot
		try {
			Robot robot = new Robot();
			screenshot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			
    		AffineTransform tx = new AffineTransform();
    	    tx.scale(dimension.getWidth() / screenshot.getWidth(), dimension.getHeight() / screenshot.getHeight());
    	    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
    	    screenshot = op.filter(screenshot, null);
		} catch (AWTException ae) {
			getLogger().error("Unable to build robot", ae);
		}
		
		animator = PropertySetter.createAnimator(6000, this, "position", 0.0f, 0.0f);
		animator.start();
	}
	
	public void render(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		
		gl.glPushMatrix();
        gl.glLoadIdentity();

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        
		gl.glMatrixMode(GL.GL_MODELVIEW);
    	Texture t = TextureIO.newTexture(screenshot, false);
    	
        TextureCoords tc = t.getImageTexCoords();
        float tx1 = tc.left();
        float ty1 = tc.top();
        float tx2 = tc.right();
        float ty2 = tc.bottom();
    	
    	t.enable();
        t.bind();  

        gl.glBegin(GL.GL_QUADS);           	  // Draw A Quad
        gl.glTexCoord2f(tx1, ty1); gl.glVertex2f(-position,  position);
        gl.glTexCoord2f(tx2, ty1); gl.glVertex2f( position,  position);
        gl.glTexCoord2f(tx2, ty2); gl.glVertex2f( position, -position);
        gl.glTexCoord2f(tx1, ty2); gl.glVertex2f(-position, -position);
        gl.glEnd();
        
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glPopMatrix();
	}

}
