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
package org.ambiance.desktop.gl.carousel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.ambiance.desktop.gl.renderable.Renderable;
import org.ambiance.desktop.gl.util.Point3f;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureCoords;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

public class GLCarouselItem implements Renderable, ActionListener {

	private String label;
	
	private TextureData textureData; 
	
	private Point3f position;
	
	private float size;
	
	private double angle;
   
	public GLCarouselItem(String label) {
		this.label = label;
	}
	
	public GLCarouselItem(BufferedImage image, String label) {
		this(label);

		AffineTransform tx = new AffineTransform();
	    tx.scale(128.0d / image.getWidth(), 128.0d / image.getHeight());
	    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	    image = op.filter(image, null);
		
		textureData = TextureIO.newTextureData(image, false);
	}
	
	public void render(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glLoadIdentity();
        gl.glTranslated(position.getX(), position.getY(), position.getZ());
        
        if(textureData !=null) {
        	Texture t = TextureIO.newTexture(textureData);
        	
            TextureCoords tc = t.getImageTexCoords();
            float tx1 = tc.left();
            float ty1 = tc.top();
            float tx2 = tc.right();
            float ty2 = tc.bottom();
        	
        	t.enable();
            t.bind();  
            
            gl.glBegin(GL.GL_QUADS);           	  // Draw A Quad
	        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f); // Set the current drawing color
            gl.glTexCoord2f(tx1, ty1); gl.glVertex3f(-size, size, 0.0f);
            gl.glTexCoord2f(tx2, ty1); gl.glVertex3f(size, size, 0.0f);
            gl.glTexCoord2f(tx2, ty2); gl.glVertex3f(size, -size, 0.0f);
            gl.glTexCoord2f(tx1, ty2); gl.glVertex3f(-size, -size, 0.0f);
            gl.glEnd();
        } else {    
	        gl.glBegin(GL.GL_QUADS);           	  // Draw A Quad
	        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f); // Set the current drawing color
	    	gl.glVertex3f(-size, size, 0.0f);	  // Top Left
	    	gl.glVertex3f(size, size, 0.0f);	  // Top Right
	    	gl.glVertex3f(size, -size, 0.0f);	  // Bottom Right
	    	gl.glVertex3f(-size, -size, 0.0f);	  // Bottom Left
	    	gl.glEnd();				              // Done Drawing The Quad
        }
        
	}
	
	public void setPosition(Point3f p3f) {
		position = p3f;
	}
	
	public Point3f getPosition() {
		return position;
	}
	
	public String getLabel() {
		return label;
	}

	public void setSize(float size) {
		this.size = size;		
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public void actionPerformed(ActionEvent ae) {
		System.out.println(label);
	}
}
