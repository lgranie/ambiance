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
package org.ambiance.nntpclient;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ambiance.nntpclient.command.NntpGroupCommandHead;
import org.ambiance.nntpclient.command.NntpGroupCommandNext;
import org.ambiance.nntpclient.command.NntpServerCommandGroup;
import org.ambiance.nntpclient.model.Newsgroup;
import org.ambiance.nntpclient.model.NntpServer;
import org.ambiance.nntpclient.nzb.File;
import org.ambiance.nntpclient.nzb.Segment;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

/**
 * @plexus.component role="org.ambiance.nntpclient.AmbianceNntpClient" role-hint="default"
 */
public class DefaultNntpClient extends AbstractLogEnabled implements AmbianceNntpClient, Initializable {
	/**
	 * @plexus.configuration default-value="news.free.fr"
	 */
	private String serverHost;

	/**
	 * @plexus.configuration default-value="119"
	 */
	private int serverPort;

	/**
	 * @plexus.configuration default-value="4"
	 */
	private int serverMaxConnections;

	private NntpConnectionManager ncm = null;
	
	private FileFactory fileFactory = null;

	public void initialize() throws InitializationException {

		NntpServer server = new NntpServer(serverHost, serverPort, serverMaxConnections);

		if (null == server)
			throw new IllegalArgumentException("Params may not be null");

		try {
			this.ncm = new NntpConnectionManager(server);
		} catch (NntpException e) {
			getLogger().error("Error while initializing server");
			throw new InitializationException("Error while initializing server", e);
		}
		
		fileFactory = new FileFactory();
		
	}

	public void refreshGroup(String groupname) throws NntpException {
		NntpConnection conn = ncm.getConnection();
		
		NntpServerCommandGroup cmd = new NntpServerCommandGroup(groupname);
		cmd.execute(conn);
		Newsgroup group = (Newsgroup) cmd.getResponseBodyAsObject();

		NntpGroupCommandNext ncmd = new NntpGroupCommandNext(group);
		NntpGroupCommandHead hcmd = new NntpGroupCommandHead(group);
		
		Pattern pheader   = Pattern.compile("(.*)\\((\\d+)/(\\d+)\\).*");
		Pattern pfilename = Pattern.compile(".*\"(.*)\".*");
				
		while (true) {
			try {
				ncmd.execute(conn);
			} catch (NntpException e) {
				break;
			}
			
			try {
				hcmd.execute(conn);
				Map<String, String> headers = (Map<String, String>) hcmd.getResponseBodyAsObject();
				//getLogger().info(headers.get("Message-ID") + "\t" + headers.get("Date") + "\t" + headers.get("Subject"));
				
				Matcher headMatcher = pheader.matcher(headers.get("Subject")); 
				if(headMatcher.matches()) {
					String filename = headMatcher.group(1);
					
					Matcher filenameMatcher = pfilename.matcher(filename);
					if (filenameMatcher.matches())
						filename = filenameMatcher.group(1);

					File file = fileFactory.getFile(headers, filename);
										
					Segment segment = new Segment();
					segment.setNumber(headMatcher.group(2));
					segment.setvalue(headers.get("Message-ID").substring(1, headers.get("Message-ID").length()-1));
					
					file.getSegments().getSegment().add(segment);
					
					//getLogger().info(headMatcher.group(1) + "\t" + headMatcher.group(3) + "\t" + file.getSegments().getSegment().size());
					if(headMatcher.group(3).equals(""+file.getSegments().getSegment().size())) {
						getLogger().info(filename);
						fileFactory.dump(filename);
					}
				}
				
			} catch (NntpException e) {
				continue;
			}
		}
		
		// LGE - free the connection
		cmd.releaseConnection();

	}
}
