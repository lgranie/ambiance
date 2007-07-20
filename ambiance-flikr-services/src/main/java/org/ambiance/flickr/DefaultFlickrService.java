package org.ambiance.flickr;

import org.ambiance.transport.Transporter;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

/**
 * @plexus.component role="org.ambiance.flickr.AmbianceFlickrService" role-hint="default"
 */
public abstract class DefaultFlickrService extends AbstractLogEnabled implements AmbianceFlickrService, Initializable {
	
	/**
	 * @plexus.requirement role="org.ambiance.transport.Transporter" role-hint="default"
	 */
	protected Transporter transporter;

	/**
	 * @plexus.configuration default-value="The Flickr Appid"
	 */
	protected String flickrId;

	public void initialize() throws InitializationException {
	
	}
	
}
