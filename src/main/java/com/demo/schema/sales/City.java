//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.04.04 a las 09:49:14 PM PET 
//


package com.demo.schema.sales;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para city.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="city">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Lima"/>
 *     &lt;enumeration value="Quito"/>
 *     &lt;enumeration value="Bogota"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "city")
@XmlEnum
public enum City {

    @XmlEnumValue("Lima")
    LIMA("Lima"),
    @XmlEnumValue("Quito")
    QUITO("Quito"),
    @XmlEnumValue("Bogota")
    BOGOTA("Bogota");
    private final String value;

    City(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static City fromValue(String v) {
        for (City c: City.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
