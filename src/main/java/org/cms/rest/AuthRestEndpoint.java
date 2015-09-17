package org.cms.rest;

import javax.servlet.http.HttpServletResponse;

import org.cms.config.annotation.RestEndpoint;
import org.cms.data.CMSUserRepository;
import org.cms.domain.CMSUser;
import org.cms.exceptions.MissingCredentialsException;
import org.cms.exceptions.MissingEntityException;
import org.cms.security.TokenAuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestEndpoint
@RequestMapping(value="/rest/auth")
public class AuthRestEndpoint {
	private static final Logger logger = LoggerFactory.getLogger(AuthRestEndpoint.class);	
	
	private TokenAuthenticationService tokenAuthenticationService;
	private AuthenticationManager authenticationManager;
	private CMSUserRepository cmsUserRepository;
	
	@Autowired
	public AuthRestEndpoint(
			TokenAuthenticationService tokenAuthenticationService,
			CMSUserRepository cmsUserRepository,
			AuthenticationManager authenticationManager){
		this.cmsUserRepository = cmsUserRepository;
		this.tokenAuthenticationService = tokenAuthenticationService;		
		this.authenticationManager = authenticationManager;
	}
	
	@RequestMapping(
			value="",
			method=RequestMethod.POST, 
			consumes="application/json")
	public CMSUser authenticate(@RequestBody CMSUser cmsUser,
			HttpServletResponse  httpResponse){
		
		// Throws NullPointerException
		if(cmsUser.getUsername() == null){
			throw new MissingCredentialsException("username",  CMSUser.class.getSimpleName());
		}				
	
		if(cmsUser.getPassword() == null){
			throw new MissingCredentialsException("password",  CMSUser.class.getSimpleName());
		} 
		
		Authentication authRequest = new UsernamePasswordAuthenticationToken(cmsUser.getUsername(),
				cmsUser.getPassword());	
		
		// Note: Implicitly this process throws a BadCredentialsException if
		// authentication fails, which is caught within our RestExceptionHandler.
		authenticationManager.authenticate(authRequest);			
		
		// If it succeeds only then will the addAuthentication proceed.
		CMSUser savedSpitter = cmsUserRepository.findByUsername(cmsUser.getUsername());
		tokenAuthenticationService.addAuthentication(httpResponse, savedSpitter);
		
		if(savedSpitter == null) throw new MissingEntityException(CMSUser.class.getName()); 
		
		return savedSpitter;	
	}		
}



