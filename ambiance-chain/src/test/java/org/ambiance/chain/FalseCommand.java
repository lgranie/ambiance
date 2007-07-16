package org.ambiance.chain;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class FalseCommand implements Command {

	@SuppressWarnings("unchecked")
	public boolean execute(Context ctx) throws Exception {
		ctx.put("FalseCommand", "no");
		return false;
	}

}
