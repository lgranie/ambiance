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
package org.ambiance.nntpclient;

import junit.framework.TestCase;

import org.ambiance.nntpclient.NntpException;
import org.ambiance.nntpclient.model.StatusResponse;

public class StatusResponseTest extends TestCase {

	public void testNotMatchNOK() {
		Exception e = null;
		try {
			new StatusResponse("tralala");
		} catch (NntpException e1) {
			e = e1;
		}
		assertNotNull(e);
	}
	
	public void testErrorDuringCmdNOK() {
		Exception e = null;
		try {
			new StatusResponse("402 tralala\r\n");
		} catch (NntpException e1) {
			e = e1;
		}
		assertNotNull(e);
	}
}
