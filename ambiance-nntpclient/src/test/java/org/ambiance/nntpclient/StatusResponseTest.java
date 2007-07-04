package org.ambiance.nntpclient;

import junit.framework.TestCase;

import org.ambiance.nntpclient.NntpException;
import org.ambiance.nntpclient.model.StatusResponse;

public class StatusResponseTest extends TestCase {

	public void testNotMatchNOK() {
		Exception e = null;
		try {
			new StatusResponse("tralala");
		} catch (NntpException e1) {
			e = e1;
		}
		assertNotNull(e);
	}
	
	public void testErrorDuringCmdNOK() {
		Exception e = null;
		try {
			new StatusResponse("402 tralala\r\n");
		} catch (NntpException e1) {
			e = e1;
		}
		assertNotNull(e);
	}
}
