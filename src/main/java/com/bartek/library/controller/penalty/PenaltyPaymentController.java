package com.bartek.library.controller.penalty;

import com.bartek.library.service.notifications.PenaltyPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PenaltyPaymentController {

    private PenaltyPaymentService penaltyPaymentService;

    @Autowired
    PenaltyPaymentController(PenaltyPaymentService penaltyPaymentService) {
        this.penaltyPaymentService = penaltyPaymentService;
    }

    @PostMapping("/payment")
    public double payPenalty(@RequestParam double moneyFromReaderForPenalty) {
        return penaltyPaymentService.payPenalty(moneyFromReaderForPenalty);
    }
}
