package com.bartek.library.service.book.rental;


import com.bartek.library.model.accounts.Account;
import com.bartek.library.model.book.Book;
import com.bartek.library.model.book.BookRental;
import com.bartek.library.model.book.FoundPenaltyToPayException;
import com.bartek.library.repository.book.BookRentalRepository;
import com.bartek.library.repository.book.BookRepository;
import com.bartek.library.repository.notifications.PenaltyRepository;
import com.bartek.library.service.notifications.UsersNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReturnBookService {

    private BookRentalRepository rentBookRepository;
    private BookRepository bookRepository;
    private UsersNotificationService usersNotificationService;
    private PenaltyRepository penaltyRepository;

    @Autowired
    ReturnBookService(BookRentalRepository rentBookRepository,
                      BookRepository bookRepository,
                      UsersNotificationService usersNotificationService,
                      PenaltyRepository penaltyRepository) {

        this.rentBookRepository = rentBookRepository;
        this.bookRepository = bookRepository;
        this.usersNotificationService = usersNotificationService;
        this.penaltyRepository = penaltyRepository;
    }

    public Book returnBook(Long idOfBook) {
        BookRental bookToReturn = rentBookRepository.findOne(idOfBook);
        usersNotificationService.notifyUserThatBookIsAbleToRent(idOfBook);
        if (checkIfUserHasPenaltyToPay(bookToReturn.getAccount())) {
            throw new FoundPenaltyToPayException("It's very nice that you return book, but you already need to pay " +
                    "punishment please proceed to /payment to pay penalty");
        }
        return updateBookStatus(bookToReturn);
    }

    private boolean checkIfUserHasPenaltyToPay(Account account) {
        return penaltyRepository.findPenaltyByAccountId(account.getId()) != null;

    }

    private Book updateBookStatus(BookRental bookToReturn) {
        Book returningBook = bookToReturn.getBook();
        returningBook.setAvailable(true);
        bookRepository.save(returningBook);
        return returningBook;
    }


}

