package com.idemia.oauth.advice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.idemia.oauth.controller.exception.CustomMessageException;
import com.idemia.oauth.response.GenericResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CustomMessageException.class)
	public ResponseEntity<Object> duplicateRecordFoundException(CustomMessageException ex, WebRequest request) {
		GenericResponse<Object> response = getErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> globleExcpetionHandler(Exception ex, WebRequest request) {
		GenericResponse<Object> response = getErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		final List<String> errors = new ArrayList<>();
		ex.getBindingResult().getFieldErrors().forEach(error->errors.add(error.getField() + ": " + error.getDefaultMessage()));
		ex.getBindingResult().getGlobalErrors().forEach(error->errors.add(error.getObjectName() + ": " + error.getDefaultMessage()));
		final GenericResponse<?> apiError = getErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
		apiError.getErrors().addAll(errors);
		return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		GenericResponse<Object> response = getErrorResponse(ex.getMessage(), status);
		return new ResponseEntity<>(response, status);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		GenericResponse<Object> response = getErrorResponse(ex.getMessage(), status);
		return new ResponseEntity<>(response, status);
	}

	private GenericResponse<Object> getErrorResponse(String message, HttpStatus status) {
		GenericResponse<Object> response = new GenericResponse<>();
		response.setMessage(message);
		response.setStatusCode(String.valueOf(status.value()));
		response.setTimeStamp(getCurrenDateTime());
		return response;
	}

	/**
	 * 
	 * @return date in string format
	 */
	private String getCurrenDateTime() {
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		return dtf.format(LocalDateTime.now());
	}
}
