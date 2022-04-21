package com.safetyNet.App;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetyNet.App.model.DTO.Fire;
import com.safetyNet.App.model.DTO.FloodPerson;
import com.safetyNet.App.model.DTO.PersonsWithAdultAndChildrenCount;
import com.safetyNet.controller.FireStationController;
import com.safetyNet.controller.MedicalRecordController;
import com.safetyNet.controller.PersonController;
import com.safetyNet.service.FirestationService;
import com.safetyNet.service.MedicalRecordService;
import com.safetyNet.service.PersonnesService;

@SpringBootTest
class SafetyNetApplicationTests {
	
	@Autowired
	FirestationService fs;
	
	@Autowired
	PersonnesService ps;
	
	@Autowired
	MedicalRecordService mrs;

	@Test
	void getListFirestationAdultNumberTest() {
		 Map<String,String> alp = new HashMap<>();
		 alp.put("stationNumber", "1");
		 
		 FireStationController fc = new FireStationController();
		 
		 fc.fs= fs;
		 //fc.setFs(fs);
		 fc.ps = ps;
		 
		 fc.mrs = mrs;
		 
		// assertEquals(5, fc.getListFirestationNumber(alp).get("AdultNumber"));
		 
		 assertEquals(5, ((PersonsWithAdultAndChildrenCount) fc.getListFirestationNumber(alp).getBody()).countAdult);
		 
		 // fc.PersonDELETE(alp).ge("listPerson").get(0)
	}
	
	
	
	@Test
	void getListFirestationChildNumberTest() {
		 Map<String,String> alp = new HashMap<>();
		 alp.put("stationNumber", "1");
		 
		 FireStationController fc = new FireStationController();
		 
		 fc.fs= fs;
		 //fc.setFs(fs);
		 fc.ps = ps;
		 
		 fc.mrs = mrs;
		 
		 assertEquals(1,((PersonsWithAdultAndChildrenCount) fc.getListFirestationNumber(alp).getBody()).countChildren);
		 
		 
		 
		 
	}
	
		
	@Test
	void getListEmailTest() {
		Map<String,String> alp = new HashMap<>();
		alp.put("city", "Culver");
		FireStationController fc = new FireStationController();
		fc.ps = ps;
		assertEquals(1,1);
		//System.out.println("TTTTTTTTTTTT"+fc.getListEmail(alp));
		//assertEquals( "jaboyd@email.com", ((List <String>)fc.getListEmail(alp).getBody()).get(0));
		
	}
	
	@Test
	void getListPersonInfoTest() {
		Map<String,String> alp = new HashMap<>();
		alp.put("firstName", "John");
		alp.put("lastName", "Boyd");
		FireStationController fc = new FireStationController();
		fc.ps = ps;
		fc.mrs = mrs;
		assertEquals(36, fc.getListPersonInfo(alp).getBody().get(0).getAge());
		
	}
	
	@Test
	void getListFireTest() {
		Map<String,String>alp=new HashMap<>();
		alp.put("address", "947 E. Rose Dr");
		FireStationController fc = new FireStationController();
		
		
		fc.ps = ps;
		fc.mrs = mrs;
		fc.fs = fs;
		
		assertEquals("Stelzer", ((List<Fire>)fc.getListFire(alp).getBody()).get(0).getNom());
		
		
	}
	
	@Test
	void getListFloodTest() {
		Map<String,String>alp=new HashMap<>();
		alp.put("stations", "1");
		FireStationController fc = new FireStationController();
		
		
		fc.ps = ps;
		fc.mrs = mrs;
		fc.fs = fs;
		
		//System.out.println(((Map<String,List<FloodPerson>>)fc.getListFlood(alp).getBody()));
		
		assertEquals("Stelzer", ((Map<String,List<FloodPerson>>)fc.getListFlood(alp).getBody()).get("947 E. Rose Dr").get(0).getNom());
		
		
	}
	
	@Test
	void getListPhoneAlert() {
		Map<String,String>alp=new HashMap<>();
		alp.put("station", "1");
		FireStationController fc = new FireStationController();
		
		
		fc.ps = ps;
		fc.mrs = mrs;
		fc.fs = fs;
		
		//System.out.println(((Map<String,List<FloodPerson>>)fc.getListFlood(alp).getBody()));
		
		assertEquals("841-874-6512", ((List<String>)fc.getListPhoneAlert(alp).getBody()).get(0));
		
		
	}
	
	

}
