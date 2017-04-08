package com.demo.sales.util;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.NodeList;

public class BasicSupport {
	
	private static final <T> Set<T> convertArrayToSet(T[] array) {
		return new HashSet<T>(Arrays.asList(array));
	}
	
	public static final Integer[] deleteDuplicate(Integer[] array) {
		return convertArrayToSet(array).toArray( new Integer[array.length] );
	}

	public static final String[] deleteDuplicate(String[] array) {
		return convertArrayToSet(array).toArray( new String[array.length] );
	}
	
	public static final String convertNodeListToString(NodeList nodes) throws TransformerException {
	    DOMSource source = new DOMSource();
	    StringWriter writer = new StringWriter();
	    StreamResult result = new StreamResult(writer);
	    Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

	    for (int i = 0; i < nodes.getLength(); ++i) {
	        source.setNode(nodes.item(i));
	        transformer.transform(source, result);
	    }

	    return writer.toString();
	}
}