package com.gslab.amqp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.gslab.model.BeanBase;
import com.gslab.model.ContentVideoProgress;
import com.gslab.service.PatientService;
import com.gslab.service.impl.PatientServiceImpl;
import com.gslab.vo.*;

public class MessageListenerAmqp implements MessageListener {

	@Autowired PatientService patientService;

	public void onMessage(Message message) {

		String jsonString= new String(message.getBody());
		System.out.println("Message reveived ; "+jsonString);
		
		Map<String,String> map=new HashMap<String,String>();
		ObjectMapper inputObject=new ObjectMapper();
		try{
			map=inputObject.readValue(jsonString, new TypeReference<HashMap<String, String>>() {});
			System.out.println("Event Id : "+map.get("eventId"));
			
			if(map.get("eventId").equals("addVideo")){
				patientService.setContentVideo(new ContentVideoVo(),jsonString);
			}
			else if (map.get("eventId").equals("patientProgress")) {
				patientService.setContentVideoProgress(new ContentVideoVo(),jsonString);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	/*	Map<String,Object> map = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			map = mapper.readValue(jsonString, new TypeReference<HashMap>(){});
			System.out.println(map.get("eventId"));
			ContentVideoProgress cvp=mapper.readValue(map.get("ContentVideoProgress").toString(), ContentVideoProgress.class) ;
			System.out.println(cvp.getUserId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		System.out.println(map);


		ObjectMapper mapper = new ObjectMapper();
		try {
			BeanBase bb=mapper.readValue(jsonString, BeanBase.class);
			System.out.println(bb.getEventId());
			System.out.println(bb.getCv());
			System.out.println(bb.getCvp());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
}