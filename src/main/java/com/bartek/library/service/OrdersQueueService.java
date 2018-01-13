package com.bartek.library.service;

import com.bartek.library.model.OrdersQueue;
import com.bartek.library.repository.OrdersQueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersQueueService {

    private OrdersQueueRepository ordersQueueRepository;

    @Autowired
    OrdersQueueService(OrdersQueueRepository ordersQueueRepository){
        this.ordersQueueRepository = ordersQueueRepository;
    }

    public List<OrdersQueue> displayQueueToBook(Long idOfBook) {
        return ordersQueueRepository.findAllPeopleInQueue(idOfBook);
    }
}
