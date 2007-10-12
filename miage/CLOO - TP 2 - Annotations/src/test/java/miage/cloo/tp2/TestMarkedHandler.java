package miage.cloo.tp2;

import junit.framework.TestCase;
import miage.cloo.tp2.annotation.Marked;
import miage.cloo.tp2.annotation.MarkedHandler;
import miage.cloo.tp2.model.Personne;

public class TestMarkedHandler extends TestCase {

	private MarkedHandler mh;
	
	protected void setUp() throws Exception {
		super.setUp();
		mh= new MarkedHandler(new Personne("GRANIE", "Laurent", "Lolo"));
	}

	public void testPrintMarkedNormal() {
		mh.printMarked(Marked.NORMAL);
	}
	
	public void testPrintMarkedMineur() {
		mh.printMarked(Marked.MINEUR);
	}
	
}
