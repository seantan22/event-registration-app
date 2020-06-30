package com.example.EventRegistration.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Calendar;

import com.example.EventRegistration.model.Event;
import com.example.EventRegistration.model.Person;
import com.example.EventRegistration.model.Registration;
import com.example.EventRegistration.service.EventRegistrationService;

public class TestUtils {
	
	public static Person setUpPerson(EventRegistrationService service, String name) throws IllegalArgumentException {
		
		return service.createPerson(name);
		
	}

	public static Event setUpEvent(EventRegistrationService service, String name) throws IllegalArgumentException {
		
		Calendar c = Calendar.getInstance();
	    c.set(2020, Calendar.JUNE, 1);
		Date date = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("09:00");
	    LocalTime endTime = LocalTime.parse("18:00");
	    String description = "Event Description";
		
		return service.createEvent(name, date, Time.valueOf(startTime), Time.valueOf(endTime), description);
		
	}
	
	public static Registration register(EventRegistrationService service, Person person, Event event) throws IllegalArgumentException {
		
		return service.createRegistration(person, event);
		
	}
	
}
