package login.plugintest;

import static org.junit.Assert.*;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import junit.framework.TestCase;

import it.uniba.di.socialcdeforeclipse.action.ActionLoginPanel;
import it.uniba.di.socialcdeforeclipse.controller.Controller;

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

public class PluginTestLogin extends TestCase {

	/**
	 * Unit test for User story number 26.
	 * 
	 * Field considered: Login Button
	 * 
	 * Equivalence classes considered:  
	 * 1.Fail login with wrong proxy
	 * 2.Fail login with username and password wrongs
	 * 3.Correct Login
	 * */
	
	HashMap<String, Object> dati; 
	Document document; 

	
	@Before
	public void setUp() throws Exception {
		
		SAXBuilder builder = new SAXBuilder();
		try {
			document = builder.build(new File("./testData.xml").getCanonicalPath());
		} catch (JDOMException e) {
			System.out.println("Eccezione lanciata"); 
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview");
	
	}
	
	@Test
	public void testCase1() { 
		 assertNotNull(Controller.getLoginPanel());
		  dati = Controller.getLoginPanel().getData(); 
		  dati.put("ID_action", "btnLogin"); 
		  dati.put("Event_type", SWT.Selection); 
		  ( (Text)  dati.get("txtProxyHost")).setText(document.getRootElement().getChild("WrongData").getChild("Proxy").getText());
		  ( (Text)  dati.get("txtUsername")).setText("Floriano");
		  ( (Text)  dati.get("txtPassword")).setText("gualtiero");
		  new ActionLoginPanel(dati);
		  assertTrue( (Boolean) ((Label) dati.get("labelImageHost")).getData("Image_no")); 
		  assertFalse((Boolean) ((Label) dati.get("labelImageHost")).getData("Image_ok"));
		  assertEquals("The connection with the Proxy failed",  ((Label) dati.get("labelAlert")).getText()); 
		  assertTrue( ((Label)  dati.get("labelImageHost")).getVisible());  
	
	}
	
	public void testCase2() { 
		 assertNotNull(Controller.getLoginPanel());
		  dati = Controller.getLoginPanel().getData(); 
		  dati.put("ID_action", "btnLogin"); 
		  dati.put("Event_type", SWT.Selection); 
		  ( (Text)  dati.get("txtProxyHost")).setText(document.getRootElement().getChild("CorrectData").getChild("Proxy").getText());
		  ( (Text)  dati.get("txtUsername")).setText(document.getRootElement().getChild("CorrectData").getChild("Username").getText());
		  ( (Text)  dati.get("txtPassword")).setText(document.getRootElement().getChild("WrongData").getChild("Password").getText());
		  new ActionLoginPanel(dati);
		  assertEquals("username or password not valid!", ((Label) dati.get("labelAlert")).getText());
		  assertTrue( ((Label)  dati.get("labelImageHost")).getVisible());  
		  assertTrue( ((Label)  dati.get("labelImageHost")).getVisible());
		  
		  assertTrue( (Boolean) ((Label) dati.get("labelImageUsername")).getData("Image_no"));
		  assertTrue( (Boolean) ((Label) dati.get("labelImagePassword")).getData("Image_no"));
		   
	
	}
	
	@Test
	public void testCase3() { 
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
	  assertEquals("Floriano", Controller.getCurrentUser().Username); 
	  assertEquals("gualtiero", Controller.getCurrentUserPassword()); 
	  assertNotNull(Controller.getHomeWindow()); 
	  assertNull(Controller.getLoginPanel()); 
	  assertEquals("Home",Controller.getWindowName()); 
	  
	}
	
	@After
	public void tearDown() throws Exception {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview"));
	}
	
	
	

	
	

}
