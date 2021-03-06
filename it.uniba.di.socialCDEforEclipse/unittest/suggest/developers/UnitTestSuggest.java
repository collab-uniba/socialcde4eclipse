package suggest.developers;

import static org.junit.Assert.*;

import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.model.ProxyWrapper;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.WUser;

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UnitTestSuggest {
	/**
	 * Unit test for User story number 35.
	 * 
	 * Action considered: Suggests a developer
	 * 
	 * Equivalence classes considered: 1.There are developers that the system
	 * can suggests. 2.There aren't developers to suggests.
	 * 
	 * */
	Document document;
	int positionUser;
	WUser[] users;

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

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testCase1() {

		users = Controller.getProxy().GetSuggestedFriends(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword());
		assertTrue(users.length > 0);
	}

	@Test
	public void testCase2() {

		users = Controller.getProxy().GetSuggestedFriends(
				Controller.getCurrentUser().Username, "try");
		assertTrue(users.length == 0);
	}

}
