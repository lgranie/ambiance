package org.ambiance.azureus.ruler;

import org.apache.commons.chain.Context;


public interface AmbianceAzureusRuler {
	
	/** The Plexus role identifier. */
	String ROLE = AmbianceAzureusRuler.class.getName();
	
	public boolean execute(String name, Context ctx) throws AmbianceAzureusRulerException;
	
}
