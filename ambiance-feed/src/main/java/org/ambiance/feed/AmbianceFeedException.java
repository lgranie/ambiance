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
package org.ambiance.feed;

public class AmbianceFeedException extends Exception {

	private static final long serialVersionUID = -1067792145932228006L;

	public AmbianceFeedException() {
		super();
	}

	public AmbianceFeedException(String arg0) {
		super(arg0);
	}

	public AmbianceFeedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AmbianceFeedException(Throwable arg0) {
		super(arg0);
	}

}
