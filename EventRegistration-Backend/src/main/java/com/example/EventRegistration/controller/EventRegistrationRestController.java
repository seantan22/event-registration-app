package com.example.EventRegistration.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@PostMapping(value = {"/organizers/{name}", "/organizers/{name}/"})
	public OrganizerDto createOrganizer(@PathVariable("name") String name) throws IllegalArgumentException {
		
		Organizer organizer = service.createOrganizer(name);
		
		return convertToDto(organizer);
	}
	
	@PostMapping(value = {"events/{name}", "/events/{name}/"})
	public EventDto createEvent(@PathVariable("name") String name, 
								@RequestParam(name = "date") Date date,
								@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
								@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime,
								@RequestParam(name = "description") String description) 
										throws IllegalArgumentException {
		
		Event event = service.createEvent(name, date, Time.valueOf(startTime), Time.valueOf(endTime), description);
		
		return convertToDto(event);
	}
	

	@PostMapping(value = {"/register", "/register/"})
	public RegistrationDto createRegistration(@RequestParam(name = "person") PersonDto personDto, 
											  @RequestParam(name = "event") EventDto eventDto) 
													  throws IllegalArgumentException {
		
		Person person = service.getPerson(personDto.getName());
		Event event = service.getEvent(eventDto.getName());
		
		Registration registration = service.createRegistration(person, event);
		
		return convertToDto(registration, person, event);
	}

	@PostMapping(value = {"/assignOrganizer", "/assignOrganizer/"}) 
	public OrganizerDto assignOrganizerToEvent(@RequestParam(name = "organizer") String organizerName,
											   @RequestParam(name = "event") String eventName)
													throws IllegalArgumentException {
		
		Organizer organizer = service.getOrganizer(organizerName);
		Event event = service.getEvent(eventName);
		
		service.organizesEvent(organizer, event);
		
		return convertToDto(organizer);
	}
	
	@PostMapping(value = {"/payment", "/payment/"})
	public PersonDto createCreditCardPayment(@RequestParam(name = "accountNumber") String accountNumber,
											 @RequestParam(name = "amount") float amount,
											 @RequestParam(name = "person") String personName,
											 @RequestParam(name = "event") String eventName) 
													 throws IllegalArgumentException {
		
		Person person = service.getPerson(personName);
		Event event = service.getEvent(eventName);
		
		Registration registration = service.getRegistrationByPersonAndEvent(person, event);
		
		CreditCard creditCard = service.createCreditCardPayment(accountNumber, amount);
		
		service.pay(registration, creditCard);
		
		PersonDto personDto = convertToDto(person);
		
		return personDto;
		
	}
	
	
	/*** GET MAPPINGS ***/
	
	@GetMapping(value = {"/persons/{name}", "/persons/{name}/"})
	public PersonDto getPersonByName(@PathVariable("name") String name) throws IllegalArgumentException {
		
		return convertToDto(service.getPerson(name));
		
	}
	
	@GetMapping(value = {"/persons", "/persons/"})
	public List<PersonDto> getAllPersons() {
		
		List<PersonDto> persons = new ArrayList<>();
		for (Person person : service.getAllPersons()) {
			persons.add(convertToDto(person));
		}
		
		return persons;
	}
	
	@GetMapping(value = {"/organizers/{name}", "/organizers/{name}/"})
	public OrganizerDto getOrganizerByName(@PathVariable("name") String name) throws IllegalArgumentException {
		
		return convertToDto(service.getOrganizer(name));
		
	}
	
	@GetMapping(value = {"/organizers", "/organizers/"})
	public List<OrganizerDto> getAllOrganizers() {
		
		List<OrganizerDto> organizers = new ArrayList<>();
		for (Organizer organizer : service.getAllOrganizers()) {
			organizers.add(convertToDto(organizer));
		}
		
		return organizers;
		
	}
	
	@GetMapping(value = {"/events/{name}", "/events/{name}/"})
	public EventDto getEventByName(@PathVariable("name") String name) throws IllegalArgumentException {
		
		return convertToDto(service.getEvent(name));
		
	}
	
	@GetMapping(value = {"/events", "/events/"})
	public List<EventDto> getAllEvents() {
		
		List<EventDto> eventDtos = new ArrayList<>();
		for (Event event : service.getAllEvents()) {
			
				eventDtos.add(convertToDto(event));
		}
		
		return eventDtos;
		
	}

	@GetMapping(value = { "/events/person/{name}", "/events/person/{name}/" })
	public List<EventDto> getEventsOfPerson(@PathVariable("name") PersonDto personDto) {
		
		Person person = convertToDomainObject(personDto);
		
		return createAttendedEventDtosForPerson(person);
	}
	
	@GetMapping(value = {"/registrations", "/registrations/"})
	public RegistrationDto getRegistration(@RequestParam(name = "person") String personName,
										   @RequestParam(name = "event") String eventName)
												   throws IllegalArgumentException {
		
		Person person = service.getPerson(personName);
		Event event = service.getEvent(eventName);
		
		Registration registration = service.getRegistrationByPersonAndEvent(person, event);
		
		return convertToDtoWithoutPerson(registration);
		
	}

	@GetMapping(value = {"/registrations/person/{name}", "/registrations/person/{name}/"})
	public List<RegistrationDto> getRegistrationsForPerson(@PathVariable("name") PersonDto personDto) throws IllegalArgumentException {
		
		Person person = service.getPerson(personDto.getName());
		
		return createRegistrationDtosForPerson(person);
		
	}
	
	@GetMapping(value = {"/registrations/creditCard", "/registrations/creditCard/"})
	public CreditCardDto getCreditCardForRegistration(@RequestParam(name = "person") String personName,
													  @RequestParam(name = "event") String eventName)
															  throws IllegalArgumentException {
		Person person = service.getPerson(personName);
		Event event = service.getEvent(eventName);
		
		Registration registration = service.getRegistrationByPersonAndEvent(person, event);
		
		CreditCard creditCard = registration.getCreditCard();
		
		return convertToDto(creditCard);
		
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