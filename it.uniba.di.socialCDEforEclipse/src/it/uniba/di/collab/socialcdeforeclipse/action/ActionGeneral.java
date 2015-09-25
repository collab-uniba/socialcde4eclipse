package it.uniba.di.collab.socialcdeforeclipse.action;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.popup.ConnLostPanel;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.WService;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.WUser;

public class ActionGeneral implements Listener {

	private HashMap<String, Object> uiData;

	@Override
	public void handleEvent(Event event) {
		

		ArrayList<String> widgetProfile;
		widgetProfile = new ArrayList();
		widgetProfile.add("profilePanel");
		widgetProfile.add("labelAvatarProfile");
		widgetProfile.add("labelLogout");
		widgetProfile.add("labelPeople");
		widgetProfile.add("labelHomeTimeline");
		widgetProfile.add("labelIterationTimeline");
		widgetProfile.add("labelInteractiveTimeline"); 
		Widget widget = event.widget;
		//System.out.println("id action trovato " + widget.getData("ID_action").toString()); 
		
		switch (Controller.getWindowName()) {
		case "Registration":
			uiData = Controller.getRegistrationPanel().getData();
			uiData.put("Event", event);
			uiData.put("Event_type", event.type);
			uiData.put("ID_action", widget.getData("ID_action").toString());
			
			
				new ActionRegistrationPanel(uiData);
			
			break;
		case "Login":
			uiData = Controller.getLoginPanel().getData();
			uiData.put("Event", event);
			uiData.put("Event_type", event.type);
			uiData.put("ID_action", widget.getData("ID_action").toString());
			
			
			new ActionLoginPanel(uiData);
			
			break;
		case "Profile":
			//System.out.println("Action profile avviata");
			new ActionProfile(widget, event);
			break;
		default:
			break;
		case "Home":
			if (widgetProfile.contains(widget.getData("ID_action").toString())) {
				new ActionProfile(widget, event);
			} else {
				uiData = Controller.getHomeWindow().getData();
				uiData.put("Event", event);
				uiData.put("Event_type", event.type);
				uiData.put("ID_action", widget.getData("ID_action").toString());
				uiData.put("service", (WService) widget.getData("service"));
				new ActionHomePanel(uiData);
			}
			break;
		case "Settings":
			if (widgetProfile.contains(widget.getData("ID_action").toString())) {
				new ActionProfile(widget, event);
			} else {
				new ActionSettingPanel(widget, event);
			}
			break;
		case "People":
			if (widgetProfile.contains(widget.getData("ID_action").toString())) {
				new ActionProfile(widget, event);
			} else {
				new ActionDynamicPeople(widget, event);
			}
			break;
		case "userTimeline":
			if (widgetProfile.contains(widget.getData("ID_action").toString())) {
				new ActionProfile(widget, event);
			} else {
				uiData = Controller.getDynamicUserWindow().getData();
				uiData.put("Event", event);
				uiData.put("Event_type", event.type);
				uiData.put("ID_action", widget.getData("ID_action").toString());

				new ActionDynamicUserTimeline(uiData);
			}
			break;
		case "homeTimeline":
			if (widgetProfile.contains(widget.getData("ID_action").toString())) {
				new ActionProfile(widget, event);
			} else {
				uiData = Controller.getHomeTimelineWindow().getData();
				uiData.put("Event", event);
				uiData.put("Event_type", event.type);
				uiData.put("ID_action", widget.getData("ID_action").toString());

				if (widget.getData("ID_action").equals("labelAvatarLink")) {
					uiData.put("User_data",
							(WUser) event.widget.getData("User_data"));
				}

				if (widget.getData("ID_action").equals("usernameLink")) {
					//System.out
					//		.println("ottengo " + widget.getData("User_data"));
					uiData.put("User_data", (WUser) widget.getData("User_data"));
				}

				new ActionHomeTimeline(uiData);
			}
			break;
		case "iterationTimeline":
			if (widgetProfile.contains(widget.getData("ID_action").toString())) {
				new ActionProfile(widget, event);
			} else {
				uiData = Controller.getInteractionTimelineWindow().getData();
				uiData.put("Event", event);
				uiData.put("Event_type", event.type);
				uiData.put("ID_action", widget.getData("ID_action").toString());

				if (widget.getData("ID_action").equals("labelAvatarLink")) {
					uiData.put("User_data", (WUser) widget.getData("User_data"));
				}

				if (widget.getData("ID_action").equals("usernameLink")) {
					uiData.put("User_data", (WUser) widget.getData("User_data"));
				}

				new ActionIterationTimeline(uiData);
			}
			break;
		case "interactiveTimeline":
			if (widgetProfile.contains(widget.getData("ID_action").toString())) {
				new ActionProfile(widget, event);
			} else {
				uiData = Controller.getInteractiveTimelineWindow().getData();
				uiData.put("Event", event);
				uiData.put("Event_type", event.type);
				uiData.put("ID_action", widget.getData("ID_action").toString());

				if (widget.getData("ID_action").equals("labelAvatarLink")) {
					uiData.put("User_data", (WUser) widget.getData("User_data"));
				}

				if (widget.getData("ID_action").equals("usernameLink")) {
					uiData.put("User_data", (WUser) widget.getData("User_data"));
				}

				new ActionInteractiveTimeline(uiData);
			}
			break;
		}

	}
	


}
