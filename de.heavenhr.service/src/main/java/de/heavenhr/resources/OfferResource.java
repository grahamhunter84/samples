package de.heavenhr.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.heavenhr.constants.Path;
import de.heavenhr.messages.NumberOfApplicationsResponseMessage;
import de.heavenhr.messages.ResponseMessage;
import de.heavenhr.model.Application;
import de.heavenhr.model.Offer;
import de.heavenhr.service.IOfferService;

/**
 * Application resource class for all the offer related requests.
 * 
 * @author Binu VM
 *
 */
@RestController
public class OfferResource {

	/**
	 * offer service
	 */
	@Autowired
	private IOfferService offerService;

	/**
	 * Creates the job offer
	 * 
	 * Request type:POST http://localhost:8080/api/v1/offers/new
	 * Request format     {  "jobTitle": "SW102" }
	 * 
	 * @param offer
	 * @return Response entity with success message
	 */
	@PostMapping(Path.OFFER_NEW)
	public ResponseEntity<?> createOffer(@Valid @RequestBody Offer offer) {
		offerService.createOffer(offer);
		ResponseMessage message = ResponseMessage.successOffer();
		return new ResponseEntity<ResponseMessage>(message, message.getHttpstatus());
	}

	/**
	 * Retrieves a single the job offer based on the job title
	 * 
	 * Request type:POST http://localhost:8080/api/v1/offers/SW102
	 * 
	 * @return set of offers
	 */
	@RequestMapping(value = Path.OFFER_PATH + "{jobTitle}", method = RequestMethod.GET, produces = "application/json")
	public Offer getOffer(@PathVariable String jobTitle) {
		return offerService.getOffers(jobTitle);

	}

	/**
	 * Retrieves all the offers
	 * 
	 * Request type:GET URL:http://localhost:8080/api/v1/offers/
	 * 
	 * @return set of offers
	 */
	@GetMapping(value = Path.OFFER_PATH, produces = "application/json")
	public List<Offer> getOffers() {
		return offerService.getOffers();

	}

	/**
	 * Gets all the applications applied for the job offer.
	 * 
	 * Request type:GET URL: http://localhost:8080/api/v1/offers/applications/SW101
	 * 
	 * @param jobTitle
	 * @return list of applications
	 */
	@RequestMapping(value = Path.NUMBER_OF_APPLICATIONS
			+ "{jobTitle}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> numberOfApplications(@PathVariable String jobTitle) {
		List<Application> applications = offerService.getApplications(jobTitle);
		NumberOfApplicationsResponseMessage message = NumberOfApplicationsResponseMessage.getMessage(applications);
		return new ResponseEntity<NumberOfApplicationsResponseMessage>(message, message.getHttpstatus());
	}

}
