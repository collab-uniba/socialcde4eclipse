package it.uniba.di.socialcdeforeclipse.popup;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.WHidden;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.WUser;
import it.uniba.di.socialcdeforeclipse.views.GeneralButton;
import it.uniba.di.socialcdeforeclipse.views.Panel;

public class HideUserPanel implements Panel {

	private Shell shadow;
	private int xCoordinate; 
	private int yCoordinate; 
	private Shell shell;
	private int xCoordinateWithOffset; 
	private int yCoordinateWithOffset; 
	private WUser user_selected; 
	private Listener btnCancelListener; 
	private Listener btnSaveListener; 
	private HashMap<String, Button>	checkboxCreated; 
	
	
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
	
	
	
	public HashMap<String, Button> getCheckboxCreated() {
		return checkboxCreated;
	}

	public void setCheckboxCreated(HashMap<String, Button> checkboxCreated) {
		this.checkboxCreated = checkboxCreated;
	}

	public Listener getBtnCancelListener() {
		return btnCancelListener;
	}

	public void setBtnCancelListener(Listener btnCancelListener) {
		this.btnCancelListener = btnCancelListener;
	}

	public Listener getBtnSaveListener() {
		return btnSaveListener;
	}

	public void setBtnSaveListener(Listener btnSaveListener) {
		this.btnSaveListener = btnSaveListener;
	}

	public int getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public int getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public int getxCoordinateWithOffset() {
		return xCoordinateWithOffset;
	}

	public void setxCoordinateWithOffset(int xCoordinateWithOffset) {
		this.xCoordinateWithOffset = xCoordinateWithOffset;
	}

	public int getyCoordinateWithOffset() {
		return yCoordinateWithOffset;
	}

	public void setyCoordinateWithOffset(int yCoordinateWithOffset) {
		this.yCoordinateWithOffset = yCoordinateWithOffset;
	}

	public WUser getUser_selected() {
		return user_selected;
	}

	public void setUser_selected(WUser user_selected) {
		this.user_selected = user_selected;
	}

	

	@Override
	public void inizialize(Composite panel) {
		// TODO Auto-generated method stub
		shadow = new Shell(panel.getShell(), SWT.NO_TRIM);
		shadow.setSize(Controller.getWindowWidth(),
				Controller.getWindowHeight());
		shadow.setBounds(xCoordinate, yCoordinate, Controller.getWindowWidth(),
				Controller.getWindowHeight());
		shadow.setLayout(new GridLayout(1, false));
		shadow.setAlpha(100);
		shadow.layout();
		shadow.open();

		// TODO Auto-generated method stub
		shell = new Shell(panel.getShell(), SWT.NO_REDRAW_RESIZE);
		shell.setSize(300, 100);

		shell.setBounds(xCoordinateWithOffset, yCoordinateWithOffset, 340, 200);
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		shell.setBackground(new Color(Display.getCurrent(),255,255,255));
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalIndent = 20; 
		shell.setLayoutData(gridData);

		Label labelRegistration = new Label(shell, SWT.WRAP);
		labelRegistration.setText("Hide user from: ");
		labelRegistration.setFont(new Font(shell.getDisplay(), "Calibri", 12,
				SWT.BOLD));
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.widthHint = 300;
		labelRegistration.setLayoutData(gridData);
		
		Label labelAvatar = new Label(shell,SWT.NONE); 
		gridData = new GridData();
		gridData.verticalSpan = 3; 
		labelAvatar.setLayoutData(gridData); 
		labelAvatar.setData("ID_action", "labelAvatar");
		 
		
			try {
				labelAvatar.setImage(get_ImageStream(new URL(user_selected.Avatar).openStream()));
				labelAvatar.setImage(resize(labelAvatar.getImage(), 75, 75)); 
				 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//System.out.println("Eccezione lanciata"); 
				labelAvatar.setImage(get_ImageStream(PATH_DEFAULT_AVATAR));
				labelAvatar.setImage(resize(labelAvatar.getImage(), 75, 75));
				//e.printStackTrace();
			} 
			catch (NullPointerException e) {
				// TODO: handle exception
				labelAvatar.setImage(get_ImageStream(PATH_DEFAULT_AVATAR)); 
				labelAvatar.setImage(resize(labelAvatar.getImage(), 75, 75));
			}
		
			checkboxCreated = new HashMap<String,Button>(); 
		WHidden hiddenSetting = Controller.getProxy().GetUserHideSettings(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), user_selected.Id); 
			
			
				 
				Button suggestionPermission = new Button(shell, SWT.CHECK);
				suggestionPermission.setText("Suggestions");
				if (hiddenSetting.isSuggestions()) {
					suggestionPermission.setSelection(true);
					 
				}
				checkboxCreated.put("suggestion", suggestionPermission);
				
				Button iterationTimelinePermission = new Button(shell, SWT.CHECK); 
				iterationTimelinePermission.setText("Iteration Timeline"); 
				if(hiddenSetting.isInteractive())
				{
					iterationTimelinePermission.setSelection(true); 
				}
				checkboxCreated.put("iteration", iterationTimelinePermission); 
				
				Button interactiveTimelinePermission = new Button(shell, SWT.CHECK); 
				interactiveTimelinePermission.setText("Interactive Timeline"); 
				if(hiddenSetting.isInteractive())
				{
					interactiveTimelinePermission.setSelection(true); 
				}
				checkboxCreated.put("interactive", interactiveTimelinePermission); 
				
				Label labelhidden = new Label(shell, SWT.None); 
				gridData = new GridData(); 
				gridData.horizontalSpan = 2; 
				gridData.verticalSpan = 2; 
				labelhidden.setLayoutData(gridData); 
				
				GeneralButton btnCancel = new GeneralButton(shell, SWT.None); 
				btnCancel.setText("Cancel"); 
				btnCancel.setWidth(90);
				btnCancel.setHeight(30); 
				btnCancel.setxCoordinate(5);
				btnCancel.setyCoordinate(133); 
				btnCancel.setDefaultColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
				btnCancel.setClickedColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
				btnCancel.setHoverColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
				btnCancel.setSelectedColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
				btnCancel.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 12, SWT.BOLD )); 
				btnCancel.addListener(SWT.Selection, btnCancelListener);
				
				GeneralButton btnSave = new GeneralButton(shell, SWT.None); 
				btnSave.setText("Save"); 
				btnSave.setWidth(90);
				btnSave.setHeight(30); 
				btnSave.setxCoordinate(105);
				btnSave.setyCoordinate(133); 
				btnSave.setDefaultColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
				btnSave.setClickedColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
				btnSave.setHoverColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
				btnSave.setSelectedColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
				btnSave.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 12, SWT.BOLD )); 
				btnSave.addListener(SWT.Selection, btnSaveListener);
				
				
				
			

			shadow.addMouseListener(new MouseListener() {

				@Override
				public void mouseUp(MouseEvent e) {
					// TODO Auto-generated method stub
					shell.forceFocus();
				}

				@Override
				public void mouseDown(MouseEvent e) {
					// TODO Auto-generated method stub
					shell.forceFocus();
				}

				@Override
				public void mouseDoubleClick(MouseEvent e) {
					// TODO Auto-generated method stub
					shell.forceFocus();
				}
			});
			
			shell.layout();
			shell.open();
			
	}

	@Override
	public void dispose(Composite panel) {
		// TODO Auto-generated method stub
		shadow.dispose();
		shell.dispose();
	}

	
	@Override
	public HashMap<String, Object> getData() {
		// TODO Auto-generated method stub
		return null;
	}

}
