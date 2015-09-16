package org.cms.web;

import java.util.Locale;

import javax.validation.Valid;

import org.cms.config.annotation.WebController;
import org.cms.data.CMSUserRepository;
import org.cms.domain.CMSUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Handles requests for the application home page.
 */
@WebController
@RequestMapping(value = "/auth")
public class AuthController {	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	private PasswordEncoder passwordEncoder;	
	private CMSUserRepository cmsUserRepository;

	@Autowired
	public AuthController(CMSUserRepository cmsUserRepository,
			PasswordEncoder passwordEncoder) {
	    this.cmsUserRepository = cmsUserRepository;
	    this.passwordEncoder = passwordEncoder;
	}
	
	/*
	 * Show the login page 
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {
		logger.info("Login");
		return "/auth/login";
	}
	
	/*
	 * Show the registration page 
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegistrationForm(Locale locale, Model model) {
		logger.info("/register");
		model.addAttribute("registerForm", new RegisterForm());
		return "/auth/registerForm";
	}
	
	
	/*
	 * Process the registration - forget about the avatar for now
	 * */
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String processRegistration(
			@RequestPart("profilePicture") MultipartFile profilePicture,
			RedirectAttributes model, //Must precede validate
		    @Valid RegisterForm registerForm,		    
		    Errors errors) {
		
		if (errors.hasErrors()) {
			logger.info("Register form errors encountered");
			return "/auth/registerForm";
		}
			
		CMSUser cmsUser = registerForm.toCMSUser();
		cmsUser.setPassword(passwordEncoder.encode(cmsUser.getPassword()));
		cmsUser.setRole("ROLE_CMSUSER");
		
		try{
			cmsUserRepository.save(cmsUser);
			
			model.addAttribute("username", cmsUser.getUsername());
			model.addFlashAttribute("cmsUser", cmsUser);

			// When InternalResourceViewResolver sees the "redirect: " prefix
			// on the view specification, it will redirect 
			return "redirect:/auth/profile/{username}";
			
		} catch (DataIntegrityViolationException dee) {			
			String message = "Error: " + dee.getMostSpecificCause().getMessage();
			
			model.addFlashAttribute("message", message);
			return "redirect:/auth/register";
		}	
	}
	
	@RequestMapping(value="/profile/{username}", method=RequestMethod.GET)
	public String showSpitterProfile(
			@PathVariable String username,
			Model model) {
				
		if(!model.containsAttribute("spitter")){
			CMSUser cmsUser = cmsUserRepository.findByUsername(username);
		    model.addAttribute(cmsUser);			    
		}
		
		return "/auth/profile";	 
	}
}
