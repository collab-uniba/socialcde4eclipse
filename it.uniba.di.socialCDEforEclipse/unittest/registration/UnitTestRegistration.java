/**
 * 
 */
package registration;

import it.uniba.di.socialcdeforeclipse.action.ActionRegistrationPanel;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.eclipse.swt.widgets.Text;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author Flo
 * 
 */
public class UnitTestRegistration extends TestCase {
	/**
	 * Unit test for User story number 26.
	 * 
	 * Field considered: Registration button
	 * 
	 * Equivalence classes considered: 1.All field are empty 2.Same field are
	 * empty 3.All field are not empty but with wrong information 4.All field
	 * contains correct information
	 * 
	 * */

	Document document;

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

		// For testCase 4, I suppose that an invitation email was send to
		// o3401944@rtrtr.com and
		// an username like 'John' is available
		ProxyWrapper pw = new ProxyWrapper();
		pw.setHost(document.getRootElement().getChild("CorrectData")
				.getChild("Proxy").getText());
		Controller.setProxy(pw);
		assertTrue(pw.IsAvailable(document.getRootElement()
				.getChild("CorrectData").getChild("Username").getText()));

	}

	@Test
	public void testCase1() {

		HashMap<String, String> dataExtracted = new HashMap<String, String>();
		dataExtracted.put("Username", "");
		dataExtracted.put("Password1", "");
		dataExtracted.put("Password2", "");
		dataExtracted.put("InvitationCode", "");
		dataExtracted.put("Email", "");
		dataExtracted.put("ProxyHost", "");

		ActionRegistrationPanel action = new ActionRegistrationPanel();

		int res = action.actionRegister(dataExtracted);

		assertEquals(-3, res);
	}

	@Test
	public void testCase2() {

		HashMap<String, String> dataExtracted = new HashMap<String, String>();
		dataExtracted.put(
				"Username",
				document.getRootElement().getChild("WrongData")
						.getChild("Username").getText());
		dataExtracted.put("Password1", "");
		dataExtracted.put("Password2", "");
		dataExtracted.put("InvitationCode", "");
		dataExtracted.put(
				"Email",
				document.getRootElement().getChild("WrongData")
						.getChild("Email").getText());
		dataExtracted.put(
				"ProxyHost",
				document.getRootElement().getChild("CorrectData")
						.getChild("Proxy").getText());

		ActionRegistrationPanel action = new ActionRegistrationPanel();

		int res = action.actionRegister(dataExtracted);

		assertEquals(-3, res);
	}

	@Test
	public void testCase3() {

		HashMap<String, String> dataExtracted = new HashMap<String, String>();
		dataExtracted.put(
				"Username",
				document.getRootElement().getChild("WrongData")
						.getChild("Username").getText());
		dataExtracted.put(
				"Password1",
				document.getRootElement().getChild("WrongData")
						.getChild("Password").getText());
		dataExtracted.put(
				"Password2",
				document.getRootElement().getChild("WrongData")
						.getChild("Second_Password").getText());
		dataExtracted.put(
				"InvitationCode",
				document.getRootElement().getChild("WrongData")
						.getChild("Invitation_Code").getText());
		dataExtracted.put(
				"Email",
				document.getRootElement().getChild("WrongData")
						.getChild("Email").getText());
		dataExtracted.put(
				"ProxyHost",
				document.getRootElement().getChild("CorrectData")
						.getChild("Proxy").getText());
		ActionRegistrationPanel action = new ActionRegistrationPanel();

		int res = action.actionRegister(dataExtracted);

		assertFalse(res == 0);
	}

	@Test
	public void testCase4() {

		HashMap<String, String> dataExtracted = new HashMap<String, String>();
		dataExtracted.put(
				"Username",
				document.getRootElement().getChild("CorrectData")
						.getChild("Username").getText());
		dataExtracted.put(
				"Password1",
				document.getRootElement().getChild("CorrectData")
						.getChild("Password").getText());
		dataExtracted.put(
				"Password2",
				document.getRootElement().getChild("CorrectData")
						.getChild("Second_Password").getText());
		dataExtracted.put(
				"InvitationCode",
				document.getRootElement().getChild("CorrectData")
						.getChild("Invitation_Code").getText());
		dataExtracted.put(
				"Email",
				document.getRootElement().getChild("CorrectData")
						.getChild("Email").getText());
		dataExtracted.put(
				"ProxyHost",
				document.getRootElement().getChild("CorrectData")
						.getChild("Proxy").getText());
		ActionRegistrationPanel action = new ActionRegistrationPanel();

		int res = action.actionRegister(dataExtracted);

		assertEquals(0, res);
	}
}
