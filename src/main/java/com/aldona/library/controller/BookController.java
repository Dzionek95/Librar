package com.aldona.library.controller;

import com.aldona.library.model.Book;
import com.aldona.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.aldona.library.controller.BookController.BOOK_PATH;

@RestController
@RequestMapping(BOOK_PATH)
public class BookController {

    static final String BOOK_PATH = "/book";

    private BookService bookService;

    @Autowired
    BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/allbooks")
    List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/byauthor")
    List<Book> findBooksByAuthor(@RequestParam String author){
        return bookService.findBooksByAuthor(author);
    }

    @GetMapping("/bycategory")
    List<Book> findBooksByCategory(@RequestParam String category){
        return bookService.findBooksByCategory(category);
    }

    @GetMapping("/bytitle")
    List<Book> findBooksByTitle(@RequestParam String title){
        return bookService.findBooksByTitle(title);
    }

}
