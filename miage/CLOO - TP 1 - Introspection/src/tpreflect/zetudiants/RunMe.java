package tpreflect.zetudiants;

import tpreflect.paquetcadeau.PaquetCadeau;
import java.util.Iterator;
import java.util.Vector;

public class RunMe {

  public static void main(String[] args) {

    Vector v = new PaquetCadeau().getPaquetCadeau();

    Iterator i = v.iterator();
    while (i.hasNext()) {
      Object o = i.next();
      introspect(o);
    }
  }

  private static void introspect(Object o) {
    // fill in!
  }

}
