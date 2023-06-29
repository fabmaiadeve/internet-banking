package com.fabdev.internetbanking.controllers;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.fabdev.internetbanking.models.Account;
import com.fabdev.internetbanking.services.AccountService;

import io.restassured.http.ContentType;

@WebMvcTest
public class AccountControllerTest {
	
	@Autowired
	private AccountController accountController;
	
	@MockBean
	private AccountService accountService;
	
	@BeforeEach
	public void setUp() {
		standaloneSetup(this.accountController);
	}
	
	@Test
	@DisplayName("Should return a list all accounts!")
	public void getAllAccountTest() {
		
		when(this.accountService.listAllAccount())
			.thenReturn(new ArrayList<Account>());
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/account")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	@DisplayName("Should save an account successfully!")
	public void persistAccountTest() {
		Account accountSaved = new Account();
		accountSaved.setName("NomeTeste");
		accountSaved.setExclusivePlan(true);
		accountSaved.setBalance(new BigDecimal("1000"));
		accountSaved.setAccountNumber("CC0001");
		accountSaved.setDateOfBirth(LocalDateTime.parse("1980-06-28T22:24:32"));
		
		when(this.accountService.saveAccount(accountSaved))
			.thenReturn(accountSaved);
		
		given()
			.contentType("application/json")
			.body(accountSaved)
		.when()
			.post("/account")			
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	

}
