/*
   Copyright (C) 2007 Laurent GRANIE.

   This program is free software; you can redistribute it and/or modify it
   under the terms of the GNU General Public License as published by the
   Free Software Foundation; either version 3, or (at your option) any later
   version.

   This program is distributed in the hope that it will be useful, but
   WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General
   Public License for more details.

   You should have received a copy of the GNU General Public License along
   with this program; if not, write to the Free Software Foundation, Inc.,
   51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
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
			String url = this.getClass().getResource("/I Like to Move it.ogg").toString();
			System.out.println("Trying to play : " + url);
			aap.open(url);
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
			String url = this.getClass().getResource("/01 Intro.mp3").toString();
			System.out.println("Trying to play : " + url);
			aap.open(url);
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
			String url = "http://live.urn1350.net:8080/urn_high.ogg";
			System.out.println("Trying to play : " + url);
			aap.open(url);
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
			String url = "http://joelarsouille.free.fr/nico/Mp3/madona HUNG UP .mp3";
			System.out.println("Trying to play : " + url);
			aap.open(url);
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
