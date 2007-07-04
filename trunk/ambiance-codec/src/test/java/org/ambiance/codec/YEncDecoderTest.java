package org.ambiance.codec;

import java.io.File;

import org.codehaus.plexus.PlexusTestCase;


public class YEncDecoderTest extends PlexusTestCase {

	public void testDecodeSimpleOK() {
		Exception e = null;
		try {
			Decoder yencDecoder = (Decoder) lookup(Decoder.ROLE, "yenc");
			
			// Retrieve file to decode
			File input = getTestFile("src/test/resources/whatsnew.txt.ntx");
			
			// Decode file
			yencDecoder.decode(input);
			
		} catch (Exception ne) {
			System.out.println(ne.getMessage());
			e = ne;
		}
		
		assertNull(e);
	}

}