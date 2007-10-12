package miage.cloo.tp2.persistence;

public class PersistenceManager {
	
	private static PersistenceManager pm = null;
	
	private PersistenceManager(){
		// Init connection to DB
	};
	
	public static PersistenceManager getInstance() {
		if(null == pm)
			pm = new PersistenceManager();
		return pm;
	}
	
	public Object select(Object id, Class<?> type) throws PersistenceException {
		Object result = null;
		
		// TODO : Générer une requête SQL de type SELECT
		
		return result;
	}
	
	public void update(Object o) throws PersistenceException {
		// TODO : Générer une requête SQL de type UPDATE
	}
	
	public void insert(Object o) throws PersistenceException {
		// TODO : Générer une requête SQL de type INSERT
	}

}
