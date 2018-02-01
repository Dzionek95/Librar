package com.bartek.library.service.penalty;

import com.bartek.library.model.accounts.Account;
import com.bartek.library.model.accounts.Role;
import com.bartek.library.model.book.Book;
import com.bartek.library.model.book.BookRental;
import com.bartek.library.model.notifications.Penalty;
import com.bartek.library.repository.book.BookRentalRepository;
import com.bartek.library.repository.notifications.PenaltyRepository;
import com.bartek.library.service.notifications.UsersNotificationService;
import com.bartek.library.service.penalty.PenaltyService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PenaltyServiceTest {

    @Mock
    private BookRentalRepository bookRentalRepository;
    @Mock
    private UsersNotificationService usersNotificationService;
    @Mock
    private PenaltyRepository penaltyRepository;

    @InjectMocks
    private PenaltyService penaltyService;

    @Test
    public void shouldSaveProperPunishmentToDataBase() {
        //given
        LocalDateTime dummyTime = createLocalDateTimeData();
        Account dummyAccount = createAccountData();
        Book dummyBook = createBookData();
        BookRental dummyBookRental = createBookRentalData(dummyAccount, dummyBook, dummyTime);
        ArgumentCaptor<Penalty> argumentCaptor = ArgumentCaptor.forClass(Penalty.class);

        //when
        when(bookRentalRepository.findAll()).thenReturn(Collections.singletonList(dummyBookRental));

        //then
        penaltyService.checkIfSomeoneIsEligibleToCountPenalty();
        verify(usersNotificationService, times(1)).notifyUserAboutPunishment(any(Account.class), anyLong());
        verify(penaltyRepository, times(1)).save(argumentCaptor.capture());
        Assert.assertTrue(argumentCaptor.getValue().getBook().equals(dummyBook));
        Assert.assertTrue(argumentCaptor.getValue().getAccount().equals(dummyAccount));
        Assert.assertTrue(argumentCaptor.getValue().getAmountOfMoney() > 0);
    }

    @Test
    public void shouldSaveProperPunishmentToDataBaseAfterUpdatingPunishment() {
        //given
        ArgumentCaptor<Penalty> argumentCaptor = ArgumentCaptor.forClass(Penalty.class);
        LocalDateTime dummyTime = createLocalDateTimeData();
        Account dummyAccount = createAccountData();
        Book dummyBook = createBookData();
        BookRental dummyBookRental = createBookRentalData(dummyAccount, dummyBook, dummyTime);
        Penalty dummyPenalty = Penalty
                .builder()
                .account(dummyAccount)
                .amountOfMoney(123)
                .book(dummyBook)
                .build();
        //when
        when(bookRentalRepository.findAll()).thenReturn(Collections.singletonList(dummyBookRental));
        when(penaltyRepository.findPenaltyByAccountId(anyLong())).thenReturn(dummyPenalty);

        //then
        penaltyService.checkIfSomeoneIsEligibleToCountPenalty();

        verify(usersNotificationService, times(1)).notifyUserAboutPunishment(any(Account.class), anyLong());
        verify(penaltyRepository, times(1)).save(argumentCaptor.capture());
        Assert.assertTrue(argumentCaptor.getValue().getBook().equals(dummyBook));
        Assert.assertTrue(argumentCaptor.getValue().getAccount().equals(dummyAccount));
        Assert.assertTrue(argumentCaptor.getValue().getAmountOfMoney() > 123);
    }

    private Account createAccountData() {
        return Account
                .builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_READER)
                .enabled(true)
                .build();
    }

    private Book createBookData() {
        return Book.builder()
                .author("Henryk Sienkiewicz")
                .title("Krzyżacy")
                .category("powieść historyczna")
                .available(false)
                .build();
    }

    private LocalDateTime createLocalDateTimeData() {
        return LocalDateTime.parse("1999-01-10 20:59:42", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private BookRental createBookRentalData(Account dummyAccount, Book dummyBook, LocalDateTime dummyTime) {
        return BookRental.builder()
                .book(dummyBook)
                .dateOfRental(dummyTime)
                .returnDate(dummyTime.plusMonths(1))
                .account(dummyAccount)
                .build();
    }

}
