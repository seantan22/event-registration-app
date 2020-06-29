package com.example.EventRegistration.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EventRegistration.dao.EventRepository;
import com.example.EventRegistration.dao.OrganizerRepository;
import com.example.EventRegistration.dao.PersonRepository;
import com.example.EventRegistration.model.Event;
import com.example.EventRegistration.model.Organizer;
import com.example.EventRegistration.model.Person;

@Service
public class EventRegistrationService {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private OrganizerRepository organizerRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	
	
	/*** PERSON ***/
	
	@Transactional
	public Person createPerson(String name) {
		
		if(name == null || name.trim().length() == 0 || name.replaceAll("//s", "").length() == 0 || name == "" ) {
			throw new IllegalArgumentException("Person name cannot be empty");
		} else if(personRepository.existsById(name)) {
			throw new IllegalArgumentException("Person already exists");
		}
		
		Person person = new Person();
		person.setName(name);
		personRepository.save(person);
		return person;
		
	}
	
	@Transactional
	public Person getPerson(String name) {
		
		if(name == null || name.trim().length() == 0 || name.replaceAll("//s", "").length() == 0 || name == "" ) {
			throw new IllegalArgumentException("Person name cannot be empty");
		} else if(!personRepository.existsById(name)) {
			throw new IllegalArgumentException("Person does not exist");
		}
		
		Person person = personRepository.findByName(name);
		return person;
		
	}
	
	@Transactional
	public List<Person> getAllPersons() {
		return toList(personRepository.findAll());
	}
	
	@Transactional
	public void deletePerson(String name) {
		
		if(name == null || name.trim().length() == 0 || name.replaceAll("//s", "").length() == 0 || name == "" ) {
			throw new IllegalArgumentException("Person name cannot be empty");
		} else if(!personRepository.existsById(name)) {
			throw new IllegalArgumentException("Person does not exist");
		}
		
		Person person = personRepository.findByName(name);
		personRepository.delete(person);
		
	}
	
	
	/*** ORGANIZER ***/
	
	@Transactional
	public Organizer createOrganizer(String name) {
		
		if(name == null || name.trim().length() == 0 || name.replaceAll("//s", "").length() == 0 || name == "" ) {
			throw new IllegalArgumentException("Organizer name cannot be empty");
		} else if(organizerRepository.existsById(name)) {
			throw new IllegalArgumentException("Organizer already exists");
		}
		
		Organizer organizer = new Organizer();
		organizer.setName(name);
		organizerRepository.save(organizer);
		return organizer;
		
	}
	
	@Transactional
	public Person getOrganizer(String name) {
		
		if(name == null || name.trim().length() == 0 || name.replaceAll("//s", "").length() == 0 || name == "" ) {
			throw new IllegalArgumentException("Organizer name cannot be empty");
		} else if(!organizerRepository.existsById(name)) {
			throw new IllegalArgumentException("Organizer does not exist");
		}
		
		Person organizer = organizerRepository.findByName(name);
		return organizer;
		
	}
	
	@Transactional
	public List<Organizer> getAllOrganizers() {
		return toList(organizerRepository.findAll());
	}
	
	@Transactional
	public void deleteOrganizer(String name) {
		
		if(name == null || name.trim().length() == 0 || name.replaceAll("//s", "").length() == 0 || name == "" ) {
			throw new IllegalArgumentException("Organizer name cannot be empty");
		} else if(!organizerRepository.existsById(name)) {
			throw new IllegalArgumentException("Organizer does not exist");
		}
		
		Organizer organizer = organizerRepository.findByName(name);
		organizerRepository.delete(organizer);
		
	}
	
	@Transactional
	public Organizer organizesEvent(Organizer organizer, Event event) {
		
		if(organizer == null) {
			throw new IllegalArgumentException("Organizer must be selected");
		} else if(!organizerRepository.existsById(organizer.getName())) {
			throw new IllegalArgumentException("Organizer does not exist");
		}
		if(event == null) {
			throw new IllegalArgumentException("Event must be selected");
		} else if(!eventRepository.existsById(event.getName())) {
			throw new IllegalArgumentException("Event does not exist");
		}
		
		HashSet<Event> eventsOrganized = new HashSet<Event>();
		Set<Event> organizedEvents = organizer.getOrganizes();
		
		if(organizedEvents != null) {
			for(Event newEvent : organizedEvents) {
				if(newEvent == event) {
					throw new IllegalArgumentException("Organizer is already organizing event");
				}
				eventsOrganized.add(newEvent);
			}
		}
		
		eventsOrganized.add(event);
		organizer.setOrganizes(eventsOrganized);
		organizerRepository.save(organizer);
		return organizer;
		
	}
	
	/*** EVENT ***/
	
	
	
	/*** REGISTRATION ***/
	
	
	
	/*** CREDIT CARD ***/
	
	
	
	/*** HELPER METHODS ***/
	
	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
}