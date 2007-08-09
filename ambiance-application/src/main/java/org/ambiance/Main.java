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
