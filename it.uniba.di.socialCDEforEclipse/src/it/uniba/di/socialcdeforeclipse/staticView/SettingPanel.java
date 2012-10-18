package it.uniba.di.socialcdeforeclipse.staticView;

import it.uniba.di.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.views.Panel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.viewers.CellEditor.LayoutData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class SettingPanel implements Panel {
	
	private Label labelAvatar; 
	private Label labelTitle;
	private Label labelHidden;
	private Label labelAlert; 
	private Label labelOldPassword; 
	private Text txtOldPassword; 
	private Label labelNewPassword; 
	private Text txtNewPassword; 
	private Button btnCancel; 
	private Button btnOk; 
	private ArrayList<Control> controlli;
	
	private final InputStream PATH_DEFAULT_AVATAR = this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png");
	
	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Controller.getWindow().getDisplay().getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0,
		image.getBounds().width, image.getBounds().height,
		0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
		}
	
	public Image get_ImageStream(InputStream stream)
	{
		return  new Image(Controller.getWindow().getDisplay(),stream); 
	}
	
	@Override
	public void inizialize(Composite panel) {
		// TODO Auto-generated method stub
		controlli = new ArrayList<Control>();
		Listener azioni = new ActionGeneral();
		
		panel.setLayout(new GridLayout(3,false)); 
		labelTitle = new Label(panel, SWT.NONE);
		labelTitle.setText("Change password" );
		labelTitle.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 15, SWT.BOLD ));  
		GridData layoutdata = new GridData(); 
		layoutdata.horizontalSpan = 3; 
		labelTitle.setLayoutData(layoutdata); 
		controlli.add(labelTitle); 
		
		labelHidden = new Label(panel,SWT.NONE); 
		labelHidden.setVisible(false); 
		layoutdata = new GridData(); 
		layoutdata.horizontalSpan = 3; 
		labelHidden.setLayoutData(layoutdata); 
		controlli.add(labelHidden);
		
		labelAlert = new Label(panel, SWT.NONE); 
		labelAlert.setVisible(false); 
	    layoutdata = new GridData(); 
		layoutdata.horizontalSpan = 3; 
		labelAlert.setLayoutData(layoutdata);
		labelAlert.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.BOLD ));  
		labelAlert.setForeground(new Color(Controller.getWindow().getDisplay(),Controller.getWindow().getDisplay().getSystemColor(SWT.COLOR_DARK_RED).getRGB()));
		controlli.add(labelAlert); 
		
		labelHidden = new Label(panel,SWT.NONE); 
		labelHidden.setVisible(false); 
		layoutdata = new GridData(); 
		layoutdata.horizontalSpan = 3; 
		labelHidden.setLayoutData(layoutdata); 
		controlli.add(labelHidden);
		
		labelAvatar = new Label(panel,SWT.NONE); 
		GridData gridData = new GridData();
		gridData.verticalSpan = 2; 
		labelAvatar.setLayoutData(gridData); 
		if(Controller.getCurrentUser().Avatar == null || Controller.getCurrentUser().Avatar.equals(""))
		{
			labelAvatar.setImage(get_ImageStream(PATH_DEFAULT_AVATAR)); 
			labelAvatar.setImage(resize(labelAvatar.getImage(), 48, 48));
		}
		else
		{
			try {
				labelAvatar.setImage(get_ImageStream(new URL(Controller.getCurrentUser().Avatar).openStream()));
				labelAvatar.setImage(resize(labelAvatar.getImage(), 48, 48)); 
				 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Eccezione lanciata"); 
				labelAvatar.setImage(get_ImageStream(PATH_DEFAULT_AVATAR));
				labelAvatar.setImage(resize(labelAvatar.getImage(), 48, 48));
				//e.printStackTrace();
			} 
		}
		controlli.add(labelAvatar); 
		
		labelOldPassword = new Label(panel, SWT.NONE); 
		labelOldPassword.setText("Old password"); 
		labelOldPassword.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.NONE ));
		controlli.add(labelOldPassword); 
		
		txtOldPassword = new Text(panel, SWT.PASSWORD | SWT.BORDER ); 
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.minimumWidth = 120;
		txtOldPassword.setLayoutData(gridData); 
		controlli.add(txtOldPassword); 
		
		labelNewPassword = new Label(panel, SWT.NONE); 
		labelNewPassword.setText("Password"); 
		labelNewPassword.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.NONE ));
		controlli.add(labelNewPassword); 
		
		txtNewPassword = new Text(panel, SWT.PASSWORD | SWT.BORDER ); 
		txtNewPassword.setLayoutData(gridData); 
		controlli.add(txtNewPassword); 
		
		labelHidden = new Label(panel,SWT.NONE); 
		labelHidden.setVisible(false); 
		layoutdata = new GridData(); 
		layoutdata.horizontalSpan = 3; 
		labelHidden.setLayoutData(layoutdata); 
		controlli.add(labelHidden);
		
		btnCancel = new Button(panel, SWT.PUSH); 
		btnCancel.setText("Cancel");
		btnCancel.computeSize(SWT.DEFAULT, SWT.DEFAULT); 
	    btnCancel.setData("ID_action", "SettingBtnCancel");
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		btnCancel.setLayoutData(gridData); 
		controlli.add(btnCancel); 
		
		btnOk = new Button(panel, SWT.PUSH); 
		btnOk.setText("Ok"); 
		btnOk.setData("ID_action", "SettingBtnOk");
		controlli.add(btnOk); 
		
		btnCancel.addListener(SWT.Selection, azioni); 
		btnOk.addListener(SWT.Selection, azioni); 
		 
		Controller.getProfilePanel().getComposite_dinamic().layout(); 
		Controller.getWindow().layout(); 
		 
		Controller.setWindowName("Settings"); 
		
	}

	@Override
	public void dispose(Composite panel) {
		// TODO Auto-generated method stub
		for(int i=0; i < controlli.size();i++)
		{
		 controlli.get(i).dispose(); 
			
		}
		
		panel.setLayout(null); 
	}

	@Override
	public HashMap<String, String> getInput() {
		// TODO Auto-generated method stub
		HashMap<String, String> Input_data = new HashMap<>();
		Input_data.put("oldPassword", txtOldPassword.getText()); 
		Input_data.put("newPassword", txtNewPassword.getText()); 
		return Input_data; 
	}

	public Button getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(Button btnCancel) {
		this.btnCancel = btnCancel;
	}

	public Button getBtnOk() {
		return btnOk;
	}

	public void setBtnOk(Button btnOk) {
		this.btnOk = btnOk;
	}

	public Label getLabelAvatar() {
		return labelAvatar;
	}

	public void setLabelAvatar(Label labelAvatar) {
		this.labelAvatar = labelAvatar;
	}

	public Label getLabelAlert() {
		return labelAlert;
	}

	public void setLabelAlert(Label labelAlert) {
		this.labelAlert = labelAlert;
	}

	public Text getTxtOldPassword() {
		return txtOldPassword;
	}

	public void setTxtOldPassword(Text txtOldPassword) {
		this.txtOldPassword = txtOldPassword;
	}

	public Text getTxtNewPassword() {
		return txtNewPassword;
	}

	public void setTxtNewPassword(Text txtNewPassword) {
		this.txtNewPassword = txtNewPassword;
	}
	
	

}
