package com.safetyNet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetyNet.App.model.MedicalRecords;
import com.safetyNet.service.MedicalRecordService;

@RestController
public class MedicalRecordController {
	private static final Logger logger = LogManager.getLogger(FireStationController.class);

	@Autowired
	MedicalRecordService mrs;

	@RequestMapping(value = { "/medicalRecord/", "/medicalRecord" }, method = RequestMethod.GET)
	public ResponseEntity<?> medicalRecordGET(@RequestParam Map<String, String> allRequestParams) {

		List<MedicalRecords> listMedicalRecord = mrs.getListMedicalRecords();

		logger.info("/medicalRecord: medicalRecordGET(), listMedicalRecord = " + listMedicalRecord);
		return ResponseEntity.ok(listMedicalRecord);
	}

	@RequestMapping(value = { "/medicalRecord/", "/medicalRecord" }, method = RequestMethod.PUT)
	public ResponseEntity<?> medicalRecordPUT(@RequestParam Map<String, String> allRequestParams) {

		String firstName = allRequestParams.get("firstName");
		String lastName = allRequestParams.get("lastName");
		String birthdate = allRequestParams.get("birthdate");
		String medications = allRequestParams.get("medications");
		String allergies = allRequestParams.get("allergies");

		List<MedicalRecords> listMedicalRecords = mrs.getListMedicalRecords();

		MedicalRecords medicalRecords = new MedicalRecords();

		medicalRecords.setFirstName(firstName);
		medicalRecords.setLastName(lastName);
		medicalRecords.setBirthdate(birthdate);

		List<String> medic = new ArrayList();

		for (String element : medications.split(",")) {
			medic.add(element);
		}

		medicalRecords.setMedications(medic);

		List<String> allergieList = new ArrayList<>();

		for (String allergieB : allergies.split(",")) {
			allergieList.add(allergieB);
		}
		medicalRecords.setAllergies(allergieList);

		listMedicalRecords.add(medicalRecords);

		logger.info("/medicalRecord: medicalRecordPUT(), params First Name = " + firstName + " params Last Name"
				+ lastName + "params : Birthdate = " + birthdate + "params : medications = " + medications
				+ "params : allergies = " + allergies + "reponse = " + listMedicalRecords);
		return ResponseEntity.ok(listMedicalRecords);
	}

	@RequestMapping(value = { "/medicalRecord/", "/medicalRecord" }, method = RequestMethod.POST)
	public ResponseEntity<?> medicalRecordPOST(@RequestParam Map<String, String> allRequestParams) {

		String firstName = allRequestParams.get("firstName");
		String lastName = allRequestParams.get("lastName");
		String birthdate = allRequestParams.get("birthdate");
		String medications = allRequestParams.get("medications");
		String allergies = allRequestParams.get("allergies");

		List<MedicalRecords> listMedicalrecords = mrs.getListMedicalRecords();

		MedicalRecords medicalRecordModify = listMedicalrecords.stream()
				.filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
				.collect(Collectors.toList()).get(0);

		if (birthdate != null) {
			medicalRecordModify.setBirthdate(birthdate);
		}

		List<String> medic = new ArrayList<>();

		for (String element : medications.split(",")) {
			medic.add(element);
		}

		medicalRecordModify.setMedications(medic);

		List<String> allergieList = new ArrayList<>();

		for (String allergieB : allergies.split(",")) {
			allergieList.add(allergieB);
		}
		medicalRecordModify.setAllergies(allergieList);
		logger.info("/medicalRecord: medicalRecordPOST(), params First Name = " + firstName + " params Last Name"
				+ lastName + "params : Birthdate = " + birthdate + "params : medications = " + medications
				+ "params : allergies = " + allergies + "reponse = " + listMedicalrecords);
		return ResponseEntity.ok(listMedicalrecords);
	}

	@RequestMapping(value = { "/medicalRecord/", "/medicalRecord" }, method = RequestMethod.DELETE)
	public ResponseEntity<?> medicalRecordDELETE(@RequestParam Map<String, String> allRequestParams) {

		String firstName = allRequestParams.get("firstName");

		String lastName = allRequestParams.get("lastName");

		List<MedicalRecords> listMedicalrecords = mrs.getListMedicalRecords();

		listMedicalrecords = listMedicalrecords.stream()
				.filter(person -> !person.getFirstName().equals(firstName) && !person.getLastName().equals(lastName))
				.collect(Collectors.toList());
		logger.info("/medicalRecord: medicalRecordDELETE(), params First Name = " + firstName + " params Last Name"
				+ lastName + "params : medications = " + "reponse = " + listMedicalrecords);
		return ResponseEntity.ok(listMedicalrecords);
	}

	public void setMrs(MedicalRecordService mrs) {
		this.mrs = mrs;
	}

}
