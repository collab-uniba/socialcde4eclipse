/*
 * Classname Controller
 *
 * Version info 1.0.0.0
 *
 * Copyright notice
 * 
 * */
package it.uniba.di.collab.socialcdeforeclipse.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.prefs.Preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import socialcdeforeclipse.Activator;



import it.uniba.di.collab.socialcdeforeclipse.dynamic.view.DynamicHome;
import it.uniba.di.collab.socialcdeforeclipse.dynamic.view.DynamicHomeTimeline;
import it.uniba.di.collab.socialcdeforeclipse.dynamic.view.DynamicInteractiveTimeline;
import it.uniba.di.collab.socialcdeforeclipse.dynamic.view.DynamicIterationTimeline;
import it.uniba.di.collab.socialcdeforeclipse.dynamic.view.DynamicPeople;
import it.uniba.di.collab.socialcdeforeclipse.dynamic.view.DynamicUserTimeline;
import it.uniba.di.collab.socialcdeforeclipse.dynamic.view.SettingPanel;
import it.uniba.di.collab.socialcdeforeclipse.model.ProxyWrapper;
import it.uniba.di.collab.socialcdeforeclipse.object.ProgressBarThread;
import it.uniba.di.collab.socialcdeforeclipse.object.ProgressBarWindow;
import it.uniba.di.collab.socialcdeforeclipse.object.SquareButtonService;
import it.uniba.di.collab.socialcdeforeclipse.popup.ConnLostPanel;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.*;
import it.uniba.di.collab.socialcdeforeclipse.staticview.LoginPanel;
import it.uniba.di.collab.socialcdeforeclipse.staticview.ProfilePanel;
import it.uniba.di.collab.socialcdeforeclipse.staticview.RegistrationPanel;

public class Controller {

	// attributes
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

	private static DynamicIterationTimeline interationTimelineWindow = null;

	private static DynamicInteractiveTimeline interactiveTimelineWindow = null;

	private static DynamicHomeTimeline homeTimelineWindow = null;

	private static DynamicPeople peopleWindow = null;

	private static SettingPanel settingWindow = null;

	private static DynamicUserTimeline dynamicUserWindow = null;

	public static HashMap<String, Object> temporaryInformation = new HashMap<String, Object>();

	public static int progressBarPositionX = 0;

	public static int progressBarPositionY = 0;
	
	private static HashMap<String,Image> usersAvatar = new HashMap<String,Image>(); 
	
	private static HashMap<String, Image> servicesImage = new HashMap<String,Image>(); 


	// getter and setter for attributes

	public static HashMap<String, Image> getUsersAvatar() {
		return usersAvatar;
	}

	public static HashMap<String, Image> getServicesImage() {
		return servicesImage;
	}

	public static LoginPanel getLoginPanel() {
		return loginPanel;
	}

	public static DynamicHomeTimeline getHomeTimelineWindow() {
		return homeTimelineWindow;
	}

