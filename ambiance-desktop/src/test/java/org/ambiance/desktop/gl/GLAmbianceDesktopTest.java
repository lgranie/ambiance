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
package org.ambiance.desktop.gl;

import org.ambiance.desktop.gl.GLAmbianceDesktop;
import org.codehaus.plexus.PlexusTestCase;

/**
 * @plexus.component role="org.ambiance.desktop.AmbianceDesktop" role-hint="default"
 */
public class GLAmbianceDesktopTest extends PlexusTestCase {

	public void test1() {
		Exception e = null;
		
		GLAmbianceDesktop dad = null;
		
		try {
			super.setUp();
			dad = (GLAmbianceDesktop) lookup("org.ambiance.desktop.AmbianceDesktop", "gl");
			Thread.sleep(60000);
		} catch (Exception e1) {
			e = e1;
			e.printStackTrace();
		}
		
		assertNull(e);
		assertNotNull(dad);
	}
	
}
