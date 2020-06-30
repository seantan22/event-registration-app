package com.example.EventRegistration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
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
import com.example.EventRegistration.model.Person;
import com.example.EventRegistration.model.Registration;
import com.example.EventRegistration.service.EventRegistrationService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestRegistration {
	
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
	public void test_01_createRegistration() {
		
		assertEquals(0, service.getAllRegistrations().size());
		
		Person person = service.createPerson("FirstName LastName");
		
		String name = "Event Name";
		Calendar c = Calendar.getInstance();
	    c.set(2020, Calendar.JUNE, 1);
		Date date = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("09:00");
	    LocalTime endTime = LocalTime.parse("18:00");
	    String description = "Event Description";
		
		Event event = service.createEvent(name, date, Time.valueOf(startTime), Time.valueOf(endTime), description);
		assertEquals(0, service.getEvent(event.getName()).getNumOfParticipants());
		
		try {
			service.createRegistration(person, event);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		assertEquals(1, service.getEvent(event.getName()).getNumOfParticipants());
		assertEquals(1, service.getAllRegistrations().size());

	}
	
	@Test
	public void test_07_getRegistrationByPersonAndEvent() {
		
		assertEquals(0, service.getAllRegistrations().size());
		
		Person person = service.createPerson("FirstName LastName");
		
		String name = "Event Name";
		Calendar c = Calendar.getInstance();
	    c.set(2020, Calendar.JUNE, 1);
		Date date = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("09:00");
	    LocalTime endTime = LocalTime.parse("18:00");
	    String description = "Event Description";
		
		Event event = service.createEvent(name, date, Time.valueOf(startTime), Time.valueOf(endTime), description);
		
		service.createRegistration(person, event);
		
		assertEquals(1, service.getAllRegistrations().size());
		
		try {
			service.getRegistrationByPersonAndEvent(person, event);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		Registration registration = service.getRegistrationByPersonAndEvent(person, event);
		
		assertEquals(person.getName(), registration.getPerson().getName());
		assertEquals(event.getName(), registration.getEvent().getName());
		
	}
	
	@Test
	public void test_12_getAllRegistrations() {
		
		assertEquals(0, service.getAllRegistrations().size());
		
		Person person1 = service.createPerson("FirstName1 LastName1");
		Person person2 = service.createPerson("FirstName2 LastName2");
		
		String name1 = "Event1 Name";
		Calendar c1 = Calendar.getInstance();
	    c1.set(2020, Calendar.JUNE, 1);
		Date date1 = new Date(c1.getTimeInMillis());
		LocalTime startTime1 = LocalTime.parse("09:00");
	    LocalTime endTime1 = LocalTime.parse("18:00");
	    String description1 = "Event Description";
	    
	    String name2 = "Event2 Name";
		Calendar c2 = Calendar.getInstance();
	    c2.set(2020, Calendar.JUNE, 2);
		Date date2 = new Date(c2.getTimeInMillis());
		LocalTime startTime2 = LocalTime.parse("09:00");
	    LocalTime endTime2 = LocalTime.parse("18:00");
	    String description2 = "Event Description";
		
		Event event1 = service.createEvent(name1, date1, Time.valueOf(startTime1), Time.valueOf(endTime1), description1);
		Event event2 = service.createEvent(name2, date2, Time.valueOf(startTime2), Time.valueOf(endTime2), description2);
		
		service.createRegistration(person1, event1);
		service.createRegistration(person2, event2);
		
		try {
			service.getAllRegistrations();
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		List<Registration> registrations = service.getAllRegistrations();
		assertEquals(2, service.getAllRegistrations().size());
		assertEquals(person1.getName(), registrations.get(0).getPerson().getName());
		assertEquals(event1.getName(), registrations.get(0).getEvent().getName());
		assertEquals(person2.getName(), registrations.get(1).getPerson().getName());
		assertEquals(event2.getName(), registrations.get(1).getEvent().getName());
		
	}

	@Test
	public void test_16_deleteRegistration() {
		
		assertEquals(0, service.getAllRegistrations().size());
		
		Person person1 = service.createPerson("FirstName1 LastName1");
		Person person2 = service.createPerson("FirstName2 LastName2");
		
		String name = "Event Name";
		Calendar c = Calendar.getInstance();
	    c.set(2020, Calendar.JUNE, 1);
		Date date = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("09:00");
	    LocalTime endTime = LocalTime.parse("18:00");
	    String description = "Event Description";
		
		Event event = service.createEvent(name, date, Time.valueOf(startTime), Time.valueOf(endTime), description);
		
		Registration registration1 = service.createRegistration(person1, event);
		Registration registration2 = service.createRegistration(person2, event);
		assertEquals(2, service.getAllRegistrations().size());
		
		try {
			service.deleteRegistration(registration1);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		assertEquals(1, service.getAllRegistrations().size());
		
		
	}
}
