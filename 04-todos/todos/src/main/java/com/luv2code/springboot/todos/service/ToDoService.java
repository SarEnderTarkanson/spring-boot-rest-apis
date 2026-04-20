package com.luv2code.springboot.todos.service;

import com.luv2code.springboot.todos.request.ToDoRequest;
import com.luv2code.springboot.todos.response.ToDoResponse;

import java.util.List;

public interface ToDoService {

    List<ToDoResponse> getAllToDos();

    ToDoResponse createToDo(ToDoRequest toDoRequest);
}
