package de.heavenhr.resources;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import de.heavenhr.MainApplication;
import de.heavenhr.constants.AppConstants;
import de.heavenhr.constants.Path;
import de.heavenhr.de.db.ApplicationDB;
import de.heavenhr.de.db.OfferDB;
import de.heavenhr.exceptions.ResourceNotFoundException;
import de.heavenhr.messages.FailureResponseMessage;
import de.heavenhr.messages.NumberOfApplicationsResponseMessage;
import de.heavenhr.messages.ResponseMessage;
import de.heavenhr.model.Application;
import de.heavenhr.model.Offer;
import de.heavenhr.utils.ExceptionUtils;

/**
 * Test case class with related to Offer controller class.
 * 
 * @author Binu VM
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferResourceTest extends BaseTestCase {

	/**
	 * Headers
	 */
	HttpHeaders headers = new HttpHeaders();

	/**
	 * port number to be used for the integration test
	 */
	@LocalServerPort
	private int port;

	@Before
	public void setup() {

	}

	/**
	 * Test case for successfully creates the job offer
	 * 
	 * @throws Exception
	 */

	@Test
	public void createJobOfferSuccess() throws Exception {
		Offer offer = new Offer();
		offer.setJobTitle("sw877654");
		String appNewpath = Path.OFFER_NEW;
		HttpEntity<Offer> entity = new HttpEntity<Offer>(offer, headers);
		ResponseEntity<String> response = getResponse(appNewpath, entity, HttpMethod.POST);
		ResponseMessage message = ResponseMessage.successOffer();
		String body = response.getBody();
		String json = toJson(message);
		JSONAssert.assertEquals(json, body, false);

	}

	/**
	 * Test case for jobtitle is null
	 * 
	 * @throws Exception
	 */

	@Test
	public void jobTitleNull() throws Exception {
		Offer offer = new Offer();
		offer.setJobTitle(null);
		HttpEntity<Offer> entity = new HttpEntity<Offer>(offer, headers);
		String appNewpath = Path.OFFER_NEW;
		ResponseEntity<String> response = getResponse(appNewpath, entity, HttpMethod.POST);
		FailureResponseMessage failureResponseMessage = new FailureResponseMessage(HttpStatus.BAD_REQUEST,
				"Validation Error", AppConstants.EMPTY_JOBTITLE);
		String expectedResponseMessage = toJson(failureResponseMessage);
		String responseContent = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, responseContent, false);
	}

	/**
	 * Test case for jobtitle is empty
	 * 
	 * @throws Exception
	 */
	@Test
	public void jobTitleEmpty() throws Exception {
		Offer offer = new Offer();
		offer.setJobTitle("");
		HttpEntity<Offer> entity = new HttpEntity<Offer>(offer, headers);
		String appNewpath = Path.OFFER_NEW;
		ResponseEntity<String> response = getResponse(appNewpath, entity, HttpMethod.POST);

		FailureResponseMessage failureResponseMessage = new FailureResponseMessage(HttpStatus.BAD_REQUEST,
				"Validation Error", AppConstants.EMPTY_JOBTITLE);
		String expectedResponseMessage = toJson(failureResponseMessage);
		String responseContent = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, responseContent, false);
	}

	/**
	 * Test case for jobtitle is blank
	 * 
	 * @throws Exception
	 */
	@Test
	public void jobTitleBlank() throws Exception {
		Offer offer = new Offer();
		offer.setJobTitle(" ");
		HttpEntity<Offer> entity = new HttpEntity<Offer>(offer, headers);
		String appNewpath = Path.OFFER_NEW;
		ResponseEntity<String> response = getResponse(appNewpath, entity, HttpMethod.POST);
		FailureResponseMessage failureResponseMessage = new FailureResponseMessage(HttpStatus.BAD_REQUEST,
				"Validation Error", AppConstants.EMPTY_JOBTITLE);
		String expectedResponseMessage = toJson(failureResponseMessage);
		String responseContent = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, responseContent, false);
	}

	/**
	 * Test case for retrieving all the job offers
	 * 
	 * @throws Exception
	 */
	@Test
	public void getOffers() throws Exception {

		Offer offer = new Offer();
		offer.setJobTitle("SW100");
		HttpEntity<Offer> entity = new HttpEntity<Offer>(null, headers);

		OfferDB.INSTANCE.getOffers().add(TestData.mockOffer());
		OfferDB.INSTANCE.getOffers().add(TestData.mockOffer("sw200"));

		String appNewpath = Path.OFFER_PATH;
		String createURLWithPort = createURLWithPort(appNewpath);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort, HttpMethod.GET, entity,
				String.class);

		String responseContent = response.getBody();
		String expectedResponseMessage = toJson(OfferDB.INSTANCE.getOffers());
		JSONAssert.assertEquals(expectedResponseMessage, responseContent, false);
	}

	/**
	 * Test case for retrieving single job offer based on jobTitle
	 * 
	 * de.heavenhr.de.heavenhr.resources.OfferController.getOffer(String)
	 * 
	 * @throws Exception
	 */
	@Test
	public void getSingleOfferByJobTitle() throws Exception {
		Offer offer = new Offer();
		offer.setJobTitle("SW100");
		OfferDB.INSTANCE.getOffers().add(offer);
		OfferDB.INSTANCE.getOffers().add(TestData.mockOffer("sw200"));

		String appNewpath = Path.OFFER_PATH;
		String createURLWithPort = createURLWithPort(appNewpath + "SW100");
		HttpEntity<Offer> entity = new HttpEntity<Offer>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort, HttpMethod.GET, entity,
				String.class);
		String responseContent = response.getBody();
		String expectedResponseMessage = toJson(offer);
		JSONAssert.assertEquals(expectedResponseMessage, responseContent, false);

	}

	/**
	 * Test case for throwing ResourceNotFoundException if the offer not found while
	 * retrieving single job offer based on jobTitle
	 * 
	 * de.heavenhr.de.heavenhr.resources.OfferController.getOffer(String)
	 * 
	 * @throws Exception
	 */
	@Test
	public void offerNoFoundException() throws Exception {

		Offer offer = new Offer();
		offer.setJobTitle("SW100");
		OfferDB.INSTANCE.getOffers().add(offer);
		OfferDB.INSTANCE.getOffers().add(TestData.mockOffer("sw200"));

		String appNewpath = Path.OFFER_PATH;
		String createURLWithPort = createURLWithPort(appNewpath + "SW333");
		HttpEntity<Offer> entity = new HttpEntity<Offer>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort, HttpMethod.GET, entity,
				String.class);

		ResourceNotFoundException ex = ResourceNotFoundException.offerNotExistException();

		FailureResponseMessage responseMessage = ExceptionUtils.convertExceptionToFailureMessage(ex);
		String responseContent = response.getBody();
		String expectedResponseMessage = toJson(responseMessage);
		JSONAssert.assertEquals(expectedResponseMessage, responseContent, false);

	}

	/**
	 * Test case for number of applications empty
	 * 
	 * @throws Exception
	 */
	@Test
	public void numbrOfApplicationsEmpty() throws Exception {
		HttpEntity<Offer> entity = new HttpEntity<Offer>(null, headers);
		String appNewpath = Path.NUMBER_OF_APPLICATIONS + "SW100";
		ResponseEntity<String> response = getResponse(appNewpath, entity, HttpMethod.GET);
		Offer mockOffer = TestData.mockOffer();
		ApplicationDB applicationDB = ApplicationDB.INSTANCE;
		List<Application> all = applicationDB.getApplicationsByOffer(mockOffer.getJobTitle());
		String expectedResponseMessage = toJson(NumberOfApplicationsResponseMessage.getMessage(all));
		String responseContent = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, responseContent, false);
	}

	/**
	 * Test case for number of applications not empty
	 * 
	 * @throws Exception
	 */
	@Test
	public void numbrOfApplicationsNotEmpty() throws Exception {
		TestData.clearApplicationDBContents();
		TestData.clearOfferDBContents();
		Offer mockOffer = TestData.mockOffer();
		OfferDB.INSTANCE.getOffers().add(mockOffer);
		Application application = TestData.prepareData();
		application.setOffer(mockOffer);
		ApplicationDB applicationDB = ApplicationDB.INSTANCE;
		applicationDB.save(application);
		List<Application> all = applicationDB.getApplicationsByOffer(mockOffer.getJobTitle());

		HttpEntity<Offer> entity = new HttpEntity<Offer>(null, headers);
		String appNewpath = Path.NUMBER_OF_APPLICATIONS + "SW100";
		ResponseEntity<String> response = getResponse(appNewpath, entity, HttpMethod.GET);
		String expectedResponseMessage = toJson(NumberOfApplicationsResponseMessage.getMessage(all));
		String responseContent = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, responseContent, false);

	}

	@Override
	public int port() {
		return port;
	}
}
