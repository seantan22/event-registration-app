package com.example.EventRegistration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.EventRegistration.dao.PersonRepository;
import com.example.EventRegistration.model.Person;
import com.example.EventRegistration.service.EventRegistrationService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestPerson {

	@Autowired
	private EventRegistrationService service;
	
	@Autowired
	private PersonRepository personRepository;
	
	@AfterEach
	public void clearDatabase() {
		personRepository.deleteAll();
	}
	
    @Test
    public void test_01_createPerson() {
    	
    	assertEquals(0, service.getAllPersons().size());
        String name = "FirstName LastName";   
        
        try {        	
            service.createPerson(name);
        } catch (IllegalArgumentException e) {
            fail();
        }
        
        List<Person> persons = service.getAllPersons();
        assertEquals(1, persons.size());
        assertEquals(name, persons.get(0).getName());
    }
    
    @Test
    public void test_02_createPerson_Null() {
    	
    	assertEquals(0, service.getAllPersons().size());
        String name = null;
        String error = null;
        
        try {
            service.createPerson(name);
        } catch (IllegalArgumentException e) {
        	error = e.getMessage();
        }
        
        List<Person> persons = service.getAllPersons();
        assertEquals("Person name cannot be empty", error);
        assertEquals(0, persons.size());
    }
    
    @Test
    public void test_03_createPerson_Duplicate() {
    	
    	assertEquals(0, service.getAllPersons().size());
        String name = "Duplicate Name";   
        
        try {        	
            service.createPerson(name);
        } catch (IllegalArgumentException e) {
            fail();
        }
        
        List<Person> persons = service.getAllPersons();
        assertEquals(1, persons.size());
        assertEquals(name, persons.get(0).getName());
    	
        String duplicateName = "Duplicate Name";
        String error = null;
        
        try {
            service.createPerson(duplicateName);
        } catch (IllegalArgumentException e) {
        	error = e.getMessage();
        }
        
        persons = service.getAllPersons();
        assertEquals("Person already exists", error);
        assertEquals(1, persons.size());
    }
    
    @Test
    public void test_04_getPerson() {
    	
    	assertEquals(0, service.getAllPersons().size());
    	String name = "FirstName LastName";
    	
    	try {
    		service.createPerson(name);
    		service.getPerson(name);
    	} catch (IllegalArgumentException e) {
    		fail();
    	}
    	
    }
    
    @Test
    public void test_05_getPerson_Null() {
    	
    	assertEquals(0, service.getAllPersons().size());
    	String name = null;
    	String error = null;
    	
    	try {
    		service.getPerson(name);
    		fail();
    	} catch (IllegalArgumentException e) {
    		error = e.getMessage();
    	}
    	
    	assertEquals("Person name cannot be empty", error);
    	
    }
    
    @Test
    public void test_06_getPerson_NonExistent() {
    	
    	assertEquals(0, service.getAllPersons().size());
    	String name = "FirstName LastName";
    	String nonExistentName = "Non Existent Name";
    	String error = "";
    	
    	try {
    		service.createPerson(name);
    		service.getPerson(nonExistentName);
    	} catch (IllegalArgumentException e) {
    		error = e.getMessage();
    	}
    	
    	assertEquals("Person does not exist", error);
    	
    }
    
    @Test
    public void test_07_getAllPersons() {
    	
    	assertEquals(0, service.getAllPersons().size());
    	String nameOne = "Person One"; 
    	String nameTwo = "Person Two"; 
    	
    	try {
    		service.createPerson(nameOne);
    		service.createPerson(nameTwo);
    	} catch (IllegalArgumentException e) {
    		fail();
    	}
    	
    	List<Person> persons = service.getAllPersons();
    	assertEquals(2, persons.size());
    	assertEquals(nameOne, persons.get(0).getName());
    	assertEquals(nameTwo, persons.get(1).getName());
    	
    }
    
    @Test
    public void test_08_deletePerson() {
    	
    	assertEquals(0, service.getAllPersons().size());
        String name = "FirstName LastName";   
        
        try {        	
            service.createPerson(name);
        } catch (IllegalArgumentException e) {
            fail();
        }
        
        List<Person> persons = service.getAllPersons();
        assertEquals(1, persons.size());
        assertEquals(name, persons.get(0).getName());
        
        try {        	
            service.deletePerson(name);
        } catch (IllegalArgumentException e) {
            fail();
        }
        
        persons = service.getAllPersons();
        
        assertEquals(0, persons.size());
          
    }
    
    @Test
    public void test_09_deletePerson_Null() {
    	
    	assertEquals(0, service.getAllPersons().size());
        String name = null; 
        String error = null;
        
        try {        	
            service.deletePerson(name);
            fail();
        } catch (IllegalArgumentException e) {
        	error = e.getMessage();
        }
        
        assertEquals("Person name cannot be empty", error);
          
    }
    
    @Test
    public void test_10_deletePerson_NonExistent() {
    	
    	assertEquals(0, service.getAllPersons().size());
        String name = "Non Existent Name"; 
        String error = null;
        
        try {        	
            service.deletePerson(name);
            fail();
        } catch (IllegalArgumentException e) {
        	error = e.getMessage();
        }
        
        assertEquals("Person does not exist", error);
          
    }
    
}