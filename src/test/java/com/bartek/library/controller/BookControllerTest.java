package com.bartek.library.controller;

import com.bartek.library.model.Book;
import com.bartek.library.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class, secure = false)
@RunWith(SpringRunner.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void shouldReturnOneBookByAuthor() throws Exception {
        //given
        Book dummyBook = Book.builder()
                .author("Henryk Sienkiewicz")
                .title("Krzyżacy")
                .category("powieść historyczna")
                .available(true)
                .build();
        //when
        when(bookService.findBooksByAuthor(any())).thenReturn(Collections.singletonList(dummyBook));
        //then
        mockMvc.perform(get("/book/byauthor?author=Henryk"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Krzyżacy")))
                .andExpect(jsonPath("$[0].author", is("Henryk Sienkiewicz")))
                .andExpect(jsonPath("$[0].category", is("powieść historyczna")))
                .andExpect(jsonPath("$[0].available", is(true)));

        verify(bookService, times(1)).findBooksByAuthor(any());
    }

    @Test
    public void shouldReturnOneBookByTitle() throws Exception {
        //given
        Book dummyBook = Book.builder()
                .author("Henryk Sienkiewicz")
                .title("Krzyżacy")
                .category("powieść historyczna")
                .available(true)
                .build();
        //when
        when(bookService.findBooksByTitle(any())).thenReturn(Collections.singletonList(dummyBook));
        //then
        mockMvc.perform(get("/book/bytitle?title=Krzyżacy"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].title", is("Krzyżacy")))
                .andExpect(jsonPath("$[0].author", is("Henryk Sienkiewicz")))
                .andExpect(jsonPath("$[0].category", is("powieść historyczna")))
                .andExpect(jsonPath("$[0].available", is(true)))
                .andExpect(jsonPath("$", hasSize(1)));

        verify(bookService, times(1)).findBooksByTitle(any());
    }

    @Test
    public void shouldReturnOneBookByCategory() throws Exception {
        //given
        Book dummyBook = Book.builder()
                .author("Henryk Sienkiewicz")
                .title("Krzyżacy")
                .category("powieść historyczna")
                .available(true)
                .build();
        //when
        when(bookService.findBooksByCategory(any())).thenReturn(Collections.singletonList(dummyBook));
        //then
        mockMvc.perform(get("/book/bycategory?category=historyczna"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Krzyżacy")))
                .andExpect(jsonPath("$[0].author", is("Henryk Sienkiewicz")))
                .andExpect(jsonPath("$[0].category", is("powieść historyczna")))
                .andExpect(jsonPath("$[0].available", is(true)));

        verify(bookService, times(1)).findBooksByCategory(any());
    }

    @Test
    public void shouldReturnTwoBooks() throws Exception {
        //given
        Book dummyBook1 = Book.builder()
                .author("Henryk Sienkiewicz")
                .title("Krzyżacy")
                .category("powieść historyczna")
                .available(true)
                .build();
        Book dummyBook2 = Book.builder()
                .author("Zbigniew Nienacki")
                .title("Nowe przygody Pana Samochodzika")
                .category("powieść dla młodzieży")
                .available(true)
                .build();
        //when
        when(bookService.findAllBooks()).thenReturn(Arrays.asList(dummyBook1, dummyBook2));
        //then
        mockMvc.perform(get("/book/allbooks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Krzyżacy")))
                .andExpect(jsonPath("$[0].author", is("Henryk Sienkiewicz")))
                .andExpect(jsonPath("$[0].category", is("powieść historyczna")))
                .andExpect(jsonPath("$[0].available", is(true)))
                .andExpect(jsonPath("$[1].title", is("Nowe przygody Pana Samochodzika")))
                .andExpect(jsonPath("$[1].author", is("Zbigniew Nienacki")))
                .andExpect(jsonPath("$[1].category", is("powieść dla młodzieży")))
                .andExpect(jsonPath("$[1].available", is(true)));

        verify(bookService, times(1)).findAllBooks();
    }
}
