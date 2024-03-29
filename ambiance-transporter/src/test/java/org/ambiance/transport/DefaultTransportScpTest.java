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
package org.ambiance.transport;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.codehaus.plexus.PlexusTestCase;
import org.codehaus.plexus.util.FileUtils;

public class DefaultTransportScpTest extends PlexusTestCase {
	
	private Transporter transporter = null;
	
	public void setUp() {
		Exception e = null;
	    try {        
			super.setUp();
			
			transporter = (Transporter)  lookup("org.ambiance.transport.Transporter", "default");
			
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}    
		assertNull(e);
		assertNotNull(transporter);    
	}
	
	public void testURLWithoutSpace() {
		
		Exception e = null;
		File tmp = null;
		int read = 0;
		
		try {
			tmp = FileUtils.createTempFile("ambiance-transporter-", ".test", null);
			transporter.get("scp://lgranie:po09iu87@svn/home/lgranie/derby.log", tmp);
			FileInputStream fis = new FileInputStream(tmp);
			while(fis.read() != -1) {
				read++;
			}
		} catch (Exception e1) {
			e = e1;
			e1.printStackTrace();
		}
		
		assertNull(e);
		assertNotNull(tmp);
		assertEquals(read, 37);
	}
	
	public void testURLWithSpace() {
		
		Exception e = null;
		File tmp = null;
		int read = 0;
		
		try {
			tmp = FileUtils.createTempFile("ambiance-transporter-", ".test", null);
			transporter.get("http://laurent.granie.free.fr/ambiance/test/ambiance-transporter/name with space.txt", tmp);
			FileInputStream fis = new FileInputStream(tmp);
			while(fis.read() != -1) {
				read++;
			}
		} catch (Exception e1) {
			e = e1;
			e1.printStackTrace();
		}
		
		assertNull(e);
		assertNotNull(tmp);
		assertEquals(read, 31);
	}
	
	public void testURLWithoutSpaceAsStream() {
		
		Exception e = null;
		InputStream is = null;
		int read = 0;
		try {
			String url = "http://laurent.granie.free.fr/ambiance/test/ambiance-transporter/name_without_space.txt";
			is = transporter.getAsStream(url);
			while(is.read() != -1) {
				read++;
			}
		} catch (Exception e1) {
			e = e1;
			e1.printStackTrace();
		}
		
		assertNull(e);
		assertNotNull(is);
		assertEquals(read, 37);
	}
	
	public void testURLWithSpaceAsStream() {
		
		Exception e = null;
		InputStream is = null;
		int read = 0;
		try {
			String url = "http://laurent.granie.free.fr/ambiance/test/ambiance-transporter/name with space.txt";
			is = transporter.getAsStream(url);
			while(is.read() != -1) {
				read++;
			}
		} catch (Exception e1) {
			e = e1;
			e1.printStackTrace();
		}
		
		assertNull(e);
		assertNotNull(is);
		assertEquals(read, 31);
	}	
	
}