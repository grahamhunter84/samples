package de.heavenhr.messages;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.heavenhr.constants.AppConstants;
import de.heavenhr.model.Application;

/**
 * This class is specifically used to send the response message when the request
 * is made for the number of applications.
 * 
 * @author Binu VM
 *
 */
public class NumberOfApplicationsResponseMessage extends ResponseMessage {

	/**
	 * list of applications property
	 */
	private List<Application> applications;

	/**
	 * count property
	 */
	@JsonProperty(value = "Total Number of applications")
	private int count;

	/**
	 * Constructor
	 * 
	 * @param applications
	 * @param count
	 */
	public NumberOfApplicationsResponseMessage(List<Application> applications, int count) {
		super("Below applications received", HttpStatus.OK);
		this.applications = applications;
		this.count = count;
	}
	

	/**
	 * Constructor
	 * 
	 * @param applications
	 * @param count
	 */
	public NumberOfApplicationsResponseMessage(List<Application> applications, int count,HttpStatus httpStatus) {
		super("Below applications received", httpStatus);
		this.applications = applications;
		this.count = count;
	}


	/**
	 * Gets the application list
	 * 
	 * @return list of application
	 */
	public List<Application> getApplicationsList() {
		return applications;
	}

	/**
	 * Gets the count
	 * 
	 * @return count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Returns the NumberOfApplicationsResponseMessage used for the number of
	 * applications response. If the number of applications is empty, then
	 * NumberOfAppEmptyResponseMessage is used for the response with appropriate
	 * message.
	 * 
	 * @param applications list of applications
	 * @return NumberOfApplicationsResponseMessage
	 */
	public static NumberOfApplicationsResponseMessage getMessage(List<Application> applications) {
		NumberOfApplicationsResponseMessage message = null;
		if (applications == null || applications.isEmpty()) {
			message = new NumberOfAppEmptyResponseMessage(applications, applications.size(),
					AppConstants.NO_APPLICATION_FOR_JOB);
		} else {
			message = new NumberOfApplicationsResponseMessage(applications, applications.size());
		}

		return message;
	}
}
