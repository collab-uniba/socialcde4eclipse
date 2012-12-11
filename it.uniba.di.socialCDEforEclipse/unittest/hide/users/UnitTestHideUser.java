package hide.users;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;
import it.uniba.di.socialcdeforeclipse.shared.library.WUser;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UnitTestHideUser extends TestCase {
	/**
	 * Unit test for User story number 42.
	 * 
	 * 
	 * Equivalence classes considered: 1.Hide user from suggestion 2.Hide user
	 * from itaraction timeline 3.Hide user from interactive timeline 4.Hide
	 * user with wrong id;
	 * 
	 * Note: For this test will be choose a random user between suggested user.
	 * 
	 * */

	Document document;
	WUser[] users;
	int positionUser;

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

		Controller.setProxy(new ProxyWrapper());
		Controller.getProxy().setHost(
				document.getRootElement().getChild("CorrectData")
						.getChild("Proxy").getText());
		Controller.setCurrentUser(Controller.getProxy().GetUser(
				document.getRootElement().getChild("CorrectData")
						.getChild("Username").getText(),
				document.getRootElement().getChild("CorrectData")
						.getChild("Password").getText()));
		Controller.setCurrentUserPassword(document.getRootElement()
				.getChild("CorrectData").getChild("Password").getText());

		users = Controller.getProxy().GetSuggestedFriends(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword());

	}

	@Test
	public void testCase1() {
		positionUser = (int) (Math.random() * (users.length - 1));

		assertTrue(Controller.getProxy().UpdateHiddenUser(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword(), users[positionUser].Id,
				true, false, false));

	}

	@Test
	public void testCase2() {
		positionUser = (int) (Math.random() * (users.length - 1));

		assertTrue(Controller.getProxy().UpdateHiddenUser(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword(), users[positionUser].Id,
				false, true, false));

	}

	@Test
	public void testCase3() {
		positionUser = (int) (Math.random() * (users.length - 1));

		assertTrue(Controller.getProxy().UpdateHiddenUser(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword(), users[positionUser].Id,
				false, false, true));

	}

	@Test
	public void testCase4() {
		positionUser = -1;

		assertFalse(Controller.getProxy().UpdateHiddenUser(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword(), positionUser, false,
				false, true));

	}

	@After
	public void tearDown() {

		if (positionUser != -1) {
			assertTrue(Controller.getProxy().UpdateHiddenUser(
					Controller.getCurrentUser().Username,
					Controller.getCurrentUserPassword(),
					users[positionUser].Id, false, false, false));
		}

	}

}
