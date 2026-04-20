package com.luv2code.springboot.todos.service;

import com.luv2code.springboot.todos.entity.Todo;
import com.luv2code.springboot.todos.entity.User;
import com.luv2code.springboot.todos.repository.TodoRepository;
import com.luv2code.springboot.todos.request.ToDoRequest;
import com.luv2code.springboot.todos.response.TodoResponse;
import com.luv2code.springboot.todos.util.FindAuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository toDoRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;

    public TodoServiceImpl(TodoRepository toDoRepository, FindAuthenticatedUser findAuthenticatedUser) {
        this.toDoRepository = toDoRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getAllToDos() {
        User currentUser = findAuthenticatedUser.getAuthenticatedUser();
        return toDoRepository.findByOwner(currentUser)
                .stream()
                .map(this::convertTodoResponse)
                .toList();
    }

    @Override
    @Transactional
    public TodoResponse createToDo(ToDoRequest toDoRequest) {

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

    @Override
    @Transactional
    public TodoResponse toggleTodoCompletion(long id) {

        User currentUser = findAuthenticatedUser.getAuthenticatedUser();

        Optional<Todo> todo = toDoRepository.findByIdAndOwner(id, currentUser);

        if (todo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }

        todo.get().setComplete(!todo.get().isComplete());

        Todo updatedTodo = toDoRepository.save(todo.get());

        return convertTodoResponse(updatedTodo);
    }

    @Override
    @Transactional
    public void deleteToDo(long id) {

        User currentUser = findAuthenticatedUser.getAuthenticatedUser();

        Optional<Todo> todo = toDoRepository.findByIdAndOwner(id, currentUser);

        if (todo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }

        toDoRepository.delete(todo.get());

    }

    private TodoResponse convertTodoResponse(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getPriority(),
                todo.isComplete()
        );
    }
}
