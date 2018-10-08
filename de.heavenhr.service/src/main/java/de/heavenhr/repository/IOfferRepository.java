package de.heavenhr.repository;

import de.heavenhr.model.Offer;

public interface IOfferRepository<E, P>  extends IRepository<E, P> {

	void update(Offer e);

}
