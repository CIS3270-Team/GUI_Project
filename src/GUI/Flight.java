package GUI;

import java.sql.Date;
import java.sql.Time;

public class Flight {

	private int Flight_ID;
	private String Airline, Arrival_City_ID, Departure_City_ID,Departure_Time, Arrival_Time,Departure_Date, Arrival_Date;
	
	public Flight(int int1, String string, String string2, String string3, String string4, String string5, String string6,
			String string7) {
		Flight_ID = int1;
		Airline = string;
		Arrival_City_ID = string2;
		Departure_City_ID = string3;
		Departure_Date = string4;
		Departure_Time = string5;
		Arrival_Date = string6;
		Arrival_Time = string7;
	}

	public int getFlight_ID() {
		return Flight_ID;
	}

	public void setFlight_ID(int flight_ID) {
		Flight_ID = flight_ID;
	}

	public String getAirline() {
		return Airline;
	}

	public void setAirline(String airline) {
		Airline = airline;
	}

	public String getArrival_City_ID() {
		return Arrival_City_ID;
	}

	public void setArrival_City_ID(String arrival_City_ID) {
		Arrival_City_ID = arrival_City_ID;
	}

	public String getDeparture_City_ID() {
		return Departure_City_ID;
	}

	public void setDeparture_City_ID(String departure_City_ID) {
		Departure_City_ID = departure_City_ID;
	}

	public String getDeparture_Date() {
		return Departure_Date;
	}

	public void setDeparture_Date(String departure_Date) {
		Departure_Date = departure_Date;
	}

	public String getDeparture_Time() {
		return Departure_Time;
	}

	public void setDeparture_Time(String departure_Time) {
		Departure_Time = departure_Time;
	}

	public String getArrival_Date() {
		return Arrival_Date;
	}

	public void setArrival_Date(String arrival_Date) {
		Arrival_Date = arrival_Date;
	}

	public String getArrival_Time() {
		return Arrival_Time;
	}

	public void setArrival_Time(String arrival_Time) {
		Arrival_Time = arrival_Time;
	}

}
