package com.luv2code.springboot.todos.service;

import com.luv2code.springboot.todos.request.ToDoRequest;
import com.luv2code.springboot.todos.response.TodoResponse;

import java.util.List;

public interface TodoService {

    List<TodoResponse> getAllToDos();

    TodoResponse createToDo(ToDoRequest toDoRequest);

    TodoResponse toggleTodoCompletion(long id);

    void deleteToDo(long id);
}
