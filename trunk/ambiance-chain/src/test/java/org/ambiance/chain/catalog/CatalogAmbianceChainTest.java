package org.ambiance.chain.catalog;

import org.ambiance.chain.AmbianceChain;
import org.apache.commons.chain.impl.ContextBase;
import org.codehaus.plexus.PlexusTestCase;

/**
 * @plexus.component role="org.ambiance.chain.AmbianceChain" role-hint="catalog"
 */
public class CatalogAmbianceChainTest extends PlexusTestCase {

	AmbianceChain ac = null;
	
	public void setUp() {
		Exception e = null;
		try {        
			super.setUp();
			ac = (AmbianceChain) lookup("org.ambiance.chain.AmbianceChain", "catalog");
		} catch (Exception e1) {
			e = e1;
			e.printStackTrace();
		}
		assertNull(e);
		assertNotNull(ac);
	}
	
	public void testExecute1() {
		Exception e = null;
		ContextBase ctx = new ContextBase();
		try {
			ac.execute("chain1", ctx);
		} catch (Exception e1) {
			e = e1;
			e.printStackTrace();
		}
		assertEquals(ctx.get("FalseCommand"), "no");
		assertEquals(ctx.get("TrueCommand"), "yes");
		assertNull(e);
	}
	
	public void testExecute2() {
		Exception e = null;
		ContextBase ctx = new ContextBase();
		try {
			ac.execute("chain2", ctx);
		} catch (Exception e1) {
			e = e1;
			e.printStackTrace();
		}
		assertEquals(ctx.get("FalseCommand"), null);
		assertEquals(ctx.get("TrueCommand"), "yes");
		assertNull(e);
	}

}
