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
package org.ambiance.chain;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

public abstract class AbstractAmbianceChain extends AbstractLogEnabled implements AmbianceChain, Initializable {

	protected Catalog catalog;
	
	public abstract void initialize() throws InitializationException;

	public boolean execute(String name, Context ctx) throws AmbianceChainException {
		Command cmd = catalog.getCommand(name);

		try {
			return cmd.execute(ctx);
		} catch (Exception e) {
			throw new AmbianceChainException("Error while executing command : " + name, e);
		}
		
	}

}
