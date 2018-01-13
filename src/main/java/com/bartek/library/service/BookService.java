package com.bartek.library.service;

import com.bartek.library.model.Book;
import com.bartek.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService{

    private BookRepository bookRepository;

    @Autowired
    BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public List<Book> findBooksByAuthor(String author) {
        return bookRepository.findByAuthorIgnoreCaseContaining(author);
    }

    public List<Book> findBooksByCategory(String category) {
        return bookRepository.findByCategoryIgnoreCaseContaining(category);
    }

    public List<Book> findBooksByTitle(String title) {
        return bookRepository.findByTitleIgnoreCaseContaining(title);
    }

    public List<Book> findAllBooks() {
        List<Book> allFoundBooks = new ArrayList<>();

        bookRepository
                .findAll()
                .forEach(allFoundBooks::add);

        return allFoundBooks;
    }
}
