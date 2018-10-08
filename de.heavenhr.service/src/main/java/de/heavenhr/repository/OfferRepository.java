package de.heavenhr.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import de.heavenhr.de.db.OfferDB;
import de.heavenhr.model.Offer;

@Repository
public class OfferRepository implements IOfferRepository<Offer,String> {

	private OfferDB offerDB = OfferDB.INSTANCE;
	
	@Override
	public List<Offer> getAll() {
		return offerDB.getOffers();
	}

	@Override
	public Offer find(String p) {
		return offerDB.getOffer(p);
		
	}

	@Override
	public void save(Offer e) {
		offerDB.save(e);
		
	}

	@Override
	public void update(Offer e) {
		// TODO Auto-generated method stub
		
	}

}
