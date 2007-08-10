package org.ambiance.desktop;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

import junit.framework.TestCase;

public class TimingFrameworkTest extends TestCase {

	public float f = 0.0f;
	
	public void test1() {
		PropertySetter modifier = new PropertySetter(this, "f", 0f, 360f*8f);
        Animator timer = new Animator(2000, modifier);
        timer.start();
	}
	
	public void test2() {
		Animator timer = PropertySetter.createAnimator(4000, this, "f", 0f, 360f*8f);
		timer.start();
	}
}
