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
