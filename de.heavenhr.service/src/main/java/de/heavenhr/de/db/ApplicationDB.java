package de.heavenhr.de.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.heavenhr.db.exceptions.DuplicateDataException;
import de.heavenhr.model.Application;
import de.heavenhr.model.ApplicationStatus;
import de.heavenhr.model.Offer;

/**
 * The Application DB run as a in memory db and does not save anywhere. It
 * contains the application data and reference of Offer(Jobtitle) object. All
 * the data are saved as key value pair. Here reference of Offer(jobtitle)
 * object will put as key and Application reference will be as value.
 * 
 * @author Binu VM
 * 
 * 
 */

public enum ApplicationDB {
	/**
	 * instance
	 */
	INSTANCE;

	/**
	 * List of applications
	 */
	private List<Application> applications = new ArrayList<>();

	/**
	 * application map which contains offer(Jobtitle) as key and application as
	 * value.
	 */
	private Map<String, List<Application>> applicationsMap = new HashMap<>();

	/**
	 * Returns all applications
	 * 
	 * @return all set of applications
	 */
	public List<Application> getAll() {
		List<Application> appset = new ArrayList<>();		
		Iterator<Entry<String, List<Application>>> iterator = applicationsMap.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String, List<Application>> next = iterator.next();
			List<Application> value = next.getValue();			
			appset.addAll(value);
		}
		return applications;		
	}
	
	public Map<String, List<Application>> getApplicationsMap() {
		return applicationsMap;
	}

	/**
	 * Saves an application to DB. If an application already exists per offer, then
	 * it throws DuplicateDataException
	 * 
	 * @param application
	 * @throws DuplicateDataException
	 */
	public void save(Application application) throws DuplicateDataException {
		Offer offer = application.getOffer();
		String primaryKey = offer.getJobTitle();
		List<Application> list = getApplicationsByOffer(primaryKey);
		if (list.isEmpty()) {
			list.add(application);
			applicationsMap.put(primaryKey, list);
			
		} else {
			if (list.contains(application)) {
				throw new DuplicateDataException();
			}
			list.add(application);
		}
		application.setStatus(ApplicationStatus.APPLIED);
		applications.add(application);//temporary solution
	}

	/**
	 * Retrieves all the applications based on the email id. Email id is primary key
	 * here. So maximum one entry per email id can be expected. Returns application
	 * when the matching email id is found in the application DB.Otherwise returns
	 * false.
	 * 
	 * @param emailId primary key
	 * @return null when there is no matching application found based on
	 *         emailid.Otherwise return application.
	 */
	public Application getApplication(String emailId) {
		for (Application application : applications) {
			if (application.getEmail().equals(emailId)) {
				return application;
			}
		}
		return null;
	}

	/**
	 * Get all applications based on Job title.
	 * 
	 * @param jobTitile primary key
	 * @return set of applications found by jobtitle
	 */
	public List<Application> getApplicationsByOffer(String jobTitile) {
		List<Application> list = applicationsMap.get(jobTitile);
		if (list == null) {
			return new ArrayList<>();
		}
		return list;
	}

}
