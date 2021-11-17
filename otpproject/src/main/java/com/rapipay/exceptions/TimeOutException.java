package com.rapipay.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TimeOutException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "TimeOutException [Time Out, try again later]";
	}

}
