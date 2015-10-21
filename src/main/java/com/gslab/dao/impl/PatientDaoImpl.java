package com.gslab.dao.impl;

import java.io.IOException;
import java.sql.Time;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;


import com.gslab.dao.*;
import com.gslab.model.*;
import com.gslab.util.HibernateUtil;
import com.gslab.vo.*;

@Service
public class PatientDaoImpl implements PatientDao{

	@Autowired
	SessionFactory sessionFactory;
	
	

	@Override
	@Transactional
	public ContentVideo getDaoByName(ContentVideo cv) {
		
		Session session=sessionFactory.getCurrentSession();
		
		String videoName=cv.getCommonKey();
		
		Criteria cr = session.createCriteria(ContentVideo.class);
		cr.add(Restrictions.eq("commonKey", videoName));
		cv=(ContentVideo) cr.uniqueResult();
		
		return cv;
	}

	//handles posted data
	
	@Transactional
	@Override
	public ContentVideo getContentVideoDao(ContentVideo cv,String json) throws JsonParseException, JsonMappingException, IOException {
		String videoName=cv.getCommonKey();
		Session session = sessionFactory.getCurrentSession();
		
		Criteria cr = session.createCriteria(ContentVideo.class);
		cr.add(Restrictions.eq("commonKey", videoName));
		System.out.println(videoName);
		ContentVideo cont=(ContentVideo) cr.list().listIterator().next();
		
		ObjectMapper mapper = new ObjectMapper();
		ContentVideoProgress cvp=mapper.readValue(json, ContentVideoProgress.class);
		//search for existing progress
		Query query=session.createSQLQuery("select videoProgress_pk from content_video_progress cvp,content_video cv where cv.video_pk=cvp.video_fk and common_key='"+cont.getCommonKey()+"' and user_id='"+cvp.getUserId()+"';");
		try {
			//video found , initiate count update=count+1;
			int pk=(int)query.list().iterator().next();
			int i=session.createSQLQuery("update content_video_progress set  watch_count_video=watch_count_video+1,viewed_percentage="+cvp.getViewedPercentage()+",position='"+cvp.getPosition() +"' where videoProgress_pk="+pk).executeUpdate();
			System.out.println("updated!");
		} catch (Exception e) {
			// TODO: already existing video not found, save the current object

			cvp.setContentVideo(cont);
			session.save(cvp);
			System.out.println("added");
		}
		return cont;
	}



	@Transactional
	@Override
	public List<ContentVideo> getAllVideoList() {
		Session session = sessionFactory.getCurrentSession();

		
		String hql = "FROM ContentVideo";
		Query query = session.createQuery(hql);
		List result = query.list();
		

		return result;
	}

	@Transactional
	@Override
	public List<Map<String, Object>> getPopularVideoList(int percentage,String QUERY) {
		
		Session session=sessionFactory.getCurrentSession();

		Query query=session.createSQLQuery(QUERY).setParameter("percentage", percentage);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String,Object>> aliasToValueMapList=query.list();
		
		return aliasToValueMapList;
	}

	@Transactional
	@Override
	public ContentVideo createNewVideo(String json) {
		Session session = sessionFactory.getCurrentSession();
		
		ObjectMapper mapper = new ObjectMapper();
		ContentVideo cv;
		try {
			cv = mapper.readValue(json, ContentVideo.class);
			session.save(cv);
			return cv;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	return null;
		
	}
	@Transactional
	@Override
	public List<Map<String, Object>> getPopularByTime(String startTime,String endTime, String QUERY) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createSQLQuery(QUERY).setParameter("startTime", startTime).setParameter("endTime", endTime);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String,Object>> aliasToValueMapList=query.list();
		
		return aliasToValueMapList;
	}
@Transactional
	@Override
	public List<Map<String, Object>> getOptimisedVideoLength(String mostOptimisedVideoLengthQuery) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createSQLQuery(mostOptimisedVideoLengthQuery);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String,Object>> aliasToValueMapList=query.list();
		
		return aliasToValueMapList;
	
	}


@Transactional
@Override
public List<Map<String, Object>> getMostWatchedByPatient(int userId,
		String mostWatchedByPatient) {
	Session session=sessionFactory.getCurrentSession();
	Query query=session.createSQLQuery(mostWatchedByPatient).setParameter("userId", userId);
	query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
	List<Map<String,Object>> aliasToValueMapList=query.list();
	return aliasToValueMapList;
}

@Override
public List<Map<String, String>> getVideoTraffic(String videoTrafficByHour) {
	return null;
}
@Transactional
@Override
public List<Map<String, Object>> getAllVideoProgress(String videosInProgress) {
	Session session=sessionFactory.getCurrentSession();

	Query query=session.createSQLQuery(videosInProgress);
	query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
	List<Map<String,Object>> aliasToValueMapList=query.list();
	
	return aliasToValueMapList;
}

@Transactional
@Override
public List<Map<String, Object>> getLanguages(String languageQuery) {
	return null;
}


@Transactional
@Override
public List<Map<String, Object>> getPopularByLanguage(String language,
		String popularLanguageQuery) {
	Session session=sessionFactory.getCurrentSession();
	Query query=session.createSQLQuery(popularLanguageQuery).setParameter("language", language);
	query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
	List<Map<String,Object>> aliasToValueMapList=query.list();
	return aliasToValueMapList;
}

	
}
