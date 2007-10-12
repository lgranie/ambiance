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