package org.ambiance.azureus.commands.seeding;

import java.util.Calendar;

import org.ambiance.azureus.commands.AbstractCommand;
import org.apache.commons.chain.Context;
import org.gudy.azureus2.core3.download.DownloadManager;

public class ShareLimitCommand extends AbstractCommand {
		
	public boolean execute(Context ctx) throws Exception {
		DownloadManager manager = (DownloadManager) ctx.get("manager");
		
		if (manager.getStats().getShareRatio() > 0.6
				|| (manager.getNbPeers() == 0 && Calendar.getInstance().getTimeInMillis()
						- manager.getCreationTime() > 1200000))
			return true;
		
		return false;
	}

}
