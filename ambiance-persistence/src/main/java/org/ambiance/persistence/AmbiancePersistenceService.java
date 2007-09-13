package org.ambiance.persistence;

import java.util.List;

import org.codehaus.plexus.logging.AbstractLogEnabled;


public abstract class AmbiancePersistenceService extends AbstractLogEnabled {

	/** The Plexus role identifier. */
	String ROLE = AmbiancePersistenceService.class.getName();

	public abstract List query(String query) throws AmbiancePersistenceException;
	
	public abstract void persist(Object o) throws AmbiancePersistenceException;
}
