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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Head" type="{http://npci.org/upi/schema/}headType"/>
 *         &lt;element name="Txn" type="{http://npci.org/upi/schema/}payTrans"/>
 *         &lt;element name="Creds" type="{http://npci.org/upi/schema/}credsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "head",
        "txn",
        "creds"
})
@XmlRootElement(name = "ReqListKeys")
public class ReqListKeys {

    @XmlElement(name = "Head", required = true)
    protected HeadType head;
    @XmlElement(name = "Txn", required = true)
    protected PayTrans txn;
    @XmlElement(name = "Creds", required = true)
    protected CredsType creds;

    /**
     * Gets the value of the head property.
     *
     * @return possible object is
     * {@link HeadType }
     */
    public HeadType getHead() {
        return head;
    }

    /**
     * Sets the value of the head property.
     *
     * @param value allowed object is
     *              {@link HeadType }
     */
    public void setHead(HeadType value) {
        this.head = value;
    }

    /**
     * Gets the value of the txn property.
     *
     * @return possible object is
     * {@link PayTrans }
     */
    public PayTrans getTxn() {
        return txn;
    }

    /**
     * Sets the value of the txn property.
     *
     * @param value allowed object is
     *              {@link PayTrans }
     */
    public void setTxn(PayTrans value) {
        this.txn = value;
    }

    /**
     * Gets the value of the creds property.
     *
     * @return possible object is
     * {@link CredsType }
     */
    public CredsType getCreds() {
        return creds;
    }

    /**
     * Sets the value of the creds property.
     *
     * @param value allowed object is
     *              {@link CredsType }
     */
    public void setCreds(CredsType value) {
        this.creds = value;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
