package org.ambiance.flickr;


public class AmbianceFlickrException extends Exception {

	private static final long serialVersionUID = 1763540623355173074L;

	public AmbianceFlickrException() {
		super();
	}

	public AmbianceFlickrException(String msg, Throwable t) {
		super(msg, t);
	}

	public AmbianceFlickrException(String msg) {
		super(msg);
	}

	public AmbianceFlickrException(Throwable t) {
		super(t);
	}	

}
