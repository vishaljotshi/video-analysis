package com.gslab.dao;

import java.io.IOException;
import java.sql.Time;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.transaction.annotation.Transactional;

import com.gslab.model.*;
import com.gslab.vo.*;

public interface PatientDao {
	public ContentVideo getDaoByName(ContentVideo cv);

	public ContentVideo getContentVideoDao(ContentVideo cv,String json) throws JsonParseException, JsonMappingException, IOException;

	public List<ContentVideo> getAllVideoList();

	public List<Map<String, Object>> getPopularVideoList(int percentage, String mostPopularQuery);

	public ContentVideo createNewVideo(String json);

	public List<Map<String, Object>> getPopularByTime(String startTime,
			String endTime, String popularByTimeQuery);

	public List<Map<String, Object>> getOptimisedVideoLength(String mostOptimisedVideoLengthQuery);

	List<Map<String, Object>> getMostWatchedByPatient(int userId, String mostWatchedByPatient);

	public List<Map<String, String>> getVideoTraffic(String videoTrafficByHour);

	public List<Map<String, Object>> getAllVideoProgress(String videosInProgress);

	public List<Map<String, Object>> getLanguages(String languageQuery);

	public List<Map<String, Object>> getPopularByLanguage(String language,
			String popularLanguageQuery);


}
