package org.ambiance.musicbrainz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.plexus.PlexusTestCase;
import org.musicbrainz.JMBWSException;
import org.musicbrainz.model.Artist;
import org.musicbrainz.model.Disc;
import org.musicbrainz.model.Release;
import org.musicbrainz.model.ReleaseEvent;
import org.musicbrainz.model.Track;
import org.musicbrainz.utils.MbUtils;
import org.musicbrainz.webservice.filter.ArtistFilter;
import org.musicbrainz.webservice.filter.ReleaseFilter;
import org.musicbrainz.webservice.filter.TrackFilter;
import org.musicbrainz.webservice.includes.ArtistIncludes;
import org.musicbrainz.webservice.includes.ReleaseIncludes;
import org.musicbrainz.webservice.includes.TrackIncludes;
import org.musicbrainz.wsxml.element.ArtistResult;
import org.musicbrainz.wsxml.element.ArtistSearchResults;
import org.musicbrainz.wsxml.element.ReleaseResult;
import org.musicbrainz.wsxml.element.ReleaseSearchResults;
import org.musicbrainz.wsxml.element.TrackResult;
import org.musicbrainz.wsxml.element.TrackSearchResults;

/**
 * Integration tests using the {@link DefaultMusicbrainzService} class.
 * 
 * @author Laurent GRANIE
 * @see DefaultMusicbrainzService
 */
public class DefaultMusicbrainzServiceTest extends PlexusTestCase {
	private Log log = LogFactory.getLog(DefaultMusicbrainzServiceTest.class);
		
	/**
	 * The default {@link DefaultMusicbrainzService}
	 */
	private AmbianceMusicbrainzService s;
	
