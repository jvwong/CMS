package org.cms.rest;

import java.net.URI;
import java.util.List;

import org.cms.config.annotation.RestEndpoint;
import org.cms.data.CMSUserRepository;
import org.cms.domain.CMSUser;
import org.cms.exceptions.GenericNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;


@RestEndpoint
@RequestMapping(value="/rest/user")
public class CMSUserRestEndpoint {
	private static final Logger logger = LoggerFactory.getLogger(CMSUserRestEndpoint.class);	
	
	private CMSUserRepository CMSUserRepository;	
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public CMSUserRestEndpoint(CMSUserRepository CMSUserRepository,
			PasswordEncoder passwordEncoder){
		this.CMSUserRepository = CMSUserRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public List<CMSUser> CMSUsers(
			@RequestParam(value="count", defaultValue="20") int count){		
		logger.info("Listing CMSUsers");
		return CMSUserRepository.findAll();		
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> CMSUser(
	      @PathVariable("id") long id) {
		  
		CMSUser CMSUser = CMSUserRepository.findOne(id);		  
		if(CMSUser == null) throw new GenericNotFoundException(id, CMSUser.class.getSimpleName()); 
		return new ResponseEntity<CMSUser>(CMSUser, HttpStatus.OK);
	}
	
	@RequestMapping(
			value="",
			method=RequestMethod.POST, 
			consumes="application/json")
	public ResponseEntity<CMSUser> saveCMSUser(
			@RequestBody CMSUser CMSUser,
			UriComponentsBuilder ucb){
		
		try{
			
			HttpHeaders headers = new HttpHeaders();
			
			// NullPointerException
			CMSUser.setPassword(passwordEncoder.encode(CMSUser.getPassword()));			
			// DataIntegrityViolationException
			CMSUser CMSUserSaved = CMSUserRepository.save(CMSUser);
			
			URI locationUri = 
					ucb.path("/services/rest/user")
					   .path(String.valueOf(CMSUserSaved.getId()))
					   .build()
					   .toUri();				
			headers.setLocation(locationUri);
			return  new ResponseEntity<CMSUser>(CMSUserSaved, headers, HttpStatus.CREATED);
			
		} catch (NullPointerException npe) {
			return new ResponseEntity<CMSUser>(HttpStatus.UNPROCESSABLE_ENTITY);
				
		} catch (DataIntegrityViolationException dee) {			
			return new ResponseEntity<CMSUser>(HttpStatus.CONFLICT);			
		}				
	}		
}
