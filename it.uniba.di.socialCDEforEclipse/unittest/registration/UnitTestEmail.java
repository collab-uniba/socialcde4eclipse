package registration;

import java.io.File;
import java.io.IOException;

import it.uniba.di.socialcdeforeclipse.dynamic.view.InterceptingFilter;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class UnitTestEmail extends TestCase {
	/**
	 * Unit test for User story number 26.
	 * 
	 * Field considered: Email
	 * 
	 * Equivalence classes considered: 1.Empty string 2.String that is not an
	 * email 3.String that is an email
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
	}

	@Test
	public void testCase1() {
		String email = "";
		assertFalse(InterceptingFilter.verifyMail(email));
	}

	@Test
	public void testCase2() {
		String email = "prova.123at120.com";
		assertFalse(InterceptingFilter.verifyMail(email));
	}

	@Test
	public void testCase3() {
		String email = document.getRootElement().getChild("CorrectData")
				.getChild("Email").getText();
		assertTrue(InterceptingFilter.verifyMail(email));
	}

	@Test
	public void testCase4() {
		String email = ".@.com";
		assertFalse(InterceptingFilter.verifyMail(email));
	}
}
