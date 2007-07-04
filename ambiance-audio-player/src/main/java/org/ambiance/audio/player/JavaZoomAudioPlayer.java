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
	}

	public void open(InputStream is) throws BasicPlayerException {
		player.open(is);
	}

	public void open(File f) throws BasicPlayerException {
		player.open(f);
	}

	public void open(URL url) throws BasicPlayerException {
		player.open(url);
	}

	public long seek(long l) throws BasicPlayerException {
		return player.seek(l);
	}

	public void play() throws BasicPlayerException {
		player.play();
	}

	public void stop() throws BasicPlayerException {
		player.stop();
	}

	public void pause() throws BasicPlayerException {
		player.pause();
	}

	public void resume() throws BasicPlayerException {
		player.resume();
	}

	public void setPan(double pan) throws BasicPlayerException {
		player.setPan(pan);
	}

	public void setGain(double gain) throws BasicPlayerException {
		player.setGain(gain);
	}

}
