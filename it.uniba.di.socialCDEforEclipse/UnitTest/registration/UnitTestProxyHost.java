package registration;

import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;

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

	@Test
	public void testCase1() {
		String proxyHost = "";
		ProxyWrapper pw = new ProxyWrapper();
		pw.setHost(proxyHost);
		assertFalse(pw.IsWebServiceRunning());
	}

	@Test
	public void testCase2() {
		String proxyHost = "http://www.google.it";
		ProxyWrapper pw = new ProxyWrapper();
		pw.setHost(proxyHost);
		assertFalse(pw.IsWebServiceRunning());
	}

	@Test
	public void testCase3() {
		String proxyHost = "http://apat.di.uniba.it:8081";
		ProxyWrapper pw = new ProxyWrapper();
		pw.setHost(proxyHost);
		assertTrue(pw.IsWebServiceRunning());
	}

}
