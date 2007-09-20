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
