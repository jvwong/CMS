package org.cms.config;

import javax.servlet.ServletContext;

/**
 * Register Spring Security Filters
 */
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.multipart.support.MultipartFilter;

public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {
	
	/**
	 * Placing MultipartFilter before Spring Security
	 * 
	 * The first option is to ensure that the MultipartFilter is specified 
	 * before the Spring Security filter. Specifying the MultipartFilter before 
	 * the Spring Security filter means that there is no authorization for 
	 * invoking the MultipartFilter which means anyone can place temporary 
	 * files on your server. However, only authorized users will be able 
	 * to submit a File that is processed by your application. In general, 
	 * this is the recommended approach because the temporary file upload should 
	 * have a negligble impact on most servers.
	 * 
	 * To ensure MultipartFilter is specified before the Spring Security filter 
	 * with java configuration, users can override beforeSpringSecurityFilterChain 
	 * as shown below:
	 */
	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		insertFilters(servletContext, new MultipartFilter());
	}

}
