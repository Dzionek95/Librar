package com.bartek.library.service;

import com.bartek.library.model.accounts.Accounts;
import com.bartek.library.repository.admin.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class AccountsService {

    private AccountRepository accountRepository;
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    AccountsService(AccountRepository accountRepository,
                    InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.accountRepository = accountRepository;
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }

    public Accounts createAccount(Accounts accountToCreate) {
        registerAccountInSecurityContext(accountToCreate);
        accountToCreate.setEnabled(true);
        log.info("Account has been created {}", accountToCreate.toString());
        return accountRepository.save(accountToCreate);
    }

    private void registerAccountInSecurityContext(Accounts accountToCreate) {
        inMemoryUserDetailsManager.createUser(createSecurityUser(accountToCreate));
    }

    private UserDetails createSecurityUser(Accounts accountToCreate) {
        ArrayList<GrantedAuthority> arrayList = new ArrayList<>();
        arrayList.add((GrantedAuthority) () -> accountToCreate.getRole().toString());
        return new User(accountToCreate.getUsername(), accountToCreate.getPassword(), arrayList);
    }

    public void deleteAccount(Long id) {
        log.info("Account with id: {} has been deleted ", id);
        accountRepository.delete(id);
    }

    public Accounts updateAccount(Accounts accountToUpdate) {
        log.info("Account has been updated ", accountToUpdate);
        return accountRepository.save(accountToUpdate);
    }
}
