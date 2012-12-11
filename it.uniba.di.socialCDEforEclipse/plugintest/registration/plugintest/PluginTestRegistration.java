package registration.plugintest;

import static org.junit.Assert.*;

import java.util.HashMap;

import junit.framework.TestCase;

import it.uniba.di.socialcdeforeclipse.action.ActionLoginPanel;
import it.uniba.di.socialcdeforeclipse.action.ActionRegistrationPanel;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.staticview.RegistrationPanel;

import org.eclipse.ui.PlatformUI;
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

	@Before
	public void setUp() throws Exception {
		PlatformUI
				.getWorkbench()
				.getActiveWorkbenchWindow()
				.getActivePage()
				.showView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview");

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

		((Text) dati.get("ProxyHost")).setText("http://apat.di.uniba.it:8081");
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
		((Text) dati.get("ProxyHost")).setText("http://localhost:8081");
		((Text) dati.get("Email")).setText("o3822340@rtrtr.com");
		((Text) dati.get("InvitationCode")).setText("0T9y-K_!Ct");
		((Text) dati.get("Username")).setText("Antonio");
		((Text) dati.get("Password1")).setText("antionio24");
		((Text) dati.get("Password2")).setText("antionio24");

		new ActionRegistrationPanel(dati);
		assertNotNull(Controller.getProxy());
		assertEquals("Login", Controller.getWindowName());
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
										"it.uniba.di.socialcdeforeclipse.views.SocialCDEview"));

	}

}
