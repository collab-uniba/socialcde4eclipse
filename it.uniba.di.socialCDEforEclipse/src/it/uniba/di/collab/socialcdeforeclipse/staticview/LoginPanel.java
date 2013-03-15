/**
 * This class define the Login form.
 *
 * @version 1.00 13/09/2012
 * @author Fauzzi Floriano
 */

package it.uniba.di.collab.socialcdeforeclipse.staticview;

import it.uniba.di.collab.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.object.GeneralButton;
import it.uniba.di.collab.socialcdeforeclipse.object.Panel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class LoginPanel implements Panel {

	private Label labelAlert;
	private Label labelHidden;
	private Label labelLogin;
	private Label labelProxyHost;
	private Text txtProxyHost;
	private Label labelUsername;
	private Text txtUsername;
	private Label labelPassword;
	private Text txtPassword;
	private GeneralButton btnLogin;
	private Button chkAutologin;
	private Button chkSavePassword;
	private Label labelImageHostOk;
	private Label labelImageHostNo;
	private Label labelImageUsernameOk;
	private Label labelImageUsernameNo;
	private Label labelImagePasswordOk;
	private Label labelImagePasswordNo;
	private Label labelHiddenProxyHost;
	private Label labelHiddenUsername;
	private Label labelHiddenPassword;
	private ArrayList<Control> controlli;
	private Link labelRegistration;

	/*private final InputStream PATH_ICON_OK = this.getClass().getClassLoader()
			.getResourceAsStream("images/yes_icon.png");*/
	/*private final InputStream PATH_ICON_ERROR = this.getClass()
			.getClassLoader().getResourceAsStream("images/no_icon.png");*/
//	private final InputStream PATH_WALLPAPER = this.getClass().getClassLoader()
//			.getResourceAsStream("images/Wallpaper.png");

	private static Listener azioni;

	

	public Image getImageStream(InputStream stream) {
		return new Image(Controller.getWindow().getDisplay(), stream);

	}

	@Override
	public void inizialize(Composite panel) {
		// TODO Auto-generated method stub

		for (int i = 0; i < panel.getChildren().length; i++) {
			panel.getChildren()[i].dispose();
		}

		panel.setBackgroundMode(SWT.INHERIT_DEFAULT);

		azioni = new ActionGeneral();
		controlli = new ArrayList<Control>();
		GridLayout layout = new GridLayout(4, false);
		panel.setLayout(layout);
		

		labelLogin = new Label(panel, SWT.FILL);
		labelLogin.setText("Login");
		labelLogin.setVisible(true);
		labelLogin.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 15, SWT.BOLD));
		GridData gridData = new GridData();
		gridData.horizontalSpan = 4;
		labelLogin.setLayoutData(gridData);
		controlli.add(labelLogin);

		labelAlert = new Label(panel, SWT.NONE);
		labelAlert.setText("");
		labelAlert.setVisible(false);
		gridData = new GridData();
		gridData.horizontalSpan = 4;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = gridData.FILL;
		labelAlert.setLayoutData(gridData);
		labelAlert.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 10, SWT.BOLD));
		labelAlert.setForeground(new Color(Controller.getWindow().getDisplay(),
				Controller.getWindow().getDisplay()
						.getSystemColor(SWT.COLOR_RED).getRGB()));
		controlli.add(labelAlert);

		labelProxyHost = new Label(panel, SWT.LEFT);
		labelProxyHost.setText("Proxy server host");
		labelProxyHost.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 10, SWT.NONE));
		controlli.add(labelProxyHost);

		txtProxyHost = new Text(panel, SWT.BORDER | SWT.WRAP);
		txtProxyHost.setData("ID_action", "txtProxyHost");
		gridData = new GridData();
		gridData.widthHint = 180; 
		//gridData.grabExcessHorizontalSpace = true; 
		txtProxyHost.setLayoutData(gridData);
		txtProxyHost.setText(Controller.getPreferences("proxyHost").toString());

		labelHiddenProxyHost = new Label(panel, SWT.None);
		labelHiddenProxyHost.setText("");
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		labelHiddenProxyHost.setLayoutData(gridData);
		
		controlli.add(labelHiddenProxyHost);
		controlli.add(txtProxyHost);

		labelImageHostOk = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.widthHint = 32;
		gridData.exclude = true; 
		gridData.horizontalSpan = 2;
		labelImageHostOk.setLayoutData(gridData);
		labelImageHostOk.setImage(new Image(Display.getCurrent(),getImageStream(this.getClass().getClassLoader()
				.getResourceAsStream("images/yes_icon.png")) ,SWT.IMAGE_COPY)); 
		controlli.add(labelImageHostOk);
		
		labelImageHostNo = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.widthHint = 32;
		gridData.horizontalSpan = 2;
		gridData.exclude = true; 
		labelImageHostNo.setLayoutData(gridData);
		labelImageHostNo.setImage(new Image(Display.getCurrent(),getImageStream(this.getClass()
				.getClassLoader().getResourceAsStream("images/no_icon.png")) ,SWT.IMAGE_COPY));
		controlli.add(labelImageHostNo);


		labelUsername = new Label(panel, SWT.LEFT);
		labelUsername.setText("Username");
		labelUsername.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 10, SWT.NONE));
		controlli.add(labelUsername);

		txtUsername = new Text(panel, SWT.BORDER | SWT.WRAP);
		gridData = new GridData();
		gridData.widthHint = 180; 
		//gridData.grabExcessHorizontalSpace = true;
		//gridData.horizontalSpan = 3; 
		txtUsername.setLayoutData(gridData);
		txtUsername.setData("ID_action", "txtUsername");
		txtUsername.setText(Controller.getPreferences("username"));
		controlli.add(txtUsername);
		
		labelHiddenUsername = new Label(panel, SWT.None);
		labelHiddenUsername.setText("");
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		labelHiddenUsername.setLayoutData(gridData);
		
		controlli.add(labelHiddenUsername);
		controlli.add(labelHiddenProxyHost);

		labelImageUsernameOk = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.widthHint = 32;
		gridData.horizontalSpan = 2;
		gridData.exclude = true; 
		labelImageUsernameOk.setLayoutData(gridData);
		labelImageUsernameOk.setImage(new Image(Display.getCurrent(),getImageStream(this.getClass().getClassLoader()
				.getResourceAsStream("images/yes_icon.png")) ,SWT.IMAGE_COPY));
		controlli.add(labelImageUsernameOk);
		
		labelImageUsernameNo = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.widthHint = 32;
		gridData.horizontalSpan = 2;
		gridData.exclude = true; 
		labelImageUsernameNo.setLayoutData(gridData);
		labelImageUsernameNo.setImage(new Image(Display.getCurrent(),getImageStream(this.getClass()
				.getClassLoader().getResourceAsStream("images/no_icon.png")) ,SWT.IMAGE_COPY));
		controlli.add(labelImageUsernameNo);

		
		
		labelPassword = new Label(panel, SWT.LEFT);
		labelPassword.setText("Password");
		labelPassword.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 10, SWT.NONE));
		controlli.add(labelPassword);

		txtPassword = new Text(panel, SWT.PASSWORD | SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint = 180; 
		//gridData.horizontalSpan = 2; 
		txtPassword.setLayoutData(gridData);
		txtPassword.setData("ID_action", "txtPassword");
		controlli.add(txtPassword);
		
		labelHiddenPassword = new Label(panel, SWT.None);
		labelHiddenPassword.setText("");
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		labelHiddenPassword.setLayoutData(gridData);

		controlli.add(labelHiddenPassword);
		
		labelImagePasswordOk = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.widthHint = 32;
		gridData.horizontalSpan = 2;
		gridData.exclude = true; 
		labelImagePasswordOk.setLayoutData(gridData);
		labelImagePasswordOk.setImage(new Image(Display.getCurrent(),getImageStream(this.getClass().getClassLoader()
				.getResourceAsStream("images/yes_icon.png")) ,SWT.IMAGE_COPY));
		controlli.add(labelImagePasswordOk);
		
		labelImagePasswordNo = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.widthHint = 32;
		gridData.exclude = true; 
		gridData.horizontalSpan = 2;
		labelImagePasswordNo.setLayoutData(gridData);
		labelImagePasswordNo.setImage(new Image(Display.getCurrent(),getImageStream(this.getClass()
				.getClassLoader().getResourceAsStream("images/no_icon.png")) ,SWT.IMAGE_COPY));
		controlli.add(labelImagePasswordNo);

		labelHidden = new Label(panel, SWT.NONE);
		labelHidden.setText("");
		labelHidden.setVisible(false);
		gridData = new GridData();
		gridData.horizontalSpan = 4;
		labelHidden.setLayoutData(gridData);
		controlli.add(labelHidden);

		btnLogin = new GeneralButton(panel, SWT.None);
		btnLogin.setText("Login");
		btnLogin.setWidth(80);
		btnLogin.setHeight(30);
		btnLogin.setxCoordinate(5);
		btnLogin.setyCoordinate(152);
		btnLogin.setDefaultColors(new Color(panel.getDisplay(), 179, 180, 168),
				new Color(panel.getDisplay(), 179, 180, 168), null, null);
		btnLogin.setClickedColors(new Color(panel.getDisplay(), 179, 180, 168),
				new Color(panel.getDisplay(), 179, 180, 168), null, null);
		btnLogin.setHoverColors(new Color(panel.getDisplay(), 179, 180, 168),
				new Color(panel.getDisplay(), 179, 180, 168), null, null);
		btnLogin.setSelectedColors(
				new Color(panel.getDisplay(), 179, 180, 168),
				new Color(panel.getDisplay(), 179, 180, 168), null, null);
		btnLogin.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 12, SWT.BOLD));
		btnLogin.setData("ID_action", "btnLogin");
		controlli.add(btnLogin);

		labelHidden = new Label(panel, SWT.NONE);
		labelHidden.setText("");
		labelHidden.setVisible(false);
		gridData = new GridData();
		gridData.horizontalSpan = 4;
		labelHidden.setLayoutData(gridData);
		controlli.add(labelHidden);

		chkAutologin = new Button(panel, SWT.CHECK);
		chkAutologin.setText("Auto login");
		controlli.add(chkAutologin);

		chkSavePassword = new Button(panel, SWT.CHECK);
		chkSavePassword.setText("Save password");
		controlli.add(chkSavePassword);

		chkAutologin.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			chkSavePassword.setSelection(true); 	
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		}); 
		
		if (Controller.getPreferences("autoLogin").equals("true")) {
			chkAutologin.setSelection(true);
		}

		if (!Controller.getPreferences("password").equals("")) {
			txtPassword.setText(Controller.getPreferences("password"));
			chkSavePassword.setSelection(true);
		}

		for (int i = 0; i < 5; i++) {
			labelHidden = new Label(panel, SWT.NONE);
			labelHidden.setText("");
			labelHidden.setVisible(false);
			gridData = new GridData();
			gridData.horizontalSpan = 4;
			labelHidden.setLayoutData(gridData);
			controlli.add(labelHidden);
		}

		labelRegistration = new Link(panel, SWT.NONE);
		labelRegistration
				.setText("<a>I have no account, I want to register</a>");
		labelRegistration.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 10, SWT.UNDERLINE_LINK));
		gridData = new GridData();
		gridData.horizontalSpan = 4;
		gridData.horizontalIndent = 55;
		labelRegistration.setLayoutData(gridData);

		labelRegistration.setData("ID_action", "labelRegistration");
		labelRegistration.setData("labelAlert", labelAlert);
		labelRegistration.setData("txtProxyHost", txtProxyHost);
		labelRegistration.setData("labelImageHostOk", labelImageHostOk);
		labelRegistration.setData("labelImageHostNo", labelImageHostNo);
		labelRegistration.setData("txtUsername", txtUsername);
		labelRegistration.setData("labelImageUsernameOk", labelImageUsernameOk);
		labelRegistration.setData("labelImageUsernameNo", labelImageUsernameNo);
		labelRegistration.setData("txtPassword", txtPassword);
		labelRegistration.setData("labelImagePasswordOk", labelImagePasswordOk);
		labelRegistration.setData("labelImagePasswordNo", labelImagePasswordNo);
		labelRegistration.setData("chkAutologin", chkAutologin);
		labelRegistration.setData("chkSavePassword", chkSavePassword);

		controlli.add(labelRegistration);

		panel.setTabList(new Control[] { txtProxyHost, txtUsername,
				txtPassword, btnLogin, chkAutologin, chkSavePassword });

		txtProxyHost.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(TraverseEvent e) {
				// TODO Auto-generated method stub
				if (e.detail == SWT.TRAVERSE_TAB_NEXT
						|| e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
					e.doit = true;
			}
		});

		txtUsername.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(TraverseEvent e) {
				// TODO Auto-generated method stub
				if (e.detail == SWT.TRAVERSE_TAB_NEXT
						|| e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
					e.doit = true;
			}
		});

		txtPassword.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(TraverseEvent e) {
				// TODO Auto-generated method stub
				if (e.detail == SWT.TRAVERSE_TAB_NEXT
						|| e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
					e.doit = true;
			}
		});

		btnLogin.addListener(SWT.Selection, azioni);
		labelRegistration.addListener(SWT.Selection, azioni);
		
		txtProxyHost.addListener(SWT.FocusOut, azioni);
		txtUsername.addListener(SWT.FocusOut, azioni);
		
		panel.layout();
	}

	@Override
	public void dispose(Composite panel) {
		// TODO Auto-generated method stub

		for (int i = 0; i < controlli.size(); i++) {
			final int j = i;
			Display.getCurrent().syncExec(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					controlli.get(j).dispose();
				}
			});

		}

	

	}

	@Override
	public HashMap<String, Object> getData() {
		HashMap<String, Object> uiData = new HashMap<String, Object>();
		uiData.put("labelAlert", labelAlert);
		uiData.put("txtProxyHost", txtProxyHost);
		uiData.put("labelImageHostOk", labelImageHostOk);
		uiData.put("labelImageHostNo", labelImageHostNo);
		uiData.put("txtUsername", txtUsername);
		uiData.put("labelImageUsernameOk", labelImageUsernameOk);
		uiData.put("labelImageUsernameNo", labelImageUsernameNo);
		uiData.put("txtPassword", txtPassword);
		uiData.put("labelImagePasswordOk", labelImagePasswordOk);
		uiData.put("labelImagePasswordNo", labelImagePasswordNo);
		uiData.put("chkAutologin", chkAutologin);
		uiData.put("chkSavePassword", chkSavePassword);
		uiData.put("btnLogin", btnLogin);
		uiData.put("labelHiddenProxyHost", labelHiddenProxyHost);
		uiData.put("labelHiddenUsername", labelHiddenUsername);
		uiData.put("labelHiddenPassword", labelHiddenPassword);

		return uiData;
	}

	

}
