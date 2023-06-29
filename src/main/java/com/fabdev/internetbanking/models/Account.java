package com.fabdev.internetbanking.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name ="tb_account")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(nullable = false, name = "name")
	private String name;
	
	@Column(nullable = false, name = "exclusive_plan")
	private Boolean exclusivePlan;
	
	@Column(nullable = false, name = "balance")
	private BigDecimal balance;
	
	@Column(nullable = false, name = "account_number")
	private String accountNumber;
	
	@Column(nullable = false, name = "date_of_birth")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
	private LocalDateTime dateOfBirth;

	public Account() {
	}

	public Account(String name, Boolean exclusivePlan, BigDecimal balance, String accountNumber,
			LocalDateTime dateOfBirth) {
		super();
		this.name = name;
		this.exclusivePlan = exclusivePlan;
		this.balance = balance;
		this.accountNumber = accountNumber;
		this.dateOfBirth = dateOfBirth;
	}

	public Account(Long id, String name, Boolean exclusivePlan, BigDecimal balance, String accountNumber,
			LocalDateTime dateOfBirth) {
		super();
		this.id = id;
		this.name = name;
		this.exclusivePlan = exclusivePlan;
		this.balance = balance;
		this.accountNumber = accountNumber;
		this.dateOfBirth = dateOfBirth;
	}

	public Account(Long id) {
		this.id = id;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", exclusivePlan=" + exclusivePlan + ", balance=" + balance
				+ ", accountNumber=" + accountNumber + ", dateOfBirth=" + dateOfBirth + "]";
	}
}