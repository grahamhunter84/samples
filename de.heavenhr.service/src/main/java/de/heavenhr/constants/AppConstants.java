package de.heavenhr.constants;

/**
 * 
 * Interface intended for all the constants related to application.
 * 
 * @author Binu
 *
 */
public interface AppConstants {

	/**
	 * Success message
	 */
	String SUCCESS_OFFER = "Successfully created the Offer";

	/**
	 * Success message
	 */
	String VALIDATION_ERROR = "Validation Error";

	/**
	 * Success application creation
	 */
	String SUCCESS_APPLICATION = "Successfully applied for the Offer";

	/**
	 * Success application creation
	 */
	String UPDATE_STATUS_SUCCESS = "Successfully updated the status";

	/**
	 * Offer not found
	 */
	String OFFER_NOT_EXISTS = "Could not find the offer for the specified job title";

	/**
	 * invalid email
	 */
	String INVALID_EMAIL = "Invalid email";

	/**
	 * Application is null
	 */
	String APPLICATION_IS_NULL = "Email id is missing in the request.";

	/**
	 * Job title missing when application does not contain offer
	 */

	String APPLICATION_WITHOUT_EMPTY_OFFER = "Job title is missing in the request";

	/**
	 * duplicate email
	 */
	String DUPLICATE_EMAIL = "You have already applied for this Job";

	/**
	 * Updates status message
	 */
	String UPDATED_STATUS = "Updated status";

	/**
	 * Parent offer exception
	 */
	String PARENT_OFFER_EXCEPTION = "For applying a job offer, valid Job title should be entered. The job tile which you entered does not exist in the DB";

	/**
	 * No application found
	 */
	String NO_APPLICATION_FOUND = "No Application found for the specified email id";

	/**
	 * No applications found
	 */
	String NO_APPLICATIONS_FOUND = "No Applications found";

	/**
	 * Null offer
	 */
	String NULL_OFFER = "Offer cannot be null";

	/**
	 * Job title missing
	 */

	String EMPTY_JOBTITLE = "Job title is empty";

	/**
	 * Duplicate job title
	 */
	String DUPLICATE_JOBTITLE = "Duplicate Jobtitle";

	/**
	 * Empty email message
	 */
	String EMPTY_EMAIL = "Email cannot be empty";

	/**
	 * Status not updated when email already exists
	 */
	String STATUS_NOT_UPDATED_EMAIL_EXISTS = "Status could not be updated. Specified email id does not exists";

	/**
	 * Status null
	 */
	String STATUS_NULL = "Status is empty";
	
	/**
	 * No application for job
	 */
	String NO_APPLICATION_FOR_JOB = "No applications found for the specified job title";

	/**
	 * Email patterN
	 */
	String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	

}
