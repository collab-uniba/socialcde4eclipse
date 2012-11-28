package suggest.developers.plugintest;

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
import it.uniba.di.socialcdeforeclipse.popup.ChooseAvatar;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class PluginTestSuggest extends TestCase {

	/**
	 * Unit test for User story number 35.
	 * 
	 * Action considered: Suggests a developer
	 * 
	 * Equivalence classes considered: 
	 * 1.There are developers that the system can suggests.
	 * 2.There aren't developers to suggests. 
	 * 
	 * */
	
	static HashMap<String, Object> dati; 
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
		
		 assertNotNull(Controller.getLoginPanel());
		  dati = Controller.getLoginPanel().getData(); 
		  dati.put("ID_action", "btnLogin"); 
		  dati.put("Event_type", SWT.Selection); 
		  ( (Text)  dati.get("txtProxyHost")).setText(document.getRootElement().getChild("CorrectData").getChild("Proxy").getText());
		  ( (Text)  dati.get("txtUsername")).setText(document.getRootElement().getChild("CorrectData").getChild("Username").getText());
		  ( (Text)  dati.get("txtPassword")).setText(document.getRootElement().getChild("CorrectData").getChild("Password").getText());
		  new ActionLoginPanel(dati);
		assertNotNull(Controller.getHomeWindow()); 
		
	}
	
	@Test public void testCase1()
	{
		
		Controller.selectDynamicWindow(2); 
		assertNotNull(Controller.getPeopleWindow()); 
		dati = Controller.getPeopleWindow().getData();
		
		if(dati.containsKey("userSuggested"))
		{
			assertTrue( ((ArrayList<Composite>)  dati.get("userSuggested")).size() > 0); 
		}
		else
		{
			fail("no developers founded"); 
		}
		  
	}
	
	@Test public void testCase2()
	{
		String psw = Controller.getCurrentUserPassword();
		 Controller.setCurrentUserPassword("prova");
		Controller.selectDynamicWindow(2); 
		assertNotNull(Controller.getPeopleWindow()); 
		dati = Controller.getPeopleWindow().getData();
		
		if(dati.containsKey("labelsuggestionText"))
		{
			assertEquals("We have no suggestion for you.\n Please try again soon.", ((Label) dati.get("labelsuggestionText")).getText()); 
		}
		else
		{
			fail("Test not passed"); 
		}
		
		 
	}
	
	@After
	public void tearDown() throws Exception {
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview"));
	}
	
	
	

	
	

}
