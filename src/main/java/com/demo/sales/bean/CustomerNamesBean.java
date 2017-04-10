package com.demo.sales.bean;

import org.hsqldb.jdbc.JDBCArray;

public class CustomerNamesBean {
	
	private JDBCArray firstnames;

	public JDBCArray getFirstnames() {
		return firstnames;
	}

	public void setFirstnames(JDBCArray firstnames) {
		this.firstnames = firstnames;
	}

}