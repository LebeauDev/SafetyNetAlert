package com.safetyNet.App.model.DTO;

import java.util.List;

import com.safetyNet.App.model.Persons;

public class Child {
	private String nom;
	private String prenom;
	private int age;
	
	private List <Persons> famille;
	
	public Child( ) {

	}
	
	
	public Child(String nom, String prenom,  int age, List <Persons> famille ) {
		this.nom = nom;
		this.prenom =  prenom;
		this.age = age;
		this.famille = famille;
	}
	
	public List <Persons> getFamille() {
		return famille;
	}
	public void setFamille(List <Persons> famille) {
		this.famille = famille;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

}
