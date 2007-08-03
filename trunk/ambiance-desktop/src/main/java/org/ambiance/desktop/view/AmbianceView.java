package org.ambiance.desktop.view;

import javax.swing.JFrame;

public interface AmbianceView {
	
	/** The Plexus role identifier. */
	String ROLE = AmbianceView.class.getName();

	void setFrame(JFrame frame);
	
}
