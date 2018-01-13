package com.bartek.library.controller;

import com.bartek.library.model.NewBookPurchaseRequest;
import com.bartek.library.service.NewBookPurchaseRequestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bartek.library.controller.NewBookPurchaseRequestController.NEW_BOOK_PURCHASE_REQUEST_PATH;


@RestController
@RequestMapping(NEW_BOOK_PURCHASE_REQUEST_PATH)
public class NewBookPurchaseRequestController {

    final static String NEW_BOOK_PURCHASE_REQUEST_PATH = "/purchase";

    private NewBookPurchaseRequestService newBookPurchaseRequestService;

    @Autowired
    NewBookPurchaseRequestController(NewBookPurchaseRequestService newBookPurchaseRequestService){
        this.newBookPurchaseRequestService = newBookPurchaseRequestService;
    }

    @ApiOperation(value = "/save", notes = "Possibility to create new book request, which can be accepted by admin")
    @PostMapping("/save")
    NewBookPurchaseRequest createNewBookRequest(@RequestBody NewBookPurchaseRequest bookPurchaseRequest){
        return newBookPurchaseRequestService.createNewBookRequest(bookPurchaseRequest);
    }

}
