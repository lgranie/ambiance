package org.ambiance.audio.player;

import org.codehaus.plexus.PlexusTestCase;

public class JavazoomAudioPlayerTest extends PlexusTestCase {

	AmbianceAudioPlayer aap = null;
	
	public void setUp() {
		try {
			super.setUp();
			aap = (AmbianceAudioPlayer) lookup("org.ambiance.audio.player.AmbianceAudioPlayer", "javazoom");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testPlayMP3() {
		System.out.println("Are you listening 10 secondes music?");
		Exception e = null;
		try {
			aap.open(this.getClass().getResource("/01 Intro.mp3").toString());
			aap.play();
			Thread.sleep(10000);
			aap.stop();
		} catch (Exception e1) {
			e = e1;
			e.printStackTrace();
		}
		
		assertNull(e);
	}
	
	public void testPlayOggFromTheWeb() {
		System.out.println("Are you listening 10 secondes music?");
		Exception e = null;
		try {
			aap.open("http://live.urn1350.net:8080/urn_high.ogg");
			aap.play();
			Thread.sleep(10000);
			aap.stop();
		} catch (Exception e1) {
			e = e1;
			e.printStackTrace();
		}
		
		assertNull(e);
	}
	
	public void testPlayOgg() {
		System.out.println("Are you listening 10 secondes music?");
		Exception e = null;
		try {
			aap.open(this.getClass().getResource("/I Like to Move it.ogg").toString());
			aap.play();
			Thread.sleep(10000);
			aap.stop();
		} catch (Exception e1) {
			e = e1;
			e.printStackTrace();
		}
		
		assertNull(e);
	}
}
