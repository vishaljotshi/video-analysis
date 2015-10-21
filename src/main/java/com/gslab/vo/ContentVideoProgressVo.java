package com.gslab.vo;

import java.sql.Time;
public class ContentVideoProgressVo {

	private Integer userId;
	private Integer viewedPercentage;
	private Time position;
	
//constructor
	public ContentVideoProgressVo() {
	}
	
	public ContentVideoProgressVo(Integer patientId, Integer percentage) {
		this.userId = patientId;
		this.viewedPercentage = percentage;
	}
//user id
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer patientId) {
		this.userId = patientId;
	}
//Viewed percentage
	public Integer getViewedPercentage() {
		return viewedPercentage;
	}
	
	public void setViewedPercentage(Integer percentage) {
		this.viewedPercentage = percentage;
	}
//position
	public Time getPosition() {
		return position;
	}

	public void setPosition(Time position) {
		this.position = position;
	}
	
	
}