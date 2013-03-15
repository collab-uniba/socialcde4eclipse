package it.uniba.di.collab.socialcdeforeclipse.shared.library;

import java.lang.reflect.Type;
import java.util.Calendar;


import javax.xml.bind.DatatypeConverter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class JsonDateDeserializer implements JsonDeserializer<Calendar> {
	public Calendar deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		String s = json.getAsJsonPrimitive().getAsString();
		String dataLong = s.substring(6, s.length() - 7);
		String timezone = s.substring(19, s.length() -4 ); 
		
		Calendar date_selected = Calendar.getInstance();
		
		try {
			
		
		date_selected.setTimeInMillis(Long.parseLong(dataLong));
		
		if(timezone.contains("+"))
		{
			date_selected.set(Calendar.HOUR, date_selected.HOUR + Integer.parseInt(timezone.substring(1,3))); 
		}
		else
		{
			date_selected.set(Calendar.HOUR, date_selected.HOUR - Integer.parseInt(timezone.substring(1,3))); 
		}
		
		System.out.println("Timezone selezionato " + timezone);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Metodo non riuscito");
		}
		

		
		

	

		return date_selected;

	}

}
