package it.uniba.di.socialcdeforeclipse.sharedLibrary;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;




public class JsonDateDeserializer implements JsonDeserializer<Date> {
	   public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
	      String s = json.getAsJsonPrimitive().getAsString();
	      String dataLong = s.substring(6, s.length()-7);
	      Date d = new Date(Long.parseLong(dataLong)); 
	      return d; 
	    
	   }

	
	}
	

