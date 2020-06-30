package com.example.EventRegistration.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EventRegistration.dto.CreditCardDto;
import com.example.EventRegistration.dto.EventDto;
import com.example.EventRegistration.dto.OrganizerDto;
import com.example.EventRegistration.dto.PersonDto;
import com.example.EventRegistration.dto.RegistrationDto;
import com.example.EventRegistration.model.CreditCard;
import com.example.EventRegistration.model.Event;
import com.example.EventRegistration.model.Organizer;
import com.example.EventRegistration.model.Person;
import com.example.EventRegistration.model.Registration;
import com.example.EventRegistration.service.EventRegistrationService;

@CrossOrigin(origins = "*")
@RestController
public class EventRegistrationRestController {
	
	@Autowired
	EventRegistrationService service;
	
	
	/*** POST MAPPINGS ***/
	
	@PostMapping(value = {"/persons/{name}", "/persons/{name}/"})
	public PersonDto createPerson(@PathVariable("name") String name) throws IllegalArgumentException {
		Person person = service.createPerson(name);
		return convertToDto(person);
	}
	
	
	
	/*** CONVERT TO DTO ***/
	
	private PersonDto convertToDto(Person person) {
	
		if(person == null) {
			throw new IllegalArgumentException("There is no such person");
		}
		
		PersonDto personDto = new PersonDto(person.getName());
		personDto.setEventsAttended(createAttendedEventDtosForPerson(person));
		
		return personDto;
	}
	
	private Person convertToDomainObject(PersonDto personDto) {
		
		List<Person> allPersons = service.getAllPersons();
		for (Person person : allPersons) {
			if (person.getName().equals(personDto.getName())) {
				return person;
			}
		}
		
		return null;
	}
	
	private OrganizerDto convertToDto(Organizer organizer) {
		
		if (organizer == null) {
			throw new IllegalArgumentException("There is no such organizer");
		}
		
		OrganizerDto organizerDto = new OrganizerDto(organizer.getName());
		
		if(organizer.getOrganizes() != null) {
			organizerDto.setOrganizedEvents(createOrganizedEventDtosForOrganizer(organizer));
		}
		
		return organizerDto;
	}
	
	private EventDto convertToDto(Event event) {
		
		if(event == null) {
			throw new IllegalArgumentException("There is no such event");
		}
		
		EventDto eventDto = new EventDto(event.getName(), event.getDate(), event.getStartTime(), event.getEndTime(), event.getDescription());
		
		return eventDto;
	}
	
	private RegistrationDto convertToDto(Registration registration, Person person, Event event) {
		
		EventDto eventDto = convertToDto(event);
		PersonDto personDto = convertToDto(person);
		
		return new RegistrationDto(personDto, eventDto);
	}

	private RegistrationDto convertToDto(Registration registration) {
		
		EventDto eventDto = convertToDto(registration.getEvent());
		PersonDto personDto = convertToDto(registration.getPerson());
		RegistrationDto registrationDto = new RegistrationDto(personDto, eventDto);
		
		return registrationDto;
	}
	
	private RegistrationDto convertToDtoWithoutPerson(Registration registration) {
		
		RegistrationDto registrationDto = convertToDto(registration);
		registrationDto.setPerson(null);
		
		return registrationDto;
	}
	
	private CreditCardDto convertToDto(CreditCard creditCard) {
		
		if(creditCard == null) {
			throw new IllegalArgumentException("There is no such credit card");
		}
		
		CreditCardDto creditCardDto = new CreditCardDto(creditCard.getAccountNumber(), creditCard.getAmount());
		
		return creditCardDto;
	}
	
	/*** HELPER METHODS ***/
	
	private List<EventDto> createAttendedEventDtosForPerson(Person person) {
		
		List<Event> eventsForPerson = service.getAllEventsAttendedByPerson(person);
		List<EventDto> events = new ArrayList<>();
		EventDto eventDto;
		for (Event event : eventsForPerson) {
			eventDto = convertToDto(event);
			eventDto.setCreditCard(createCreditCardDtosForPerson(event, person));
			events.add(eventDto);
		}
		
		return events;
	}
	
	private List<RegistrationDto> createRegistrationDtosForPerson(Person person) {
		List<Registration> registrationsByPerson = service.getRegistrationsByPerson(person);
		List<RegistrationDto> registrations = new ArrayList<RegistrationDto>();
		for (Registration registration : registrationsByPerson) {
			registrations.add(convertToDtoWithoutPerson(registration));
		}
		
		return registrations;
	}
	
	private List<EventDto> createOrganizedEventDtosForOrganizer(Organizer organizer){
		
		List<Event> eventsOrganizedOrganizer = service.getAllEventsOrganizedByOrganizer(organizer);
		List<EventDto> events = new ArrayList<>();
		for(Event event : eventsOrganizedOrganizer) {
			events.add(convertToDto(event));
		}
		
		return events;
	}
	
	private CreditCardDto createCreditCardDtosForPerson(Event event, Person person) {
		
		Registration registration = service.getRegistrationByPersonAndEvent(person, event);
		if(registration != null) {
			if(registration.getCreditCard() != null) {
				return convertToDto(registration.getCreditCard());
			}
		}
		
		return null;
	}

}