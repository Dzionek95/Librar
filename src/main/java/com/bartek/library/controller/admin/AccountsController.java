package com.bartek.library.controller.admin;

import com.bartek.library.model.accounts.Accounts;
import com.bartek.library.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.bartek.library.controller.admin.AccountsController.ADMIN_ACCOUNTS_PATH;

@RestController
@RequestMapping(ADMIN_ACCOUNTS_PATH)
public class AccountsController {

    static final String ADMIN_ACCOUNTS_PATH = "/admin/accounts";

    private AccountsService accountsService;

    @Autowired
    AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @PostMapping("/createaccount")
    Accounts createAccount(@RequestBody Accounts accountToCreate) {
        return accountsService.createAccount(accountToCreate);
    }

    @DeleteMapping("/deleteaccount")
    void deleteAccount(@RequestParam Long id) {
        accountsService.deleteAccount(id);
    }

    @PutMapping("/updateaccount")
    Accounts updateAccount(@RequestBody Accounts accountToUpdate) {
        return accountsService.updateAccount(accountToUpdate);
    }
}
