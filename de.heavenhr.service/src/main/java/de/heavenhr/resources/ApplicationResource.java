package de.heavenhr.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.heavenhr.constants.Path;
import de.heavenhr.messages.ApplicationStatusResponseMessage;
import de.heavenhr.messages.ResponseMessage;
import de.heavenhr.model.Application;
import de.heavenhr.service.IApplicationService;

/**
 * Application resource class for all the application related requests.
 * 
 * @author Binu VM
 *
 */
@RestController
public class ApplicationResource {

	/**
	 * application service
	 */
	@Autowired
	private IApplicationService applicationService;

	/**
	 * Creates a new application. For creating a new application,an offer is mandatory.
	 * 
	 * URL
	 * Request type:POST
	 * Request format: { "email": "aa@gmail.com", "resumeTxt": "Java 4", "status": null, "offer": { "jobTitle": "SW102" } } 
	 * @param application
	 * @return
	 */
	@PostMapping(value = Path.APPLICATION_NEW, produces = "application/json")
	public ResponseEntity<?> newApplication(@Valid @RequestBody(required = false) Application application) {
		applicationService.newApplication(application);
		ResponseMessage message = ResponseMessage.successApplication();
		return new ResponseEntity<ResponseMessage>(message, message.getHttpstatus());
	}

	/**
	 * Updates the application status.
	 * 
	 * URL: http://localhost:8080/api/v1/applications/update/status 
	 * Request type:POST 
	 * Request format: 	 {"email": "ddk@gmail.com","resumeTxt": "Java 4","status": "HIRED" }
	 * 
	 * @param application
	 * @return
	 */
	@PutMapping(Path.UPDATE_APPLICATION_STATUS)
	public ResponseEntity<?> updateApplicationStatus(@RequestBody Application application) {
		Application updateApplicationStatus = applicationService.updateApplicationStatus(application.getEmail(),
				application.getStatus());
		if (updateApplicationStatus == null) {
			ResponseMessage message = ResponseMessage.couldNotUpdateStatusEmailExists();
			return new ResponseEntity<ResponseMessage>(message, message.getHttpstatus());
		}
		return new ResponseEntity<ResponseMessage>(
				ApplicationStatusResponseMessage.updateSuccess(updateApplicationStatus.getStatus()), HttpStatus.OK);
	}

	/**
	 * Find an application based on the email.
	 * 
	 * Request type:GET
	 * Request format: http://localhost:8080/api/v1/applications/kk2@gmail.com
	 * @param email
	 * @return
	 */
	@RequestMapping(value = Path.APPLICATION_PATH
			+ "{email}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> findApplicationByEmail(@PathVariable String email) {
		Application findApplicationByEmail = applicationService.findApplicationByEmail(email);
		if (findApplicationByEmail == null) {
			ResponseMessage responseMessage = ResponseMessage.noApplicationForEmailFound();
			return new ResponseEntity<ResponseMessage>(responseMessage, responseMessage.getHttpstatus());
		}
		return new ResponseEntity<Application>(findApplicationByEmail, HttpStatus.OK);
	}

	/**
	 * Retrieves all the applications 
	 * 
	 * Request type:GET
	 * Request format: http://localhost:8080/api/v1/applications/
	 * 
	 * @return
	 */
	@RequestMapping(value = Path.APPLICATION_PATH, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getApplications() {
		List<Application> applications = applicationService.getApplications();
		if (applications.isEmpty()) {
			ResponseMessage message = ResponseMessage.noApplicationsFound();
			return new ResponseEntity<ResponseMessage>(ResponseMessage.noApplicationsFound(), message.getHttpstatus());
		}
		return new ResponseEntity<List<Application>>(applications, HttpStatus.OK);
	}

}
