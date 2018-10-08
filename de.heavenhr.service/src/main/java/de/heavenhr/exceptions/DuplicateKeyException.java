package de.heavenhr.exceptions;

import org.springframework.http.HttpStatus;

import de.heavenhr.constants.AppConstants;

/**
 * If any resource is already exists in the server, then DuplicateKeyException
 * is thrown.
 * 
 * @author Binu Mana
 *
 */
public class DuplicateKeyException extends BaseException {

	/**
	 * serial version id
	 */
	private static final long serialVersionUID = -2879426558258895628L;

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public DuplicateKeyException(String message) {
		super(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(), message);
	}

	/**
	 * When an application tries to save and the Appliation contains email id which
	 * is already existing, then DuplicateKeyException is thrown.
	 * 
	 * @return DuplicateKeyException
	 */
	public static DuplicateKeyException duplicateEmail() {
		return new DuplicateKeyException(AppConstants.DUPLICATE_EMAIL);
	}

	/**
	 * When an Offer tries to save and the Offer contains job title which is already
	 * existing, then DuplicateKeyException is thrown.
	 * 
	 * @return DuplicateKeyException
	 */
	public static DuplicateKeyException duplicateJobTitle() {
		return new DuplicateKeyException(AppConstants.DUPLICATE_JOBTITLE);
	}

	public HttpStatus getHttpStatus() {
		return HttpStatus.CONFLICT;
	}

}
