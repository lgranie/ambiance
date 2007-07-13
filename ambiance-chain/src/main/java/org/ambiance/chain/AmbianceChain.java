package org.ambiance.chain;

import org.apache.commons.chain.Context;


public interface AmbianceChain {
	
	/** The Plexus role identifier. */
	String ROLE = AmbianceChain.class.getName();
		
	public boolean execute(String name, Context ctx) throws AmbianceChainException;
	
}
