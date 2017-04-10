package com.demo.sales.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import com.demo.sales.bean.CustomerBean;
import com.demo.sales.bean.CustomerNamesBean;
import com.demo.sales.bean.InvoiceBean;
import com.demo.sales.bean.ItemBean;
import com.demo.sales.dao.ISalesDAO;
import com.demo.sales.sp.SpGetInvoiceDetail;
import com.demo.sales.sp.SpGetSysdate;
import com.demo.sales.sp.params.GetInvoiceDetailParams;

@Component
public class SalesJdbcDAOImpl implements ISalesDAO {
	
	private static final Logger logger = Logger.getLogger(SalesJdbcDAOImpl.class);

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall procGetCustomer;
	private SimpleJdbcCall procGetCustomers;
	private SimpleJdbcCall procGetFirstnames;
	private SimpleJdbcInsert insertInvoice;
	private SimpleJdbcInsert insertItem;
	private SpGetSysdate spGetSysdate;
	private SpGetInvoiceDetail spGetInvoiceDetail;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	public void init() {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.jdbcTemplate.setResultsMapCaseInsensitive(true);
		
        this.procGetCustomer = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GET_CUSTOMER")
                .returningResultSet("result-set-1",
                BeanPropertyRowMapper.newInstance(CustomerBean.class))
                .useInParameterNames("id")
                .declareParameters(
                        new SqlParameter("id", Types.INTEGER));
        
        this.procGetCustomers = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GET_CUSTOMERS")
                .returningResultSet("result-set-1",
                BeanPropertyRowMapper.newInstance(CustomerBean.class))
                .useInParameterNames("firstname", "lastname")
                .declareParameters(
                        new SqlParameter("firstname", Types.VARCHAR),
                        new SqlParameter("lastname", Types.VARCHAR));
        
        this.procGetFirstnames = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GET_FIRSTNAMES")
                .returningResultSet("result-set-1",
                BeanPropertyRowMapper.newInstance(CustomerNamesBean.class))
                .useInParameterNames("ids")
                .declareParameters(
                        new SqlParameter("ids", Types.ARRAY));
        
        this.insertInvoice = new SimpleJdbcInsert(dataSource)
        		.withTableName("invoice");
        
        this.insertItem = new SimpleJdbcInsert(dataSource)
        		.withTableName("item");
        
        this.spGetSysdate = new SpGetSysdate(dataSource);
        
        this.spGetInvoiceDetail = new SpGetInvoiceDetail(dataSource);
	}
	
	@Override
	public CustomerBean getCustomer(CustomerBean customerDTO) {
		
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(customerDTO);
		
		Map<String, Object> map = procGetCustomer.execute(sqlParameterSource);
		
		@SuppressWarnings("unchecked")
		List<CustomerBean> customers = (ArrayList<CustomerBean>) map.get("result-set-1");
		
		customers.forEach( customer -> logger.info("1.customer: " + customer) );
		
		return customers.size() > 0 ? customers.get(0) : null;
	}
	
	@Override
	public List<CustomerBean> getCustomers(CustomerBean customerDTO) {
		
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(customerDTO);
		
		Map<String, Object> map = procGetCustomers.execute(sqlParameterSource);
		
		@SuppressWarnings("unchecked")
		List<CustomerBean> customers = (ArrayList<CustomerBean>) map.get("result-set-1");
		
		//customers.forEach( customer -> logger.info("2.customer: " + customer) );
		
		return customers;
	}
	
	@Override
	public List<String> getCustomerNamesByIds(Integer[] ids) {

		SqlParameterSource parameters =
				new MapSqlParameterSource("ids", ids);
		
		Map<String, Object> map = procGetFirstnames.execute(parameters);
		List<String> names = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		List<CustomerNamesBean> namesJdbcArray = (ArrayList<CustomerNamesBean>) map.get("result-set-1");
		
		if(namesJdbcArray != null) {
			namesJdbcArray.forEach( nameJdbcArray -> {
				
				Object[] arrNames = nameJdbcArray.getFirstnames() == null ? 
						new Object[]{} : nameJdbcArray.getFirstnames().getArrayInternal();
				
				for (Object name : arrNames) {
					names.add(name.toString());
				}
			});
		}
		
		return names;
	}
	
	@Override
	public InvoiceBean insertInvoice(InvoiceBean invoiceDTO) {
		
		//SqlParameterSource parameters = new BeanPropertySqlParameterSource(invoiceDTO);
		
		Date date = spGetSysdate.execute();
		logger.info("fecha--> " + date);
		
		Map<String, Object> parameters = new HashMap<String, Object>(3);
		parameters.put("id", invoiceDTO.getId());
        parameters.put("customerId", invoiceDTO.getCustomerId());
        parameters.put("total", invoiceDTO.getTotal());
        insertInvoice.execute(parameters);  
		
		return invoiceDTO;
	}
	
	@Override
	public void insertItem(ItemBean itemDTO) {
		
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(itemDTO);
		insertItem.execute(parameters);
	}
	
	@Override
	public List<InvoiceBean> getInvoiceDetail(InvoiceBean invoiceDTO) {
		
		GetInvoiceDetailParams params = new GetInvoiceDetailParams();
		params.setIds(invoiceDTO.getIds());
		List<InvoiceBean> invoicesDTO = spGetInvoiceDetail.execute(params);
		
		return invoicesDTO;
	}
	
}