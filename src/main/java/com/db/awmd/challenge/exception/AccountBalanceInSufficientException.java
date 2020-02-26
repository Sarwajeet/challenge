package com.db.awmd.challenge.exception;

public class AccountBalanceInSufficientException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountBalanceInSufficientException(String message) {
		super(message);
	}
}
