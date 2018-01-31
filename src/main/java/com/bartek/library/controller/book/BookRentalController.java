package com.bartek.library.controller.book;

import com.bartek.library.model.book.Book;
import com.bartek.library.model.book.BookRental;
import com.bartek.library.service.book.rental.RentBookService;
import com.bartek.library.service.book.rental.ReturnBookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rental")
public class BookRentalController {

    private ReturnBookService returnBookService;
    private RentBookService rentBookService;

    @Autowired
    BookRentalController(ReturnBookService returnBookService,
                         RentBookService rentBookService) {
        this.returnBookService = returnBookService;
        this.rentBookService = rentBookService;
    }

    @ApiOperation(value = "/rent", notes = "Possibility to rent a book from library, by it's id")
    @PostMapping("/rent")
    BookRental rentBook(@RequestParam Long id) {
        return rentBookService.rentBook(id);
    }

    @ApiOperation(value = "/return", notes = "Possibility to return book, by it's id")
    @PostMapping("/return")
    Book returnBook(@RequestParam Long id) {
        return returnBookService.returnBook(id);
    }
}
