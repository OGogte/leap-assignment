package com.omgogte.todoapp.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="person")

public class Person {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name="email")
	private String email;

	 @OneToMany(cascade = CascadeType.ALL)
	    @JoinColumn(name = "person_id", referencedColumnName = "id")
	    private List<Todo> todos;

	@Column(name = "password")
	private String password;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Todo> getTodos() {
		return todos;
	}

	public void setTodos(List<Todo> todos) {
		this.todos = todos;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Iterable<Todo> getTodosBeforeDate(Date due_date) {
		ArrayList<Todo> todosBeforeDate = new ArrayList<>();
		for ( Todo todo: this.todos) {
			if(todo.getDue_date().before(due_date)) {
				todosBeforeDate.add(todo);
			}
		}
		return todosBeforeDate;
	}
	
	@Override
	public String toString() {
		return "Person ID: " + id + " Name: " + name;
	}
	
}
