package de.heavenhr.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.heavenhr.de.db.ApplicationDB;
import de.heavenhr.exceptions.BadRequestException;
import de.heavenhr.exceptions.DuplicateKeyException;
import de.heavenhr.exceptions.ResourceNotFoundException;
import de.heavenhr.model.Application;
import de.heavenhr.model.Offer;
import de.heavenhr.repository.IOfferRepository;

/**
 * 
 * 
 * Service class dealt with all the services of Offer related functionality.
 * 
 * 
 * @author Binu VM
 * 
 * 
 */
@Service
public class OfferService implements IOfferService {

	@Autowired
	private IOfferRepository<Offer,String> offerRepository;
	
	/**
	 * {@inheritDoc}
	 * 
	 * Creates an job offer.
	 * 
	 * If the offer is null, then it throws <code>OfferNotFoundException</code>
	 * 
	 * if the job title already exists, then it throws
	 * <code>DuplicateJobTitleException</code>
	 * 
	 */

	@Override
	public Offer createOffer(Offer offer) {
		if (offer == null) {
			throw BadRequestException.offerNotFoundException();
		}
		if (getOffers().contains(offer)) {
			throw DuplicateKeyException.duplicateJobTitle();
		}
		offerRepository.save(offer);		
		return offer;

	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 */

	@Override
	public List<Offer> getOffers() {
		return offerRepository.getAll();
	}

	/**
	 * {@inheritDoc} If the s
	 */
	@Override
	public Offer getOffers(String jobTitle) {
		Offer offer = findOfferByJobTitle(jobTitle);
		if (offer == null) {
			throw ResourceNotFoundException.offerNotExistException();
		}
		List<Application> applications = getApplications(offer.getJobTitle());
		Set<Application> set = new HashSet<>(applications);
		offer.setApplications(set);
		offer.setNoOfApplicagtions(set.size());
		return offer;

	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Offer findOfferByJobTitle(String jobTitle) {
		Offer offer = offerRepository.find(jobTitle);
		if(offer!=null) {
			return offer;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int numberOfApplications(String jobTitle) {
		List<Application> applications = getApplications(jobTitle);
		if (applications != null) {
			return applications.size();
		}
		return 0;
	}

	/**	 
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public List<Application> getApplications(String jobTitle) {
		return ApplicationDB.INSTANCE.getApplicationsByOffer(jobTitle);		
	}

}