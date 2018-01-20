package com.bartek.library.repository.admin;

import com.bartek.library.model.accounts.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findOneByUsername(String username);

}
