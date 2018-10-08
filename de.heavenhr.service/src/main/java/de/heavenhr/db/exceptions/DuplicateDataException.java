package de.heavenhr.db.exceptions;

/**
 * DuplicateDataException is throws when an object tries to save to DB which
 * already exists. This exception is coming from the DB layer. It is a checked
 * exception,hence need catch it.
 * 
 * @author Binu VM
 *
 */
public class DuplicateDataException extends Exception {

	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 2376838233994924425L;

}
