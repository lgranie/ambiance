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
package org.ambiance.nntpclient.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.ambiance.nntpclient.NntpException;
import org.ambiance.nntpclient.model.Newsgroup;

public class NntpGroupCommandHead extends NntpGroupCommand {
	private static final String NAME = "HEAD";

	public NntpGroupCommandHead(Newsgroup group, String arg) {
		super(group, NntpGroupCommandHead.NAME, "<"+arg+">");
	}

	public NntpGroupCommandHead(Newsgroup group) {
		super(group, NntpGroupCommandHead.NAME, "");
	}

	@Override
	public Object getResponseBodyAsObject() throws NntpException {
		
		Map<String, String> headers = new HashMap<String, String>();

		BufferedReader br = new BufferedReader(new InputStreamReader(getResponseBodyAsStream()));
		
		String line = null;
		try {
			line = br.readLine().trim();
			
			int index = -1;
			String key = null;
			String value = null;
		
			while (null != line && !END_RESPONSE_BODY.equals(line)) {
				// Parsing line
				index = line.indexOf(":");
				if (index < 0) {
					// Multi-line value
					value = value+line;
				} else {
					key = line.substring(0, index);
					value = line.substring(index + 1).trim();
				}
				
				// Save value as header
				headers.put(key, value);

				// Read next line
				line = br.readLine();
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		return headers;
	}
}
