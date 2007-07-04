package org.ambiance.audio.player;

import javazoom.jlgui.basicplayer.BasicController;

public interface AmbianceAudioPlayer extends BasicController {

	/** The Plexus role identifier. */
	String ROLE = AmbianceAudioPlayer.class.getName();

	
}
