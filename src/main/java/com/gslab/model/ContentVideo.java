package com.gslab.model;

import java.util.HashSet;
import java.util.Set;
import java.sql.Time;


import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonManagedReference;
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "content_video", catalog = "patient", uniqueConstraints = {@UniqueConstraint(columnNames = "common_key") })
public class ContentVideo implements java.io.Serializable{
	private Integer videoPk;
	private String commonKey;
	private String language;
	private Time videoLength;
	
	private Set<ContentVideoProgress> contentVideoProgress = new HashSet<ContentVideoProgress>(0);
//constructors
	public ContentVideo(){

	}

	public ContentVideo(String videoName) {
		this.commonKey = videoName;
	}
	public ContentVideo(int videoPk) {
		this.videoPk = videoPk;
	}
	

	public ContentVideo(String commonKey, String language, Time video_length,
			Set<ContentVideoProgress> contentVideoProgress) {
		this.commonKey = commonKey;
		this.language = language;
		this.videoLength = video_length;
		this.contentVideoProgress = contentVideoProgress;
	}

//getters and setters
	

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "contentVideo")
	@JsonManagedReference
	public Set<ContentVideoProgress> getContentVideoProgress() {
		return contentVideoProgress;
	}

	public void setContentVideoProgress(
			Set<ContentVideoProgress> contentVideoProgress) {
		this.contentVideoProgress = contentVideoProgress;
	}
//new------------------------getter and setter
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "video_pk", unique = true, nullable = false)
	public Integer getVideoPk() {
		return videoPk;
	}

	public void setVideoPk(Integer videoPk) {
		this.videoPk = videoPk;
	}
	
	@Column(name = "common_key", unique = true, nullable = false, length = 50)
	public String getCommonKey() {
		return commonKey;
	}

	public void setCommonKey(String commonKey) {
		this.commonKey = commonKey;
	}
	@Column(name="language")
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	@Column(name="video_length")
	public Time getVideoLength() {
		return videoLength;
	}

	public void setVideoLength(Time video_length) {
		this.videoLength = video_length;
	}

	
	
	
	
	
	
	
}
