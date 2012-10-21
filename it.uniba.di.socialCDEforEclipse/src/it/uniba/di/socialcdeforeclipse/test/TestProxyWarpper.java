package it.uniba.di.socialcdeforeclipse.test;


import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.*;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class TestProxyWarpper extends TestCase {

	ProxyWrapper pW;
	
	@Before public void setUp() {
		
		Controller.setProxy(new ProxyWrapper()); 
		Controller.getProxy().setHost("http://apat.di.uniba.it:8081"); 
		Controller.setCurrentUserPassword("patrizio1");
		WUser utente = Controller.getProxy().GetUser("Floriano",Controller.getCurrentUserPassword() ); 
		Controller.setCurrentUser(utente); 
		System.out.println("Before statement"); 
		
	}
	
	@Test public void testAuthorize()
	{
		assertTrue(Controller.getProxy().Authorize(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), 5, "71991", "795c5b17-8a84-4c98-87fd-a70ad253d198", "86f8c4ef-8e77-4e78-b0d3-ee980eed175f"));
	}
	
	@Test public void testServices()
	{
		
		assertNotNull(Controller.getProxy().GetServices(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword())); 
		WService[] servizi = Controller.getProxy().GetServices(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		
		for(int i=0;i< servizi.length;i++)
		{
			System.out.println("Service n. " + i + " dato: " + servizi[i].Name);
			System.out.println("Tegistered n. " + i + " dato: " + servizi[i].Registered);
		}
	}
	
	@Test public void testFollowers()
	{
		assertNotNull(Controller.getProxy().GetFollowers(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword())); 
		WUser[] followers = Controller.getProxy().GetFollowers(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		for(int i=0;i< followers.length;i++)
		{
			System.out.println("Followers n. " + i + " user: " + followers[i].Username);
		}
	}
	
	@Test public void testFollowing()
	{
		assertNotNull(Controller.getProxy().GetFollowings(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword())); 
		WUser[] following = Controller.getProxy().GetFollowings(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		
		System.out.println("Number of following "+ following.length); 
		
		for(int i=0;i< following.length;i++)
		{
			System.out.println("Followers n. " + i + " user: " + following[i].Username);
		}
	}
}
