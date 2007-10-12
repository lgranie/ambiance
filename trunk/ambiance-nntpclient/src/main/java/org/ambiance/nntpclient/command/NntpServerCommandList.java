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
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.ambiance.nntpclient.NntpException;
import org.ambiance.nntpclient.model.Newsgroup;

public class NntpServerCommandList extends NntpServerCommand {
	private static final String NAME = "LIST";

	public NntpServerCommandList() {
		super(NntpServerCommandList.NAME, null);
	}

	@Override
	public Object getResponseBodyAsObject() throws NntpException {
		List<Newsgroup> result = new ArrayList<Newsgroup>();

		BufferedReader br = new BufferedReader(new InputStreamReader(
				getResponseBodyAsStream()));
		String line = null;
		try {
			line = br.readLine().trim();
			while (null != line && !END_RESPONSE_BODY.equals(line)) {
				System.out.println(line);
				StringTokenizer st = new StringTokenizer(line);

				if (st.countTokens() != 4)
					throw new NntpException("Response line does not match : "
							+ line);

				((List<Newsgroup>) result).add(new Newsgroup(st.nextToken(),
						Integer.parseInt(st.nextToken()), Integer.parseInt(st
								.nextToken()), "y".equals(st.nextToken())));

				line = br.readLine();
			}
		} catch (IOException e) {
			throw new NntpException("Error while parsing response line : "
					+ line);
		}
		return result;
	}

}
