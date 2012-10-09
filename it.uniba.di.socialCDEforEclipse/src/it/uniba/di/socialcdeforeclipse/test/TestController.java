package it.uniba.di.socialcdeforeclipse.test;

import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;


public class TestController extends TestCase {

	@Before public void setUp() {
		Controller.setPreferences("Username", "Floriano");
		Controller.setPreferences("ProxyHost", "Http://localhost:8081/socialTFSProxy.svc");
		Controller.setProxy(new ProxyWrapper()); 
		Controller.getProxy().setHost(Controller.getPreferences("ProxyHost")); 
		System.out.println("Before statement"); 
		
	}
	
	@Test public void testIsRegisteredTrue()
	{
		assertTrue(Controller.isRegistered());
	}
	
	@Test public void testIsUsernameAvailable()
	{
	  assertFalse(Controller.isUsernameAvailable(Controller.getPreferences("Username").toString()));
	}
	
}
