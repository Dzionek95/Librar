package com.bartek.library.service.penalty;

import com.bartek.library.model.accounts.Account;
import com.bartek.library.model.notifications.Penalty;
import com.bartek.library.repository.admin.AccountRepository;
import com.bartek.library.repository.notifications.PenaltyRepository;
import com.bartek.library.service.SecurityUtilities;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PenaltyPaymentServiceTest {

    @Mock
    private PenaltyRepository penaltyRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private SecurityUtilities securityUtilities;

    @InjectMocks
    private PenaltyPaymentService penaltyPaymentService;

    @Test
    public void shouldReturnZeroDollarsLeftToPay(){
        //given
        double amountOfMoneyLeftToPayByReader = 0.0;
        Account dummyAccount = createDummyAccount();
        Penalty dummyPenalty = createDummyPenalty();

        //when
        when(securityUtilities.retrieveNameFromAuthentication()).thenReturn("Dzionek");
        when(accountRepository.findOneByUsername(anyString())).thenReturn(dummyAccount);
        when(penaltyRepository.findPenaltyByAccountId(anyLong())).thenReturn(dummyPenalty);
        //then
        Assert.assertThat(amountOfMoneyLeftToPayByReader, equalTo(penaltyPaymentService.payPenalty(4.0)));
        verify(penaltyRepository, times(1)).delete(any(Penalty.class));
    }

    @Test
    public void shouldReturnOneDollarLeftToPay(){
        //given
        double amountOfMoneyLeftToPayByReader = 1.0;
        Account dummyAccount = createDummyAccount();
        Penalty dummyPenalty = createDummyPenalty();

        //when
        when(securityUtilities.retrieveNameFromAuthentication()).thenReturn("Dzionek");
        when(accountRepository.findOneByUsername(anyString())).thenReturn(dummyAccount);
        when(penaltyRepository.findPenaltyByAccountId(anyLong())).thenReturn(dummyPenalty);
        //then
        Assert.assertThat(amountOfMoneyLeftToPayByReader, equalTo(penaltyPaymentService.payPenalty(3.0)));
        verify(penaltyRepository, times(1)).save(any(Penalty.class));
    }


    private Account createDummyAccount(){
        return Account.builder().id(1L).build();
    }

    private Penalty createDummyPenalty(){
        return Penalty.builder().amountOfMoney(4.0).build();
    }
}
