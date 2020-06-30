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
	public void test_02_createRegistration_NullPerson() {
		
		assertEquals(0, service.getAllRegistrations().size());
		String error = "";
		
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
			service.createRegistration(null, event);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Person must be selected", error);
		assertEquals(0, service.getAllRegistrations().size());

	}
	
	@Test
	public void test_03_createRegistration_NonExistentPerson() {
		
		assertEquals(0, service.getAllRegistrations().size());
		String error = "";
		
		Person person = new Person();
		person.setName("FirstName LastName");
		
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
			error = e.getMessage();
		}
		
		assertEquals("Person does not exist", error);
		assertEquals(0, service.getAllRegistrations().size());

	}
	
	@Test
	public void test_04_createRegistration_NullEvent() {
		
		assertEquals(0, service.getAllRegistrations().size());
		String error = "";
		
		Person person = service.createPerson("FirstName LastName");
		
		Event event = null;
		
		try {
			service.createRegistration(person, event);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Event must be selected", error);
		assertEquals(0, service.getAllRegistrations().size());

	}
	
	@Test
	public void test_05_createRegistration_NonExistentEvent() {
		
		assertEquals(0, service.getAllRegistrations().size());
		String error = "";
		
		Person person = service.createPerson("FirstName LastName");
		
		Event event = new Event();
		event.setName("Event Name");
		
		try {
			service.createRegistration(person, event);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Event does not exist", error);
		assertEquals(0, service.getAllRegistrations().size());

	}

	@Test
	public void test_06_createRegistration_Duplicate() {
		
		assertEquals(0, service.getAllRegistrations().size());
		String error="";
		
		Person person = service.createPerson("FirstName LastName");
		
		String name = "Event Name";
		Calendar c = Calendar.getInstance();
	    c.set(2020, Calendar.JUNE, 1);
		Date date = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("09:00");
	    LocalTime endTime = LocalTime.parse("18:00");
	    String description = "Event Description";
		
		Event event = service.createEvent(name, date, Time.valueOf(startTime), Time.valueOf(endTime), description);
		
		try {
			service.createRegistration(person, event);
			service.createRegistration(person, event);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Person is already registered to this event", error);
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
	public void test_08_getRegistrationByPersonAndEvent_NullPerson() {
		
		assertEquals(0, service.getAllRegistrations().size());
		String error = "";
		
		String name = "Event Name";
		Calendar c = Calendar.getInstance();
	    c.set(2020, Calendar.JUNE, 1);
		Date date = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("09:00");
	    LocalTime endTime = LocalTime.parse("18:00");
	    String description = "Event Description";
		
		Event event = service.createEvent(name, date, Time.valueOf(startTime), Time.valueOf(endTime), description);
		
		try {
			service.getRegistrationByPersonAndEvent(null, event);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Person must be selected", error);
		
	}
	
	@Test
	public void test_09_getRegistrationByPersonAndEvent_NonExistentPerson() {
		
		assertEquals(0, service.getAllRegistrations().size());
		
		Person nonExistentPerson = new Person();
		nonExistentPerson.setName("Non Existent Name");
		String error = "";
		
		String name = "Event Name";
		Calendar c = Calendar.getInstance();
	    c.set(2020, Calendar.JUNE, 1);
		Date date = new Date(c.getTimeInMillis());
		LocalTime startTime = LocalTime.parse("09:00");
	    LocalTime endTime = LocalTime.parse("18:00");
	    String description = "Event Description";
		
		Event event = service.createEvent(name, date, Time.valueOf(startTime), Time.valueOf(endTime), description);
		
		try {
			service.getRegistrationByPersonAndEvent(nonExistentPerson, event);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Person does not exist", error);
		
	}
	
	@Test
	public void test_10_getRegistrationByPersonAndEvent_NullEvent() {
		
		assertEquals(0, service.getAllRegistrations().size());
		String error = "";
		
		Person person = service.createPerson("FirstName LastName");
		
		try {
			service.getRegistrationByPersonAndEvent(person, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Event must be selected", error);
		
	}

	@Test
	public void test_11_getRegistrationByPersonAndEvent_NonExistentEvent() {
		
		assertEquals(0, service.getAllRegistrations().size());
		String error = "";
		
		Person person = service.createPerson("FirstName LastName");
		
		Event event = new Event();
		event.setName("Non Existent Event");
		
		try {
			service.getRegistrationByPersonAndEvent(person, event);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Event does not exist", error);
		
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
	public void test_13_getRegistrationsByPerson() {
		
		assertEquals(0, service.getAllRegistrations().size());
		
		Person person = service.createPerson("FirstName LastName");
		
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
		
		service.createRegistration(person, event1);
		service.createRegistration(person, event2);
		
		try {
			service.getRegistrationsByPerson(person);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		List<Registration> registrationsByPerson = service.getRegistrationsByPerson(person);
		
		assertEquals(2, registrationsByPerson.size());
		assertEquals(event1.getName(), registrationsByPerson.get(0).getEvent().getName());
		assertEquals(event2.getName(), registrationsByPerson.get(1).getEvent().getName());
		
	}
	
	@Test
	public void test_14_getRegistrationsByPerson_Null() {
		
		assertEquals(0, service.getAllRegistrations().size());
		String error = "";
		
		Person person = null;
		
		try {
			service.getRegistrationsByPerson(person);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Person must be selected", error);
		
	}
	
	@Test
	public void test_15_getRegistrationsByPerson_NonExistent() {
		
		assertEquals(0, service.getAllRegistrations().size());
		String error = "";
		
		Person person = new Person();
		person.setName("Non Existent Name");
		
		try {
			service.getRegistrationsByPerson(person);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Person does not exist", error);
		
	}
	
}
