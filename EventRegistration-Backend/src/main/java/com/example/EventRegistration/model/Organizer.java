package com.example.EventRegistration.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;


@Entity
public class Organizer extends Person {
	
	private Set<Event> organizedEvents;
	
	@ManyToMany
	public Set<Event> getOrganizes() {
		return this.organizedEvents;
	}
	
	public void setOrganizes(Set<Event> organizedEvents) {
		this.organizedEvents = organizedEvents;
	}
	
}
