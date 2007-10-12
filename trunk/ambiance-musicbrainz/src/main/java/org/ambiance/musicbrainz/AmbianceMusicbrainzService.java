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
package org.ambiance.musicbrainz;

import org.musicbrainz.JMBWSException;
import org.musicbrainz.model.Artist;
import org.musicbrainz.model.Release;
import org.musicbrainz.model.Track;
import org.musicbrainz.webservice.filter.ArtistFilter;
import org.musicbrainz.webservice.filter.ReleaseFilter;
import org.musicbrainz.webservice.filter.TrackFilter;
import org.musicbrainz.webservice.includes.ArtistIncludes;
import org.musicbrainz.webservice.includes.ReleaseIncludes;
import org.musicbrainz.webservice.includes.TrackIncludes;
import org.musicbrainz.wsxml.element.ArtistSearchResults;
import org.musicbrainz.wsxml.element.ReleaseSearchResults;
import org.musicbrainz.wsxml.element.TrackSearchResults;


public interface AmbianceMusicbrainzService {

	/** The Plexus role identifier. */
	String ROLE = AmbianceMusicbrainzService.class.getName();

	public Artist getArtistById(String id, ArtistIncludes includes) throws JMBWSException;

	public ArtistSearchResults getArtists(ArtistFilter filter) throws JMBWSException;

	public Release getReleaseById(String id, ReleaseIncludes include) throws JMBWSException;

	public ReleaseSearchResults getReleases(ReleaseFilter filter) throws JMBWSException;

	public Track getTrackById(String id, TrackIncludes include) throws JMBWSException;

	public TrackSearchResults getTracks(TrackFilter filter) throws JMBWSException;
}
