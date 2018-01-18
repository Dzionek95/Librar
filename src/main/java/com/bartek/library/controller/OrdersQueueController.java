package com.bartek.library.controller;

import com.bartek.library.model.OrdersQueue;
import com.bartek.library.service.OrdersQueueService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ordersqueue")
public class OrdersQueueController {

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
