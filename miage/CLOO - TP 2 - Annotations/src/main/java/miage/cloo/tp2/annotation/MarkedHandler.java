package miage.cloo.tp2.annotation;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class MarkedHandler {

	private Object o;
	
	public MarkedHandler(Object o) {
		this.o = o;
	}
	
	public void printMarked(int level) {
		Class<?> lClassInstance = o.getClass();
		
		// Pour tous les champs de l'objet : 
		for ( Field f : lClassInstance.getDeclaredFields() ) {
			printMarked(f, level);
		}

		// Pour tous les constructeurs de l'objet : 
		for ( Constructor<?> c : lClassInstance.getDeclaredConstructors() ) {
			printMarked(c, level);
		}
		
	}
	
	private void printMarked(AnnotatedElement ae, int level) {
		// On recherche l'annotation @Marked : 
		Marked marked = ae.getAnnotation(Marked.class);
		if(marked != null && marked.level() >= level)
			System.out.println(ae.toString());
	}
	
}
