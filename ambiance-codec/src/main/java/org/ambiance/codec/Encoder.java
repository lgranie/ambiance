package org.ambiance.codec;

import java.io.File;

import org.apache.commons.codec.EncoderException;

public interface Encoder {
	
	/** The Plexus role identifier. */
	String ROLE = Encoder.class.getName();
	
	public void encode(File input) throws EncoderException;
}
