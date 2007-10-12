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
package org.ambiance.desktop.gl.util;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import org.ambiance.desktop.gl.renderable.Renderable;

public class DrawAxis implements Renderable {

	public void render(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();

        gl.glLoadIdentity();
        gl.glBegin(GL.GL_LINES);		    // Drawing Axes

        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glVertex2i(0,-100);
        gl.glVertex2i(0,100);
        
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex2i(-100,0);
        gl.glVertex2i(100,0);
        
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3i(0,0,-100);
        gl.glVertex3i(0,0,100);
 
        
        // Draw grid
        gl.glColor3f(0.5f, 0.5f, 1.0f);
        for(int i=-1000; i<1000;i +=10){
          gl.glVertex3f(-1000, -0.09f, i);
          gl.glVertex3f(1000, -0.09f, i);
        }
        for(int i=-1000; i<1000;i +=10){
          gl.glVertex3f(i, -0.09f, -1000);
          gl.glVertex3f(i, -0.09f, 1000);
        }
        
        gl.glEnd();				            // Finished Drawing

	}

}
