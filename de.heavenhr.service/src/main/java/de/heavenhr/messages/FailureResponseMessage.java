package de.heavenhr.messages;

import org.springframework.http.HttpStatus;

/**
 * This class is used for the Failure response message
 * 
 * @author Binu Mana
 *
 */
public class FailureResponseMessage extends ResponseMessage {

	/**
	 * error details property
	 */
	private String errordetails;

	/**
	 * Constructor
	 * 
	 * @param message
	 * @param errorcode
	 * @param errordetails
	 */
	public FailureResponseMessage(HttpStatus httpstatus, String message, String errordetails) {
		super(message, httpstatus);
		this.errordetails = errordetails;
	}

	/**
	 * Sets the error details
	 * 
	 * @param errordetails
	 */
	public void setErrordetails(String errordetails) {
		this.errordetails = errordetails;
	}

	/**
	 * Gets the error details
	 * 
	 * @return
	 */
	public String getErrordetails() {
		return errordetails;
	}
}
