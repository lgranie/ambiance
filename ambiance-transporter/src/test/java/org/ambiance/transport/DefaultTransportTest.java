package org.ambiance.transport;

import java.io.File;
import java.io.InputStream;
import java.net.URLDecoder;

import org.codehaus.plexus.PlexusTestCase;
import org.codehaus.plexus.util.FileUtils;

public class DefaultTransportTest extends PlexusTestCase {
	
	private Transporter transporter = null;
	
	private String tmpdir;
	
	public void setUp() {
		Exception e = null;
	    try {        
			super.setUp();
			
			transporter = (Transporter)  lookup("org.ambiance.transport.Transporter", "default");
			
			//	 LGE - Wagon does not support url with \\ as in win32
			tmpdir = System.getProperty("java.io.tmpdir").replaceAll("\\\\", "/");
			
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}    
		assertNull(e);
		assertNotNull(transporter);    
	}

	public void testHttpWagon() {
		
		Exception e = null;
		File tmp = null;
		
		try {			
			tmp = FileUtils.createTempFile("ambiance-transporter-", ".test", null);
			FileUtils.forceDelete(tmp);
			
			transporter.get("http://laurent.granie.free.fr/", tmp);
			
		} catch (Exception e1) {
			
			e = e1;
			
		}
		
		assertNull(e);
		assertNotNull(tmp);
		
	}
	
	public void testFileWagon() {
		
		Exception e = null;
		File tmp = null;
		
		try {
			
			if (FileUtils.fileExists(tmpdir+"test 2.html")) {
				FileUtils.forceDelete(tmpdir+"test 2.html");
			}
			
			tmp = FileUtils.createTempFile("ambiance-transporter-", ".test", null);
			
			transporter.get("http://laurent.granie.free.fr/", tmp);
			
			transporter.put(tmp, "file://"+tmpdir+"test 2.html");
			
			transporter.get("file://"+tmpdir+"test 2.html", tmp);
			
		} catch (Exception e1) {
			
			e = e1;
			
		}
		
		assertNull(e);
		assertNotNull(tmp);
		assertTrue(FileUtils.fileExists(tmpdir+"test 2.html"));
	}
	
	public void testFileWagonAsStream() {
		
		Exception e = null;
		InputStream is = null;
		int read = 0;
		try {
			String url = URLDecoder.decode(this.getClass().getResource("/avec espace.txt").toString(), "UTF-8");
			is = transporter.getAsStream(url);
			while(is.read() != -1) {
				read++;
			}
		} catch (Exception e1) {
			e = e1;
		}
		
		assertNull(e);
		assertNotNull(is);
		assertEquals(read, 34);
	}
	
	
}
