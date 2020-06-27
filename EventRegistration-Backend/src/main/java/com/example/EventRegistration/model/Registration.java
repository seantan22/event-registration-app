package com.example.EventRegistration.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Registration {
	
	private int id;
	
	@Id
	public int getId() {
		return this.id;
	}
	
	public void setId(int value) {
		this.id = value;
	}
	
	
	private Person person;
	
	@ManyToOne(optional = true, cascade = CascadeType.ALL)
	public Person getPerson() {
		return this.person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	
	private Event event;
	
	@ManyToOne(optional = true, cascade = CascadeType.ALL)
	public Event getEvent() {
		return this.event;
	}
	
	public void setEvent(Event event) {
		this.event = event;
	}
	
	
	private CreditCard payment;
	
	@OneToOne
	public CreditCard getCreditCard() {
		return this.payment;
	}
	
	public void setCreditCard(CreditCard payment) {
		this.payment = payment;
	}
	
}