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

import java.awt.Font;
import java.text.DecimalFormat;

import javax.media.opengl.GLAutoDrawable;

import org.ambiance.desktop.gl.renderable.Renderable;

import com.sun.opengl.util.j2d.TextRenderer;

public class FPSText implements Renderable{

	private String fpsText;

	private int fpsWidth;

	private long startTime = 0;

	private int frameCount;

	private DecimalFormat format = new DecimalFormat("####.00");

	private TextRenderer textRenderer;

	public FPSText() {
		Font font = new Font("SansSerif", Font.BOLD, 24);
		textRenderer = new TextRenderer(font, true, false);
	}

	public FPSText(TextRenderer textRenderer) {
		this.textRenderer = textRenderer;
	}

	public void render(GLAutoDrawable drawable) {

        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        }
		
		if (++frameCount == 100) {
			long endTime = System.currentTimeMillis();
			float fps = 100.0f / (float) (endTime - startTime) * 1000;
			frameCount = 0;
			startTime = System.currentTimeMillis();

			fpsText = "FPS: " + format.format(fps);
			if (fpsWidth == 0) {
				// Place it at a fixed offset wrt the upper right corner
				fpsWidth = (int) textRenderer.getBounds("FPS: 10000.00").getWidth();
			}
		}

		if (fpsWidth == 0) {
			return;
		}

		// Calculate text location and color
		int x = drawable.getWidth() - fpsWidth - 5;
		int y = drawable.getHeight() - 30;
		float c = 0.55f;

		// Render the text
		textRenderer.beginRendering(drawable.getWidth(), drawable.getHeight());
		textRenderer.setColor(c, c, c, c);
		textRenderer.draw(fpsText, x, y);
		textRenderer.endRendering();
	}

}
