/**
 * 
 */
package it.uniba.di.socialcdeforeclipse.popup;

import it.uniba.di.socialCDEforEclipse.SharedLibrary.WService;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Flo
 *
 */
public class SettingServicePanel extends Thread {

	private Display display; 
	private Shell shell; 
	private int xCoordinate; 
	private int yCoordinate;
	private WService service; 
	
	
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


	public WService getService() {
		return service;
	}


	public void setService(WService service) {
		this.service = service;
	}


	public void run()
	{
		display = new Display();
		shell = new Shell(display,SWT.NO_REDRAW_RESIZE);
		shell.setSize(300,100);

		shell.setBounds(xCoordinate, yCoordinate,300,200);
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
		
		
		  while (!shell.isDisposed()) {
		      if (!display.readAndDispatch()) {
		        // If no more entries in event queue
		        display.sleep();
		      }
		    }

		    display.dispose();
	}
	
	
}
