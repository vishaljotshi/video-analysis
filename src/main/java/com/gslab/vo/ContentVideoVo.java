package com.gslab.vo;

import java.util.HashSet;
import java.util.Set;
import java.sql.Time;

public class ContentVideoVo {
	private String commonKey;
	private Time videoLength;
	private String language;
	private Set<ContentVideoProgressVo> contentVideoProgressVo = new HashSet<ContentVideoProgressVo>(0);

//constructors
	public ContentVideoVo() {
	}

	public ContentVideoVo(String videoName) {
		this.commonKey = videoName;
	}
//common key
	public String getCommonKey() {
		return commonKey;
	}

	public void setCommonKey(String videoName) {
		this.commonKey = videoName;
	}
//video length
	public Time getVideoLength() {
		return videoLength;
	}

	public void setVideoLength(Time videoLength) {
		this.videoLength = videoLength;
	}
	
//language
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

//contentvideoprogressVO
	public Set<ContentVideoProgressVo> getContentVideoProgressVo() {
		return contentVideoProgressVo;
	}

	
	public void setContentVideoProgressVo(
			Set<ContentVideoProgressVo> contentVideoProgressVo) {
		this.contentVideoProgressVo = contentVideoProgressVo;
	}
	
	
	
}