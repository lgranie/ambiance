package org.ambiance.feed;

import java.util.Iterator;

import org.codehaus.plexus.PlexusTestCase;

public class RomeFeedReaderTest extends PlexusTestCase {

	public void testLookup() {
		Exception e = null;
		AmbianceFeedReader feedreader = null;
		try {
			feedreader = (AmbianceFeedReader) lookup(
					"org.ambiance.feed.AmbianceFeedReader", "laurent.granie");
			feedreader.retrieve();
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}
		assertNull(e);
		assertNotNull(feedreader);
	}
	
	public void testRetrieveRSS() {
		Exception e = null;
		AmbianceFeedReader feedreader = null;
		Iterator ite = null;
		try {
			feedreader = (AmbianceFeedReader) lookup(
					"org.ambiance.feed.AmbianceFeedReader", "laurent.granie");
			feedreader.retrieve();
			ite = feedreader.iterator();
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}
		assertNull(e);
		assertNotNull(ite);
		assertTrue(ite.hasNext());
	}

}
