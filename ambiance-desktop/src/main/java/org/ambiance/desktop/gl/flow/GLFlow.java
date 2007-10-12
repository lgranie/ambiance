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
