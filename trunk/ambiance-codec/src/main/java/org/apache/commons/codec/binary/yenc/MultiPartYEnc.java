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

import java.io.File;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.codec.DecoderException;

public class MultiPartYEnc {

	private SortedSet<YEncFile> parts;

	public MultiPartYEnc(File[] inputs) throws YEncException {
		parts = new TreeSet<YEncFile>();
		
		for (int i=0; i < inputs.length; i++) {
			parts.add(new YEncFile(inputs[i]));
		}
	}

	public void decode(String path) throws YEncException {
		YEnc yenc = new YEnc();
		for (YEncFile file: parts) {
			try {
				yenc.decode(file);
			} catch (DecoderException e) {
				throw new YEncException(e);
			}
		}
	}


}
