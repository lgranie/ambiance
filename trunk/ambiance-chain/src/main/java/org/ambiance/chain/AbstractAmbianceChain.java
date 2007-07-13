package org.ambiance.chain;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

public abstract class AbstractAmbianceChain extends AbstractLogEnabled implements AmbianceChain, Initializable {

	protected Catalog catalog;
	
	public abstract void initialize() throws InitializationException;

	public boolean execute(String name, Context ctx) throws AmbianceChainException {
		Command cmd = catalog.getCommand(name);

		try {
			return cmd.execute(ctx);
		} catch (Exception e) {
			throw new AmbianceChainException("Error while executing command : " + name, e);
		}
		
	}

}
