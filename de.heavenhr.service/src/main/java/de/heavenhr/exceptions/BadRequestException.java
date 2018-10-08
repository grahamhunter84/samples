package de.heavenhr.exceptions;

import org.springframework.http.HttpStatus;

import de.heavenhr.constants.AppConstants;

/**
 * BadRequestException is thrown when the client is transferred invalid data.
 * 
 * @author Binu VM
 *
 */
public class BadRequestException extends BaseException {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 7627417812346931675L;

	/**
	 * Bad request
	 */
	private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public BadRequestException(String message) {
		super(BAD_REQUEST.value(), BAD_REQUEST.getReasonPhrase(), message);
	}

	/**
	 * Bad request exception is thrown when the status is empty
	 * 
	 * @return BadRequestException
	 */
	public static BadRequestException statusIsEmpty() {
		return new BadRequestException(AppConstants.STATUS_NULL);
	}

	/**
	 * Bad request exception is thrown when the application is null
	 * 
	 * @return BadRequestException
	 */
	public static BadRequestException applicationIsNull() {
		return new BadRequestException(AppConstants.APPLICATION_IS_NULL);
	}


	/**
	 * Bad request exception is thrown when the offer object of application is null
	 * 
	 * @return BadRequestException
	 */
	public static BadRequestException applicationWithoutEmptyOffer() {
		return new BadRequestException(AppConstants.APPLICATION_WITHOUT_EMPTY_OFFER);
	}
	/**
	 * Bad request exception is thrown when the offer is null
	 * 
	 * @return BadRequestException
	 */
	public static BadRequestException offerNotFoundException() {
		return new BadRequestException(AppConstants.NULL_OFFER);
	}

	/**
	 * BadRequest exception for empty job title.
	 * 
	 * @return bad request exception
	 */
	public static BadRequestException emptyJobTitle() {
		return new BadRequestException(AppConstants.EMPTY_JOBTITLE);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Gets the https status BadRequest
	 */
	@Override
	public HttpStatus getHttpStatus() {
		return BAD_REQUEST;
	}

}
