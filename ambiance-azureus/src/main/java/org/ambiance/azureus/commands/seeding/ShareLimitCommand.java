package org.ambiance.azureus.commands.seeding;

import java.util.Calendar;

import org.ambiance.azureus.commands.AbstractCommand;
import org.apache.commons.chain.Context;
import org.gudy.azureus2.core3.download.DownloadManager;

public class ShareLimitCommand extends AbstractCommand {
	
	private int shareLimitRation;
	
	private double shareLimitTime;
	
	public ShareLimitCommand() {
		shareLimitRation = Integer.parseInt(props.getString("share.limit.ration"));
		shareLimitTime   = Integer.parseInt(props.getString("share.limit.time"));
	}
	
	public boolean execute(Context ctx) throws Exception {
		DownloadManager manager = (DownloadManager) ctx.get("manager");
		
		if (manager.getStats().getShareRatio() > shareLimitRation
				|| (manager.getNbPeers() == 0 && Calendar.getInstance().getTimeInMillis()
						- manager.getCreationTime() > shareLimitTime))
			return true;
		
		return false;
	}

}
