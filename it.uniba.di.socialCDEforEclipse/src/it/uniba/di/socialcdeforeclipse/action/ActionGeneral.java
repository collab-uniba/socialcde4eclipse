package it.uniba.di.socialcdeforeclipse.action;


import java.util.ArrayList;

import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

import it.uniba.di.socialcdeforeclipse.controller.Controller;



public class ActionGeneral implements Listener  {
	
	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		
		ArrayList<String> widgetProfile;
		widgetProfile = new ArrayList<>();
		widgetProfile.add("profilePanel");
		widgetProfile.add("labelAvatarProfile"); 
		widgetProfile.add("labelLogout");
		widgetProfile.add("labelPeople"); 
		Widget widget =  event.widget;
		System.out.println("Window name " + Controller.getWindowName()); 
		switch (Controller.getWindowName()) {
			case "Registration":
				new ActionRegistrationPanel(widget, event); 
				break;
			case "Login":
				new ActionLoginPanel(widget, event); 
				break;
			case "Profile":
				System.out.println("Action profile avviata"); 
				new ActionProfile(widget, event); 
				break;
			default:
				break;
			case "Home":
				if(widgetProfile.contains(widget.getData("ID_action").toString()))
				{
					new ActionProfile(widget, event); 
				}
				else
				{
					new ActionHomePanel(widget, event); 
				}
				break;
			case "Settings":
				if(widgetProfile.contains(widget.getData("ID_action").toString()))
				{
					new ActionProfile(widget, event); 
				}
				else
				{
					new ActionSettingPanel(widget, event); 
				}
				break;
		}
	
		
	}

	
	
}
