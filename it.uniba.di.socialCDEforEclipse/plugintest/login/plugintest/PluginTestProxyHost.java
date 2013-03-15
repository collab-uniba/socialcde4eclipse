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
	String mainViewId; 
	Document document; 
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
		dati.put("ID_action", "txtProxyHost");
		dati.put("Event_type", SWT.FocusOut);
		((Text) dati.get("txtProxyHost")).setText("");
		new ActionLoginPanel(dati);
		assertNull(Controller.getProxy());
		assertTrue(((Label) dati.get("labelAlert")).getVisible());
		assertEquals("Please insert a valid proxy!",
				((Label) dati.get("labelAlert")).getText());
		
		GridData tempGrid = (GridData)  ((Label) dati.get("labelImageHostNo")).getLayoutData();				 
		 
		assertFalse(tempGrid.exclude);
		tempGrid = (GridData)  ((Label) dati.get("labelImageHostOk")).getLayoutData();
		assertTrue(tempGrid.exclude);

	}

	@Test
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
		dati.put("ID_action", "txtProxyHost");
		dati.put("Event_type", SWT.FocusOut);
		((Text) dati.get("txtProxyHost")).setText("www.google.it");
		new ActionLoginPanel(dati);
		assertNull(Controller.getProxy());
		assertTrue(((Label) dati.get("labelAlert")).getVisible());
		assertEquals("Please insert a valid proxy!",
				((Label) dati.get("labelAlert")).getText());
		
		GridData tempGrid = (GridData)  ((Label) dati.get("labelImageHostNo")).getLayoutData();				 
		assertTrue(!tempGrid.exclude);
		
		
		/*assertTrue((Boolean) ((Label) dati.get("labelImageHost"))
				.getData("Image_no"));
		assertFalse((Boolean) ((Label) dati.get("labelImageHost"))
				.getData("Image_ok"));*/

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
		dati.put("ID_action", "txtProxyHost");
		dati.put("Event_type", SWT.FocusOut);
		((Text) dati.get("txtProxyHost"))
				.setText("http://apat.di.uniba.it:8081");
		new ActionLoginPanel(dati);
		assertTrue(Controller.getProxy().IsWebServiceRunning());
		assertFalse(((Label) dati.get("labelAlert")).getVisible());
		
		GridData tempGrid = (GridData)  ((Label) dati.get("labelImageHostOk")).getLayoutData();				 
		assertFalse(tempGrid.exclude);
		
		
		
		/*assertTrue((Boolean) ((Label) dati.get("labelImageHost"))
				.getData("Image_ok"));
		assertFalse((Boolean) ((Label) dati.get("labelImageHost"))
				.getData("Image_no"));*/

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
