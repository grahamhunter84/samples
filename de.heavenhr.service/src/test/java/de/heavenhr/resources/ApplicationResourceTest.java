package de.heavenhr.resources;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import de.heavenhr.MainApplication;
import de.heavenhr.constants.AppConstants;
import de.heavenhr.constants.Path;
import de.heavenhr.de.db.ApplicationDB;
import de.heavenhr.de.db.OfferDB;
import de.heavenhr.exceptions.BadRequestException;
import de.heavenhr.exceptions.DuplicateKeyException;
import de.heavenhr.exceptions.ResourceNotFoundException;
import de.heavenhr.messages.ApplicationStatusResponseMessage;
import de.heavenhr.messages.FailureResponseMessage;
import de.heavenhr.messages.ResponseMessage;
import de.heavenhr.model.Application;
import de.heavenhr.model.ApplicationStatus;
import de.heavenhr.utils.ExceptionUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ApplicationResourceTest extends BaseTestCase {

	/**
	 * headers
	 */
	HttpHeaders headers = new HttpHeaders();

	/**
	 * port number to be used for the integration test
	 */
	@LocalServerPort
	private int port;

	/**
	 * Test case:While creating a new application and the application is null
	 * 
	 * @throws Exception
	 */
	@Test
	public void applicationIsNull() throws Exception {
		TestData.clearAllDBContents();
		OfferDB.INSTANCE.getOffers().add(TestData.mockOffer());
		headers.setContentType(MediaType.APPLICATION_JSON);
		Application application = null;
		HttpEntity<Application> entity = new HttpEntity<Application>(application, headers);
		ResponseEntity<String> response = getResponse(Path.APPLICATION_NEW, entity, HttpMethod.POST);
		BadRequestException ex = BadRequestException.applicationIsNull();
		FailureResponseMessage failureResponseMessage = ExceptionUtils.convertExceptionToFailureMessage(ex);
		String expectedResponseMessage = toJson(failureResponseMessage);
		String actualMessage = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, actualMessage, false);

	}

	/**
	 * Test case:While creating a new application and the application's Offer is
	 * null
	 * 
	 * @throws Exception
	 */
	@Test
	public void applicationOfferIsNull() throws Exception {
		TestData.clearAllDBContents();
		OfferDB.INSTANCE.getOffers().add(TestData.mockOffer());
		headers.setContentType(MediaType.APPLICATION_JSON);
		Application application = new Application();
		application.setEmail("a@b.com");
		HttpEntity<Application> entity = new HttpEntity<Application>(application, headers);
		ResponseEntity<String> response = getResponse(Path.APPLICATION_NEW, entity, HttpMethod.POST);
		BadRequestException ex = BadRequestException.applicationWithoutEmptyOffer();
		FailureResponseMessage failureResponseMessage = ExceptionUtils.convertExceptionToFailureMessage(ex);
		String expectedResponseMessage = toJson(failureResponseMessage);
		String actualMessage = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, actualMessage, false);

	}

	/**
	 * Test case shows message -> Parent Offer does not exist in the DB Scenario:
	 * when create a new application and the specified parent does not exists in the
	 * DB
	 * 
	 * @throws Exception
	 */
	@Test
	public void applyApplicationNoParentOfferException() throws Exception {
		TestData.clearAllDBContents();
		OfferDB.INSTANCE.getOffers().add(TestData.mockOffer());
		headers.setContentType(MediaType.APPLICATION_JSON);

		String json = "{\"email\":\"abcd@gmail.com\",\"resumeTxt\": \"Java 4\",\"status\": null,\"offer\": {\"jobTitle\": \"SW1236\"}}";

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = getResponse(Path.APPLICATION_NEW, entity, HttpMethod.POST);

		ResourceNotFoundException ex = ResourceNotFoundException.noParentOfferException();
		FailureResponseMessage failureResponseMessage = ExceptionUtils.convertExceptionToFailureMessage(ex);

		String actualMessage = response.getBody();
		String expectedResponseMessage = toJson(failureResponseMessage);

		JSONAssert.assertEquals(expectedResponseMessage, actualMessage, false);

	}

	/**
	 * Test case shows message -> Already applied
	 * 
	 * @see de.heavenhr.constants.Path.DUPLICATE_EMAIL
	 * 
	 *      Scenario: when create a new application and the the application's email
	 *      id is already existing in the db.*
	 * 
	 * @throws Exception
	 */
	@Test
	public void duplicateEmail() throws Exception {
		TestData.clearAllDBContents();
		TestData.createOffer();

		Application mockApplication = TestData.mockApplication();
		mockApplication.setOffer(TestData.mockOffer());
		ApplicationDB db = ApplicationDB.INSTANCE;
		db.save(mockApplication);

		String json = "{\"email\":\"a@b.com\",\"resumeTxt\": \"Java 4\",\"status\": null,\"offer\": {\"jobTitle\": \"SW100\"}}";
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = getResponse(Path.APPLICATION_NEW, entity, HttpMethod.POST);

		FailureResponseMessage failureResponseMessage = ExceptionUtils
				.convertExceptionToFailureMessage(DuplicateKeyException.duplicateEmail());

		String expectedResponseMessage = toJson(failureResponseMessage);
		String actualMessage = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, actualMessage, false);

	}

	/**
	 * Test case: Validation error
	 * 
	 * Scenario: When application try to save and jobTitle is empty Offer.
	 * 
	 * @throws Exception
	 */

	@Test
	public void jobTitleEmpty() throws Exception {
		TestData.clearAllDBContents();
		String json = "{\"email\":\"a@b.com\",\"resumeTxt\": \"Java 4\",\"status\": null,\"offer\": {\"jobTitle\": \"\"}}";
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = getResponse(Path.APPLICATION_NEW, entity, HttpMethod.POST);
		String actualMessage = response.getBody();
		FailureResponseMessage failureResponseMessage = new FailureResponseMessage(HttpStatus.BAD_REQUEST,
				"Validation Error", AppConstants.EMPTY_JOBTITLE);
		String expectedResponseMessage = toJson(failureResponseMessage);
		JSONAssert.assertEquals(expectedResponseMessage, actualMessage, false);
	}

	/**
	 * Test case: Validation error
	 * 
	 * Scenario: When application try to save and jobTitle is blank Offer.
	 *
	 * 
	 * @throws Exception
	 */

	@Test
	public void jobTitleBlank() throws Exception {
		TestData.clearAllDBContents();
		String json = "{\"email\":\"a@b.com\",\"resumeTxt\": \"Java 4\",\"status\": null,\"offer\": {\"jobTitle\": \" \"}}";
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = getResponse(Path.APPLICATION_NEW, entity, HttpMethod.POST);
		String actualMessage = response.getBody();

		FailureResponseMessage failureResponseMessage = new FailureResponseMessage(HttpStatus.BAD_REQUEST,
				"Validation Error", AppConstants.EMPTY_JOBTITLE);
		String expectedResponseMessage = toJson(failureResponseMessage);
		JSONAssert.assertEquals(expectedResponseMessage, actualMessage, false);
	}

	/**
	 * Test case for successfully applied for the offer. Application status will be
	 * changed to APPLIED status.
	 * 
	 * Scenario: When application try to save and does not contain jobTitle in
	 * Offer. *
	 * 
	 * @throws Exception
	 */

	@Test
	public void applyApplicationSuccess() throws Exception {
		TestData.clearAllDBContents();

		OfferDB.INSTANCE.getOffers().add(TestData.mockOffer("SW1236"));

		Application application = TestData.mockApplication();
		application.setOffer(TestData.mockOffer());
		headers.setContentType(MediaType.APPLICATION_JSON);

		String json = "{\"email\":\"abcd@gmail.com\",\"resumeTxt\": \"Java 4\",\"status\": null,\"offer\": {\"jobTitle\": \"SW1236\"}}";

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = getResponse(Path.APPLICATION_NEW, entity, HttpMethod.POST);

		String expectedResponseMessage = toJson(ResponseMessage.successApplication());
		String actualMessage = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, actualMessage, false);

	}

	/**
	 * Find an application entry based on the email Should Return one application
	 * entry
	 * 
	 * de.heavenhr.de.heavenhr.resources.ApplicationController.findApplicationByEmail(String)
	 * 
	 * @throws Exception
	 */
	@Test
	public void findApplicationByEmail() throws Exception {
		TestData.clearApplicationDBContents();
		TestData.clearOfferDBContents();
		TestData.createOffer();
		Application mockedApplication = TestData.prepareData();
		ApplicationDB.INSTANCE.getAll().add(mockedApplication);

		HttpEntity<Application> entity = new HttpEntity<Application>(null, headers);
		ResponseEntity<String> response = getResponse(Path.APPLICATION_PATH + "a@b.com", entity,
				HttpMethod.GET);

		String expectedResponseMessage = toJson(mockedApplication);
		String actualResponseMessage = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, actualResponseMessage, false);
	}

	/**
	 * Retrieve all the applications from the db irrespective of the email id
	 * 
	 * de.heavenhr.de.heavenhr.resources.ApplicationController.getApplications()
	 * 
	 * @throws Exception
	 */
	@Test
	public void getApplications() throws Exception {
		TestData.clearApplicationDBContents();
		TestData.clearOfferDBContents();
		TestData.createOffer();
		Application mockedApplication1 = TestData.prepareData();
		Application mockedApplication2 = TestData.prepareData();
		mockedApplication2.setEmail("xyz@gmail.com");
		ApplicationDB.INSTANCE.getAll().add(mockedApplication1);
		ApplicationDB applicationDB = ApplicationDB.INSTANCE;
		List<Application> all = applicationDB.getAll();
		all.add(mockedApplication2);
		HttpEntity<Application> entity = new HttpEntity<Application>(null, headers);
		ResponseEntity<String> response = getResponse(Path.APPLICATION_PATH, entity, HttpMethod.GET);

		String expectedResponseMessage = toJson(all);
		String actualResponseMessage = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, actualResponseMessage, false);

	}

	/**
	 * 
	 * Test case for empty applications
	 * 
	 * 
	 * @throws Exception
	 */

	@Test
	public void getApplicationsEmptyResult() throws Exception {
		TestData.clearApplicationDBContents();
		TestData.clearOfferDBContents();
		TestData.createOffer();

		HttpEntity<Application> entity = new HttpEntity<Application>(null, headers);
		ResponseEntity<String> response = getResponse(Path.APPLICATION_PATH, entity, HttpMethod.GET);
		String expectedResponseMessage = toJson(ResponseMessage.noApplicationsFound());
		String actualResponseMessage = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, actualResponseMessage, false);

	}

	/**
	 * Test case for empty applications searched based on email id
	 * 
	 * de.heavenhr.de.heavenhr.resources.ApplicationController.findApplicationByEmail(String)
	 * 
	 * @throws Exception
	 */
	@Test
	public void findApplicationByEmailNoResult() throws Exception {
		TestData.clearApplicationDBContents();
		TestData.clearOfferDBContents();
		TestData.createOffer();
		Application mockedApplication = TestData.prepareData();
		ApplicationDB.INSTANCE.getAll().add(mockedApplication);

		HttpEntity<Application> entity = new HttpEntity<Application>(null, headers);
		ResponseEntity<String> response = getResponse(Path.APPLICATION_PATH + "kkk@b.com", entity,
				HttpMethod.GET);
		String expectedResponseMessage = toJson(ResponseMessage.noApplicationForEmailFound());
		String actualResponseMessage = response.getBody();
		JSONAssert.assertEquals(expectedResponseMessage, actualResponseMessage, false);
	}

	/**
	 * 
	 * Test case for Updates the application status
	 * 
	 * @throws Exception
	 */
	@Test
	public void updateApplicationStatusSuccess() throws Exception {

		TestData.clearAllDBContents();
		TestData.createOffer();

		Application mockApplication = TestData.mockApplication();
		ApplicationDB.INSTANCE.getAll().add(mockApplication);

		Application mockApplication2 = TestData.mockApplication();
		mockApplication2.setStatus(ApplicationStatus.HIRED);

		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Application> entity = new HttpEntity<Application>(mockApplication2, headers);
		ResponseEntity<String> response = getResponse(Path.UPDATE_APPLICATION_STATUS, entity, HttpMethod.PUT);

		String expectedResponseMessage = toJson(
				ApplicationStatusResponseMessage.updateSuccess(mockApplication2.getStatus()));
		String actualResponseMessage = response.getBody();
		assertEquals(expectedResponseMessage, actualResponseMessage);
	}

	/**
	 * 
	 * Test case for updating the application status where status is null
	 * 
	 * @throws Exception
	 */
	@Test
	public void updateApplicationStatusNull() throws Exception {

		TestData.clearAllDBContents();
		TestData.createOffer();

		ApplicationDB.INSTANCE.getAll().add(TestData.mockApplication());

		String json = "{\"email\":\"a@b.com\",\"resumeTxt\": \"Java 4\",\"status\":null, \"offer\": {\"jobTitle\": \"SW100\"}}";
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = getResponse(Path.UPDATE_APPLICATION_STATUS, entity, HttpMethod.PUT);
		BadRequestException ex = BadRequestException.statusIsEmpty();
		FailureResponseMessage message = ExceptionUtils.convertExceptionToFailureMessage(ex);

		String expectedResponseMessage = toJson(message);
		String actualResponseMessage = response.getBody();
		assertEquals(expectedResponseMessage, actualResponseMessage);
	}

	/**
	 * 
	 * Test case for updating the application status where status is empty
	 * 
	 * @throws Exception
	 */
	@Test
	public void updateApplicationStatusEmpty() throws Exception {

		TestData.clearAllDBContents();
		TestData.createOffer();
		ApplicationDB.INSTANCE.getAll().add(TestData.mockApplication());		
		String json = "{\"email\":\"a@b.com\",\"resumeTxt\": \"Java 4\",\"status\":null, \"offer\": {\"jobTitle\": \"SW100\"}}";
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = getResponse(Path.UPDATE_APPLICATION_STATUS, entity, HttpMethod.PUT);
		BadRequestException ex = BadRequestException.statusIsEmpty();
		FailureResponseMessage message = ExceptionUtils.convertExceptionToFailureMessage(ex);

		String expectedResponseMessage = toJson(message);
		String actualResponseMessage = response.getBody();
		assertEquals(expectedResponseMessage, actualResponseMessage);
	}

	

	@Override
	public int port() {
		return port;
	}

}
