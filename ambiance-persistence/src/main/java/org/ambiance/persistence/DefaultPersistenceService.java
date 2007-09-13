package org.ambiance.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

/**
 * @plexus.component role="org.ambiance.persistence.DefaultPersistenceService" role-hint="default"
 */
public class DefaultPersistenceService extends AmbiancePersistenceService implements Initializable {

	// EntityManagerFactory
    EntityManagerFactory emf = null;
	
	public void initialize() throws InitializationException {
		// Start EntityManagerFactory
	    emf = Persistence.createEntityManagerFactory("ambiance");
		
	}

	@Override
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

	@Override
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
