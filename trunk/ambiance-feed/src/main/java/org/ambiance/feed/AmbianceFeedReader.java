package org.ambiance.feed;

public interface AmbianceFeedReader extends Iterable {
	
	/** The Plexus role identifier. */
    String ROLE = AmbianceFeedReader.class.getName();
    
    /**
	 * Retrieve feeds from an URL
	 * 
	 * @throws AmbianceFeedException
	 */
    public void retrieve() throws AmbianceFeedException ;
    
    
}
