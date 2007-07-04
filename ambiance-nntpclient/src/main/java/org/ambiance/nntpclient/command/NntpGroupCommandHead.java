package org.ambiance.nntpclient.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.ambiance.nntpclient.NntpException;
import org.ambiance.nntpclient.model.Newsgroup;

public class NntpGroupCommandHead extends NntpGroupCommand {
	private static final String NAME = "HEAD";

	public NntpGroupCommandHead(Newsgroup group, String arg) {
		super(group, NntpGroupCommandHead.NAME, "<"+arg+">");
	}

	public NntpGroupCommandHead(Newsgroup group) {
		super(group, NntpGroupCommandHead.NAME, "");
	}

	@Override
	public Object getResponseBodyAsObject() throws NntpException {
		
		Map<String, String> headers = new HashMap<String, String>();

		BufferedReader br = new BufferedReader(new InputStreamReader(getResponseBodyAsStream()));
		
		String line = null;
		try {
			line = br.readLine().trim();
			
			int index = -1;
			String key = null;
			String value = null;
		
			while (null != line && !END_RESPONSE_BODY.equals(line)) {
				// Parsing line
				index = line.indexOf(":");
				if (index < 0) {
					// Multi-line value
					value = value+line;
				} else {
					key = line.substring(0, index);
					value = line.substring(index + 1).trim();
				}
				
				// Save value as header
				headers.put(key, value);

				// Read next line
				line = br.readLine();
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		return headers;
	}
}
