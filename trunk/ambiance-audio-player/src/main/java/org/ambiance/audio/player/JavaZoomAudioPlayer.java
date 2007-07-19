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
			InputStream stream = transporter.getAsStream(url, 32768);
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
