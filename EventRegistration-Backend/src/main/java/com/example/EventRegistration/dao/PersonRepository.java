package com.example.EventRegistration.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.EventRegistration.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, String>{
	
	Person findByName(String name);
	
}