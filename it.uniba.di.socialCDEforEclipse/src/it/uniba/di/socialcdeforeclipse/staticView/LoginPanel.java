/**
* This class define the Login form.
*
* @version 1.00 13/09/2012
* @author Fauzzi Floriano
*/

package it.uniba.di.socialcdeforeclipse.staticView;

import it.uniba.di.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.views.GeneralButton;
import it.uniba.di.socialcdeforeclipse.views.Panel;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class LoginPanel implements Panel{

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
	private Label labelImageHost; 
	private Label labelImageUsername; 
	private Label labelImagePassword; 
	private ArrayList<Control> controlli; 
	private Link labelRegistration; 
	
	
	private final InputStream PATH_ICON_OK = this.getClass().getClassLoader().getResourceAsStream("images/yes_icon.png"); 
	private final InputStream PATH_ICON_ERROR = this.getClass().getClassLoader().getResourceAsStream("images/no_icon.png");
	private final InputStream PATH_WALLPAPER = this.getClass().getClassLoader().getResourceAsStream("images/Wallpaper.png");  

	private static Listener azioni; 
	
	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0,image.getBounds().width, image.getBounds().height,	0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
		}
	
	
	public Image getImageStream(InputStream stream)
	{ 	
		return  new Image(Controller.getWindow().getDisplay(),stream);
		
	}
	
	
	@Override
	public void inizialize(Composite panel) {
		// TODO Auto-generated method stub
		
		for(int i=0;i< panel.getChildren().length;i++)
		{
			panel.getChildren()[i].dispose(); 
		}
		
		panel.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		azioni = new ActionGeneral(); 
		controlli = new ArrayList<Control>();
		GridLayout layout = new GridLayout(3, false);
		panel.setLayout(layout);
		//panel.setData("ID_action","loginPanel"); 
		//panel.setBackgroundImage(resize(getImageStream(PATH_WALLPAPER),100,100)); 
		System.out.println("larghezza " + Controller.getWindowWidth() + " altezza " + Controller.getWindowHeight() );  
		
		labelLogin = new Label(panel, SWT.FILL);
		labelLogin.setText("Login" );
		labelLogin.setVisible(true); 
		labelLogin.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 15, SWT.BOLD ));
		GridData gridData = new GridData();
		gridData.horizontalSpan = 3; 
		labelLogin.setLayoutData(gridData); 
		controlli.add(labelLogin); 
		
		labelAlert = new Label(panel, SWT.NONE);
		labelAlert.setText("");
		labelAlert.setVisible(false);
		gridData = new GridData();
		gridData.horizontalSpan = 3; 
		gridData.grabExcessHorizontalSpace = true; 
		gridData.horizontalAlignment = gridData.FILL; 
		labelAlert.setLayoutData(gridData);
		labelAlert.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.BOLD ));  
		labelAlert.setForeground(new Color(Controller.getWindow().getDisplay(),Controller.getWindow().getDisplay().getSystemColor(SWT.COLOR_RED).getRGB()));
		controlli.add(labelAlert);
		
		
		labelProxyHost = new Label(panel, SWT.LEFT); 
		labelProxyHost.setText("Proxy server host"); 
		labelProxyHost.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.NONE ));
		controlli.add(labelProxyHost); 
		
		txtProxyHost = new Text(panel, SWT.BORDER | SWT.WRAP );
		txtProxyHost.setData("ID_action","txtProxyHost");
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		txtProxyHost.setLayoutData(gridData);
		txtProxyHost.setText(Controller.getPreferences("ProxyRoot").toString()); 
		
		controlli.add(txtProxyHost); 
		
		labelImageHost = new Label(panel,SWT.NONE);
	    gridData = new GridData(); 
	    gridData.grabExcessHorizontalSpace = false; 
	    gridData.widthHint = 32; 
	    labelImageHost.setLayoutData(gridData);
	    controlli.add(labelImageHost); 
	    
		labelUsername = new Label(panel, SWT.LEFT); 
		labelUsername.setText("Username"); 
		labelUsername.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.NONE ));
		controlli.add(labelUsername); 
		 
		txtUsername = new Text(panel, SWT.BORDER | SWT.WRAP);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		txtUsername.setLayoutData(gridData);
		txtUsername.setData("ID_action","txtUsername");
		txtUsername.setText(Controller.getPreferences("Username")); 
		controlli.add(txtUsername);
		
		labelImageUsername = new Label(panel,SWT.NONE);
	    gridData = new GridData(); 
	    gridData.grabExcessHorizontalSpace = false; 
	    gridData.widthHint = 32; 
	    labelImageUsername.setLayoutData(gridData);
	    controlli.add(labelImageUsername);
	    
		labelPassword = new Label(panel,SWT.LEFT);
		labelPassword.setText("Password");
		labelPassword.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.NONE ));
		controlli.add(labelPassword); 
		
		txtPassword = new Text(panel, SWT.PASSWORD |SWT.BORDER );
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		txtPassword.setLayoutData(gridData);
		txtPassword.setData("ID_action","txtPassword");
		controlli.add(txtPassword);
		
		labelImagePassword = new Label(panel,SWT.NONE);
	    gridData = new GridData(); 
	    gridData.grabExcessHorizontalSpace = false; 
	    gridData.widthHint = 32; 
	    labelImagePassword.setLayoutData(gridData);
	    controlli.add(labelImagePassword);  
	 
	    labelHidden = new Label(panel, SWT.NONE);
		labelHidden.setText("");
		labelHidden.setVisible(false);
		gridData = new GridData();
		gridData.horizontalSpan = 3; 
		labelHidden.setLayoutData(gridData);
		controlli.add(labelHidden);
	    
		btnLogin = new GeneralButton(panel, SWT.None); 
		btnLogin.setText("Login"); 
		btnLogin.setWidth(80);
		btnLogin.setHeight(30); 
		btnLogin.setxCoordinate(5);
		btnLogin.setyCoordinate(152); 
		btnLogin.setDefaultColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
		btnLogin.setClickedColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
		btnLogin.setHoverColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
		btnLogin.setSelectedColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
		btnLogin.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 12, SWT.BOLD )); 
		btnLogin.setData("ID_action", "btnLogin");
		controlli.add(btnLogin);
		
		labelHidden = new Label(panel, SWT.NONE);
		labelHidden.setText("");
		labelHidden.setVisible(false);
		gridData = new GridData();
		gridData.horizontalSpan = 3; 
		labelHidden.setLayoutData(gridData);
		controlli.add(labelHidden);
		
		chkAutologin = new Button(panel, SWT.CHECK); 
		chkAutologin.setText("Auto login");
		controlli.add(chkAutologin);
		
		chkSavePassword = new Button(panel, SWT.CHECK); 
		chkSavePassword.setText("Save password");
		controlli.add(chkSavePassword);
		
		if(Controller.getPreferences("Autologin").equals("True"))
		{
			chkAutologin.setSelection(true); 
		}
		
		
		if(!Controller.getPreferences("Password").equals(""))
		{
			txtPassword.setText(Controller.getPreferences("Password")); 
			chkSavePassword.setSelection(true); 
		}
		
		for (int i = 0; i < 5; i++) {
			labelHidden = new Label(panel, SWT.NONE);
			labelHidden.setText("");
			labelHidden.setVisible(false);
			gridData = new GridData();
			gridData.horizontalSpan = 3; 
			labelHidden.setLayoutData(gridData);
			controlli.add(labelHidden);
		}
		
		
		
		
		 labelRegistration = new Link(panel, SWT.NONE);
		 labelRegistration.setText("<a>I have no account, I want to register</a>"); 
		 labelRegistration.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.UNDERLINE_LINK)); 
		 gridData = new GridData(); 
		 gridData.horizontalSpan = 3; 
		 gridData.horizontalIndent = 55; 
		 labelRegistration.setLayoutData(gridData); 
		 
		 labelRegistration.setData("ID_action", "labelRegistration");
		 labelRegistration.setData("labelAlert",labelAlert); 
		 labelRegistration.setData("txtProxyHost",txtProxyHost); 
		 labelRegistration.setData("labelImageHost", labelImageHost); 
		 labelRegistration.setData("txtUsername",txtUsername); 
		 labelRegistration.setData("labelImageUsername", labelImageUsername); 
		 labelRegistration.setData("txtPassword", txtPassword); 
		 labelRegistration.setData("labelImagePassword", labelImagePassword); 
		 labelRegistration.setData("chkAutologin",chkAutologin); 
		 labelRegistration.setData("chkSavePassword", chkSavePassword); 
		 
		 controlli.add(labelRegistration);
		 
		
		 panel.setTabList(new Control[] { txtProxyHost, txtUsername, txtPassword, btnLogin , chkAutologin , chkSavePassword });	
		 
		 System.out.println("Controlli " + panel.getChildren().length); 
		
		txtProxyHost.addTraverseListener(new TraverseListener() {
			
			@Override
			public void keyTraversed(TraverseEvent e) {
				// TODO Auto-generated method stub
				if(e.detail == SWT.TRAVERSE_TAB_NEXT ||
						e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
						e.doit = true;	
			}
		});
		
		txtUsername.addTraverseListener(new TraverseListener() {
			
			@Override
			public void keyTraversed(TraverseEvent e) {
				// TODO Auto-generated method stub
				if(e.detail == SWT.TRAVERSE_TAB_NEXT ||
						e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
						e.doit = true;	
			}
		});
		
		txtPassword.addTraverseListener(new TraverseListener() {
			
			@Override
			public void keyTraversed(TraverseEvent e) {
				// TODO Auto-generated method stub
				if(e.detail == SWT.TRAVERSE_TAB_NEXT ||
						e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
						e.doit = true;	
			}
		});
		
		btnLogin.addListener(SWT.Selection, azioni); 
		labelRegistration.addListener(SWT.Selection, azioni); 
		labelRegistration.addListener(SWT.Paint, azioni); 
		txtProxyHost.addListener(SWT.FocusOut, azioni); 
		txtUsername.addListener(SWT.FocusOut, azioni); 
		//panel.addListener(SWT.Resize , azioni); 
		panel.layout();
		}
	
	
	

	@Override
	public void dispose(Composite panel) {
		// TODO Auto-generated method stub
	
		for(int i=0; i < controlli.size();i++)
		{
		System.out.println("Pos "+ i + " controllo " + controlli.get(i));
		 controlli.get(i).dispose(); 
			
		}
		panel.setLayout(null); 
		
	}

	public HashMap<String, Object> getData()
	{
		HashMap<String, Object> uiData = new HashMap<String, Object>();
		uiData.put("labelAlert",labelAlert); 
		uiData.put("txtProxyHost",txtProxyHost); 
		uiData.put("labelImageHost", labelImageHost); 
		uiData.put("txtUsername",txtUsername); 
		uiData.put("labelImageUsername", labelImageUsername); 
		uiData.put("txtPassword", txtPassword); 
		uiData.put("labelImagePassword", labelImagePassword); 
		uiData.put("chkAutologin",chkAutologin); 
		uiData.put("chkSavePassword", chkSavePassword); 
		uiData.put("btnLogin",btnLogin);
		return uiData; 
	}
	
	@Override
	public HashMap<String, String> getInput() {
		// TODO Auto-generated method stub
		HashMap<String, String> Input_data = new HashMap<>();
		Input_data.put("Proxy", txtProxyHost.getText()); 
		Input_data.put("Username", txtUsername.getText()); 
		Input_data.put("Password", txtPassword.getText());  
		return Input_data; 
		
	}
	//Getter and setter methods
	
	
	
	
	public InputStream getPathIconOk() {
		return PATH_ICON_OK;
	}

	public InputStream getPathIconError() {
		return PATH_ICON_ERROR;
	}




}
