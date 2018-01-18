package com.bartek.library.service;

import com.bartek.library.model.accounts.Account;
import com.bartek.library.model.accounts.Role;
import com.bartek.library.repository.admin.AccountRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountsServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Spy
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @InjectMocks
    private AccountsService accountsService;

    @Test
    public void shouldExecuteDeleteMethodFromRepositoryOnce() {
        //verify
        accountsService.deleteAccount(anyLong());
        verify(accountRepository, times(1)).delete(anyLong());
    }

    @Test
    public void shouldReturnOneAccountAndExecuteSaveMethodFromRepositoryOnce() {
        //given
        Account dummyAccount = createAccountData();
        //when
        when(accountRepository.save(any(Account.class))).thenReturn(dummyAccount);
        //then
        Assert.assertEquals(dummyAccount, accountsService.updateAccount(dummyAccount));
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void shouldReturnOneAccount() {
        //given
        Account dummyAccount = createAccountData();
        //when
        when(accountRepository.save(any(Account.class))).thenReturn(dummyAccount);
        //then
        Assert.assertEquals(accountsService.createAccount(dummyAccount), dummyAccount);
        verify(accountRepository, times(1)).save(any(Account.class));
        verify(inMemoryUserDetailsManager, times(1)).createUser(any(UserDetails.class));
    }

    private Account createAccountData() {
        return Account
                .builder()
                .username("dummylogin")
                .password("dummypassword")
                .role(Role.ROLE_ADMIN)
                .build();
    }
}
