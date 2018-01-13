package com.bartek.library.controller;

import com.bartek.library.model.Book;
import com.bartek.library.model.BookRental;
import com.bartek.library.service.BookRentalService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookRentalController.class, secure = false)
@RunWith(SpringRunner.class)
public class BookRentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRentalService bookRentalService;

    @Test
    public void shouldReturnOneBookRental() throws Exception {
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

        String response = "{\"id\":0,\"rentedBy\":\"Zbyszek\",\"book\":{\"id\":0,\"title\":\"Krzy\u017Cacy\",\"author\":\"Henryk Sienkiewicz\",\"category\":\"powie\u015B\u0107 historyczna\",\"available\":false},\"dateOfRental\":\"2018-01-10 20:59:42\",\"returnDate\":\"2018-02-10 20:59:42\"}";
        //when
        when(bookRentalService.rentBook(any())).thenReturn(dummyBookRental);
        //then
        mockMvc.perform(post("/rental/rent?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        Assert.assertEquals(mockMvc.perform(post("/rental/rent?id=1")).andReturn().getResponse().getContentAsString(), response);
        verify(bookRentalService, times(2)).rentBook(any());
    }

    @Test
    public void shouldReturnOneBook() throws Exception {
        //given
        Book dummyBook = Book.builder()
                .author("Henryk Sienkiewicz")
                .title("Krzyżacy")
                .category("powieść historyczna")
                .available(true)
                .build();
        String response = "{\"id\":0,\"title\":\"Krzyżacy\",\"author\":\"Henryk Sienkiewicz\",\"category\":\"powieść historyczna\",\"available\":true}";
        //when
        when(bookRentalService.returnBook(any())).thenReturn(dummyBook);
        //then

        mockMvc.perform(post("/rental/return?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        Assert.assertEquals(mockMvc.perform(post("/rental/return?id=1")).andReturn().getResponse().getContentAsString(), response);
        verify(bookRentalService, times(2)).returnBook(any());
    }

}

