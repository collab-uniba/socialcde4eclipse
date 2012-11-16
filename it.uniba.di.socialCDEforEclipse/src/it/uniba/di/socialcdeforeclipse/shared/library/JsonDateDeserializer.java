package it.uniba.di.socialcdeforeclipse.shared.library;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;




public class JsonDateDeserializer implements JsonDeserializer<Calendar> {
	   public Calendar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
	      String s = json.getAsJsonPrimitive().getAsString();
	      String dataLong = s.substring(6, s.length()-7);
	     
	      Calendar date_selected = Calendar.getInstance(); 
	      date_selected.setTimeInMillis(Long.parseLong(dataLong));
	      //Date d = new Date(Long.parseLong(dataLong)); 
	      
	      return date_selected; 
	    
	   }

	
	}
	

