package com.omgogte.todoapp.repository;
import com.omgogte.todoapp.models.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Integer>{

}