	public void setUp() {
	    try {        
			super.setUp();
			s = (DefaultMusicbrainzService)  lookup("org.ambiance.musicbrainz.AmbianceMusicbrainzService", "default");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
	}

	/**
	 * Search for an artist by name.
	 * @throws JMBWSException
	 */
	public void testFindArtist() throws JMBWSException {
		// Search for all artists matching the given name.
		ArtistFilter f = new ArtistFilter("Doors Down");
		
		// Limit the results to the 5 best matches.
		f.setLimit(5L);
		ArtistSearchResults artistResults = s.getArtists(f);
	  
		// some tests
		assertTrue(artistResults.getArtistResults().size() > 0);
		boolean found = false;
		for (ArtistResult ar : artistResults.getArtistResults()) 
		{
			Artist artist = ar.getArtist();
			log.debug("[artist] " + artist.getId() + " - " + artist.getName() + " (" + artist.getBeginDate() + " - " + artist.getEndDate() + ")");
	  
			assertNotNull(artist.getId());
			if ("3 Doors Down".equals(artist.getName())) {
				assertNotNull(artist.getSortName());
				assertNotNull(artist.getBeginDate());
				found = true;
			}
		}
		assertTrue(found);
	}
	
	/**
	 * Retrieve an artist by ID and display all official albums.
	 * @throws JMBWSException
	 */
	public void testGetArtist() throws JMBWSException 
	{		
		// 3 doors down id
		String id = "2386cd66-e923-4e8e-bf14-2eebe2e9b973";
		  
		// The result should include all official albums.
		ArtistIncludes includes = new ArtistIncludes(new String[] { Release.TYPE_OFFICIAL, Release.TYPE_ALBUM } );
		Artist artist = s.getArtistById(id, includes);
		log.debug(artist.toString());
		
		// some tests
		assertEquals(MbUtils.convertIdToURI(id, "artist"), artist.getId());
		assertEquals("3 Doors Down", artist.getName());
		assertTrue(artist.getReleases().size() > 0);
		
		for (Release r : artist.getReleases()) {
			log.debug(r.toString());
		}
	}
	
	/**
	 * Search for an release by name.
	 * @throws JMBWSException
	 */
	public void testFindRelease() throws JMBWSException 
	{
		// Search for a specific release
		ReleaseFilter f = new ReleaseFilter();
		f.setArtistName("3 Doors Down");
		f.setTitle("Away from the Sun");
		f.setReleaseTypesStr(new String[] { Release.TYPE_OFFICIAL } );
		ReleaseSearchResults releaseResults = s.getReleases(f);
	  
		// some tests
		assertTrue(releaseResults.getReleaseResults().size() > 0);
		ReleaseResult rs = releaseResults.getReleaseResults().get(0);
		assertNotNull(rs);
		
		Release r = rs.getRelease();
		log.debug("[release] " + r.getId() + " - " + r.getArtist() + " - " + r.getTitle());
		assertNotNull(r.getId());
		assertNotNull(r.getArtist());
		assertNotNull(r.getAsin());
		assertNotNull(r.getTitle());
		assertNotNull(r.getTextLanguage());
		assertNotNull(r.getTextScript());
	}
	
	/**
	 * Retrieve an release by ID and display some releations.
	 * @throws JMBWSException
	 */
	public void testGetRelease() throws JMBWSException 
	{
		// Away from the Sun from 3 Doors Down id
		String id = "6fc2c148-58d4-4120-842c-a56d8c961ae9";
		  
		// Additionally to the release itself, we want the server to include
		// the release's artist, all release events, associated discs and
		// the track list.
		ReleaseIncludes includes = new ReleaseIncludes();
		includes.setArtist(true);
		includes.setReleaseEvents(true);
		includes.setDiscs(true);
		includes.setTracks(true);

		Release r = s.getReleaseById(id, includes);
		log.debug(r.toString());
		
		// some tests
		assertEquals(MbUtils.convertIdToURI(id, "release"), r.getId());
		assertTrue("away from the sun".equalsIgnoreCase(r.getTitle()));
		assertNotNull(r.getArtist());
		assertTrue("3 Doors Down".equals(r.getArtist().getName()));
		assertNotNull(r.getAsin());
		assertNotNull(r.getReleaseEventList());
		assertNotNull(r.getTrackList());
		assertTrue(r.getTrackList().getTracks().size() > 10);
		assertNotNull(r.getDiscList());
		assertTrue(r.getDiscList().getDiscs().size() > 0);
		
		// print releases
		log.debug("earliest release: " + r.getEarliestReleaseDate());
		for (ReleaseEvent re : r.getReleaseEventList().getReleaseEvents()) {
			log.debug(re);
		}
		
		// print discs
		for (Disc disc : r.getDiscList().getDiscs()) {
			log.debug(disc);
		}
		
		// print tracks
		for (Track t : r.getTrackList().getTracks()) {
			log.debug(t.toString());
		}
	}
	
	/**
	 * Search for a track by title (and optionally by artist name).
	 * @throws JMBWSException 
	 */
	public void testFindTrack() throws JMBWSException 
	{
		// find all tracks with a title superman
		TrackFilter tf = new TrackFilter();
		tf.setTitle("gone");
		TrackSearchResults trackResults = s.getTracks(tf);
		  
		// some tests
		assertTrue(trackResults.getTrackResults().size() > 0);
		for (TrackResult tr : trackResults.getTrackResults()) 
		{
			Track t = tr.getTrack();
			log.debug(t);
			assertNotNull(t.getId());
			assertNotNull(t.getDuration());
			assertNotNull(t.getTitle());
		}
		
		// now limit results to artist
		tf.setArtistName("3 Doors Down");
		
		trackResults = s.getTracks(tf);
		  
		// some tests
		assertTrue(trackResults.getTrackResults().size() > 0);
		for (TrackResult tr : trackResults.getTrackResults()) 
		{
			Track t = tr.getTrack();
			log.debug(t);
			assertNotNull(t.getId());
			assertNotNull(t.getTitle());
		}
		
	}
	
	/**
	 * Retrieve a tack by ID.
	 * @throws JMBWSException
	 */
	public void testGetTrack() throws JMBWSException 
	{
		// "Here Without You" by 3 Doors Down
		String id = "d3f1cd62-ae04-46d0-ba11-ec1970f890a0";
		  
		TrackIncludes includes = new TrackIncludes();
		includes.setArtist(true);
		
		Track t = s.getTrackById(id, includes);
		log.debug(t.toString());
		
		// some tests
		assertEquals(MbUtils.convertIdToURI(id, "track"), t.getId());
		assertTrue("here without you".equalsIgnoreCase(t.getTitle()));		
		assertNotNull(t.getDuration());
		assertTrue("3 doors down".equalsIgnoreCase(t.getArtist().getName()));		
	}

}
