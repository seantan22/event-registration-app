package com.example.EventRegistration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.EventRegistration.dao.EventRepository;
import com.example.EventRegistration.dao.OrganizerRepository;
import com.example.EventRegistration.dao.PersonRepository;
import com.example.EventRegistration.dao.RegistrationRepository;
import com.example.EventRegistration.model.Event;
import com.example.EventRegistration.model.Organizer;
import com.example.EventRegistration.service.EventRegistrationService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestEvent {
	
	@Autowired
	private EventRegistrationService service;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private OrganizerRepository organizerRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private RegistrationRepository registrationRepository;
	
	@AfterEach
	public void clearDatabase() {
		registrationRepository.deleteAll();
		personRepository.deleteAll();
		organizerRepository.deleteAll();
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
		assertEquals(0, service.getAllRegistrations().size());
		
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
	
	@Test
	public void test_08_getEvent() {
		
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
			service.getEvent(name);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		assertEquals(1, service.getAllEvents().size());
		
	}
	
	@Test
	public void test_09_getEvent_Null() {
		
		assertEquals(0, service.getAllEvents().size());
		String error = "";
		
		try {
			service.getEvent("");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Event name cannot be empty", error);
		
	}

	@Test
	public void test_10_getEvent_NonExistent() {
		
		assertEquals(0, service.getAllEvents().size());
		String error = "";
		
		try {
			service.getEvent("Non Existent Event");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Event does not exist", error);
		
	}

	@Test
	public void test_11_getAllEvents() {
		
		assertEquals(0, service.getAllEvents().size());
		
		String name1 = "Event Name One";
		Calendar c1 = Calendar.getInstance();
	    c1.set(2020, Calendar.JUNE, 1);
		Date date1 = new Date(c1.getTimeInMillis());
		LocalTime startTime1 = LocalTime.parse("09:00");
	    LocalTime endTime1 = LocalTime.parse("18:00");
	    String description1 = "Event Description";
	    
	    String name2 = "Event Name Two";
		Calendar c2 = Calendar.getInstance();
	    c2.set(2020, Calendar.JUNE, 2);
		Date date2 = new Date(c2.getTimeInMillis());
		LocalTime startTime2 = LocalTime.parse("09:00");
	    LocalTime endTime2 = LocalTime.parse("18:00");
	    String description2 = "Event Description";
		
		try {
			service.createEvent(name1, date1, Time.valueOf(startTime1), Time.valueOf(endTime1), description1);
			service.createEvent(name2, date2, Time.valueOf(startTime2), Time.valueOf(endTime2), description2);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		List<Event> events = service.getAllEvents();
		assertEquals(2, events.size());
		assertEquals(name1, events.get(0).getName());
		assertEquals(name2, events.get(1).getName());
		
	}
	
	@Test
	public void test_15_getAllEventsOrganizedByOrganizer() {
		
		assertEquals(0, service.getAllEvents().size());
		
		Organizer organizer = service.createOrganizer("Organizer Name");
		
		String name1 = "Event Name One";
		Calendar c1 = Calendar.getInstance();
	    c1.set(2020, Calendar.JUNE, 1);
		Date date1 = new Date(c1.getTimeInMillis());
		LocalTime startTime1 = LocalTime.parse("09:00");
	    LocalTime endTime1 = LocalTime.parse("18:00");
	    String description1 = "Event Description";
	    
	    Event event1 = service.createEvent(name1, date1, Time.valueOf(startTime1), Time.valueOf(endTime1), description1);
	    
	    String name2 = "Event Name Two";
		Calendar c2 = Calendar.getInstance();
	    c2.set(2020, Calendar.JUNE, 2);
		Date date2 = new Date(c2.getTimeInMillis());
		LocalTime startTime2 = LocalTime.parse("09:00");
	    LocalTime endTime2 = LocalTime.parse("18:00");
	    String description2 = "Event Description";
	    
	    Event event2 = service.createEvent(name2, date2, Time.valueOf(startTime2), Time.valueOf(endTime2), description2);
	    
	    assertEquals(2, service.getAllEvents().size());
	    
	    service.organizesEvent(organizer, event1);
		service.organizesEvent(organizer, event2);
		
		assertEquals(2, organizer.getOrganizes().size());
	    
		try {
			service.getAllEventsOrganizedByOrganizer(organizer);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		List<Event> eventsOrganizedByOrganizer = service.getAllEventsOrganizedByOrganizer(organizer);
		assertEquals(2, eventsOrganizedByOrganizer.size());
		assertEquals(name1, eventsOrganizedByOrganizer.get(0).getName());
		assertEquals(name2, eventsOrganizedByOrganizer.get(1).getName());

	}
	
	@Test
	public void test_16_getAllEventsOrganizedByOrganizer_Null() {
		
		assertEquals(0, service.getAllEvents().size());
		String error = "";
		
		Organizer organizer = null;
	    
		try {
			service.getAllEventsOrganizedByOrganizer(organizer);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Organizer must be selected", error);

	}
	
	@Test
	public void test_17_getAllEventsOrganizedByOrganizer_NonExistent() {
		
		assertEquals(0, service.getAllEvents().size());
		String error = "";
		
		Organizer organizer = new Organizer();
    	organizer.setName("Non Existent Organizer");
	    
		try {
			service.getAllEventsOrganizedByOrganizer(organizer);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Organizer does not exist", error);

	}

	@Test
	public void test_18_deleteEvent() {
		
		assertEquals(0, service.getAllEvents().size());
		
		String name = "Event Name";
		Calendar c = Calendar.getInstance();
	    c.set(2020, Calendar.JUNE, 1);
		Date date = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("09:00");
	    LocalTime endTime = LocalTime.parse("18:00");
	    String description = "Event Description";
	    
	    service.createEvent(name, date, Time.valueOf(startTime), Time.valueOf(endTime), description);
	    
	    assertEquals(1, service.getAllEvents().size());
		
		try {
			service.deleteEvent(name);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		assertEquals(0, service.getAllEvents().size());
		
	}

	@Test
	public void test_19_deleteEvent_Null() {
		
		assertEquals(0, service.getAllEvents().size());
		String error = "";
		
		try {
			service.deleteEvent(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Event name cannot be empty", error);
		assertEquals(0, service.getAllEvents().size());
		
	}

	@Test
	public void test_20_deleteEvent_NonExistent() {
		
		assertEquals(0, service.getAllEvents().size());
		String error = "";
		
		try {
			service.deleteEvent("Non Existent Event");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Event does not exist", error);
		assertEquals(0, service.getAllEvents().size());
		
	}
	
}