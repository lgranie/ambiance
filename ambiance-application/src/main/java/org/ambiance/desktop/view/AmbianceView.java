package org.ambiance.desktop.view;

import java.awt.Image;

import javax.swing.JFrame;

import org.codehaus.plexus.logging.AbstractLogEnabled;

public abstract class AmbianceView extends AbstractLogEnabled {
	
	/** The Plexus role identifier. */
	String ROLE = AmbianceView.class.getName();

	protected JFrame frame;
		
	private String label;
	
	private Image image;
	
	public void setFrame(JFrame frame) {
		this.frame = frame;
		frame.pack();
	}

	public String getLabel() {
		return label;
	}
	
	public Image getImage() {
		return image;
	}
	
}
