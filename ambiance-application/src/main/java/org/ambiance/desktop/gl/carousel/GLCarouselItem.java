package org.ambiance.desktop.gl.carousel;

import static javax.media.opengl.GL.GL_QUADS;

import java.awt.image.BufferedImage;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.ambiance.desktop.gl.Point3f;
import org.ambiance.desktop.gl.Renderable;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

public class GLCarouselItem implements Renderable {

	private String label;
	
	private TextureData textureData; 
	
	private Point3f position;
	
	public GLCarouselItem(String label) {
		position = new Point3f(1.0f, 1.0f, 1.0f);
	}
	
	public GLCarouselItem(BufferedImage image, String label) {
		textureData = TextureIO.newTextureData(image, false);
		position = new Point3f(1.0f, 1.0f, 1.0f);
	}
	
	public void render(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glLoadIdentity();
        
        // LGE - Init texture
//        Texture t = TextureIO.newTexture(textureData);
//        t.enable();
//        t.bind();
        
        // LGE - Position
        gl.glTranslatef(position.getX(), position.getY(), position.getZ());
        System.out.println(position.getX()+", "+ position.getY()+", "+ position.getZ());
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        
        // LGE - Draw Texture
//        gl.glBegin(GL_QUADS);
//        gl.glTexCoord2f(0.0f, 0.0f);
//        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
//        gl.glTexCoord2f(1.0f, 0.0f);
//        gl.glVertex3f(1.0f, -1.0f, 1.0f);
//        gl.glTexCoord2f(1.0f, 1.0f);
//        gl.glVertex3f(1.0f, 1.0f, 1.0f);
//        gl.glTexCoord2f(0.0f, 1.0f);
//        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        
        gl.glBegin(GL.GL_QUADS);           	// Draw A Quad
        gl.glColor3f(0.5f, 0.5f, 1.0f);   // Set the current drawing color to light blue
        gl.glVertex3f(-1.0f, 1.0f, 0.0f);	// Top Left
        gl.glColor3f(0.5f, 1.0f, 0.5f);
        gl.glVertex3f(1.0f, 1.0f, 0.0f);	// Top Right
        gl.glColor3f(1.0f, 0.5f, 0.5f);
        gl.glVertex3f(1.0f, -1.0f, 0.0f);	// Bottom Right
        gl.glVertex3f(-1.0f, -1.0f, 0.0f);	// Bottom Left
        gl.glEnd();				// Done Drawing The Quad
        
        
        // Render "reflected" image
//        a = reflectionAlpha;
//        gl.glColor4f(a*0.4f, a*0.4f, a*0.4f, a*0.4f);
//        gl.glTexCoord2f(tx1, ty2); gl.glVertex3f(x  , y+h, 0f);
//        gl.glTexCoord2f(tx2, ty2); gl.glVertex3f(x+w, y+h, 0f);
//        gl.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
//        gl.glTexCoord2f(tx2, ty1+((ty2-ty1)/3)); gl.glVertex3f(x+w, y+h/3, 0f);
//        gl.glTexCoord2f(tx1, ty1+((ty2-ty1)/3)); gl.glVertex3f(x  , y+h/3, 0f);

//      t.disable();
        //gl.glEnd();
        
	}
	
	public void setPosition(Point3f p3f) {
		position = p3f;
	}
}
