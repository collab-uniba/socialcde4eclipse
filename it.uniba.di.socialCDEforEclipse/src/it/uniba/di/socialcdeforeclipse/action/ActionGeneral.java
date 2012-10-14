package it.uniba.di.socialcdeforeclipse.action;


import java.util.ArrayList;

import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

import it.uniba.di.socialcdeforeclipse.controller.Controller;



public class ActionGeneral implements Listener , KeyListener {
	
	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		
		ArrayList<String> widgetProfile;
		widgetProfile = new ArrayList<>();
		widgetProfile.add("profilePanel");
		widgetProfile.add("labelLogout");
		Widget widget =  event.widget;
		System.out.println("Window name " + Controller.getWindowName()); 
		switch (Controller.getWindowName()) {
			case "Registration":
				new ActionRegistrationPanel(widget, event.type); 
				break;
			case "Login":
				new ActionLoginPanel(widget, event.type); 
				break;
			case "Profile":
				System.out.println("Action profile avviata"); 
				new ActionProfile(widget, event.type); 
				break;
			default:
				break;
			case "Home":
				if(widgetProfile.contains(widget.getData("ID_action").toString()))
				{
					new ActionProfile(widget, event.type); 
				}
				new ActionHomePanel(widget, event.type); 
				break;
			case "Settings":
				if(widgetProfile.contains(widget.getData("ID_action").toString()))
				{
					new ActionProfile(widget, event.type); 
				}
				new ActionSettingPanel(widget, event.type); 
				break;
		}
	
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		Widget widget = e.widget; 
		
		switch (Controller.getWindowName()) {
		case "Registration":
			new ActionRegistrationPanel(widget, e.keyCode); 
			break;
		case "Login":
			new ActionLoginPanel(widget, e.keyCode); 
			break;
		case "Profile":
			new ActionProfile(widget, e.keyCode); 
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
