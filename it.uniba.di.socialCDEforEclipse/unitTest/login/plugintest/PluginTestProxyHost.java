package login.plugintest;

import static org.junit.Assert.*;


import java.util.HashMap;

import junit.framework.TestCase;

import it.uniba.di.socialcdeforeclipse.action.ActionLoginPanel;
import it.uniba.di.socialcdeforeclipse.controller.Controller;

import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class PluginTestProxyHost extends TestCase {

	/**
	 * Unit test if UI for User story number 44.
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
	
	}
	
	@Test
	public void testCase1() { 
	  assertNotNull(Controller.getLoginPanel());
	  dati = Controller.getLoginPanel().getData(); 
	  dati.put("ID_action", "txtProxyHost"); 
	  dati.put("Event_type", SWT.FocusOut); 
	  ( (Text)  dati.get("txtProxyHost")).setText(""); 
	  new ActionLoginPanel(dati);
	  assertNull(Controller.getProxy());
	  assertTrue( ((Label) dati.get("labelAlert")).getVisible()); 
	  assertEquals("Please insert a valid proxy!", ((Label) dati.get("labelAlert")).getText()); 
	  assertTrue(  (Boolean)	((Label)  dati.get("labelImageHost")).getData("Image_no")  );
	  assertFalse(  (Boolean)	((Label)  dati.get("labelImageHost")).getData("Image_ok")  );	
	
	  
	}
	
	@Test
	public void testCase2() { 
	  assertNotNull(Controller.getLoginPanel());
	  dati = Controller.getLoginPanel().getData(); 
	  dati.put("ID_action", "txtProxyHost"); 
	  dati.put("Event_type", SWT.FocusOut); 
	  ( (Text)  dati.get("txtProxyHost")).setText("www.google.it"); 
	  new ActionLoginPanel(dati);
	  assertNull(Controller.getProxy());
	  assertTrue( ((Label) dati.get("labelAlert")).getVisible()); 
	  assertEquals("Please insert a valid proxy!", ((Label) dati.get("labelAlert")).getText()); 
	  assertTrue(  (Boolean)	((Label)  dati.get("labelImageHost")).getData("Image_no")  );
	  assertFalse(  (Boolean)	((Label)  dati.get("labelImageHost")).getData("Image_ok")  );	
	
	  
	}

	@Test
	public void testCase3() { 
	  assertNotNull(Controller.getLoginPanel());
	  dati = Controller.getLoginPanel().getData(); 
	  dati.put("ID_action", "txtProxyHost"); 
	  dati.put("Event_type", SWT.FocusOut); 
	  ( (Text)  dati.get("txtProxyHost")).setText("http://apat.di.uniba.it:8081"); 
	  new ActionLoginPanel(dati);
	  assertTrue(Controller.getProxy().IsWebServiceRunning());
	  assertFalse( ((Label) dati.get("labelAlert")).getVisible());  
	  assertTrue(  (Boolean)	((Label)  dati.get("labelImageHost")).getData("Image_ok")  );
	  assertFalse(  (Boolean)	((Label)  dati.get("labelImageHost")).getData("Image_no")  );
		
	
	  
	}
	
	@After
	public void tearDown() throws Exception {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview"));
	}
	

}
