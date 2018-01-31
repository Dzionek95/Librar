package com.bartek.library.service.book.rental;

import com.bartek.library.model.accounts.Account;
import com.bartek.library.model.accounts.Role;
import com.bartek.library.model.book.Book;
import com.bartek.library.model.book.BookRental;
import com.bartek.library.repository.admin.AccountRepository;
import com.bartek.library.repository.book.BookRentalRepository;
import com.bartek.library.repository.book.BookRepository;
import com.bartek.library.repository.notifications.PenaltyRepository;
import com.bartek.library.service.SecurityUtilities;
import com.bartek.library.service.book.rental.OrdersQueueService;
import com.bartek.library.service.book.rental.RentBookService;
import com.bartek.library.service.notifications.UsersNotificationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RentBookServiceTest {

    @Mock
    private BookRentalRepository rentBookRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private OrdersQueueService ordersQueueService;
    @Mock
    private SecurityUtilities securityUtilities;
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private RentBookService rentBookService;

    @Test
    public void shouldReturnOneBookRental() {
        //given
        LocalDateTime dummyTime = LocalDateTime.parse("2018-01-10 20:59:42", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Account dummyAccount = Account
                .builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_READER)
                .enabled(true)
                .build();

        Book dummyBook = Book.builder()
                .author("Henryk Sienkiewicz")
                .title("Krzyżacy")
                .category("powieść historyczna")
                .available(true)
                .build();

        BookRental dummyBookRental = BookRental.builder()
                .book(dummyBook)
                .dateOfRental(dummyTime)
                .returnDate(dummyTime.plusMonths(1))
                .account(dummyAccount)
                .build();

        //when
        when(securityUtilities.retrieveNameFromAuthentication()).thenReturn("Zbyszek");
        when(accountRepository.findOneByUsername(anyString())).thenReturn(dummyAccount);
        when(bookRepository.findOne(any())).thenReturn(dummyBook);

        //then
        Assert.assertEquals(dummyBookRental, rentBookService.rentBook(1L));
        verify(securityUtilities, times(1)).retrieveNameFromAuthentication();
        verify(rentBookRepository, times(1)).save(any(BookRental.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldCreateQueueObjectAndThrowException() {
        //given
        Book dummyBook = Book.builder()
                .author("Henryk Sienkiewicz")
                .title("Krzyżacy")
                .category("powieść historyczna")
                .available(false)
                .build();

        //when
        when(bookRepository.findOne(anyLong())).thenReturn(dummyBook);
        when(securityUtilities.retrieveNameFromAuthentication()).thenReturn("Dzionek95");
        //then
        rentBookService.rentBook(anyLong());
        verify(bookRepository, times(1)).findOne(anyLong());
        verify(ordersQueueService, times(1))
                .placeAnOrderToQueue(any(Book.class), any(String.class));
    }

}
