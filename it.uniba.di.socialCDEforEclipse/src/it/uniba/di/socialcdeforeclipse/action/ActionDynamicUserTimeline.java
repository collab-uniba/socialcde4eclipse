package it.uniba.di.socialcdeforeclipse.action;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

public class ActionDynamicUserTimeline {

	public ActionDynamicUserTimeline(Widget widget, Event event)
	{
		String widgetName = widget.getData("ID_action").toString();
		
		switch (widgetName) {
		case "":
			
			break;

		default:
			break;
		}
		
	}
	
}
