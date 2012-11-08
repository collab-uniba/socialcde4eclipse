/*
* Classname Controller
*
* Version info 1.0.0.0
*
* Copyright notice
 * 
 * */
package it.uniba.di.socialcdeforeclipse.controller;

import java.util.HashMap;
import java.util.prefs.Preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;


import it.uniba.di.socialcdeforeclipse.dynamicView.DynamicHome;
import it.uniba.di.socialcdeforeclipse.dynamicView.DynamicInteractionTimeline;
import it.uniba.di.socialcdeforeclipse.dynamicView.DynamicInteractiveTimeline;
import it.uniba.di.socialcdeforeclipse.dynamicView.DynamicPeople;
import it.uniba.di.socialcdeforeclipse.dynamicView.DynamicUserTimeline;
import it.uniba.di.socialcdeforeclipse.dynamicView.SettingPanel;
import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.*;
import it.uniba.di.socialcdeforeclipse.staticView.LoginPanel;
import it.uniba.di.socialcdeforeclipse.staticView.ProfilePanel;
import it.uniba.di.socialcdeforeclipse.staticView.ProgressBarThread;
import it.uniba.di.socialcdeforeclipse.staticView.RegistrationPanel;
import it.uniba.di.socialcdeforeclipse.views.SquareButtonService;

public class Controller {

	//attributes
	private static ProxyWrapper proxy = null; 
	
	private static Composite window = null;
	
	private static int windowWidth = 0;
	
	private static int windowHeight = 0; 
	
	private static int scrollHeight = 0; 
	
	private static String windowName = null;
	
	private static RegistrationPanel registrationPanel = null;
	
	private static LoginPanel loginPanel = null; 
	
	private static ProfilePanel profilePanel = null;

	private static WUser currentUser = null;
	
	private static String currentUserPassword = null; 
	

	private static DynamicHome homeWindow = null;
	
	private static DynamicInteractionTimeline interactionTimelineWindow = null;
	
	private static DynamicInteractiveTimeline interactiveTimelineWindow = null;
	
	private static DynamicPeople peopleWindow = null;
	
	private static SettingPanel settingWindow = null;
	
	private static DynamicUserTimeline dynamicUserWindow = null;
	
	public static HashMap<String, Object> temporaryInformation =  new HashMap<String,Object>();  
	
	public static int progressBarPositionX = 0;
	
	public static int progressBarPositionY = 0;
	
	//getter and setter for attributes
	

	
	
	
	public static LoginPanel getLoginPanel() {
		return loginPanel;
	}
	
	

	public static int getProgressBarPositionX() {
		return progressBarPositionX;
	}



	public static void setProgressBarPositionX(int progressBarPositionX) {
		Controller.progressBarPositionX = progressBarPositionX;
	}



	public static int getProgressBarPositionY() {
		return progressBarPositionY;
	}



	public static void setProgressBarPositionY(int progressBarPositionY) {
		Controller.progressBarPositionY = progressBarPositionY;
	}



	public static DynamicUserTimeline getDynamicUserWindow() {
		return dynamicUserWindow;
	}



	public static void setDynamicUserWindow(DynamicUserTimeline dynamicUserWindow) {
		Controller.dynamicUserWindow = dynamicUserWindow;
	}



	public static int getScrollHeight() {
		return scrollHeight;
	}

	public static void setScrollHeight(int scrollHeight) {
		Controller.scrollHeight = scrollHeight;
	}

	public static int getWindowWidth() {
		return windowWidth;
	}


	public static void setWindowWidth(int windowWidth) {
		Controller.windowWidth = windowWidth;
	}


	public static int getWindowHeight() {
		return windowHeight;
	}


	public static void setWindowHeight(int windowHeight) {
		Controller.windowHeight = windowHeight;
	}


	public static SettingPanel getSettingWindow() {
		return settingWindow;
	}


	public static void setSettingWindow(SettingPanel settingWindow) {
		Controller.settingWindow = settingWindow;
	}


	public static String getCurrentUserPassword() {
		return currentUserPassword;
	}


	public static void setCurrentUserPassword(String currentUserPassword) {
		Controller.currentUserPassword = currentUserPassword;
	}


	public static DynamicHome getHomeWindow() {
		return homeWindow;
	}

	public static void setHomeWindow(DynamicHome newhomeWindow) {
		homeWindow = newhomeWindow;
	}

	public static DynamicInteractionTimeline getInteractionTimelineWindow() {
		return interactionTimelineWindow;
	}

