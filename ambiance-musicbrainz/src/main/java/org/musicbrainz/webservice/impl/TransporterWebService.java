package org.musicbrainz.webservice.impl;

import java.io.InputStream;

import org.ambiance.transport.Transporter;
import org.ambiance.transport.TransporterException;
import org.musicbrainz.webservice.DefaultWebService;
import org.musicbrainz.webservice.WebServiceException;

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
	protected InputStream doGet(String url) throws WebServiceException {		
		try {
			return transporter.getAsStream(url);
		} catch (TransporterException e) {
			throw new WebServiceException(e);
		}
	}

	@Override
	protected void doPost(String arg0, InputStream arg1) throws WebServiceException {
		throw new WebServiceException("Not implemented");
	}

}
