package com.workflow.oozie.nodes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JOIN")

public class Join {
	  @XmlAttribute(name = "name", required = true)
	    protected String name;
	    @XmlAttribute(name = "to", required = true)
	    protected String to;

	    /**
	     * Gets the value of the name property.
	     * 
	     * @return
	     *     possible object is
	     *     {@link String }
	     *     
	     */
	    public String getName() {
	        return name;
	    }

	    /**
	     * Sets the value of the name property.
	     * 
	     * @param value
	     *     allowed object is
	     *     {@link String }
	     *     
	     */
	    public void setName(String value) {
	        this.name = value;
	    }

	    /**
	     * Gets the value of the to property.
	     * 
	     * @return
	     *     possible object is
	     *     {@link String }
	     *     
	     */
	    public String getTo() {
	        return to;
	    }

	    /**
	     * Sets the value of the to property.
	     * 
	     * @param value
	     *     allowed object is
	     *     {@link String }
	     *     
	     */
	    public void setTo(String value) {
	        this.to = value;
	    }

	}

