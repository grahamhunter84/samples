package de.heavenhr.messages;

import org.springframework.http.HttpStatus;

import de.heavenhr.constants.AppConstants;
import de.heavenhr.model.ApplicationStatus;

/**
 * This class is specifically used for the Application status response message.
 * 
 * @author Binu VM
 *
 */
public class ApplicationStatusResponseMessage extends ResponseMessage {

	/**
	 * application status
	 */
	private String applicationStatus;

	/**
	 * Constructor
	 * 
	 * @param message
	 * @param applicationStatus
	 */
	public ApplicationStatusResponseMessage(String message, String applicationStatus) {
		super(message, HttpStatus.OK);
		this.applicationStatus = applicationStatus;

	}

	/**
	 * sets the application
	 * 
	 * @param applicationStatus
	 */
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	/**
	 * Gets the application status
	 * 
	 * @return application status
	 */
	public String getApplicationStatus() {
		return applicationStatus;
	}

	/**
	 * ApplicationStatusResponseMessage contains the update success message
	 * 
	 * @param status
	 * @return ApplicationStatusResponseMessage
	 */
	public static ApplicationStatusResponseMessage updateSuccess(ApplicationStatus status) {
		return new ApplicationStatusResponseMessage(AppConstants.UPDATE_STATUS_SUCCESS, status.name());
	}

}
