package com.example.EventRegistration.model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="event")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType=DiscriminatorType.STRING, name="TYPE")
public class Event {
	
	private String name;
	
	@Id
	public String getName() {
		return this.name;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	
	private Date date;
	
	public Date getDate() {
		return this.date;
	}
	
	public void setDate(Date value) {
		this.date = value;
	}
	
	
	private Time startTime;
	
	public Time getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(Time value) {
		this.startTime = value;
	}
	
	
	private Time endTime;
	
	public Time getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Time value) {
		this.endTime = value;
	}
	
	
	private String description;
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String value) {
		this.description = value;
	}
	
	
	private int numOfParticipants;
	
	public int getNumOfParticipants() {
		return this.numOfParticipants;
	}
	
	public void setNumOfParticipants(int value) {
		this.numOfParticipants = value;
	}
	
	
	private List<Organizer> participants;
	
	@ManyToMany
	public List<Organizer> getParticipants() {
		return this.participants;
	}
	
	public void setParticipants(List<Organizer> participants) {
		this.participants = participants;
	}
	
}