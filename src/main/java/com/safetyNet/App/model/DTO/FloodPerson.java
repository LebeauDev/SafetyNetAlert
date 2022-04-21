package com.safetyNet.App.model.DTO;

import java.util.List;

import com.safetyNet.App.model.MedicalRecords;

public class FloodPerson {
	
	private String nom;
	private List<String> medicament;
	private String prenom;
	private String telephone;
	private int age;
	private String adresse;
	private List<String> allergies;
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
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
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
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	

}
