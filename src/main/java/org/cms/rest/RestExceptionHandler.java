package org.cms.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.cms.config.annotation.RestEndpointAdvice;
import org.cms.exceptions.GenericNotFoundException;
import org.cms.exceptions.MissingCredentialsException;
import org.cms.exceptions.MissingEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.cms.exceptions.Error;

@RestEndpointAdvice
public class RestExceptionHandler {
	
	private MessageSource messageSource;
	 
    @Autowired
    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
  
	@ExceptionHandler(GenericNotFoundException.class)
	@ResponseStatus(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
    public Error objectNotFound(GenericNotFoundException e) {		
		return new Error(404, e.getClassName() + " [" + e.getId() + "] not found");	    
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Error objectNotFound(BadCredentialsException e) {		
		return new Error(401, e.getMessage());	    
	}
	
	@ExceptionHandler(MissingEntityException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error objectNotFound(MissingEntityException e) {		
		return new Error(400, "Bad/missing " + e.getClassName());	    
	}
	
	@ExceptionHandler(MissingCredentialsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error objectNotFound(MissingCredentialsException e) {		
		return new Error(400, e.getClassName() + " missing credential [" + e.getMessage() + "]");	    
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error processValidationError(MethodArgumentNotValidException e) {
		BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<String> errors = new ArrayList<String>();
        
        for (FieldError fieldError: fieldErrors) {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            errors.add(localizedErrorMessage);
        }
		return new Error(400, errors.toString());	    
	}
	
	private String resolveLocalizedErrorMessage(FieldError fieldError) {
		Locale currentLocale =  LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);
        return localizedErrorMessage;
    }

}