package org.ambiance.desktop.gl.carousel;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.media.opengl.GLAutoDrawable;

import org.ambiance.desktop.gl.renderable.Renderable;
import org.ambiance.desktop.gl.util.Point3f;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

public class GLCarousel implements Renderable, KeyListener {
	
	public static final int TURN_RIGHT =  1;
	public static final int TURN_LEFT  = -1;
	
	private Point3f position;
	private Point3f dimension;
	private float pente;
	
	private LinkedList<GLCarouselItem> items;
	private GLCarouselItem currentItem;
	
	private Animator animator;
	
	private double angle;
	
	
	
	private float iconSize;
	
	public GLCarousel(Point3f position, Point3f dimension, float iconSize) {
		items = new LinkedList<GLCarouselItem>();
		this.position = position;
		this.dimension = dimension;
		this.pente = - dimension.getY() / dimension.getZ();
		
		this.iconSize = iconSize;
		
		angle = Math.PI / 2;
		
		animator = new Animator(0);
    }

	public void render(GLAutoDrawable drawable) {
		double r = Math.PI * 2 / items.size();
		
		int i = 1;
		for (GLCarouselItem item : items) {			
			double x = dimension.getX() * Math.cos(i*r + angle);
			double z = dimension.getZ() * Math.sin(i*r + angle);
			item.setPosition(new Point3f((float)  x + position.getX(), 
					                     ((float) z * pente) + position.getY(),
			     	                     (float)  z + position.getZ()));

			item.render(drawable);
			i++;
		}

	}
	
	public void addItem(GLCarouselItem item) {
		items.add(item);
		item.setSize(iconSize);
		currentItem = item;
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
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
		int currentIndex = items.indexOf(currentItem);
		final int index  = (items.size() + (currentIndex - direction)) % items.size();

		if(animator.isRunning()) return;
		
		animator = PropertySetter.createAnimator(1500, this, "angle", angle, angle + (direction * Math.PI * 2 / items.size()));
		animator.setAcceleration(0.5f);
		animator.setDeceleration(0.3f);
		
		animator.start();
		
		animator.addTarget(new TimingTargetAdapter() {
			public void end() {
				currentItem = items.get(index);
			}
		});
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public GLCarouselItem getCurrentItem() {
		return currentItem;
	}
	
}
