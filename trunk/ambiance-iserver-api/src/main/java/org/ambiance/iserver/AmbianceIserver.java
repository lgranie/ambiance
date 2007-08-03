package org.ambiance.iserver;

public interface AmbianceIserver {

	/** The Plexus role identifier. */
	String ROLE = AmbianceIserver.class.getName();
	
	
	public void start() throws AmbianceIserverException;

}
