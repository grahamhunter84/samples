package de.heavenhr.messages;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.heavenhr.constants.AppConstants;

/**
 * This class is used for the response messages with status and status code.
 * 
 * @author Binu VM
 *
 */
public class ResponseMessage {
	
	/**
	 * http status
	 */
	@JsonIgnoreProperties
	private HttpStatus httpstatus;

	/**
	 * property message
	 */
	private String message;

	/**
	 * property code
	 */
	private String code;

	/**
	 * Constructor
	 * 
	 * @param message
	 * @param status
	 * @param statuscode
	 */
	public ResponseMessage(String message, HttpStatus status) {
		this.message = message;
		this.httpstatus = status;
		this.code = "" + status.value();
	}

	/**
	 * Sets the message
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the message
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the code
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the code
	 * 
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * gets the http status
	 * 
	 * @return
	 */
	public HttpStatus getHttpstatus() {
		return httpstatus;
	}

	/**
	 * Gets the status
	 * 
	 * @return status
	 */
	public String getStatus() {
		return getHttpstatus().getReasonPhrase();
	}

	/**
	 * Response message contains the http status and no applications for email id
	 * message.
	 * 
	 * @return response message
	 */
	public static ResponseMessage noApplicationForEmailFound() {
		return new ResponseMessage(AppConstants.NO_APPLICATION_FOUND, HttpStatus.NOT_FOUND);
	}

	/**
	 * Response message contains the http status and no applications found message.
	 * 
	 * @return response message
	 */

	public static ResponseMessage noApplicationsFound() {
		return new ResponseMessage(AppConstants.NO_APPLICATIONS_FOUND, HttpStatus.NOT_FOUND);
	}

	/**
	 * Response message contains the http status and reason for not updated the
	 * status
	 * 
	 * @return response message
	 */
	public static ResponseMessage couldNotUpdateStatusEmailExists() {
		return new ResponseMessage(AppConstants.STATUS_NOT_UPDATED_EMAIL_EXISTS, HttpStatus.NOT_FOUND);
	}

	/**
	 * Response message contains the http status and status empty message.
	 * 
	 * @return response message
	 */
	public static ResponseMessage statusEmpty() {
		return new ResponseMessage(AppConstants.STATUS_NULL, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Response message contains the http status and message after creating the
	 * application successfully.
	 * 
	 * @return response message
	 */
	public static ResponseMessage successApplication() {
		return new ResponseMessage(AppConstants.SUCCESS_APPLICATION, HttpStatus.CREATED);
	}

	/**
	 * Response message contains the http status and message after creating the
	 * offer successfully.
	 * 
	 * @return response message
	 */
	public static ResponseMessage successOffer() {
		return new ResponseMessage(AppConstants.SUCCESS_OFFER, HttpStatus.CREATED);
	}
}
