package org.ambiance.desktop.gl.flow;

import javax.media.opengl.GLAutoDrawable;

import org.ambiance.desktop.gl.renderable.Renderable;
import org.ambiance.desktop.gl.util.Point3f;

import com.sun.opengl.util.texture.TextureData;

public class GLFlowItem implements Renderable {

	private TextureData textureData;

	private Point3f position;

	public void render(GLAutoDrawable drawable) {

	}
	
	public void setPosition(Point3f p3f) {
		position = p3f;
	}
	
	public Point3f getPosition() {
		return position;
	}
	
}
