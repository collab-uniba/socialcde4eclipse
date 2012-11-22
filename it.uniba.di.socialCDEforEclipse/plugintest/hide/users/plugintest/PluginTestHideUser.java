package hide.users.plugintest;

import static org.junit.Assert.*;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;

import it.uniba.di.socialcdeforeclipse.action.ActionDynamicUserTimeline;
import it.uniba.di.socialcdeforeclipse.action.ActionHomePanel;
import it.uniba.di.socialcdeforeclipse.action.ActionLoginPanel;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.object.GeneralButton;
import it.uniba.di.socialcdeforeclipse.popup.ChooseAvatar;
import it.uniba.di.socialcdeforeclipse.popup.HideUserPanel;
import it.uniba.di.socialcdeforeclipse.shared.library.WHidden;
import it.uniba.di.socialcdeforeclipse.shared.library.WUser;
import it.uniba.di.socialcdeforeclipse.object.ButtonAvatar;

import org.eclipse.ui.PlatformUI;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class PluginTestHideUser extends TestCase {

	/**
	 * Unit test for User story number 42.
	 * 
	 * 
	 * Equivalence classes considered: 
	 * 1.Hide user from suggestion
	 * 2.Hide user from itaraction timeline
	 * 3.Hide user from interactive timeline
	 * 
	 * 
	 * Note: For this test will be choose a random user between suggested user.
	 * 
	 * */
	
	static HashMap<String, Object> dati; 
	Document document; 
	WUser [] users; 
	int positionUser; 
	@Before
	public void setUp() throws Exception {
		
		SAXBuilder builder = new SAXBuilder();
		try {
			document = builder.build(new File("./testData.xml").getCanonicalPath());
		} catch (JDOMException e) {
			 
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview");
		
		 assertNotNull(Controller.getLoginPanel());
		  dati = Controller.getLoginPanel().getData(); 
		  dati.put("ID_action", "btnLogin"); 
		  dati.put("Event_type", SWT.Selection); 
		  ( (Text)  dati.get("txtProxyHost")).setText(document.getRootElement().getChild("CorrectData").getChild("Proxy").getText());
		  ( (Text)  dati.get("txtUsername")).setText(document.getRootElement().getChild("CorrectData").getChild("Username").getText());
		  ( (Text)  dati.get("txtPassword")).setText(document.getRootElement().getChild("CorrectData").getChild("Password").getText());
		  new ActionLoginPanel(dati);
		assertNotNull(Controller.getHomeWindow()); 
		
		users = Controller.getProxy().GetSuggestedFriends(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword());
		assertTrue(users.length > 0);
		
	}
	
	@Test public void testCase1()
	{
		 positionUser = (int) (Math.random() * (users.length -1)); 
		 Controller.temporaryInformation.put("User_selected", users[positionUser]); 
		 Controller.temporaryInformation.put("User_type", "Suggested");
		 Controller.selectDynamicWindow(3);
		 assertNotNull(Controller.getDynamicUserWindow()); 
		 dati = Controller.getDynamicUserWindow().getData(); 
		 dati.put("ID_action", "labelHide"); 
		  dati.put("Event_type", SWT.MouseDown);
		  new ActionDynamicUserTimeline(dati); 
		  HideUserPanel hidePanel = (HideUserPanel) dati.get("HideUserPanel"); 
		  HashMap<String, Object> datiPanel = hidePanel.getData(); 
		  ((Button)  datiPanel.get("suggestionPermission")).setSelection(true); 
		  GeneralButton btnSave =  ((GeneralButton) datiPanel.get("btnSave")); 
		  btnSave.notifyListeners(SWT.Selection,new Event() ); 
		  WHidden userSetting = Controller.getProxy().GetUserHideSettings(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), users[positionUser].Id); 
		  assertTrue(userSetting.isSuggestions()); 
		  
	}
	
	@Test public void testCase2()
	{
		 positionUser = (int) (Math.random() * (users.length -1)); 
		 Controller.temporaryInformation.put("User_selected", users[positionUser]); 
		 Controller.temporaryInformation.put("User_type", "Suggested");
		 Controller.selectDynamicWindow(3);
		 assertNotNull(Controller.getDynamicUserWindow()); 
		 dati = Controller.getDynamicUserWindow().getData(); 
		 dati.put("ID_action", "labelHide"); 
		  dati.put("Event_type", SWT.MouseDown);
		  new ActionDynamicUserTimeline(dati); 
		  HideUserPanel hidePanel = (HideUserPanel) dati.get("HideUserPanel"); 
		  HashMap<String, Object> datiPanel = hidePanel.getData(); 
		  ((Button)  datiPanel.get("iterationTimelinePermission")).setSelection(true); 
		  GeneralButton btnSave =  ((GeneralButton) datiPanel.get("btnSave")); 
		  btnSave.notifyListeners(SWT.Selection,new Event() ); 
		  WHidden userSetting = Controller.getProxy().GetUserHideSettings(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), users[positionUser].Id); 
		  assertTrue(userSetting.isDynamic()); 
	}
	
	@Test public void testCase3()
	{
		 positionUser = (int) (Math.random() * (users.length -1)); 
		 Controller.temporaryInformation.put("User_selected", users[positionUser]); 
		 Controller.temporaryInformation.put("User_type", "Suggested");
		 Controller.selectDynamicWindow(3);
		 assertNotNull(Controller.getDynamicUserWindow()); 
		 dati = Controller.getDynamicUserWindow().getData(); 
		 dati.put("ID_action", "labelHide"); 
		  dati.put("Event_type", SWT.MouseDown);
		  new ActionDynamicUserTimeline(dati); 
		  HideUserPanel hidePanel = (HideUserPanel) dati.get("HideUserPanel"); 
		  HashMap<String, Object> datiPanel = hidePanel.getData(); 
		  ((Button)  datiPanel.get("interactiveTimelinePermission")).setSelection(true); 
		  GeneralButton btnSave =  ((GeneralButton) datiPanel.get("btnSave")); 
		  btnSave.notifyListeners(SWT.Selection,new Event() ); 
		  WHidden userSetting = Controller.getProxy().GetUserHideSettings(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), users[positionUser].Id); 
		  assertTrue(userSetting.isInteractive()); 
	}
	
	
	@After
	public void tearDown() throws Exception {
		
		if(positionUser != -1)
		{
			assertTrue(Controller.getProxy().UpdateHiddenUser(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), users[positionUser].Id, false, false, false));
		}
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview"));
	}
	
	
	

	
	

}
