package it.uniba.di.socialcdeforeclipse.test;

import it.uniba.di.socialCDEforEclipse.SharedLibrary.WService;
import it.uniba.di.socialCDEforEclipse.SharedLibrary.WUser;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class TestProxyWarpper extends TestCase {

	ProxyWrapper pW;
	
	@Before public void setUp() {
		
		Controller.setProxy(new ProxyWrapper()); 
		Controller.getProxy().setHost("http://apat.di.uniba.it:8081"); 
		WUser utente = Controller.getProxy().GetUser("Floriano", "pamela2781983"); 
		Controller.setCurrentUser(utente); 
		System.out.println("Before statement"); 
		
	}
	
	@Test public void testServices()
	{
		
		assertNotNull(Controller.getProxy().GetServices(Controller.getCurrentUser().Username, "pamela2781983")); 
		WService[] servizi = Controller.getProxy().GetServices(Controller.getCurrentUser().Username, "pamela2781983"); 
		
		for(int i=0;i< servizi.length;i++)
		{
			System.out.println("Service n. " + i + " dato: " + servizi[i].Name);
			System.out.println("Tegistered n. " + i + " dato: " + servizi[i].Registered);
		}
	}
	
	@Test public void testFollowers()
	{
		assertNotNull(Controller.getProxy().GetFollowers(Controller.getCurrentUser().Username, "pamela2781983")); 
		WUser[] followers = Controller.getProxy().GetFollowers(Controller.getCurrentUser().Username, "pamela2781983"); 
		for(int i=0;i< followers.length;i++)
		{
			System.out.println("Followers n. " + i + " user: " + followers[i].Username);
		}
	}
	
	@Test public void testFollowing()
	{
		assertNotNull(Controller.getProxy().GetFollowings(Controller.getCurrentUser().Username, "pamela2781983")); 
		WUser[] following = Controller.getProxy().GetFollowings(Controller.getCurrentUser().Username, "pamela2781983"); 
		
		System.out.println("Number of following "+ following.length); 
		
		for(int i=0;i< following.length;i++)
		{
			System.out.println("Followers n. " + i + " user: " + following[i].Username);
		}
	}
}
