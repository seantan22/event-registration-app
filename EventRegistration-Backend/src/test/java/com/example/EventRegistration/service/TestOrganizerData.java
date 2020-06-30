package com.example.EventRegistration.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Calendar;

import com.example.EventRegistration.model.Event;
import com.example.EventRegistration.service.EventRegistrationService;

public class TestOrganizerData {

	public static Event setupEvent(EventRegistrationService service, String name) throws IllegalArgumentException {
		
		Calendar c = Calendar.getInstance();
		c.set(2020, Calendar.JUNE, 16, 7, 0, 0);
		Date eventDate = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("07:00");
		c.set(2020, Calendar.JUNE, 16, 9, 30, 0);
		LocalTime endTime = LocalTime.parse("09:00");
		String description = "Event Description";
		
		return service.createEvent(name, eventDate, Time.valueOf(startTime) , Time.valueOf(endTime), description);
		
	}
	
}