package com.fabdev.internetbanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fabdev.internetbanking.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
