package org.ambiance.desktop.view;

import org.codehaus.plexus.personality.plexus.lifecycle.phase.StartingException;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.StoppingException;

import com.blogofbug.utility.ImageUtilities;

/**
 * @plexus.component role="org.ambiance.desktop.view.AmbianceView" role-hint="game"
 */
public class AmbianceViewGame extends AmbianceView {

	public void start() throws StartingException {
		setLabel("Game");
		setImage(ImageUtilities.loadCompatibleImage(this.getClass().getResource(getLabel()+ ".png").toString()));
	}

	public void stop() throws StoppingException {
		// TODO Auto-generated method stub
		
	}
	
		
}
