/**
 * 
 */
package it.uniba.di.socialcdeforeclipse.popup;

import java.util.HashMap;

import it.uniba.di.socialCDEforEclipse.SharedLibrary.WService;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.views.Panel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Flo
 *
 */
public class SettingServicePanel implements Panel{

	private Shell shadow; 
	private Shell shell; 
	private int xCoordinate; 
	private int yCoordinate;
	private WService service; 
	private int xCoordinateWithOffset; 
	private int yCoordinateWithOffset; 
	
	public int getxCoordinate() {
		return xCoordinate;
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



	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}


	public int getyCoordinate() {
		return yCoordinate;
	}


	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}


	public WService getService() {
		return service;
	}


	public void setService(WService service) {
		this.service = service;
	}


	

	@Override
	public void inizialize(Composite panel) {
		shadow = new Shell(panel.getShell(),SWT.NO_TRIM);
		shadow.setSize(Controller.getWindowWidth(),Controller.getWindowHeight()); 
		shadow.setBounds(xCoordinate, yCoordinate, Controller.getWindowWidth(), Controller.getWindowHeight()); 
		shadow.setLayout(new GridLayout(1,false)); 
		shadow.setAlpha(100);
		shadow.layout(); 
		shadow.open(); 
		
		// TODO Auto-generated method stub
		shell = new Shell(panel.getShell(),SWT.NO_REDRAW_RESIZE);
		shell.setSize(300,100);

		shell.setBounds(xCoordinateWithOffset, yCoordinateWithOffset,300,200);
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout); 
		shell.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_CYAN)); 
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = gridData.FILL;
		shell.setLayoutData(gridData);

		Label labelRegistration = new Label(shell, SWT.WRAP | SWT.NO_BACKGROUND);
		labelRegistration.setText("Registration of service : " + service.Name);
		labelRegistration.setFont(new Font(shell.getDisplay(), "Calibri", 15, SWT.BOLD));
		gridData = new GridData(); 
		gridData.horizontalSpan = 2; 
		gridData.widthHint = 300; 
		labelRegistration.setLayoutData(gridData); 
		
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
	public HashMap<String, String> getInput() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
