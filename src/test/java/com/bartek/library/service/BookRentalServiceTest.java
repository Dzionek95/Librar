package com.bartek.library.service;

import com.bartek.library.model.Book;
import com.bartek.library.model.BookRental;
import com.bartek.library.model.OrdersQueue;
import com.bartek.library.repository.BookRentalRepository;
import com.bartek.library.repository.BookRepository;
import com.bartek.library.repository.OrdersQueueRepository;
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
public class BookRentalServiceTest {

    @Mock
    private BookRentalRepository rentBookRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private OrdersQueueRepository ordersQueueRepository;
    @Mock
    private SecurityUtilities securityUtilities;

    @InjectMocks
    private BookRentalService bookRentalService;

    @Test
    public void shouldReturnOneBookWithAvailabilitySetToTrue() {
        //given
        LocalDateTime dummyTime = LocalDateTime.parse("2018-01-10 20:59:42", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Book dummyBook = Book.builder()
                .author("Henryk Sienkiewicz")
                .title("Krzyżacy")
                .category("powieść historyczna")
                .available(false)
                .build();
        BookRental dummyBookRental = BookRental.builder()
                .book(dummyBook)
                .dateOfRental(dummyTime)
                .returnDate(dummyTime.plusMonths(1))
                .rentedBy("Zbyszek")
                .build();
        //when
        when(rentBookRepository.findOne(any())).thenReturn(dummyBookRental);
        //then
        Assert.assertEquals(true, bookRentalService.returnBook(any()).isAvailable());
    }

    @Test
    public void shouldReturnOneBookRental() {
        //given
        LocalDateTime dummyTime = LocalDateTime.parse("2018-01-10 20:59:42", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

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
                .rentedBy("Zbyszek")
                .build();

        //when
        when(securityUtilities.retrieveNameFromAuthentication()).thenReturn("Zbyszek");
        when(bookRepository.findOne(any())).thenReturn(dummyBook);

        //then
        Assert.assertEquals(dummyBookRental, bookRentalService.rentBook(1L));
        verify(securityUtilities, times(1)).retrieveNameFromAuthentication();
        verify(rentBookRepository, times(1)).save(any(BookRental.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldCreateQueueObjectAndThrowException(){
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
        bookRentalService.rentBook(anyLong());
        verify(bookRepository, times(1)).findOne(anyLong());
        verify(ordersQueueRepository, times(1)).save(any(OrdersQueue.class));
    }

}
