package org.ambiance.nntpclient.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ambiance.nntpclient.NntpException;

public class StatusResponse {
	private static final Pattern PATTERN = Pattern.compile("^(\\d{3})\\s*(.*)$");

	private int status;

	private String message;

	public StatusResponse(String response) throws NntpException {
		Matcher m =  StatusResponse.PATTERN.matcher(response);
		
		if(!m.matches() || m.groupCount() != 2)
			throw new NntpException("Status response does not match.");
		
		this.status = Integer.parseInt(m.group(1));
		this.message = m.group(2);
		
		if(this.status >= 400)
			throw new NntpException("Error while performing command on server : " + response);
	}

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}

}
