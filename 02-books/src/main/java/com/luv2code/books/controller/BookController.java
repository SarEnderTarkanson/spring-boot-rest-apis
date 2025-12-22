package com.luv2code.books.controller;

import com.luv2code.books.entity.Book;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
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
                new Book(1, "A Pirate's treasure", "Curt C.", "History", 3),
                new Book(1, "Why 2+2 is better", "Dan D.", "Computer Science", 1)
        ));
    }

    @GetMapping
    public List<Book> getSomeBook(@RequestParam(required = false) String category) {

        if (category == null) {
            return books;

        }

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();

    }


    @GetMapping("/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public void createBook(@RequestBody Book newbook) {

        boolean isNewBook = books.stream()
                .noneMatch(book -> book.getTitle().equalsIgnoreCase(newbook.getTitle()));

        if (isNewBook) {
            books.add(newbook);
        }

    }

    @PutMapping("/{title}")
    public void updateBook(@PathVariable String title, @RequestBody Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getTitle().equalsIgnoreCase(title)) {
                books.set(i, updatedBook);
                return;
            }
        }
    }

    @DeleteMapping("/{title}")
    public void deleteBook(@PathVariable String title) {
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }
}
