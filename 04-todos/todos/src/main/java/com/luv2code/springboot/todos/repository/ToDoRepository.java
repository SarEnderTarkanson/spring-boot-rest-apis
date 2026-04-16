package com.luv2code.springboot.todos.repository;

import com.luv2code.springboot.todos.entity.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends CrudRepository<Todo, Long> {
}
