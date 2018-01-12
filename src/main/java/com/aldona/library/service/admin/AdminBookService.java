package com.aldona.library.service.admin;

import com.aldona.library.model.Book;
import com.aldona.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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

    public void deleteBookById(Long id) {
        bookRepository.delete(id);
    }

    public Book updateBooks(Book book) {
       return bookRepository.save(book);
    }
}
