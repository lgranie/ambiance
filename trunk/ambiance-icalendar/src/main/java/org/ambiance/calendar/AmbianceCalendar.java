package org.ambiance.calendar;

public interface AmbianceCalendar {

	/** The Plexus role identifier. */
	String ROLE = AmbianceCalendar.class.getName();

	public void retrieve() throws CalendarException;

	public void publish() throws CalendarException;
}
