package com.bartek.library.service.penalty;

import com.bartek.library.model.book.BookRental;
import com.bartek.library.model.notifications.Penalty;
import com.bartek.library.repository.book.BookRentalRepository;
import com.bartek.library.repository.notifications.PenaltyRepository;
import com.bartek.library.service.notifications.UsersNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class PenaltyService {

    private static final double AMOUNT_OF_MONEY_FOR_ONE_DAY_OF_DELAY = 0.5;

    private UsersNotificationService usersNotificationService;
    private BookRentalRepository bookRentalRepository;
    private PenaltyRepository penaltyRepository;

    private double punishmentToPay;

    @Autowired
    PenaltyService(UsersNotificationService usersNotificationService,
                   BookRentalRepository bookRentalRepository,
                   PenaltyRepository penaltyRepository) {

        this.usersNotificationService = usersNotificationService;
        this.bookRentalRepository = bookRentalRepository;
        this.penaltyRepository = penaltyRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void checkIfSomeoneIsEligibleToCountPenalty() {
        log.info("Checking for punishment has been started");
        Map<BookRental, Long> rentalsToDaysOfDelay = new HashMap<>();

        bookRentalRepository
                .findAll()
                .forEach(bookRental -> {
                    if (LocalDateTime.now().isAfter(bookRental.getReturnDate())) {
                        rentalsToDaysOfDelay.put(bookRental, calculateDaysOfDelay(bookRental.getReturnDate()));
                    }
                });

        if (!rentalsToDaysOfDelay.isEmpty()) {
            processPunishmentAction(rentalsToDaysOfDelay);
        }
    }

    private Long calculateDaysOfDelay(LocalDateTime returnDate) {
        return ChronoUnit.MINUTES.between(returnDate, LocalDateTime.now());
    }

    private void processPunishmentAction(Map<BookRental, Long> rentalsToDaysOfDelay) {
        for (Map.Entry<BookRental, Long> affectedRentalToDelay : rentalsToDaysOfDelay.entrySet()) {

            Long amountOfDays = affectedRentalToDelay.getValue();
            punishmentToPay = calculatePunishmentAmountOfMoney(amountOfDays);
            Optional<Penalty> oldPenaltyFromDb =
                    Optional.ofNullable(penaltyRepository
                                .findPenaltyByAccountId(affectedRentalToDelay.getKey().getAccount().getId()));

            if (oldPenaltyFromDb.isPresent()) {
                updatePunishment(oldPenaltyFromDb.get());
            } else {
                createNewPunishment(affectedRentalToDelay.getKey());
            }
        }
    }

    private double calculatePunishmentAmountOfMoney(long amountOfDays) {
        return amountOfDays * AMOUNT_OF_MONEY_FOR_ONE_DAY_OF_DELAY;
    }

    private void updatePunishment(Penalty penaltyToUpdate) {
        penaltyToUpdate.setAmountOfMoney(punishmentToPay);
        penaltyRepository.save(penaltyToUpdate);
        usersNotificationService.notifyUserAboutPunishment(penaltyToUpdate.getAccount(), punishmentToPay);
    }

    private void createNewPunishment(BookRental affectedRental) {
        penaltyRepository.save(createPenaltyData(affectedRental, punishmentToPay));
        usersNotificationService.notifyUserAboutPunishment(affectedRental.getAccount(), punishmentToPay);
    }

    private Penalty createPenaltyData(BookRental affectedRental, double amountOfDays) {
        return Penalty
                .builder()
                .account(affectedRental.getAccount())
                .book(affectedRental.getBook())
                .amountOfMoney(amountOfDays)
                .build();
    }

}
