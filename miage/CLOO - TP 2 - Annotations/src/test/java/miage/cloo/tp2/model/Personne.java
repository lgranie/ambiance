package miage.cloo.tp2.model;

import miage.cloo.tp2.annotation.Marked;

public class Personne {

	@Marked(level=1)
	public String nom;
	
	@Marked(level=1)
	public String prenom;
	
	@SuppressWarnings("unused")
	@Marked(level=2)
	private String surnom;
	
	@Marked()
	public Personne(String nom, String prenom, String surnom) {
		this.nom = nom;
		this.prenom = prenom;
		this.surnom = surnom;
	}
	
}
