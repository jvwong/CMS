
package org.cms.security;

import java.util.ArrayList;
import java.util.List;

import org.cms.data.CMSUserRepository;
import org.cms.domain.CMSUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * UserDetailService that is asked by DaoAuthenticationProvider to validate
 * a UsernamePasswordAuthenticationToken created upstream of login by the 
 * UsernamePasswordAuthenticationFilter upon an AccessDeniedException
 * @author jvwong
 *
 */
@Component
public class CMSUserService implements UserDetailsService{
	private static final Logger logger = LoggerFactory.getLogger(CMSUserService.class);
	private final CMSUserRepository cmsUserRepository;
	
	@Autowired
	public CMSUserService(CMSUserRepository cmsUserRepository) {
		this.cmsUserRepository = cmsUserRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		CMSUser cmsUser = cmsUserRepository.findByUsername(username);
		
		if(cmsUser != null){
			List<GrantedAuthority> authorities = 
					new ArrayList<GrantedAuthority>();
			
			// Stub, should access the list of roles
			authorities.add(new SimpleGrantedAuthority(cmsUser.getRole()));
		
			// Spring will check against this user
			// Better to implement UserDetails and merge with Spitter
			return new User(
					cmsUser.getUsername(),
					cmsUser.getPassword(),
					authorities);
		}		
		
		throw new UsernameNotFoundException("User '" + username + "' not found.");
	}	
}
