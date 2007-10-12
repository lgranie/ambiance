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
package org.ambiance.yahoo.image;

import org.ambiance.yahoo.RequestParameter;

public class ImageRequestParameter extends RequestParameter {

	/**
	 * Specifies the kind of image file to search for. 
	 * any (default), bmp, gif, jpeg, png
	 */
	private String format = "any";

	/**
	 * Specifies whether to allow results with adult content. Enter a 1 to allow adult content. 
	 * no value (default), or 1
	 */
	private short adult_ok = 0;

	/**
	 * The service returns only the images of the coloration specified (color or black-and-white). 
	 * any (default), color, bw
	 */
	private String coloration = "any";

	/**
	 * A domain to restrict your searches to (e.g. www.yahoo.com). 
	 * You may submit up to 30 values
	 * (site=www.yahoo.com&site=www.cnn.com).
	 */
	String[] site = null;

	public ImageRequestParameter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImageRequestParameter(int results, int start, String type, String format, short adult_ok, String coloration, String[] site) {
		super(results, start, type);
		
		if (format != null)
			this.format = format;
		
		if (adult_ok != 0)
			this.adult_ok = 1;
		
		if (coloration != null)
			this.coloration = coloration;
		
		if (site != null && site.length > 0)
			this.site = site;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());

		if( !ANY.equals(format))
			sb.append("&").append("format=").append(format);
		
		if( 0 != adult_ok)
			sb.append("&").append("adult_ok=").append(1);
		
		if( !ANY.equals(coloration))
			sb.append("&").append("coloration=").append(coloration);
		
		if( site != null && site.length > 0 ) {
			for (String s : site) {
				sb.append("&").append("site=").append(s);
			}
		}
		
		return sb.toString();
	}
}
