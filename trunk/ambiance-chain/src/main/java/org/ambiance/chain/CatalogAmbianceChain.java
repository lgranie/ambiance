package org.ambiance.chain;

import java.net.URL;

import org.apache.commons.chain.config.ConfigParser;
import org.apache.commons.chain.impl.CatalogFactoryBase;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;

/**
 * @plexus.component role="org.ambiance.chain.AmbianceChain" role-hint="catalog"
 */
public class CatalogAmbianceChain extends AbstractAmbianceChain {

	/**
	 * @plexus.configuration default-value="catalog.xml"
	 */
	private String catalogFileName;
	
	public void initialize() throws InitializationException {
		ConfigParser parser = new ConfigParser();
		try {
			parser.parse(this.getClass().getResource(catalogFileName));
			catalog = CatalogFactoryBase.getInstance().getCatalog();
		} catch (Exception e) {
			throw new InitializationException("Unable to load rules catalog", e);
		}
		
	}

}
