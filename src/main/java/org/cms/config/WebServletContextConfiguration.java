package org.cms.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import org.cms.config.annotation.WebController;
import org.cms.config.annotation.WebControllerAdvice;

/* Take over servlet-context.xml configuration */
@Configuration
@EnableWebMvc 
@ComponentScan(
        basePackages = "org.cms.web",
        useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter({
        	WebController.class,
        	WebControllerAdvice.class})
)
public class WebServletContextConfiguration extends WebMvcConfigurerAdapter {
	
	@Autowired
	SpringValidatorAdapter validator;

	/*
	 * Declare view resolver
	 * Resolves template views from logical view names
	 */
	@Bean
	public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine);
		return viewResolver;
	}	
	
	/*
	 * Declare template engine 
	 * Processes the templates and renders results
	 */
	@Bean
	public SpringTemplateEngine templateEngine(TemplateResolver templateResolver) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);	
		templateEngine.addDialect(new SpringSecurityDialect());
		return templateEngine;
	}	
		
	/* 
	 * Declare template resolver
	 * Locates templates 
	 */
	@Bean
	public TemplateResolver templateResolver() {
		TemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");		
		return templateResolver;
	}	
	
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {	    
	    super.addResourceHandlers(registry);
	}

	@Bean
	public MultipartResolver multipartResolver() throws IOException {
		return new StandardServletMultipartResolver();
	}
	
	// Bypass controller and map directly
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");		
	}	
	
	// Tell web MVC to use the validator in RootContextConfiguration
	@Override
    public Validator getValidator()
    {
        return this.validator;
    }
}
