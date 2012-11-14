package login;

import java.io.File;
import java.io.IOException;

import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class UnitTestProxyHost extends TestCase {
	/**
	 * Unit test for User story number 26.
	 * 
	 * Field considered: ProxyHost
	 * 
	 * Equivalence classes considered: 1.Empty string 2.String that not link to
	 * server 3.String that link to server
	 * */

	
	Document document;
	
	@Before
	public void setUp()
	{ 	
		SAXBuilder builder = new SAXBuilder();
		try {
			document = builder.build(new File("./testData.xml").getCanonicalPath());
		} catch (JDOMException e) {
			System.out.println("Eccezione lanciata"); 
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCase1() {
		String proxyHost = "";
		ProxyWrapper pw = new ProxyWrapper();
		pw.setHost(proxyHost);
		assertFalse(pw.IsWebServiceRunning());
	}

	@Test
	public void testCase2() {
		String proxyHost = document.getRootElement().getChild("WrongData").getChild("Proxy").getText();
		ProxyWrapper pw = new ProxyWrapper();
		pw.setHost(proxyHost);
		assertFalse(pw.IsWebServiceRunning());
	}

	@Test
	public void testCase3() {
		String proxyHost = document.getRootElement().getChild("CorrectData").getChild("Proxy").getText();
		ProxyWrapper pw = new ProxyWrapper();
		pw.setHost(proxyHost);
		assertTrue(pw.IsWebServiceRunning());
	}
}
