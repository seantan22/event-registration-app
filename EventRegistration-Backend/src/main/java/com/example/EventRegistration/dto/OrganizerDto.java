package com.example.EventRegistration.dto;

import java.util.Collections;
import java.util.List;

public class OrganizerDto {
	
	private String name;
	private List<EventDto> organizedEvents;
	
	public OrganizerDto() {
	}
	
	@SuppressWarnings("unchecked")
	public OrganizerDto(String name) {
		this.name = name;
		this.organizedEvents = Collections.EMPTY_LIST;
	}
	
	public OrganizerDto(String name, List<EventDto> events) {
		this.name = name;
		this.organizedEvents = events;
	}
	
	public String getName() {
		return name;
	}
	
	public List<EventDto> getOrganizedEvents() {
		return organizedEvents;
	}

	public void setOrganizedEvents(List<EventDto> organizedEvents) {
		this.organizedEvents = organizedEvents;
	}
	
}
