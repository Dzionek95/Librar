package com.bartek.library.service.notifications;

import com.bartek.library.model.accounts.Account;
import com.bartek.library.model.accounts.Role;
import com.bartek.library.model.book.Book;
import com.bartek.library.model.book.OrdersQueue;
import com.bartek.library.model.notifications.NotificationType;
import com.bartek.library.model.notifications.UserNotification;
import com.bartek.library.repository.admin.AccountRepository;
import com.bartek.library.repository.notifications.UserNotificationRepository;
import com.bartek.library.service.SecurityUtilities;
import com.bartek.library.service.book.rental.OrdersQueueService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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

    @Test
    public void shouldSavePunishmentUserNotificationToDataBase() {
        //given
        ArgumentCaptor<UserNotification> argumentCaptor = ArgumentCaptor.forClass(UserNotification.class);

        Account dummyAccount = Account
                .builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_READER)
                .enabled(true)
                .build();

        //verify
        usersNotificationService.notifyUserAboutPunishment(dummyAccount, 100);
        verify(userNotificationRepository, times(1)).save(argumentCaptor.capture());
        Assert.assertEquals("You have to pay for delays in book reurn: 100.0", argumentCaptor.getValue().getMessage());
        Assert.assertEquals(NotificationType.PUNISHMENT, argumentCaptor.getValue().getNotificationType());

    }

    @Test
    public void shouldSavePunishmentUserNotificationToDatabaseAfterUpdatingNotification() {
        //given
        ArgumentCaptor<UserNotification> argumentCaptor = ArgumentCaptor.forClass(UserNotification.class);

        Account dummyAccount = Account
                .builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_READER)
                .enabled(true)
                .build();

        UserNotification dummyNotification = UserNotification
                .builder()
                .account(dummyAccount)
                .message("You have to pay for delays in book reurn: 50.0")
                .build();
        //when
        when(userNotificationRepository.findPunishmentNotificationForUser(anyLong())).thenReturn(dummyNotification);
        //then
        usersNotificationService.notifyUserAboutPunishment(dummyAccount, 100.5);
        verify(userNotificationRepository, times(1)).save(argumentCaptor.capture());
        Assert.assertEquals("You have to pay for delays in book reurn: 100.5", argumentCaptor.getValue().getMessage());
    }

    @Test
    public void shouldReturnListOfTwoUserNotifications() {
        //given

        Account dummyAccount = Account
                .builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_READER)
                .enabled(true)
                .build();

        UserNotification dummyNotification = UserNotification
                .builder()
                .account(dummyAccount)
                .message("You have to pay for delays in book reurn: 50.0")
                .build();

        UserNotification dummyNotification2 = UserNotification
                .builder()
                .account(dummyAccount)
                .message("You have to pay for delays in book reurn: 50.0")
                .build();
        //when
        when(userNotificationRepository.findAllNotificationsForUser(anyLong()))
                .thenReturn(Arrays.asList(dummyNotification, dummyNotification2));
        when(securityUtilities.retrieveNameFromAuthentication())
                .thenReturn("Dzionek95");
        when(accountRepository.findOneByUsername(anyString()))
                .thenReturn(dummyAccount);
        //then
        Assert.assertEquals(2, usersNotificationService.getUsersAllNotifications().size());
    }

}
