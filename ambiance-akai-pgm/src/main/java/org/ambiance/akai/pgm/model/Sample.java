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
package org.ambiance.akai.pgm.model;

public class Sample {
	/*
	 * Value is the name of the wave file without the ".wav" extension assigned
	 * to this pad. Names shorter than 16 characters are null (0x00) padded.
	 */
	private String name;
	
	private int level;
	
	private int rangeLower;
	
	private int rangeUpper;
	
	private int tuning;
	
	private char playMode;
	
}
