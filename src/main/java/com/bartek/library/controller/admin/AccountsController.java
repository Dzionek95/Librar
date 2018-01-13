package com.bartek.library.controller.admin;

import com.bartek.library.model.accounts.Accounts;
import com.bartek.library.service.AccountsService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "/create", notes = "Possibility to create new user account")
    @PostMapping("/create")
    Accounts createAccount(@RequestBody Accounts accountToCreate) {
        return accountsService.createAccount(accountToCreate);
    }

    @ApiOperation(value = "/delete", notes = "Possibility to delete account by it's id")
    @DeleteMapping("/delete")
    void deleteAccount(@RequestParam Long id) {
        accountsService.deleteAccount(id);
    }

    @ApiOperation(value = "/update", notes = "Possibility to update account by account body")
    @PutMapping("/update")
    Accounts updateAccount(@RequestBody Accounts accountToUpdate) {
        return accountsService.updateAccount(accountToUpdate);
    }
}
