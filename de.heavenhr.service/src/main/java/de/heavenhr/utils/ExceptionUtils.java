package de.heavenhr.utils;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import de.heavenhr.exceptions.BaseException;
import de.heavenhr.messages.FailureResponseMessage;

public class ExceptionUtils {

	public static FailureResponseMessage convertExceptionToFailureMessage(BaseException ex) {
		return new FailureResponseMessage(ex.getHttpStatus(), "Server error", ex.getMessage());
	}

	public static FailureResponseMessage convertValidationToFailureMessage(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		return new FailureResponseMessage(HttpStatus.BAD_REQUEST, "Validation Error",
				ValidationUtil.getSingleResult(result));
	}

}
