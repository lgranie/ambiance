package org.ambiance.desktop.gl.carousel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.media.opengl.GLAutoDrawable;

import org.ambiance.desktop.gl.Point3f;
import org.ambiance.desktop.gl.Renderable;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

public class GLCarousel implements Renderable, KeyListener {
	
	private Point3f position;
	
	private Point3f dimension;
	
	private LinkedList<GLCarouselItem> items;
	
	private Animator animator;
	
	private double angle;
	
	private GLCarouselItem currentItem;
	
	public GLCarousel(Point3f position, Point3f dimension) {
		items = new LinkedList<GLCarouselItem>();
		this.position = position;
		this.dimension = dimension;
		
		angle = 0.0;
	}

	public void render(GLAutoDrawable drawable) {
		double r = Math.PI * 2 / items.size();
		
		int i = 0;
		for (GLCarouselItem item : items) {
			double x = dimension.getX() * Math.cos(i*r + angle);
			double z = dimension.getZ() * Math.sin(i*r + angle);
			item.setPosition(new Point3f((float) x + position.getX(), 
										      0.0f + position.getY(),
					                     (float) z + position.getZ()));
			item.render(drawable);
			i++;
		}
	}
	
	public void addItem(GLCarouselItem item) {
		items.add(item);
		currentItem = item;
	}

	public void keyPressed(KeyEvent e) {
		System.out.println(currentItem.getLabel());	
	}

	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_RIGHT :
			if(animator != null && animator.isRunning()) break;
			
			animator = PropertySetter.createAnimator(1500, this, "angle", angle, angle + Math.PI * 2 / items.size());
			animator.setAcceleration(0.3f);
			animator.setDeceleration(0.5f);
			
			animator.start();
			
			animator.addTarget(new TimingTargetAdapter() {
				public void end() {
					currentItem = items.getFirst();	
				}				
			});
				        
			break;
			
		case KeyEvent.VK_LEFT :
			if(animator != null && animator.isRunning()) break;
		
			animator = PropertySetter.createAnimator(1500, this, "angle", angle, angle - Math.PI * 2 / items.size());
			animator.setAcceleration(0.3f);
			animator.setDeceleration(0.5f);
		
			animator.start();
		
			animator.addTarget(new TimingTargetAdapter() {
				public void end() {
					currentItem = items.getLast();					
				}				
			});
			break;
			
		default:
			break;
		}
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
	
}
