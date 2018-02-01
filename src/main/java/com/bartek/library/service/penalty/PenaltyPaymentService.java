package com.bartek.library.service.penalty;

import com.bartek.library.model.accounts.Account;
import com.bartek.library.model.notifications.Penalty;
import com.bartek.library.repository.admin.AccountRepository;
import com.bartek.library.repository.notifications.PenaltyRepository;
import com.bartek.library.service.SecurityUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PenaltyPaymentService {

    private static final double FULL_PAID_AMOUNT_OF_MONEY_FOR_PENALTY = 0.0;

    private PenaltyRepository penaltyRepository;
    private AccountRepository accountRepository;
    private SecurityUtilities securityUtilities;

    @Autowired
    PenaltyPaymentService(PenaltyRepository penaltyRepository,
                          AccountRepository accountRepository,
                          SecurityUtilities securityUtilities) {

        this.penaltyRepository = penaltyRepository;
        this.accountRepository = accountRepository;
        this.securityUtilities = securityUtilities;
    }

    public double payPenalty(double amountOfMoneyFromReader) {
        Account accountOfUser = getAccountOfUserWhoHasPenalty();
        Penalty penaltyToPay = getPenaltyForAccountId(accountOfUser.getId());

        double amountOfMoney = calculateMoneyLeftToPayForPenalty(penaltyToPay.getAmountOfMoney(), amountOfMoneyFromReader);

        if (checkIfUserPaidAllPenalty(amountOfMoney)) {
            penaltyRepository.delete(penaltyToPay);
            return FULL_PAID_AMOUNT_OF_MONEY_FOR_PENALTY;
        } else {
            penaltyRepository.save(penaltyToPay);
            return amountOfMoney;
        }
    }

    private Account getAccountOfUserWhoHasPenalty() {
        return accountRepository
                .findOneByUsername(securityUtilities.retrieveNameFromAuthentication());
    }

    private Penalty getPenaltyForAccountId(long idOfUser) {
        return penaltyRepository.findPenaltyByAccountId(idOfUser);
    }

    private double calculateMoneyLeftToPayForPenalty(double amountOfMoneyFromPenalty, double amountOfMoneyFromReader) {
        return amountOfMoneyFromPenalty - amountOfMoneyFromReader;
    }

    private boolean checkIfUserPaidAllPenalty(double amountOfMoney) {
        return amountOfMoney == FULL_PAID_AMOUNT_OF_MONEY_FOR_PENALTY;
    }

}