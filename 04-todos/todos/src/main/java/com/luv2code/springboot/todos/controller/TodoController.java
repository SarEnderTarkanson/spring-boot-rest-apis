package com.luv2code.springboot.todos.controller;

import com.luv2code.springboot.todos.request.ToDoRequest;
import com.luv2code.springboot.todos.response.TodoResponse;
import com.luv2code.springboot.todos.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "To do REST API Endpoints", description = "Operations for managing user todos")
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService toDoService;

    public TodoController(TodoService toDoService) {
        this.toDoService = toDoService;
    }

    @Operation(summary = "Get all todos for the user", description = "Fetch all todos for the signed in user.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TodoResponse> getAllTodos() {
        return toDoService.getAllToDos();
    }

    @Operation(summary = "Create todo for user", description = "Create todo for the signed in user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TodoResponse createToDo(@Valid @RequestBody ToDoRequest toDoRequest) {
        return toDoService.createToDo(toDoRequest);
    }

    @Operation(summary = "Update todo for user", description = "Update todo for the signed in user")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public TodoResponse toggleTodoCompletion(@PathVariable @Min(1) long id) {
        return toDoService.toggleTodoCompletion(id);
    }

    @Operation(summary = "Delete todo for user", description = "Delete todo for the signed in user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteToDo(@PathVariable @Min(1) long id) {
        toDoService.deleteToDo(id);
    }

}
