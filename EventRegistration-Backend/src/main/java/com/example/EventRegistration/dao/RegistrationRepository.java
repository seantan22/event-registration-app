package com.example.EventRegistration.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.EventRegistration.model.Event;
import com.example.EventRegistration.model.Person;
import com.example.EventRegistration.model.Registration;

public interface RegistrationRepository extends CrudRepository<Registration, String> {
	
	List<Registration> findByPerson(Person person);

	boolean existsByPersonAndEvent(Person person, Event event);

	Registration findByPersonAndEvent(Person person, Event event);
	
}