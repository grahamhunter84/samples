package de.heavenhr.de.db;

import java.util.ArrayList;
import java.util.List;

import de.heavenhr.model.Offer;

/**
 * The OfferDB run as a in memory db and does not save anywhere. It contains the
 * job offer related data.
 * 
 * @author Binu VM
 * 
 * 
 */

public enum OfferDB {

	/**
	 * instance
	 */
	INSTANCE;

	/**
	 * List of offers
	 */
	private List<Offer> offers = new ArrayList<>();

	/**
	 * Gets all the offer objects
	 * 
	 * @return list of offer
	 */
	public List<Offer> getOffers() {
		return offers;
	}

	/**
	 * Saves the offer object
	 * 
	 * @param offer
	 */
	public void save(Offer offer) {
		getOffers().add(offer);
	}

	/**
	 * Retrieves a single offer based on the job title
	 * 
	 * @param jobTitle
	 * @return
	 */
	public Offer getOffer(String jobTitle) {
		List<Offer> offers = getOffers();
		for (Offer offer : offers) {
			if (offer.getJobTitle().equals(jobTitle)) {
				return offer;
			}
		}
		return null;
	}
}
