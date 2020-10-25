package com.COVID.COVIDView.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.COVID.COVIDView.Entity.Model;
import com.COVID.COVIDView.Exception.FieldNotFound;

@Service
public class COVIDService {

	//private final String API_URL_All = "https://corona.lmao.ninja/v2/all";
	//private final String API_URL_COUNTRIES = "https://corona.lmao.ninja/v2/countries/";
	private final String API_URL_All = "https://disease.sh/v3/covid-19/all";
	private final String API_URL_COUNTRIES = "https://disease.sh/v3/covid-19/countries/";
	private final String[] EUcountries = { "Albania", "Andorra", "Austria", "Belarus", "Belgium", "Bosnia", "Bulgaria",
			"Channel Islands", "Croatia", "Czechia", "Denmark", "Estonia", "Faroe Islands", "Finland", "France",
			"Germany", "Gibraltar", "Greece", "Holy See (Vatican City State)", "Hungary", "Iceland", "Ireland",
			"Isle of Man", "Italy", "Latvia", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Malta",
			"Moldova", "Monaco", "Montenegro", "Netherlands", "Norway", "Poland", "Portugal", "Romania", "Russia",
			"San Marino", "Serbia", "Slovakia", "Slovenia", "Spain", "Sweden", "Switzerland", "UK", "Ukraine" };

	public List<Model> getDataEu() throws IOException, InterruptedException {
		// API Call EU countries data
		List<Model> COVIDdata = new ArrayList<Model>();
		COVIDdata = apiCallEU();
		return COVIDdata;
	}

	public List<Model> getDataWorldWide() throws IOException, InterruptedException {
		// API Call World Wide data
		List<Model> COVIDdata = new ArrayList<Model>();
		COVIDdata = apiCallWorldWide();
		return COVIDdata;
	}

	public List<Model> getDataSorted(String field) throws FieldNotFound, IOException, InterruptedException {
		List<Model> COVIDdata = new ArrayList<Model>();
		// API Call gets the data, then sorts it by the given field
		COVIDdata = sortList(apiCallEU(), field);
		return COVIDdata;
	}

	public List<Model> apiCallEU() throws IOException, InterruptedException {
		// API call each country
		List<Model> data = new ArrayList<Model>();
		for (String country : EUcountries) {
			// Form the URL for each country and encode the country string with %20
			country = java.net.URLEncoder.encode(country, "UTF-8").replace("+", "%20");
			String url = API_URL_COUNTRIES + country;
			// Gets the HTTP response from the URL
			HttpResponse<String> response = httpConnect(url);
			// Parses the JSON response
			JSONObject jsonObj = jsonParser(response);
			// Fill in the Model with the API response data
			Model temp = fillModel(jsonObj);
			// Ads to the list of Models
			data.add(temp);
		}
		return data;
	}

	public List<Model> apiCallWorldWide() throws IOException, InterruptedException {
		// API call world wide
		List<Model> data = new ArrayList<Model>();
		// Gets the HTTP response from the URL
		HttpResponse<String> response = httpConnect(API_URL_All);
		// Parses the JSON response
		JSONObject jsonObj = jsonParser(response);
		// Fill in the Model with the API response data
		Model temp = fillModel(jsonObj);
		// Ads to the list of Models
		data.add(temp);
		return data;
	}

	public HttpResponse<String> httpConnect(String url) throws IOException, InterruptedException {
		// Creates HTTP request
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		return response;
	}

	public JSONObject jsonParser(HttpResponse<String> response) {
		// Parse JSON into objects
		JSONObject jsonObj = new JSONObject(response.body());
		return jsonObj;
	}

	public Model fillModel(JSONObject jsonObj) {
		// Fill in the model data
		Model ret = new Model();
		if (jsonObj.has("country")) {
			ret.setCountry(jsonObj.getString("country"));
		} else {
			// In the case where its called world wide API
			ret.setCountry("World Wide");
		}
		if (jsonObj.has("active"))
			ret.setActive(jsonObj.getInt("active"));
		if (jsonObj.has("cases"))
			ret.setCases(jsonObj.getInt("cases"));
		if (jsonObj.has("critical"))
			ret.setCritical(jsonObj.getInt("critical"));
		if (jsonObj.has("deaths"))
			ret.setDeaths(jsonObj.getInt("deaths"));
		if (jsonObj.has("recovered"))
			ret.setRecovered(jsonObj.getInt("recovered"));
		if (jsonObj.has("todayCases"))
			ret.setTodayCases(jsonObj.getInt("todayCases"));
		if (jsonObj.has("todayDeaths"))
			ret.setTodayDeaths(jsonObj.getInt("todayDeaths"));
		if (jsonObj.has("todayRecovered"))
			ret.setTodayRecovered(jsonObj.getInt("todayRecovered"));
		if (jsonObj.has("tests"))
			ret.setTests((jsonObj.getInt("tests")));
		return ret;
	}

	public List<Model> sortList(List<Model> ret, String field) throws FieldNotFound {
		// Sort the arrayList by given field
		if (field.equals("TotalCases")) {
			ret.sort(Comparator.comparing(Model::getCases).reversed());
		} else if (field.contentEquals("TodayCases")) {
			ret.sort(Comparator.comparing(Model::getTodayCases).reversed());
		} else if (field.equals("TotalDeaths")) {
			ret.sort(Comparator.comparing(Model::getDeaths).reversed());
		} else if (field.equals("TodayDeaths")) {
			ret.sort(Comparator.comparing(Model::getTodayDeaths).reversed());
		} else if (field.equals("TotalRecovered")) {
			ret.sort(Comparator.comparing(Model::getRecovered).reversed());
		} else if (field.equals("TodayRecovered")) {
			ret.sort(Comparator.comparing(Model::getTodayRecovered).reversed());
		} else if (field.equals("Active")) {
			ret.sort(Comparator.comparing(Model::getActive).reversed());
		} else if (field.equals("Critical")) {
			ret.sort(Comparator.comparing(Model::getCritical).reversed());
		} else if (field.equals("Tests")) {
			ret.sort(Comparator.comparing(Model::getTests).reversed());
		} else {
			// When the Field name does not exist
			throw new FieldNotFound("Invalid URL not valid Table column name");
		}
		return ret;
	}

}
