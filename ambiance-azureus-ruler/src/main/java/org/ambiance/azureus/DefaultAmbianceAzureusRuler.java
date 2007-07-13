package org.ambiance.azureus;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Chain;
import org.apache.commons.chain.config.ConfigParser;
import org.apache.commons.chain.impl.CatalogFactoryBase;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

/**
 * @plexus.component role="org.ambiance.azureus.AmbianceAzureusService" role-hint="default"
 */
public class DefaultAmbianceAzureusRuler extends AbstractLogEnabled implements AmbianceAzureusRuler, Initializable {

	private Catalog catalog;

	public void initialize() throws InitializationException {
		ConfigParser parser = new ConfigParser();
		try {
			parser.parse(this.getClass().getResource("/org/ambiance/azureus/catalog.xml"));
			catalog = CatalogFactoryBase.getInstance().getCatalog();
		} catch (Exception e) {
			throw new InitializationException("Unable to load rules catalog", e);
		}
		
	}

	public Chain getChain(String chainName) throws AmbianceAzureusRulerException {
		return (Chain) catalog.getCommand(chainName);
	}

}
