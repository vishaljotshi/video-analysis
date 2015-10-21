package com.gslab.controller;

import java.io.IOException;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gslab.exception.CustomGenericException;
import com.gslab.query.Query_definition;
import com.gslab.service.PatientService;
import com.gslab.vo.ContentVideoVo;




@Controller
@RequestMapping("/videos")
public class PatientContentAnalysisController implements Query_definition{
	ApplicationContext context = new ClassPathXmlApplicationContext("rabbit-sender-context.xml");//loading beans
    //AmqpTemplate senderTemplate = (AmqpTemplate) context.getBean("sendMessage");// getting a reference to the sender bean
	
	@Autowired
	PatientService patientService;
	
	//get 5 most popular video
	@RequestMapping(value = "/most-popular/{per}", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> showMVideos(ModelMap m,@PathVariable("per") int percentage) {
		List<Map<String, Object>> popularVideoList= patientService.getPopularVideoList(percentage);
		
		
		return popularVideoList;
	}
	
	//get 5 least popular videos
	@RequestMapping(value = "/least-popular/{per}", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> showLVideos(ModelMap m,@PathVariable("per") int percentage) {
		List<Map<String, Object>> popularVideoList= patientService.getLeastPopularVideoList(percentage);
		
		
		return popularVideoList;
	}
	
	//most optimised length of video
	
	@RequestMapping(value = "/optimised-length", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> showMostOptimisedLength() {
		
		List<Map<String, Object>> mostOptimisedVideoLength=patientService.getMostOptimisedVideoLength();
		return mostOptimisedVideoLength;
	}
	
	@RequestMapping("/gui")
	public String displayGUI(){
		return "index";
	}
	//get popular between timing
	@RequestMapping(value = "/popular/", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> showPopular(@RequestParam("startTime") String  startTime,@RequestParam("endTime") String  endTime) {
		List<Map<String, Object>> popularList=patientService.getPopularByTime(startTime,endTime);
		return popularList;
	}
	
	
	@RequestMapping(value = "/most-watched/userid/{userId}", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> mostWatchedByPatient(@PathVariable("userId") int userId) {
		List<Map<String, Object>> mostWatchedVideo= patientService.getMostWatchedVideoByPatient(userId);
		
		
		return mostWatchedVideo;
	}
	
	//popular by language
	@RequestMapping(value = "/popular/{language}", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> popularByLanguage(@PathVariable("language") String Language) {
		List<Map<String, Object>> popularByLanguage= patientService.getPopularByLanguage(Language);
		
		
		return popularByLanguage;
	}
	
	
	//show all videos
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody List showAllVideos() {
		
		List<ContentVideoVo> videoList;
		videoList=patientService.getAllVideos();

		return videoList;
		
	}
	
	//get Languages
	@RequestMapping(value = "/languages", method = RequestMethod.GET)
	public @ResponseBody List showAllLanguages() {
		
	List<Map<String, Object>> languages= patientService.getLanguages();
	
	return languages;
	}
	
	//get video in progress list
		@RequestMapping(value = "/videoProgress", method = RequestMethod.GET)
		public @ResponseBody List<Map<String, Object>> showVideoInProgress() {
			List<Map<String, Object>> popularVideoList= patientService.getAllVideoProgress();
			
			
			return popularVideoList;
		}
	
	
	
	
	//POSTED video in JSON <add progress>
	@RequestMapping(value = "/progress", method = RequestMethod.POST)
	public @ResponseBody ContentVideoVo addProgress(@RequestBody String json) throws JsonParseException, JsonMappingException, IOException {
		
		ContentVideoVo cvo=new ContentVideoVo();
		
		cvo= patientService.setContentVideoProgress(cvo,json);
		
		//sending request json to queue
		//sendToQueue("com.gslab.log", json);
		return cvo;
		
	}
	
	//POSTED video in JSON <add video>
		@RequestMapping(value = "/add", method = RequestMethod.POST)
		public @ResponseBody ContentVideoVo addVideo(@RequestBody String json) throws JsonParseException, JsonMappingException, IOException {
			
			ContentVideoVo cvo=new ContentVideoVo();
			
			cvo= patientService.setContentVideo(cvo,json);
			
			//sending request json to queue
			//sendToQueue("com.gslab.log", json);
			return cvo;
			
		}
	
	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
	public @ResponseBody ContentVideoVo showVideosNames(@PathVariable("name") String name) {

		ContentVideoVo cvo=new ContentVideoVo(name);
		cvo= patientService.getProgressByName(cvo);
		return cvo;

	}
	//traffic analysis
	@RequestMapping(value = "/traffic/", method = RequestMethod.GET)
	public @ResponseBody List trafficProgress() {
		
		List<Map<String, Object>> videoTraffic= patientService.getTraffic();
		
		return videoTraffic;
		
	}
	
	//exception handling


	@ExceptionHandler(Exception.class)
	public  ModelAndView handleAllException(Exception ex) {

		ModelAndView model = new ModelAndView("hello");
		model.addObject("exception", ex.toString().replaceAll("\"", "\\\\\""));
		return model;

	}


}