package com.omgogte.todoapp.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.omgogte.todoapp.models.Person;
import com.omgogte.todoapp.models.Todo;
import com.omgogte.todoapp.repository.PersonRepository;

@RequestMapping("api/person")
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RestController
public class PersonController {

    private final PersonRepository personRepository;
    
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    
    @GetMapping
    public Iterable<Person> getAllPeople() {
        return this.personRepository.findAll();
    }

    
    @GetMapping(path = "/{id}")
    public Optional<Person> getPersonById(@PathVariable Integer id) {
        return this.personRepository.findById(id);
    }

    
    @GetMapping(path = "/{id}/todos")
    public Iterable<Todo> getTodosForPerson(@PathVariable Integer id) {
        Optional<Person> personOptional = this.personRepository.findById(id);
        if (!personOptional.isPresent()) {
            System.out.println("Person not found");
            return null;
        } else {
            Person personChoice = personOptional.get();
            return personChoice.getTodos();
        }
    }

    
    @GetMapping(path = "/{id}/todos/date")
    public Iterable<Todo> getTodosBeforeDate(@PathVariable Integer id, @RequestParam(name = "due_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date due_date) {
        Optional<Person> personOptional = this.personRepository.findById(id);
        if (!personOptional.isPresent()) {
            System.out.println("Person not found");
            return null;
        } else {
            Person personChoice = personOptional.get();
            return personChoice.getTodosBeforeDate(due_date);
        }
    }

    
    @GetMapping(path = "/login")
    public Person loginUser(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        ArrayList<Person> users = (ArrayList<Person>) this.personRepository.findAll();
        for (Person person : users) {
            System.out.println(person.toString());
            if (person.getEmail().equals(email)) {
                if (person.getPassword().equals(password)) {
                    System.out.println("Login successful");
                    return person;
                } else {
                    System.out.println("Wrong password");
                    return null;
                }
             }
        }
        System.out.println("Person Not found");
        return null;
    }

    
    @PostMapping
    public Person addPerson(@RequestBody Person person) {
        return this.personRepository.save(person);
    }

    
    @PostMapping(path = "/register")
    public Person registerUser(@RequestParam(name = "name") String name, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        Person person = new Person();
        person.setName(name);
        person.setEmail(email);
        person.setPassword(password);
        return this.personRepository.save(person);
    }

    
    @PutMapping(path = "/{id}")
    public Person updatePerson(@RequestBody Person person, @PathVariable Integer id) {
        Optional<Person> personToUpdateOptional = this.personRepository.findById(id);
        if (personToUpdateOptional.isPresent()) {
            Person personUpdate = personToUpdateOptional.get();
            if (person.getName() != null) {
                personUpdate.setName(person.getName());
            }
            if (person.getEmail() != null) {
                personUpdate.setEmail(person.getEmail());
            }
            if (person.getPassword() != null) {
                personUpdate.setPassword(person.getPassword());
            }
            return this.personRepository.save(personUpdate);
        } else {
            System.out.println("Could not locate person");
            return null;
        }
    }

    
    @DeleteMapping(path = "/{id}")
    public Person deletePerson(@PathVariable Integer id) {
        Optional<Person> personToDelete = this.personRepository.findById(id);
        if (personToDelete.isPresent()) {
            this.personRepository.delete(personToDelete.get());
            return personToDelete.get();
        } else {
            System.out.println("Person does not exist");
            return null;
        }
    }
}