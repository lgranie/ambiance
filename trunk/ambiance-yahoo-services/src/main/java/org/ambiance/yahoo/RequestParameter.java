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
package org.ambiance.yahoo;

public class RequestParameter {

	protected static String ANY = "any";
	
	/**
	 * The kind of search to submit: 
	 * all returns results with all query terms. 
	 * any returns results with one or more of the query terms. 
	 * phrase returns results containing the query terms as a phrase.
	 */
	private String type = "all";

	/**
	 * The number of results to return. integer: default 10, max 100
	 */
	private int results = 10;

	/**
	 * The starting result position to return (1-based). The finishing position (start + results - 1) cannot exceed
	 * 1000. integer: default 1
	 */
	private int start = 1;

	public RequestParameter() {}
	
	public RequestParameter(int results, int start, String type) {
		this.results = results;
		this.start = start;
		this.type = type;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if (results != 10 && results <= 100) 
			sb.append("&").append("results=").append(results);
		
		if (start != 1 && results + start <= 1000)
			sb.append("&").append("start=").append(start);
		
		if (!"all".equals(type)) 
			sb.append("&").append("type=").append(type);
				
		return sb.toString();
	}
	
}
