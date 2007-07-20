package org.ambiance.webcamimagegrabber;

import java.awt.Image;

public interface WebCamImageGrabber {
	
	/** The Plexus role identifier. */
    String ROLE = WebCamImageGrabber.class.getName();

	public Image grab() throws Exception;
	
	public void dispose();
}