	public static void setInteractionTimelineWindow(
			DynamicInteractionTimeline newinteractionTimelineWindow) {
		interactionTimelineWindow = newinteractionTimelineWindow;
	}

	public static DynamicInteractiveTimeline getInteractiveTimelineWindow() {
		return interactiveTimelineWindow;
	}

	public static void setInteractiveTimelineWindow(
			DynamicInteractiveTimeline newinteractiveTimelineWindow) {
		interactiveTimelineWindow = newinteractiveTimelineWindow;
	}

	public static DynamicPeople getPeopleWindow() {
		return peopleWindow;
	}

	public static void setPeopleWindow(DynamicPeople newpeopleWindow) {
		peopleWindow = newpeopleWindow;
	}

	public static ProfilePanel getProfilePanel() {
		return profilePanel;
	}

	public static void setProfilePanel(ProfilePanel newProfilePanel) {
		profilePanel = newProfilePanel;
	}

	public static WUser getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(WUser newCurrentUser) {
		currentUser = newCurrentUser;
	}

	public static void setLoginPanel(LoginPanel newLoginPanel) {
		Controller.loginPanel = newLoginPanel;
	}

	public static ProxyWrapper getProxy() {
		return proxy;
	}

	public static void setProxy(ProxyWrapper newProxy) {
		proxy = newProxy;
	}

	public static Composite getWindow() {
		return window;
	}

	public static void setWindow(Composite newWindow) {
		window = newWindow;
	}

	public static String getWindowName() {
		return windowName;
	}

	public static void setWindowName(String newWindowName) {
		windowName = newWindowName;
	}
	
	public static RegistrationPanel getRegistrationPanel() {
		return registrationPanel;
	}

	public static void setRegistration_panel(RegistrationPanel newRegistrationPanel) {
		Controller.registrationPanel = newRegistrationPanel;
	}
	
