package org.ambiance.feed;

import java.util.Iterator;

import org.ambiance.feed.aggregator.AmbianceFeedAggregator;
import org.codehaus.plexus.PlexusTestCase;

public class RomeFeedAggregatorTest extends PlexusTestCase {

	AmbianceFeedAggregator afr = null;
	
	public void setUp() {
		try {        
			super.setUp();
			afr = (AmbianceFeedAggregator) lookup("org.ambiance.feed.AmbianceFeedAggregator", "aggregator");
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	public void testRetrieveRSS() {
		Exception e = null;
		Iterator ite = null;
		try {
			afr.update();
			ite = afr.iterator();
			System.out.println(afr.toString());
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}
		assertNull(e);
		assertNotNull(ite);
		assertTrue(ite.hasNext());
	}

	public void testToXML() {
		Exception e = null;
		String result = null;
		try {
			afr.update();
			result = afr.toXML();
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e1.printStackTrace();
			e = e1;
		}
		assertNull(e);
		assertNotNull(result);
	}
	
}
