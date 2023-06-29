package com.fabdev.internetbanking.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fabdev.internetbanking.dtos.AccountRequest;
import com.fabdev.internetbanking.dtos.MonetaryTransactionRequest;
import com.fabdev.internetbanking.models.Account;
import com.fabdev.internetbanking.services.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	public static final String DEPOSIT = "d";
	public static final String WITHDRAWAL = "w";
	public static final int ABAIXO_DE_100 = 100;
	public static final double TAXA01 = 0.004;
	public static final int ABAIXO_DE_300 = 300;
	public static final double TAXA02 = 0.01;
	
	final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@PostMapping
	public ResponseEntity<Object> persistAccount(@RequestBody @Valid AccountRequest accountRequest) {
		
		Account account = new Account(accountRequest.getName(), 
									  accountRequest.getExclusivePlan(), 
									  accountRequest.getBalance(),
									  accountRequest.getAccountNumber(),
									  accountRequest.getDateOfBirth());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(accountService.saveAccount(account));
	}
	
	@GetMapping
	public ResponseEntity<List<Account>> getAllAccount() {
		return ResponseEntity.status(HttpStatus.OK).body(accountService.listAllAccount());
	}
	
	@RequestMapping(value = "/page")
	@GetMapping
	public ResponseEntity<Page<Account>> findPage(
												  @RequestParam(value="page", defaultValue = "0") Integer page,
												  @RequestParam(value="linesPerPage", defaultValue = "5") Integer linesPerPage,
												  @RequestParam(value="orderBy", defaultValue = "id") String orderBy,
												  @RequestParam(value="direction", defaultValue = "ASC") String direction) {
		
		return ResponseEntity.status(HttpStatus.OK).body(accountService.findPageAccount(page, linesPerPage, orderBy, direction	));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> cashDepositOrWithdrawal(@PathVariable(value = "id") Long id, @RequestBody @Valid MonetaryTransactionRequest monetaryTransactionRequest) {
		
		Optional<Account> accountOptional = accountService.getById(id);
		
		if(accountOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found!");
		}
		
		BigDecimal balanceAmount = accountOptional.get().getBalance();
		Boolean exclusivePlan = accountOptional.get().getExclusivePlan();
		
		Account account = new Account();
		
		account.setId(accountOptional.get().getId());
		account.setName(accountOptional.get().getName());
		account.setExclusivePlan(accountOptional.get().getExclusivePlan());		
		
		account.setAccountNumber(accountOptional.get().getAccountNumber());
		account.setDateOfBirth(accountOptional.get().getDateOfBirth());
		
		if(monetaryTransactionRequest.getOperation().equals(DEPOSIT)) {
			BigDecimal newBalance = makeTheDeposit(balanceAmount, monetaryTransactionRequest.getTransactionAmount());
			account.setBalance(newBalance);
		} else if (monetaryTransactionRequest.getOperation().equals(WITHDRAWAL)) {
			BigDecimal newBalance = withdrawal(balanceAmount, monetaryTransactionRequest.getTransactionAmount(), exclusivePlan);
			account.setBalance(newBalance);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Operation!");
		}
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(accountService.saveAccount(account));
	}
	
	private BigDecimal makeTheDeposit(BigDecimal balanceAmount, BigDecimal transactionAmount) {
		return balanceAmount.add(transactionAmount);
	}
	
	private BigDecimal withdrawal(BigDecimal balanceAmount, BigDecimal transactionAmount, Boolean exclusivePlan) {
		
		if(exclusivePlan) {
			return balanceAmount.subtract(transactionAmount);
		} else {
			BigDecimal faixa_100 = new BigDecimal(ABAIXO_DE_100);
			BigDecimal faixa_300 = new BigDecimal(ABAIXO_DE_300);
			
			if(transactionAmount.compareTo(faixa_100) == 0 || 
					transactionAmount.compareTo(faixa_100) == -1) {
				
				return balanceAmount.subtract(transactionAmount);
				
			} else if(transactionAmount.compareTo(faixa_300) == -1 ||
					transactionAmount.compareTo(faixa_300) == 0) {
				
				BigDecimal taxa01 = new BigDecimal(TAXA01);
				BigDecimal collectionFee = transactionAmount.multiply(taxa01);
				BigDecimal billingAmount = transactionAmount.add(collectionFee);
				
				return balanceAmount.subtract(billingAmount);
				
			} else {
				
				BigDecimal taxa02 = new BigDecimal(TAXA02);
				BigDecimal collectionFee = transactionAmount.multiply(taxa02);
				BigDecimal billingAmount = transactionAmount.add(collectionFee);
				
				return balanceAmount.subtract(billingAmount);
			}
		}
	}
}
