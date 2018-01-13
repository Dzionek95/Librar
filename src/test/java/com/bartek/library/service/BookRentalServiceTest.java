package com.bartek.library.service;

import com.bartek.library.model.Book;
import com.bartek.library.model.BookRental;
import com.bartek.library.repository.BookRentalRepository;
import com.bartek.library.repository.BookRepository;
import com.bartek.library.repository.QueueRepository;
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
    private QueueRepository queueRepository;
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
        Assert.assertEquals(bookRentalService.returnBook(any()).isAvailable(), true);
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
        Assert.assertEquals(bookRentalService.rentBook(1L), dummyBookRental);
        verify(securityUtilities, times(1)).retrieveNameFromAuthentication();
        verify(rentBookRepository, times(1)).save(any(BookRental.class));
    }

}
