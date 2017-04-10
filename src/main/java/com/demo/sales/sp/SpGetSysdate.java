package com.demo.sales.sp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.util.LinkedCaseInsensitiveMap;

public class SpGetSysdate extends StoredProcedure {

	private static final String SQL = "sysdate";
	
	public SpGetSysdate(DataSource dataSource) {
		
		setDataSource(dataSource);
		setFunction(true);
		setSql(SQL);
		//declareParameter(new SqlOutParameter("date", Types.DATE));
		compile();
	}

	public Date execute() {
		Map<String, Object> parameters = new HashMap<String, Object>(1);
		//Date sysdate = new Date();
		//parameters.put("date", sysdate);
		Map<String, Object> results = execute(parameters);
        @SuppressWarnings("unchecked")
		Date sysdate = ((ArrayList<LinkedCaseInsensitiveMap<Date>>) results.get("#result-set-1")).get(0).get("@p0");       
        return sysdate;
	}
}
