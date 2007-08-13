package org.ambiance.desktop.gl.carousel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GLAutoDrawable;

import org.ambiance.desktop.gl.Point3f;
import org.ambiance.desktop.gl.Renderable;

public class GLCarousel implements Renderable, MouseListener, MouseWheelListener {

	private MouseEvent mouse;
	
	private Point3f position;
	
	private Point3f dimension;
	
	private LinkedList<GLCarouselItem> items;
	
	public GLCarousel(Point3f position, Point3f dimension) {
		items = new LinkedList<GLCarouselItem>();
		this.position = position;
		this.dimension = dimension;
	}

	public void render(GLAutoDrawable drawable) {
		double r = 360f / items.size();
		
		int i = 0;
		for (GLCarouselItem item : items) {
			//item.setPosition(new Point3f(r*Math.cos(i*r)f, 0.0f, r*Math.sin(i*r)f));
			double x = dimension.getX() * Math.cos(i*r);
			double z = dimension.getZ() * Math.sin(i*r);
			item.setPosition(new Point3f((float) x, 0.0f,(float) z));
			item.render(drawable);
			i++;
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		mouse = e;
	}

	public void mousePressed(MouseEvent e) {
		mouse = e;
	}

	public void mouseReleased(MouseEvent e) {
		mouse = e;
	}

	public void mouseEntered(MouseEvent e) {
		mouse = e;
	}

	public void mouseExited(MouseEvent e) {
		mouse = e;
	}

	public void addItem(GLCarouselItem item) {
		items.add(item);
	}

	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
	}
	
}
