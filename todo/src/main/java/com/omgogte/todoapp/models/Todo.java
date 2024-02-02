package com.omgogte.todoapp.models;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="todo")

public class Todo {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="due_date")
	private Date due_date;
	
	@Column(name="date_added")
	private Date date_added;
	
	@Column(name="person_id")
	private Integer person_id;

	@Column(name="completed")
	private Boolean isCompleted;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDue_date() {
		return due_date;
	}

	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}

	public Date getDate_added() {
		return date_added;
	}

	public void setDate_added(Date date_added) {
		this.date_added = date_added;
	}

	public Integer getPerson_id() {
		return person_id;
	}

	public void setPerson_id(Integer person_id) {
		this.person_id = person_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String toString() {
		return "Todo: " + title + " due at: " + due_date.toString() + " added at: " + date_added.toString();
	}
}
