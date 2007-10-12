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
package org.ambiance.yahoo.web;

import java.util.Locale;

import org.ambiance.yahoo.RequestParameter;

public class WebRequestParameter extends RequestParameter {
		
	/**
	 * Coupling country and language The country in which to restrict your search results. Only results on web sites
	 * within this country are returned. <a href="http://developer.yahoo.com/search/countries.html">Supported Country
	 * Codes</a>. string: default no value 
	 * 
	 * The language the results are written in. < a href="http://developer.yahoo.com/search/languages.html"> Supported Languages</a>. string:
	 * default no value (all languages)
	 */
	private Locale locale = null;

	/**
	 * Specifies the kind of file to search for. 
	 * any (default), html, msword, pdf, ppt, rss, txt, xls
	 */
	private String format = "any";

	/**
	 * Specifies whether to allow results with adult content. 
	 * Enter a 1 to allow adult content. no value (default), or 1
	 */
	private short adult_ok = 0;

	/**
	 * Specifies whether to allow multiple results with similar content. Enter a 1 to allow similar content. no value
	 * (default), or 1
	 */
	private short similar_ok = 0;

	/**
	 * A domain to restrict your searches to (e.g. www.yahoo.com). You may submit up to 30 values
	 * (site=www.yahoo.com&site=www.cnn.com).
	 */
	String[] site = null;

	/**
	 * Any <a href="http://search.yahoo.com/subscription/learnmore">subscriptions</a> to premium content that should
	 * also be searched. You may submit multiple values. <a
	 * href="http://developer.yahoo.com/search/subscriptions.html">Supported Subscription Codes</a>. string: default no
	 * value
	 */
	private String[] subscription = null;

	/**
	 * The Creative Commons license that the contents are licensed under. You may submit multiple values (e.g.
	 * license=cc_commercial&license=cc_modifiab any (default), cc_any, cc_commercial, cc_modifiable
	 */
	private String[] license = new String[] {"any"};

	public WebRequestParameter() {
		super();
	}
	
	public WebRequestParameter(short adult_ok, String format, String[] license, Locale locale, int results, short similar_ok, String[] site, int start, String[] subscription, String type) {
		super(results, start, type);
		
		if (adult_ok != 0)
			this.adult_ok = 1;
		
		if (format != null)
			this.format = format;
		
		if (license != null && license.length > 0)
			this.license = license;
		
		if (locale != null)
			this.locale = locale;
		
		if (similar_ok != 0)
			this.similar_ok = 1;
		
		if (site != null && site.length > 0)
			this.site = site;
		
		if (subscription != null && subscription.length > 0)
			this.subscription = subscription;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		
		if( 0 != adult_ok)
			sb.append("&").append("adult_ok=").append(1);
		
		if( !ANY.equals(format))
			sb.append("&").append("format=").append(format);
		
		if( license.length != 1 && !license[0].equals(ANY)) {
			for (String l : license) {
				sb.append("&").append("license=").append(l);
			}
		}
		
		if (null != locale) {
			sb.append("&").append("country=").append(locale.getCountry().toLowerCase());
			sb.append("&").append("language=").append(locale.getLanguage().toLowerCase());
		}
				
		if (similar_ok != 0)
			sb.append("&").append("similar_ok=").append(1);
		
		if( site != null && site.length > 0 ) {
			for (String s : site) {
				sb.append("&").append("site=").append(s);
			}
		}
		
		if (null != subscription) {
			for (String s : subscription) {
				sb.append("&").append("subscription=").append(s);
			}
		}
			
		return sb.toString();
	}

	public short getAdult_ok() {
		return adult_ok;
	}

	public void setAdult_ok(short adult_ok) {
		this.adult_ok = adult_ok;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String[] getLicense() {
		return license;
	}

	public void setLicense(String[] license) {
		this.license = license;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public short getSimilar_ok() {
		return similar_ok;
	}

	public void setSimilar_ok(short similar_ok) {
		this.similar_ok = similar_ok;
	}

	public String[] getSite() {
		return site;
	}

	public void setSite(String[] site) {
		this.site = site;
	}

	public String[] getSubscription() {
		return subscription;
	}

	public void setSubscription(String[] subscription) {
		this.subscription = subscription;
	}
	
}
