package com.safetyNet.App;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetyNet.App.model.MedicalRecords;
import com.safetyNet.App.model.Persons;
import com.safetyNet.controller.MedicalRecordController;
import com.safetyNet.controller.PersonController;
import com.safetyNet.service.FirestationService;
import com.safetyNet.service.MedicalRecordService;
import com.safetyNet.service.PersonnesService;

@SpringBootTest
public class MedicalRecordsControllerTest {
	
	@Autowired
	FirestationService fs;
	
	@Autowired
	PersonnesService ps;
	
	@Autowired
	MedicalRecordService mrs;

	@Test
	void MedicalRecordsGetTest() {
		 Map<String,String> alp = new HashMap<>();
		 alp.put("firstName", "John");
		 alp.put("lastName", "Boyd");
		 
		 MedicalRecordController mrc = new MedicalRecordController();
		 
		 
		 //pc.ps = ps;
		 mrc.setMrs(mrs);
		
		 assertEquals("Boyd", ((List<MedicalRecords>) mrc.medicalRecordGET(alp).getBody()).get(0).getLastName());
		 
		 //((List<Persons>) pc.PersonGET(alp).getBody()).get(((List<Persons>) pc.PersonGET(alp).getBody()).size()-1)
		
	}
	
	@Test
	void medicalRecordDELETETest() {
		 Map<String,String> alp = new HashMap<>();
		 alp.put("firstName", "Eric");
		 alp.put("lastName", "Cadigan");
		 
		 MedicalRecordController mrc = new MedicalRecordController();
		 
		 mrc.setMrs(mrs);
		
		 assertEquals(mrs.getListMedicalRecords().size()-1, ((List<MedicalRecords>) mrc.medicalRecordDELETE(alp).getBody()).size());
		 
	}

	@Test
	void medicalRecordPUTTest() {
		 Map<String,String> alp = new HashMap<>();
		 alp.put("firstName", "Marc");
		 alp.put("lastName", "Lebeau");
		 alp.put("address", "12 route de saint Côme");
		 alp.put("medications", "paracetamol,Dolipran");
		 alp.put("allergies", "Pollen");
		 MedicalRecordController mrc = new MedicalRecordController();
		 mrc.setMrs(mrs);
		
		 assertEquals(mrs.getListMedicalRecords().size()+1, ((List<MedicalRecords>) mrc.medicalRecordPUT(alp).getBody()).size());
		 
		 
		
	}
	
	
	@Test
	void medicalRecordPOSTTest() {
		 Map<String,String> alp = new HashMap<>();
		 alp.put("firstName", "John");
		 alp.put("lastName", "Boyd");
		 alp.put("medications", "paracetamol,Dolipran");
		 alp.put("allergies", "Pollen,else");
		 alp.put("birthdate", "02/12/1985");
		 
		 MedicalRecordController mrc = new MedicalRecordController();
		 mrc.setMrs(mrs);
		 
		 System.out.println(((List<MedicalRecords>) mrc.medicalRecordPOST(alp).getBody()).get(0).getLastName());
		
		 assertEquals("02/12/1985", ((List<MedicalRecords>) mrc.medicalRecordPOST(alp).getBody()).get(0).getBirthdate());
		 
		 
		
	}
}
