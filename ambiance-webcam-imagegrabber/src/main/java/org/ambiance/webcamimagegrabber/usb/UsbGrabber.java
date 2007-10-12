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

import java.awt.Image;
import java.io.IOException;
import java.util.Vector;

import javax.media.Buffer;
import javax.media.CannotRealizeException;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;

import org.ambiance.webcamimagegrabber.WebCamImageGrabber;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

public class UsbGrabber extends AbstractLogEnabled implements WebCamImageGrabber, Initializable {

	private CaptureDeviceInfo captureVideoDevice = null;

	private VideoFormat captureVideoFormat = null;
	
	private Player player = null;

	public void initialize() throws InitializationException {

		Vector<CaptureDeviceInfo> deviceList = CaptureDeviceManager.getDeviceList(null);

		if (deviceList == null)
			throw new InitializationException("media device list vector is null, program aborted");

		if (deviceList.size() == 0)
			throw new InitializationException("media device list vector size is 0, program aborted");

		for (CaptureDeviceInfo deviceInfo : deviceList) {

			// display device formats
			Format deviceFormat[] = deviceInfo.getFormats();
			for (int i = 0; i < deviceFormat.length; i++) {
				// search for default video device
				if (captureVideoDevice == null)
					if (deviceFormat[i] instanceof VideoFormat)
						captureVideoDevice = deviceInfo;

				// search for default video format
				if (captureVideoDevice == deviceInfo)
					if (captureVideoFormat == null)
						captureVideoFormat = (VideoFormat) deviceFormat[i];
			}
		}
		
		// setup video data source
		MediaLocator videoMediaLocator = captureVideoDevice.getLocator();
		try {
			player = Manager.createRealizedPlayer(videoMediaLocator);
		} catch (NoPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotRealizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		player.start(); 
	}

	public Image grab() throws Exception {
		Buffer buf = null; 
		BufferToImage btoi = null;
		
		// Grab a frame
		FrameGrabbingControl fgc = (FrameGrabbingControl)
		player.getControl("javax.media.control.FrameGrabbingControl");
		buf = fgc.grabFrame();
		
		// Convert it to an image
		btoi = new BufferToImage((VideoFormat)buf.getFormat());
		return btoi.createImage(buf); 
	}

	public void dispose() {
		player.close();
		player.deallocate(); 
	}

}
