package org.ambiance.audio.player;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

/**
 * @plexus.component role="org.ambiance.audio.player.AmbianceAudioPlayer" role-hint="javazoom"
 */
public class JavaZoomAudioPlayer extends AbstractLogEnabled implements AmbianceAudioPlayer, Initializable {

	private BasicPlayer player;

	public void initialize() throws InitializationException {
		player = new BasicPlayer();
		player.addBasicPlayerListener(new AmbianceBasicPlayerListener());
	}

	/* (non-Javadoc)
	 * @see org.ambiance.audio.player.AmbianceAudioPlayer2#open(java.io.InputStream)
	 */
	public void open(InputStream is) throws BasicPlayerException {
		player.open(is);
	}

	/* (non-Javadoc)
	 * @see org.ambiance.audio.player.AmbianceAudioPlayer2#open(java.io.File)
	 */
	public void open(File f) throws BasicPlayerException {
		player.open(f);
	}

	/* (non-Javadoc)
	 * @see org.ambiance.audio.player.AmbianceAudioPlayer2#open(java.net.URL)
	 */
	public void open(URL url) throws BasicPlayerException {
		player.open(url);
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
