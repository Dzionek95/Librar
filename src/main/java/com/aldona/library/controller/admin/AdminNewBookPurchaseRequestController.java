package com.aldona.library.controller.admin;

import com.aldona.library.model.NewBookPurchaseRequest;
import com.aldona.library.service.admin.AdminNewBookPurchaseRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aldona.library.controller.admin.AdminNewBookPurchaseRequestController.ADMIN_NEW_BOOK_REQUEST;

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
