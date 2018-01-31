package com.bartek.library.controller.admin;

import com.bartek.library.controller.book.OrdersQueueController;
import com.bartek.library.model.book.Book;
import com.bartek.library.model.book.OrdersQueue;
import com.bartek.library.service.book.rental.OrdersQueueService;
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
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Bartlomiej Janik
 * @since 1/16/2018
 */
@WebMvcTest(controllers = OrdersQueueController.class, secure = false)
@RunWith(SpringRunner.class)
public class OrdersQueueControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrdersQueueService ordersQueueService;

    @Test
    public void shouldReturnListOfTwoOrdersQueue() throws Exception {
        //given

        LocalDateTime dummyTime = LocalDateTime.parse("2018-01-10 20:59:42", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


        Book dummyBook = Book.builder()
                .author("Mieszko I")
                .title("Jak ochrzcic Polske")
                .category("Poradniki")
                .available(true)
                .build();

        OrdersQueue dummyOrdersQueue = OrdersQueue
                .builder()
                .queueToBook(dummyBook)
                .timeOfJoiningQueue(dummyTime)
                .username("Dzionek95")
                .build();

        OrdersQueue dummyOrdersQueue2 = OrdersQueue
                .builder()
                .queueToBook(dummyBook)
                .timeOfJoiningQueue(dummyTime.plusHours(2))
                .username("Marta97")
                .build();

        //when
        when(ordersQueueService.displayQueueToBook(any()))
                .thenReturn(Arrays.asList(dummyOrdersQueue, dummyOrdersQueue2));
        //then
        mockMvc.perform(get("/ordersqueue/display?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].queueToBook.author", is("Mieszko I")))
                .andExpect(jsonPath("$[0].queueToBook.title", is("Jak ochrzcic Polske")))
                .andExpect(jsonPath("$[0].queueToBook.category", is("Poradniki")))
                .andExpect(jsonPath("$[0].queueToBook.available", is(true)))
                .andExpect(jsonPath("$[0].timeOfJoiningQueue", is(dummyTime.toString().replace('T', ' '))))
                .andExpect(jsonPath("$[0].username", is("Dzionek95")))
                .andExpect(jsonPath("$[1].queueToBook.author", is("Mieszko I")))
                .andExpect(jsonPath("$[1].queueToBook.title", is("Jak ochrzcic Polske")))
                .andExpect(jsonPath("$[1].queueToBook.category", is("Poradniki")))
                .andExpect(jsonPath("$[1].queueToBook.available", is(true)))
                .andExpect(jsonPath("$[1].timeOfJoiningQueue", is(dummyTime.plusHours(2).toString().replace('T', ' '))))
                .andExpect(jsonPath("$[1].username", is("Marta97")))
        ;

    }
}
