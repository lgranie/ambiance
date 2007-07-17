package org.ambiance.audio.player;

import javazoom.jlgui.basicplayer.BasicPlayerException;

import org.codehaus.plexus.PlexusTestCase;

public class JavazoomAudioPlayerTest extends PlexusTestCase {

	AmbianceAudioPlayer aap = null;
	
	public void setUp() {
		try {
			super.setUp();
			aap = (AmbianceAudioPlayer) lookup("org.ambiance.audio.player.AmbianceAudioPlayer","javazoom");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testPlay() {
		Exception e = null;
		try {
			aap.open(this.getClass().getResource("/I Like to Move it.ogg"));
			aap.play();
		} catch (BasicPlayerException e1) {
			e = e1;
			e.printStackTrace();
		}
		
		assertNull(e);
	}
	
}
