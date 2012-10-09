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
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import it.uniba.di.socialCDEforEclipse.SharedLibrary.WUser;
import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;
import it.uniba.di.socialcdeforeclipse.views.DynamicHome;
import it.uniba.di.socialcdeforeclipse.views.DynamicInteractionTimeline;
import it.uniba.di.socialcdeforeclipse.views.DynamicInteractiveTimeline;
import it.uniba.di.socialcdeforeclipse.views.DynamicPeople;
import it.uniba.di.socialcdeforeclipse.views.LoginPanel;
import it.uniba.di.socialcdeforeclipse.views.ProfilePanel;
import it.uniba.di.socialcdeforeclipse.views.ProgressBarThread;
import it.uniba.di.socialcdeforeclipse.views.RegistrationPanel;
import it.uniba.di.socialcdeforeclipse.views.SettingPanel;

public class Controller {

	//attributes
	private static ProxyWrapper proxy = null; 
	
	private static Composite window = null;
	
	private static int windowWidth = 0;
	
	private static int windowHeight = 0; 
	
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
	
	public static HashMap<String, Object> temporaryInformation =  new HashMap<String,Object>();  
	
	//getter and setter for attributes
	
	
	
	public static LoginPanel getLoginPanel() {
		return loginPanel;
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
		ProgressBarThread pbThread; 
		
			
			
		 	pbThread = new ProgressBarThread(); 
			pbThread.setLabelTxt("Operation in progress..");
			pbThread.setxCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x); 
			pbThread.setyCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y); 
			//pbThread.setWidth(Controller.getWindow().getSize().x);
			//pbThread.setHeight(Controller.getWindow().getSize().y); 
			pbThread.start(); 
		
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
				dynamicComposite.setLayoutData(gridData); 
				Controller.getProfilePanel().setComposite_dinamic(dynamicComposite); 
			}
			homeWindow.inizialize(Controller.getProfilePanel().getComposite_dinamic());
			pbThread.setStop(1);
			
			break;
		case 1:
			settingWindow = new SettingPanel(); 
			if(Controller.getProfilePanel().getComposite_dinamic() == null)
			{
			 dynamicComposite = new Composite(getWindow(),SWT.NONE); 
			 gridData = new GridData(); 
			gridData.grabExcessHorizontalSpace = true;
			gridData.horizontalAlignment = gridData.FILL; 
			dynamicComposite.setLayoutData(gridData);
			Controller.getProfilePanel().setComposite_dinamic(dynamicComposite);
			}
			settingWindow.inizialize(Controller.getProfilePanel().getComposite_dinamic()); 
			pbThread.setStop(1);
		
			Controller.getWindow().layout(); 
			
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
		}
		
	}

	public static boolean isUsernameAvailable(String username)
	{
		return  getProxy().IsAvailable(username);
	}
	
	
}
