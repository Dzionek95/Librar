package com.bartek.library.controller;

import com.bartek.library.model.Book;
import com.bartek.library.service.BookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.bartek.library.controller.BookController.BOOK_PATH;

@RestController
@RequestMapping(BOOK_PATH)
public class BookController {

    static final String BOOK_PATH = "/book";

    private BookService bookService;

    @Autowired
    BookController(BookService bookService){
        this.bookService = bookService;
    }

    @ApiOperation(value = "/allbooks", notes = "Displaying all books that are available in library")
    @GetMapping("/allbooks")
    List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }

    @ApiOperation(value = "/byauthor", notes = "Displaying all books that match author signs pattern passed by param")
    @GetMapping("/byauthor")
    List<Book> findBooksByAuthor(@RequestParam String author){
        return bookService.findBooksByAuthor(author);
    }

    @ApiOperation(value = "/bycategory", notes = "Displaying all books that match category pattern passed by param")
    @GetMapping("/bycategory")
    List<Book> findBooksByCategory(@RequestParam String category){
        return bookService.findBooksByCategory(category);
    }

    @ApiOperation(value = "/bytitle", notes = "Displaying all books that match title pattern passed by param")
    @GetMapping("/bytitle")
    List<Book> findBooksByTitle(@RequestParam String title){
        return bookService.findBooksByTitle(title);
    }

}
