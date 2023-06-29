package com.fabdev.internetbanking.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AccountRequest {
	
	@NotBlank
	private String name;	
	
	private Boolean exclusivePlan;	
	
	private BigDecimal balance;
	
	@NotBlank
	private String accountNumber;	
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
	private LocalDateTime dateOfBirth;
	
	public AccountRequest() {
	}

	public AccountRequest(@NotBlank String name, Boolean exclusivePlan, BigDecimal balance,
			@NotBlank String accountNumber, LocalDateTime dateOfBirth) {
		super();
		this.name = name;
		this.exclusivePlan = exclusivePlan;
		this.balance = balance;
		this.accountNumber = accountNumber;
		this.dateOfBirth = dateOfBirth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getExclusivePlan() {
		return exclusivePlan;
	}

	public void setExclusivePlan(Boolean exclusivePlan) {
		this.exclusivePlan = exclusivePlan;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public LocalDateTime getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDateTime dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}
