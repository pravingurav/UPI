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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

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
 *         &lt;element name="Payer">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Bal">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Data">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="ki" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="addr" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="seqNum" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
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
        "payer"
})
@XmlRootElement(name = "RespBalEnq")
public class RespBalEnq {

    @XmlElement(name = "Head", required = true)
    protected HeadType head;
    @XmlElement(name = "Resp", required = true)
    protected RespType resp;
    @XmlElement(name = "Txn", required = true)
    protected PayTrans txn;
    @XmlElement(name = "Payer", required = true)
    protected RespBalEnq.Payer payer;

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
     * Gets the value of the payer property.
     *
     * @return possible object is
     * {@link RespBalEnq.Payer }
     */
    public RespBalEnq.Payer getPayer() {
        return payer;
    }

    /**
     * Sets the value of the payer property.
     *
     * @param value allowed object is
     *              {@link RespBalEnq.Payer }
     */
    public void setPayer(RespBalEnq.Payer value) {
        this.payer = value;
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
     *         &lt;element name="Bal">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Data">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="ki" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="addr" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="seqNum" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "bal"
    })
    public static class Payer {

        @XmlElement(name = "Bal", required = true)
        protected RespBalEnq.Payer.Bal bal;
        @XmlAttribute
        protected String addr;
        @XmlAttribute
        protected String name;
        @XmlAttribute
        protected String seqNum;
        @XmlAttribute
        protected String type;
        @XmlAttribute
        protected String code;

        /**
         * Gets the value of the bal property.
         *
         * @return possible object is
         * {@link RespBalEnq.Payer.Bal }
         */
        public RespBalEnq.Payer.Bal getBal() {
            return bal;
        }

        /**
         * Sets the value of the bal property.
         *
         * @param value allowed object is
         *              {@link RespBalEnq.Payer.Bal }
         */
        public void setBal(RespBalEnq.Payer.Bal value) {
            this.bal = value;
        }

        /**
         * Gets the value of the addr property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getAddr() {
            return addr;
        }

        /**
         * Sets the value of the addr property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setAddr(String value) {
            this.addr = value;
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
         * Gets the value of the seqNum property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getSeqNum() {
            return seqNum;
        }

        /**
         * Sets the value of the seqNum property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setSeqNum(String value) {
            this.seqNum = value;
        }

        /**
         * Gets the value of the type property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setType(String value) {
            this.type = value;
        }

        /**
         * Gets the value of the code property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCode() {
            return code;
        }

        /**
         * Sets the value of the code property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCode(String value) {
            this.code = value;
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
         *         &lt;element name="Data">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="ki" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
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
                "data"
        })
        public static class Bal {

            @XmlElement(name = "Data", required = true)
            protected RespBalEnq.Payer.Bal.Data data;

            /**
             * Gets the value of the data property.
             *
             * @return possible object is
             * {@link RespBalEnq.Payer.Bal.Data }
             */
            public RespBalEnq.Payer.Bal.Data getData() {
                return data;
            }

            /**
             * Sets the value of the data property.
             *
             * @param value allowed object is
             *              {@link RespBalEnq.Payer.Bal.Data }
             */
            public void setData(RespBalEnq.Payer.Bal.Data value) {
                this.data = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * <p/>
             * <p>The following schema fragment specifies the expected content contained within this class.
             * <p/>
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="ki" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "value"
            })
            public static class Data {

                @XmlValue
                protected String value;
                @XmlAttribute
                protected String code;
                @XmlAttribute
                protected String ki;

                /**
                 * Gets the value of the value property.
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setValue(String value) {
                    this.value = value;
                }

                /**
                 * Gets the value of the code property.
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getCode() {
                    return code;
                }

                /**
                 * Sets the value of the code property.
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setCode(String value) {
                    this.code = value;
                }

                /**
                 * Gets the value of the ki property.
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getKi() {
                    return ki;
                }

                /**
                 * Sets the value of the ki property.
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setKi(String value) {
                    this.ki = value;
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
