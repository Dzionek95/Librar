package com.bartek.library.controller.admin;

import com.bartek.library.model.NewBookPurchaseRequest;
import com.bartek.library.service.admin.AdminNewBookPurchaseRequestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bartek.library.controller.admin.AdminNewBookPurchaseRequestController.ADMIN_NEW_BOOK_REQUEST;

@RestController
@RequestMapping(ADMIN_NEW_BOOK_REQUEST)
public class AdminNewBookPurchaseRequestController {

    static final String ADMIN_NEW_BOOK_REQUEST = "/admin/purchase";

    private AdminNewBookPurchaseRequestService adminNewBookPurchaseRequestService;


    AdminNewBookPurchaseRequestController(AdminNewBookPurchaseRequestService adminNewBookPurchaseRequestService) {
        this.adminNewBookPurchaseRequestService = adminNewBookPurchaseRequestService;
    }

    @ApiOperation(value = "/display", notes = "Displaying all book purchase requests that could be acknowledged")
    @GetMapping("/display")
    List<NewBookPurchaseRequest> findAllPurchaseRequests() {
        return adminNewBookPurchaseRequestService.findAllPurchaseRequests();
    }

    @ApiOperation(value = "/acknowledge", notes = "Possibility to acknowledge book purchase request(automatically adding book to library)")
    @PostMapping("/acknowledge")
    NewBookPurchaseRequest acknowledgePurchaseRequest(@RequestParam Long id){
        return adminNewBookPurchaseRequestService.acknowledgePurchaseRequest(id);
    }
}
