package com.safetyNet.controller;

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

import com.safetyNet.App.model.Persons;
import com.safetyNet.service.PersonnesService;

@RestController
public class PersonController {
	private static final Logger logger = LogManager.getLogger(FireStationController.class);

	@Autowired
	private PersonnesService ps;

	@RequestMapping(value = { "/person/", "/person" }, method = RequestMethod.GET)
	public ResponseEntity<?> personGET(@RequestParam Map<String, String> allRequestParams) {

		String firstName = allRequestParams.get("firstName");

		String lastName = allRequestParams.get("lastName");

		List<Persons> listPersons1 = ps.getListPersons();

		List<Persons> listPersons = ps.getListPersons();

		listPersons = listPersons.stream()
				.filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
				.collect(Collectors.toList());

		if (firstName == null && lastName == null) {
			return ResponseEntity.ok(listPersons1);

		}

		logger.info("/person: personGET(), params : First Name  = " + firstName + " params : Last Name  = " + lastName
				+ " Reponse = " + listPersons);
		return ResponseEntity.ok(listPersons);
	}

	@RequestMapping(value = { "/person/", "/person" }, method = RequestMethod.PUT)
	public ResponseEntity<?> personPUT(@RequestParam Map<String, String> allRequestParams) {

		String firstName = allRequestParams.get("firstName");
		String lastName = allRequestParams.get("lastName");
		String address = allRequestParams.get("address");
		String city = allRequestParams.get("city");
		String zip = allRequestParams.get("zip");
		String phone = allRequestParams.get("phone");
		String email = allRequestParams.get("email");

		// String entier = allRequestParams.get("entier");

		List<Persons> listPersons = ps.getListPersons();

		Persons person = new Persons();

		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setAddress(address);
		person.setCity(city);
		person.setZip(zip);
		person.setPhone(phone);
		person.setEmail(email);

		listPersons.add(person);

		logger.info("/person: personGET(), params : First Name  = " + firstName + " params : Last Name  = " + lastName
				+ " params : Address  = " + address + " params : City  = " + city + " params : zip  = " + zip
				+ " params : phone  = " + phone + " params : Email  = " + email + " Reponse = " + listPersons);

		return ResponseEntity.ok(listPersons);
	}

	@RequestMapping(value = { "/person/", "/person" }, method = RequestMethod.POST)
	public ResponseEntity<?> personPOST(@RequestParam Map<String, String> allRequestParams) {

		String firstName = allRequestParams.get("firstName");
		String lastName = allRequestParams.get("lastName");
		String address = allRequestParams.get("address");
		String city = allRequestParams.get("city");
		String zip = allRequestParams.get("zip");
		String phone = allRequestParams.get("phone");
		String email = allRequestParams.get("email");

		List<Persons> listPersons = ps.getListPersons();

		Persons personToModify = listPersons.stream()
				.filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
				.collect(Collectors.toList()).get(0);

		if (address != null) {
			personToModify.setAddress(address);
		}
		if (city != null) {
			personToModify.setCity(city);
		}
		if (zip != null) {
			personToModify.setZip(zip);
		}
		if (phone != null) {
			personToModify.setPhone(phone);
		}
		if (email != null) {
			personToModify.setEmail(email);
		}

		logger.info("/person: personPOST(), params : First Name  = " + firstName + " params : Last Name  = " + lastName
				+ " params : Address  = " + address + " params : City  = " + city + " params : zip  = " + zip
				+ " params : phone  = " + phone + " params : Email  = " + email + " Reponse = " + listPersons);

		return ResponseEntity.ok(listPersons);
	}

	@RequestMapping(value = { "/person/", "/person" }, method = RequestMethod.DELETE)
	public ResponseEntity<?> personDELETE(@RequestParam Map<String, String> allRequestParams) {

		String firstName = allRequestParams.get("firstName");

		String lastName = allRequestParams.get("lastName");

		List<Persons> listPersons = ps.getListPersons();

		listPersons = listPersons.stream()
				.filter(person -> !person.getFirstName().equals(firstName) && !person.getLastName().equals(lastName))
				.collect(Collectors.toList());

		logger.info("/person: personGET(), params : First Name  = " + firstName + " params : Last Name  = " + lastName
				+ " Reponse = " + listPersons);
		return ResponseEntity.ok(listPersons);
	}

	public void setPs(PersonnesService ps) {
		this.ps = ps;
	}

}
