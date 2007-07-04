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