	//other methods
	public static void closeAllDynamicPanel(){
		if(dynamicUserWindow != null)
		{
			dynamicUserWindow.dispose(Controller.getProfilePanel().getComposite_dinamic());
			dynamicUserWindow = null;
		}
		
		
	   if(homeWindow != null){
		   homeWindow.dispose(Controller.getProfilePanel().getComposite_dinamic()); 
		   homeWindow = null;
	   }
	   
	   if(interactionTimelineWindow != null){
		   interactionTimelineWindow.dispose(Controller.getProfilePanel().getComposite_dinamic()); 
		   interactionTimelineWindow = null;
	   }
	   
	   if(interactiveTimelineWindow != null){
		   interactiveTimelineWindow.dispose(Controller.getProfilePanel().getComposite_dinamic()); 
		   interactiveTimelineWindow = null;
	   }
	   
	   if(peopleWindow != null){
		   peopleWindow.dispose(Controller.getProfilePanel().getComposite_dinamic()); 
		   peopleWindow = null;
	   }
	   
	   if(settingWindow != null){
		   settingWindow.dispose(Controller.getProfilePanel().getComposite_dinamic());
		   settingWindow = null; 
	   }
		
		 
	}
	
	
	public static void selectDynamicWindow(int choose)
	{
		Composite dynamicComposite;
		GridData gridData; 
		ProgressBarThread pbWindow; 
		System.out.println("Inizio ricaricamento della pagina..");
		System.out.println("Classico  " + Controller.getWindow().toDisplay(Controller.getWindow().getLocation())); 
		System.out.println("Classico parent " + Controller.getWindow().toDisplay(Controller.getWindow().getParent().getLocation()));
		System.out.println("Classico parent2 " + Controller.getWindow().toDisplay(Controller.getWindow().getParent().getParent().getLocation()));
		System.out.println("Classico parent3 " + Controller.getWindow().toDisplay(Controller.getWindow().getParent().getParent().getParent().getLocation()));
		System.out.println("Classico parent4 " + Controller.getWindow().getLocation());
		System.out.println("Classico parent5 " + Controller.getWindow().toControl(Controller.getWindow().getParent().getParent().getParent().getLocation()));
		
		 	pbWindow = new ProgressBarThread(); 
			pbWindow.setLabelTxt("Operation in progress..");
			pbWindow.setxCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getParent().getParent().getParent().getLocation()).x); 
			pbWindow.setyCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getParent().getParent().getParent().getLocation()).y);
			//pbWindow.setyCoordinate(Controller.getWindow().toControl(Controller.getWindow().getParent().getParent().getParent().getLocation()).y);
			//pbWindow.setxCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x); 
			//pbWindow.setyCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y); 
			//pbThread.setWidth(Controller.getWindow().getSize().x);
			//pbThread.setHeight(Controller.getWindow().getSize().y); 
			pbWindow.start();  
		
		closeAllDynamicPanel(); 
		
		switch (choose) {
		case 0:
			homeWindow = new DynamicHome(); 
			if(Controller.getProfilePanel().getComposite_dinamic() == null)
			{
				dynamicComposite = new Composite(getWindow(),SWT.NONE); 
				 gridData = new GridData(); 
				gridData.grabExcessHorizontalSpace = true;
				gridData.horizontalAlignment = gridData.FILL; 
				gridData.grabExcessVerticalSpace = true; 
				gridData.verticalAlignment = gridData.FILL; 
				dynamicComposite.setLayoutData(gridData); 
				Controller.getProfilePanel().setComposite_dinamic(dynamicComposite); 
				Controller.getProfilePanel().getComposite_dinamic().layout();
			}
			else
			{
				Controller.getProfilePanel().getComposite_dinamic().dispose();
				
				dynamicComposite = new Composite(getWindow(),SWT.NONE); 
				 gridData = new GridData(); 
				gridData.grabExcessHorizontalSpace = true;
				gridData.horizontalAlignment = gridData.FILL;
				gridData.grabExcessVerticalSpace = true; 
				gridData.verticalAlignment = gridData.FILL; 
				dynamicComposite.setLayoutData(gridData); 
				Controller.getProfilePanel().setComposite_dinamic(dynamicComposite); 
				Controller.getProfilePanel().getComposite_dinamic().redraw(); 
			}
			//SquareButtonService.flagDimension = false; 
			
			SquareButtonService.yCoordinateValue = 5; 
			SquareButtonService.counterPosition = 0; 
			
			homeWindow.inizialize(Controller.getProfilePanel().getComposite_dinamic());
			pbWindow.setStop(1); 
			
			Controller.getWindow().layout(); 
			break;
		case 1:
			settingWindow = new SettingPanel(); 
			if(Controller.getProfilePanel().getComposite_dinamic() == null)
			{
			 dynamicComposite = new Composite(getWindow(),SWT.NONE); 
			 gridData = new GridData(); 
			gridData.grabExcessHorizontalSpace = true;
			gridData.horizontalAlignment = gridData.FILL; 
			gridData.grabExcessVerticalSpace = true; 
			gridData.verticalAlignment = gridData.FILL;
			dynamicComposite.setLayoutData(gridData);
			Controller.getProfilePanel().setComposite_dinamic(dynamicComposite);
			}
			else
			{
				Controller.getProfilePanel().getComposite_dinamic().dispose(); 
				dynamicComposite = new Composite(getWindow(),SWT.NONE); 
				 gridData = new GridData(); 
				gridData.grabExcessHorizontalSpace = true;
				gridData.horizontalAlignment = gridData.FILL;
				gridData.grabExcessVerticalSpace = true; 
				gridData.verticalAlignment = gridData.FILL; 
				dynamicComposite.setLayoutData(gridData); 
				Controller.getProfilePanel().setComposite_dinamic(dynamicComposite); 
				Controller.getProfilePanel().getComposite_dinamic().redraw(); 
			}
			settingWindow.inizialize(Controller.getProfilePanel().getComposite_dinamic()); 
			pbWindow.setStop(1); 
		
			Controller.getWindow().layout(); 
			
		break;
		case 2: 
			
			
			peopleWindow = new DynamicPeople();
			if(Controller.getProfilePanel().getComposite_dinamic() == null)
			{
			 dynamicComposite = new Composite(getWindow(),SWT.NONE); 
			
			gridData = new GridData(); 
			gridData.grabExcessHorizontalSpace = true;
			gridData.horizontalAlignment = gridData.FILL; 
			gridData.grabExcessVerticalSpace = true; 
			gridData.verticalAlignment = gridData.FILL;
			dynamicComposite.setLayoutData(gridData);
			
			Controller.getProfilePanel().setComposite_dinamic(dynamicComposite);
			}
			else
			{
				Controller.getProfilePanel().getComposite_dinamic().dispose(); 
				dynamicComposite = new Composite(getWindow(),SWT.NONE); 
				 
				
				gridData = new GridData(); 
				gridData.grabExcessHorizontalSpace = true;
				gridData.horizontalAlignment = GridData.FILL;
				gridData.grabExcessVerticalSpace = true; 
				gridData.verticalAlignment = GridData.FILL; 
				dynamicComposite.setLayoutData(gridData); 
				
				Controller.getProfilePanel().setComposite_dinamic(dynamicComposite); 
				Controller.getProfilePanel().getComposite_dinamic().redraw(); 
			}
			 
			
			
			 
			 
			peopleWindow.inizialize(Controller.getProfilePanel().getComposite_dinamic());
			
			
			
			pbWindow.setStop(1); 
			
			Controller.getWindow().layout(); 
			break;
		case 3:
			System.out.println("Selezionato caso n.3"); 
			dynamicUserWindow = new DynamicUserTimeline(); 
			if(Controller.getProfilePanel().getComposite_dinamic() == null)
			{
			 dynamicComposite = new Composite(getWindow(),SWT.NONE); 
			
			gridData = new GridData(); 
			gridData.grabExcessHorizontalSpace = true;
			gridData.horizontalAlignment = gridData.FILL; 
			gridData.grabExcessVerticalSpace = true; 
			gridData.verticalAlignment = gridData.FILL;
			dynamicComposite.setLayoutData(gridData);
			
			Controller.getProfilePanel().setComposite_dinamic(dynamicComposite);
			}
			else
			{
				Controller.getProfilePanel().getComposite_dinamic().dispose(); 
				dynamicComposite = new Composite(getWindow(),SWT.NONE); 
				 
				
				gridData = new GridData(); 
				gridData.grabExcessHorizontalSpace = true;
				gridData.horizontalAlignment = gridData.FILL;
				gridData.grabExcessVerticalSpace = true; 
				gridData.verticalAlignment = gridData.FILL; 
				dynamicComposite.setLayoutData(gridData); 
				
				Controller.getProfilePanel().setComposite_dinamic(dynamicComposite); 
				Controller.getProfilePanel().getComposite_dinamic().redraw(); 
			}
			
			dynamicUserWindow.setUser( (WUser)  Controller.temporaryInformation.get("User_selected")); 
			dynamicUserWindow.setUserType((String) Controller.temporaryInformation.get("User_type"));
			dynamicUserWindow.inizialize( Controller.getProfilePanel().getComposite_dinamic() ); 
			pbWindow.setStop(1); 
			
			Controller.getWindow().layout(); 
			
			break;
			default:
				break;
		}
		
	}
	
	public static String getPreferences(String node)
	{
		String value;
		Preferences prefs;
		prefs = Preferences.userRoot().node(Controller.class.getName());
		
		try
		{
		  value = prefs.get(node, "");
		}
		catch(Exception e)
		{
			value = null;
		}
		
		return value;
	}
	
	public static boolean setPreferences(String node,String value)
	{
		boolean flag = false;
		Preferences prefs;
		prefs = Preferences.userRoot().node(Controller.class.getName());
		try
		{
			prefs.put(node,value);
			flag = true;
		}
		catch(Exception e)
		{
			flag = false;
		}
		
		return flag;
	}
	
	public static boolean isRegistered()
	{
		
		
		if((!getPreferences("Username").equals("")) && (!getPreferences("ProxyHost").equals("")) )
		{
			return true;
		}
		else
		{
			return false;
		}
		
	 
		
	}
	
	
	public static void selectWindow(Composite parent)
	{
		if(!isRegistered())
		{
			setWindowName("Registration"); 
			Controller.setRegistration_panel(new RegistrationPanel()); 
			Controller.getRegistrationPanel().inizialize(parent);
			//Registration form
		}
		else
		{
			setWindowName("Login"); 
			Controller.setLoginPanel(new LoginPanel());
			Controller.loginPanel.inizialize(parent); 
			//Login form
			/*
			
			if(!Controller.getPreferences("Autologin").equals(""))
			{
				
				Controller.setProxy(new ProxyWrapper()); 
				Controller.getProxy().setHost(Controller.getPreferences("ProxyHost"));
				Controller.setCurrentUser(Controller.getProxy().GetUser(Controller.getPreferences("Username").toString(), Controller.getPreferences("Password").toString()));
				Controller.setProfilePanel(new ProfilePanel());
				Controller.getProfilePanel().inizialize(parent);
				
				//Main form
			}
			else
			{
				setWindowName("Login"); 
				Controller.setLoginPanel(new LoginPanel());
				Controller.loginPanel.inizialize(parent); 
				//Login form
			}
			*/
		}
		
	}

	public static boolean isUsernameAvailable(String username)
	{
		return  getProxy().IsAvailable(username);
	}
	
	
}
