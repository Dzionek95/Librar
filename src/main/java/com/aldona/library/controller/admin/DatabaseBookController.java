package com.aldona.library.controller.admin;


import com.aldona.library.model.Book;
import com.aldona.library.service.admin.AdminBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.aldona.library.controller.admin.DatabaseBookController.ADMIN_BOOK_PATH;

@RestController
@RequestMapping(value = ADMIN_BOOK_PATH)
public class DatabaseBookController {

    static final String ADMIN_BOOK_PATH = "/admin/book";

    private AdminBookService adminBookService;

    @Autowired
    DatabaseBookController(AdminBookService adminBookService) {
        this.adminBookService = adminBookService;
    }

    @PostMapping("/savebook")
    Book saveBook(@RequestBody Book book) {
        return adminBookService.saveBook(book);
    }

    @DeleteMapping("/deletebook")
    void deleteBook(@RequestParam Long id) {
        adminBookService.deleteBookById(id);
    }

    @PutMapping("/updatebook")
    Book updateBook(@RequestBody Book book){
        return adminBookService.updateBooks(book);
    }
}
