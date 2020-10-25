package com.COVID.COVIDView.Entity;

public class Model {
	private String country;
	private int cases;
	private int todayCases;
	private int deaths;
	private int todayDeaths;
	private int recovered;
	private int todayRecovered;
	private int active;
	private int critical;
	private int tests;

	public int getTests() {
		return tests;
	}

	public void setTests(int tests) {
		this.tests = tests;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getCases() {
		return cases;
	}

	public void setCases(int cases) {
		this.cases = cases;
	}

	public int getTodayCases() {
		return todayCases;
	}

	public void setTodayCases(int todayCases) {
		this.todayCases = todayCases;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getTodayDeaths() {
		return todayDeaths;
	}

	public void setTodayDeaths(int todayDeaths) {
		this.todayDeaths = todayDeaths;
	}

	public int getRecovered() {
		return recovered;
	}

	public void setRecovered(int recovered) {
		this.recovered = recovered;
	}

	public int getTodayRecovered() {
		return todayRecovered;
	}

	public void setTodayRecovered(int todayRecovered) {
		this.todayRecovered = todayRecovered;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getCritical() {
		return critical;
	}

	public void setCritical(int critical) {
		this.critical = critical;
	}

	@Override
	public String toString() {
		return "Model [country=" + country + ", cases=" + cases + ", todayCases=" + todayCases + ", deaths=" + deaths
				+ ", todayDeaths=" + todayDeaths + ", recovered=" + recovered + ", todayRecovered=" + todayRecovered
				+ ", active=" + active + ", critical=" + critical + ", tests=" + tests + "]";
	}

}
