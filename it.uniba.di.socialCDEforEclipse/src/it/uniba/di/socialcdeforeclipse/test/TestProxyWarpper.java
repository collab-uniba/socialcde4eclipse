package it.uniba.di.socialcdeforeclipse.test;


import java.net.URI;

import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;
import it.uniba.di.socialcdeforeclipse.shared.library.*;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class TestProxyWarpper extends TestCase {

	
	
	@Before public void setUp() {
		
		Controller.setProxy(new ProxyWrapper()); 
		Controller.getProxy().setHost("http://apat.di.uniba.it:8081"); 
		Controller.setCurrentUserPassword("gualtiero");
		WUser utente = Controller.getProxy().GetUser("Floriano",Controller.getCurrentUserPassword() ); 
		Controller.setCurrentUser(utente); 
		System.out.println("Before statement"); 
		
	}
	
	@Test public void testGetUser()
	{
		assertNotNull(Controller.getCurrentUser()); 
		assertNotNull(Controller.getCurrentUserPassword());
		assertNotNull(Controller.getProxy().GetUser(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword())); 
	}
	
	@Test public void testGetColleagueProfile()
	{
		//Test with the user Antonio Loiacono that have id 3
		assertNotNull(Controller.getCurrentUser()); 
		assertNotNull(Controller.getCurrentUserPassword());
		assertNotNull(Controller.getProxy().GetColleagueProfile(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), 3)); 
	}
	
	@Test public void testGetHomeTimeline()
	{
		assertNotNull(Controller.getCurrentUser()); 
		assertNotNull(Controller.getCurrentUserPassword()); 
		assertNotNull(Controller.getProxy().GetHomeTimeline(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), 0, 0));  
	
	}
	
	@Test public void testGetUserTimeline()
	{
		//Test with the user Antonio Loiacono

		assertNotNull(Controller.getCurrentUser()); 
		assertNotNull(Controller.getCurrentUserPassword());
		WUser user = Controller.getProxy().GetColleagueProfile(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), 3); 
		assertNotNull(user);
		assertNotNull(Controller.getProxy().GetUserTimeline(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), user.Username, 0, 0));  		
	}
	
	@Test public void testGetIterationTimeline()
	{
		assertNotNull(Controller.getCurrentUser()); 
		assertNotNull(Controller.getCurrentUserPassword());
		assertNotNull(Controller.getProxy().GetIterationTimeline(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword())); 
	}
	
	@Test public void testGetFollowings()
	{
		assertNotNull(Controller.getCurrentUser()); 
		assertNotNull(Controller.getCurrentUserPassword());
		WUser[] users = Controller.getProxy().GetFollowings(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		
		for(int i=0;i< users.length;i++)
		{
			assertNotNull(users[i]); 
		}
	}
	
	@Test public void testGetOAuthData()
	{
		assertNotNull(Controller.getCurrentUser()); 
		assertNotNull(Controller.getCurrentUserPassword());
		WService[] services = Controller.getProxy().GetServices(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		
		assertNotNull(services); 
		
		for (int i = 0; i < services.length; i++) {
			if(services[i].RequireOAuth)
			{
				 assertNotNull(Controller.getProxy().GetOAuthData(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), services[i].Id));

			}
			else
			{
			   WOAuthData data = Controller.getProxy().GetOAuthData(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), services[i].Id);
			  assertNull(data.getAccessSecret());
			  assertNull(data.getAccessToken()); 
			  assertNull(data.getAuthorizationLink()); 
			  
			}
		}
		
		
	}
	
	@Test public void testIsAvailable()
	{
		assertNotNull(Controller.getCurrentUser()); 
		assertFalse(Controller.getProxy().IsAvailable(Controller.getCurrentUser().Username)); 
	}
	
	@Test public void  testIsWebServiceRunning(){
		assertTrue(Controller.getProxy().IsWebServiceRunning()); 
	}	
	
	
	@Test public void testAuthorize()
	{
		assertNotNull(Controller.getCurrentUser()); 
		assertNotNull(Controller.getCurrentUserPassword());
		assertFalse(Controller.getProxy().Authorize(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), 5, "71991", "795c5b17-8a84-4c98-87fd-a70ad253d198", "86f8c4ef-8e77-4e78-b0d3-ee980eed175f"));
	}
	
	@Test public void testGetServices()
	{
		assertNotNull(Controller.getCurrentUser()); 
		assertNotNull(Controller.getCurrentUserPassword());
		assertNotNull(Controller.getProxy().GetServices(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword())); 	
	}
	
	@Test public void testGetFollowers()
	{
		assertNotNull(Controller.getCurrentUser()); 
		assertNotNull(Controller.getCurrentUserPassword()); 
		assertNotNull(Controller.getProxy().GetFollowers(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()));  
	}
	
	@Test public void testGetSuggestedFriends()
	{
		assertNotNull(Controller.getCurrentUser()); 
		assertNotNull(Controller.getCurrentUserPassword()); 
		assertNotNull(Controller.getProxy().GetSuggestedFriends(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword())); 
	}
	
	@Test public void testGetSkills()
	{
		assertNotNull(Controller.getCurrentUser()); 
		assertNotNull(Controller.getCurrentUserPassword()); 
		assertNotNull(Controller.getProxy().GetSkills(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), Controller.getCurrentUser().Username)); 
	}
	
	@Test public void testGetHiddenUsers()
	{
		assertNotNull(Controller.getCurrentUser()); 
		assertNotNull(Controller.getCurrentUserPassword()); 
		assertNotNull(Controller.getProxy().GetHiddenUsers(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword())); 	
	}
	
	@Test public void testGetUserHideSettings()
	{
		//Test with the user Antonio Loiacono
		assertNotNull(Controller.getCurrentUser()); 
		assertNotNull(Controller.getCurrentUserPassword());
		WUser user = Controller.getProxy().GetColleagueProfile(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), 3); 
		assertNotNull(user);
		assertNotNull(Controller.getProxy().GetUserHideSettings(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), user.Id));
	}
	
	@Test public void testGetChosenFeatures()
	{
		assertNotNull(Controller.getCurrentUser()); 
		assertNotNull(Controller.getCurrentUserPassword());
		WService[] services = Controller.getProxy().GetServices(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		assertTrue(services.length > 0); 
		
		for (int i = 0; i < services.length; i++) {
			if(services[i].Registered)
			{
				assertNotNull(Controller.getProxy().GetChosenFeatures(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), services[i].Id)); 
			}
		}
	}
	
	@Test public void testGetAvailableAvatars()
	{
		assertNotNull(Controller.getCurrentUser()); 
		assertNotNull(Controller.getCurrentUserPassword());
		URI[] avatars = Controller.getProxy().GetAvailableAvatars(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword());
		assertTrue(avatars.length > 0); 
		
	}
	
}
