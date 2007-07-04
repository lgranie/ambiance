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
