package miage.cloo.tp2.persistence;

import junit.framework.TestCase;
import miage.cloo.tp2.model.Personne;

public class PersistenceManagerTest extends TestCase {

	PersistenceManager pm = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		pm = PersistenceManager.getInstance();
		assertNotNull(pm);
	}

	public void testInsert() {
		Personne p = new Personne("GRANIE", "Laurent", "Lolo");
		Exception e = null;
		try {
			pm.insert(p);
		} catch (PersistenceException pe) {
			e = pe;
			e.printStackTrace();
		}
		assertNull(e);
	}
	
	public void testSelect() {
		Personne p = null;
		Exception e = null;
		try {
			p = (Personne) pm.select("GRANIE", Personne.class);
		} catch (PersistenceException pe) {
			e = pe;
			e.printStackTrace();
		}
		assertNull(e);
		assertNotNull(p);
	}

	public void testUpdate() {
		Personne p = new Personne("GRANIE", "Laurent", "Lol");
		Exception e = null;
		try {
			pm.insert(p);
		} catch (PersistenceException pe) {
			e = pe;
			e.printStackTrace();
		}
		assertNull(e);
	}

}
