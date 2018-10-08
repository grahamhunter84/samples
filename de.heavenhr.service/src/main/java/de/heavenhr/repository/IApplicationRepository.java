package de.heavenhr.repository;

import de.heavenhr.model.Application;
import de.heavenhr.model.ApplicationStatus;

/**
 * Interface for the Application repository
 * 
 * @author Binu VM
 *
 * @param <E> Entity to be perisited
 * @param <P> Primary key type such as i=Integer,String etc
 */
public interface IApplicationRepository<E, P> extends IRepository<E, P> {

	/**
	 * Updates the application status
	 * 
	 * @param email
	 * @param applicationStatus
	 * @return updated application
	 */
	Application updateApplicationStatus(String email, ApplicationStatus applicationStatus);

}
