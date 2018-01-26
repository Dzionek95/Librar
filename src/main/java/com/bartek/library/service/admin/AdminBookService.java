package com.bartek.library.service.admin;

import com.bartek.library.model.book.Book;
import com.bartek.library.repository.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminBookService {

    private BookRepository bookRepository;

    @Autowired
    AdminBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(Book book) {
       return bookRepository.save(book);
    }

    public void deleteBookById(Long idOfBook) {
        bookRepository.delete(idOfBook);
    }

    public Book updateBooks(Book book) {
       return bookRepository.save(book);
    }
}
