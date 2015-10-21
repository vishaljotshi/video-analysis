package com.gslab.service.impl;

import java.io.IOException;
import java.rmi.dgc.Lease;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import com.gslab.dao.*;
import com.gslab.model.*;
import com.gslab.query.Query_definition;
import com.gslab.service.*;
import com.gslab.vo.*;

public class PatientServiceImpl implements PatientService,Query_definition{
	
	
	@Autowired
	PatientDao patientDao;

	@Override
	public ContentVideoVo getProgressByName(ContentVideoVo cvo) {
		String name=cvo.getCommonKey();
		
		//creating model object
		ContentVideo cv=new ContentVideo(name);
		cv=patientDao.getDaoByName(cv);
		cvo.setLanguage(cv.getLanguage());	//set video length and language of VO object , Name is already set
		cvo.setVideoLength(cv.getVideoLength());
		cvo=fillContentVideoVo(cv,cvo);
		//filling cvo object
		
		
		return cvo;
	}

public ContentVideoVo fillContentVideoVo(ContentVideo cv,ContentVideoVo cvo) {

	Set<ContentVideoProgressVo> cvpo=new HashSet<ContentVideoProgressVo>(0);
	Set<ContentVideoProgress> cvp=cv.getContentVideoProgress();
	
	Iterator<ContentVideoProgress> contentVideoProgressIterator= (Iterator<ContentVideoProgress>) cvp.iterator();
	while(contentVideoProgressIterator.hasNext()){
		ContentVideoProgress cvpTemp=contentVideoProgressIterator.next();
		ContentVideoProgressVo cvpoTemp=new ContentVideoProgressVo();
		
		cvpoTemp.setUserId(cvpTemp.getUserId());
		cvpoTemp.setViewedPercentage(cvpTemp.getViewedPercentage());
		cvpoTemp.setPosition(cvpTemp.getPosition());
		
		cvpo.add(cvpoTemp);
	}
	cvo.setContentVideoProgressVo(cvpo);
		return cvo;
		
	}
//create progress handles posted json data
	@Override
	public ContentVideoVo setContentVideoProgress(ContentVideoVo cvo,
			String json) throws JsonParseException, JsonMappingException, IOException {
		
		Map<String,String> map=new HashMap<String,String>();
		ObjectMapper inputObject=new ObjectMapper();
		try{
			map=inputObject.readValue(json, new TypeReference<HashMap<String, String>>() {});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//creating model object
		ContentVideo cv=new ContentVideo(map.get("commonKey"));
		//add progress of an existing video
		cv=patientDao.getContentVideoDao(cv, json);
		//filling cvo object
		cvo.setCommonKey(cv.getCommonKey());
		cvo.setLanguage(cv.getLanguage());
		cvo.setVideoLength(cv.getVideoLength());
		cvo=fillContentVideoVo(cv,cvo);

		return cvo;
	}
//create video handles posted json data
	@Override
	public ContentVideoVo setContentVideo(ContentVideoVo cvo,
			String json) throws JsonParseException, JsonMappingException, IOException {
		

		//creating model object
		ContentVideo cv=new ContentVideo();

		cv=patientDao.createNewVideo(json);

		//filling cvo object
		cvo.setCommonKey(cv.getCommonKey());
		cvo.setLanguage(cv.getLanguage());
		cvo.setVideoLength(cv.getVideoLength());
		cvo=fillContentVideoVo(cv,cvo);

		return cvo;


	}

	@Override
	public List<ContentVideoVo> getAllVideos() {
		List<ContentVideo> listDao=patientDao.getAllVideoList();	//contains cv object list
		List<ContentVideoVo> listVo=new ArrayList<ContentVideoVo>();	//empty cvO object list
		
		Iterator<ContentVideo> listIterator=listDao.iterator();
		while(listIterator.hasNext()){
			ContentVideo cv=listIterator.next();	//get cv object
			ContentVideoVo cvo=new ContentVideoVo();	//instantiate a cVO object
			cvo.setCommonKey(cv.getCommonKey());	//set video name to cVO object
			cvo.setLanguage(cv.getLanguage());	//set langauge to CVO
			cvo.setVideoLength(cv.getVideoLength());
			cvo=fillContentVideoVo(cv, cvo);	//fill the progress in new cVO
			listVo.add(cvo);
			
		}
		
		return  listVo;
	}

	@Override
	public List<Map<String, Object>> getPopularVideoList(int percentage) {
		
		return patientDao.getPopularVideoList(percentage,MOST_POPULAR_QUERY);
	}

	@Override
	public List<Map<String, Object>> getLeastPopularVideoList(int percentage) {
		
		return patientDao.getPopularVideoList(percentage,LEAST_POPULAR_QUERY);
	}

	@Override
	public List<Map<String, Object>> getPopularByTime(String startTime,String endTime) {
		return patientDao.getPopularByTime(startTime,endTime,POPULAR_BY_TIME_QUERY);
	}

	@Override
	public List<Map<String, Object>> getMostOptimisedVideoLength() {
		
		return patientDao.getOptimisedVideoLength(MOST_OPTIMISED_VIDEO_LENGTH_QUERY);
	}

	@Override
	public List<Map<String, Object>> getMostWatchedVideoByPatient(int userId) {
	
		return  patientDao.getMostWatchedByPatient(userId,MOST_WATCHED_BY_PATIENT);
	}

	@Override
	public List<Map<String, Object>> getTraffic() {
		return patientDao.getOptimisedVideoLength(VIDEO_TRAFFIC_BY_HOUR);
	}

	@Override
	public List<Map<String, Object>> getAllVideoProgress() {
	
		return patientDao.getAllVideoProgress(VIDEOS_IN_PROGRESS);
	}

	@Override
	public List<Map<String, Object>> getLanguages() {
	
		return patientDao.getAllVideoProgress(LANGUAGE_QUERY);
	}

	@Override
	public List<Map<String, Object>> getPopularByLanguage(String language) {
		return patientDao.getPopularByLanguage(language,POPULAR_LANGUAGE_QUERY);
	}

}
