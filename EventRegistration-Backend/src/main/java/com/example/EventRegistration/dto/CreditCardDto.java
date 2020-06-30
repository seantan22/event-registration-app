package com.example.EventRegistration.dto;

public class CreditCardDto {
	
	private String accountNumber;
	private float amount;
	
	public CreditCardDto() {
	}
	
	public CreditCardDto(String accountNumber, float amount) {
		this.accountNumber = accountNumber;
		this.amount = amount;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	public float getAmount() {
		return amount;
	}
	
}