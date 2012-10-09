/**
 * 
 */
package it.di.uniba.di.socialcdeforeclipse.popup;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import it.uniba.di.socialCDEforEclipse.SharedLibrary.WService;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.views.Panel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author Flo
 * 
 */
public class PinPanel extends Thread{

	private Display display;
	private Shell shell;
	private int xCoordinate;
	private int yCoordinate;
	private WService service; 
	private int oauthVersion; 
	private Label labelRegistration; 
	private Label labelVerifierPin; 
	private Text txtPin; 
	private Label labelInformation; 
	private Composite buttonComposite; 
	private Button btnOk; 
	private Button btnCancel; 
	private Listener okListener; 
	private Listener cancelListener; 
	private final InputStream PATH_WALLPAPER = this.getClass().getClassLoader().getResourceAsStream("images/Wallpaper.png");
	private final InputStream PATH_ECLIPSE_ICON = this.getClass().getClassLoader().getResourceAsStream("icons/sample.gif"); 
	
	
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
	
	private Image getImageStream(InputStream stream)
	{
		return  new Image(Controller.getWindow().getDisplay(),stream); 
	}


	@Override
	public void run(){
		// TODO Auto-generated method stub
	    display = new Display();
		shell = new Shell(display,SWT.NO_REDRAW_RESIZE);
		shell.setSize(300,100);

		shell.setBounds(xCoordinate, yCoordinate,300,200);
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		shell.setImage(getImageStream(PATH_ECLIPSE_ICON)); 
		shell.setBackgroundImage(resize(getImageStream(PATH_WALLPAPER),300, 200));
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = gridData.FILL;
		shell.setLayoutData(gridData);

		labelRegistration = new Label(shell, SWT.WRAP | SWT.NO_BACKGROUND);
		labelRegistration.setText("Registration of service : " + service.Name);
		labelRegistration.setFont(new Font(shell.getDisplay(), "Calibri", 15, SWT.BOLD));
		gridData = new GridData(); 
		gridData.horizontalSpan = 2; 
		gridData.widthHint = 300; 
		labelRegistration.setLayoutData(gridData); 
		
		if(oauthVersion == 1)
		{
			labelVerifierPin = new Label(shell, SWT.RIGHT); 
			labelVerifierPin.setText("Verifier Pin"); 
			labelVerifierPin.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.NONE ));
			
			txtPin = new Text(shell, SWT.BORDER | SWT.WRAP); 
			txtPin.setData("ID_action","txtPin");
			gridData = new GridData();
			gridData.horizontalAlignment = SWT.FILL;
			gridData.grabExcessHorizontalSpace = true;
			gridData.minimumWidth = 120; 
			txtPin.setLayoutData(gridData);
		}
		else
		{
			labelInformation = new Label(shell, SWT.WRAP); 
			labelInformation.setText("Please authorize the application and click OK before closing the browser"); 
			gridData = new GridData(); 
			gridData.horizontalSpan = 2; 
			gridData.horizontalAlignment = SWT.CENTER; 
			gridData.widthHint = 300; 
			labelInformation.setLayoutData(gridData); 
			
			System.out.println("Dimensioni label " + labelInformation.getBounds()); 
		}
	
		
		
		buttonComposite = new Composite(shell, SWT.NONE); 
		buttonComposite.setLayout(new GridLayout(2,true));
		gridData = new GridData(); 
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = SWT.CENTER; 
		buttonComposite.setLayoutData(gridData); 
		
		btnOk = new Button(buttonComposite, SWT.NONE); 
		btnOk.setText("Ok"); 
		
		btnCancel = new Button(buttonComposite, SWT.None); 
		btnCancel.setText("Cancel"); 
		
		btnOk.addListener(SWT.Selection, okListener); 
		btnCancel.addListener(SWT.Selection, cancelListener); 
		
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
	
	

	

	public Text getTxtPin() {
		return txtPin;
	}

	public void setTxtPin(Text txtPin) {
		this.txtPin = txtPin;
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

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	

	public Button getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(Button btnCancel) {
		this.btnCancel = btnCancel;
	}

	

	public WService getService() {
		return service;
	}

	public void setService(WService service) {
		this.service = service;
	}

	public int getOauthVersion() {
		return oauthVersion;
	}

	public void setOauthVersion(int oauthVersion) {
		this.oauthVersion = oauthVersion;
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

	

}
