package de.heavenhr.resources;

import de.heavenhr.de.db.ApplicationDB;
import de.heavenhr.de.db.OfferDB;
import de.heavenhr.model.Application;
import de.heavenhr.model.Offer;

public class TestData {
	
	public static void clearApplicationDBContents() {
		ApplicationDB.INSTANCE.getAll().clear();
	}

	/**
	 * Creates the offer object in DB
	 */
	public static void createOffer() {
		OfferDB.INSTANCE.save(mockOffer());		
	}

	/**
	 * Clear the contents of Offer DB
	 */
	public static void clearOfferDBContents() {
		OfferDB.INSTANCE.getOffers().clear();
	}
	
	public static Offer mockOffer() {
		return mockOffer("SW100");
		
	}
	
	public static Offer mockOffer(String jobTitle) {
		Offer offer = new Offer();
		offer.setJobTitle(jobTitle);
		return offer;
	}
	
	public static void commonTestData() {
		clearApplicationDBContents();
		clearOfferDBContents();
		createOffer();
	}
	
	public static void clearAllDBContents() {
		clearApplicationDBContents();
		clearOfferDBContents();
		
	}
	
	public static Application prepareData() {
		Application mockedApplication = mockApplication();
		mockedApplication.setOffer(mockOffer());
		return mockedApplication;
	}

	public static Application mockApplication() {
		return mockApplication("a@b.com");
	}

	

	public static Application mockApplication(String email) {
		Application application = new Application();
		application.setEmail(email);
		return application;
	}
	

}
