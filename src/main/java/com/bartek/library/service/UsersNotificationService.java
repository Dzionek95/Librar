package com.bartek.library.service;

import com.bartek.library.model.OrdersQueue;
import com.bartek.library.model.UserNotification;
import com.bartek.library.model.accounts.Account;
import com.bartek.library.repository.UserNotificationRepository;
import com.bartek.library.repository.admin.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsersNotificationService {

    private final String BOOK_AVAILABILITY = "This book is now available for you: ";
    private final int FIRST_PERSON_IN_QUEUE = 0;

    private OrdersQueueService ordersQueueService;
    private UserNotificationRepository userNotificationRepository;
    private AccountRepository accountRepository;
    private SecurityUtilities securityUtilities;

    UsersNotificationService(OrdersQueueService ordersQueueService,
                             UserNotificationRepository userNotificationRepository,
                             AccountRepository accountRepository,
                             SecurityUtilities securityUtilities) {
        this.ordersQueueService = ordersQueueService;
        this.userNotificationRepository = userNotificationRepository;
        this.accountRepository = accountRepository;
        this.securityUtilities = securityUtilities;
    }

    public List<UserNotification> getUsersAllNotifications() {
        Account userAccount = accountRepository
                .findOneByUsername(securityUtilities.retrieveNameFromAuthentication());

        return userNotificationRepository
                .findAllNotificationsForUser(userAccount.getId());
    }

    public void notifyUserThatBookIsAbleToRent(Long idOfBook) {
        List<OrdersQueue> ordersList =
                ordersQueueService.displayQueueToBook(idOfBook);

        if (checkIfAnyoneIsInQueue(ordersList)) {
            OrdersQueue nextToOrder = ordersList.get(FIRST_PERSON_IN_QUEUE);
            UserNotification newNotification = createNotificationData(nextToOrder);
            userNotificationRepository.save(newNotification);
        }
    }

    private boolean checkIfAnyoneIsInQueue(List<OrdersQueue> ordersList) {
        return !ordersList.isEmpty();
    }

    private UserNotification createNotificationData(OrdersQueue nextToOrder) {
        return UserNotification
                .builder()
                .account(getUserAccount(nextToOrder.getUsername()))
                .timeOfCreationOfNotification(LocalDateTime.now())
                .message(BOOK_AVAILABILITY +
                        nextToOrder.getQueueToBook().toString())
                .build();
    }

    private Account getUserAccount(String username) {
        return accountRepository.findOneByUsername(username);
    }

}
