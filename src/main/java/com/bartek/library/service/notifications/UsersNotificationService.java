package com.bartek.library.service.notifications;

import com.bartek.library.model.notifications.NotificationType;
import com.bartek.library.model.book.OrdersQueue;
import com.bartek.library.model.notifications.UserNotification;
import com.bartek.library.model.accounts.Account;
import com.bartek.library.repository.notifications.UserNotificationRepository;
import com.bartek.library.repository.admin.AccountRepository;
import com.bartek.library.service.book.rental.OrdersQueueService;
import com.bartek.library.service.SecurityUtilities;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsersNotificationService {

    private static final String AVAILABILITY_MESSAGE = "This book is now available for you: ";
    private static final String PUNISHMENT_MESSAGE = "You have to pay for delays in book reurn: ";
    private static final int FIRST_PERSON_IN_QUEUE = 0;

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

    public void notifyUserAboutPunishment(Account account, double amountOfMoney) {
        Optional<UserNotification> oldPunishmentUserNotification =
                Optional.ofNullable(userNotificationRepository.findPunishmentNotificationForUser(account.getId()));

        if (oldPunishmentUserNotification.isPresent()) {
            UserNotification punishmentNotificationToUpdate = oldPunishmentUserNotification.get();
            punishmentNotificationToUpdate.setMessage(PUNISHMENT_MESSAGE + amountOfMoney);
            userNotificationRepository.save(punishmentNotificationToUpdate);
        } else {
            UserNotification notificationAboutPunishment = createPunishmentUserNotificationData(account, amountOfMoney);
            userNotificationRepository.save(notificationAboutPunishment);
        }
    }

    private UserNotification createPunishmentUserNotificationData(Account account, double amountOfMoney) {
        return UserNotification
                .builder()
                .timeOfCreationOfNotification(LocalDateTime.now())
                .account(account)
                .message(PUNISHMENT_MESSAGE + amountOfMoney)
                .notificationType(NotificationType.PUNISHMENT)
                .build();
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
                .message(AVAILABILITY_MESSAGE +
                        nextToOrder.getQueueToBook().toString())
                .notificationType(NotificationType.BOOK_AVIABILITY)
                .build();
    }

    private Account getUserAccount(String username) {
        return accountRepository.findOneByUsername(username);
    }
}
