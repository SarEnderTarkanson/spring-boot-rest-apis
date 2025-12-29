package com.luv2code.books.controller;

import com.luv2code.books.entity.Book;
import com.luv2code.books.exception.BookErrorResponse;
import com.luv2code.books.exception.BookNotFoundException;
import com.luv2code.books.request.BookRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Books REST API endpoints", description = "Operations related to books")
@RestController
@RequestMapping("/api/books")
@Validated
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController() {
        initializeBooks();
    }

    private void initializeBooks() {
        books.addAll(List.of(
                new Book(1, "Computer Science Pro", "Chad Darby", "Computer Science", 5),
                new Book(2, "Java Spring Master", "Eric Roby", "Computer Science", 5),
                new Book(3, "Why 1+1 rocks?", "Adil A.", "Math", 1),
                new Book(4, "How bears hibernate", "Bob B.", "Science", 2),
                new Book(5, "A Pirate's treasure", "Curt C.", "History", 3),
                new Book(6, "Why 2+2 is better", "Dan D.", "Computer Science", 1)
        ));
    }

    @Operation(summary = "Get all books", description = "Retrieve a list of all available books")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Book> getBooks(@Parameter(description = "Optional query parameter") @RequestParam(required = false) String category) {

        if (category == null) {
            return books;

        }

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();

    }

    @Operation(summary = "Get a book by id", description = "Retrieve a specific book by its id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Book getBookById(@Parameter(description = "Id of the book to be retrieved") @PathVariable @Min(value = 0) long id) {

        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(
                        () -> new BookNotFoundException("Book not found - " + id)
                );
    }

    @Operation(summary = "Create a new book", description = "Create a new book and add it to the list")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createBook(@Valid @RequestBody BookRequest bookRequest) {

        long id = books.isEmpty() ? 1 : books.getLast().getId() + 1;

        Book book = convertToBook(id, bookRequest);

        books.add(book);

    }

    @Operation(summary = "Update a book", description = "Update the details of an existing book")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateBook(@Parameter(description = "Id of the the book to be updated") @PathVariable @Min(value = 0) long id, @Valid @RequestBody BookRequest bookRequest) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                Book updatedBook = convertToBook(id, bookRequest);
                books.set(i, updatedBook);
                return;
            }
        }
    }

    @Operation(summary = "Delete a book", description = "Remove a book from the list")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@Parameter(description = "Id of the the book to be deleted") @PathVariable @Min(value = 0) long id) {
        books.removeIf(book -> book.getId() == id);
    }

    private Book convertToBook(long id, BookRequest bookRequest) {
        return new Book(
                id,
                bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getCategory(),
                bookRequest.getRating()
        );
    }

    @ExceptionHandler
    public ResponseEntity<BookErrorResponse> handleException(BookNotFoundException exc) {
        BookErrorResponse bookErrorResponse = new BookErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(bookErrorResponse, HttpStatus.NOT_FOUND);
    }

}
