package com.webbdealer.dms.infrastructure.admin.persistence.repositories;

import com.webbdealer.dms.infrastructure.admin.persistence.entities.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface AccountRepository extends CrudRepository<Account, Long> {

    @Query(value = "SELECT * FROM accounts" +
            " a where a.active = 1", nativeQuery = true)
    Collection<Account> findAllActiveAccounts();
}
