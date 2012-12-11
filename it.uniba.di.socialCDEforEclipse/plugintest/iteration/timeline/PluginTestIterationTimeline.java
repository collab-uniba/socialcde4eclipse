package iteration.timeline;

import it.uniba.di.socialcdeforeclipse.action.ActionLoginPanel;
import it.uniba.di.socialcdeforeclipse.controller.Controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class PluginTestIterationTimeline extends TestCase {

	/**
	 * Unit test for User story number 39.
	 * 
	 * 
	 * Equivalence classes considered: 1.Current user have static friend.
	 * 2.Current user have not static friend. 3.Current user can send a post.
	 * 4.Current user can't send a post. Note: By default, current user have
	 * static friend.
	 * 
	 * */

	Document document;
	HashMap<String, Object> dati;

	@Before
	public void setUp() {
		SAXBuilder builder = new SAXBuilder();
		try {
			document = builder.build(new File("./testData.xml")
					.getCanonicalPath());
		} catch (JDOMException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			PlatformUI
					.getWorkbench()
					.getActiveWorkbenchWindow()
					.getActivePage()
					.showView(
							"it.uniba.di.socialcdeforeclipse.views.SocialCDEview");
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testCase1() {
		assertNotNull(Controller.getLoginPanel());
		dati = Controller.getLoginPanel().getData();
		dati.put("ID_action", "btnLogin");
		dati.put("Event_type", SWT.Selection);
		((Text) dati.get("txtProxyHost")).setText(document.getRootElement()
				.getChild("CorrectData").getChild("Proxy").getText());
		((Text) dati.get("txtUsername")).setText(document.getRootElement()
				.getChild("CorrectData").getChild("Username").getText());
		((Text) dati.get("txtPassword")).setText(document.getRootElement()
				.getChild("CorrectData").getChild("Password").getText());
		new ActionLoginPanel(dati);
		assertNotNull(Controller.getHomeWindow());
		Controller.selectDynamicWindow(5);
		assertNotNull(Controller.getInteractionTimelineWindow());
		assertNull(Controller.getHomeWindow());
		dati = Controller.getInteractionTimelineWindow().getData();

		assertTrue(((Composite) dati.get("userPostMaster")).getChildren().length > 0);

	}

	@Test
	public void testCase2() {
		assertNotNull(Controller.getLoginPanel());
		dati = Controller.getLoginPanel().getData();
		dati.put("ID_action", "btnLogin");
		dati.put("Event_type", SWT.Selection);
		((Text) dati.get("txtProxyHost")).setText(document.getRootElement()
				.getChild("CorrectData").getChild("Proxy").getText());
		((Text) dati.get("txtUsername")).setText(document.getRootElement()
				.getChild("CorrectData").getChild("Username").getText());
		((Text) dati.get("txtPassword")).setText(document.getRootElement()
				.getChild("CorrectData").getChild("Password").getText());
		new ActionLoginPanel(dati);
		assertNotNull(Controller.getHomeWindow());
		Controller.setCurrentUserPassword("try1");
		Controller.selectDynamicWindow(5);
		assertNotNull(Controller.getInteractionTimelineWindow());
		assertNull(Controller.getHomeWindow());
		dati = Controller.getInteractionTimelineWindow().getData();
		assertTrue(((Composite) dati.get("userPostMaster")).getChildren().length == 1);
		assertEquals("There are no older post available.",
				((Label) ((Composite) dati.get("otherPostWarning"))
						.getChildren()[0]).getText());

	}

	@Test
	public void testCase3() {
		assertNotNull(Controller.getLoginPanel());
		dati = Controller.getLoginPanel().getData();
		dati.put("ID_action", "btnLogin");
		dati.put("Event_type", SWT.Selection);
		((Text) dati.get("txtProxyHost")).setText(document.getRootElement()
				.getChild("CorrectData").getChild("Proxy").getText());
		((Text) dati.get("txtUsername")).setText(document.getRootElement()
				.getChild("CorrectData").getChild("Username").getText());
		((Text) dati.get("txtPassword")).setText(document.getRootElement()
				.getChild("CorrectData").getChild("Password").getText());
		new ActionLoginPanel(dati);
		assertNotNull(Controller.getHomeWindow());
		Controller.selectDynamicWindow(5);
		assertNotNull(Controller.getInteractionTimelineWindow());
		assertNull(Controller.getHomeWindow());
		dati = Controller.getInteractionTimelineWindow().getData();
		String message = ((Text) dati.get("textMessage")).getText();
		message = "Message of pluginTest";
		if (!Controller.getProxy().Post(Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword(), message)) {
			fail("Post not send");
		}

	}

	@Test
	public void testCase4() {
		assertNotNull(Controller.getLoginPanel());
		dati = Controller.getLoginPanel().getData();
		dati.put("ID_action", "btnLogin");
		dati.put("Event_type", SWT.Selection);
		((Text) dati.get("txtProxyHost")).setText(document.getRootElement()
				.getChild("CorrectData").getChild("Proxy").getText());
		((Text) dati.get("txtUsername")).setText(document.getRootElement()
				.getChild("CorrectData").getChild("Username").getText());
		((Text) dati.get("txtPassword")).setText(document.getRootElement()
				.getChild("CorrectData").getChild("Password").getText());
		new ActionLoginPanel(dati);
		assertNotNull(Controller.getHomeWindow());
		Controller.setCurrentUserPassword("try1");
		Controller.selectDynamicWindow(5);
		assertNotNull(Controller.getInteractionTimelineWindow());
		assertNull(Controller.getHomeWindow());
		dati = Controller.getInteractionTimelineWindow().getData();
		assertTrue(((Composite) dati.get("userPostMaster")).getChildren().length == 1);
		Controller.setCurrentUserPassword("try1");
		String message = ((Text) dati.get("textMessage")).getText();
		message = "Message of pluginTest";
		if (Controller.getProxy().Post(Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword(), message)) {
			fail("Post not send");
		}

	}

	@After
	public void tearDown() throws Exception {
		PlatformUI
				.getWorkbench()
				.getActiveWorkbenchWindow()
				.getActivePage()
				.hideView(
						PlatformUI
								.getWorkbench()
								.getActiveWorkbenchWindow()
								.getActivePage()
								.findView(
										"it.uniba.di.socialcdeforeclipse.views.SocialCDEview"));
	}

}
