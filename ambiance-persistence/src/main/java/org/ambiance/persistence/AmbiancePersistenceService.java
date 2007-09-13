package org.ambiance.persistence;

import java.util.List;

import org.codehaus.plexus.logging.AbstractLogEnabled;


public interface AmbiancePersistenceService {

	/** The Plexus role identifier. */
	String ROLE = AmbiancePersistenceService.class.getName();

	public List query(String query) throws AmbiancePersistenceException;
	
	public void persist(Object o) throws AmbiancePersistenceException;
}
