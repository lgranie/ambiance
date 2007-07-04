package org.ambiance.nntpclient;

public interface AmbianceNntpClient {

	/** The Plexus role identifier. */
	String ROLE = AmbianceNntpClient.class.getName();

	public void refreshGroup(String groupname) throws NntpException;
}
