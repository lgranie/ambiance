/*
   Copyright (C) 2007 Laurent GRANIE.

   This program is free software; you can redistribute it and/or modify it
   under the terms of the GNU General Public License as published by the
   Free Software Foundation; either version 3, or (at your option) any later
   version.

   This program is distributed in the hope that it will be useful, but
   WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General
   Public License for more details.

   You should have received a copy of the GNU General Public License along
   with this program; if not, write to the Free Software Foundation, Inc.,
   51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.apache.commons.codec.binary.yenc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class YEncFile implements Comparable {

	private YEncHeader header;

	private YEncTrailer trailer;

	private long dataBegin, dataEnd;

	private RandomAccessFile input;

	public YEncFile(File file) throws YEncException {
		try {
			input = new RandomAccessFile(file, "r");
			long pos = 0;
			int c;

			// LGR - Get Header from first line
			input.seek(pos);
			StringBuffer line = new StringBuffer();
			while ((c = input.read()) != -1) {
				input.seek(++pos);
				if ('\r' == c)
					continue;
				if ('\n' == c)
					break;
				line.append((char) c);
			}
			header = new YEncHeader(line.toString());
			dataBegin = pos;

			line = new StringBuffer();
			while ((c = this.input.read()) != -1) {
				input.seek(++pos);
				if ('\r' == c)
					continue;
				if ('\n' == c)
					break;
				line.append((char) c);
			}
			if (line.toString().startsWith("=ypart"))
				dataBegin = pos;

			// LGR - Get trailer from last line
			pos = input.length() - 2;
			input.seek(pos);
			line = new StringBuffer();
			while ((c = input.read()) != -1) {
				input.seek(--pos);
				if ('\r' == c)
					continue;
				if ('\n' == c)
					break;
				line.insert(0, (char) c);
			}
			trailer = new YEncTrailer(line.toString());
			dataEnd = pos;

			if (header.getSize() != trailer.getSize())
				throw new YEncException("error in size");
		} catch (FileNotFoundException fnfe) {
			throw new YEncException(fnfe);
		} catch (IOException ioe) {
			throw new YEncException(ioe);
		}
	}

	public long getDataBegin() {
		return dataBegin;
	}

	public long getDataEnd() {
		return dataEnd;
	}

	public YEncHeader getHeader() {
		return header;
	}

	public YEncTrailer getTrailer() {
		return trailer;
	}

	public RandomAccessFile getInput() {
		return input;
	}

	public int compareTo(Object obj) {
		if (!(obj instanceof YEncFile))
			throw new ClassCastException();
		
		YEncFile other = (YEncFile) obj;
		return this.getHeader().getPart() - other.getHeader().getPart();
	}

}
