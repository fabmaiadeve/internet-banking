package com.fabdev.internetbanking.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fabdev.internetbanking.models.Account;
import com.fabdev.internetbanking.repositories.AccountRepository;

@Service
public class AccountService {
	
	final AccountRepository accountRepository;

	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	@Transactional
	public Account saveAccount(Account account) {
		
		return accountRepository.save(account);
	}
	
	public List<Account> listAllAccount() {
		
		return accountRepository.findAll();
	}
	
	public Optional<Account> getById(Long id) {
		return accountRepository.findById(id);
	}
	
	public Page<Account> findPageAccount(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return accountRepository.findAll(pageRequest);
	}

}