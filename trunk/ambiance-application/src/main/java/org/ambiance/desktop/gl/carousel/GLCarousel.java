package org.ambiance.desktop.gl.carousel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GLDrawable;

import org.ambiance.desktop.gl.Renderable;

public class GLCarousel implements Renderable, MouseListener, MouseWheelListener {

	private MouseEvent mouse;
	
	private List<GLCarouselItem> items;
	
	public GLCarousel() {
		items = new LinkedList<GLCarouselItem>();
	}

	public void render(GLDrawable drawable) {
		
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
