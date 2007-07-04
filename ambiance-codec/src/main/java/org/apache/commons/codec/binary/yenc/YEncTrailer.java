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

public class YEncTrailer {

	/*
	 * Size of the original unencode binary (in bytes)
	 */
	private long size = -1;

	/*
	 * The part number
	 */
	private int part = -1;

	/*
	 * The part 32-bit Cyclic Redundancy Check (CRC)
	 */
	private String pcrc32 = null;

	/*
	 * 32-bit Cyclic Redundancy Check (CRC)
	 */
	private String crc32 = null;

	public YEncTrailer() {

	}

	public YEncTrailer(String line) throws YEncException {
		Pattern p = Pattern
				.compile("^=yend\\s*size=(\\d+)(\\s*part=(\\d+))?(\\s*pcrc32=(.+))?(\\s*crc32=(.+))?(\r\n)?");
		Matcher m = p.matcher(line);
		if (m.matches()) {
			this.size = Integer.parseInt(m.group(1));
			if (null != m.group(3))
				this.part = Integer.parseInt(m.group(3));
			if (null != m.group(5))
				this.pcrc32 = m.group(5).toUpperCase();
			if (null != m.group(7))
				this.crc32 = m.group(7).toUpperCase();
		} else {
			throw new YEncException("error in trailer");
		}
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public int getPart() {
		return part;
	}

	public void setPart(int part) {
		this.part = part;
	}

	public String getPcrc32() {
		return pcrc32;
	}

	public void setPcrc32(String pcrc32) {
		this.pcrc32 = pcrc32;
	}

	public String getCrc32() {
		return crc32;
	}

	public void setCrc32(String crc32) {
		this.crc32 = crc32;
	}

}
