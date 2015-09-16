package org.cms.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import org.cms.domain.CMSUser;

@Service
public class TokenAuthenticationService {
	private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationService.class);

	@Autowired
	UserDetailsService userDetailsService;

	private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
	private static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;

	private final TokenHandler tokenHandler;

	@Autowired
	public TokenAuthenticationService(@Value("${token.secret}") String secret) {
		tokenHandler = new TokenHandler(DatatypeConverter.parseBase64Binary(secret));
	}

	public void addAuthentication(HttpServletResponse response, CMSUser cmsUser) {
		//user.setExpires(System.currentTimeMillis() + TEN_DAYS);
		response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(cmsUser));
	}

	public CMSUser getUser(HttpServletRequest request) {
		final String token = request.getHeader(AUTH_HEADER_NAME);
		if (token == null || token.isEmpty()){
			logger.info("token: {}", token);
			return null;
		}  
		
		return tokenHandler.parseUserFromToken(token);		
	}
}
