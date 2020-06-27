package com.example.EventRegistration.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.EventRegistration.model.Organizer;

public interface OrganizerRepository extends CrudRepository<Organizer, String> {
	
	Organizer findByName(String name);
	
}