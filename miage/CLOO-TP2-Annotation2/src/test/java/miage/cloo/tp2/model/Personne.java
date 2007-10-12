package miage.cloo.tp2.model;

import miage.cloo.tp2.annotation.Table;
import miage.cloo.tp2.annotation.Column;
import miage.cloo.tp2.annotation.Id;

@Table("PERSONNE")
public class Personne {

	@Id
	@Column("NOM")
	public String nom;
	
	@Column("PRENOM")
	public String prenom;

	@Column("SURNOM")
	private String surnom;
	
	public Personne(String nom, String prenom, String surnom) {
		this.nom = nom;
		this.prenom = prenom;
		this.surnom = surnom;
	}
	
}
