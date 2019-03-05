package com.guilhermefgl.inter.controller.validation;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ErrorHandlingControllerAdvice {

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
		ValidationErrorResponse error = new ValidationErrorResponse();
		for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
			error.addErrorMessage(violation.getPropertyPath().toString(), violation.getMessage());
		}
		return error;
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		ValidationErrorResponse error = new ValidationErrorResponse();
		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			error.addErrorMessage(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return error;
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingRequestHeaderException.class)
	ValidationErrorResponse onMethodArgumentNotValidException(MissingRequestHeaderException e) {
		ValidationErrorResponse error = new ValidationErrorResponse();
		error.addErrorMessage(e.getHeaderName(), e.getMessage());
		return error;
	}

}