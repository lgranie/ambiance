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
package org.musicbrainz.webservice.impl;

import java.io.InputStream;

import org.ambiance.transport.Transporter;
import org.musicbrainz.webservice.DefaultWebService;
import org.musicbrainz.webservice.WebServiceException;
import org.musicbrainz.wsxml.element.Metadata;

/**
 * A simple http client using Ambiance Transporter.
 * 
 * @author Laurent GRANIE
 */
public class TransporterWebService extends DefaultWebService 
{
	//private Log log = LogFactory.getLog(TransporterWebService.class);
	
	/**
	 * A {@link HttpClient} instance
	 */
	Transporter transporter;

	/**
	 * Default constructor
	 *
	 */
	public TransporterWebService(Transporter transporter) 
	{
		this.transporter = transporter;	
	}

	/* (non-Javadoc)
	 * @see org.musicbrainz.webservice.DefaultWebService#doGet(java.lang.String)
	 */
	@Override
	protected Metadata doGet(String url) throws WebServiceException {		
		try {
			return getParser().parse(transporter.getAsStream(url));
		} catch (Exception e) {
			throw new WebServiceException(e);
		}
	}

	@Override
	protected void doPost(String arg0, InputStream arg1) throws WebServiceException {
		throw new WebServiceException("Not implemented");
	}

}
