package org.ambiance.musicbrainz;

public class AmbianceMusicbrainzException extends Exception {

	private static final long serialVersionUID = -4189074269629694633L;

	public AmbianceMusicbrainzException() {
		super();
	}

	public AmbianceMusicbrainzException(String msg) {
		super(msg);
	}

	public AmbianceMusicbrainzException(String msg, Throwable t) {
		super(msg, t);
	}

	public AmbianceMusicbrainzException(Throwable t) {
		super(t);
	}

}
