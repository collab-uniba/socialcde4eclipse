package registration.plugintest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import junit.framework.TestCase;

import it.uniba.di.collab.socialcdeforeclipse.action.ActionRegistrationPanel;
import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.staticview.RegistrationPanel;

import org.eclipse.ui.PartInitException;
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

public class PluginTestEmail extends TestCase {

	/**
	 * Unit test if UI for User story number 26.
	 * 
	 * Field considered: ProxyHost
	 * 
	 * Equivalence classes considered: 1.Empty string 2.String that is not an
	 * email 3.String that is an email
	 * */

	HashMap<String, Object> dati;
	Document document;
	String mainViewId; 
	@Before
	public void setUp() {

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
		
		try {
			PlatformUI
					.getWorkbench()
					.getActiveWorkbenchWindow()
					.getActivePage()
					.showView(
							mainViewId);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(Controller.getRegistrationPanel() == null)
		{
			assertNotNull(Controller.getLoginPanel()); 
			Controller.getLoginPanel().dispose(Controller.getWindow()); 
			Controller.setLoginPanel(null); 
			Controller.setRegistration_panel(new RegistrationPanel()); 
			Controller.getRegistrationPanel().inizialize(Controller.getWindow()); 
			assertNotNull(Controller.getRegistrationPanel());
		}
		else
		{
			assertNotNull(Controller.getRegistrationPanel());
			Controller.setRegistration_panel(null); 
			Controller.setRegistration_panel(new RegistrationPanel()); 
			Controller.getRegistrationPanel().inizialize(Controller.getWindow()); 
			assertNotNull(Controller.getRegistrationPanel());
		}

	}

	@Test
	public void testCase1() {

		assertNotNull(Controller.getRegistrationPanel());
		dati = Controller.getRegistrationPanel().getData();
		dati.put("ID_action", "txtMail");
		dati.put("Event_type", SWT.FocusOut);
		((Text) dati.get("Email")).setText("");
		new ActionRegistrationPanel(dati);
		assertTrue(((Label) dati.get("LabelAlert")).getVisible());
		assertEquals("Please insert a valid mail!",
				((Label) dati.get("LabelAlert")).getText());
		GridData tempGrid = (GridData) ((Label) dati.get("LabelImageMailNo")).getLayoutData(); 
		assertFalse(tempGrid.exclude);
		tempGrid = (GridData) ((Label) dati.get("LabelImageMailOk")).getLayoutData(); 
		assertTrue(tempGrid.exclude);
		tempGrid = (GridData) ((Label) dati.get("LabelImageMailHidden")).getLayoutData(); 
		assertTrue(tempGrid.exclude);
		
		
	

	}

	@Test
	public void testCase2() {
		assertNotNull(Controller.getRegistrationPanel());
		dati = Controller.getRegistrationPanel().getData();
		dati.put("ID_action", "txtMail");
		dati.put("Event_type", SWT.FocusOut);
		((Text) dati.get("Email")).setText(document.getRootElement()
				.getChild("WrongData").getChild("Proxy").getText());
		new ActionRegistrationPanel(dati);
		assertTrue(((Label) dati.get("LabelAlert")).getVisible());
		assertEquals("Please insert a valid mail!",
				((Label) dati.get("LabelAlert")).getText());
		
		GridData tempGrid = (GridData) ((Label) dati.get("LabelImageMailNo")).getLayoutData(); 
		assertFalse(tempGrid.exclude);
		tempGrid = (GridData) ((Label) dati.get("LabelImageMailOk")).getLayoutData(); 
		assertTrue(tempGrid.exclude);
		tempGrid = (GridData) ((Label) dati.get("LabelImageMailHidden")).getLayoutData(); 
		assertTrue(tempGrid.exclude);
		
		

	}

	@Test
	public void testCase3() {
		assertNotNull(Controller.getRegistrationPanel());
		((Text) Controller.getRegistrationPanel().getData().get("Email")).setText(document.getRootElement()
				.getChild("WrongData").getChild("Email").getText());
		dati = Controller.getRegistrationPanel().getData();
		dati.put("ID_action", "txtMail");
		dati.put("Event_type", SWT.FocusOut);
		
		new ActionRegistrationPanel(dati);
		
		GridData tempGrid = (GridData) ((Label) dati.get("LabelImageMailNo")).getLayoutData(); 
		assertTrue(tempGrid.exclude);
		tempGrid = (GridData) ((Label) dati.get("LabelImageMailOk")).getLayoutData(); 
		assertFalse(tempGrid.exclude);
		tempGrid = (GridData) ((Label) dati.get("LabelImageMailHidden")).getLayoutData(); 
		assertTrue(tempGrid.exclude);
	

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
