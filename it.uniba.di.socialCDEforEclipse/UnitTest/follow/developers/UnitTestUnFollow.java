package follow.developers;

import static org.junit.Assert.*;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;
import it.uniba.di.socialcdeforeclipse.shared.library.WUser;

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UnitTestUnFollow {

	/**
	 * Unit test for User story number 11.
	 * 
	 * Action considered: Unfollow a developer
	 * 
	 * Equivalence classes considered: 
	 * 1.Unfollow a developer identified by correct id
	 * 2.Unfollow a developer identified by wrong id (-1) 
	 * 
	 * Note: For test the equivalence class number 1, the system select a random developer 
	 * between followings developers. At the end of test the user selected will be follow. 
	 * */
	
	Document document; 
	 int positionUser; 
	 WUser[] users; 
	 
	@Before
	public void setUp() throws Exception {
		
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
		
		Controller.setProxy(new ProxyWrapper()); 
		Controller.getProxy().setHost(document.getRootElement().getChild("CorrectData").getChild("Proxy").getText());
		Controller.setCurrentUser(Controller.getProxy().GetUser(document.getRootElement().getChild("CorrectData").getChild("Username").getText(), document.getRootElement().getChild("CorrectData").getChild("Password").getText()));
		Controller.setCurrentUserPassword(document.getRootElement().getChild("CorrectData").getChild("Password").getText()); 
		users = Controller.getProxy().GetFollowings(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword());
		assertTrue(users.length > 0); 
	}

	@After
	public void tearDown() throws Exception {
		
		if(positionUser != -1)
		{
			assertTrue(Controller.getProxy().Follow(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), users[positionUser].Id));
		}
	}

	@Test
	public void testCase1() {
	 
	 positionUser = (int) (Math.random() * (users.length -1)); 
	 
	 assertTrue(Controller.getProxy().UnFollow(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), users[positionUser].Id)); 
	 
	}
	
	@Test
	public void testCase2() {
	 
	 positionUser = -1; 
	 
	 assertFalse(Controller.getProxy().UnFollow(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(),-1)); 
	 
	}

}
