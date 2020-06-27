package com.example.EventRegistration.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CreditCard {
	
	private String accountNumber;
	
	@Id
	public String getAccountNumber() {
		return this.accountNumber;
	}
	
	public void setAccountNumber(String value) {
		this.accountNumber = value;
	}
	
	
	
	private float amount;
	
	public float getAmount() {
		return this.amount;
	}
	
	public void setAmount(float value) {
		this.amount = value;
	}
	
}