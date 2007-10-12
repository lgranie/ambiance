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
