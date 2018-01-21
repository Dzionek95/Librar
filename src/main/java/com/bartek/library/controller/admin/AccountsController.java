package com.bartek.library.controller.admin;

import com.bartek.library.model.accounts.Account;
import com.bartek.library.service.admin.AccountsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/accounts")
public class AccountsController {

    private AccountsService accountsService;

    @Autowired
    AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @ApiOperation(value = "/create", notes = "Possibility to create new user account")
    @PostMapping("/create")
    Account createAccount(@RequestBody Account accountToCreate) {
        return accountsService.createAccount(accountToCreate);
    }

    @ApiOperation(value = "/delete", notes = "Possibility to delete account by it's id")
    @DeleteMapping("/delete")
    void deleteAccount(@RequestParam Long id) {
        accountsService.deleteAccount(id);
    }

        @ApiOperation(value = "/update", notes = "Possibility to update account by account body")
    @PutMapping("/update")
    Account updateAccount(@RequestBody Account accountToUpdate) {
        return accountsService.updateAccount(accountToUpdate);
    }
}
