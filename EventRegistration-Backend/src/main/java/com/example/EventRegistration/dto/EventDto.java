package com.example.EventRegistration.dto;

import java.sql.Time;
import java.sql.Date;

public class EventDto {
	
	private String name;
	private Date date;
	private Time startTime;
	private Time endTime;
	private String description;
	private CreditCardDto creditCardDto;
	
	public EventDto() {
	}
	
	public EventDto(String name) {
		this.name = name;
		this.date = Date.valueOf("1971-01-01");
		this.startTime = Time.valueOf("00:00:00");
		this.endTime = Time.valueOf("23:59:59");
		this.description = "--";
	}
	
	public EventDto(String name, Date date, Time startTime, Time endTime, String description) {
		this.name = name;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public Date getDate() {
		return date;
	}

	public Time getStartTime() {
		return startTime;
	}

	public Time getEndTime() {
		return endTime;
	}
	
	public String getDescription() {
		return description;
	}
	
	public CreditCardDto getCreditCard(){
		return creditCardDto;
	}
	
	public void setCreditCard(CreditCardDto creditCardDto) {
		this.creditCardDto = creditCardDto;
	}
	
}
