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
package org.ambiance.yahoo.web;

import org.ambiance.yahoo.QueryYahooService;
import org.codehaus.plexus.PlexusTestCase;


public class AmbianceYahooWebTest extends PlexusTestCase {

	public void testRequest() {
		Exception e = null;
		QueryYahooService yahoo = null;
		Object result = null;
		try {
			yahoo = (QueryYahooService) lookup(
					"org.ambiance.yahoo.AmbianceYahooService", "web");
			 result = yahoo.query("intitle:index.of (mp3 OR ogg OR flac) \"mf doom\"");
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}
		assertNull(e);
		assertNotNull(yahoo);
		assertNotNull(result);
	}

	
}
