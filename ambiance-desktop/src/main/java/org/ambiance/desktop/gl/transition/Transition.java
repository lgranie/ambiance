package org.ambiance.desktop.gl.transition;

import org.ambiance.desktop.gl.renderable.Renderable;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.jdesktop.animation.timing.Animator;

public abstract class Transition extends AbstractLogEnabled implements Renderable {

    protected Animator animator;
    
    protected int duration;
	
}
