package com.safetyNet.App;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetyNet.App.model.FireStations;
import com.safetyNet.App.model.DTO.PersonsWithAdultAndChildrenCount;
import com.safetyNet.controller.FireStationController;
import com.safetyNet.service.FirestationService;
import com.safetyNet.service.MedicalRecordService;
import com.safetyNet.service.PersonnesService;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FireStationControllerTest {

	FireStationController fsc;

	@Value("${file.url}")
	String stringValue;

	@BeforeEach
	public void fireStationEach() {
		fsc = new FireStationController();

		fsc.fs = new FirestationService(stringValue);
	}

	@Autowired
	PersonnesService ps;

	@Autowired
	MedicalRecordService mrs;

	@Test
	void AFireStationControllerGetTest() {
		Map<String, String> alp = new HashMap<>();
		alp.put("stationNumber", "3");

		fsc.ps = ps;
		fsc.mrs = mrs;
		assertEquals("Boyd", ((PersonsWithAdultAndChildrenCount) fsc.getListFirestationNumber(alp).getBody())
				.getPersons().get(0).getLastName());

		// ((List<Persons>) pc.PersonGET(alp).getBody()).get(((List<Persons>)
		// pc.PersonGET(alp).getBody()).size()-1)

	}

	@Test
	void BFireStationControllerPUTTest() {
		Map<String, String> alp = new HashMap<>();
		alp.put("station", "5");
		alp.put("address", "12 st Gluver");

		assertEquals(fsc.fs.getListFirestation().size() + 1,
				((List<FireStations>) fsc.personPUT(alp).getBody()).size());

	}

	@Test
	void CFireStationControllerPOSTTest() {
		Map<String, String> alp = new HashMap<>();
		alp.put("station", "6");
		alp.put("address", "1509 Culver St");

		assertEquals("6", ((List<FireStations>) fsc.fireStationPOST(alp).getBody()).stream()
				.filter(a -> a.getAddress().equals("1509 Culver St")).collect(Collectors.toList()).get(0).getStation());

	}

	@Test
	void DFireStationControllerDELETETest() {
		Map<String, String> alp = new HashMap<>();
		alp.put("station", "1");
		alp.put("address", "947 E. Rose Dr");

		assertEquals(fsc.fs.getListFirestation().size() - 1,
				((List<FireStations>) fsc.fireStationDELETE(alp).getBody()).size());

	}

}
