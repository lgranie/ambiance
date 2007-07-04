package org.apache.commons.codec.binary.yenc;

import junit.framework.TestCase;


public class YEncTrailerTest extends TestCase {

	public void testYEncHeader() {
		
		String line = "=yend size=123";
		try {
			YEncTrailer trailer = new YEncTrailer(line);
			assertNotNull(trailer);
			assertEquals(123, trailer.getSize());
			assertEquals(-1, trailer.getPart());
			assertNull(trailer.getPcrc32());
			assertNull(trailer.getCrc32());
		} catch (YEncException e) {
			e.printStackTrace();
			assertNotNull(null);
		}
		
		line = "=yend size=123 crc32=456";
		try {
			YEncTrailer trailer2 = new YEncTrailer(line);
			assertNotNull(trailer2);
			assertEquals(123, trailer2.getSize());
			assertEquals(-1, trailer2.getPart());
			assertNull(trailer2.getPcrc32());
			assertEquals("456", trailer2.getCrc32());
		} catch (YEncException e) {
			e.printStackTrace();
			assertNotNull(null);
		}
		
		line = "=yend size=123 part=5 pcrc32=A59C8BE9";
		try {
			YEncTrailer trailer3 = new YEncTrailer(line);
			assertNotNull(trailer3);
			assertEquals(123, trailer3.getSize());
			assertEquals(5, trailer3.getPart());
			assertEquals("A59C8BE9", trailer3.getPcrc32());
			assertNull(trailer3.getCrc32());
		} catch (YEncException e) {
			e.printStackTrace();
			assertNotNull(null);
		}
		 
		
	}
	

}
