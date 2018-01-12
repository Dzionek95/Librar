package com.aldona.library.controller;

import com.aldona.library.model.Book;
import com.aldona.library.model.BookRental;
import com.aldona.library.service.BookRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.aldona.library.controller.BookRentalController.RENTAL_PATH;

@RestController
@RequestMapping(RENTAL_PATH)
public class BookRentalController {

    final static String RENTAL_PATH = "/rental";

    private BookRentalService bookRentalService;

    @Autowired
    BookRentalController(BookRentalService bookRentalService){
        this.bookRentalService = bookRentalService;
    }

    @PostMapping("/rent")
    BookRental rentBook(@RequestParam Long idOfBook){
        return bookRentalService.rentBook(idOfBook);
    }

    @PostMapping("/return")
    Book returnBook(@RequestParam Long idOfBook){
        return bookRentalService.returnBook(idOfBook);
    }
}
