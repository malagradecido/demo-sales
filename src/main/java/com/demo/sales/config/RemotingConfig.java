package com.demo.sales.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.remoting.rmi.RmiServiceExporter;

import com.demo.sales.services.ISalesService;

@Configuration
public class RemotingConfig {

	@Autowired
	ISalesService salesService;
	
	@Bean
	public RmiServiceExporter rmiSalesService(ISalesService salesService) {
		RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
		rmiServiceExporter.setServiceName("salesService");
		rmiServiceExporter.setService(salesService);
		rmiServiceExporter.setServiceInterface(ISalesService.class);
		rmiServiceExporter.setRegistryPort(1199);
		return rmiServiceExporter;
	}
	
	@Bean(name = "/HessianSalesService")
	public HessianServiceExporter hessianExportedSalesService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(salesService);
		exporter.setServiceInterface(ISalesService.class);
		return exporter;
	}
	
	@Bean(name = "/HttpInvokerSalesService")
	public HttpInvokerServiceExporter httpInvokerExportedSalesService() {
		HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
		exporter.setService(salesService);
		exporter.setServiceInterface(ISalesService.class);
		return exporter;
	}

}