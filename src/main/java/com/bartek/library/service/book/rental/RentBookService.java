package com.bartek.library.service.book.rental;

import com.bartek.library.model.accounts.Account;
import com.bartek.library.model.book.Book;
import com.bartek.library.model.book.BookRental;
import com.bartek.library.repository.admin.AccountRepository;
import com.bartek.library.repository.book.BookRentalRepository;
import com.bartek.library.repository.book.BookRepository;
import com.bartek.library.service.SecurityUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RentBookService {

    private BookRentalRepository rentBookRepository;
    private BookRepository bookRepository;
    private OrdersQueueService ordersQueueService;
    private SecurityUtilities securityUtilities;
    private AccountRepository accountRepository;

    @Autowired
    RentBookService(BookRentalRepository rentBookRepository,
                    BookRepository bookRepository,
                    OrdersQueueService ordersQueueService,
                    SecurityUtilities securityUtilities,
                    AccountRepository accountRepository) {

        this.rentBookRepository = rentBookRepository;
        this.bookRepository = bookRepository;
        this.ordersQueueService = ordersQueueService;
        this.securityUtilities = securityUtilities;
        this.accountRepository = accountRepository;
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
                .dateOfRental(LocalDateTime.now())
                .returnDate(LocalDateTime.now().plusMinutes(2))
                .build();
    }

}
