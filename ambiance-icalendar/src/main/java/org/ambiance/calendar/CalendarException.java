package org.ambiance.calendar;

public class CalendarException extends Exception {

	private static final long serialVersionUID = -2595079519222542085L;

	public CalendarException() {
		super();
	}

	public CalendarException(String msg) {
		super(msg);
	}

	public CalendarException(String msg, Throwable t) {
		super(msg, t);
	}

	public CalendarException(Throwable t) {
		super(t);
	}

}
