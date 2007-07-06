package org.ambiance.azureus;

import java.util.List;

import org.gudy.azureus2.core3.download.DownloadManager;


public interface AmbianceAzureusService {
	
	/** The Plexus role identifier. */
	String ROLE = AmbianceAzureusService.class.getName();
	
	public void addDownload(String url) throws AmbianceAzureusException;
	
	public List<DownloadManager> getDownloadManagers();
}
