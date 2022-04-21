package com.safetyNet.App;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetyNet.App.model.Persons;
import com.safetyNet.App.model.DTO.PersonsWithAdultAndChildrenCount;
import com.safetyNet.controller.FireStationController;
import com.safetyNet.controller.PersonController;
import com.safetyNet.service.FirestationService;
import com.safetyNet.service.MedicalRecordService;
import com.safetyNet.service.PersonnesService;

@SpringBootTest
public class PersonControllerTest {
	
	@Autowired
	FirestationService fs;
	
	@Autowired
	PersonnesService ps;
	
	@Autowired
	MedicalRecordService mrs;

	@Test
	void PersonGetTest() {
		 Map<String,String> alp = new HashMap<>();
		 alp.put("firstName", "John");
		 alp.put("lastName", "Boyd");
		 
		 PersonController pc = new PersonController();
		 
		 
		 //pc.ps = ps;
		 pc.setPs(ps);
		
		 assertEquals("Boyd", ((List<Persons>) pc.personGET(alp).getBody()).get(0).getLastName());
		 
		 //((List<Persons>) pc.PersonGET(alp).getBody()).get(((List<Persons>) pc.PersonGET(alp).getBody()).size()-1)
		
	}
	
	@Test
	void PersonDeleteTest() {
		 Map<String,String> alp = new HashMap<>();
		 alp.put("firstName", "Eric");
		 alp.put("lastName", "Cadigan");
		 
		 PersonController pc = new PersonController();
		 //pc.ps = ps;
		 pc.setPs(ps);
		
		 assertEquals(ps.getListPersons().size()-1, ((List<Persons>) pc.personDELETE(alp).getBody()).size());
		 
	}
	
	@Test
	void PersonPutTest() {
		 Map<String,String> alp = new HashMap<>();
		 alp.put("firstName", "Marc");
		 alp.put("lastName", "Lebeau");
		 alp.put("address", "12 route de saint Côme");
		 
		 PersonController pc = new PersonController();
		 pc.setPs(ps);
		
		 assertEquals(ps.getListPersons().size()+1, ((List<Persons>) pc.personPUT(alp).getBody()).size());
		 
		 
		
	}
	
	@Test
	void PersonPostTest() {
		 Map<String,String> alp = new HashMap<>();
		 alp.put("firstName", "John");
		 alp.put("lastName", "Boyd");
		 alp.put("address", "12 route de saint Côme");
		 
		 PersonController pc = new PersonController();
		 pc.setPs(ps);
		
		 assertEquals("12 route de saint Côme", ((List<Persons>) pc.personPOST(alp).getBody()).get(0).getAddress());
		 
		 
		
	}

}
