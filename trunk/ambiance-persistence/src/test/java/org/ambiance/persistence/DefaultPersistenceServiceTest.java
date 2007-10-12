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
package org.ambiance.persistence;

import java.util.List;

import org.ambiance.persistence.model.Message;
import org.codehaus.plexus.PlexusTestCase;

public class DefaultPersistenceServiceTest extends PlexusTestCase {
	private AmbiancePersistenceService service = null;
	
	public void setUp() {
		Exception e = null;
		
		try {
			super.setUp();
			service = (AmbiancePersistenceService) lookup("org.ambiance.persistence.AmbiancePersistenceService", "default");
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}
		assertNull(e);
		assertNotNull(service);
	}
	
	public void testPersist() {
		Exception e = null;
		
		Message message = new Message("Hello World with JPA");
		try {
			service.persist(message);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}
		assertNull(e);
	}	
	
	@SuppressWarnings("unchecked")
	public void testQuery() {
		Exception e = null;
	
		List<Message> results = null;
		try {
			results = (List<Message>) service.query("select m from Message m order by m.text asc");
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}
		assertNull(e);
		assertNotNull(results);
		assertFalse(results.isEmpty());
	}
}
