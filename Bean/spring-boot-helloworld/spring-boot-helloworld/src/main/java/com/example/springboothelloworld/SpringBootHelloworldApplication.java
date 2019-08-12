package com.example.springboothelloworld;

import com.example.springboothelloworld.servlet.HelloFilter;
import com.example.springboothelloworld.servlet.HelloListener;
import com.example.springboothelloworld.servlet.HelloServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootHelloworldApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHelloworldApplication.class, args);
	}

	// register Servlet
	@Bean
	public ServletRegistrationBean getServletRegistrationBean(){
		ServletRegistrationBean servletBean = new ServletRegistrationBean(new HelloServlet());
		servletBean.addUrlMappings("/helloServlet");

		return servletBean;
	}

	// Register Filter
	@Bean
	public FilterRegistrationBean getFilterRegistrationBean(){

		FilterRegistrationBean filterRegistrationBean =  new FilterRegistrationBean(new HelloFilter());

		// Add filter path
		filterRegistrationBean.addUrlPatterns("/helloServlet");
		return filterRegistrationBean;

	}

	@Bean
	public ServletListenerRegistrationBean<HelloListener> getServletListenerRegistrationBean(){
		ServletListenerRegistrationBean lisnerBean = new ServletListenerRegistrationBean(new HelloListener());
		return lisnerBean;
	}

}
