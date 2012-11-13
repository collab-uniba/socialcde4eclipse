package login;

import junit.framework.TestCase;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;

import org.junit.Before;
import org.junit.Test;

public class UnitTestLogin extends TestCase {
	/**
	 * Unit test for User story number 26.
	 * 
	 * Field considered: Login Button
	 * 
	 * Equivalence classes considered: 1.Correct Login 2.Fail login
	 * */
	
	ProxyWrapper pw; 
	
	@Before
	public void setUp()
	{
		pw = new ProxyWrapper();  
		
	}
	
	@Test
	public void testCase1()
	{
		String proxyServer = "http://apat.di.uniba.it:8081"; 
		String username = "Floriano"; 
		String password = "gualtiero"; 
		
		pw.setHost(proxyServer); 
		assertTrue(pw.IsWebServiceRunning()); 
		
		assertNotNull(pw.GetUser(username, password)); 
		
	}
	
	@Test
	public void testCase2()
	{
		String proxyServer = "http://apat.di.uniba.it:8081"; 
		String username = "Floriano"; 
		String password = "antonio"; 
		
		pw.setHost(proxyServer); 
		assertTrue(pw.IsWebServiceRunning()); 
		
		assertNull(pw.GetUser(username, password)); 
		
	}
	

}
