/**
 * Copyright (C) 2007 Laurent GRANIE.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 3, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.ambiance.feed.aggregator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ambiance.feed.AmbianceFeedException;
import org.ambiance.transport.Transporter;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.XmlReader;

/**
 * classe permettant d'aggreger les flux RSS en un seul
 * 
 * @author moumbe
 * @version 1.0 (09/01/07)
 * @plexus.component role="org.ambiance.feed.AmbianceFeedAggregator" role-hint="aggregator"
 */

public class RomeFeedAggregator extends AbstractLogEnabled implements AmbianceFeedAggregator, Initializable {

	/**
	 * @plexus.requirement role="org.ambiance.transport.Transporter" role-hint="default"
	 */
	private Transporter transporter;

	/**
	 * @plexus.configuration default-value="your title"
	 */
	private String title;

	/**
	 * @plexus.configuration default-value="rss_1.0"
	 */
	private String outputType;

	/**
	 * @plexus.configuration default-value="<input-url>http://your-feed-url</input-url>"
	 */
	private List<String> inputUrls;

	private SyndFeedInput feedInput;

	private SyndFeedOutput feedOutput;

	private SyndFeed feed = null;

	public void initialize() throws InitializationException {

		feed = new SyndFeedImpl();
		feed.setFeedType(outputType);
		feed.setTitle(title);

		feed.setEntries(new ArrayList());
		feed.setAuthors(new ArrayList());

		feedInput  = new SyndFeedInput();
		feedOutput = new SyndFeedOutput();

	}

	@SuppressWarnings("unchecked")
	public void update() throws AmbianceFeedException {
		for (String url : inputUrls) {
			try {

				SyndFeed tmpFeed = feedInput.build(new XmlReader(transporter.getAsStream(url)));
				feed.getEntries().addAll(tmpFeed.getEntries());
			} catch (Exception e) {
				getLogger().error("Enable to get feed from input-url : " + url);
				getLogger().debug(e.getMessage());
			}
		}
	}

	public String toXML() throws AmbianceFeedException {
		
		try {
			return feedOutput.outputString (feed);
		} catch (Exception e) {
			getLogger().error("Enable to get XML from feed.");
			throw new AmbianceFeedException(e);
		}
		
	}

	public Iterator iterator() {
		return feed.getEntries().iterator();
	}

	public void setInputUrls(List<String> inputUrls) {
		this.inputUrls = inputUrls;
	}
}
