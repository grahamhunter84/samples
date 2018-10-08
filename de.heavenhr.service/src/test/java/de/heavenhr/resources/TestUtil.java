package de.heavenhr.resources;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Utility class for 
 * 
 * @author Binu VM
 *
 */
public class TestUtil {

	/**
	 * Converts the any object to json string format
	 * 
	 * @param object any object example: <code>Offer<code>,<code>Application</code>
	 *               etc
	 * @return string as json format
	 * 
	 * @throws Exception
	 */
	public static String toJson(Object object) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
	
	
	

}
