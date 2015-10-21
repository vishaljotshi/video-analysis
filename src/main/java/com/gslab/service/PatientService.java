package com.gslab.service;
import java.io.IOException;
import java.sql.Time;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.gslab.vo.*;
public interface PatientService {

	public ContentVideoVo getProgressByName(ContentVideoVo cvo);

	public ContentVideoVo setContentVideoProgress(ContentVideoVo cvo, String json) throws JsonParseException, JsonMappingException, IOException;

	public List<ContentVideoVo> getAllVideos();

	public List<Map<String, Object>> getPopularVideoList(int percentage);

	ContentVideoVo setContentVideo(ContentVideoVo cvo, String json)
			throws JsonParseException, JsonMappingException, IOException;



	public List<Map<String, Object>> getLeastPopularVideoList(int percentage);

	public List<Map<String, Object>> getPopularByTime(String startTime,
			String endTime);

	public List<Map<String, Object>> getMostOptimisedVideoLength();

	public List<Map<String, Object>> getMostWatchedVideoByPatient(int userId);

	public List<Map<String, Object>> getTraffic();

	public List<Map<String, Object>> getAllVideoProgress();

	public List<Map<String, Object>> getLanguages();

	public List<Map<String, Object>> getPopularByLanguage(String language);

	
}
