package user.profile;

import static org.junit.Assert.*;

import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;
import it.uniba.di.socialcdeforeclipse.shared.library.WService;
import it.uniba.di.socialcdeforeclipse.shared.library.WUser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UnitTestProfile {
	/**
	 * Unit test for User story number 18.
	 * 
	 * Action considered: View profile of current user
	 * 
	 * Equivalence classes considered: 
	 * 1.Profile of user that can active one or more services
	 * 2.Profile of user that can't active services.  
	 * */
	Document document; 
	HashMap<String, Object> data; 
	 
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

		
	}

	@After
	public void tearDown() throws Exception {
		
		
	}

	@Test
	public void testCase1() {
	 
		WService[] services = Controller.getProxy().GetServices(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		assertTrue(services.length > 0); 
	}
	
	@Test
	public void testCase2() {
	 
		WService[] services = Controller.getProxy().GetServices(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword());
		assertTrue(services.length == 0); 
		
	}

}
