package registration.plugintest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import junit.framework.TestCase;

import it.uniba.di.collab.socialcdeforeclipse.action.ActionLoginPanel;
import it.uniba.di.collab.socialcdeforeclipse.action.ActionRegistrationPanel;
import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.staticview.RegistrationPanel;

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

public class PluginTestRegistration extends TestCase {

	/**
	 * Unit test if UI for User story number 26.
	 * 
	 * Field considered: Registration button
	 * 
	 * Equivalence classes considered: 1.All field are empty 2.Same field are
	 * empty 3.All field are not empty but with wrong information 4.All field
	 * contains correct information
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
		}
	}

	@Test
	public void testCase1() {

		assertNotNull(Controller.getRegistrationPanel());
		dati = Controller.getRegistrationPanel().getData();
		dati.put("ID_action", "btnRegister");
		dati.put("Event_type", SWT.Selection);

		((Text) dati.get("ProxyHost")).setText("");
		((Text) dati.get("Email")).setText("");
		((Text) dati.get("InvitationCode")).setText("");
		((Text) dati.get("Username")).setText("");
		((Text) dati.get("Password1")).setText("");
		((Text) dati.get("Password2")).setText("");

		new ActionRegistrationPanel(dati);
		assertNull(Controller.getProxy());
		assertTrue(((Label) dati.get("LabelAlert")).getVisible());
		assertEquals("Please compile all field correctly!",
				((Label) dati.get("LabelAlert")).getText());

	}

	@Test
	public void testCase2() {
		assertNotNull(Controller.getRegistrationPanel());
		dati = Controller.getRegistrationPanel().getData();
		dati.put("ID_action", "btnRegister");
		dati.put("Event_type", SWT.Selection);

		((Text) dati.get("ProxyHost")).setText("http://apat.di.uniba.it:8081");
		((Text) dati.get("Email")).setText("prova.1123@hotmail.com");
		((Text) dati.get("InvitationCode")).setText("");
		((Text) dati.get("Username")).setText("Antonio123");
		((Text) dati.get("Password1")).setText("");
		((Text) dati.get("Password2")).setText("");
		Controller.setProxy(null); 
		new ActionRegistrationPanel(dati);
		assertNull(Controller.getProxy());
		assertTrue(((Label) dati.get("LabelAlert")).getVisible());
		assertEquals("Please compile all field correctly!",
				((Label) dati.get("LabelAlert")).getText());

	}

	@Test
	public void testCase3() {
		assertNotNull(Controller.getRegistrationPanel());
		dati = Controller.getRegistrationPanel().getData();
		dati.put("ID_action", "btnRegister");
		dati.put("Event_type", SWT.Selection);

		((Text) dati.get("ProxyHost")).setText(document.getRootElement()
				.getChild("RegistrationData").getChild("Proxy").getText());
		((Text) dati.get("Email")).setText("prova.1123@hotmail.com");
		((Text) dati.get("InvitationCode")).setText("2514");
		((Text) dati.get("Username")).setText("Antonio123");
		((Text) dati.get("Password1")).setText("antionio");
		((Text) dati.get("Password2")).setText("antionio");

		new ActionRegistrationPanel(dati);
		assertNotNull(Controller.getProxy());
		assertTrue(((Label) dati.get("LabelAlert")).getVisible());
		assertEquals("Please insert the email on which you recived the invite",
				((Label) dati.get("LabelAlert")).getText());

	}

	@Test
	public void testCase4() {
		assertNotNull(Controller.getRegistrationPanel());
		dati = Controller.getRegistrationPanel().getData();
		dati.put("ID_action", "btnRegister");
		dati.put("Event_type", SWT.Selection);
		Controller.setProxy(null);
		((Text) dati.get("ProxyHost")).setText(document.getRootElement()
				.getChild("RegistrationData").getChild("Proxy").getText());
		((Text) dati.get("Email")).setText(document.getRootElement()
				.getChild("RegistrationData").getChild("Email").getText());
		((Text) dati.get("InvitationCode")).setText(document.getRootElement()
				.getChild("RegistrationData").getChild("Invitation_Code").getText());
		((Text) dati.get("Username")).setText(document.getRootElement()
				.getChild("RegistrationData").getChild("Username").getText());
		((Text) dati.get("Password1")).setText(document.getRootElement()
				.getChild("RegistrationData").getChild("Password").getText());
		((Text) dati.get("Password2")).setText(document.getRootElement()
				.getChild("RegistrationData").getChild("Second_Password").getText());

		new ActionRegistrationPanel(dati);
		assertNotNull(Controller.getProxy());
		assertNotNull(Controller.getLoginPanel());

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
