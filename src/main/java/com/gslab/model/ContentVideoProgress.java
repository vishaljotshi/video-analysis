package com.gslab.model;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Columns;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "content_video_progress", catalog = "patient")
public class ContentVideoProgress implements java.io.Serializable {
	private Integer videoProgress_pk;
	private Integer userId;
	private Integer videoFk;
	private Integer viewedPercentage;
	private Time position;
	private ContentVideo contentVideo;
	
	public ContentVideoProgress() {
		
	}

	public ContentVideoProgress(Integer userId, Integer videoFk,
			Integer percentage, Time position, ContentVideo contentVideo) {
		this.userId = userId;
		this.videoFk = videoFk;
		this.viewedPercentage = percentage;
		this.position = position;
		this.contentVideo = contentVideo;
	}


	
	

	



	//new --------------------------
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "videoProgress_pk", unique = true, nullable = false)
	public Integer getVideoProgress_pk() {
		return videoProgress_pk;
	}

	public void setVideoProgress_pk(Integer videoProgress_pk) {
		this.videoProgress_pk = videoProgress_pk;
	}
	
	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name="position")
	public Time getPosition() {
		return position;
	}

	public void setPosition(Time position) {
		this.position = position;
	}
	
	@Column(name = "viewed_percentage")
	public Integer getViewedPercentage() {
		return viewedPercentage;
	}

	public void setViewedPercentage(Integer percentage) {
		this.viewedPercentage = percentage;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "video_fk", nullable = false)
	@JsonBackReference
	public ContentVideo getContentVideo() {
		return this.contentVideo;
	}
	
	public void setContentVideo(ContentVideo contentVideo) {
		this.contentVideo = contentVideo;
	}
	

}
