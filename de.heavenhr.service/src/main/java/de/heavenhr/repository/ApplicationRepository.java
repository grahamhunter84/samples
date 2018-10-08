package de.heavenhr.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import de.heavenhr.constants.AppConstants;
import de.heavenhr.db.exceptions.DuplicateDataException;
import de.heavenhr.de.db.ApplicationDB;
import de.heavenhr.de.db.OfferDB;
import de.heavenhr.exceptions.DuplicateKeyException;
import de.heavenhr.exceptions.ResourceNotFoundException;
import de.heavenhr.model.Application;
import de.heavenhr.model.ApplicationStatus;
import de.heavenhr.model.Offer;
import de.heavenhr.service.ApplicationService;

/**
 * Repository class for Application
 * 
 * @author Binu VM
 *
 */
@Repository
public class ApplicationRepository implements IApplicationRepository<Application, String> {

	/**
	 * Logger for logging
	 */
	private static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

	/**
	 * Application db
	 */
	private ApplicationDB applicationDB = ApplicationDB.INSTANCE;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Application> getAll() {
		return applicationDB.getAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Application find(String emailId) {
		return applicationDB.getApplication(emailId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(Application application) {
		Offer offer = application.getOffer();
		if (OfferDB.INSTANCE.getOffers().contains(offer)) {
			try {
				applicationDB.save(application);
			} catch (DuplicateDataException e) {
				throw DuplicateKeyException.duplicateEmail();
			}
		} else {
			throw ResourceNotFoundException.noParentOfferException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Application updateApplicationStatus(String email, ApplicationStatus applicationStatus) {
		Application application = find(email);
		if (application != null) {
			application.setStatus(applicationStatus);
			logger.info(AppConstants.UPDATED_STATUS);
			return application;

		}
		return application;

	}
}
