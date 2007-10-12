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
package org.ambiance;

import org.ambiance.desktop.AmbianceDesktop;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.PlexusContainerException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.codehaus.plexus.embed.Embedder;

public class Main {

	public static void main(String[] args){

		Embedder embedder = new Embedder();
        try {
            embedder.start();
            PlexusContainer container = embedder.getContainer();
            
            AmbianceDesktop ad = (AmbianceDesktop) container.lookup( AmbianceDesktop.ROLE, "gl" );
        } catch ( PlexusContainerException e ) {
            e.printStackTrace();
        } catch ( ComponentLookupException e ) {
            e.printStackTrace();
        }
	}

}
