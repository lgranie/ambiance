package org.ambiance.transport;

import java.io.File;
import java.io.InputStream;
import java.net.URLDecoder;

import org.codehaus.plexus.PlexusTestCase;
import org.codehaus.plexus.util.FileUtils;

public class DefaultTransportFileTest extends PlexusTestCase {
	
	private Transporter transporter = null;
	
	public void setUp() {
		Exception e = null;
	    try {        
			super.setUp();
			
			transporter = (Transporter)  lookup("org.ambiance.transport.Transporter", "default");
			
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}    
		assertNull(e);
		assertNotNull(transporter);    
	}
	
	public void testFileWithSpace() {
		
		Exception e = null;
		File tmp = null;
		
		try {
			tmp = FileUtils.createTempFile("ambiance-transporter-", ".test", null);
			transporter.get(URLDecoder.decode(this.getClass().getResource("/name with space.txt").toString(), "UTF-8"), tmp);
		} catch (Exception e1) {
			e = e1;
			e1.printStackTrace();
		}
		
		assertNull(e);
		assertNotNull(tmp);
	}
	
	public void testFileWithoutSpace() {
		
		Exception e = null;
		File tmp = null;
		
		try {
			tmp = FileUtils.createTempFile("ambiance-transporter-", ".test", null);
			transporter.get(this.getClass().getResource("/name_without_space.txt").toString(), tmp);
		} catch (Exception e1) {
			e = e1;
			e1.printStackTrace();
		}
		
		assertNull(e);
		assertNotNull(tmp);
	}
	
	public void testFileWithSpaceAsStream() {
		
		Exception e = null;
		InputStream is = null;
		int read = 0;
		try {
			String url = URLDecoder.decode(this.getClass().getResource("/name with space.txt").toString(), "UTF-8");
			is = transporter.getAsStream(url);
			while(is.read() != -1) {
				read++;
			}
		} catch (Exception e1) {
			e = e1;
			e1.printStackTrace();
		}
		
		assertNull(e);
		assertNotNull(is);
		assertEquals(read, 32);
	}
	
	public void testFileWithoutSpaceAsStream() {
		
		Exception e = null;
		InputStream is = null;
		int read = 0;
		try {
			String url = URLDecoder.decode(this.getClass().getResource("/name_without_space.txt").toString(), "UTF-8");
			is = transporter.getAsStream(url);
			while(is.read() != -1) {
				read++;
			}
		} catch (Exception e1) {
			e = e1;
			e1.printStackTrace();
		}
		
		assertNull(e);
		assertNotNull(is);
		assertEquals(read, 38);
	}
	
	
}
