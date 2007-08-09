package org.ambiance.desktop.gl.carousel;

import java.awt.image.BufferedImage;

import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

public class GLCarouselItem {

	private String label;
	
	private TextureData textureData; 
	
	public GLCarouselItem(BufferedImage image, String label) {
		textureData = TextureIO.newTextureData(image, false);
	}

	public TextureData getTextureData() {
		return textureData;
	}
	
	public String getLabel() {
		return label;
	}
	
}
