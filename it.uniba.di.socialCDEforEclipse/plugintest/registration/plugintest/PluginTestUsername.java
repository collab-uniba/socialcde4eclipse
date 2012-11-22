package registration.plugintest;

import static org.junit.Assert.*;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import junit.framework.TestCase;

import it.uniba.di.socialcdeforeclipse.action.ActionLoginPanel;
import it.uniba.di.socialcdeforeclipse.action.ActionRegistrationPanel;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.staticview.RegistrationPanel;

import org.eclipse.ui.IWorkbenchWindow;
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

public class PluginTestUsername extends TestCase {

	/**
	 * Unit test if UI for User story number 26.
	 * 
	 * Field considered: ProxyHost
	 * 
	 * Field considered: Username
	 * 
	 * Equivalence classes considered: 
	 * 1.Empty string 
	 * 2.Not valid username 
	 * 3.Valid username
	 * 
	 * */
	
	HashMap<String, Object> dati; 
	Document document;

	
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
	  dati.put("ID_action", "txtUsername"); 
	  dati.put("Event_type", SWT.FocusOut); 
	  ( (Text)  dati.get("ProxyHost")).setText(document.getRootElement().getChild("CorrectData").getChild("Proxy").getText()); 
	  ( (Text)  dati.get("Username")).setText(""); 
	  new ActionRegistrationPanel(dati);
	  assertTrue( ((Label) dati.get("LabelAlert")).getVisible()); 
	  assertEquals("Please insert a valid username!", ((Label) dati.get("LabelAlert")).getText()); 
	  assertTrue(  (Boolean)	((Label)  dati.get("LabelImageUsername")).getData("Image_no")  );
	  assertFalse(  (Boolean)	((Label)  dati.get("LabelImageUsername")).getData("Image_ok")  );	
	
	  
	}
	
	@Test
	public void testCase2() { 
	  assertNotNull(Controller.getRegistrationPanel());
	  dati = Controller.getRegistrationPanel().getData(); 
	  dati.put("ID_action", "txtProxyHost"); 
	  dati.put("Event_type", SWT.FocusOut); 
	  ( (Text)  dati.get("ProxyHost")).setText(document.getRootElement().getChild("CorrectData").getChild("Proxy").getText()); 
	  new ActionRegistrationPanel(dati);
	  dati.put("ID_action", "txtUsername"); 
	  dati.put("Event_type", SWT.FocusOut);  
	  ( (Text)  dati.get("Username")).setText(document.getRootElement().getChild("CorrectData").getChild("Username").getText()); 
	  new ActionRegistrationPanel(dati);
	  assertNotNull(Controller.getProxy());
	  assertTrue( ((Label) dati.get("LabelAlert")).getVisible()); 
	  assertEquals("Please insert a valid username!", ((Label) dati.get("LabelAlert")).getText()); 
	  assertTrue(  (Boolean)	((Label)  dati.get("LabelImageUsername")).getData("Image_no")  );
	  assertFalse(  (Boolean)	((Label)  dati.get("LabelImageUsername")).getData("Image_ok")  );	
	
	  
	}

	@Test
	public void testCase3() { 
	  assertNotNull(Controller.getRegistrationPanel());
	  dati = Controller.getRegistrationPanel().getData(); 
	  
	  dati.put("ID_action", "txtProxyHost"); 
	  dati.put("Event_type", SWT.FocusOut); 
	  ( (Text)  dati.get("ProxyHost")).setText(document.getRootElement().getChild("CorrectData").getChild("Proxy").getText()); 
	  new ActionRegistrationPanel(dati);
	  
	  dati.put("ID_action", "txtUsername"); 
	  dati.put("Event_type", SWT.FocusOut);
	  ( (Text)  dati.get("ProxyHost")).setText(document.getRootElement().getChild("CorrectData").getChild("Proxy").getText()); 
	  ( (Text)  dati.get("Username")).setText(document.getRootElement().getChild("WrongData").getChild("Username").getText()); 
	  new ActionRegistrationPanel(dati);
	  assertTrue(  (Boolean)	((Label)  dati.get("LabelImageUsername")).getData("Image_ok")  );
	  assertFalse(  (Boolean)	((Label)  dati.get("LabelImageUsername")).getData("Image_no")  );
		
	
	  
	}
	
	@After
	public void tearDown() throws Exception {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview"));
	}
	

}
