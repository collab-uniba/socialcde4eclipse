package it.uniba.di.collab.socialcdeforeclipse.action;

import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.WUser;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

public class ActionDynamicPeople {

	public ActionDynamicPeople(Widget widget, Event event) {
		String widgetName = widget.getData("ID_action").toString();

		switch (widgetName) {
		case "User_selected":
			if(Controller.getProxy().IsWebServiceRunning())
			{
			Controller.temporaryInformation.put("User_selected",
					(WUser) widget.getData("User_data"));
			Controller.temporaryInformation.put("User_type",
					widget.getData("User_type"));

			Controller.selectDynamicWindow(3);
			}
			else
			{
				Controller.openConnectionLostPanel("Connection Lost! \n You will be redirected to Login page."); 	
			}
			break;

		default:
			break;
		}

	}

}
