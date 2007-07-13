package org.ambiance.chain;

import java.net.URL;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.config.ConfigParser;
import org.apache.commons.chain.impl.CatalogFactoryBase;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

/**
 * @plexus.component role="org.ambiance.chain.AmbianceChain" role-hint="catalog"
 */
public class CatalogAmbianceChain extends AbstractLogEnabled implements AmbianceChain, Initializable {

	private Catalog catalog;

	/**
	 * @plexus.configuration default-value="catalog.xml"
	 */
	private String catalogFileName;
	
	public void initialize() throws InitializationException {
		ConfigParser parser = new ConfigParser();
		try {
			parser.parse(new URL(catalogFileName));
			catalog = CatalogFactoryBase.getInstance().getCatalog();
		} catch (Exception e) {
			throw new InitializationException("Unable to load rules catalog", e);
		}
		
	}

	public boolean execute(String name, Context ctx) throws AmbianceChainException {
		Command cmd = catalog.getCommand(name);

		try {
			return cmd.execute(ctx);
		} catch (Exception e) {
			throw new AmbianceChainException("Error while executing command : " + name, e);
		}
		
	}

}
