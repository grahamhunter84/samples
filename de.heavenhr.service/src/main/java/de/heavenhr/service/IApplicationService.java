package de.heavenhr.service;

import java.util.List;

import org.springframework.stereotype.Service;

import de.heavenhr.model.Application;
import de.heavenhr.model.ApplicationStatus;

/**
 * Service interface for all the services of application related functionality
 * 
 * @author Binu VM
 *
 */
@Service
public interface IApplicationService {

	/**
	 * Apply for a specific job offer
	 * 
	 * @param application
	 * @return the applied application
	 */
	public Application newApplication(Application application);

	/**
	 * Retrieves all the applications
	 * 
	 * @return set of applications
	 */
	public List<Application> getApplications();

	/**
	 * Retrieves the count of the total applications.
	 * 
	 * @return
	 */
	public int count();

	/**
	 * Retrieves an application based on email
	 * 
	 * @param email
	 * @return application
	 */
	public Application findApplicationByEmail(String email);

	/**
	 * Updates an application's status
	 * 
	 * @param email
	 * @param applicationStatus
	 * @return application
	 */
	public Application updateApplicationStatus(String email, ApplicationStatus applicationStatus);

}
