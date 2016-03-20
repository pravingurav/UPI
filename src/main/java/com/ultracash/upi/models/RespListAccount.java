//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.03 at 06:38:41 PM GMT+05:30 
//


package com.ultracash.upi.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element name="Resp" type="{http://npci.org/upi/schema/}respType"/>
 *         &lt;element name="Txn" type="{http://npci.org/upi/schema/}payTrans"/>
 *         &lt;element name="AccountList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Account" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="CredsAllowed" type="{http://npci.org/upi/schema/}credsAllowedType" maxOccurs="3" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="accRefNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="maskedAccnumber" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="ifsc" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="mmid" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="aeba" type="{http://npci.org/upi/schema/}aebaType" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "head",
        "resp",
        "txn",
        "accountList"
})
@XmlRootElement(name = "RespListAccount")
public class RespListAccount {

    @XmlElement(name = "Head", required = true)
    protected HeadType head;
    @XmlElement(name = "Resp", required = true)
    protected RespType resp;
    @XmlElement(name = "Txn", required = true)
    protected PayTrans txn;
    @XmlElement(name = "AccountList", required = true)
    protected RespListAccount.AccountList accountList;

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
     * Gets the value of the resp property.
     *
     * @return possible object is
     * {@link RespType }
     */
    public RespType getResp() {
        return resp;
    }

    /**
     * Sets the value of the resp property.
     *
     * @param value allowed object is
     *              {@link RespType }
     */
    public void setResp(RespType value) {
        this.resp = value;
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
     * Gets the value of the accountList property.
     *
     * @return possible object is
     * {@link RespListAccount.AccountList }
     */
    public RespListAccount.AccountList getAccountList() {
        return accountList;
    }

    /**
     * Sets the value of the accountList property.
     *
     * @param value allowed object is
     *              {@link RespListAccount.AccountList }
     */
    public void setAccountList(RespListAccount.AccountList value) {
        this.accountList = value;
    }


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
     *         &lt;element name="Account" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="CredsAllowed" type="{http://npci.org/upi/schema/}credsAllowedType" maxOccurs="3" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="accRefNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="maskedAccnumber" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="ifsc" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="mmid" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="aeba" type="{http://npci.org/upi/schema/}aebaType" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "account"
    })
    public static class AccountList {

        @XmlElement(name = "Account", required = true)
        protected List<RespListAccount.AccountList.Account> account;

        /**
         * Gets the value of the account property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the account property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAccount().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link RespListAccount.AccountList.Account }
         */
        public List<RespListAccount.AccountList.Account> getAccount() {
            if (account == null) {
                account = new ArrayList<RespListAccount.AccountList.Account>();
            }
            return this.account;
        }


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
         *         &lt;element name="CredsAllowed" type="{http://npci.org/upi/schema/}credsAllowedType" maxOccurs="3" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="accRefNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="maskedAccnumber" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="ifsc" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="mmid" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="aeba" type="{http://npci.org/upi/schema/}aebaType" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "credsAllowed"
        })
        public static class Account {

            @XmlElement(name = "CredsAllowed")
            protected List<CredsAllowedType> credsAllowed;
            @XmlAttribute
            protected String accRefNumber;
            @XmlAttribute
            protected String maskedAccnumber;
            @XmlAttribute
            protected String ifsc;
            @XmlAttribute
            protected String mmid;
            @XmlAttribute
            protected String name;
            @XmlAttribute
            protected AebaType aeba;

            /**
             * Gets the value of the credsAllowed property.
             * <p/>
             * <p/>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the credsAllowed property.
             * <p/>
             * <p/>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getCredsAllowed().add(newItem);
             * </pre>
             * <p/>
             * <p/>
             * <p/>
             * Objects of the following type(s) are allowed in the list
             * {@link CredsAllowedType }
             */
            public List<CredsAllowedType> getCredsAllowed() {
                if (credsAllowed == null) {
                    credsAllowed = new ArrayList<CredsAllowedType>();
                }
                return this.credsAllowed;
            }

            /**
             * Gets the value of the accRefNumber property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getAccRefNumber() {
                return accRefNumber;
            }

            /**
             * Sets the value of the accRefNumber property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setAccRefNumber(String value) {
                this.accRefNumber = value;
            }

            /**
             * Gets the value of the maskedAccnumber property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getMaskedAccnumber() {
                return maskedAccnumber;
            }

            /**
             * Sets the value of the maskedAccnumber property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setMaskedAccnumber(String value) {
                this.maskedAccnumber = value;
            }

            /**
             * Gets the value of the ifsc property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getIfsc() {
                return ifsc;
            }

            /**
             * Sets the value of the ifsc property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setIfsc(String value) {
                this.ifsc = value;
            }

            /**
             * Gets the value of the mmid property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getMmid() {
                return mmid;
            }

            /**
             * Sets the value of the mmid property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setMmid(String value) {
                this.mmid = value;
            }

            /**
             * Gets the value of the name property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Gets the value of the aeba property.
             *
             * @return possible object is
             * {@link AebaType }
             */
            public AebaType getAeba() {
                return aeba;
            }

            /**
             * Sets the value of the aeba property.
             *
             * @param value allowed object is
             *              {@link AebaType }
             */
            public void setAeba(AebaType value) {
                this.aeba = value;
            }

            @Override
            public String toString() {
                return ReflectionToStringBuilder.reflectionToString(this);
            }
        }

        @Override
        public String toString() {
            return ReflectionToStringBuilder.reflectionToString(this);
        }
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
