package org.ambiance.desktop.view;

import java.awt.Image;

import javax.swing.JFrame;

import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Startable;

public abstract class AmbianceView extends AbstractLogEnabled implements Startable {
	
	/** The Plexus role identifier. */
	String ROLE = AmbianceView.class.getName();

	protected JFrame frame;
	
	/**
	 * @plexus.configuration default="label"
	 */
	private String label;
	
	private Image image;
	
	public void setFrame(JFrame frame) {
		this.frame = frame;
		frame.pack();
	}

	public String getLabel() {
		return label;
	}
	
	protected void setLabel(String label) {
		this.label = label;
	}
	
	public Image getImage() {
		return image;
	}
	
	protected void setImage(Image img) {
		image = img;
	}
	
}
