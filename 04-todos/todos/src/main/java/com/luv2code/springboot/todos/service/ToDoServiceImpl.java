package com.luv2code.springboot.todos.service;

import com.luv2code.springboot.todos.entity.Todo;
import com.luv2code.springboot.todos.entity.User;
import com.luv2code.springboot.todos.repository.ToDoRepository;
import com.luv2code.springboot.todos.request.ToDoRequest;
import com.luv2code.springboot.todos.response.ToDoResponse;
import com.luv2code.springboot.todos.util.FindAuthenticatedUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository toDoRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;

    public ToDoServiceImpl(ToDoRepository toDoRepository, FindAuthenticatedUser findAuthenticatedUser) {
        this.toDoRepository = toDoRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
    }

    @Override
    @Transactional
    public ToDoResponse createToDo(ToDoRequest toDoRequest) {

        User currentUser = findAuthenticatedUser.getAuthenticatedUser();

        Todo todo = new Todo(
                toDoRequest.getTitle(),
                toDoRequest.getDescription(),
                toDoRequest.getPriority(),
                false,
                currentUser
        );

        Todo savedTodo = toDoRepository.save(todo);

        ToDoResponse toDoResponse = new ToDoResponse(
                savedTodo.getId(),
                savedTodo.getTitle(),
                savedTodo.getDescription(),
                savedTodo.getPriority(),
                savedTodo.isComplete()
        );

        return toDoResponse;
    }
}
