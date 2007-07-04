/*
 * Copyright (C) 2006  Laurent GRANIE
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * laurent.granie@gmail.com
 */
package org.apache.commons.codec.binary.yenc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YEncHeader {

	public static Pattern HEADER_PATTERN = Pattern
			.compile("^=ybegin\\s*(part=(\\d+))?(\\s+total=\\d+)?\\s+line=(\\d+)\\s*size=(\\d+)\\s*name=(.+)");

	/*
	 * The part number
	 */
	private int part = -1;

	/*
	 * Typical size length
	 */
	private int line = -1;

	/*
	 * Size of the original unencode binary (in bytes)
	 */
	private long size;

	/*
	 * Name of the original binary file
	 */
	private String name;

	public YEncHeader(String line) throws YEncException {
		Matcher m = HEADER_PATTERN.matcher(line);

		if (m.matches()) {
			if (null != m.group(2))
				this.part = Integer.parseInt(m.group(2));
			this.line = Integer.parseInt(m.group(4));
			this.size = Integer.parseInt(m.group(5));
			this.name = m.group(6);
		} else {
			throw new YEncException("error in header");
		}
	}

	public YEncHeader() {
	}

	public int getPart() {
		return part;
	}

	public void setPart(int part) {
		this.part = part;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("=ybegin");
		if (this.part != -1) {
			sb.append(" part=").append(part);
		}
		sb.append(" line=").append(line).append(" size=").append(size).append(
				" name=").append(name);
		return sb.toString();
	}

}
