package com.example.EventRegistration.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.EventRegistration.model.Event;

public interface EventRepository extends CrudRepository<Event, String> {
	
	Event findByName(String name);
	
}