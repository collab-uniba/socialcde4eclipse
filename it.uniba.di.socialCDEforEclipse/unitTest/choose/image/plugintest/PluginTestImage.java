package choose.image.plugintest;

import static org.junit.Assert.*;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;

import it.uniba.di.socialcdeforeclipse.action.ActionDynamicUserTimeline;
import it.uniba.di.socialcdeforeclipse.action.ActionHomePanel;
import it.uniba.di.socialcdeforeclipse.action.ActionLoginPanel;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.popup.ChooseAvatar;
import it.uniba.di.socialcdeforeclipse.shared.library.WUser;
import it.uniba.di.socialcdeforeclipse.object.ButtonAvatar;

import org.eclipse.ui.PlatformUI;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class PluginTestImage extends TestCase {

	/**
	 * Unit test for User story number 11.
	 * 
	 * Action considered: Follow a developer
	 * 
	  * Unit test for User story number 29.
	 * 
	 * Action considered: Choose an image
	 * 
	 * Equivalence classes considered: 
	 * 1. Choose an image between the available
	 * 2. No images are available  
	 * */
	
	static HashMap<String, Object> dati; 
	Document document; 
	
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
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview");
		
		 assertNotNull(Controller.getLoginPanel());
		  dati = Controller.getLoginPanel().getData(); 
		  dati.put("ID_action", "btnLogin"); 
		  dati.put("Event_type", SWT.Selection); 
		  ( (Text)  dati.get("txtProxyHost")).setText(document.getRootElement().getChild("CorrectData").getChild("Proxy").getText());
		  ( (Text)  dati.get("txtUsername")).setText(document.getRootElement().getChild("CorrectData").getChild("Username").getText());
		  ( (Text)  dati.get("txtPassword")).setText(document.getRootElement().getChild("CorrectData").getChild("Password").getText());
		  new ActionLoginPanel(dati);
		assertNotNull(Controller.getHomeWindow()); 
		
	}
	
	@Test public void testCase1()
	{
		
		  dati = Controller.getHomeWindow().getData(); 
		  dati.put("ID_action", "labelAvatar"); 
		  dati.put("Event_type",SWT.MouseDown);
		  new ActionHomePanel(dati); 
		  ChooseAvatar avatarWindow = (ChooseAvatar) dati.get("Avatar_Window"); 
		  dati = avatarWindow.getData(); 
		  ArrayList<ButtonAvatar> btnAvatar = new ArrayList<ButtonAvatar>();
		  for(int i=0;i< dati.size();i++)
		  {
			  if(dati.containsKey("Avatar" + i))
			  {
				  btnAvatar.add( (ButtonAvatar) dati.get("Avatar" + i)); 
			  }
		  }
		  
		 int avatar_selected = (int) (Math.random() * (btnAvatar.size() -1));
		 
		 ButtonAvatar btnAvatarSelected = btnAvatar.get(avatar_selected);
		 
		 btnAvatarSelected.notifyListeners(SWT.MouseDown,new Event() ); 
		  
	}
	@Test public void testCase2()
	{
		String psw = Controller.getCurrentUserPassword();
		  dati = Controller.getHomeWindow().getData(); 
		  Controller.setCurrentUserPassword("prova"); 
		  dati.put("ID_action", "labelAvatar"); 
		  dati.put("Event_type",SWT.MouseDown);
		  new ActionHomePanel(dati);
		  ChooseAvatar avatarWindow = (ChooseAvatar) dati.get("Avatar_Window"); 
		  dati = avatarWindow.getData(); 
		  Controller.setCurrentUserPassword(psw); 
		  Button btnBack = (Button) dati.get("btnAvatarBack");
		  assertNotNull( (Label) dati.get("noAvatarMessage"));
		  assertEquals("There are no avatars available.", ( (Label) dati.get("noAvatarMessage")).getText());
		  btnBack.notifyListeners(SWT.Selection,new Event() ); 
	}
	
	@After
	public void tearDown() throws Exception {
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview"));
	}
	
	
	

	
	

}
