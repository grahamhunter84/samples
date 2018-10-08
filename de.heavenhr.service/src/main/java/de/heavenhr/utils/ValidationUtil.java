package de.heavenhr.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

/**
 * 
 * Utility class for the Client validation.
 * 
 * Binu VM
 * 
 */
public class ValidationUtil {

	/**
	 * All the errors coming as part of the validation are stored in a list as
	 * String format
	 * 
	 * @param errors
	 * @return list of string errors
	 */
	public static List<String> fromBindingErrors(Errors errors) {
		List<String> validErrors = new ArrayList<String>();
		for (ObjectError objectError : errors.getAllErrors()) {
			validErrors.add(objectError.getDefaultMessage());
		}
		return validErrors;
	}

	/**
	 * Gets single result from the list of errors.
	 * 
	 * @param errors
	 * @return error message
	 */
	public static String getSingleResult(Errors errors) {
		return errors.getAllErrors().size() > 0 ? errors.getAllErrors().get(0).getDefaultMessage() : "";
	}
}