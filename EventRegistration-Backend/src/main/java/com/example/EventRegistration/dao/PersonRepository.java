package com.example.EventRegistration.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.EventRegistration.model.Person;

public interface PersonRepository extends CrudRepository<Person, String>{
	
	Person findByName(String name);
	
}