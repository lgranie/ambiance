package org.ambiance.azureus.commands.downloading;

import org.ambiance.azureus.commands.AbstractCommand;
import org.apache.commons.chain.Context;
import org.gudy.azureus2.core3.download.DownloadManager;

public class IncompleteCommand extends AbstractCommand {
		
	public boolean execute(Context ctx) throws Exception {
		DownloadManager manager = (DownloadManager) ctx.get("manager");
		
		if (manager.getNbSeeds() > 0
				&& manager.getStats().getAvailability() <= manager.getStats().getCompleted() / 1000.0)
			return CHAIN_STOP;
		
		return CHAIN_CONTINUE;
	}

}
