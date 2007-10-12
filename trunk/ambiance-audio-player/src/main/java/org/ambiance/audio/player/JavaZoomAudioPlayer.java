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

import java.io.InputStream;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import org.ambiance.transport.Transporter;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

/**
 * @plexus.component role="org.ambiance.audio.player.AmbianceAudioPlayer" role-hint="javazoom"
 */
public class JavaZoomAudioPlayer extends AbstractLogEnabled implements AmbianceAudioPlayer, Initializable {

	private BasicPlayer player;
	
	private static final int BUFFER_SIZE = 65536;

	/**
	 * @plexus.requirement role="org.ambiance.transport.Transporter" role-hint="default"
	 */
	private Transporter transporter;

	public void initialize() throws InitializationException {
		player = new BasicPlayer();
	    
		player.addBasicPlayerListener(new AmbianceBasicPlayerListener());
	}

	public void open(String url) throws BasicPlayerException {
		try {
			InputStream stream = transporter.getAsStream(url, BUFFER_SIZE);
			player.open(stream);
		} catch(Exception e) {
			throw new BasicPlayerException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.ambiance.audio.player.AmbianceAudioPlayer2#seek(long)
	 */
	public long seek(long l) throws BasicPlayerException {
		return player.seek(l);
	}

	/* (non-Javadoc)
	 * @see org.ambiance.audio.player.AmbianceAudioPlayer2#play()
	 */
	public void play() throws BasicPlayerException {
		player.play();
	}

	/* (non-Javadoc)
	 * @see org.ambiance.audio.player.AmbianceAudioPlayer2#stop()
	 */
	public void stop() throws BasicPlayerException {
		player.stop();
	}

	/* (non-Javadoc)
	 * @see org.ambiance.audio.player.AmbianceAudioPlayer2#pause()
	 */
	public void pause() throws BasicPlayerException {
		player.pause();
	}

	/* (non-Javadoc)
	 * @see org.ambiance.audio.player.AmbianceAudioPlayer2#resume()
	 */
	public void resume() throws BasicPlayerException {
		player.resume();
	}

	/* (non-Javadoc)
	 * @see org.ambiance.audio.player.AmbianceAudioPlayer2#setPan(double)
	 */
	public void setPan(double pan) throws BasicPlayerException {
		player.setPan(pan);
	}

	/* (non-Javadoc)
	 * @see org.ambiance.audio.player.AmbianceAudioPlayer2#setGain(double)
	 */
	public void setGain(double gain) throws BasicPlayerException {
		player.setGain(gain);
	}

}
