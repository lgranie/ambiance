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
package org.ambiance.feed;

import java.util.Iterator;

import org.ambiance.feed.aggregator.AmbianceFeedAggregator;
import org.codehaus.plexus.PlexusTestCase;

public class RomeFeedAggregatorTest extends PlexusTestCase {

	AmbianceFeedAggregator afr = null;
	
	public void setUp() {
		Exception e = null;
		try {        
			super.setUp();
			afr = (AmbianceFeedAggregator) lookup("org.ambiance.feed.AmbianceFeedAggregator", "aggregator");
		} catch (Exception e1) {
			e = e1;
			e.printStackTrace();
		}  
		
		assertNull(e);
		assertNotNull(afr);
	}
	
	public void testRetrieveRSS() {
		Exception e = null;
		Iterator ite = null;
		try {
			afr.update();
			ite = afr.iterator();
			System.out.println(afr.toString());
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}
		assertNull(e);
		assertNotNull(ite);
		assertTrue(ite.hasNext());
	}

	public void testToXML() {
		Exception e = null;
		String result = null;
		try {
			afr.update();
			result = afr.toXML();
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e1.printStackTrace();
			e = e1;
		}
		assertNull(e);
		assertNotNull(result);
	}
	
}
