package org.ambiance.desktop.gl.carousel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.ambiance.desktop.gl.renderable.Renderable;
import org.ambiance.desktop.gl.util.Point3f;

import com.sun.opengl.util.j2d.TextRenderer;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureCoords;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

public class GLCarouselItem implements Renderable, ActionListener {

	private String label;
	
	private TextureData textureData; 
	
	private Point3f position;
	
	private float size;

	private TextRenderer textRenderer;
   
	public GLCarouselItem(String label) {
		this.label = label;
		
		Font font = new Font("SansSerif", Font.BOLD, 24);
		textRenderer = new TextRenderer(font, true, false);
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
        
        double labelWidth = textRenderer.getBounds(label).getWidth();
        double labelHeigth = textRenderer.getBounds(label).getHeight();
        
        gl.glLoadIdentity();
        textRenderer.begin3DRendering();
        textRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        textRenderer.draw3D(label, position.getX() - ((float) labelWidth / 20), position.getY() - (float) labelHeigth / 20 - size - 1.0f, position.getZ(), 0.1f);
        textRenderer.end3DRendering(); 

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

	public void actionPerformed(ActionEvent ae) {
		System.out.println(label);
	}
}
