package com.luv2code.springboot.todos.controller;

import com.luv2code.springboot.todos.request.ToDoRequest;
import com.luv2code.springboot.todos.response.ToDoResponse;
import com.luv2code.springboot.todos.service.ToDoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "To do REST API Endpoints", description = "Operations for managing user todos")
@RestController
@RequestMapping("/api/todos")
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @Operation(summary = "Create to do for user", description = "Create todo for the signed in user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ToDoResponse createToDo(@Valid @RequestBody ToDoRequest toDoRequest) {
        return toDoService.createToDo(toDoRequest);
    }
}
