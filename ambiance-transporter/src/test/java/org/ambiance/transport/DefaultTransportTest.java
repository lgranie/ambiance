package org.ambiance.transport;

import java.io.File;

import org.codehaus.plexus.PlexusTestCase;
import org.codehaus.plexus.util.FileUtils;

public class DefaultTransportTest extends PlexusTestCase {

	public void testLookup() {
		Exception e = null;
		Transporter transport = null;
		try {
			transport = (Transporter) lookup(
					"org.ambiance.transport.Transporter", "default");
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			e = e1;
		}
		assertNull(e);
		assertNotNull(transport);
	}

	public void testHttpWagon() {
		
		Exception e = null;
		File tmp = null;
		
		try {
			
			Transporter transporter = (Transporter) lookup(
					"org.ambiance.transport.Transporter", "default");
			
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
		// LGE - Wagon does not support url with \\ as in win32
		String tmpdir = System.getProperty("java.io.tmpdir").replaceAll("\\\\", "/");
		
		try {
			
			if (FileUtils.fileExists(tmpdir+"test1.html")) {
				FileUtils.forceDelete(tmpdir+"test1.html");
			}
			
			Transporter transporter = (Transporter) lookup(
					"org.ambiance.transport.Transporter", "default");
			
			tmp = FileUtils.createTempFile("ambiance-transporter-", ".test", null);
			
			transporter.get("http://laurent.granie.free.fr/", tmp);
			
			transporter.put(tmp, "file://"+tmpdir+"test1.html");
			
		} catch (Exception e1) {
			
			e = e1;
			
		}
		
		assertNull(e);
		assertNotNull(tmp);
		assertTrue(FileUtils.fileExists(tmpdir+"test1.html"));
	}
	
	
}
