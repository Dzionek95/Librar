package com.bartek.library.service.book;

import com.bartek.library.model.book.Book;
import com.bartek.library.model.book.OrdersQueue;
import com.bartek.library.repository.book.OrdersQueueRepository;
import com.bartek.library.service.book.rental.OrdersQueueService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrdersQueueServiceTest {

    private LocalDateTime dummyTime;
    private Book dummyBook;
    private OrdersQueue dummyOrdersQueue;

    @Mock
    private OrdersQueueRepository ordersQueueRepository;

    @InjectMocks
    private OrdersQueueService ordersQueueService;

    @Before
    public void setUpData(){
        this.dummyTime = LocalDateTime.parse("2018-01-10 20:59:42", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        this.dummyBook = Book.builder()
                .author("Mieszko I")
                .title("Jak ochrzcic Polske")
                .category("Poradniki")
                .available(true)
                .build();

        this.dummyOrdersQueue = OrdersQueue
                .builder()
                .queueToBook(dummyBook)
                .timeOfJoiningQueue(dummyTime)
                .username("Dzionek95")
                .build();
    }

    @Test
    public void shouldReturnListOfTwoOrdersQueue() {
        //given
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

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionThatInformThatUserIsInQueue() {
        //given
        //when
        when(ordersQueueRepository.findAllPeopleInQueue(anyLong())).thenReturn(Arrays.asList(dummyOrdersQueue));
        //then
        ordersQueueService.placeAnOrderToQueue(dummyBook, "Dzionek95");
    }

    @Test
    public void shouldExecuteSavingOrderToRepositoryOnce() {
        //given
        //when
        when(ordersQueueRepository.findAllPeopleInQueue(anyLong())).thenReturn(Collections.emptyList());
        //then
        ordersQueueService.placeAnOrderToQueue(dummyBook, "Dzionek95");
        verify(ordersQueueRepository, times(1)).save(any(OrdersQueue.class));
    }
}
