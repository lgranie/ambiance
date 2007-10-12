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
package org.ambiance.nntpclient.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ambiance.nntpclient.NntpException;

public class Newsgroup {
	private static final Pattern PATTERN = Pattern
			.compile("^(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(.+)\\s*$");

	private String name;

	private int first;

	private int last;

	private boolean isPostingAllowed;

	private List<Thread> threads;

	public Newsgroup(String name) {
		this(name, 0, 0, false);
	}
	
	public Newsgroup(String name, int first, int last, boolean isPostingAllowed) {
		super();
		this.name = name;
		this.first = first;
		this.last = last;
		this.isPostingAllowed = isPostingAllowed;

		this.threads = new ArrayList<Thread>();
	}

	public void update(String message) throws NntpException {
		Matcher m = Newsgroup.PATTERN.matcher(message);

		if (!m.matches() || m.groupCount() != 4)
			throw new NntpException("GROUP command failed : " + message);
		
		if(null == this.name)
			this.name = m.group(4);
		else if(!m.group(4).equals(name))
			throw new NntpException("Not updating the good group : " + name + " -> " + m.group(4));
		
		this.first = Integer.parseInt(m.group(2));
		this.last = Integer.parseInt(m.group(3));
	}

	public List<Thread> getThreads() {
		return threads;
	}

	public void setThreads(List<Thread> threads) {
		this.threads = threads;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPostingAllowed() {
		return this.isPostingAllowed;
	}

}
