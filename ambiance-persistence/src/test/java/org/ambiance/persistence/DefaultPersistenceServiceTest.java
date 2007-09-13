package org.ambiance.persistence;

import java.util.List;

import org.ambiance.persistence.model.Message;
import org.codehaus.plexus.PlexusTestCase;

public class DefaultPersistenceServiceTest extends PlexusTestCase {
	private AmbiancePersistenceService service = null;
	
	public void setup() {
		Exception e = null;
		
		try {
			service = (AmbiancePersistenceService) lookup("org.ambiance.persistence.DefaultPersistenceService", "default");
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
	
	public void testQuery() {
		Exception e = null;
	
		List results = null;
		try {
			service.query("select m from Message m order by m.text asc");
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}
		assertNull(e);
		assertNotNull(results);
	}
}
