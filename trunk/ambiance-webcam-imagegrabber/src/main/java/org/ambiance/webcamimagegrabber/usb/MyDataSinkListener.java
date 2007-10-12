/**
 * Copyright (C) 2007 Laurent GRANIE.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 3, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.ambiance.webcamimagegrabber.usb;

/******************************************************
 * File: MyDataSinkListener.java
 * created 24.07.2001 21:41:47 by David Fischer, fischer@d-fischer.com
 * Decription: simple data sink listener, used to check for end of stream
 */

import javax.media.datasink.*;


public class MyDataSinkListener implements DataSinkListener {
	boolean endOfStream = false;

	public void dataSinkUpdate(DataSinkEvent event)
	{
		if (event instanceof javax.media.datasink.EndOfStreamEvent)
			endOfStream = true;
	}

	public void waitEndOfStream(long checkTimeMs)
	{
		while (! endOfStream)
		{
			Stdout.log("datasink: waiting for end of stream ...");
			try { Thread.currentThread().sleep(checkTimeMs); } catch (InterruptedException ie) {}
		}
		Stdout.log("datasink: ... end of stream reached.");
	}
}


