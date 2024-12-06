package com.test.imaginnovate.exception;

public class NoEmployeesFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoEmployeesFoundException(String message) {
        super(message);
    }
}
