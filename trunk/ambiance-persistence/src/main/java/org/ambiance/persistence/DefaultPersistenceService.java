package org.ambiance.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Startable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.StartingException;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.StoppingException;

/**
 * @plexus.component role="org.ambiance.persistence.AmbiancePersistenceService" role-hint="default"
 */
public class DefaultPersistenceService extends AbstractLogEnabled implements Startable, AmbiancePersistenceService {

	/**
	 * @plexus.configuration default-value="ambiance-db"
	 */
	private String persistenceUnitName;
	
	// EntityManagerFactory
    EntityManagerFactory emf = null;
	
    public void start() throws StartingException {
    	// Start EntityManagerFactory
	    emf = Persistence.createEntityManagerFactory(persistenceUnitName);
		
	}

	public void stop() throws StoppingException {
		// TODO Auto-generated method stub
		
	}

	public List query(String query) throws AmbiancePersistenceException {
		List result = null;
		
		EntityManager em = emf.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
	    try {
	    	tx.begin();
	        result = em.createQuery(query).getResultList();
	        tx.commit();
	    } catch (Exception e) {
	    	tx.rollback();
	    	throw new AmbiancePersistenceException(e);
	    } finally {
	    	em.close();
	    }
	    
	    return result;
	    
	}

	public void persist(Object o) throws AmbiancePersistenceException {
		EntityManager em = emf.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
	    try {
	    	tx.begin();
	        em.persist(o);
	        tx.commit();
	    } catch (Exception e) {
	    	tx.rollback();
	    	throw new AmbiancePersistenceException(e);
	    } finally {
	    	em.close();
	    }
		
	}
	
}
