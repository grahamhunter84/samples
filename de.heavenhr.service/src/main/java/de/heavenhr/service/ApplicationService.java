package de.heavenhr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import de.heavenhr.constants.AppConstants;
import de.heavenhr.exceptions.BadRequestException;
import de.heavenhr.model.Application;
import de.heavenhr.model.ApplicationStatus;
import de.heavenhr.repository.IApplicationRepository;

/**
 * Implementation class for all the services of application related
 * functionality.
 * 
 * @author Binu VM
 *
 */
@Service
public class ApplicationService implements IApplicationService {

	/**
	 * Application repository
	 */
	@Autowired
	private IApplicationRepository<Application, String> applicationRepository;

	/**
	 * {@inheritDoc}
	 * 
	 * Creates a new application by applying for an job offer.
	 * 
	 * If application is null,it throws <code>ApplicationNotFoundException<code>
	 * 
	 * If an application does not contains any offer, it throws
	 *  <code>BadRequestException<code>. If an application already contains an email
	 * id, it throws  <code>DuplicateKeyException <code> If an application's offer
	 * does not exists in DB, it throws <code>ResourceNotFoundException<code>.It is
	 * mandatory that an application's parent should exists in DB.
	 * 
	 */
	@Override
	public Application newApplication(Application application) {
		if (application == null) {
			throw new BadRequestException(AppConstants.APPLICATION_IS_NULL);
		}
		if (application.getOffer() == null) {
			throw new BadRequestException(AppConstants.APPLICATION_WITHOUT_EMPTY_OFFER);
		}
		if (StringUtils.isEmpty(application.getOffer().getJobTitle())) {
			throw BadRequestException.emptyJobTitle();			
		}

		applicationRepository.save(application);
		return application;

	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public List<Application> getApplications() {
		return applicationRepository.getAll();
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int count() {
		return getApplications().size();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Retrieve an application based on the email.If no application found based on
	 * the email, then returns null.
	 */
	@Override
	public Application findApplicationByEmail(String email) {
		return applicationRepository.find(email);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Updates an application's status.An application is found based on the email.
	 * If an application exists, then it updates it's status and logging the same
	 * for monitoring.
	 * 
	 */
	@Override
	public Application updateApplicationStatus(String email, ApplicationStatus applicationStatus) {
		if (applicationStatus == null) {
			throw BadRequestException.statusIsEmpty();
		}
		return applicationRepository.updateApplicationStatus(email, applicationStatus);
	}

}
