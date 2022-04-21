package com.safetyNet.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyNet.App.model.FireStations;

@Component
public class FirestationService {

	private List<FireStations> listFirestations = new ArrayList();

	private JSONObject jo;

	public FirestationService(@Value("${file.url}") String stringValue) {

		try {

			InputStream inputStream = new FileInputStream(stringValue);

			Reader jsonFile = new InputStreamReader(inputStream, "UTF-8");

			Object ob = new JSONParser().parse(jsonFile);
			// Stockage du fichier Json.json en memoire
			jo = (JSONObject) ob;

			String json = jo.get("firestations").toString();

			final ObjectMapper objectMapper = new ObjectMapper();

			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			listFirestations = objectMapper.readValue(json, new TypeReference<List<FireStations>>() {
			});

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<FireStations> getListFirestation() {
		return listFirestations;
	}

	public void setListFirestation(List<FireStations> listFirestations) {
		this.listFirestations = listFirestations;
	}

}
