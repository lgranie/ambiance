package org.ambiance.codec;

import java.io.File;

import org.apache.commons.codec.DecoderException;

public interface Decoder {
	
	/** The Plexus role identifier. */
	String ROLE = Decoder.class.getName();
	
	public void decode(File input) throws DecoderException;
	
	public void decode(File[] inputs) throws DecoderException;
	
}
