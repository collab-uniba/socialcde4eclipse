package it.uniba.di.socialcdeforeclipse.staticView;

import it.uniba.di.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.views.GeneralButton;
import it.uniba.di.socialcdeforeclipse.views.Panel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;



import org.eclipse.swt.SWT;
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

public class RegistrationPanel implements Panel {
 
	private Label labelReg; 
	private Label labelHidden; 
	private Label labelProxyHost; 
	private Text txtProxyHost;  
	private Label labelMail; 
	private Text txtMail; 
	private Label labelInvitationCode; 
	private Text txtInvitationCode; 
	private Label labelUsername;
	private Text txtUsername;
	private Label labelPassword; 
	private Text txtPassword; 
	private Label labelPassword2; 
	private Text txtPassword2;
	private  GeneralButton btnRegister; 
	private Label labelAlert; 
	private Label labelImageHost; 
	private Label labelImageMail;
	private Label labelImageInvitationCode; 
	private Label labelUmageUsername; 
	private Label labelImagePassword;
	private Label labelImagePassword2;
	 
	private Link labelLogin; 
	private ArrayList<Control> controlli;
	private Listener azioni; 
	private final InputStream PATH_ICON_OK = this.getClass().getClassLoader().getResourceAsStream("images/yes_icon.png"); 
	private final InputStream PATH_ICON_ERROR = this.getClass().getClassLoader().getResourceAsStream("images/no_icon.png");

