package login.plugintest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import junit.framework.TestCase;

import it.uniba.di.collab.socialcdeforeclipse.action.ActionLoginPanel;
import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.staticview.LoginPanel;

import org.eclipse.ui.PlatformUI;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class PluginTestLogin extends TestCase {

	/**
	 * Unit test for User story number 26.
	 * 
	 * Field considered: Login Button
	 * 
	 * Equivalence classes considered: 1.Fail login with wrong proxy 2.Fail
	 * login with username and password wrongs 3.Correct Login
	 * */

	HashMap<String, Object> dati;
	Document document;
	String mainViewId; 
	@Before
	public void setUp() throws Exception {

		SAXBuilder builder = new SAXBuilder();
		try {
			document = builder.build(new File("./testData.xml")
					.getCanonicalPath());
		} catch (JDOMException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainViewId = document.getRootElement().getChild("ViewInfo").getChild("MainView").getChild("Id").getText(); 
		PlatformUI
				.getWorkbench()
				.getActiveWorkbenchWindow()
				.getActivePage()
				.showView(mainViewId);

	}

	@Test
	public void testCase1() {
		if(Controller.getPreferences("username").equals(""))
		{
			assertNotNull(Controller.getRegistrationPanel()); 
			Controller.getRegistrationPanel().dispose(Controller.getWindow()); 
			Controller.setRegistration_panel(null); 
			Controller.setLoginPanel(new LoginPanel()); 
			Controller.getLoginPanel().inizialize(Controller.getWindow()); 
			assertNotNull(Controller.getLoginPanel()); 
			assertNotNull(Controller.getLoginPanel()); 
		}
		else
		{
			assertNotNull(Controller.getLoginPanel());
		}
		dati = Controller.getLoginPanel().getData();
		dati.put("ID_action", "btnLogin");
		dati.put("Event_type", SWT.Selection);
		((Text) dati.get("txtProxyHost")).setText(document.getRootElement()
				.getChild("WrongData").getChild("Proxy").getText());
		((Text) dati.get("txtUsername")).setText("Floriano");
		((Text) dati.get("txtPassword")).setText("gualtiero");
		new ActionLoginPanel(dati);
		
		GridData tempGrid = (GridData)  ((Label) dati.get("labelImageHostNo")).getLayoutData();				 
		assertFalse(tempGrid.exclude);
		tempGrid = (GridData)  ((Label) dati.get("labelImageHostOk")).getLayoutData();
		assertTrue(tempGrid.exclude);
		
		
		assertEquals("The connection with the Proxy failed",
				((Label) dati.get("labelAlert")).getText());
		
	}

	public void testCase2() {
		if(Controller.getPreferences("username").equals(""))
		{
			assertNotNull(Controller.getRegistrationPanel()); 
			Controller.getRegistrationPanel().dispose(Controller.getWindow()); 
			Controller.setRegistration_panel(null); 
			Controller.setLoginPanel(new LoginPanel()); 
			Controller.getLoginPanel().inizialize(Controller.getWindow()); 
			assertNotNull(Controller.getLoginPanel()); 
			assertNotNull(Controller.getLoginPanel()); 
		}
		else
		{
			assertNotNull(Controller.getLoginPanel());
		}
		dati = Controller.getLoginPanel().getData();
		dati.put("ID_action", "btnLogin");
		dati.put("Event_type", SWT.Selection);
		((Text) dati.get("txtProxyHost")).setText(document.getRootElement()
				.getChild("CorrectData").getChild("Proxy").getText());
		((Text) dati.get("txtUsername")).setText(document.getRootElement()
				.getChild("CorrectData").getChild("Username").getText());
		((Text) dati.get("txtPassword")).setText(document.getRootElement()
				.getChild("WrongData").getChild("Password").getText());
		new ActionLoginPanel(dati);
		assertEquals("username or password not valid!",
				((Label) dati.get("labelAlert")).getText());
		
		GridData tempGrid = (GridData)  ((Label) dati.get("labelImageUsernameNo")).getLayoutData();				 
		 
		assertFalse(tempGrid.exclude);
		tempGrid = (GridData)  ((Label) dati.get("labelImagePasswordNo")).getLayoutData();
		assertFalse(tempGrid.exclude);
		

	}

	@Test
	public void testCase3() {
		if(Controller.getPreferences("username").equals(""))
		{
			assertNotNull(Controller.getRegistrationPanel()); 
			Controller.getRegistrationPanel().dispose(Controller.getWindow()); 
			Controller.setRegistration_panel(null); 
			Controller.setLoginPanel(new LoginPanel()); 
			Controller.getLoginPanel().inizialize(Controller.getWindow()); 
			assertNotNull(Controller.getLoginPanel()); 
			assertNotNull(Controller.getLoginPanel()); 
		}
		else
		{
			assertNotNull(Controller.getLoginPanel());
		}
		dati = Controller.getLoginPanel().getData();
		dati.put("ID_action", "btnLogin");
		dati.put("Event_type", SWT.Selection);
		((Text) dati.get("txtProxyHost")).setText(document.getRootElement()
				.getChild("CorrectData").getChild("Proxy").getText());
		((Text) dati.get("txtUsername")).setText(document.getRootElement()
				.getChild("CorrectData").getChild("Username").getText());
		((Text) dati.get("txtPassword")).setText(document.getRootElement()
				.getChild("CorrectData").getChild("Password").getText());
		new ActionLoginPanel(dati);
		assertTrue(Controller.getProxy().IsWebServiceRunning());
		assertNotNull(Controller.getCurrentUser());
		assertEquals(document.getRootElement().getChild("CorrectData")
				.getChild("Username").getText(),
				Controller.getCurrentUser().Username);
		assertEquals(document.getRootElement().getChild("CorrectData")
				.getChild("Password").getText(),
				Controller.getCurrentUserPassword());
		assertNotNull(Controller.getHomeWindow());
		assertNull(Controller.getLoginPanel());
		assertEquals("Home", Controller.getWindowName());

	}

	@After
	public void tearDown() throws Exception {
		PlatformUI
				.getWorkbench()
				.getActiveWorkbenchWindow()
				.getActivePage()
				.hideView(
						PlatformUI
								.getWorkbench()
								.getActiveWorkbenchWindow()
								.getActivePage()
								.findView(
										mainViewId));
	}

}
