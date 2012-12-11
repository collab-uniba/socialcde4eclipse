package home.timeline;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;
import it.uniba.di.socialcdeforeclipse.shared.library.WPost;
import it.uniba.di.socialcdeforeclipse.shared.library.WUser;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UnitTestHomeTimeline extends TestCase {
	/**
	 * Unit test for User story number 38.
	 * 
	 * 
	 * Equivalence classes considered: 1.Current user have static friend.
	 * 2.Current user have not static friend. 3.Current user can send a post.
	 * 4.Current user can't send a post. Note: By default, current user have
	 * static friend.
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

	@Test
	public void testCase1() {
		WPost[] posts = Controller.getProxy().GetHomeTimeline(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword());
		assertTrue(posts.length > 0);
	}

	@Test
	public void testCase2() {
		Controller.setCurrentUserPassword("try1");
		WPost[] posts = Controller.getProxy().GetHomeTimeline(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword());
		assertTrue(posts.length == 0);
	}

	@Test
	public void testCase3() {
		assertTrue(Controller.getProxy().Post(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword(), "unit test message"));
	}

	@Test
	public void testCase4() {
		Controller.setCurrentUserPassword("try1");
		assertFalse(Controller.getProxy().Post(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword(), "unit test message"));
	}

	@After
	public void tearDown() {

	}

}
