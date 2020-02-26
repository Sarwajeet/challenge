package com.db.awmd.challenge.exception;

public class TransferAmountNegativeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TransferAmountNegativeException(String message) {
		super(message);
	}
}
