package com.bartek.library.controller.admin;

import com.bartek.library.model.NewBookPurchaseRequest;
import com.bartek.library.service.admin.AdminNewBookPurchaseRequestService;
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

    @GetMapping
    List<NewBookPurchaseRequest> findAllPurchaseRequests() {
        return adminNewBookPurchaseRequestService.findAllPurchaseRequests();
    }

    @PostMapping("/acknowledge")
    NewBookPurchaseRequest acknowledgePurchaseRequest(@RequestParam Long id){
        return adminNewBookPurchaseRequestService.acknowledgePurchaseRequest(id);
    }
}
