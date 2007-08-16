package org.ambiance.desktop.gl.carousel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.ambiance.desktop.gl.Point3f;
import org.ambiance.desktop.gl.Renderable;

import com.sun.opengl.util.j2d.TextRenderer;
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
		textureData = TextureIO.newTextureData(image, false);
	}
	
	public void render(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glLoadIdentity();
        gl.glTranslated(position.getX(), position.getY(), position.getZ());
        gl.glBegin(GL.GL_QUADS);           	  // Draw A Quad
        gl.glColor4f(1.0f, 0.5f, 1.0f, 1.0f); // Set the current drawing color
        gl.glVertex3f(-size, size, 0.0f);	  // Top Left
        gl.glVertex3f(size, size, 0.0f);	  // Top Right
        gl.glVertex3f(size, -size, 0.0f);	  // Bottom Right
        gl.glVertex3f(-size, -size, 0.0f);	  // Bottom Left
        gl.glEnd();				              // Done Drawing The Quad
        
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
