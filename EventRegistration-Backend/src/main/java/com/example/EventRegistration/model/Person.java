package com.example.EventRegistration.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Person {
	
	private String name;
	
	@Id
	public String getName() {
		return this.name;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
}