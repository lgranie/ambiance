package org.ambiance.audio.player;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javazoom.jlgui.basicplayer.BasicPlayerException;

public interface AmbianceAudioPlayer {

	/** The Plexus role identifier. */
	String ROLE = AmbianceAudioPlayer.class.getName();

	public void open(InputStream is) throws BasicPlayerException;

	public void open(File f) throws BasicPlayerException;

	public void open(URL url) throws BasicPlayerException;

	public long seek(long l) throws BasicPlayerException;

	public void play() throws BasicPlayerException;

	public void stop() throws BasicPlayerException;

	public void pause() throws BasicPlayerException;

	public void resume() throws BasicPlayerException;

	public void setPan(double pan) throws BasicPlayerException;

	public void setGain(double gain) throws BasicPlayerException;

}