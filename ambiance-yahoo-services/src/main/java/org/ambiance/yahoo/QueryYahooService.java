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
