package com.bartek.library.service;

import com.bartek.library.model.Book;
import com.bartek.library.model.BookRental;
import com.bartek.library.model.OrdersQueue;
import com.bartek.library.model.UserNotification;
import com.bartek.library.model.accounts.Account;
import com.bartek.library.model.accounts.Role;
import com.bartek.library.repository.BookRentalRepository;
import com.bartek.library.repository.BookRepository;
import com.bartek.library.repository.UserNotificationRepository;
import com.bartek.library.repository.admin.AccountRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.management.Notification;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsersNotificationServiceTest {

    @Mock
    private OrdersQueueService ordersQueueService;
    @Mock
    private UserNotificationRepository userNotificationRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private SecurityUtilities securityUtilities;

    @InjectMocks
    private UsersNotificationService usersNotificationService;

    @Test
    public void verifyIfMethodsDuringNotifyingUsersAreExecuted() {
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
        //when
        when(ordersQueueService.displayQueueToBook(anyLong())).thenReturn(Arrays.asList(dummyOrdersQueue));
        //then
        usersNotificationService.notifyUserThatBookIsAbleToRent(anyLong());
        verify(ordersQueueService, times(1)).displayQueueToBook(anyLong());
        verify(userNotificationRepository, times(1)).save(any(UserNotification.class));
        verify(accountRepository, times(1)).findOneByUsername(anyString());
    }

}
