package com.example.EventRegistration.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.EventRegistration.model.CreditCard;

public interface CreditCardRepository extends CrudRepository<CreditCard, String> {
	
//	CreditCard findByAccountNumber(String accountNumber);
	
}

