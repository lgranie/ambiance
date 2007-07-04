package org.ambiance.calendar;

public interface AmbianceCalendarUpdater {

	/** The Plexus role identifier. */
	String ROLE = AmbianceCalendarUpdater.class.getName();

	public void update() throws CalendarException;
}