	public static void setHomeTimelineWindow(
			DynamicHomeTimeline homeTimelineWindow) {
		Controller.homeTimelineWindow = homeTimelineWindow;
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

	public static void setDynamicUserWindow(
			DynamicUserTimeline dynamicUserWindow) {
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

	public static DynamicIterationTimeline getInteractionTimelineWindow() {
		return interationTimelineWindow;
	}

	public static void setInteractionTimelineWindow(
			DynamicIterationTimeline newinteractionTimelineWindow) {
		interationTimelineWindow = newinteractionTimelineWindow;
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

	public static void setRegistration_panel(
			RegistrationPanel newRegistrationPanel) {
		Controller.registrationPanel = newRegistrationPanel;
	}

	// other methods
	public static void closeAllDynamicPanel() {
		if (dynamicUserWindow != null) {
			dynamicUserWindow.dispose(Controller.getProfilePanel()
					.getComposite_dinamic());
			dynamicUserWindow = null;
		}

		if (homeTimelineWindow != null) {
			homeTimelineWindow.dispose(Controller.getProfilePanel()
					.getComposite_dinamic());
			homeTimelineWindow = null;
		}

		if (homeWindow != null) {
			homeWindow.dispose(Controller.getProfilePanel()
					.getComposite_dinamic());
			homeWindow = null;
			
		}

		if (interationTimelineWindow != null) {
			interationTimelineWindow.dispose(Controller.getProfilePanel()
					.getComposite_dinamic());
			interationTimelineWindow = null;
		}

		if (interactiveTimelineWindow != null) {
			interactiveTimelineWindow.dispose(Controller.getProfilePanel()
					.getComposite_dinamic());
			interactiveTimelineWindow = null;
		}

		if (peopleWindow != null) {
			peopleWindow.dispose(Controller.getProfilePanel()
					.getComposite_dinamic());
			peopleWindow = null;
		}

		if (settingWindow != null) {
			settingWindow.dispose(Controller.getProfilePanel()
					.getComposite_dinamic());
			settingWindow = null;
		}

	}

	public static void selectDynamicWindow(int choose) {
		Composite dynamicComposite;
		GridData gridData;
		ProgressBarWindow pbNewW = null;
		ProgressBarThread pbWindow = null;
		 
		
		
		
		if(Controller.OSisWindows())
		{
			pbWindow = new ProgressBarThread();
			Controller.temporaryInformation.put("ProgressBarThread", pbWindow);
			pbWindow.setLabelTxt("Operation in progress..");
			pbWindow.setxCoordinate(Controller.getWindow().toDisplay(
					Controller.getWindow().getParent().getParent().getParent()
					.getLocation()).x);
			pbWindow.setyCoordinate(Controller.getWindow().toDisplay(
					Controller.getWindow().getParent().getParent().getParent()
					.getLocation()).y);
			pbWindow.start(); 
		}
		else if(Controller.OSisUnix())
		{
			pbNewW = new ProgressBarWindow(); 
			pbNewW.setLabelTxt("Operation in progress..");
			pbNewW.setxCoordinate(Controller.getWindow().toDisplay(
					Controller.getWindow().getParent().getParent().getParent()
							.getLocation()).x);
			pbNewW.setyCoordinate(Controller.getWindow().toDisplay(
					Controller.getWindow().getParent().getParent().getParent()
							.getLocation()).y);
			
			pbNewW.inizialize(getWindow()); 
			
		}
			
		closeAllDynamicPanel();
		if (Controller.getProfilePanel().getComposite_dinamic() != null) {
		Controller.getProfilePanel().getComposite_dinamic().dispose();
		}

		switch (choose) {
		case 0:
		 
			homeWindow = new DynamicHome();
			
			if(Controller.OSisUnix())
			{
				pbNewW.updateProgressBar(); 
			}
			
			if (Controller.getProfilePanel().getComposite_dinamic() == null) {
				dynamicComposite = new Composite(getWindow(), SWT.None);
		 
				gridData = new GridData();
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				
				gridData.widthHint = Controller.getWindowWidth() - 10;
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				gridData.grabExcessVerticalSpace = true;
				 
				gridData.verticalAlignment = GridData.FILL;
				dynamicComposite.setLayoutData(gridData);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				Controller.getProfilePanel().setComposite_dinamic(
						dynamicComposite);
				 
				Controller.getProfilePanel().getComposite_dinamic().layout();
			} else {
				Controller.getProfilePanel().getComposite_dinamic().dispose();
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				} 
				dynamicComposite = new Composite(getWindow(), SWT.None);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				} 
				gridData = new GridData();
				
				gridData.widthHint = Controller.getWindowWidth() - 10;
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				} 
				gridData.grabExcessVerticalSpace = true;
				gridData.verticalAlignment = GridData.FILL;
				dynamicComposite.setLayoutData(gridData);
				Controller.getProfilePanel().setComposite_dinamic(
						dynamicComposite);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				} 
				Controller.getProfilePanel().getComposite_dinamic().redraw();
			}
			

			SquareButtonService.yCoordinateValue = 5;
			SquareButtonService.counterPosition = 0;
			if(Controller.OSisUnix())
			{
				pbNewW.updateProgressBar(); 
				Controller.temporaryInformation.put("LinuxProgressBar", pbNewW); 
			}
			 
			homeWindow.inizialize(Controller.getProfilePanel()
					.getComposite_dinamic());
			 
			if(Controller.OSisWindows())
			{
				pbWindow.setStop(1);
				pbWindow = null;  
			}
			else if(Controller.OSisUnix())
			{
				pbNewW.dispose(null); 
				pbNewW = null;
			}
			Controller.getWindow().layout();
			break;
		case 1:
			settingWindow = new SettingPanel();
			if (Controller.getProfilePanel().getComposite_dinamic() == null) {
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				dynamicComposite = new Composite(getWindow(), SWT.NONE);
				gridData = new GridData();
				 
				gridData.widthHint = Controller.getWindowWidth() - 10;
				gridData.grabExcessVerticalSpace = true;
				gridData.verticalAlignment = gridData.FILL;
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				} 
				dynamicComposite.setLayoutData(gridData);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				} 
				Controller.getProfilePanel().setComposite_dinamic(
						dynamicComposite);
				 
			} else {
				Controller.getProfilePanel().getComposite_dinamic().dispose();
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				} 
				dynamicComposite = new Composite(getWindow(), SWT.NONE);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				} 
				gridData = new GridData();
				gridData.widthHint = Controller.getWindowWidth() - 10;
				gridData.grabExcessVerticalSpace = true;
				gridData.verticalAlignment = gridData.FILL;
				dynamicComposite.setLayoutData(gridData);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				} 
				Controller.getProfilePanel().setComposite_dinamic(
						dynamicComposite);
				 
				Controller.getProfilePanel().getComposite_dinamic().redraw();
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				} 
			}
			if(Controller.OSisUnix())
			{
				pbNewW.updateProgressBar(); 
				Controller.temporaryInformation.put("LinuxProgressBar", pbNewW); 
			}
			settingWindow.inizialize(Controller.getProfilePanel()
					.getComposite_dinamic());
			 
			 
			if(Controller.OSisWindows())
			{
				pbWindow.setStop(1);
				pbWindow = null;  
			}
			else if(Controller.OSisUnix())
			{
				pbNewW.dispose(null); 
				pbNewW = null;
			}
			Controller.getWindow().layout();

			break;
		case 2:

			peopleWindow = new DynamicPeople();
			if (Controller.getProfilePanel().getComposite_dinamic() == null) {
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				} 
				dynamicComposite = new Composite(getWindow(), SWT.NONE);

				gridData = new GridData();
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				} 
				gridData.widthHint = Controller.getWindowWidth() - 10;
				gridData.grabExcessVerticalSpace = true;
				gridData.verticalAlignment = GridData.FILL;
				dynamicComposite.setLayoutData(gridData);
				 
				Controller.getProfilePanel().setComposite_dinamic(
						dynamicComposite);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				} 
			} else {
				Controller.getProfilePanel().getComposite_dinamic().dispose();
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				} 
				dynamicComposite = new Composite(getWindow(), SWT.None);
				 
				gridData = new GridData();
				gridData.widthHint = Controller.getWindowWidth() - 10;
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				} 
				gridData.grabExcessVerticalSpace = true;
				gridData.verticalAlignment = GridData.FILL;
				dynamicComposite.setLayoutData(gridData);
				 
				Controller.getProfilePanel().setComposite_dinamic(
						dynamicComposite);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				} 
				Controller.getProfilePanel().getComposite_dinamic().redraw();
				 
			}
			if(Controller.OSisUnix())
			{
				pbNewW.updateProgressBar(); 
				Controller.temporaryInformation.put("LinuxProgressBar", pbNewW); 
			}
			peopleWindow.inizialize(Controller.getProfilePanel()
					.getComposite_dinamic());
			 
			if(Controller.OSisWindows())
			{
				pbWindow.setStop(1);
				pbWindow = null;  
			}
			else if(Controller.OSisUnix())
			{
				pbNewW.dispose(null);
				pbNewW = null;
			}
			 
			
			Controller.getWindow().layout();
			break;
		case 3:

			dynamicUserWindow = new DynamicUserTimeline();
			 
			if (Controller.getProfilePanel().getComposite_dinamic() == null) {
				dynamicComposite = new Composite(getWindow(), SWT.NONE);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				gridData = new GridData();
				gridData.grabExcessHorizontalSpace = true;
				gridData.horizontalAlignment = gridData.FILL;
				gridData.grabExcessVerticalSpace = true;
				gridData.verticalAlignment = gridData.FILL;
				dynamicComposite.setLayoutData(gridData);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				Controller.getProfilePanel().setComposite_dinamic(
						dynamicComposite);
			 
			} else {
				Controller.getProfilePanel().getComposite_dinamic().dispose();
		 
				dynamicComposite = new Composite(getWindow(), SWT.NONE);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				gridData = new GridData();
				gridData.grabExcessHorizontalSpace = true;
				gridData.horizontalAlignment = gridData.FILL;
				gridData.grabExcessVerticalSpace = true;
				gridData.verticalAlignment = gridData.FILL;
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				dynamicComposite.setLayoutData(gridData);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				Controller.getProfilePanel().setComposite_dinamic(
						dynamicComposite);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				Controller.getProfilePanel().getComposite_dinamic().redraw();
		 
			}

			dynamicUserWindow.setUser((WUser) Controller.temporaryInformation
					.get("User_selected"));
		 
			dynamicUserWindow
					.setUserType((String) Controller.temporaryInformation
							.get("User_type"));
		 
			if(Controller.OSisUnix())
			{
				pbNewW.updateProgressBar(); 
				Controller.temporaryInformation.put("LinuxProgressBar", pbNewW); 
			} 
			
			dynamicUserWindow.inizialize(Controller.getProfilePanel()
					.getComposite_dinamic());
			
			
			if(Controller.OSisWindows())
			{
				pbWindow.setStop(1);
				pbWindow = null;  
			}
			else if(Controller.OSisUnix())
			{
				pbNewW.dispose(null); 
				pbNewW = null;
			}
			 
			
			Controller.getWindow().layout();

			break;

		case 4:

			homeTimelineWindow = new DynamicHomeTimeline();
			 
			if (Controller.getProfilePanel().getComposite_dinamic() == null) {
				dynamicComposite = new Composite(getWindow(), SWT.None);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				gridData = new GridData();
				gridData.widthHint = Controller.getWindowWidth() - 10;
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				gridData.grabExcessVerticalSpace = true;
				gridData.verticalAlignment = GridData.FILL_VERTICAL;
				dynamicComposite.setLayoutData(gridData);
			 
				Controller.getProfilePanel().setComposite_dinamic(
						dynamicComposite);
			 
			} else {
				Controller.getProfilePanel().getComposite_dinamic().dispose();
				dynamicComposite = new Composite(getWindow(), SWT.None);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				gridData = new GridData();
				gridData.widthHint = Controller.getWindowWidth() - 10;
			 
				gridData.grabExcessVerticalSpace = true;
				gridData.verticalAlignment = GridData.FILL_VERTICAL;
				dynamicComposite.setLayoutData(gridData);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				Controller.getProfilePanel().setComposite_dinamic(
						dynamicComposite);
				Controller.getProfilePanel().getComposite_dinamic().redraw();
			 
			}
			if(Controller.OSisUnix())
			{
				Controller.temporaryInformation.put("LinuxProgressBar", pbNewW); 
			}
			long windowStartTime = System.currentTimeMillis();
			homeTimelineWindow.inizialize(Controller.getProfilePanel()
					.getComposite_dinamic());
			long windowEndTime = System.currentTimeMillis();
			
			System.out.println("chiamata window " + (windowEndTime - windowStartTime) + " milliseconds");
			
			if(Controller.OSisWindows())
			{
				pbWindow.setStop(1);
				pbWindow = null;  
			}
			else if(Controller.OSisUnix())
			{
				pbNewW.dispose(null); 
				pbNewW = null;
			}
			
			//Controller.getWindow().layout();
			//Controller.getWindow().redraw();
			//Display.getCurrent().update();
			
			break;

		case 5:

			interationTimelineWindow = new DynamicIterationTimeline();
			 
			if (Controller.getProfilePanel().getComposite_dinamic() == null) {
				dynamicComposite = new Composite(getWindow(), SWT.None);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				gridData = new GridData();
				gridData.widthHint = Controller.getWindowWidth() - 10;
			 
				gridData.grabExcessVerticalSpace = true;
				gridData.verticalAlignment = GridData.FILL;
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				dynamicComposite.setLayoutData(gridData);
			 
				Controller.getProfilePanel().setComposite_dinamic(
						dynamicComposite);
			 
			} else {
				Controller.getProfilePanel().getComposite_dinamic().dispose();
				dynamicComposite = new Composite(getWindow(), SWT.None);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				gridData = new GridData();
				gridData.widthHint = Controller.getWindowWidth() - 10;
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				gridData.grabExcessVerticalSpace = true;
				gridData.verticalAlignment = GridData.FILL;
				dynamicComposite.setLayoutData(gridData);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				Controller.getProfilePanel().setComposite_dinamic(
						dynamicComposite);
			 
				Controller.getProfilePanel().getComposite_dinamic().redraw();
			 
			}
			
			if(Controller.OSisUnix())
			{
				pbNewW.updateProgressBar(); 
				Controller.temporaryInformation.put("LinuxProgressBar", pbNewW); 
			}
			long secondCallpostStartTime = System.currentTimeMillis();
		
			interationTimelineWindow.inizialize(Controller.getProfilePanel()
					.getComposite_dinamic());
			long secondCallpostEndTime = System.currentTimeMillis();
			
			System.out.println("window chiamata " + (secondCallpostEndTime - secondCallpostStartTime) + " milliseconds");
			
			if(Controller.OSisWindows())
			{
				pbWindow.setStop(1);
				pbWindow = null;  
			}
			else if(Controller.OSisUnix())
			{
				pbNewW.dispose(null); 
				pbNewW = null;
			}
			
			break;
			
		case 6:

			interactiveTimelineWindow = new DynamicInteractiveTimeline();
			 
			if (Controller.getProfilePanel().getComposite_dinamic() == null) {
				dynamicComposite = new Composite(getWindow(), SWT.None);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				gridData = new GridData();
				gridData.widthHint = Controller.getWindowWidth() - 10;
			 
				gridData.grabExcessVerticalSpace = true;
				gridData.verticalAlignment = GridData.FILL;
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				dynamicComposite.setLayoutData(gridData);
			 
				Controller.getProfilePanel().setComposite_dinamic(
						dynamicComposite);
			 
			} else {
				Controller.getProfilePanel().getComposite_dinamic().dispose();
				dynamicComposite = new Composite(getWindow(), SWT.None);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				gridData = new GridData();
				gridData.widthHint = Controller.getWindowWidth() - 10;
			 
				gridData.grabExcessVerticalSpace = true;
				gridData.verticalAlignment = GridData.FILL;
				dynamicComposite.setLayoutData(gridData);
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				}
				Controller.getProfilePanel().setComposite_dinamic(
						dynamicComposite);
			 
				Controller.getProfilePanel().getComposite_dinamic().redraw();
			 
			}

			if(Controller.OSisUnix())
			{
				pbNewW.updateProgressBar(); 
				Controller.temporaryInformation.put("LinuxProgressBar", pbNewW); 
			}
			
			interactiveTimelineWindow.inizialize(Controller.getProfilePanel()
					.getComposite_dinamic());
			 
			if(Controller.OSisWindows())
			{
				pbWindow.setStop(1);
				pbWindow = null;  
			}
			else if(Controller.OSisUnix())
			{
				pbNewW.dispose(null); 
				pbNewW = null;
			}
			
			break;

		default:
			break;
		}

	}

	public static String getPreferences(String node) {
		
		String value;
		final String prefix = "SocialCDE";  
		try {
			value = Activator.getDefault().getPreferenceStore().getString(prefix + node); 
		} catch (Exception e) {
			value = null;
		}

		return value;
	}

	public static boolean setPreferences(String node, String value) {
		boolean flag = false;
		final String prefix = "SocialCDE";  
		try {
			Activator.getDefault().getPreferenceStore().setValue(prefix + node, value); 
			flag = true;
		} catch (Exception e) {
			flag = false;
		}

		return flag;
	}

	public static boolean isRegistered() {

		if ((!getPreferences("username").equals(""))
				&& (!getPreferences("proxyHost").equals(""))) {
			return true;
		} else {
			return false;
		}

	}

	public static void selectWindow(Composite parent) {
		if (!isRegistered()) {
			setWindowName("Registration");
			Controller.setRegistration_panel(new RegistrationPanel());
			Controller.getRegistrationPanel().inizialize(parent);
			// Registration form
		}  else if (!Controller.getPreferences("autoLogin").equals("")
				&& Controller.getPreferences("autoLogin")
				.equals("true"))
{
WUser user;
 
 
  ProgressBarThread	pbWindow = new ProgressBarThread();
	
  	pbWindow.setLabelTxt("Login in progress..");
	pbWindow.setxCoordinate(Controller.getWindow()
			.toDisplay(
					Controller.getWindow().getLocation().x,
					Controller.getWindow().getLocation().y).x);
	pbWindow.setyCoordinate(Controller.getWindow()
			.toDisplay(
					Controller.getWindow().getLocation().x,
					Controller.getWindow().getLocation().y).y);
	pbWindow.setxCoordinateWithOffset(Controller
			.getWindow().toDisplay(
					Controller.getWindow().getLocation().x,
					Controller.getWindow().getLocation().y).x
			+ (Controller.getWindow().getBounds().width - 300)
			/ 2);
	pbWindow.setyCoordinateWithOffset(Controller
			.getWindow().toDisplay(
					Controller.getWindow().getLocation().x,
					Controller.getWindow().getLocation().y).y
			+ (Controller.getWindow().getBounds().height - 200)
			/ 2);
	Controller
			.setProgressBarPositionX(Controller.getWindow()
					.toDisplay(
							Controller.getWindow()
									.getLocation().x,
							Controller.getWindow()
									.getLocation().y).x);
	Controller
			.setProgressBarPositionY(Controller.getWindow()
					.toDisplay(
							Controller.getWindow()
									.getLocation().x,
							Controller.getWindow()
									.getLocation().y).y);
	pbWindow.start();  

	if (Controller.getProxy() == null) {
		 
		Controller.setProxy(new ProxyWrapper());
		 
		Controller.getProxy().setHost(Controller.getPreferences("proxyHost"));
	} else {
		Controller.getProxy().setHost(Controller.getPreferences("proxyHost"));
		 
	}
	if (Controller.getProxy().IsWebServiceRunning()) {
		 
		user = Controller
				.getProxy()
				.GetUser(Controller.getPreferences("username"),
						Controller.getPreferences("password"));
		 
		if (user == null) {
			 
			setWindowName("Login");
			Controller.setLoginPanel(new LoginPanel());
			Controller.loginPanel.inizialize(parent);
			pbWindow.setStop(1); 
			pbWindow = null;

			//System.out.println("Utente non corretto");

		} else {
			 
			
			 
			Controller.setCurrentUser(user);
			
			Controller
					.setCurrentUserPassword(Controller.getPreferences("password"));
			 
			Controller.setWindowName("Profile");
			 
			Controller.setProfilePanel(new ProfilePanel());
			 
			
			 
			 
			pbWindow.setStop(1);
			pbWindow = null;
			
			Controller.getProfilePanel().inizialize(
					Controller.getWindow());
			 Controller.getWindow().layout(); 
			

		}
	} else {
	 
		setWindowName("Login");
		Controller.setLoginPanel(new LoginPanel());
		Controller.loginPanel.inizialize(parent);
		pbWindow.setStop(1); 
		pbWindow = null;
	}


}else {
			setWindowName("Login");
			Controller.setLoginPanel(new LoginPanel());
			Controller.loginPanel.inizialize(parent);
			// Login form
			
		}

	}

	public static boolean isUsernameAvailable(String username) {
		return getProxy().IsAvailable(username);
	}
	public static boolean OSisWindows() {
		String OS = System.getProperty("os.name").toLowerCase();
		return (OS.indexOf("win") >= 0);
 
	}
 
	public static boolean OSisMac() {
		String OS = System.getProperty("os.name").toLowerCase();
		return (OS.indexOf("mac") >= 0);
 
	}
 
	public static boolean OSisUnix() {
		String OS = System.getProperty("os.name").toLowerCase();
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
 
	}
 
	public static boolean OSisSolaris() {
		String OS = System.getProperty("os.name").toLowerCase();
		return (OS.indexOf("sunos") >= 0);
 
	}
	
	public  static void openConnectionLostPanel(String message)
	{
		final ConnLostPanel panel1 = new ConnLostPanel(); 
		
		panel1
		.setxCoordinate(Controller.getWindow()
				.toDisplay(
						Controller.getWindow()
								.getLocation().x,
						Controller.getWindow()
								.getLocation().y).x);
		panel1
		.setyCoordinate(Controller.getWindow()
				.toDisplay(
						Controller.getWindow()
								.getLocation().x,
						Controller.getWindow()
								.getLocation().y).y);
		panel1
		.setxCoordinateWithOffset(Controller
				.getWindow().toDisplay(
						Controller.getWindow()
								.getLocation().x,
						Controller.getWindow()
								.getLocation().y).x- 30);
		panel1
		.setyCoordinateWithOffset(Controller
				.getWindow().toDisplay(
						Controller.getWindow()
								.getLocation().x,
						Controller.getWindow()
								.getLocation().y).y
				+ (Controller.getWindow().getBounds().height - 200)
				/ 2);

		panel1.setMessage(message); 

		panel1.setOkListener(new Listener() {

	@Override
	public void handleEvent(Event event) {
		

		panel1.dispose(null); 
		Controller.closeAllDynamicPanel();
		Controller.setCurrentUser(null);
		Controller.setCurrentUserPassword(null);
		Controller.setWindowName("Login");
		Controller.getProfilePanel().dispose(Controller.getWindow());
		Controller.setProfilePanel(null);
		Controller.setLoginPanel(new LoginPanel());
		Controller.getLoginPanel().inizialize(Controller.getWindow());
		Controller.getWindow().layout();
		

	}
});
		
		panel1.inizialize(Controller.getWindow()); 
	}

}
