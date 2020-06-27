package com.example.EventRegistration.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EventRegistration.dao.CreditCardRepository;
import com.example.EventRegistration.dao.EventRepository;
import com.example.EventRegistration.dao.OrganizerRepository;
import com.example.EventRegistration.dao.PersonRepository;
import com.example.EventRegistration.dao.RegistrationRepository;
import com.example.EventRegistration.model.Person;

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
	
	@Autowired
	private CreditCardRepository creditCardRepository;
	
	
	/*** PERSON ***/
	
	@Transactional
	public Person createPerson(String name) {
		
		if(name == null || name.trim().length() == 0 || name.replaceAll("//s", "").length() == 0 || name =="" ) {
			throw new IllegalArgumentException("Please enter person name");
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
		
		if(name == null || name.trim().length() == 0 || name.replaceAll("//s", "").length() == 0 || name =="" ) {
			throw new IllegalArgumentException("Please enter person name");
		}
		
		Person person = personRepository.findByName(name);
		return person;
		
	}
	
	@Transactional
	public List<Person> getAllPersons() {
		return toList(personRepository.findAll());
	}
	
	
	/*** ORGANIZER ***/
	
	
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