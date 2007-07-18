package org.ambiance.audio.player;

import javazoom.jlgui.basicplayer.BasicPlayerException;

public interface AmbianceAudioPlayer {

	/** The Plexus role identifier. */
	String ROLE = AmbianceAudioPlayer.class.getName();

	public void open(String url) throws BasicPlayerException;

	public long seek(long l) throws BasicPlayerException;

	public void play() throws BasicPlayerException;

	public void stop() throws BasicPlayerException;

	public void pause() throws BasicPlayerException;

	public void resume() throws BasicPlayerException;

	public void setPan(double pan) throws BasicPlayerException;

	public void setGain(double gain) throws BasicPlayerException;

}