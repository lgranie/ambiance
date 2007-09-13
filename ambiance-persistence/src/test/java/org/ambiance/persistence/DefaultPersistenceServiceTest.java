package org.ambiance.persistence;

import java.util.List;

import org.ambiance.persistence.model.Message;
import org.codehaus.plexus.PlexusTestCase;

public class DefaultPersistenceServiceTest extends PlexusTestCase {

	public void testLookup() {
		Exception e = null;
		AmbiancePersistenceService service = null;
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
		AmbiancePersistenceService service = null;
		Message message = new Message("Hello World with JPA");
		try {
			service = (AmbiancePersistenceService) lookup("org.ambiance.persistence.DefaultPersistenceService", "default");
			service.persist(message);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}
		assertNull(e);
		assertNotNull(service);
	}	
	
	public void testQuery() {
		Exception e = null;
		AmbiancePersistenceService service = null;
		List results = null;
		try {
			service = (AmbiancePersistenceService) lookup("org.ambiance.persistence.DefaultPersistenceService", "default");
			service.query("select m from Message m order by m.text asc");
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}
		assertNull(e);
		assertNotNull(service);
		assertNotNull(results);
	}
}
