package com.example.EventRegistration.service;

import java.sql.Date;
import java.sql.Time;
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
import com.example.EventRegistration.dao.RegistrationRepository;
import com.example.EventRegistration.model.Event;
import com.example.EventRegistration.model.Organizer;
import com.example.EventRegistration.model.Person;
import com.example.EventRegistration.model.Registration;

@Service
public class EventRegistrationService {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private OrganizerRepository organizerRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private RegistrationRepository registrationRepository;
	
	
	
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
	
	@Transactional
	public Event buildEvent(Event event, String name, Date date, Time startTime, Time endTime, String description) {
		
		if(event == null) {
			throw new IllegalArgumentException("Event cannot be empty");
		} else if(name == null || name.trim().length() == 0) {
			throw new IllegalArgumentException("Event name cannot be empty");
		} else if(eventRepository.existsById(name)) {
			throw new IllegalArgumentException("Event already exists");
		} else if(date == null) {
			throw new IllegalArgumentException("Event date cannot be empty");
		} else if(startTime == null) {
			throw new IllegalArgumentException("Event start time cannot be empty");
		} else if(endTime == null) {
			throw new IllegalArgumentException("Event end time cannot be empty");
		} else if(endTime != null && startTime != null && endTime.before(startTime)) {
			throw new IllegalArgumentException("Event end time cannot be before event start time");
		}
		
		event.setName(name);
		event.setDate(date);
		event.setStartTime(startTime);
		event.setEndTime(endTime);
		event.setDescription(description);
		
		return event;
		
	}
	
	@Transactional
	public Event createEvent(String name, Date date, Time startTime, Time endTime, String description) {
		
		Event event = new Event();
		buildEvent(event, name, date, startTime, endTime, description);
		eventRepository.save(event);
		
		return event;
		
	}
	
	@Transactional
	public Event getEvent(String name) {
		
		if(name == null || name.trim().length() == 0  || name.replaceAll("\\s", "").length() == 0 || name == "") {
			throw new IllegalArgumentException("Event name cannot be empty");
		} else if(!eventRepository.existsById(name)) {
			throw new IllegalArgumentException("Event does not exist");
		}
		
		Event event = eventRepository.findByName(name);
		
		return event;
		
	}

	@Transactional
	public List<Event> getAllEvents() {
		
		return toList(eventRepository.findAll());
	
	}
	
	@Transactional
	public List<Event> getAllEventsAttendedByPerson(Person person) {
		
		if(person == null) {
			throw new IllegalArgumentException("Person must be selected");
		} else if(!personRepository.existsById(person.getName())) {
			throw new IllegalArgumentException("Person does not exist");
		}
		
		List<Event> eventsAttendedByPerson = new ArrayList<>();
		for(Registration r : registrationRepository.findByPerson(person)) {
			eventsAttendedByPerson.add(r.getEvent());
		}
		
		return eventsAttendedByPerson;
	
	}
	
	@Transactional
	public List<Event> getAllEventsOrganizedByOrganizer(Organizer organizer) {
		
		if(organizer == null) {
			throw new IllegalArgumentException("Organizer must be selected");
		} else if(!organizerRepository.existsById(organizer.getName())) {
			throw new IllegalArgumentException("Organizer does not exist");
		}
		
		List<Event> eventsOrganizedByOrganizer = new ArrayList<>();
		for(Event e : organizer.getOrganizes()) {
			eventsOrganizedByOrganizer.add(e);
		}
		
		return eventsOrganizedByOrganizer;
		
	}
	
	@Transactional
	public void deleteEvent(String name) {
		
		if(name == null || name.trim().length() == 0  || name.replaceAll("\\s", "").length() == 0 || name == "") {
			throw new IllegalArgumentException("Event name cannot be empty");
		} else if(!eventRepository.existsById(name)) {
			throw new IllegalArgumentException("Event does not exist");
		}
		
		Event event = eventRepository.findByName(name);
		eventRepository.delete(event);
	}
	
	
	
	/*** REGISTRATION ***/
	
	@Transactional
	public Registration createRegistration(Person person, Event event) {
		
		if(person == null) {
			throw new IllegalArgumentException("Person must be selected");
		} else if(!personRepository.existsById(person.getName())) {
			throw new IllegalArgumentException("Person does not exist");
		} else if(event == null) {
			throw new IllegalArgumentException("Event must be selected");
		} else if(!eventRepository.existsById(event.getName())) {
			throw new IllegalArgumentException("Event does not exist");
		} else if(registrationRepository.existsByPersonAndEvent(person, event)) {
			throw new IllegalArgumentException("Person is already registered to this event");
		}
		
		Registration registration = new Registration();
		registration.setId(person.getName().hashCode() * event.getName().hashCode());
		registration.setPerson(person);
		registration.setEvent(event);
		registrationRepository.save(registration);
		
		return registration;
		
	}
	
	@Transactional
	public Registration getRegistrationByPersonAndEvent(Person person, Event event) {
		
		if(person == null) {
			throw new IllegalArgumentException("Person must be selected");
		} else if(!personRepository.existsById(person.getName())) {
			throw new IllegalArgumentException("Person does not exist");
		} else if(event == null) {
			throw new IllegalArgumentException("Event must be selected");
		} else if(!eventRepository.existsById(event.getName())) {
			throw new IllegalArgumentException("Event does not exist");
		}
		
		Registration registration = registrationRepository.findByPersonAndEvent(person, event);
		
		return registration;
	
	}
	
	@Transactional
	public List<Registration> getAllRegistrations() {
		return toList(registrationRepository.findAll());
	}
	
	@Transactional
	public List<Registration> getRegistrationsByPerson(Person person){
		
		if(person == null) {
			throw new IllegalArgumentException("Person must be selected");
		} else if(!personRepository.existsById(person.getName())) {
			throw new IllegalArgumentException("Person does not exist");
		}
		
		List<Registration> registrationsByPerson = registrationRepository.findByPerson(person);
		
		return registrationsByPerson;
		
	}
	
	@Transactional
	public void deleteRegistration(Person person, Event event) {
		
		if(person == null) {
			throw new IllegalArgumentException("Person must be selected");
		} else if(!personRepository.existsById(person.getName())) {
			throw new IllegalArgumentException("Person does not exist");
		} else if(event == null) {
			throw new IllegalArgumentException("Event must be selected");
		} else if(!eventRepository.existsById(event.getName())) {
			throw new IllegalArgumentException("Event does not exist");
		}
		
		Registration registration = registrationRepository.findByPersonAndEvent(person, event);
		
		registrationRepository.delete(registration);
	}
	
	
	
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