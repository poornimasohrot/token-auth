package com.tds.auth.exception;

import static com.tds.auth.literals.MessageConstant.REQUESTD_ACTN_NOT_PERFORMED;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.tds.auth.form.ExceptionForm;

@ControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler({RuntimeException.class, Exception.class})
    public ResponseEntity<Object> handleRuntimeException(
    		RuntimeException ex, WebRequest request) {
		ExceptionForm message = new ExceptionForm(REQUESTD_ACTN_NOT_PERFORMED,ex.getMessage(), false);
		logMessage(message, HttpStatus.INTERNAL_SERVER_ERROR);
		ex.printStackTrace();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(InValidUserException.class)
    public ResponseEntity<Object> handleInValidUserException(
    		InValidUserException ex, WebRequest request) {
		ExceptionForm message = new ExceptionForm(ex.getMessage(),ex.getParentException().getMessage(), false);
		logMessage(message, HttpStatus.UNAUTHORIZED);
		ex.printStackTrace();
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }
	
	@ExceptionHandler(UserExistsException.class)
    public ResponseEntity<Object> handleUserExistsException(
    		UserExistsException ex, WebRequest request) {
		ExceptionForm message = new ExceptionForm(ex.getMessage(),ex.getMessage(), false);
		logMessage(message, HttpStatus.FORBIDDEN);
		ex.printStackTrace();
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }
	
	@ExceptionHandler(UserDoesNotExistsException.class)
    public ResponseEntity<Object> handleUserDoesNotExistsException(
    		UserDoesNotExistsException ex, WebRequest request) {
		ExceptionForm message = new ExceptionForm(ex.getMessage(),ex.getMessage(), false);
		logMessage(message, HttpStatus.FORBIDDEN);
		ex.printStackTrace();
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }
	
	private void logMessage(ExceptionForm message, HttpStatus httpStatus) {
		StringBuilder logMessage = new StringBuilder("An Exception Occurred. Hence, following message is sent to the User: \n");
		logMessage.append(message.toString()).append(". \n Along with Http Status: ").append(httpStatus);
		logger.error(logMessage.toString());
		
	}
}
