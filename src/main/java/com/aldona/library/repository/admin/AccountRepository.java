package com.aldona.library.repository.admin;

import com.aldona.library.model.accounts.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Accounts, Long> {
}
