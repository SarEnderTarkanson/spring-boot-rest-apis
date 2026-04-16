package com.luv2code.springboot.todos.service;

import com.luv2code.springboot.todos.request.ToDoRequest;
import com.luv2code.springboot.todos.response.ToDoResponse;

public interface ToDoService {
    ToDoResponse createToDo(ToDoRequest toDoRequest);
}
