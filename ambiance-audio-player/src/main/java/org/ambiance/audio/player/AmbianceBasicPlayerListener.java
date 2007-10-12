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

import java.util.Map;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

public class AmbianceBasicPlayerListener implements BasicPlayerListener {

	@SuppressWarnings("unchecked")
	public void opened(Object stream, Map properties) {
		
	}

	@SuppressWarnings("unchecked")
	public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties) {
		
	}

	public void stateUpdated(BasicPlayerEvent evt) {
		
	}

	public void setController(BasicController bc) {
		
	}

}
