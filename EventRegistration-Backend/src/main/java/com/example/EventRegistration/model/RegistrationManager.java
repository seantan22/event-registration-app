package com.example.EventRegistration.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class RegistrationManager {
	
	private int id;
	
	@Id
	public int getId() {
		return this.id;
	}
	
	public void setId(int value) {
		this.id = value;
	}
	
	private Set<Person> persons;
	
	@OneToMany(cascade = CascadeType.ALL)
	public Set<Person> getPersons() {
		return this.persons;
	}
	
	public void setPersons(Set<Person> personss) {
		this.persons = personss;
	}
	
	
	private Set<Event> events;
	
	@OneToMany(cascade = CascadeType.ALL)
	public Set<Event> getEvents() {
		return this.events;
	}
	
	public void setEvents(Set<Event> eventss) {
		this.events = eventss;
	}
	
	
	private Set<CreditCard> creditCard;
	
	@OneToMany(cascade = CascadeType.ALL)
	public Set<CreditCard> getCreditCard() {
		return this.creditCard;
	}
	
	public void setCreditCard(Set<CreditCard> creditCard) {
		this.creditCard = creditCard;
	}
	
}