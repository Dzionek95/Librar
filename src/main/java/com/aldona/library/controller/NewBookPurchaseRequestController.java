package com.aldona.library.controller;

import com.aldona.library.model.NewBookPurchaseRequest;
import com.aldona.library.service.NewBookPurchaseRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.aldona.library.controller.NewBookPurchaseRequestController.NEW_BOOK_PURCHASE_REQUEST_PATH;


/**
 * @author Bartlomiej Janik
 * @since 1/7/2018
 */
@RestController
@RequestMapping(NEW_BOOK_PURCHASE_REQUEST_PATH)
public class NewBookPurchaseRequestController {

    final static String NEW_BOOK_PURCHASE_REQUEST_PATH = "/purchase";

    private NewBookPurchaseRequestService newBookPurchaseRequestService;

    @Autowired
    NewBookPurchaseRequestController(NewBookPurchaseRequestService newBookPurchaseRequestService){
        this.newBookPurchaseRequestService = newBookPurchaseRequestService;
    }

    @PostMapping
    NewBookPurchaseRequest createNewBookRequest(@RequestBody NewBookPurchaseRequest bookrequest){
        return newBookPurchaseRequestService.createNewBookRequest(bookrequest);
    }

}
