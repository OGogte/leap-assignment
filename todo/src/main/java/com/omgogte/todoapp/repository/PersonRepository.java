package com.omgogte.todoapp.repository;
import com.omgogte.todoapp.models.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Integer>{

}
