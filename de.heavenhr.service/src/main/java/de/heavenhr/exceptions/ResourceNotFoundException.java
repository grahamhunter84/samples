package de.heavenhr.exceptions;

import org.springframework.http.HttpStatus;

import de.heavenhr.constants.AppConstants;

/**
 * ResourceNotFoundException is thrown when the client is request any data where
 * server could not retrieve the same.
 * 
 * @author Binu VM
 *
 */
public class ResourceNotFoundException extends BaseException {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = -1109843835964746150L;

	/**
	 * NOT FOUND status
	 */
	private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public ResourceNotFoundException(String message) {
		super(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase(), message);
	}

	/**
	 * Throws ResourceNotFoundException when no parent offer is found
	 * 
	 * @return ResourceNotFoundException
	 */
	public static ResourceNotFoundException noParentOfferException() {
		return new ResourceNotFoundException(AppConstants.PARENT_OFFER_EXCEPTION);
	}

	/**
	 * Throws ResourceNotFoundException when offer is null
	 * 
	 * @return ResourceNotFoundException
	 */
	public static ResourceNotFoundException offerNotFoundException() {
		return new ResourceNotFoundException(AppConstants.NULL_OFFER);
	}

	/**
	 * Throws ResourceNotFoundException when offer does not exists for the job title
	 * 
	 * @return ResourceNotFoundException
	 */
	public static ResourceNotFoundException offerNotExistException() {
		return new ResourceNotFoundException(AppConstants.OFFER_NOT_EXISTS);
	}

	/**
	 * Gets the http status
	 */
	public HttpStatus getHttpStatus() {
		return HttpStatus.NOT_FOUND;
	}

}
