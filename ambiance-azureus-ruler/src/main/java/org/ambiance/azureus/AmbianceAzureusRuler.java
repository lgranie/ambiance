package org.ambiance.azureus;

import org.apache.commons.chain.Chain;


public interface AmbianceAzureusRuler {
	
	/** The Plexus role identifier. */
	String ROLE = AmbianceAzureusRuler.class.getName();
	
	public Chain getChain(String chainName) throws AmbianceAzureusRulerException;
	
}
