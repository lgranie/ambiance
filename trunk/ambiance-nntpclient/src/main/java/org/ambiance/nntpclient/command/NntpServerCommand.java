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

import org.ambiance.nntpclient.NntpConnection;
import org.ambiance.nntpclient.NntpException;


/**
 * This abstract class represents a command on a server like LIST, GROUP, ....
 * 
 * @author < a href="mailto:laurent.granie@gmail.com">Laurent Granié</a>
 * 
 */
public abstract class NntpServerCommand extends NntpCommand {

	public NntpServerCommand(String name, String[] args) {
		super(name, args);
	}
	
	@Override
	public void execute(NntpConnection conn) throws NntpException {
		checkCommandLine();
		this.conn = conn;
		this.statusResponse = conn.writeCommand(this.cmdLine.toString());
	}
}
