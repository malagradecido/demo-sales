package com.demo.sales.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BasicSupport {
	
	private static final <T> Set<T> arrayToSet(T[] array) {
		return new HashSet<T>(Arrays.asList(array));
	}
	
	public static final Integer[] deleteDuplicate(Integer[] array) {
		return arrayToSet(array).toArray( new Integer[array.length] );
	}

	public static final String[] deleteDuplicate(String[] array) {
		return arrayToSet(array).toArray( new String[array.length] );
	}
}