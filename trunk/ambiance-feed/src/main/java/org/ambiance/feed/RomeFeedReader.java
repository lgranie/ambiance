package org.ambiance.feed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ambiance.transport.Transporter;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * classe permettant d'aggreger les flux RSS en un seul
 * 
 * @author moumbe
 * @version 1.0 (09/01/07)
 * @plexus.component role="org.ambiance.feed.AmbianceFeedReader" role-hint="rome"
 */

public class RomeFeedReader extends AbstractLogEnabled implements AmbianceFeedReader, Initializable {

	/**
	 * @plexus.requirement role="org.ambiance.transport.Transporter" role-hint="default"
	 */
	private Transporter transporter;

	/**
	 * @plexus.configuration default-value="<input-url>http://your-feed-url</input-url>"
	 */
	private List<String> inputUrls;

	private SyndFeedInput feedInput;

	private SyndFeed feed = null;

	public void initialize() throws InitializationException {

		feed = new SyndFeedImpl();
		feed.setEntries(new ArrayList());
		
		feedInput  = new SyndFeedInput();;	
	}

	public void retrieve() throws AmbianceFeedException {
		
		try {
			for(String url : inputUrls) {
				SyndFeed tmpFeed = feedInput.build(new XmlReader(transporter.getAsStream(url)));
				feed.getEntries().addAll(tmpFeed.getEntries());
			}
		} catch (Exception e) {
			getLogger().info("Enable to get feed from input-url");
		} 
	}

	public Iterator iterator() {
		return feed.getEntries().iterator();
	}
	
	public SyndFeed getFeed() {
		return feed;
	}
}
