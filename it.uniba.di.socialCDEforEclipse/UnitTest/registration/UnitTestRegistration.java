/**
 * 
 */
package registration;

import it.uniba.di.socialcdeforeclipse.action.ActionRegistrationPanel;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;

import java.util.HashMap;


import org.eclipse.swt.widgets.Text;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author Flo
 *
 */
public class UnitTestRegistration extends TestCase {
	/**
	 * Unit test for User story number 26.
	 * 
	 * Field considered: Registration button
	 * 
	 * Equivalence classes considered:
	 * 1.All field are empty
	 * 2.Same field are empty
	 * 3.All field are not empty but with wrong information
	 * 4.All field contains correct information
	 * 
	 * */
	
	
	@Before
	public void setUp()
	{
		//For testCase 4, I suppose that an invitation email was send to o3401944@rtrtr.com and 
		// an username like 'John' is available
		ProxyWrapper pw = new ProxyWrapper(); 
		pw.setHost("http://localhost:8081"); 
		Controller.setProxy(pw); 
		assertTrue(pw.IsAvailable("John")); 
	
	}
	
	
	@Test
	public void testCase1() {
		
		HashMap<String, String> dataExtracted = new HashMap<String,String>(); 
		dataExtracted.put("Username", "");
		dataExtracted.put("Password1", "");
		dataExtracted.put("Password2", "");
		dataExtracted.put("InvitationCode", ""); 
		dataExtracted.put("Email", "");
		dataExtracted.put("ProxyHost", "");
		
		ActionRegistrationPanel action = new ActionRegistrationPanel();
		
		int res = action.actionRegister(dataExtracted);
		
		assertEquals(-3 , res); 	
	}
	
	@Test
	public void testCase2() {
		
		HashMap<String, String> dataExtracted = new HashMap<String,String>(); 
		dataExtracted.put("Username", "Giacomo35");
		dataExtracted.put("Password1", "");
		dataExtracted.put("Password2", "");
		dataExtracted.put("InvitationCode", ""); 
		dataExtracted.put("Email", "prova.123@ciao.com");
		dataExtracted.put("ProxyHost", "http:\\localhost:8081");
		
		ActionRegistrationPanel action = new ActionRegistrationPanel();
		
		int res = action.actionRegister(dataExtracted);
		
		assertEquals(-3 , res); 	
	}
	
	@Test
	public void testCase3() {
		
		HashMap<String, String> dataExtracted = new HashMap<String,String>(); 
		dataExtracted.put("Username", "Giacomo35");
		dataExtracted.put("Password1", "antonio");
		dataExtracted.put("Password2", "antonio");
		dataExtracted.put("InvitationCode", "hsdjgdshafshg"); 
		dataExtracted.put("Email", "prova.123@ciao.com");
		dataExtracted.put("ProxyHost", "http:\\localhost:8081");
		ActionRegistrationPanel action = new ActionRegistrationPanel();
		
		int res = action.actionRegister(dataExtracted);
		
		assertFalse(res == 0); 	
	}
	
	@Test
	public void testCase4() {
		
		HashMap<String, String> dataExtracted = new HashMap<String,String>(); 
		dataExtracted.put("Username", "John");
		dataExtracted.put("Password1", "john12");
		dataExtracted.put("Password2", "john12");
		dataExtracted.put("InvitationCode", "zS9^y[6JBk"); 
		dataExtracted.put("Email", "o3401944@rtrtr.com");
		dataExtracted.put("ProxyHost", "http:\\localhost:8081");
		ActionRegistrationPanel action = new ActionRegistrationPanel();
		
		int res = action.actionRegister(dataExtracted);
		
		assertEquals(0 ,res); 	
	}
}
