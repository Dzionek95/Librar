package com.bartek.library.service;

import com.bartek.library.model.Book;
import com.bartek.library.model.OrdersQueue;
import com.bartek.library.repository.OrdersQueueRepository;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrdersQueueServiceTest {

    @Mock
    private OrdersQueueRepository ordersQueueRepository;

    @InjectMocks
    private OrdersQueueService ordersQueueService;

    @Test
    public void shouldReturnListOfTwoOrdersQueue() {
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
        when(ordersQueueRepository.findAllPeopleInQueue(anyLong())).thenReturn(Arrays.asList(dummyOrdersQueue, dummyOrdersQueue2));
        //then
        Assert.assertEquals(2, ordersQueueService.displayQueueToBook(anyLong()).size());
        verify(ordersQueueRepository, times(1)).findAllPeopleInQueue(anyLong());
    }

}
