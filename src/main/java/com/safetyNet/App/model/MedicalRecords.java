package com.safetyNet.App.model;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecords {
	 private String firstName;
	 private String lastName;
	 private String birthdate;
	 private List < String > medications;
	 private List < String > allergies;
	 private String address;
	 private int age;


	 // Getter Methods 

	 public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getFirstName() {
	  return firstName;
	 }

	 public String getLastName() {
	  return lastName;
	 }

	 public String getBirthdate() {
	  return birthdate;
	 }

	 // Setter Methods 

	 public void setFirstName(String firstName) {
	  this.firstName = firstName;
	 }

	 public void setLastName(String lastName) {
	  this.lastName = lastName;
	 }

	 public void setBirthdate(String birthdate) {
	  this.birthdate = birthdate;
	 }

	public List < String > getMedications() {
		return medications;
	}

	public void setMedications(List < String > medications) {
		this.medications = medications;
	}

	public List < String > getAllergies() {
		return allergies;
	}

	public void setAllergies(List < String > allergies) {
		this.allergies = allergies;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
