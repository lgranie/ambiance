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

import java.io.BufferedInputStream;

import org.ambiance.nntpclient.NntpConnection;
import org.ambiance.nntpclient.NntpException;
import org.ambiance.nntpclient.model.StatusResponse;

public abstract class NntpCommand {
	protected static final String END_RESPONSE_BODY = ".";

	protected StringBuffer cmdLine;

	protected StatusResponse statusResponse;

	protected NntpConnection conn = null;
	
	public NntpCommand(String name, String[] args) {
		// Initializing command line
		cmdLine = new StringBuffer(name);
		if (null != args) {
			for (String param : args) {
				cmdLine.append(" ").append(param);
			}
		}
	}
	
	public NntpCommand(String name, String arg) {
		this(name, new String[] {arg});
	}
	
	public void releaseConnection() {
		conn.release();
	}
	
	public StatusResponse getStatusResponse() {
		return statusResponse;
	}

	public BufferedInputStream getResponseBodyAsStream() {
		return conn.getInput();
	}

	public void checkCommandLine() throws NntpException {
		// RFC 977 : 2.3. Commands
		if (this.getCmdLine().toString().length() > 512)
			throw new NntpException("Command lines shall not exceed 512 characters in length.");
	}
	
	public abstract Object getResponseBodyAsObject() throws NntpException;

	public abstract void execute(NntpConnection conn) throws NntpException;

	public StringBuffer getCmdLine() {
		return cmdLine;
	}
}
