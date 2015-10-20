package it.uniba.di.collab.socialcdeforeclipse.staticview;

import it.uniba.di.collab.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.object.GeneralButton;
import it.uniba.di.collab.socialcdeforeclipse.object.Panel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
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
	private GeneralButton btnRegister;
	private Label labelAlert;
	private Label labelImageHostOk;
	private Label labelImageHostNo;
	private Label labelImageHostHidden;
	
	private Label labelImageMailOk;
	private Label labelImageMailNo;
	private Label labelImageMailHidden;
	
	
	private Label labelImageInvitationCodeOk;
	private Label labelImageInvitationCodeNo;
	private Label labelImageInvitationCodeHidden;
	
	private Label labelImageUsernameOk;
	private Label labelImageUsernameNo;
	private Label labelImageUsernameHidden;
	
	
	private Label labelImagePasswordOk;
	private Label labelImagePasswordNo;
	private Label labelImagePasswordHidden;
	
	private Label labelImagePassword2Ok;
	private Label labelImagePassword2No;
	private Label labelImagePassword2Hidden;

	private Link labelLogin;
	private ArrayList<Control> controlli;
	private Listener azioni;
	private final InputStream PATH_ICON_OK = this.getClass().getClassLoader()
			.getResourceAsStream("images/yes_icon.png");
	private final InputStream PATH_ICON_ERROR = this.getClass()
			.getClassLoader().getResourceAsStream("images/no_icon.png");

	public Image getImageStream(InputStream stream) {
		return new Image(Controller.getWindow().getDisplay(), stream);
	}

	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, image.getBounds().width,
				image.getBounds().height, 0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
	}

	@Override
	public void inizialize(Composite panel) {
		// TODO Auto-generated method stub

		for (int i = 0; i < panel.getChildren().length; i++) {
			panel.getChildren()[i].dispose();
		}

		controlli = new ArrayList<Control>();
		azioni = new ActionGeneral();

		GridLayout layout = new GridLayout(3, false);
		panel.setLayout(layout);
		Controller.getWindow().setBackground(
				new Color(Display.getCurrent(), 255, 255, 255));
		panel.setData("ID_action", "registrationPanel");

		labelReg = new Label(panel, SWT.FILL);
		labelReg.setText("Registration");
		labelReg.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 15, SWT.BOLD));
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

		txtProxyHost = new Text(panel, SWT.BORDER | SWT.SINGLE);
		txtProxyHost.setData("ID_action", "txtProxyHost");
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.minimumWidth = 120;
		txtProxyHost.setLayoutData(gridData);
		controlli.add(txtProxyHost);

		labelImageHostOk = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.widthHint = 32;
		gridData.exclude = true; 
		labelImageHostOk.setLayoutData(gridData);
		labelImageHostOk.setImage(getImageStream(this.getClass().getClassLoader()
			.getResourceAsStream("images/yes_icon.png"))); 
		controlli.add(labelImageHostOk);

		labelImageHostNo = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.widthHint = 32;
		gridData.exclude = true; 
		labelImageHostNo.setLayoutData(gridData);
		labelImageHostNo.setImage(getImageStream(this.getClass()
			.getClassLoader().getResourceAsStream("images/no_icon.png")));
		controlli.add(labelImageHostNo);
		
		labelImageHostHidden = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.widthHint = 32;
		gridData.exclude = false; 
		labelImageHostHidden.setLayoutData(gridData);
		controlli.add(labelImageHostHidden);
		
		
		labelMail = new Label(panel, SWT.LEFT);
		labelMail.setText("Email");
		labelMail.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 10, SWT.NONE));
		controlli.add(labelMail);

		txtMail = new Text(panel, SWT.BORDER | SWT.SINGLE);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		txtMail.setLayoutData(gridData);
		txtMail.setData("ID_action", "txtMail");
		controlli.add(txtMail);

		labelImageMailOk = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.widthHint = 32;
		gridData.exclude = true; 
		labelImageMailOk.setLayoutData(gridData);
		labelImageMailOk.setImage(getImageStream(this.getClass().getClassLoader()
			.getResourceAsStream("images/yes_icon.png")));
		controlli.add(labelImageMailOk);
		
		labelImageMailNo = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.widthHint = 32;
		gridData.exclude = true; 
		labelImageMailNo.setLayoutData(gridData);
		labelImageMailNo.setImage(getImageStream(this.getClass()
			.getClassLoader().getResourceAsStream("images/no_icon.png")));
		controlli.add(labelImageMailNo);
		
		labelImageMailHidden = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.widthHint = 32;
		gridData.exclude = false; 
		labelImageMailHidden.setLayoutData(gridData);
		controlli.add(labelImageMailHidden);

		labelInvitationCode = new Label(panel, SWT.LEFT);
		labelInvitationCode.setText("Invitation code");
		labelInvitationCode.setFont(new Font(Controller.getWindow()
				.getDisplay(), "Calibri", 10, SWT.NONE));
		controlli.add(labelInvitationCode);

		txtInvitationCode = new Text(panel, SWT.BORDER | SWT.SINGLE);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		txtInvitationCode.setLayoutData(gridData);
		txtInvitationCode.setData("ID_action", "txtInvitationCode");
		controlli.add(txtInvitationCode);

		labelImageInvitationCodeOk = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.exclude = true; 
		gridData.widthHint = 32;
		labelImageInvitationCodeOk.setLayoutData(gridData);
		controlli.add(labelImageInvitationCodeOk);
		
		labelImageInvitationCodeNo = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.exclude = true; 
		gridData.widthHint = 32;
		labelImageInvitationCodeNo.setLayoutData(gridData);
		controlli.add(labelImageInvitationCodeNo);
		
		labelImageInvitationCodeHidden = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.exclude = false; 
		gridData.widthHint = 32;
		labelImageInvitationCodeHidden.setLayoutData(gridData);
		controlli.add(labelImageInvitationCodeHidden);
		
		
		

		labelUsername = new Label(panel, SWT.LEFT);
		labelUsername.setText("Username");
		labelUsername.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 10, SWT.NONE));
		controlli.add(labelUsername);

		txtUsername = new Text(panel, SWT.BORDER | SWT.SINGLE);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		txtUsername.setLayoutData(gridData);
		txtUsername.setData("ID_action", "txtUsername");
		controlli.add(txtUsername);

		labelImageUsernameOk = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.widthHint = 32;
		gridData.exclude = true; 
		labelImageUsernameOk.setLayoutData(gridData);
		labelImageUsernameOk.setImage(getImageStream(this.getClass().getClassLoader()
			.getResourceAsStream("images/yes_icon.png"))); 
		
		labelImageUsernameNo = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.exclude = true; 
		gridData.widthHint = 32;
		labelImageUsernameNo.setLayoutData(gridData);
		labelImageUsernameNo.setImage(getImageStream(this.getClass().getClassLoader()
			.getResourceAsStream("images/no_icon.png"))); 
		controlli.add(labelImageUsernameNo);
		
		labelImageUsernameHidden = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.exclude = false; 
		gridData.widthHint = 32;
		labelImageUsernameHidden.setLayoutData(gridData);
		controlli.add(labelImageUsernameHidden);

		labelPassword = new Label(panel, SWT.LEFT);
		labelPassword.setText("Password");
		labelPassword.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 10, SWT.NONE));
		controlli.add(labelPassword);

		txtPassword = new Text(panel, SWT.PASSWORD | SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		txtPassword.setLayoutData(gridData);
		txtPassword.setData("ID_action", "txtPassword");
		controlli.add(txtPassword);

		labelImagePasswordOk = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.widthHint = 32;
		gridData.exclude = true; 
		labelImagePasswordOk.setLayoutData(gridData);
		controlli.add(labelImagePasswordOk);
		
		labelImagePasswordNo = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.widthHint = 32;
		gridData.exclude = true; 
		labelImagePasswordNo.setLayoutData(gridData);
		controlli.add(labelImagePasswordNo);
		
		labelImagePasswordHidden = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.widthHint = 32;
		gridData.exclude = false; 
		labelImagePasswordHidden.setLayoutData(gridData);
		controlli.add(labelImagePasswordHidden);

		labelPassword2 = new Label(panel, SWT.LEFT);
		labelPassword2.setText("Confirm Password");
		labelPassword2.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 10, SWT.NONE));
		controlli.add(labelPassword2);

		txtPassword2 = new Text(panel, SWT.PASSWORD | SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		txtPassword2.setLayoutData(gridData);
		txtPassword2.setData("ID_action", "txtPassword2");
		controlli.add(txtPassword2);

		labelImagePassword2Ok = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.exclude = true; 
		gridData.widthHint = 32;
		labelImagePassword2Ok.setLayoutData(gridData);
		controlli.add(labelImagePassword2Ok);
		
		labelImagePassword2No = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.exclude = true; 
		gridData.widthHint = 32;
		labelImagePassword2No.setLayoutData(gridData);
		controlli.add(labelImagePassword2No);
		
		labelImagePassword2Hidden = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.exclude = false; 
		gridData.widthHint = 32;
		labelImagePassword2Hidden.setLayoutData(gridData);
		controlli.add(labelImagePassword2Hidden);

		labelHidden = new Label(panel, SWT.NONE);
		labelHidden.setText("");
		labelHidden.setVisible(false);
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		labelHidden.setLayoutData(gridData);
		controlli.add(labelHidden);

		btnRegister = new GeneralButton(panel, SWT.None);
		btnRegister.setText("Register");
		btnRegister.setWidth(100);
		btnRegister.setHeight(40);
		btnRegister.setxCoordinate(5);
		btnRegister.setyCoordinate(280);
		btnRegister.setDefaultColors(new Color(panel.getDisplay(), 179, 180,
				168), new Color(panel.getDisplay(), 179, 180, 168), null, null);
		btnRegister.setClickedColors(new Color(panel.getDisplay(), 179, 180,
				168), new Color(panel.getDisplay(), 179, 180, 168), null, null);
		btnRegister.setHoverColors(
				new Color(panel.getDisplay(), 179, 180, 168),
				new Color(panel.getDisplay(), 179, 180, 168), null, null);
		btnRegister.setSelectedColors(new Color(panel.getDisplay(), 179, 180,
				168), new Color(panel.getDisplay(), 179, 180, 168), null, null);
		btnRegister.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 12, SWT.BOLD));

		gridData = new GridData();
		gridData.horizontalAlignment = SWT.LEFT;
		gridData.horizontalSpan = 3;
		btnRegister.setLayoutData(gridData);
		btnRegister.setData("ID_action", "btnRegister");

		
		controlli.add(btnRegister);

		for (int i = 0; i < 5; i++) {
			labelHidden = new Label(panel, SWT.NONE);
			labelHidden.setText("");
			labelHidden.setVisible(false);
			gridData = new GridData();
			gridData.horizontalSpan = 3;
			labelHidden.setLayoutData(gridData);
			controlli.add(labelHidden);
		}

		labelLogin = new Link(panel, SWT.CENTER);
		labelLogin
				.setText("<a>I already have an account, I want to sign in</a>");
		labelLogin.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 10, SWT.UNDERLINE_LINK));
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.horizontalIndent = 55;
		labelLogin.setLayoutData(gridData);
		labelLogin.setData("ID_action", "labelLogin");
		controlli.add(labelLogin);

		panel.setTabList(new Control[] { txtProxyHost, txtMail,
				txtInvitationCode, txtUsername, txtPassword, txtPassword2,
				btnRegister });

	
		btnRegister.addListener(SWT.Selection, azioni);
		txtProxyHost.addListener(SWT.FocusOut, azioni);
		txtUsername.addListener(SWT.FocusOut, azioni);
		labelLogin.addListener(SWT.Selection, azioni);
		txtMail.addListener(SWT.FocusOut, azioni);

		txtProxyHost.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(TraverseEvent e) {
				// TODO Auto-generated method stub
				if (e.detail == SWT.TRAVERSE_TAB_NEXT
						|| e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
					e.doit = true;
			}

		});

		txtMail.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(TraverseEvent e) {
				// TODO Auto-generated method stub
				if (e.detail == SWT.TRAVERSE_TAB_NEXT
						|| e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
					e.doit = true;
			}

		});

		txtInvitationCode.addTraverseListener(new TraverseListener() {

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

		txtPassword2.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(TraverseEvent e) {
				// TODO Auto-generated method stub
				if (e.detail == SWT.TRAVERSE_TAB_NEXT
						|| e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
					e.doit = true;
			}
		});
	}

	@Override
	public void dispose(Composite panel) {

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

		panel.setLayout(null);
		panel.layout();
		// TODO Auto-generated method stub

	}

	public HashMap<String, Object> getData() {
		HashMap<String, Object> uiData = new HashMap<String, Object>();
		uiData.put("LabelAlert", labelAlert);
		uiData.put("ProxyHost", txtProxyHost);
		uiData.put("LabelImageHostOk", labelImageHostOk);
		uiData.put("LabelImageHostNo", labelImageHostNo);
		uiData.put("LabelImageHostHidden", labelImageHostHidden);
		uiData.put("Email", txtMail);
		uiData.put("LabelImageMailOk", labelImageMailOk);
		uiData.put("LabelImageMailNo", labelImageMailNo);
		uiData.put("LabelImageMailHidden", labelImageMailHidden);
		uiData.put("InvitationCode", txtInvitationCode);
		uiData.put("LabelImageInvitationCodeOk", labelImageInvitationCodeOk);
		uiData.put("LabelImageInvitationCodeNo", labelImageInvitationCodeNo);
		uiData.put("LabelImageInvitationCodeHidden", labelImageInvitationCodeHidden);
		uiData.put("Username", txtUsername);
		uiData.put("LabelImageUsernameOk", labelImageUsernameOk);
		uiData.put("LabelImageUsernameNo", labelImageUsernameNo);
		uiData.put("LabelImageUsernameHidden", labelImageUsernameHidden);
		uiData.put("Password1", txtPassword);
		uiData.put("LabelImagePasswordOk", labelImagePasswordOk);
		uiData.put("LabelImagePasswordNo", labelImagePasswordNo);
		uiData.put("LabelImagePasswordHidden", labelImagePasswordHidden);
		uiData.put("Password2", txtPassword2);
		uiData.put("LabelImagePassword2Ok", labelImagePassword2Ok);
		uiData.put("LabelImagePassword2No", labelImagePassword2No);
		uiData.put("LabelImagePassword2Hidden", labelImagePassword2Hidden);

		return uiData;
	}

	public InputStream getPathIconOk() {
		return PATH_ICON_OK;
	}

	public InputStream getPathIconError() {
		return PATH_ICON_ERROR;
	}

}
