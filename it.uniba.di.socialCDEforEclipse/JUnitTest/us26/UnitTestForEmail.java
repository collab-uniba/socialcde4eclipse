package us26;

import it.uniba.di.socialcdeforeclipse.dynamicView.InterceptingFilter;

import org.junit.Test;

import junit.framework.TestCase;

public class UnitTestForEmail extends TestCase {
	/**
	 * Unit test for User story number 26.
	 * 
	 * Field considered: Email
	 * 
	 * Equivalence classes considered: 
	 * 1.Empty string 
	 * 2.String that is not an email 
	 * 3.String that is an email
	 * */

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
		String email = "prova.123@email.com";
		assertTrue(InterceptingFilter.verifyMail(email));
	}
}
