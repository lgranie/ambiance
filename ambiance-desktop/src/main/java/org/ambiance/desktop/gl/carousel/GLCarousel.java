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

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GLAutoDrawable;

import org.ambiance.desktop.gl.renderable.Renderable;
import org.ambiance.desktop.gl.util.Point3f;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

import com.sun.opengl.util.j2d.TextRenderer;

public class GLCarousel extends KeyAdapter implements Renderable  {
	
	public static final int TURN_RIGHT = 1;
	public static final int TURN_LEFT  = -1;
	
	private Point3f position;
	private Point3f dimension;
	private float pente;
	
	private List<GLCarouselItem> items;
	private GLCarouselItem currentItem;
	
	private Animator turnAnimator;
	
	private double angle;
	
	private double stepAngle;
	
	private float iconSize;
	
	private TextRenderer textRenderer;
	private Animator textAnimator;
	private float textAlpha = 0.0f;
	
	public GLCarousel(Point3f position, Point3f dimension, float iconSize) {
		items = new ArrayList<GLCarouselItem>();
		this.position = position;
		this.dimension = dimension;
		this.pente = - dimension.getY() / dimension.getZ();
		this.iconSize = iconSize;
				
		turnAnimator = new Animator(0);
		
		Font font = new Font("SansSerif", Font.BOLD, 24);
		textRenderer = new TextRenderer(font, true, false);
		textAnimator = new Animator(0);
    }

	public void render(GLAutoDrawable drawable) {		
		int i = 0;
		for (GLCarouselItem item : items) {			
			double x = dimension.getX() * Math.cos(i*stepAngle + angle);
			double z = dimension.getZ() * Math.sin(i*stepAngle + angle);
			item.setPosition(new Point3f((float)  x + position.getX(), 
					                     ((float) z * pente) + position.getY(),
			     	                     (float)  z + position.getZ()));

			item.render(drawable);
			i++;
		}
		
		// LGE - Render the label of the current item 
		if(!turnAnimator.isRunning()) {
			String label = items.indexOf(currentItem) + " " + currentItem.getLabel();
			
	        float x = currentItem.getPosition().getX() - (float) textRenderer.getBounds(label).getWidth()  / 20;
	        float y = currentItem.getPosition().getY() - (float) textRenderer.getBounds(label).getHeight() / 20 - iconSize - 1.0f;
			
			drawable.getGL().glLoadIdentity();
	        textRenderer.begin3DRendering();
	        textRenderer.setColor(1.0f, 1.0f, 1.0f, textAlpha);
	        textRenderer.draw3D(label, x, y, currentItem.getPosition().getZ(), 0.1f);
	        textRenderer.end3DRendering();
		}

	}
	
	public void addItem(GLCarouselItem item) {
		items.add(0, item);
		stepAngle = Math.PI * 2 / items.size();
		
		item.setSize(iconSize);
		
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setAngle(((Math.PI * 2) + Math.PI / 2 - i * stepAngle) % (Math.PI * 2));
		}
		setCurrentItem(item);
	}
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_RIGHT :
				turn(TURN_RIGHT);
				break;
			case KeyEvent.VK_LEFT :
				turn(TURN_LEFT);
				break;
			case KeyEvent.VK_ENTER :
				currentItem.actionPerformed(new ActionEvent(this, KeyEvent.VK_ENTER, ""));
				break;
			default:
				break;
		}		
	} 

	private void turn(int direction) {
		int index = (items.size() + items.indexOf(currentItem) + direction) % items.size();
		setCurrentItem(items.get(index));
	}

	public GLCarouselItem getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(final GLCarouselItem item) {

		if(turnAnimator.isRunning()) turnAnimator.cancel();
		
		double finalAngle = item.getAngle();
		if (Math.abs(finalAngle - angle) > stepAngle)
			finalAngle = Math.PI * 2;

		turnAnimator = PropertySetter.createAnimator(1500, this, "angle", angle, finalAngle);
		turnAnimator.setAcceleration(0.5f);
		turnAnimator.setDeceleration(0.3f);
		turnAnimator.start();		
		
		this.currentItem = item;
		textAnimator = PropertySetter.createAnimator(1000, this, "textAlpha", 0.0f, 1.0f);
		
		turnAnimator.addTarget(new TimingTargetAdapter() {
			public void end() {
				textAnimator.start();
				setAngle(item.getAngle());
				System.out.println("angle = " + getAngle());
			}
		});
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public float getTextAlpha() {
		return textAlpha;
	}

	public void setTextAlpha(float textAlpha) {
		this.textAlpha = textAlpha;
	}
	
}
