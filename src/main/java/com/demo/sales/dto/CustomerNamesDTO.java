package com.demo.sales.dto;

import org.hsqldb.jdbc.JDBCArray;

public class CustomerNamesDTO {
	
	private JDBCArray firstnames;

	public JDBCArray getFirstnames() {
		return firstnames;
	}

	public void setFirstnames(JDBCArray firstnames) {
		this.firstnames = firstnames;
	}

}