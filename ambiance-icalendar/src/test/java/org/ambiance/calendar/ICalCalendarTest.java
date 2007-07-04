package org.ambiance.calendar;

import org.codehaus.plexus.PlexusTestCase;

public class ICalCalendarTest extends PlexusTestCase {

	public void testLookup() {
		Exception e = null;
		AmbianceCalendar calendar = null;
		try {
			calendar = (AmbianceCalendar) lookup(
					"org.ambiance.calendar.AmbianceCalendar", "ical");
			calendar.retrieve();
			calendar.publish();
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}
		assertNull(e);
		assertNotNull(calendar);
	}

}
