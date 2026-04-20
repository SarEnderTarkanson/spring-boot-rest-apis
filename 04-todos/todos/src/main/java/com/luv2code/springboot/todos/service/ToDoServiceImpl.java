package com.luv2code.springboot.todos.service;

import com.luv2code.springboot.todos.entity.Todo;
import com.luv2code.springboot.todos.entity.User;
import com.luv2code.springboot.todos.repository.ToDoRepository;
import com.luv2code.springboot.todos.request.ToDoRequest;
import com.luv2code.springboot.todos.response.ToDoResponse;
import com.luv2code.springboot.todos.util.FindAuthenticatedUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository toDoRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;

    public ToDoServiceImpl(ToDoRepository toDoRepository, FindAuthenticatedUser findAuthenticatedUser) {
        this.toDoRepository = toDoRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ToDoResponse> getAllToDos() {
        User currentUser = findAuthenticatedUser.getAuthenticatedUser();
        return toDoRepository.findByOwner(currentUser)
                .stream()
                .map(this::convertTodoResponse)
                .toList();
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

        return convertTodoResponse(savedTodo);
    }

    private ToDoResponse convertTodoResponse(Todo todo) {
        return new ToDoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getPriority(),
                todo.isComplete()
        );
    }
}
