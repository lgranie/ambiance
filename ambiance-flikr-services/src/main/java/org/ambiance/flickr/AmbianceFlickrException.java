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
