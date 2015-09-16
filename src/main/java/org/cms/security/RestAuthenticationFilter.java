package org.cms.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import org.cms.domain.CMSUser;

/**
* Processes a HTTP request's X-Auth-Token authorization headers, putting the result into the
* <code>SecurityContextHolder</code>.
*
* <p>
* In summary, this filter is responsible for processing any request that has a HTTP
* request header of <code>X-Auth-Token</code> with a token authentication scheme and 
* a Base64-encoded <code>username:password</code> token. 
* <pre>
*
* X-Auth-Token: user.hash(user)
* </pre>
*
* <p>
* @author jvwong
*/
public class RestAuthenticationFilter extends GenericFilterBean {
	
	private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationFilter.class);

	// ~ Instance fields
	// ================================================================================================
	
	private TokenAuthenticationService tokenAuthenticationService;
	
	/**
	 * Creates an instance which will authenticate puely based on 
	 * the validity of the hash
	 *
	 * @param tokenAuthenticationService The Token parsing authentication service
	 */	
	public RestAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
		Assert.notNull(tokenAuthenticationService, "authenticationManager cannot be null");
		this.tokenAuthenticationService = tokenAuthenticationService;
	}

	// ~ Methods
	// ========================================================================================================

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(this.tokenAuthenticationService, "An TokenAuthenticationService is required");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final boolean debug = logger.isDebugEnabled();

		String username = "";
		String password = "";
		String role = "";
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		CMSUser cmsUser = tokenAuthenticationService.getUser((HttpServletRequest) request);
	
		// If not null, then the token is valid so put the Authentication object
		// inside the ContextHolder
		// Otherwise just let them through as anonymous
		if(cmsUser != null){

			username = cmsUser.getUsername();
			password = cmsUser.getPassword();
			role = cmsUser.getRole();
			authorities.add(new SimpleGrantedAuthority(role));
						
			// This suffices for authentication
			// Properly setting the username and password is optional
			UsernamePasswordAuthenticationToken authResult = 
					new UsernamePasswordAuthenticationToken(username, password, authorities);
	
			// Allow subclasses to set the "details" property
			SecurityContextHolder.getContext().setAuthentication(authResult);
		} else {
			logger.info("spitter returned null");
		}
		chain.doFilter(request, response);
	}	
}
