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

import org.ambiance.nntpclient.NntpException;
import org.ambiance.nntpclient.model.Newsgroup;

public class NntpServerCommandGroup extends NntpServerCommand {
	private static final String NAME = "GROUP";

	public NntpServerCommandGroup(String groupname) {
		super(NntpServerCommandGroup.NAME, new String[] {groupname});
	}

	@Override
	public Object getResponseBodyAsObject() throws NntpException {
		if (this.statusResponse.getStatus() == 411)
			throw new NntpException(this.statusResponse.getMessage());
		
		Newsgroup group = new Newsgroup(null);
		group.update(this.statusResponse.getMessage());
		this.conn.setCurrentGroup(group.getName());
		return group;
	}

}
