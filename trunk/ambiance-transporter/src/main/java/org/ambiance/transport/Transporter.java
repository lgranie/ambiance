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
package org.ambiance.transport;

import java.io.File;
import java.io.InputStream;

public interface Transporter {

	/** The Plexus role identifier. */
	String ROLE = Transporter.class.getName();

	/**
	 * 
	 * @param url
	 *            The url where to get data from
	 * @param file
	 *            The file to save date to
	 * @throws TransporterException
	 */
	public void get(String url, File file) throws TransporterException;

	public InputStream getAsStream(String url) throws TransporterException;
	
	public InputStream getAsStream(String url, int bufferSize) throws TransporterException;
	
	/**
	 * 
	 * @param file
	 *            The file containing data to put
	 * @param url
	 *            The url where to put data to
	 * @throws TransporterException
	 */
	public void put(File file, String url) throws TransporterException;

}
