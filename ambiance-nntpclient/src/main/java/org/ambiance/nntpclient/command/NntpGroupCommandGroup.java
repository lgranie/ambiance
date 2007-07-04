package org.ambiance.nntpclient.command;

import org.ambiance.nntpclient.NntpException;
import org.ambiance.nntpclient.model.Newsgroup;

public class NntpGroupCommandGroup extends NntpGroupCommand {
	private static final String NAME = "FIRST";

	public NntpGroupCommandGroup(Newsgroup group, String messageId)
			throws NntpException {
		super(group, NntpGroupCommandGroup.NAME, messageId);
	}

	@Override
	public Object getResponseBodyAsObject() throws NntpException {
		return null;
	}

}
