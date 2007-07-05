package org.ambiance.azureus;


public interface AmbianceAzureusService {
	
	/** The Plexus role identifier. */
	String ROLE = AmbianceAzureusService.class.getName();
	
	public void downloadFile(String url) throws AmbianceAzureusException;
}
