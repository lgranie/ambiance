package org.ambiance.azureus.commands;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public abstract class AbstractCommand implements Command {
	
	public static final boolean CHAIN_STOP = true;
    public static final boolean CHAIN_CONTINUE = false;
	
	public abstract boolean execute(Context ctx) throws Exception;

}
