package com.example.EventRegistration.dto;

public class CreditCardDto {
	
	private String accountNumber;
	private int amount;
	
	public CreditCardDto() {
	}
	
	public CreditCardDto(String accountNumber, int amount) {
		this.accountNumber = accountNumber;
		this.amount = amount;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}
	
}