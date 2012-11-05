package it.uniba.di.socialcdeforeclipse.action;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

public class ActionDynamicPeople {

	public ActionDynamicPeople(Widget widget, Event event)
	{
		String widgetName = widget.getData("ID_action").toString();
		
		switch (widgetName) {
		case "User_selected":
			
			break;

		default:
			break;
		}
		
	}
	
}
