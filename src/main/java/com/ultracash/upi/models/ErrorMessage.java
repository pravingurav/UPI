//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.03 at 06:38:41 PM GMT+05:30 
//


package com.ultracash.upi.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


/**
 * <p>Java class for errorMessage complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="errorMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorCd" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="errorDtl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "errorMessage", propOrder = {
        "errorCd",
        "errorDtl"
})
public class ErrorMessage {

    @XmlElement(required = true)
    protected String errorCd;
    @XmlElement(required = true)
    protected String errorDtl;

    /**
     * Gets the value of the errorCd property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getErrorCd() {
        return errorCd;
    }

    /**
     * Sets the value of the errorCd property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setErrorCd(String value) {
        this.errorCd = value;
    }

    /**
     * Gets the value of the errorDtl property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getErrorDtl() {
        return errorDtl;
    }

    /**
     * Sets the value of the errorDtl property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setErrorDtl(String value) {
        this.errorDtl = value;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
