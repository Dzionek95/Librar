package com.bartek.library.service.book;

import com.bartek.library.model.book.Book;
import com.bartek.library.model.book.OrdersQueue;
import com.bartek.library.repository.book.OrdersQueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdersQueueService {

    private OrdersQueueRepository ordersQueueRepository;

    @Autowired
    OrdersQueueService(OrdersQueueRepository ordersQueueRepository) {
        this.ordersQueueRepository = ordersQueueRepository;
    }

    public List<OrdersQueue> displayQueueToBook(Long idOfBook) {
        return ordersQueueRepository.findAllPeopleInQueue(idOfBook);
    }

    public OrdersQueue placeAnOrderToQueue(Book bookToOrder, String user) {
        checkIfPersonIsAlreadyIsInQueue
                (ordersQueueRepository.findAllPeopleInQueue(bookToOrder.getId()), user);

        OrdersQueue newOrder = createOrderQueue(bookToOrder, user);
        ordersQueueRepository.save(newOrder);

        return newOrder;
    }

    private OrdersQueue createOrderQueue(Book bookToOrder, String user) {
        return OrdersQueue
                .builder()
                .username(user)
                .timeOfJoiningQueue(LocalDateTime.now())
                .queueToBook(bookToOrder)
                .build();
    }

    private void checkIfPersonIsAlreadyIsInQueue(List<OrdersQueue> allPeopleInQueue, String user) {
        allPeopleInQueue
                .stream()
                .forEach(orderInQueue -> {
                    if (orderInQueue.getUsername().equals(user)) {
                        throw new IllegalStateException("You have already sign up in queue!");
                    }
                });
    }
}
