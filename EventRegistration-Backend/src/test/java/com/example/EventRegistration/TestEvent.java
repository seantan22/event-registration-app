package com.example.EventRegistration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.EventRegistration.dao.EventRepository;
import com.example.EventRegistration.model.Event;
import com.example.EventRegistration.service.EventRegistrationService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestEvent {
	
	@Autowired 
	private EventRegistrationService service;
	
	@Autowired
	private EventRepository eventRepository;
	
	@AfterEach
	public void clearDatabase() {
		eventRepository.deleteAll();
	}
	
	@Test
	public void test_01_createEvent() {
		
		assertEquals(0, service.getAllEvents().size());
	
		String name = "Event Name";
		Calendar c = Calendar.getInstance();
	    c.set(2020, Calendar.JUNE, 1);
		Date date = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("09:00");
	    LocalTime endTime = LocalTime.parse("18:00");
	    String description = "Event Description";
		
		try {
			service.createEvent(name, date, Time.valueOf(startTime), Time.valueOf(endTime), description);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		Event event = service.getAllEvents().get(0);
		
		assertEquals(0, service.getAllPersons().size());
		assertEquals(1, service.getAllEvents().size());
		assertEquals(name, event.getName());
		assertEquals(date.toString(), event.getDate().toString());
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
		assertEquals(startTime.format(timeFormat).toString(), event.getStartTime().toString());
		assertEquals(endTime.format(timeFormat).toString(), event.getEndTime().toString());
		assertEquals(description, event.getDescription());
//		assertEquals(0, service.getAllRegistrations().size());
		
	}
	
	@Test
	public void test_02_createEvent_NullName() {
		
		assertEquals(0, service.getAllEvents().size());
		String error = "";
		
		String name = null;
		Calendar c = Calendar.getInstance();
	    c.set(2020, Calendar.JUNE, 1);
		Date date = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("09:00");
	    LocalTime endTime = LocalTime.parse("18:00");
	    String description = "Event Description";
		
		try {
			service.createEvent(name, date, Time.valueOf(startTime), Time.valueOf(endTime), description);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
	    assertEquals("Event name cannot be empty", error);
        
        assertEquals(0, service.getAllEvents().size());
		
	}
	
	@Test
	public void test_03_createEvent_DuplicateName() {
		
		assertEquals(0, service.getAllEvents().size());
		String error = "";
		
		String name = "Event Name";
		Calendar c = Calendar.getInstance();
	    c.set(2020, Calendar.JUNE, 1);
		Date date = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("09:00");
	    LocalTime endTime = LocalTime.parse("18:00");
	    String description = "Event Description";
		
		try {
			service.createEvent(name, date, Time.valueOf(startTime), Time.valueOf(endTime), description);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
        
        assertEquals(1, service.getAllEvents().size());
        
        String nameDuplicate = "Event Name";
        
        try {
			service.createEvent(nameDuplicate, date, Time.valueOf(startTime), Time.valueOf(endTime), description);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
        
        assertEquals("Event already exists", error);
        
        assertEquals(1, service.getAllEvents().size());
		
	}

	@Test
	public void test_04_createEvent_NullDate() {
		
		assertEquals(0, service.getAllEvents().size());
		String error = "";
		
		String name = "Event Name";
		Date date = null;
		LocalTime startTime = LocalTime.parse("09:00");
	    LocalTime endTime = LocalTime.parse("18:00");
	    String description = "Event Description";
		
		try {
			service.createEvent(name, date, Time.valueOf(startTime), Time.valueOf(endTime), description);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
	    assertEquals("Event date cannot be empty", error);
        
        assertEquals(0, service.getAllEvents().size());
		
	}
	
	@Test
	public void test_05_createEvent_NullStartTime() {
		
		assertEquals(0, service.getAllEvents().size());
		String error = "";
		
		String name = "Event Name";
		Calendar c = Calendar.getInstance();
	    c.set(2020, Calendar.JUNE, 1);
		Date date = new Date(c.getTimeInMillis());
		Time startTime = null;
	    LocalTime endTime = LocalTime.parse("18:00");
	    String description = "Event Description";
		
		try {
			service.createEvent(name, date, startTime, Time.valueOf(endTime), description);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
	    assertEquals("Event start time cannot be empty", error);
        
        assertEquals(0, service.getAllEvents().size());
		
	}
	
	@Test
	public void test_06_createEvent_NullEndTime() {
		
		assertEquals(0, service.getAllEvents().size());
		String error = "";
		
		String name = "Event Name";
		Calendar c = Calendar.getInstance();
	    c.set(2020, Calendar.JUNE, 1);
		Date date = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("09:00");
		Time endTime = null;
	    String description = "Event Description";
		
		try {
			service.createEvent(name, date, Time.valueOf(startTime), endTime, description);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
	    assertEquals("Event end time cannot be empty", error);
        
        assertEquals(0, service.getAllEvents().size());
		
	}
	
	@Test
	public void test_07_createEvent_InvalidTime() {
		
		assertEquals(0, service.getAllEvents().size());
		String error = "";
		
		String name = "Event Name";
		Calendar c = Calendar.getInstance();
	    c.set(2020, Calendar.JUNE, 1);
		Date date = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("18:00");
	    LocalTime endTime = LocalTime.parse("09:00");
	    String description = "Event Description";
		
		try {
			service.createEvent(name, date, Time.valueOf(startTime), Time.valueOf(endTime), description);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
	    assertEquals("Event end time cannot be before event start time", error);
        
        assertEquals(0, service.getAllEvents().size());
		
	}
	
}
