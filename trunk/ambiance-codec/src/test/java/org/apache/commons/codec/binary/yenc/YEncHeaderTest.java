package org.apache.commons.codec.binary.yenc;

import junit.framework.TestCase;


public class YEncHeaderTest extends TestCase {

	public void testYEncHeader() {
		
		String line = "=ybegin line=128 size=123 name=test.dat";
		try {
			YEncHeader header = new YEncHeader(line);
			assertNotNull(header);
			assertEquals(-1, header.getPart());
			assertEquals(128, header.getLine());
			assertEquals(123, header.getSize());
			assertEquals("test.dat", header.getName());
		} catch (YEncException e) {
			e.printStackTrace();
			assertNotNull(null);
		} 
		
		line = "=ybegin part=1 line=128 size=123 name=test.dat";
		try {
			YEncHeader header2 = new YEncHeader(line);
			assertNotNull(header2);
			assertEquals(1, header2.getPart());
			assertEquals(128, header2.getLine());
			assertEquals(123, header2.getSize());
			assertEquals("test.dat", header2.getName());
		} catch (YEncException e) {
			e.printStackTrace();
			assertNotNull(null);
		} 
		
	}

}
