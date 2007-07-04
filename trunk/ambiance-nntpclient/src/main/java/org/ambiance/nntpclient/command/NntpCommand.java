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
