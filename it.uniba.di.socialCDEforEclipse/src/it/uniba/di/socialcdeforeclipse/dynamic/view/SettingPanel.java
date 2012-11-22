package it.uniba.di.socialcdeforeclipse.dynamic.view;

import it.uniba.di.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.object.GeneralButton;
import it.uniba.di.socialcdeforeclipse.views.Panel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.viewers.CellEditor.LayoutData;
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
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
	private GeneralButton btnCancel; 
	private GeneralButton btnOk; 
	private ArrayList<Control> controlli;
	
	private final InputStream PATH_DEFAULT_AVATAR = this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png");
	
	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
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

		btnCancel = new GeneralButton(panel, SWT.None); 
		btnCancel.setText("Cancel"); 
		btnCancel.setWidth(80);
		btnCancel.setHeight(30); 
		btnCancel.setxCoordinate(5);
		btnCancel.setyCoordinate(167); 
		btnCancel.setDefaultColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
		btnCancel.setClickedColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
		btnCancel.setHoverColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
		btnCancel.setSelectedColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
		btnCancel.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 12, SWT.BOLD )); 
		btnCancel.setData("ID_action", "SettingBtnCancel");
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		btnCancel.setLayoutData(gridData); 
		controlli.add(btnCancel); 
		
		btnOk = new GeneralButton(panel, SWT.None); 
		btnOk.setText("Ok"); 
		btnOk.setWidth(80);
		btnOk.setHeight(30); 
		btnOk.setxCoordinate(140);
		btnOk.setyCoordinate(167); 
		btnOk.setDefaultColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
		btnOk.setClickedColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
		btnOk.setHoverColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
		btnOk.setSelectedColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
		btnOk.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 12, SWT.BOLD )); 
		btnOk.setData("ID_action", "SettingBtnOk");
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		btnOk.setLayoutData(gridData); 
		controlli.add(btnOk); 
		
		 panel.setTabList(new Control[] { txtOldPassword , txtNewPassword , btnOk , btnCancel });
			
		
		btnCancel.addListener(SWT.Selection, azioni); 
		btnOk.addListener(SWT.Selection, azioni); 
		
		txtOldPassword.addTraverseListener(new TraverseListener() {
			
			@Override
			public void keyTraversed(TraverseEvent e) {
				// TODO Auto-generated method stub
				if(e.detail == SWT.TRAVERSE_TAB_NEXT ||
						e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
						e.doit = true;	
			}
		});
		
		txtNewPassword.addTraverseListener(new TraverseListener() {
			
			@Override
			public void keyTraversed(TraverseEvent e) {
				// TODO Auto-generated method stub
				if(e.detail == SWT.TRAVERSE_TAB_NEXT ||
						e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
						e.doit = true;	
			}
		});
		
		btnOk.addTraverseListener(new TraverseListener() {
			
			@Override
			public void keyTraversed(TraverseEvent e) {
				// TODO Auto-generated method stub
				if(e.detail == SWT.TRAVERSE_TAB_NEXT ||
						e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
						e.doit = true;	
			}
		}); 
		
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

	public GeneralButton getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(GeneralButton btnCancel) {
		this.btnCancel = btnCancel;
	}

	public GeneralButton getBtnOk() {
		return btnOk;
	}

	public void setBtnOk(GeneralButton btnOk) {
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

	@Override
	public HashMap<String, Object> getData() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
