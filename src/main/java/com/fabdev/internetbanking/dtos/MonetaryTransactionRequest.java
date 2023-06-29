package com.fabdev.internetbanking.dtos;

import java.math.BigDecimal;

public class MonetaryTransactionRequest {
	
	private String operation;
	
	private BigDecimal transactionAmount;

	public String getOperation() {
		return operation;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
}
