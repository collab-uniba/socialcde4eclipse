package it.uniba.di.socialcdeforeclipse.popup;

import java.io.InputStream;
import java.util.HashMap;

import org.eclipse.jface.viewers.LabelDecorator;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.dynamicView.InterceptingFilter;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.WService;
import it.uniba.di.socialcdeforeclipse.views.GeneralButton;
import it.uniba.di.socialcdeforeclipse.views.Panel;

public class TFSLogin implements Panel {
	private Shell shadow;
	private Shell shell;
	private int xCoordinate;
	private int yCoordinate;
	private int xCoordinateWithOffset;
	private int yCoordinateWithOffset;
	private WService service;  
	private Text textDomain; 
	private Text textUsername; 
	private Text textPassword; 
	private Listener okListener;
	private Listener cancelListener; 
	
	private final InputStream PATH_WALLPAPER = this.getClass().getClassLoader()
			.getResourceAsStream("images/Wallpaper.png");

	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Controller.getWindow().getDisplay()
				.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, image.getBounds().width,
				image.getBounds().height, 0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
	}

	private Image getImageStream(InputStream stream) {
		return new Image(Controller.getWindow().getDisplay(), stream);
	}
	
	@Override
	public void inizialize(Composite panel) {
		// TODO Auto-generated method stub
		Image imgWallpaper = resize(getImageStream(PATH_WALLPAPER), 300, 200);

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

		shell.setBounds(xCoordinateWithOffset, yCoordinateWithOffset, 300, 200);
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		shell.setBackgroundImage(imgWallpaper);
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = gridData.FILL;
		shell.setLayoutData(gridData);
		
		Label labelRegistration = new Label(shell, SWT.WRAP);
		labelRegistration.setText("Registration ");
		labelRegistration.setFont(new Font(shell.getDisplay(), "Calibri", 15, SWT.BOLD));
		gridData = new GridData(); 
		gridData.horizontalSpan = 2; 
		gridData.widthHint = 300; 
		labelRegistration.setLayoutData(gridData); 
		
		Label labelHidden = new Label(shell, SWT.NONE);
		labelHidden.setText("");
		labelHidden.setVisible(false);
		gridData = new GridData();
		gridData.horizontalSpan = 2; 
		labelHidden.setLayoutData(gridData);
		
		if(service.RequireTFSDomain)
		{
			Label labelDomain = new Label(shell, SWT.None); 
			labelDomain.setText("Domain:"); 
			labelDomain.setFont(new Font(shell.getDisplay(), "Calibri", 12, SWT.BOLD));
			
			textDomain = new Text(shell, SWT.None); 
			gridData = new GridData();
			gridData.horizontalAlignment = SWT.FILL;
			gridData.grabExcessHorizontalSpace = true;
			textDomain.setLayoutData(gridData);
		}
			Label labelUsername = new Label(shell, SWT.None); 
			labelUsername.setText("Username"); 
			labelUsername.setFont(new Font(shell.getDisplay(), "Calibri", 12, SWT.BOLD));
			
			textUsername = new Text(shell, SWT.None); 
			gridData = new GridData();
			gridData.horizontalAlignment = SWT.FILL;
			gridData.grabExcessHorizontalSpace = true;
			textUsername.setLayoutData(gridData);
			
			
			Label labelPassword = new Label(shell, SWT.None); 
			labelPassword.setText("Password"); 
			labelPassword.setFont(new Font(shell.getDisplay(), "Calibri", 12, SWT.BOLD));
			
			textPassword = new Text(shell, SWT.PASSWORD); 
			gridData = new GridData();
			gridData.horizontalAlignment = SWT.FILL;
			gridData.grabExcessHorizontalSpace = true;
			textPassword.setLayoutData(gridData); 
		
			labelHidden = new Label(shell, SWT.NONE);
			labelHidden.setText("");
			labelHidden.setVisible(false);
			gridData = new GridData();
			gridData.horizontalSpan = 2; 
			labelHidden.setLayoutData(gridData);
			
			Composite compositeBtnOk = new Composite(shell, SWT.None); 
			compositeBtnOk.setLayout(new GridLayout(1,false)); 
			GridData	grid = new GridData(); 
			grid.widthHint = 90;
			grid.heightHint = 40;  
			compositeBtnOk.setLayoutData(grid);
			
			
			
		  GeneralButton	btnOk = new GeneralButton(compositeBtnOk, SWT.None); 
		  btnOk.setText("Ok"); 
		  btnOk.setWidth(80);
		  btnOk.setHeight(30); 
		  btnOk.setxCoordinate(0);
		  btnOk.setyCoordinate(0); 
		  btnOk.setDefaultColors(new Color(panel.getDisplay(), 152, 210, 227), new Color(panel.getDisplay(), 211, 217, 223) , null, null);
		  btnOk.setClickedColors(new Color(panel.getDisplay(), 152, 210, 227), new Color(panel.getDisplay(), 211, 217, 223) , null, null);
		  btnOk.setHoverColors(new Color(panel.getDisplay(), 152, 210, 227), new Color(panel.getDisplay(), 211, 217, 223) , null, null);
		  btnOk.setSelectedColors(new Color(panel.getDisplay(), 152, 210, 227), new Color(panel.getDisplay(), 211, 217, 223) , null, null);
		  btnOk.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 12, SWT.BOLD )); 
		  btnOk.addListener(SWT.Selection, okListener);
		  
		  	Composite compositeBtnCancel = new Composite(shell, SWT.None); 
		  	compositeBtnCancel.setLayout(new GridLayout(1,false)); 
			grid = new GridData(); 
			grid.widthHint = 90;
			grid.heightHint = 40;  
			compositeBtnCancel.setLayoutData(grid);
		  
		  GeneralButton	btnCancel = new GeneralButton(compositeBtnCancel, SWT.None); 
		  btnCancel.setText("Cancel"); 
		  btnCancel.setWidth(80);
		  btnCancel.setHeight(30); 
		  btnCancel.setxCoordinate(0);
		  btnCancel.setyCoordinate(0); 
		  btnCancel.setDefaultColors(new Color(panel.getDisplay(), 152, 210, 227), new Color(panel.getDisplay(), 211, 217, 223) , null, null);
		  btnCancel.setClickedColors(new Color(panel.getDisplay(), 152, 210, 227), new Color(panel.getDisplay(), 211, 217, 223) , null, null);
		  btnCancel.setHoverColors(new Color(panel.getDisplay(), 152, 210, 227), new Color(panel.getDisplay(), 211, 217, 223) , null, null);
		  btnCancel.setSelectedColors(new Color(panel.getDisplay(), 152, 210, 227), new Color(panel.getDisplay(), 211, 217, 223) , null, null);
		  btnCancel.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 12, SWT.BOLD )); 
		  btnCancel.addListener(SWT.Selection, cancelListener); 

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
		shell.dispose(); 
		shadow.dispose(); 
	}

	@Override
	public HashMap<String, String> getInput() {
		// TODO Auto-generated method stub
		HashMap<String, String> Input = new HashMap<String,String>(); 
		
	if(service.RequireTFSDomain)
	{
			Input.put("Domain", (InterceptingFilter.verifyText(textDomain) ? textDomain.getText() : null).toString() );
	}
	else
	{
		Input.put("Domain", null );
	}
			Input.put("Username", (InterceptingFilter.verifyText(textUsername) ? textUsername.getText() : null).toString()); 
			Input.put("Password", (InterceptingFilter.verifyText(textPassword) ? textPassword.getText() : null).toString()); 
		return Input;
	}

	
	
	public Listener getOkListener() {
		return okListener;
	}

	public void setOkListener(Listener okListener) {
		this.okListener = okListener;
	}

	public Listener getCancelListener() {
		return cancelListener;
	}

	public void setCancelListener(Listener cancelListener) {
		this.cancelListener = cancelListener;
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

	public WService getService() {
		return service;
	}

	public void setService(WService service) {
		this.service = service;
	}
	
	

}
