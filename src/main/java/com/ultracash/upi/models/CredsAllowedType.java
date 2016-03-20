//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.03 at 06:38:41 PM GMT+05:30 
//


package com.ultracash.upi.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


/**
 * <p>Java class for credsAllowedType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="credsAllowedType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="type" use="required" type="{http://npci.org/upi/schema/}credType" />
 *       &lt;attribute name="subType" use="required" type="{http://npci.org/upi/schema/}credSubType" />
 *       &lt;attribute name="dType" use="required" type="{http://npci.org/upi/schema/}credDataTypeConstant" />
 *       &lt;attribute name="dLength" use="required" type="{http://npci.org/upi/schema/}credLengthConstant" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "credsAllowedType")
public class CredsAllowedType {

    @XmlAttribute(required = true)
    protected CredType type;
    @XmlAttribute(required = true)
    protected CredSubType subType;
    @XmlAttribute(required = true)
    protected CredDataTypeConstant dType;
    @XmlAttribute(required = true)
    protected int dLength;

    /**
     * Gets the value of the type property.
     *
     * @return possible object is
     * {@link CredType }
     */
    public CredType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is
     *              {@link CredType }
     */
    public void setType(CredType value) {
        this.type = value;
    }

    /**
     * Gets the value of the subType property.
     *
     * @return possible object is
     * {@link CredSubType }
     */
    public CredSubType getSubType() {
        return subType;
    }

    /**
     * Sets the value of the subType property.
     *
     * @param value allowed object is
     *              {@link CredSubType }
     */
    public void setSubType(CredSubType value) {
        this.subType = value;
    }

    /**
     * Gets the value of the dType property.
     *
     * @return possible object is
     * {@link CredDataTypeConstant }
     */
    public CredDataTypeConstant getDType() {
        return dType;
    }

    /**
     * Sets the value of the dType property.
     *
     * @param value allowed object is
     *              {@link CredDataTypeConstant }
     */
    public void setDType(CredDataTypeConstant value) {
        this.dType = value;
    }

    /**
     * Gets the value of the dLength property.
     */
    public int getDLength() {
        return dLength;
    }

    /**
     * Sets the value of the dLength property.
     */
    public void setDLength(int value) {
        this.dLength = value;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
