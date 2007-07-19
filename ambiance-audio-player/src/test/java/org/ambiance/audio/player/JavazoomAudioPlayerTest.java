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
	
	public void testPlayMp3() {
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
	
	public void testPlayMp3FromTheWeb() {
		System.out.println("Are you listening 10 secondes music?");
		Exception e = null;
		try {
			aap.open("http://yakz1337.free.fr/Mp3/News !/Daft Punk - Digital Love.mp3");
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
