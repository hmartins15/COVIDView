package com.COVID.COVIDView.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Invalid URL not valid Table column name")
public class FieldNotFound extends Exception {
	public FieldNotFound(String errorMessage) {
		super(errorMessage);
	}
}
