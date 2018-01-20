package com.bartek.library.service;


import com.bartek.library.model.Book;
import com.bartek.library.model.BookRental;
import com.bartek.library.model.accounts.Account;
import com.bartek.library.repository.BookRentalRepository;
import com.bartek.library.repository.BookRepository;
import com.bartek.library.repository.admin.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookRentalService {

    private BookRentalRepository rentBookRepository;
    private BookRepository bookRepository;
    private OrdersQueueService ordersQueueService;
    private SecurityUtilities securityUtilities;
    private AccountRepository accountRepository;
    private UsersNotificationService usersNotificationService;

    @Autowired
    BookRentalService(BookRentalRepository rentBookRepository,
                      BookRepository bookRepository,
                      OrdersQueueService ordersQueueService,
                      SecurityUtilities securityUtilities,
                      AccountRepository accountRepository,
                      UsersNotificationService usersNotificationService) {

        this.rentBookRepository = rentBookRepository;
        this.bookRepository = bookRepository;
        this.ordersQueueService = ordersQueueService;
        this.securityUtilities = securityUtilities;
        this.accountRepository = accountRepository;
        this.usersNotificationService = usersNotificationService;
    }

    public BookRental rentBook(Long idOfBook) {
        Book bookToRent = bookRepository.findOne(idOfBook);
        BookRental bookRental;
        if (bookToRent.isAvailable()) {
            bookRental = performAllActionsRequiredToRentBook(bookToRent);
        } else {
            ordersQueueService.placeAnOrderToQueue(bookToRent,
                    securityUtilities.retrieveNameFromAuthentication());
            throw new IllegalArgumentException("Book is already rented, but you have been added to queue" +
                    " check /ordersqueue/display?id=bookId for displaying queue");
        }

        return bookRental;
    }

    private BookRental performAllActionsRequiredToRentBook(Book bookToRent) {
        BookRental bookRental;
        Account rentedBy = accountRepository
                .findOneByUsername(securityUtilities.retrieveNameFromAuthentication());

        updateBookAvailabilityAndSaveToDb(bookToRent);
        bookRental = prepareBookRentalData(bookToRent, rentedBy);
        rentBookRepository.save(bookRental);

        return bookRental;
    }

    private void updateBookAvailabilityAndSaveToDb(Book bookToRent) {
        bookToRent.setAvailable(false);
        bookRepository.save(bookToRent);
    }


    private BookRental prepareBookRentalData(Book bookToRent, Account rentedBy) {
        return BookRental
                .builder()
                .book(bookToRent)
                .account(rentedBy)
                .returnDate(LocalDateTime.now().plusMonths(1))
                .dateOfRental(LocalDateTime.now())
                .build();
    }

    public Book returnBook(Long idOfBook) {
        BookRental bookToReturn = rentBookRepository.findOne(idOfBook);
        usersNotificationService.notifyUserThatBookIsAbleToRent(idOfBook);
        return updateBookStatus(bookToReturn);
    }

    private Book updateBookStatus(BookRental bookToReturn) {
        Book returningBook = bookToReturn.getBook();
        returningBook.setAvailable(true);
        bookRepository.save(returningBook);
        return returningBook;
    }



}

