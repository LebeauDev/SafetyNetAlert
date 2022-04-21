package com.safetyNet.App.model.DTO;

import java.util.List;

import com.safetyNet.App.model.Persons;

public class PersonsWithAdultAndChildrenCount {
	
	public List<Persons> persons;
	public int countChildren;
	public int countAdult;
	
	
	public List<Persons> getPersons() {
		return persons;
	}
	public void setPersons(List<Persons> persons) {
		this.persons = persons;
	}
	public int getCountChildren() {
		return countChildren;
	}
	public void setCountChildren(int countChildren) {
		this.countChildren = countChildren;
	}
	public int getCountAdult() {
		return countAdult;
	}
	public void setCountAdult(int countAdult) {
		this.countAdult = countAdult;
	}
	
	

}
