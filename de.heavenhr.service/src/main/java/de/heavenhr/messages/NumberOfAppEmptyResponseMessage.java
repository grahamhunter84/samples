package de.heavenhr.messages;

import java.util.List;

import org.springframework.http.HttpStatus;

import de.heavenhr.model.Application;

/**
 * This class is specifically used to send the response message when the request
 * is made for the number of applications and result of applications come as
 * empty.
 * 
 * @author Binu VM
 *
 */
public class NumberOfAppEmptyResponseMessage extends NumberOfApplicationsResponseMessage {

	/**
	 * message property
	 */
	private String message;

	/**
	 * Constructor
	 * 
	 * @param applications
	 * @param count
	 * @param message
	 */
	public NumberOfAppEmptyResponseMessage(List<Application> applications, int count, String message) {
		super(applications, count, HttpStatus.NOT_FOUND);
		this.message = message;
	}

	/**
	 * Gets the message
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

}
