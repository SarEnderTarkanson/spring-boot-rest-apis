package com.luv2code.books.controller;

import com.luv2code.books.entity.Book;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController() {
        initializeBooks();
    }

    private void initializeBooks() {
        books.addAll(List.of(
                new Book("Title One", "Author One", "Science"),
                new Book("Title Two", "Author Two", "History"),
                new Book("Title Three", "Author Three", "Math"),
                new Book("Title Four", "Author Four", "Science"),
                new Book("Title Five", "Author Five", "History"),
                new Book("Title Six", "Author Six", "Math")
        ));
    }

    @GetMapping("/api/books")
    public List<Book> getSomeBook(@RequestParam(required = false) String category) {

        if (category == null) {
            return books;

        }

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();

    }


    @GetMapping("api/books/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/api/books")
    public void createBook(@RequestBody Book newbook) {

        boolean isNewBook = books.stream()
                .noneMatch(book -> book.getTitle().equalsIgnoreCase(newbook.getTitle()));

        if (isNewBook) {
            books.add(newbook);
        }


    }

}
