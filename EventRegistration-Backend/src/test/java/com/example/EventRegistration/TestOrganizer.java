package com.example.EventRegistration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.EventRegistration.dao.EventRepository;
import com.example.EventRegistration.dao.OrganizerRepository;
import com.example.EventRegistration.model.Event;
import com.example.EventRegistration.model.Organizer;
import com.example.EventRegistration.service.EventRegistrationService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestOrganizer {

	@Autowired
	private EventRegistrationService service;
	
	@Autowired
	private OrganizerRepository organizerRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@AfterEach
	public void clearDatabase() {
		organizerRepository.deleteAll();
		eventRepository.deleteAll();
	}
	
    @Test
    public void test_01_organizesEvent() {
    	
    	Organizer organizer = service.createOrganizer("FirstName LastName");
    	Event event = TestOrganizerData.setupEvent(service, "Event Name");
    	
    	try {
    		service.organizesEvent(organizer, event);
    	} catch (IllegalArgumentException e) {
    		fail();
    	}
    	
    	assertEquals(1, organizer.getOrganizes().size());
    	
    }

    @Test
    public void test_02_organizesEvent_NullOrganizer() {
    	
    	Organizer organizer = null;
    	Event event = TestOrganizerData.setupEvent(service, "Event Name");
    	String error = "";
    	
    	try {
    		service.organizesEvent(organizer, event);
    	} catch (IllegalArgumentException e) {
    		error = e.getMessage();
    	}
    	
    	assertEquals("Organizer must be selected", error);
    	
    }
    
    @Test
    public void test_03_organizesEvent_NonExistentOrganizer() {
    	
    	Organizer organizer = new Organizer();
    	organizer.setName("Non Existent Organizer");
    	Event event = TestOrganizerData.setupEvent(service, "Event Name");
    	String error = "";
    	
    	try {
    		service.organizesEvent(organizer, event);
    	} catch (IllegalArgumentException e) {
    		error = e.getMessage();
    	}
    	
    	assertEquals("Organizer does not exist", error);
    	
    }
    
    @Test
    public void test_04_organizesEvent_NullEvent() {
    	
    	Organizer organizer = service.createOrganizer("FirstName LastName");
    	Event event = null;
    	String error = "";
    	
    	try {
    		service.organizesEvent(organizer, event);
    	} catch (IllegalArgumentException e) {
    		error = e.getMessage();
    	}
    	
    	assertEquals("Event must be selected", error);
    	
    }

    @Test
    public void test_05_organizesEvent_NonExistentEvent() {
    	
    	Organizer organizer = service.createOrganizer("FirstName LastName");
    	Event event = new Event();
    	event.setName("Event Name");
    	String error = "";
    	
    	try {
    		service.organizesEvent(organizer, event);
    	} catch (IllegalArgumentException e) {
    		error = e.getMessage();
    	}
    	
    	assertEquals("Event does not exist", error);
    	
    } 

    @Test
    public void test_06_organizesEvent_DuplicateEvent() {
    	
    	Organizer organizer = service.createOrganizer("FirstName LastName");
    	Event event = TestOrganizerData.setupEvent(service, "Event Name");
    	String error = "";
    	
    	try {
    		service.organizesEvent(organizer, event);
    	} catch (IllegalArgumentException e) {
    		fail();
    	}
    	
    	assertEquals(1, organizer.getOrganizes().size());
    	
    	try {
    		service.organizesEvent(organizer, event);
    	} catch (IllegalArgumentException e) {
    		error = e.getMessage();
    	}
    	
    	assertEquals("Organizer is already organizing event", error);
    	assertEquals(1, organizer.getOrganizes().size());
    	
    }

}