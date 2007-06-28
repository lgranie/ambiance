package org.ambiance.musicbrainz;

import org.ambiance.transport.Transporter;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.musicbrainz.JMBWSException;
import org.musicbrainz.Query;
import org.musicbrainz.model.Artist;
import org.musicbrainz.model.Release;
import org.musicbrainz.model.Track;
import org.musicbrainz.webservice.filter.ArtistFilter;
import org.musicbrainz.webservice.filter.ReleaseFilter;
import org.musicbrainz.webservice.filter.TrackFilter;
import org.musicbrainz.webservice.impl.TransporterWebService;
import org.musicbrainz.webservice.includes.ArtistIncludes;
import org.musicbrainz.webservice.includes.ReleaseIncludes;
import org.musicbrainz.webservice.includes.TrackIncludes;
import org.musicbrainz.wsxml.element.ArtistSearchResults;
import org.musicbrainz.wsxml.element.ReleaseSearchResults;
import org.musicbrainz.wsxml.element.TrackSearchResults;

/**
 * @plexus.component role="org.ambiance.musicbrainz.DefaultMusicBrainzService" role-hint="default"
 */
public abstract class DefaultMusicBrainzService extends AmbianceMusicbrainzService implements Initializable {

	/**
	 * @plexus.requirement role="org.ambiance.transport.Transporter" role-hint="default"
	 */
	protected Transporter transporter;
	
	/**
	 * A default {@link Query}
	 */
	private Query q;

	public void initialize() throws InitializationException {
		
		q = new Query(new TransporterWebService(transporter));
		
	}

	public Artist getArtistById(String id, ArtistIncludes includes) throws JMBWSException {
		return q.getArtistById(id, includes);
	}

	public ArtistSearchResults getArtists(ArtistFilter filter) throws JMBWSException {
		return q.getArtists(filter);
	}

	public Release getReleaseById(String id, ReleaseIncludes include) throws JMBWSException {
		return q.getReleaseById(id, include);
	}

	public ReleaseSearchResults getReleases(ReleaseFilter filter) throws JMBWSException {
		return q.getReleases(filter);
	}

	public Track getTrackById(String id, TrackIncludes include) throws JMBWSException {
		return q.getTrackById(id, include);
	}

	public TrackSearchResults getTracks(TrackFilter filter) throws JMBWSException {
		return q.getTracks(filter);
	}

}
