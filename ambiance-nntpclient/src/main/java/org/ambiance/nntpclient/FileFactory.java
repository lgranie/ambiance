/**
 * Copyright (C) 2007 Laurent GRANIE.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 3, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.ambiance.nntpclient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.ambiance.nntpclient.nzb.File;
import org.ambiance.nntpclient.nzb.Group;
import org.ambiance.nntpclient.nzb.Groups;
import org.ambiance.nntpclient.nzb.Nzb;
import org.ambiance.nntpclient.nzb.Segments;
import org.codehaus.plexus.util.FileUtils;

public class FileFactory {

	Map<String, File> cache;
	
	public FileFactory() {
		cache = new HashMap();
	}

	public File getFile(Map<String, String> headers, String filename) {
		
		File file = cache.get(filename);
		
		if (file == null) {
			file = new File();
			file.setDate(headers.get("Date"));
			
			Groups groups = new Groups();
			String[] groupsNames = headers.get("Newsgroups").split(",");
			for (String groupname : groupsNames) {
				Group group = new Group();
				group.setvalue(groupname);
				groups.getGroup().add(group);
			}
			
			file.setSubject(filename);
			
			file.setGroups(groups);
			
			file.setPoster(headers.get("From"));
			
			file.setSegments(new Segments());
			
			cache.put(filename, file);
		}
		
		return file;
	}

	public void dump(String filename) {
		File file = cache.remove(filename);
		
		Nzb nzb = new Nzb();
		//nzb.setXmlns("http://www.newzbin.com/DTD/2003/nzb");
		
		nzb.getFile().add(file);
		
		try {
			Marshaller m = JAXBContext.newInstance("org.ambiance.nntpclient.nzb").createMarshaller();
			m.marshal(file, new FileOutputStream(FileUtils.createTempFile(filename, ".nzb", null)));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
