package org.ambiance.azureus.commands.downloading;

import java.util.Calendar;

import org.ambiance.azureus.commands.AbstractCommand;
import org.apache.commons.chain.Context;
import org.gudy.azureus2.core3.download.DownloadManager;

public class TooLongToStartCommand extends AbstractCommand {
		
	public boolean execute(Context ctx) throws Exception {
		DownloadManager manager = (DownloadManager) ctx.get("manager");
		
		if (manager.getNbSeeds() == 0 && manager.getStats().getCompleted() < 1
				&& (Calendar.getInstance().getTimeInMillis() - manager.getCreationTime()) > 120000) 
			return true;
		
		return false;
	}

}
