package org.ambiance.azureus.ruler.commands;

import java.util.Calendar;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.gudy.azureus2.core3.download.DownloadManager;

public class ShareLimitCommand implements Command {
		
	public boolean execute(Context ctx) throws Exception {
		DownloadManager manager = (DownloadManager) ctx.get("manager");
		
		if (manager.getStats().getShareRatio() > 0.6
				|| (manager.getNbPeers() == 0 && Calendar.getInstance().getTimeInMillis()
						- manager.getCreationTime() > 1200000))
			return true;
		
		return false;
	}

}
