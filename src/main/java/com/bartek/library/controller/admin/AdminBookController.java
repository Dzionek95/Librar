package com.bartek.library.controller.admin;


import com.bartek.library.model.Book;
import com.bartek.library.service.admin.AdminBookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/book")
public class AdminBookController {

    private AdminBookService adminBookService;

    @Autowired
    AdminBookController(AdminBookService adminBookService) {
        this.adminBookService = adminBookService;
    }

    @ApiOperation(value = "/save", notes = "Possibility to add new book to library")
    @PostMapping("/save")
    Book saveBook(@RequestBody Book book) {
        return adminBookService.saveBook(book);
    }


    @ApiOperation(value = "/delete", notes = "Possibility to remove book from library, by book id")
    @DeleteMapping("/delete")
    void deleteBook(@RequestParam Long id) {
        adminBookService.deleteBookById(id);
    }


    @ApiOperation(value = "/update", notes = "Possibility to update book in library, by it's body")
    @PutMapping("/update")
    Book updateBook(@RequestBody Book book){
        return adminBookService.updateBooks(book);
    }
}
