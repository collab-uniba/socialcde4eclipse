package us26_UI;

import static org.junit.Assert.*;


import java.util.HashMap;

import junit.framework.TestCase;

import it.uniba.di.socialcdeforeclipse.action.ActionLoginPanel;
import it.uniba.di.socialcdeforeclipse.action.ActionRegistrationPanel;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.staticView.RegistrationPanel;

import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class UI_UnitTestForProxyHost extends TestCase {

	/**
	 * Unit test if UI for User story number 26.
	 * 
	 * Field considered: ProxyHost
	 * 
	 * Equivalence classes considered: 1.Empty string 2.String that not link to
	 * server 3.String that link to server
	 * */
	
	HashMap<String, Object> dati; 
	

	
	@Before
	public void setUp() throws Exception {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview");
		
		Controller.setWindowName("Registration");
		Controller.getLoginPanel().dispose(Controller.getWindow()); 
		Controller.setRegistration_panel(new RegistrationPanel()); 
		Controller.getRegistrationPanel().inizialize(Controller.getWindow()); 
		Controller.setLoginPanel(null); 
		Controller.getWindow().layout(); 
	
	}
	
	@Test
	public void testCase1() { 
		
  		
	  assertNotNull(Controller.getRegistrationPanel());
	  dati = Controller.getRegistrationPanel().getData(); 
	  dati.put("ID_action", "txtProxyHost"); 
	  dati.put("Event_type", SWT.FocusOut); 
	  ( (Text)  dati.get("ProxyHost")).setText(""); 
	  new ActionRegistrationPanel(dati);
	  assertNull(Controller.getProxy());
	  assertTrue( ((Label) dati.get("LabelAlert")).getVisible()); 
	  assertEquals("Please insert a valid proxy!", ((Label) dati.get("LabelAlert")).getText()); 
	  assertTrue(  (Boolean)	((Label)  dati.get("LabelImageHost")).getData("Image_no")  );
	  assertFalse(  (Boolean)	((Label)  dati.get("LabelImageHost")).getData("Image_ok")  );	
	
	  
	}
	
	@Test
	public void testCase2() { 
	  assertNotNull(Controller.getRegistrationPanel());
	  dati = Controller.getRegistrationPanel().getData();  
	  dati.put("ID_action", "txtProxyHost"); 
	  dati.put("Event_type", SWT.FocusOut); 
	  ( (Text)  dati.get("ProxyHost")).setText("www.google.it"); 
	  new ActionRegistrationPanel(dati);
	  assertNull(Controller.getProxy());
	  assertTrue( ((Label) dati.get("LabelAlert")).getVisible()); 
	  assertEquals("Please insert a valid proxy!", ((Label) dati.get("LabelAlert")).getText()); 
	  assertTrue(  (Boolean)	((Label)  dati.get("LabelImageHost")).getData("Image_no")  );
	  assertFalse(  (Boolean)	((Label)  dati.get("LabelImageHost")).getData("Image_ok")  );	
	
	  
	}

	@Test
	public void testCase3() { 
	  assertNotNull(Controller.getRegistrationPanel());
	  dati = Controller.getRegistrationPanel().getData(); 
	  dati.put("ID_action", "txtProxyHost"); 
	  dati.put("Event_type", SWT.FocusOut); 
	  ( (Text)  dati.get("ProxyHost")).setText("http://apat.di.uniba.it:8081"); 
	  new ActionRegistrationPanel(dati);
	  assertTrue(Controller.getProxy().IsWebServiceRunning());
	  assertTrue(  (Boolean)	((Label)  dati.get("LabelImageHost")).getData("Image_ok")  );
	  assertFalse(  (Boolean)	((Label)  dati.get("LabelImageHost")).getData("Image_no")  );
		
	
	  
	}
	
	@After
	public void tearDown() throws Exception {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview"));
	}

}
