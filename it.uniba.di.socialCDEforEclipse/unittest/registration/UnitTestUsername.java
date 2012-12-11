package registration;

import java.io.File;
import java.io.IOException;

import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class UnitTestUsername extends TestCase {
	/**
	 * Unit test for User story number 26.
	 * 
	 * Field considered: Username
	 * 
	 * Equivalence classes considered: 1.Empty string 2.Valid username 3.Not
	 * valid username
	 * */

	ProxyWrapper pw;
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

		pw = new ProxyWrapper();
		pw.setHost(document.getRootElement().getChild("CorrectData")
				.getChild("Proxy").getText());
		// assertFalse(pw.IsAvailable("Giacomo"));
	}

	@Test
	public void testCase1() {
		String username = " ";
		assertFalse(pw.IsAvailable(username));
	}

	@Test
	public void testCase2() {
		String username = document.getRootElement().getChild("WrongData")
				.getChild("Username").getText();
		assertTrue(pw.IsAvailable(username));
	}

	@Test
	public void testCase3() {
		String username = document.getRootElement().getChild("CorrectData")
				.getChild("Username").getText();
		assertFalse(pw.IsAvailable(username));
	}
}
