package com.example.EventRegistration.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.EventRegistration.dao.CreditCardRepository;
import com.example.EventRegistration.dao.EventRepository;
import com.example.EventRegistration.dao.OrganizerRepository;
import com.example.EventRegistration.dao.PersonRepository;
import com.example.EventRegistration.dao.RegistrationRepository;
import com.example.EventRegistration.model.CreditCard;
import com.example.EventRegistration.model.Event;
import com.example.EventRegistration.model.Person;
import com.example.EventRegistration.model.Registration;
import com.example.EventRegistration.service.EventRegistrationService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestCreditCard {
	
	@Autowired
	private EventRegistrationService service;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private OrganizerRepository organizerRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private RegistrationRepository registrationRepository;
	
	@Autowired
	private CreditCardRepository creditCardRepository;
	
	@AfterEach
	public void clearDatabase() {
		registrationRepository.deleteAll();
		personRepository.deleteAll();
		organizerRepository.deleteAll();
		eventRepository.deleteAll();
		creditCardRepository.deleteAll();
	}
	
	@Test
	public void test_01_createCreditCard() {
		
		assertEquals(0, creditCardRepository.count());
		
		try {
			service.createCreditCardPayment("1234-5678-9123-4567", 100);
		} catch(IllegalArgumentException e) {
			fail();
		}
		
		assertEquals(1, creditCardRepository.count());
		
	}
	
	@Test
	public void test_02_createCreditCard_Null() {
		
		assertEquals(0, creditCardRepository.count());
		String error = "";
		
		try {
			service.createCreditCardPayment(null, 100);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(0, creditCardRepository.count());
		assertEquals("Account number cannot be empty", error);
		
	}
	
	@Test
	public void test_03_createCreditCard_Duplicate() {
		
		assertEquals(0, creditCardRepository.count());
		String error = "";
		
		try {
			service.createCreditCardPayment("1234-5678-9123-4567", 100);
			service.createCreditCardPayment("1234-5678-9123-4567", 100);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(1, creditCardRepository.count());
		assertEquals("Account number already exists", error);
		
	}
	
	@Test
	public void test_04_createCreditCard_InvalidFormat() {
		
		assertEquals(0, creditCardRepository.count());
		String error = "";
		
		try {
			service.createCreditCardPayment("123-568-913-457", 100);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(0, creditCardRepository.count());
		assertEquals("Account number has wrong format", error);
		
	}
	
	@Test
	public void test_05_createCreditCard_ZeroAmount() {
		
		assertEquals(0, creditCardRepository.count());
		String error = "";
		
		try {
			service.createCreditCardPayment("1234-5678-9123-4567", 0);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(0, creditCardRepository.count());
		assertEquals("Payment amount cannot be 0 or negative", error);
		
	}
	
	@Test
	public void test_06_createCreditCard_NegativeAmount() {
		
		assertEquals(0, creditCardRepository.count());
		String error = "";
		
		try {
			service.createCreditCardPayment("1234-5678-9123-4567", -100);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(0, creditCardRepository.count());
		assertEquals("Payment amount cannot be 0 or negative", error);
		
	}
	
	@Test
	public void test_07_pay() {
		
		Person person = TestUtils.setUpPerson(service, "FirstName LastName");
		Event event = TestUtils.setUpEvent(service, "Event Name");
		Registration registration = TestUtils.register(service, person, event);
		CreditCard creditCard = service.createCreditCardPayment("1234-5678-9123-4567", 100);
		
		try {
			service.pay(registration, creditCard);
		} catch(IllegalArgumentException e) {
			fail();
		}
		
		List<Registration> registrations = service.getAllRegistrations();
		
		assertEquals(1, service.getAllRegistrations().size());
		assertEquals(creditCard.getAccountNumber(), registrations.get(0).getCreditCard().getAccountNumber());
		
	}
	
	@Test
	public void test_08_pay_NullRegistration() {
		
		String error = "";
		
		CreditCard creditCard = service.createCreditCardPayment("1234-5678-9123-4567", 100);
		
		try {
			service.pay(null, creditCard);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Registration cannot be empty", error);
		
	}
	
	@Test
	public void test_09_pay_NonExistentRegistration() {
		
		String error = "";
	
		Registration registration = new Registration();
		
		CreditCard creditCard = service.createCreditCardPayment("1234-5678-9123-4567", 100);
		
		try {
			service.pay(registration, creditCard);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Registration does not exist", error);
		
	}
	
	@Test
	public void test_10_pay_NullCreditCard() {
		
		String error = "";
		
		Person person = TestUtils.setUpPerson(service, "FirstName LastName");
		Event event = TestUtils.setUpEvent(service, "Event Name");
		Registration registration = TestUtils.register(service, person, event);
		CreditCard creditCard = null;
		
		try {
			service.pay(registration, creditCard);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Credit card cannot be empty", error);
		
	}
	
	@Test
	public void test_11_pay_NonExistentCreditCard() {
		
		String error = "";
		
		Person person = TestUtils.setUpPerson(service, "FirstName LastName");
		Event event = TestUtils.setUpEvent(service, "Event Name");
		Registration registration = TestUtils.register(service, person, event);
		CreditCard creditCard = new CreditCard();
		creditCard.setAccountNumber("1234-5678-9123-4567");
		
		try {
			service.pay(registration, creditCard);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Credit card does not exist", error);
		
	}

}