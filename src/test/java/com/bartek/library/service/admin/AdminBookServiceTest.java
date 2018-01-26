package com.bartek.library.service.admin;

import com.bartek.library.model.book.Book;
import com.bartek.library.repository.book.BookRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AdminBookService adminBookService;

    @Test
    public void shouldExecuteSaveMethodOnceAndReturnBook() {
        //given
        Book dummyBook = createBookData();
        //when
        when(bookRepository.save(dummyBook)).thenReturn(dummyBook);
        //then
        Assert.assertEquals(dummyBook, adminBookService.saveBook(dummyBook));
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void shouldExecuteDeleteMethodOnce() {
        //verify
        adminBookService.deleteBookById(anyLong());
        verify(bookRepository, times(1)).delete(anyLong());
    }

    @Test
    public void shouldReturnOneBookAndExecuteSaveMethodOnceByUpdateAction() {
        //given
        Book dummyBook = createBookData();
        //when
        when(bookRepository.save(dummyBook)).thenReturn(dummyBook);
        //then
        Assert.assertEquals(dummyBook, adminBookService.updateBooks(dummyBook));
        verify(bookRepository, times(1)).save(any(Book.class));
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
