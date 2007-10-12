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
package org.ambiance.yahoo;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.ambiance.transport.Transporter;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

public abstract class QueryYahooService extends AbstractLogEnabled implements AmbianceYahooService, Initializable {
	
	/**
	 * @plexus.requirement role="org.ambiance.transport.Transporter" role-hint="default"
	 */
	protected Transporter transporter;

	/**
	 * @plexus.configuration default-value="The Yahoo Appid"
	 */
	protected String yahooId;

	
	protected Unmarshaller u;

	public void initialize() throws InitializationException {
		try {
			
			u = JAXBContext.newInstance(this.getClass().getPackage().getName()).createUnmarshaller();
			
		} catch (JAXBException je) {

			throw new InitializationException("Unable to initialize unmarshaller", je);
			
		}
	}
	
	/**
	 * Quering Yahoo with default parameters values
	 * @param query the query to submit to yahoo
	 * @return
	 * @throws AmbianceYahooException
	 */
	public abstract Object query(String query) throws AmbianceYahooException;

	
}
