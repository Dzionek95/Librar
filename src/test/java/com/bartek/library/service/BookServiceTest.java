package com.bartek.library.service;

import com.bartek.library.model.Book;
import com.bartek.library.repository.BookRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void shouldReturnTwoBooks() {
        //given
        Book dummyBook = createBookData();

        Book dummyBook2 = Book.builder()
                .author("Henryk Sienkiewicz")
                .title("Krzyżacy 2")
                .category("powieść historyczna")
                .available(true)
                .build();
        //when
        when(bookRepository.findAll()).thenReturn(Arrays.asList(dummyBook, dummyBook2));
        //then
        Assert.assertEquals(2, bookService.findAllBooks().size());
    }

    @Test
    public void shouldReturnOneBookByAuthor() {
        //given
        Book dummyBook = createBookData();

        //when
        when(bookRepository.findByAuthorIgnoreCaseContaining("Henryk")).thenReturn(Collections.singletonList(dummyBook));

        //then
        Assert.assertEquals(1, bookService.findBooksByAuthor("Henryk").size());
    }

    @Test
    public void shouldReturnOneBookByTitle() {
        //given
        Book dummyBook = createBookData();

        //when
        when(bookRepository.findByTitleIgnoreCaseContaining("Krzyżacy")).thenReturn(Collections.singletonList(dummyBook));

        //then
        Assert.assertEquals(1, bookService.findBooksByTitle("Krzyżacy").size());
    }

    @Test
    public void shouldReturnOneBookByCategory() {
        //given
        Book dummyBook = createBookData();

        //when
        when(bookRepository.findByCategoryIgnoreCaseContaining("historyczna")).thenReturn(Collections.singletonList(dummyBook));

        //then
        Assert.assertEquals(1, bookService.findBooksByCategory("historyczna").size());
    }

    private Book createBookData() {
        return Book.builder()
                .author("Henryk Sienkiewicz")
                .title("Krzyżacy")
                .category("powieść historyczna")
                .available(true)
                .build();
    }

}


