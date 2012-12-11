package choose.image;

import static org.junit.Assert.*;

import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;
import it.uniba.di.socialcdeforeclipse.shared.library.WService;
import it.uniba.di.socialcdeforeclipse.shared.library.WUser;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UnitTestImage {
	/**
	 * Unit test for User story number 29.
	 * 
	 * Action considered: Choose an image
	 * 
	 * Equivalence classes considered: 1. Choose an image between the available
	 * 2. No images are available
	 * */
	Document document;
	HashMap<String, Object> data;

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

		URI[] images = Controller.getProxy().GetAvailableAvatars(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword());
		int imageChoose = (int) (Math.random() * images.length);

		assertTrue(Controller.getProxy().SaveAvatar(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword(), images[imageChoose]));

	}

	@Test
	public void testCase2() {
		Controller.setCurrentUserPassword("try1");
		URI[] images = Controller.getProxy().GetAvailableAvatars(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword());
		assertTrue(images.length == 0);

	}

}
