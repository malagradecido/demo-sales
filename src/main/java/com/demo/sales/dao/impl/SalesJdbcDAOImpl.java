package com.demo.sales.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.demo.sales.dao.ISalesDAO;
import com.demo.sales.dto.CustomerDTO;

@Component
public class SalesJdbcDAOImpl implements ISalesDAO {
	
	private static final Logger logger = Logger.getLogger(SalesJdbcDAOImpl.class);

	JdbcTemplate jdbcTemplate;
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
	
	private SimpleJdbcCall procGetCustomer;
	private SimpleJdbcCall procGetCustomers;
	
	@PostConstruct
	public void init() {

		jdbcTemplate.setResultsMapCaseInsensitive(true);
		
        this.procGetCustomer = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GET_CUSTOMER")
                .returningResultSet("result-set-1",
                BeanPropertyRowMapper.newInstance(CustomerDTO.class))
                .useInParameterNames("id")
                .declareParameters(
                        new SqlParameter("id", Types.INTEGER));
        
        this.procGetCustomers = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GET_CUSTOMERS")
                .returningResultSet("result-set-1",
                BeanPropertyRowMapper.newInstance(CustomerDTO.class))
                .useInParameterNames("firstname", "lastname")
                .declareParameters(
                        new SqlParameter("firstname", Types.VARCHAR),
                        new SqlParameter("lastname", Types.VARCHAR));
	}
	
	@Override
	public CustomerDTO getCustomer(CustomerDTO customerDTO) {
		
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(customerDTO);
		
		Map<String, Object> map = procGetCustomer.execute(sqlParameterSource);
		
		@SuppressWarnings("unchecked")
		List<CustomerDTO> customers = (ArrayList<CustomerDTO>) map.get("result-set-1");
		
		customers.forEach( customer -> logger.info(customer) );
		
		return customers.size() > 0 ? customers.get(0) : null;
	}
	
	@Override
	public List<CustomerDTO> getCustomers(CustomerDTO customerDTO) {
		
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(customerDTO);
		
		Map<String, Object> map = procGetCustomers.execute(sqlParameterSource);
		
		@SuppressWarnings("unchecked")
		List<CustomerDTO> customers = (ArrayList<CustomerDTO>) map.get("result-set-1");
		
		customers.forEach( customer -> logger.info(customer) );
		
		return customers;
	}
}