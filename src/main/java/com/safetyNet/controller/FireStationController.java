package com.safetyNet.controller;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.safetyNet.App.model.FireStations;
import com.safetyNet.App.model.MedicalRecords;
import com.safetyNet.App.model.Persons;
import com.safetyNet.App.model.DTO.Child;
import com.safetyNet.App.model.DTO.Fire;
import com.safetyNet.App.model.DTO.FloodPerson;
import com.safetyNet.App.model.DTO.PersonsWithAdultAndChildrenCount;
import com.safetyNet.service.FirestationService;
import com.safetyNet.service.MedicalRecordService;
import com.safetyNet.service.PersonnesService;
import com.safetyNet.util.Util;

@RestController
public class FireStationController {
	private static final Logger logger = LogManager.getLogger(FireStationController.class);

	// Service qui charge le Json
	@Autowired
	public FirestationService fs;

	@Autowired
	public PersonnesService ps;

	@Autowired
	public MedicalRecordService mrs;

	@RequestMapping(value = { "/firestation/", "/firestation" }, method = RequestMethod.GET)
	public ResponseEntity<?> getListFirestationNumber(@RequestParam Map<String, String> allRequestParams) {

		String stationNumber = allRequestParams.get("stationNumber");

		List<FireStations> listFirestation = fs.getListFirestation();

		List<Persons> listPersons = new ArrayList();

		List<MedicalRecords> listMedicalRecords = new ArrayList();

		if (stationNumber == null) {
			return ResponseEntity.ok(listFirestation);
		}

		listFirestation = listFirestation.stream().filter(number -> number.getStation().equals(stationNumber))
				.collect(Collectors.toList());

		for (FireStations fireStations : listFirestation) {
			listPersons.addAll(
					ps.getListPersons().stream().filter(person -> person.getAddress().equals(fireStations.getAddress()))
							.collect(Collectors.toList()));
		}

		for (Persons persons : listPersons) {
			listMedicalRecords.addAll(
					mrs.getListMedicalRecords().stream().filter(m -> m.getFirstName().equals(persons.getFirstName())
							&& m.getLastName().equals(persons.getLastName())).collect(Collectors.toList()));
		}

		int countA = 0;
		int countE = 0;

		for (MedicalRecords medicalRecords : listMedicalRecords) {

			try {
				String dateBirthdate = medicalRecords.getBirthdate();
				Date dateConvert = new SimpleDateFormat("dd/MM/yy").parse(dateBirthdate);

				int age = Util.age(dateConvert);

				if (age >= 18) {
					countA = countA + 1;
				} else {
					countE = countE + 1;
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.error("Error fonction getListFirestationNumber", e);
			}
		}

		PersonsWithAdultAndChildrenCount pwaac = new PersonsWithAdultAndChildrenCount();
		pwaac.setCountAdult(countA);
		pwaac.setCountChildren(countE);
		pwaac.setPersons(listPersons);

		logger.info("/firestation: getListFirestationNumber(), params : stationNumber = " + stationNumber
				+ ", response:" + pwaac);

		return ResponseEntity.ok(pwaac);

	}

	@RequestMapping(value = { "/firestation/", "/firestation" }, method = RequestMethod.PUT)
	public ResponseEntity<?> personPUT(@RequestParam Map<String, String> allRequestParams) {

		String station = allRequestParams.get("station");
		String address = allRequestParams.get("address");

		List<FireStations> listFireStation = fs.getListFirestation();

		FireStations fireStation = new FireStations();

		fireStation.setStation(station);
		fireStation.setAddress(address);
		listFireStation.add(fireStation);

		logger.info(
				"/firestation: personPUT (), params : stationNumber = " + station + ", response:" + listFireStation);

		return ResponseEntity.ok(listFireStation);
	}

	@RequestMapping(value = { "/firestation/", "/firestation" }, method = RequestMethod.POST)
	public ResponseEntity<?> fireStationPOST(@RequestParam Map<String, String> allRequestParams) {

		String station = allRequestParams.get("station");
		String address = allRequestParams.get("address");

		List<FireStations> listFireStation = fs.getListFirestation();

		FireStations stationToModify = listFireStation.stream().filter(f -> f.getAddress().equals(address))
				.collect(Collectors.toList()).get(0);

		if (station != null) {
			stationToModify.setStation(station);
		}

		logger.info("/firestation: fireStationPOST (), params : stationNumber = " + station + " params : address = "
				+ address + ", response:" + listFireStation);

		return ResponseEntity.ok(listFireStation);
	}

	@RequestMapping(value = { "/firestation/", "/firestation" }, method = RequestMethod.DELETE)
	public ResponseEntity<?> fireStationDELETE(@RequestParam Map<String, String> allRequestParams) {

		String station = allRequestParams.get("station");
		String address = allRequestParams.get("address");
		;

		List<FireStations> listFireStation = fs.getListFirestation();

		listFireStation = listFireStation.stream()
				.filter(station1 -> !station1.getAddress().equals(address) || !station1.getStation().equals(station))
				.collect(Collectors.toList());
		logger.info("/firestation: fireStationDELETE (), params : stationNumber = " + station + " params : address = "
				+ address + ", response:" + listFireStation);
		return ResponseEntity.ok(listFireStation);
	}

	public void setMrs(MedicalRecordService mrs) {
		this.mrs = mrs;
	}

	@RequestMapping(value = { "/childAlert/", "/childAlert" }, method = RequestMethod.GET)
	public ResponseEntity<?> getListChild(@RequestParam Map<String, String> allRequestParams) {

		String address = allRequestParams.get("address");

		List<Persons> listPersons = ps.getListPersons();

		List<MedicalRecords> listMedicalRecords = mrs.getListMedicalRecords();

		List<Child> listChild = new ArrayList();

		List<Persons> listPersonsModify = listPersons.stream().filter(p -> p.getAddress().equals(address))
				.collect(Collectors.toList());

		List<MedicalRecords> newList = new ArrayList();

		for (MedicalRecords medicalRecord : listMedicalRecords) {

			for (Persons persons : listPersonsModify) {
				if (medicalRecord.getFirstName().equals(persons.getFirstName())
						&& medicalRecord.getLastName().equals(persons.getLastName())) {
					newList.add(medicalRecord);
				}
			}
		}
		int countChild = 0;
		int countAdult = 0;

		for (MedicalRecords medicalRecords : newList) {

			try {
				String dateBirthdate = medicalRecords.getBirthdate();
				Date dateConvert = new SimpleDateFormat("dd/MM/yy").parse(dateBirthdate);

				int age = Util.age(dateConvert);
				medicalRecords.setAge(age);
				if (age >= 18) {
					countAdult = countAdult + 1;
				} else {
					countChild = countChild + 1;

					listChild.add(new Child(medicalRecords.getLastName(), medicalRecords.getFirstName(), age,
							listPersonsModify));

				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.error("Error fonction getListChild", e);
			}
		}
		logger.info("/childAlert: getListChild (), params : address = " + address + ", response:" + listChild);
		return ResponseEntity.ok(listChild);

	}

	@RequestMapping(value = { "/phoneAlert/", "/phoneAlert" }, method = RequestMethod.GET)
	public ResponseEntity<?> getListPhoneAlert(@RequestParam Map<String, String> allRequestParams) {

		String stationNumber = allRequestParams.get("station");

		List<FireStations> listFirestation = fs.getListFirestation();
		List<Persons> listPersons = ps.getListPersons();
		listFirestation = fs.getListFirestation();

		List<FireStations> collect = listFirestation.stream()
				.filter(number -> number.getStation().equals(stationNumber)).collect(Collectors.toList());

		List<Persons> newlistPersons = new ArrayList();

		for (FireStations fireStations : collect) {

			List<Persons> listPersonstemp = listPersons.stream()
					.filter(p -> p.getAddress().equals(fireStations.getAddress())).collect(Collectors.toList());

			newlistPersons.addAll(listPersonstemp);
		}

		List<String> listPersonsString = newlistPersons.stream().map(p -> p.getPhone()).collect(Collectors.toList());
		logger.info("/phoneAlert: getListPhoneAlert (), params : stationNumber = " + stationNumber + ", response:"
				+ listPersonsString);
		return ResponseEntity.ok(listPersonsString);
	}

	@RequestMapping(value = { "/communityEmail/", "/communityEmail" }, method = RequestMethod.GET)
	public ResponseEntity<?> getListEmail(@RequestParam Map<String, String> allRequestParams) {

		String city = allRequestParams.get("city");

		List<Persons> listPersons = ps.getListPersons();

		List<Persons> collect = listPersons.stream().filter(persons -> persons.getCity().equals(city))
				.collect(Collectors.toList());
		List<String> listModify = collect.stream().map(Persons::getEmail).collect(Collectors.toList());
		logger.info("/communityEmail: getListEmail (), params : city = " + city + ", response:" + listModify);
		return ResponseEntity.ok(listModify);
	}

	@RequestMapping(value = { "/personInfo/", "/personInfo" }, method = RequestMethod.GET)
	public ResponseEntity<List<MedicalRecords>> getListPersonInfo(@RequestParam Map<String, String> allRequestParams) {

		String firstName = allRequestParams.get("firstName");
		String lastName = allRequestParams.get("lastName");

		List<Persons> listPersons = ps.getListPersons();

		List<MedicalRecords> listMedicalRecords = mrs.getListMedicalRecords();

		List<MedicalRecords> listMedicalRecordsFilter = listMedicalRecords.stream()
				.filter(mr -> mr.getFirstName().equals(firstName) && mr.getLastName().equals(lastName))
				.collect(Collectors.toList());

		for (MedicalRecords medicalRecords : listMedicalRecordsFilter) {
			String adress = listPersons.stream()
					.filter(a -> a.getFirstName().equals(medicalRecords.getFirstName())
							&& a.getLastName().equals(medicalRecords.getLastName()))
					.collect(Collectors.toList()).get(0).getAddress();
			medicalRecords.setAddress(adress);

			String dateBirthdate = medicalRecords.getBirthdate();

			try {
				String dateBirthdate1 = medicalRecords.getBirthdate();
				Date dateConvert = new SimpleDateFormat("dd/MM/yy").parse(dateBirthdate1);
				System.out.println(dateBirthdate1);
				int age = Util.age(dateConvert);
				medicalRecords.setAge(age);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.error("Error fonction getListPersonInfo", e);
			}
		}
		logger.info("/personInfo: getListPersonInfo (), params : First Name = " + firstName + "params : Last Name = "
				+ lastName + ", response:" + listMedicalRecordsFilter);
		return ResponseEntity.ok(listMedicalRecordsFilter);
	}

	@RequestMapping(value = { "/flood/station/", "/flood/station" }, method = RequestMethod.GET)
	public ResponseEntity<?> getListFlood(@RequestParam Map<String, String> allRequestParams) {

		String station = allRequestParams.get("stations");

		List<Persons> listPersons = ps.getListPersons();

		List<MedicalRecords> listMedicalRecords = mrs.getListMedicalRecords();

		List<FloodPerson> floodPerson = new ArrayList();

		List<Persons> PersonModify = new ArrayList();

		List<FireStations> listFireStation = fs.getListFirestation();
		;

		List<MedicalRecords> listMedicalRecordsModify = new ArrayList<>();

		List<FireStations> listFireStationModify = listFireStation.stream()
				.filter(stationNumber -> stationNumber.getStation().equals(station)).collect(Collectors.toList());

		for (FireStations fireStations : listFireStationModify) {
			PersonModify.addAll(
					ps.getListPersons().stream().filter(person -> person.getAddress().equals(fireStations.getAddress()))
							.collect(Collectors.toList()));
		}

		for (Persons listperson : PersonModify) {
			listMedicalRecordsModify
					.addAll(listMedicalRecords.stream()
							.filter(mrp -> mrp.getFirstName().equals(listperson.getFirstName())
									&& mrp.getLastName().equals(listperson.getLastName()))
							.collect(Collectors.toList()));
		}

		for (MedicalRecords medicalRecord : listMedicalRecordsModify) {
			FloodPerson fp = new FloodPerson();
			fp.setAllergies(medicalRecord.getAllergies());
			fp.setMedicament(medicalRecord.getMedications());
			fp.setNom(medicalRecord.getLastName());
			fp.setPrenom(medicalRecord.getFirstName());

			Persons pe = listPersons.stream().filter(p -> p.getFirstName().equals(medicalRecord.getFirstName())
					&& p.getLastName().equals(medicalRecord.getLastName())).collect(Collectors.toList()).get(0);

			fp.setAdresse(pe.getAddress());
			fp.setTelephone(pe.getPhone());

			String dateBirthdate1 = medicalRecord.getBirthdate();
			Date dateConvert;
			try {
				dateConvert = new SimpleDateFormat("dd/MM/yy").parse(dateBirthdate1);
				int age = Util.age(dateConvert);

				fp.setAge(age);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.error("Error fonction getListFlood", e);
			}

			floodPerson.add(fp);
		}

		Map<String, List<FloodPerson>> mapflood = floodPerson.stream()
				.collect(Collectors.groupingBy(FloodPerson::getAdresse));
		logger.info("/flood/station: getListFlood (), params : Station = " + station + ", response:" + mapflood);
		return ResponseEntity.ok(mapflood);

	}

	@RequestMapping(value = { "/fire/", "/fire" }, method = RequestMethod.GET)
	public ResponseEntity<?> getListFire(@RequestParam Map<String, String> allRequestParams) {

		String address = allRequestParams.get("address");

		List<FireStations> listFirestations;
		List<Persons> listPersons;
		List<MedicalRecords> listMedicalRecord;

		listFirestations = fs.getListFirestation();
		listPersons = ps.getListPersons();
		listMedicalRecord = mrs.getListMedicalRecords();

		List<Persons> listPersonsModify = listPersons.stream().filter(p -> p.getAddress().equals(address))
				.collect(Collectors.toList());

		List<Fire> listFire = new ArrayList<>();

		for (Persons person : listPersonsModify) {

			MedicalRecords mrc = listMedicalRecord.stream().filter(
					m -> m.getFirstName().equals(person.getFirstName()) && m.getLastName().equals(person.getLastName()))
					.collect(Collectors.toList()).get(0);

			FireStations fss = listFirestations.stream().filter(f -> f.getAddress().equals(person.getAddress()))
					.collect(Collectors.toList()).get(0);

			Fire f = new Fire();
			f.setNom(person.getLastName());
			f.setAllergies(mrc.getAllergies());
			f.setMedicament(mrc.getMedications());
			f.setTelephone(person.getPhone());
			f.setNumeroDeCaserne(fss.getStation());

			listFire.add(f);
		}
		logger.info("/fire: getListFire (), params : Address = " + address + ", response:" + listFire);
		return ResponseEntity.ok(listFire);

	}
}
