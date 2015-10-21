package com.gslab.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BeanBase {
	String eventId;
	ContentVideoProgress cvp;
	ContentVideo cv;
	
	public BeanBase() {
	}
	public BeanBase(String eventId, ContentVideoProgress cvp, ContentVideo cv) {
		this.eventId = eventId;
		this.cvp = cvp;
		this.cv = cv;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public ContentVideoProgress getCvp() {
		return cvp;
	}
	public void setCvp(ContentVideoProgress cvp) {
		this.cvp = cvp;
	}
	public ContentVideo getCv() {
		return cv;
	}
	public void setCv(ContentVideo cv) {
		this.cv = cv;
	}
	
}