	public Image getImageStream(InputStream stream)
	{
		return  new Image(Controller.getWindow().getDisplay(),stream); 
	}
	
	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Controller.getWindow().getDisplay().getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0,image.getBounds().width, image.getBounds().height,	0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
		}

	
	@Override
	public void inizialize(Composite panel) {
		// TODO Auto-generated method stub
		
		for(int i=0;i< panel.getChildren().length;i++)
		{
			panel.getChildren()[i].dispose(); 
		}
		
		controlli = new ArrayList<Control>();
		 azioni = new ActionGeneral();
		
		
		
		GridLayout layout = new GridLayout(3, false);
		panel.setLayout(layout);
		Controller.getWindow().setBackground(new Color(Display.getCurrent(),255,255,255)); 
		panel.setData("ID_action","registrationPanel");
		
		
		labelReg = new Label(panel, SWT.FILL);
		labelReg.setText("Registration" );
		labelReg.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 15, SWT.BOLD ));  
		GridData gridData = new GridData();
		gridData.horizontalSpan = 3; 
		labelReg.setLayoutData(gridData); 
		
		controlli.add(labelReg);
		
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
		gridData.minimumWidth = 120; 
		txtProxyHost.setLayoutData(gridData);
		controlli.add(txtProxyHost);
		
		labelImageHost = new Label(panel,SWT.NONE);
	    gridData = new GridData(); 
	    gridData.grabExcessHorizontalSpace = false; 
	    gridData.widthHint = 32; 
	    labelImageHost.setLayoutData(gridData);
	    controlli.add(labelImageHost); 
	     
	    
		labelMail = new Label(panel, SWT.LEFT);
		labelMail.setText("Email");
		labelMail.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.NONE ));
		controlli.add(labelMail);
		
		txtMail = new Text(panel, SWT.BORDER | SWT.WRAP);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		txtMail.setLayoutData(gridData);
		txtMail.setData("ID_action","txtMail");
		controlli.add(txtMail);
		 
		
		labelImageMail = new Label(panel,SWT.NONE);
	    gridData = new GridData(); 
	    gridData.grabExcessHorizontalSpace = false; 
	    gridData.widthHint = 32; 
	    labelImageMail.setLayoutData(gridData); 
	    controlli.add(labelImageMail);
	    
	    
		labelInvitationCode = new Label(panel, SWT.LEFT);
		labelInvitationCode.setText("Invitation code");
		labelInvitationCode.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.NONE ));
		controlli.add(labelInvitationCode);
		
		txtInvitationCode = new Text(panel, SWT.BORDER | SWT.WRAP );
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		txtInvitationCode.setLayoutData(gridData);
		txtInvitationCode.setData("ID_action","txtInvitationCode");
		controlli.add(txtInvitationCode);
		 
		
		labelImageInvitationCode = new Label(panel,SWT.NONE);
	    gridData = new GridData(); 
	    gridData.grabExcessHorizontalSpace = false; 
	    gridData.widthHint = 32; 
	    labelImageInvitationCode.setLayoutData(gridData); 
	    controlli.add(labelImageInvitationCode);
	     
	    
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
		controlli.add(txtUsername);
		 
		
		labelUmageUsername = new Label(panel,SWT.NONE);
	    gridData = new GridData(); 
	    gridData.grabExcessHorizontalSpace = false; 
	    gridData.widthHint = 32; 
	    labelUmageUsername.setLayoutData(gridData); 
	    controlli.add(labelUmageUsername);
	   
	    
		labelPassword = new Label(panel,SWT.LEFT);
		labelPassword.setText("Password");
		labelPassword.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.NONE ));
		controlli.add(labelPassword);
		
		txtPassword = new Text(panel, SWT.PASSWORD | SWT.BORDER );
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
	  
	    
		labelPassword2 = new Label(panel,SWT.LEFT);
		labelPassword2.setText("Confirm Password");
		labelPassword2.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.NONE ));
		controlli.add(labelPassword2);
		
		txtPassword2 = new Text(panel, SWT.PASSWORD | SWT.BORDER );
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		txtPassword2.setLayoutData(gridData);
		txtPassword2.setData("ID_action","txtPassword2");
		controlli.add(txtPassword2);
		 
		
		labelImagePassword2 = new Label(panel,SWT.NONE);
	    gridData = new GridData(); 
	    gridData.grabExcessHorizontalSpace = false; 
	    gridData.widthHint = 32; 
	    labelImagePassword2.setLayoutData(gridData); 
	    controlli.add(labelImagePassword2);
		 
	    
	    labelHidden = new Label(panel, SWT.NONE);
		labelHidden.setText("");
		labelHidden.setVisible(false);
		gridData = new GridData(); 
		gridData.horizontalSpan = 3; 
		labelHidden.setLayoutData(gridData); 
		controlli.add(labelHidden);
		
		btnRegister = new GeneralButton(panel, SWT.None); 
		btnRegister.setText("Register"); 
		btnRegister.setWidth(80);
		btnRegister.setHeight(30); 
		btnRegister.setxCoordinate(5);
		btnRegister.setyCoordinate(230); 
		btnRegister.setDefaultColors(new Color(panel.getDisplay(),  179, 180, 168), new Color(panel.getDisplay(),  179, 180, 168) , null, null);
		btnRegister.setClickedColors(new Color(panel.getDisplay(),  179, 180, 168), new Color(panel.getDisplay(),  179, 180, 168) , null, null);
		btnRegister.setHoverColors(new Color(panel.getDisplay(),  179, 180, 168), new Color(panel.getDisplay(),  179, 180, 168) , null, null);
		btnRegister.setSelectedColors(new Color(panel.getDisplay(),  179, 180, 168), new Color(panel.getDisplay(),  179, 180, 168) , null, null);
		btnRegister.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 12, SWT.BOLD )); 
		
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.LEFT;
		gridData.horizontalSpan = 3; 
		btnRegister.setLayoutData(gridData);
		btnRegister.setData("ID_action", "btnRegister");
		
		btnRegister.setData("LabelAlert", labelAlert);
		btnRegister.setData("ProxyHost", txtProxyHost); 
		btnRegister.setData("LabelImageHost", labelImageHost);
		btnRegister.setData("Email", txtMail);
		btnRegister.setData("LabelImageMail", labelImageMail); 
		btnRegister.setData("InvitationCode", txtInvitationCode);
		btnRegister.setData("LabelImageInvitationCode", labelImageInvitationCode);
		btnRegister.setData("Username", txtUsername);
		btnRegister.setData("LabelUmageUsername", labelUmageUsername);
		btnRegister.setData("Password1", txtPassword);
		btnRegister.setData("LabelImagePassword", labelImagePassword);
		btnRegister.setData("Password2", txtPassword2);
		btnRegister.setData("LabelImagePassword2", labelImagePassword2);
		
		txtProxyHost.setData("LabelAlert", labelAlert);
		txtProxyHost.setData("ProxyHost", txtProxyHost); 
		txtProxyHost.setData("LabelImageHost", labelImageHost);
		txtProxyHost.setData("Email", txtMail);
		txtProxyHost.setData("LabelImageMail", labelImageMail); 
		txtProxyHost.setData("InvitationCode", txtInvitationCode);
		txtProxyHost.setData("LabelImageInvitationCode", labelImageInvitationCode);
		txtProxyHost.setData("Username", txtUsername);
		txtProxyHost.setData("LabelUmageUsername", labelUmageUsername);
		txtProxyHost.setData("Password1", txtPassword);
		txtProxyHost.setData("LabelImagePassword", labelImagePassword);
		txtProxyHost.setData("Password2", txtPassword2);
		txtProxyHost.setData("LabelImagePassword2", labelImagePassword2);
		
		txtMail.setData("LabelAlert", labelAlert);
		txtMail.setData("ProxyHost", txtProxyHost); 
		txtMail.setData("LabelImageHost", labelImageHost);
		txtMail.setData("Email", txtMail);
		txtMail.setData("LabelImageMail", labelImageMail); 
		txtMail.setData("InvitationCode", txtInvitationCode);
		txtMail.setData("LabelImageInvitationCode", labelImageInvitationCode);
		txtMail.setData("Username", txtUsername);
		txtMail.setData("LabelImageUsername", labelUmageUsername);
		txtMail.setData("Password1", txtPassword);
		txtMail.setData("LabelImagePassword", labelImagePassword);
		txtMail.setData("Password2", txtPassword2);
		txtMail.setData("LabelImagePassword2", labelImagePassword2);
		
		txtUsername.setData("LabelAlert", labelAlert);
		txtUsername.setData("ProxyHost", txtProxyHost); 
		txtUsername.setData("LabelImageHost", labelImageHost);
		txtUsername.setData("Email", txtMail);
		txtUsername.setData("LabelImageMail", labelImageMail); 
		txtUsername.setData("InvitationCode", txtInvitationCode);
		txtUsername.setData("LabelImageInvitationCode", labelImageInvitationCode);
		txtUsername.setData("Username", txtUsername);
		txtUsername.setData("LabelImageUsername", labelUmageUsername);
		txtUsername.setData("Password1", txtPassword);
		txtUsername.setData("LabelImagePassword", labelImagePassword);
		txtUsername.setData("Password2", txtPassword2);
		txtUsername.setData("LabelImagePassword2", labelImagePassword2);
		
		controlli.add(btnRegister);
		
		
		for(int i=0;i<5;i++)  {
			labelHidden = new Label(panel, SWT.NONE);
			labelHidden.setText("");
			labelHidden.setVisible(false);
			gridData = new GridData(); 
			gridData.horizontalSpan = 3; 
			labelHidden.setLayoutData(gridData); 
			controlli.add(labelHidden);
		}
		
	    labelLogin = new Link(panel, SWT.CENTER);
	    labelLogin.setText("<a>I already have an account, I want to sign in</a>"); 
	    labelLogin.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.UNDERLINE_LINK)); 
		gridData = new GridData(); 
		gridData.horizontalSpan = 3; 
		gridData.horizontalIndent = 55; 
		labelLogin.setLayoutData(gridData); 
		labelLogin.setData("ID_action", "labelLogin");
		controlli.add(labelLogin);
		
		 panel.setTabList(new Control[] { txtProxyHost, txtMail , txtInvitationCode , txtUsername ,  txtPassword , txtPassword2 , btnRegister });
		
		System.out.println("Controlli " + panel.getChildren().length); 
		
		
		panel.addListener(SWT.Resize, azioni); 
		btnRegister.addListener( SWT.Selection, azioni);
		txtProxyHost.addListener(SWT.FocusOut, azioni); 
		txtUsername.addListener(SWT.FocusOut, azioni); 
		labelLogin.addListener(SWT.Selection, azioni); 
		txtMail.addListener(SWT.FocusOut, azioni); 
		
		
		txtProxyHost.addTraverseListener(new TraverseListener() {
			
			@Override
			public void keyTraversed(TraverseEvent e) {
				// TODO Auto-generated method stub
				if(e.detail == SWT.TRAVERSE_TAB_NEXT ||
						e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
						e.doit = true;	
			}
			
		});
		
		txtMail.addTraverseListener(new TraverseListener() {
			
			@Override
			public void keyTraversed(TraverseEvent e) {
				// TODO Auto-generated method stub
				if(e.detail == SWT.TRAVERSE_TAB_NEXT ||
						e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
						e.doit = true;	
			}
			
		});
		
		txtInvitationCode.addTraverseListener(new TraverseListener() {
			
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
		
		txtPassword2.addTraverseListener(new TraverseListener() {
			
			@Override
			public void keyTraversed(TraverseEvent e) {
				// TODO Auto-generated method stub
				if(e.detail == SWT.TRAVERSE_TAB_NEXT ||
						e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
						e.doit = true;	
			}
		}); 
	}

	@Override
	public void dispose(Composite panel) {
		

		for(int i=0; i < controlli.size();i++)
		{
		 controlli.get(i).dispose(); 
			
		}
		
		panel.setLayout(null);
		panel.layout(); 
		// TODO Auto-generated method stub
	
	}


	
	public HashMap<String, Object> getData()
	{
		HashMap<String, Object> uiData = new HashMap<String, Object>();
		uiData.put("LabelAlert", labelAlert);
		uiData.put("ProxyHost", txtProxyHost); 
		uiData.put("LabelImageHost", labelImageHost);
		uiData.put("Email", txtMail);
		uiData.put("LabelImageMail", labelImageMail); 
		uiData.put("InvitationCode", txtInvitationCode);
		uiData.put("LabelImageInvitationCode", labelImageInvitationCode);
		uiData.put("Username", txtUsername);
		uiData.put("LabelImageUsername", labelUmageUsername);
		uiData.put("Password1", txtPassword);
		uiData.put("LabelImagePassword", labelImagePassword);
		uiData.put("Password2", txtPassword2);
		uiData.put("LabelImagePassword2", labelImagePassword2);
		
		return uiData; 
	}

	

	

	public InputStream getPathIconOk() {
		return PATH_ICON_OK;
	}

	public InputStream getPathIconError() {
		return PATH_ICON_ERROR;
	}

	

	
	
	
}
