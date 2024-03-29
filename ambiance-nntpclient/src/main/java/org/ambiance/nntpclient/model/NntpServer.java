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

/**
 * Holds all of the variables needed to describe a remote NNTP server host. This
 * includes remote hostname, port and max connections number.
 * 
 * @author <a href="mailto:laurent.granie@gmail.com">Laurent Grani�</a>
 */
public class NntpServer {

	/** The default port number (RFC 977 : 2.1. Overview) */
	public static final int DEFAULT_PORT = 119;

	/** The default maximum simultaneous connections to this server (4) */
	public static final int DEFAULT_MAX_CONNECTIONS = 4;

	/** The hostname to use */
	private String hostname;

	/** The port to use. */
	private int port;

	/** The maximum number of simultaneous connections to this server to use. */
	private int maxConnections;

	/**
	 * Constructor for Server
	 * 
	 * @param hostname
	 *            the hostname of the nntp server
	 * @param port
	 *            the port. Value <code>-1</code> can be used to set default
	 *            protocol port
	 * @param maxConnections
	 *            the maximum number of simultaneous connections to this server.
	 *            Value <code>-1</code> can be used to set default value
	 */
	public NntpServer(String hostname, int port, int maxConnections) {
		if (null == hostname)
			throw new IllegalArgumentException("Host name may not be null");
		this.hostname = hostname;

		if (port >= 0) {
			this.port = port;
		} else {
			this.port = DEFAULT_PORT;
		}

		if (maxConnections >= 1) {
			this.maxConnections = maxConnections;
		} else {
			this.maxConnections = DEFAULT_MAX_CONNECTIONS;
		}
	}

	public NntpServer(String hostname) {
		this(hostname, -1, -1);
	}

	public String getHostname() {
		return hostname;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public int getPort() {
		return port;
	}

}
