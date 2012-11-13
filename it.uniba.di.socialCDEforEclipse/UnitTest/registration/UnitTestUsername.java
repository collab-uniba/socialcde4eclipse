package registration;

import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class UnitTestUsername extends TestCase {
	/**
	 * Unit test for User story number 26.
	 * 
	 * Field considered: Username
	 * 
	 * Equivalence classes considered: 
	 * 1.Empty string 
	 * 2.Valid username 
	 * 3.Not valid username
	 * */

	ProxyWrapper pw;

	@Before
	public void setUp() {
		pw = new ProxyWrapper();
		pw.setHost("http://apat.di.uniba.it:8081");
		// assertFalse(pw.IsAvailable("Giacomo"));
	}

	@Test
	public void testCase1() {
		String username = " ";
		assertFalse(pw.IsAvailable(username));
	}

	@Test
	public void testCase2() {
		String username = "Giacomo";
		assertTrue(pw.IsAvailable(username));
	}

	@Test
	public void testCase3() {
		String username = "Floriano";
		assertFalse(pw.IsAvailable(username));
	}
}
