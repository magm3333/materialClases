package com.magm.web.services;

import java.io.IOException;
import java.nio.charset.Charset;

import org.json.JSONObject;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class TestUtil {
	   public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	   
	    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	        return mapper.writeValueAsBytes(object);
	    }
	    
	    public static int getIntValueFromJson(String json, String atrib) {
	    	JSONObject obj=new JSONObject(json);
	    	return obj.getInt(atrib);
	    }
	 
	  
}
