package org.ambiance.nntpclient.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.ambiance.nntpclient.NntpException;
import org.ambiance.nntpclient.model.Newsgroup;

public class NntpGroupCommandNext extends NntpGroupCommand {
	private static final String NAME = "NEXT";

	public NntpGroupCommandNext(Newsgroup group) {
		super(group, NntpGroupCommandNext.NAME, "");
	}

	@Override
	public Object getResponseBodyAsObject() throws NntpException {
		StringBuffer sb = new StringBuffer();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
				getResponseBodyAsStream()));
		
		String line = null;
		
		try {
			
			line = br.readLine().trim();
			
			while (null != line && !END_RESPONSE_BODY.equals(line)) {
				sb.append(line+"\r\n");
				line = br.readLine();
			}
			
		} catch (IOException ioe) {
			throw new NntpException(ioe);
		}
		
		return sb;
	}

}
