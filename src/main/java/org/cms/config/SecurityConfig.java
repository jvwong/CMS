package org.cms.config;

import javax.sql.DataSource;

import org.cms.data.CMSUserRepository;
import org.cms.security.RestAuthenticationFilter;
import org.cms.security.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	CMSUserRepository cmsUserRepository;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired	
	DataSource dataSource;
	
	@Bean
	public PasswordEncoder getPasswordEncoder(){
		return new BCryptPasswordEncoder(10);
	}
	
	/*
	 * User-details services
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		auth 
			//<T extends UserDetailsService> DaoAuthenticationConfigurer<AuthenticationManagerBuilder,T>
			// userDetailsService(T userDetailsService)
			.userDetailsService(userDetailsService)		
			.passwordEncoder(getPasswordEncoder());		
	}	
		
	
	@Configuration
	@Order(1)
	public static class RestSecurityConfig extends WebSecurityConfigurerAdapter {
			
		
//		@Autowired
//		private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
//		
//		@Autowired
//		private RestAuthenticationFilter restAuthenticationFilter;
		
		@Autowired 
		TokenAuthenticationService tokenAuthenticationService;
		
		@Bean(name="authenticationManager")
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
	    }
		
		/**
		 * Secure rest page requests via interceptors
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http	
				.antMatcher("/services/rest/**")
				
				.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/services/rest/auth")
						.permitAll()
					.antMatchers("/services/rest/**")
						.hasAuthority("ROLE_CMSUSER")			
					.anyRequest()
						.authenticated() 
					.and()
					
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)					
				.and()
				
				.csrf()
					.disable()
				
				.addFilterBefore(new RestAuthenticationFilter(tokenAuthenticationService),
						UsernamePasswordAuthenticationFilter.class)
				;
		}
	}	// END RestSecurityConfig
		
	@Configuration	
	public static class FormSecurityConfig extends WebSecurityConfigurerAdapter {
				
		/**
		 * Spring security filter chain
		 * In this case, tell SPring Security to ignore the resources folder
		 */
		@Override
		public void configure(WebSecurity web){
			web
				.ignoring()
					.antMatchers("/resources/**");
		}
		
		/**
		 * Secure web page requests via interceptors
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http			
				.antMatcher("/**")
				
				.formLogin()
					.loginPage("/auth/login")					
					.permitAll()
				.and()
							
				// POST to /logout
				.logout()	
					.logoutUrl("/auth/logout")
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID", "remember-me")
					.permitAll()
	            .and()
				
				.authorizeRequests()							
					.antMatchers("/auth/register")
						.permitAll()			
					.antMatchers("/")
						.permitAll()			
					.antMatchers("/**")
						.authenticated()
//						.hasAuthority("ROLE_SPITTER")
				.and()
				
				.rememberMe()
					.tokenValiditySeconds(2419200) //28 d
					.key("myApplicationKey")
				.and()
				
				.csrf()
				//.disable()
				.and()
				
				.sessionManagement()
					.sessionFixation().changeSessionId()
					// returns invalid login/password error
					.maximumSessions(1).maxSessionsPreventsLogin(true);
		}
	}	// END FormSecurityConfig
}



