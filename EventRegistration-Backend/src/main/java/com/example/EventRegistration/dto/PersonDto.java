package com.example.EventRegistration.dto;

import java.util.Collections;
import java.util.List;

public class PersonDto {

	private String name;
	private List<EventDto> eventsAttended;

	public PersonDto() {
	}

	@SuppressWarnings("unchecked")
	public PersonDto(String name) {
		this(name, Collections.EMPTY_LIST);
	}

	public PersonDto(String name, List<EventDto> events) {
		this.name = name;
		this.eventsAttended = events;
	}

	public String getName() {
		return name;
	}

	public List<EventDto> getEventsAttended() {
		return eventsAttended;
	}

	public void setEventsAttended(List<EventDto> events) {
		this.eventsAttended = events;
	}
}