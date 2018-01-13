package com.bartek.library.controller;

import com.bartek.library.model.OrdersQueue;
import com.bartek.library.service.OrdersQueueService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.bartek.library.controller.OrdersQueueController.QUEUE_PATH;

@RestController
@RequestMapping(QUEUE_PATH)
public class OrdersQueueController {

    final static String QUEUE_PATH = "/ordersqueue";

    private OrdersQueueService ordersQueueService;

    @Autowired
    OrdersQueueController(OrdersQueueService ordersQueueService) {
        this.ordersQueueService = ordersQueueService;
    }

    @ApiOperation(value = "/display", notes = "Displaying queue of orders to particular book")
    @GetMapping("/display")
    public List<OrdersQueue> displayQueueToBook(Long idOfBook) {
        return ordersQueueService.displayQueueToBook(idOfBook);
    }

}
