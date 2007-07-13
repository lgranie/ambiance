package org.ambiance.azureus.ruler.commands;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.gudy.azureus2.core3.download.DownloadManager;

public class IncompleteCommand implements Command {
		
	public boolean execute(Context ctx) throws Exception {
		DownloadManager manager = (DownloadManager) ctx.get("manager");
		
		if (manager.getNbSeeds() > 0
				&& manager.getStats().getAvailability() <= manager.getStats().getCompleted() / 1000.0)
			return false;
		
		return true;
	}

}
