package com.demo.sales.config;

import java.util.Properties;

import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import com.demo.sales.services.ISalesService;

@Configuration
public class RemotingConfig {

	@Bean
	public RmiServiceExporter rmiSalesService(ISalesService salesService) {
		RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
		rmiServiceExporter.setServiceName("salesService");
		rmiServiceExporter.setService(salesService);
		rmiServiceExporter.setServiceInterface(ISalesService.class);
		rmiServiceExporter.setRegistryPort(1199);
		return rmiServiceExporter;
	}

//	@Bean
//	public DispatcherServlet dispatcherServlet() {
//		return new DispatcherServlet();
//	}
//
//	@Bean
//	public ServletRegistrationBean dispatcherServletRegistration() {
//		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet());
//		registration.setLoadOnStartup(1);
//		registration.setName(
//				DispatcherServletAutoConfiguration.
//				DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
//		registration.addUrlMappings("/", "*.service");
//		return registration;
//	}
	
//	@Bean
//	public HandlerMapping hessianMapping() {
//		SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
//		Properties mappings = new Properties();
//		mappings.setProperty("/sales.service", "hessianExportedSalesService");
//		mapping.setMappings(mappings);
//		return mapping;
//	}
	
	@Bean(name = "salesService")
	public HessianServiceExporter hessianExportedSalesService(ISalesService salesService) {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(salesService);
		exporter.setServiceInterface(ISalesService.class);
		return exporter;
	}

}