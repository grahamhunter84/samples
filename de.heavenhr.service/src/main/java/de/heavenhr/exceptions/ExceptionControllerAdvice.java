package de.heavenhr.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import de.heavenhr.messages.FailureResponseMessage;
import de.heavenhr.utils.ExceptionUtils;

/**
 * The controller advice for all the exception handling.
 * 
 * @author Binu VM
 *
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

	/**
	 * Exception handler for ResourceNotFoundException. Any kind of
	 * ResourceNotFoundException occurred in the application will be executed in
	 * this method.
	 * 
	 * @param ex ResourceNotFoundException
	 * @return ResponseEntity which contains the FailureResponseMessage
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<FailureResponseMessage> resourceNotFound(ResourceNotFoundException ex) {
		ResponseEntity<FailureResponseMessage> entity = new ResponseEntity<FailureResponseMessage>(
				ExceptionUtils.convertExceptionToFailureMessage(ex), ex.getHttpStatus());
		return entity;
	}

	/**
	 * Exception handler for BadRequestException. Any kind of BadRequestException
	 * occurred in the application will be executed in this method.
	 * 
	 * @param ex BadRequestException
	 * @return ResponseEntity which contains the FailureResponseMessage
	 */
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<FailureResponseMessage> badRequest(BadRequestException ex) {
		FailureResponseMessage message = ExceptionUtils.convertExceptionToFailureMessage(ex);
		ResponseEntity<FailureResponseMessage> entity = new ResponseEntity<FailureResponseMessage>(message,
				HttpStatus.BAD_REQUEST);
		return entity;
	}

	/**
	 * Exception handler for DuplicateKeyException. Any kind of
	 * DuplicateKeyException occurred in the application will be executed in this
	 * method.
	 * 
	 * @param ex DuplicateKeyException
	 * @return ResponseEntity which contains the FailureResponseMessage
	 */
	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<FailureResponseMessage> duplicateEmail(DuplicateKeyException ex) {
		FailureResponseMessage message = ExceptionUtils.convertExceptionToFailureMessage(ex);
		return new ResponseEntity<FailureResponseMessage>(ExceptionUtils.convertExceptionToFailureMessage(ex),
				message.getHttpstatus());
	}

	/**
	 * Any kind of validation fails, this method will be executed.
	 * 
	 * @param ex MethodArgumentNotValidException
	 * @return ResponseEntity which contains the FailureResponseMessage
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<FailureResponseMessage> invalidInput(MethodArgumentNotValidException ex) {
		FailureResponseMessage message = ExceptionUtils.convertValidationToFailureMessage(ex);
		return new ResponseEntity<FailureResponseMessage>(message, message.getHttpstatus());
	}

}
