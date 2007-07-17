package org.ambiance.feed.aggregator;

import org.ambiance.feed.AmbianceFeedException;


public interface AmbianceFeedAggregator extends Iterable {
	
	/** The Plexus role identifier. */
    String ROLE = AmbianceFeedAggregator.class.getName();
    
    /**
	 * Update feeds from all URLs
	 * 
	 * @throws AmbianceFeedException
	 */
    public void update() throws AmbianceFeedException;
    
    public String toXML() throws AmbianceFeedException;
    
}
