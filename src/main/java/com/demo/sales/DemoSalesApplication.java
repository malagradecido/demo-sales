package com.demo.sales;

import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.demo.sales.dao.ISalesDAO;
import com.demo.sales.dto.CustomerDTO;

@SpringBootApplication
public class DemoSalesApplication {
	
	private static final Logger logger = Logger.getLogger(DemoSalesApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(DemoSalesApplication.class, args);
	}
	
	@Bean
	CommandLineRunner lookup(ISalesDAO iSalesDAO) {
		return args -> {
			//iSalesDAO.viewArray();
			CustomerDTO customerDTO = new CustomerDTO(1);
			customerDTO = iSalesDAO.getCustomer(customerDTO);
			logger.info(customerDTO);
			//System.err.println(response.getGetQuoteResult());
		};
	}
}