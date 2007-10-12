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