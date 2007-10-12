package miage.cloo.tp2.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Marked {

	static int MINEUR = 1;
	static int NORMAL = 2;
	static int HAUTE  = 3;
	
	int level() default NORMAL;
	
}
