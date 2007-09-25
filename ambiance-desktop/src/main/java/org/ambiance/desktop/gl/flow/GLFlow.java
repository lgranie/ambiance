package org.ambiance.desktop.gl.flow;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GLAutoDrawable;

import org.ambiance.desktop.gl.renderable.Renderable;

public class GLFlow implements Renderable {

	private List<GLFlowItem> items;

	public GLFlow() {
		items = new ArrayList<GLFlowItem>();
	}

	public void render(GLAutoDrawable drawable) {
		int i = 0;
		for (GLFlowItem item : items) {
			
		}
	}

}
