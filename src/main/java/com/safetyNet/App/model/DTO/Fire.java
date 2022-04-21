package com.safetyNet.App.model.DTO;

import java.util.List;

public class Fire {
	
	String nom;
	String numeroDeCaserne;
	String telephone;
	List<String> medicament;
	List<String> allergies;
	
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getNumeroDeCaserne() {
		return numeroDeCaserne;
	}
	public void setNumeroDeCaserne(String numeroDeCaserne) {
		this.numeroDeCaserne = numeroDeCaserne;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public List<String> getMedicament() {
		return medicament;
	}
	public void setMedicament(List<String> medicament) {
		this.medicament = medicament;
	}
	public List<String> getAllergies() {
		return allergies;
	}
	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}

}
