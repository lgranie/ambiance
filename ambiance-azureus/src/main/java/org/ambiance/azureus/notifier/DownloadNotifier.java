package org.ambiance.azureus.notifier;

import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import org.ambiance.azureus.AmbianceAzureusException;
import org.ambiance.chain.AmbianceChain;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.plexus.util.FileUtils;
import org.gudy.azureus2.core3.download.DownloadManager;
import org.gudy.azureus2.core3.global.GlobalManager;


public class DownloadNotifier implements Runnable {

	private AmbianceChain ac;
	
	private GlobalManager globalManager;
	
	private Context ctx = null;
	
	private long refreshTime;
	
	private static final Log LOGGER = LogFactory.getLog(DownloadNotifier.class);
	
	@SuppressWarnings("unchecked")
	public DownloadNotifier(AmbianceChain ac, GlobalManager globalManager) throws AmbianceAzureusException {
		this.ac = ac;
		this.globalManager = globalManager;
		
		ResourceBundle props = ResourceBundle.getBundle("org.ambiance.azureus.notifier.notifier");
		refreshTime = Long.parseLong((String) ctx.get("refresh.time"));
		
		// Context initialization
		this.ctx = new ContextBase();
		Enumeration keys = props.getKeys();
		while(keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			ctx.put(key, props.getString(key));
		}
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			while (true) {
				List<DownloadManager> managers = globalManager.getDownloadManagers();
				for (DownloadManager manager : managers) {
					
					// Context Initialization
					String torrentName = manager.getDisplayName() + " : ";
					ctx.put("torrentName", torrentName);
					ctx.put("manager", manager);
					
					switch (manager.getState()) {
						
						case DownloadManager.STATE_DOWNLOADING:
							LOGGER.info(
									torrentName + "Download is " + (manager.getStats().getCompleted() / 10.0)
											+ " % complete");
	
							if (ac.execute("downloading", ctx))
								manager.stopIt(DownloadManager.STATE_STOPPED, false, false);
	
							break;
							
						case DownloadManager.STATE_FINISHING:
							LOGGER.info(torrentName + "Finishing Download....");
							break;
							
						case DownloadManager.STATE_SEEDING:
							LOGGER.info(torrentName + "Download Complete - Seeding for other users - Share Ratio = "	+ manager.getStats().getShareRatio());
							if (ac.execute("seeding", ctx))
								manager.stopIt(DownloadManager.STATE_STOPPED, true, false);
							
							break;
							
						case DownloadManager.STATE_STOPPED:
							LOGGER.info(torrentName + "Download Stopped.");
							break;
							
						case DownloadManager.STATE_CLOSED:
							FileUtils.removePath(manager.getTorrentFileName());
							break;
							
					}
				}
				// Refresh every n milliseconds this progress
				Thread.sleep(refreshTime);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
