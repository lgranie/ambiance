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
