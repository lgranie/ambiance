package org.ambiance.audio.player;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javazoom.jlgui.basicplayer.BasicPlayerException;

public interface AmbianceAudioPlayer {
	/** The Plexus role identifier. */
	String ROLE = AmbianceAudioPlayer.class.getName();
	
	public abstract void open(InputStream is) throws BasicPlayerException;

	public abstract void open(File f) throws BasicPlayerException;

	public abstract void open(URL url) throws BasicPlayerException;

	public abstract long seek(long l) throws BasicPlayerException;

	public abstract void play() throws BasicPlayerException;

	public abstract void stop() throws BasicPlayerException;

	public abstract void pause() throws BasicPlayerException;

	public abstract void resume() throws BasicPlayerException;

	public abstract void setPan(double pan) throws BasicPlayerException;

	public abstract void setGain(double gain) throws BasicPlayerException;

}