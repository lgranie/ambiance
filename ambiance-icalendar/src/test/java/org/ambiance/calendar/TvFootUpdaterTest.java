package org.ambiance.calendar;

import org.codehaus.plexus.PlexusTestCase;

public class TvFootUpdaterTest extends PlexusTestCase {

	public void testLookup() {
		Exception e = null;
		AmbianceCalendarUpdater updater = null;
		try {
			updater = (AmbianceCalendarUpdater) lookup(
					"org.ambiance.calendar.AmbianceCalendarUpdater", "fr-football-tv");
			updater.update();
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}
		assertNotNull(updater);
		assertNull(e);
	}

}
