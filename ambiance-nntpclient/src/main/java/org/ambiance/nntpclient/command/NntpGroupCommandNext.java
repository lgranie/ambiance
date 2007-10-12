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

import org.ambiance.nntpclient.NntpException;
import org.ambiance.nntpclient.model.Newsgroup;

public class NntpGroupCommandNext extends NntpGroupCommand {
	private static final String NAME = "NEXT";

	public NntpGroupCommandNext(Newsgroup group) {
		super(group, NntpGroupCommandNext.NAME, "");
	}

	@Override
	public Object getResponseBodyAsObject() throws NntpException {
		StringBuffer sb = new StringBuffer();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
				getResponseBodyAsStream()));
		
		String line = null;
		
		try {
			
			line = br.readLine().trim();
			
			while (null != line && !END_RESPONSE_BODY.equals(line)) {
				sb.append(line+"\r\n");
				line = br.readLine();
			}
			
		} catch (IOException ioe) {
			throw new NntpException(ioe);
		}
		
		return sb;
	}

}
