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
package org.ambiance.webcamimagegrabber.isight;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;

import org.ambiance.webcamimagegrabber.WebCamImageGrabber;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

import quicktime.QTRuntimeException;
import quicktime.QTRuntimeHandler;
import quicktime.QTSession;
import quicktime.qd.PixMap;
import quicktime.qd.QDGraphics;
import quicktime.qd.QDRect;
import quicktime.std.StdQTConstants;
import quicktime.std.sg.SGVideoChannel;
import quicktime.std.sg.SequenceGrabber;
import quicktime.util.RawEncodedImage;

/**
 * @plexus.component role="org.ambiance.webcamimagegrabber.WebCamImageGrabber" role-hint="isight"
 */
public class ISightGrabber extends AbstractLogEnabled implements WebCamImageGrabber, Initializable {
	private SequenceGrabber grabber;
    private SGVideoChannel channel;
    private RawEncodedImage rowEncodedImage;

    /**
	 * @plexus.configuration default-value="640"
	 */
	private int width;
	
	/**
	 * @plexus.configuration default-value="480"
	 */
	private int height;
    private int videoWidth;

    private int[] pixels;
    private BufferedImage image;
    private WritableRaster raster;
	
	public void initialize() throws InitializationException {
		try {
            QTSession.open();
            QDRect bounds = new QDRect(width, height);
            QDGraphics graphics = new QDGraphics(bounds);
            grabber = new SequenceGrabber();
            grabber.setGWorld(graphics, null);
            channel = new SGVideoChannel(grabber);
            channel.setBounds(bounds);
            channel.setUsage(StdQTConstants.seqGrabPreview);
            channel.settingsDialog();
            grabber.prepare(true, false);
            grabber.startPreview();
            PixMap pixMap = graphics.getPixMap();
            rowEncodedImage = pixMap.getPixelData();

            videoWidth = width + (rowEncodedImage.getRowBytes() - width * 4) / 4;
            pixels = new int[videoWidth * height];
            image = new BufferedImage(videoWidth, height, BufferedImage.TYPE_INT_RGB);
            raster = WritableRaster.createPackedRaster(DataBuffer.TYPE_INT,
                videoWidth, height,
                new int[] { 0x00ff0000, 0x0000ff00, 0x000000ff }, null);
            raster.setDataElements(0, 0, videoWidth, height, pixels);
            image.setData(raster);
            QTRuntimeException.registerHandler(new QTRuntimeHandler() {
                public void exceptionOccurred(
                        QTRuntimeException e, Object eGenerator,
                        String methodNameIfKnown, boolean unrecoverableFlag) {
                    	getLogger().debug("what should i do?");
                }
            });
        } catch (Exception e) {
            QTSession.close();
            throw new InitializationException("", e);
        }
		
	}
	
	public void dispose() {
        try {
            grabber.stop();
            grabber.release();
            grabber.disposeChannel(channel);
            image.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            QTSession.close();
        }
    }

    public Image grab() throws Exception {
        grabber.idle();
        rowEncodedImage.copyToArray(0, pixels, 0, pixels.length);
        raster.setDataElements(0, 0, videoWidth, height, pixels);
        image.setData(raster);
        return image;
    }

	

}
