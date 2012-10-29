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
	private final InputStream PATH_WALLPAPER = this.getClass().getClassLoader().getResourceAsStream("images/Wallpaper.png");

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
		Controller.getWindow().setBackgroundImage(resize(getImageStream(PATH_WALLPAPER), Controller.getWindowWidth(), Controller.getWindowHeight())); 
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
		btnRegister.setDefaultColors(new Color(panel.getDisplay(), 152, 210, 227), new Color(panel.getDisplay(), 211, 217, 223) , null, null);
		btnRegister.setClickedColors(new Color(panel.getDisplay(), 152, 210, 227), new Color(panel.getDisplay(), 211, 217, 223) , null, null);
		btnRegister.setHoverColors(new Color(panel.getDisplay(), 152, 210, 227), new Color(panel.getDisplay(), 211, 217, 223) , null, null);
		btnRegister.setSelectedColors(new Color(panel.getDisplay(), 152, 210, 227), new Color(panel.getDisplay(), 211, 217, 223) , null, null);
		btnRegister.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 12, SWT.BOLD )); 
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.LEFT;
		gridData.horizontalSpan = 3; 
		btnRegister.setLayoutData(gridData);
		btnRegister.setData("ID_action", "btnRegister");
		controlli.add(btnRegister);
		
		/*
		btnRegister = new Button(panel, SWT.NONE);
		btnRegister.setText("Register");
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.RIGHT;
		gridData.horizontalSpan = 3; 
		btnRegister.setLayoutData(gridData);
		btnRegister.setData("ID_action", "btnRegister");
		btnRegister.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.NONE ));
		controlli.add(btnRegister);
		*/
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

	

	@Override
	public HashMap<String, String> getInput() {
		// TODO Auto-generated method stub
		HashMap<String, String> Input_data = new HashMap<>();
		Input_data.put("Proxy", txtProxyHost.getText()); 
		Input_data.put("Invitation_code", txtInvitationCode.getText()); 
		Input_data.put("Username", txtUsername.getText()); 
		Input_data.put("Password", txtPassword.getText()); 
		Input_data.put("Password2", txtPassword2.getText()); 
		Input_data.put("Email", txtMail.getText()); 
		return Input_data; 
		
	}

	

	public Text getTxtProxyHost() {
		return txtProxyHost;
	}

	public void setTxtProxyHost(Text newTxtProxyHost) {
		txtProxyHost = newTxtProxyHost;
	}

	public Text getTxtMail() {
		return txtMail;
	}

	public void setTxtMail(Text newTxtMail) {
		txtMail = newTxtMail;
	}

	public Text getTxtInvitationCode() {
		return txtInvitationCode;
	}

	public void setTxtInvitationCode(Text newTxtInvitationCode) {
		txtInvitationCode = newTxtInvitationCode;
	}

	public Text getTxtUsername() {
		return txtUsername;
	}

	public void setTxtUsername(Text newTxtUsername) {
		txtUsername = newTxtUsername;
	}

	public Text getTxtPassword() {
		return txtPassword;
	}

	public void setTxtPassword(Text newTxtPassword) {
		txtPassword = newTxtPassword;
	}

	public Text getTxtPassword2() {
		return txtPassword2;
	}

	public void setTxtPassword2(Text newTxtPassword2) {
		txtPassword2 = newTxtPassword2;
	}

	public Label getLabelAlert() {
		return labelAlert;
	}

	public void setLabelAlert(Label newlabelAlert) {
		this.labelAlert = newlabelAlert;
	}

	public Label getLabelImageHost() {
		return labelImageHost;
	}

	public void setLabelImageHost(Label newLabelImageHost) {
		this.labelImageHost = newLabelImageHost;
	}

	public Label getLabelImageMail() {
		return labelImageMail;
	}

	public void setLabelImageMail(Label newLabelImageMail) {
		this.labelImageMail = newLabelImageMail;
	}

	public Label getLabelImageInvitationCode() {
		return labelImageInvitationCode;
	}

	public void setLabelImageInvitationCode(Label newLabelImageInvitationCode) {
		this.labelImageInvitationCode = newLabelImageInvitationCode;
	}

	public Label getLabelImageUsername() {
		return labelUmageUsername;
	}

	public void setLabelImageUsername(Label newLabelImageUsername) {
		this.labelUmageUsername = newLabelImageUsername;
	}

	public Label getLabelImagePassword() {
		return labelImagePassword;
	}

	public void setLabelImagePassword(Label newLabelImagePassword) {
		this.labelImagePassword = newLabelImagePassword;
	}

	public Label getLabelImagePassword2() {
		return labelImagePassword2;
	}

	public void setLabelImagePassword2(Label newLabelImagePassword2) {
		this.labelImagePassword2 = newLabelImagePassword2;
	}

	public InputStream getPathIconOk() {
		return PATH_ICON_OK;
	}

	public InputStream getPathIconError() {
		return PATH_ICON_ERROR;
	}

	public InputStream getPathWallpaper() {
		return PATH_WALLPAPER;
	}

	
	
	
}
