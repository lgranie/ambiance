package org.ambiance.azureus.commands;

import java.util.ResourceBundle;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public abstract class AbstractCommand implements Command {
	
	protected ResourceBundle props = null;
	
	public AbstractCommand() {
		props = ResourceBundle.getBundle("commands");
	}
	
	public abstract boolean execute(Context ctx) throws Exception;

}
