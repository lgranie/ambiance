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
import org.ambiance.nntpclient.model.Newsgroup;

/**
 * This abstract class represents a command that need to be execute after a GROUP command.
 * 
 * @author < a href="mailto:laurent.granie@gmail.com">Laurent Granie</a>
 */
public abstract class NntpGroupCommand extends NntpCommand {

	protected Newsgroup group;

	public NntpGroupCommand(Newsgroup group, String name, String arg) {
		super(name, arg);
		this.group = group;
	}

	@Override
	public void execute(NntpConnection conn) throws NntpException {
		checkCommandLine();
		
		this.conn = conn;

		// Changing current group if it is not the current one of the connection
		if (!group.getName().equals(conn.getCurrentGroup())) {
			
			this.statusResponse = this.conn.writeCommand("GROUP " + group.getName());

			if (this.statusResponse.getStatus() == 411)
				throw new NntpException(this.statusResponse.getMessage());
			
			this.group.update(this.statusResponse.getMessage());
			
			this.conn.setCurrentGroup(group.getName());
		}

		// Executing the command on the current group
		this.statusResponse = this.conn.writeCommand(cmdLine.toString());
	}

}
