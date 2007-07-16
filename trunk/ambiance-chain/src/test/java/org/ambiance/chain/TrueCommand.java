package org.ambiance.chain;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class TrueCommand implements Command {

	@SuppressWarnings("unchecked")
	public boolean execute(Context ctx) throws Exception {
		ctx.put("TrueCommand", "yes");
		return true;
	}

}
