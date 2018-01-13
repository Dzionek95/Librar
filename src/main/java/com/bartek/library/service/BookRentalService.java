package com.bartek.library.service;


import com.bartek.library.model.Book;
import com.bartek.library.model.BookRental;
import com.bartek.library.model.Queue;
import com.bartek.library.repository.BookRentalRepository;
import com.bartek.library.repository.BookRepository;
import com.bartek.library.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookRentalService {

    private BookRentalRepository rentBookRepository;
    private BookRepository bookRepository;
    private QueueRepository queueRepository;
    private SecurityUtilities securityUtilities;

    @Autowired
    BookRentalService(BookRentalRepository rentBookRepository,
                      BookRepository bookRepository,
                      QueueRepository queueRepository,
                      SecurityUtilities securityUtilities) {
        this.rentBookRepository = rentBookRepository;
        this.bookRepository = bookRepository;
        this.queueRepository = queueRepository;
        this.securityUtilities = securityUtilities;
    }

    public Book returnBook(Long idOfBook) {
        BookRental bookToReturn = rentBookRepository.findOne(idOfBook);
        return updateBookStatus(bookToReturn);
    }

    private Book updateBookStatus(BookRental bookToReturn) {
        Book returningBook = bookToReturn.getBook();
        returningBook.setAvailable(true);
        bookRepository.save(returningBook);
        return returningBook;
    }

    public BookRental rentBook(Long idOfBook) {
        Book bookToRent = bookRepository.findOne(idOfBook);
        BookRental bookRental;
        if (bookToRent.isAvailable()) {
            updateBookAvailabilityAndSaveToDb(bookToRent);
            bookRental = prepareBookRentalData(bookToRent);
            rentBookRepository.save(bookRental);
        } else {
            createPlaceInQueueForUser(bookToRent);
            throw new IllegalArgumentException("Book is already rented, but you have been added to queue" +
                    " check /queue?id=bookId for displaying queue");
        }

        return bookRental;
    }

    private void createPlaceInQueueForUser(Book bookToRent) {
        queueRepository.save(createQueueData(bookToRent));
    }

    private Queue createQueueData(Book bookToRent) {
        return Queue.builder()
                .queueToBook(bookToRent)
                .timeOfJoiningQueue(LocalDateTime.now())
                .username(securityUtilities.retrieveNameFromAuthentication())
                .build();
    }

    private void updateBookAvailabilityAndSaveToDb(Book bookToRent) {
        bookToRent.setAvailable(false);
        bookRepository.save(bookToRent);
    }


    private BookRental prepareBookRentalData(Book bookToRent) {
        return BookRental
                .builder()
                .book(bookToRent)
                .rentedBy(securityUtilities.retrieveNameFromAuthentication())
                .returnDate(LocalDateTime.now().plusMonths(1))
                .dateOfRental(LocalDateTime.now())
                .build();
    }

}

