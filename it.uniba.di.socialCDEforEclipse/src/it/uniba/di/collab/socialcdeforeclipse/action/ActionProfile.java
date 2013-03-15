package it.uniba.di.collab.socialcdeforeclipse.action;

import java.io.InputStream;

import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.dynamic.view.DynamicPeople;
import it.uniba.di.collab.socialcdeforeclipse.staticview.LoginPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

public class ActionProfile {

	private final InputStream PATH_WALLPAPER = this.getClass().getClassLoader()
			.getResourceAsStream("images/Wallpaper.png");

	public Image getImageStream(InputStream stream) {
		return new Image(Controller.getWindow().getDisplay(), stream);

	}

	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, image.getBounds().width,
				image.getBounds().height, 0, 0, width, height);
		gc.dispose();
		image.dispose();
		return scaled;
	}

	public ActionProfile(Widget widget, Event event) {

		String widgetName = widget.getData("ID_action").toString();

		switch (widgetName) {

		case "profilePanel":
			Controller.setWindowHeight(Controller.getWindow().getSize().y);
			Controller.setWindowWidth(Controller.getWindow().getSize().x);
			Controller.getWindow().setBackgroundImage(
					resize(getImageStream(PATH_WALLPAPER),
							Controller.getWindowWidth(),
							Controller.getWindowHeight()));
			Controller.getWindow().layout();
			break;

		case "labelLogout":
			if (event.type == SWT.MouseDown && Controller.getProxy().IsWebServiceRunning()) {
				Controller.closeAllDynamicPanel();
				Controller.setCurrentUser(null);
				Controller.setCurrentUserPassword(null);
				Controller.setWindowName("Login");
				Controller.getProfilePanel().dispose(Controller.getWindow());
				Controller.setProfilePanel(null);
				Controller.setLoginPanel(new LoginPanel());
				Controller.getLoginPanel().inizialize(Controller.getWindow());
				Controller.getWindow().layout();
				Controller.setPreferences("FlagAutologin", "False");
				Controller.getServicesImage().clear(); 
				Controller.getUsersAvatar().clear(); 
			}
			else
			{
				Controller.openConnectionLostPanel("Connection Lost! \n You will be redirected to Login page."); 	
			}
			break;

		case "labelPeople":

			if (event.type == SWT.MouseDown && Controller.getProxy().IsWebServiceRunning()) {

				Controller.selectDynamicWindow(2);
				Controller.getWindow().layout();

			}
			else
			{
				Controller.openConnectionLostPanel("Connection Lost! \n You will be redirected to Login page."); 	
			}
			break;

		case "labelAvatarProfile":
			if (event.type == SWT.MouseDown && Controller.getProxy().IsWebServiceRunning()) {
				Controller.selectDynamicWindow(0);
				Controller.getWindow().layout();
			}
			else
			{
				Controller.openConnectionLostPanel("Connection Lost! \n You will be redirected to Login page."); 	
			}
			break;
		case "labelHomeTimeline":
			if (event.type == SWT.MouseDown && Controller.getProxy().IsWebServiceRunning()) {
				Controller.selectDynamicWindow(4);
				//Controller.getWindow().layout();
			}
			else
			{
				Controller.openConnectionLostPanel("Connection Lost! \n You will be redirected to Login page."); 	
			}
			break;
		case "labelIterationTimeline":
			if (event.type == SWT.MouseDown && Controller.getProxy().IsWebServiceRunning()) {
				Controller.selectDynamicWindow(5);
				Controller.getWindow().layout();
			}
			else
			{
				Controller.openConnectionLostPanel("Connection Lost! \n You will be redirected to Login page."); 	
			}
			break;
		case "labelInteractiveTimeline":
			if (event.type == SWT.MouseDown && Controller.getProxy().IsWebServiceRunning()) {
				Controller.selectDynamicWindow(6);
				Controller.getWindow().layout();
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
