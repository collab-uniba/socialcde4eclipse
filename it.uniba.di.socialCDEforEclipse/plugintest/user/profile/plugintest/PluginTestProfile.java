package user.profile.plugintest;

import static org.junit.Assert.*;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import junit.framework.TestCase;

import it.uniba.di.socialcdeforeclipse.action.ActionLoginPanel;
import it.uniba.di.socialcdeforeclipse.action.ActionRegistrationPanel;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.shared.library.WService;
import it.uniba.di.socialcdeforeclipse.staticview.RegistrationPanel;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class PluginTestProfile extends TestCase {

	/**
	 * Unit test for User story number 18.
	 * 
	 * Action considered: View profile of current user
	 * 
	 * Equivalence classes considered: 
	 * 1.Profile of user that can active one or more services
	 * 2.Profile of user that can't active services.  
	 * */
	
	HashMap<String, Object> dati; 
	Document document; 

	
	@Before
	public void setUp() {
		
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
		
		
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview");
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		  assertNotNull(Controller.getLoginPanel());
		  dati = Controller.getLoginPanel().getData(); 
		  dati.put("ID_action", "btnLogin"); 
		  dati.put("Event_type", SWT.Selection); 
		  ( (Text)  dati.get("txtProxyHost")).setText(document.getRootElement().getChild("CorrectData").getChild("Proxy").getText());
		  ( (Text)  dati.get("txtUsername")).setText(document.getRootElement().getChild("CorrectData").getChild("Username").getText());
		  ( (Text)  dati.get("txtPassword")).setText(document.getRootElement().getChild("CorrectData").getChild("Password").getText());
		  new ActionLoginPanel(dati);
		  assertTrue(Controller.getProxy().IsWebServiceRunning());
		  assertNotNull(Controller.getCurrentUser()); 
		  assertNotNull(Controller.getHomeWindow()); 
		  assertNull(Controller.getLoginPanel()); 
		  assertEquals("Home",Controller.getWindowName()); 
		  dati = Controller.getHomeWindow().getData(); 
		  
	
		
	}
	
	@Test
	public void testCase1() { 
		
		WService[] services = Controller.getProxy().GetServices(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		assertTrue(services.length > 0);
	}
	
	@Test
	public void testCase2() { 
		Controller.setCurrentUserPassword("try1"); 
		WService[] services = Controller.getProxy().GetServices(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		assertTrue(services.length == 0);
		assertEquals("There are no services available yet.\t\nPlease try again soon or contact your admin.",((Label) dati.get("service")).getText()); 
	
	}
	
	@After
	public void tearDown() throws Exception {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview"));
	}

}
