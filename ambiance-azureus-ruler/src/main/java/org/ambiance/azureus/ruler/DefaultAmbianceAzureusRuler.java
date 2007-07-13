package org.ambiance.azureus.ruler;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.config.ConfigParser;
import org.apache.commons.chain.impl.CatalogFactoryBase;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

/**
 * @plexus.component role="org.ambiance.azureus.AmbianceAzureusRuler" role-hint="default"
 */
public class DefaultAmbianceAzureusRuler extends AbstractLogEnabled implements AmbianceAzureusRuler, Initializable {

	private Catalog catalog;

	private String catalogFileName;
	
	public void initialize() throws InitializationException {
		ConfigParser parser = new ConfigParser();
		try {
			parser.parse(this.getClass().getResource("/org/ambiance/azureus/catalog.xml"));
			catalog = CatalogFactoryBase.getInstance().getCatalog();
		} catch (Exception e) {
			throw new InitializationException("Unable to load rules catalog", e);
		}
		
	}

	public boolean execute(String name, Context ctx) throws AmbianceAzureusRulerException {
		Command cmd = catalog.getCommand(name);

		try {
			return cmd.execute(ctx);
		} catch (Exception e) {
			throw new AmbianceAzureusRulerException("Error while executing command : " + name, e);
		}
		
	}

}
