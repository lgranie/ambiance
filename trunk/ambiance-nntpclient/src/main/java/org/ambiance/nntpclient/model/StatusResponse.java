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
package org.ambiance.nntpclient.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ambiance.nntpclient.NntpException;

public class StatusResponse {
	private static final Pattern PATTERN = Pattern.compile("^(\\d{3})\\s*(.*)$");

	private int status;

	private String message;

	public StatusResponse(String response) throws NntpException {
		Matcher m =  StatusResponse.PATTERN.matcher(response);
		
		if(!m.matches() || m.groupCount() != 2)
			throw new NntpException("Status response does not match.");
		
		this.status = Integer.parseInt(m.group(1));
		this.message = m.group(2);
		
		if(this.status >= 400)
			throw new NntpException("Error while performing command on server : " + response);
	}

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}

}
