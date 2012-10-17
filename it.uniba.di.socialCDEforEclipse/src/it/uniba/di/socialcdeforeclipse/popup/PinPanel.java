/**
 * 
 */
package it.uniba.di.socialcdeforeclipse.popup;

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
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
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
public class PinPanel implements Panel{

	private Shell shell;
	private Shell shadow; 
	private int xCoordinate;
	private int yCoordinate;
	private int xCoordinateWithOffset; 
	private int yCoordinateWithOffset; 
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

	@Override
	public void inizialize(Composite panel) {
		// TODO Auto-generated method stub
		GridData gridData; 
		
		shadow = new Shell(panel.getShell(),  SWT.NO_TRIM);
		shadow.setSize(Controller.getWindowWidth(),Controller.getWindowHeight()); 
		shadow.setBounds(xCoordinate, yCoordinate, Controller.getWindowWidth(), Controller.getWindowHeight()); 
		shadow.setLayout(new GridLayout(1,false)); 
		shadow.setAlpha(100);
		shadow.layout(); 
		shadow.open(); 
		
		
		shell = new Shell(panel.getShell(),SWT.ON_TOP | SWT.NO_TRIM  );
		shell.setSize(300,200);

		shell.setBounds(xCoordinateWithOffset, yCoordinateWithOffset,300,200);
		shell.setImage(getImageStream(PATH_ECLIPSE_ICON)); 
		
		GridLayout layout = new GridLayout(1, false);
		shell.setLayout(layout); 
		
		
		Composite firstComposite = new Composite(shell,SWT.None); 
		firstComposite.setLayout(new GridLayout(2,false)); 
		firstComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		firstComposite.setBackgroundImage(resize(getImageStream(PATH_WALLPAPER),300, 200));
		
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true; 
		gridData.horizontalAlignment = gridData.FILL;
		gridData.verticalAlignment = gridData.FILL; 
		firstComposite.setLayoutData(gridData);
		
		labelRegistration = new Label(firstComposite, SWT.WRAP | SWT.INHERIT_DEFAULT );
		labelRegistration.setText("Registration of service : " + service.Name);
		labelRegistration.setFont(new Font(shell.getDisplay(), "Calibri", 15, SWT.BOLD));
		
		gridData = new GridData(); 
		gridData.horizontalSpan = 2; 
		gridData.widthHint = 300; 
		labelRegistration.setLayoutData(gridData); 
		
		if(oauthVersion == 1)
		{
			labelVerifierPin = new Label(firstComposite, SWT.RIGHT); 
			labelVerifierPin.setText("Verifier Pin"); 
			labelVerifierPin.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.NONE ));
			
			txtPin = new Text(firstComposite, SWT.BORDER | SWT.WRAP); 
			txtPin.setData("ID_action","txtPin");
			gridData = new GridData();
			gridData.horizontalAlignment = SWT.FILL;
			gridData.grabExcessHorizontalSpace = true;
			gridData.minimumWidth = 120; 
			txtPin.setLayoutData(gridData);
		}
		else
		{
			labelInformation = new Label(firstComposite, SWT.WRAP ); 
			labelInformation.setText("Please authorize the application and click OK before closing the browser"); 
			gridData = new GridData(); 
			gridData.horizontalSpan = 2; 
			gridData.horizontalAlignment = SWT.CENTER; 
			gridData.widthHint = 300; 
			labelInformation.setLayoutData(gridData); 
			
			System.out.println("Dimensioni label " + labelInformation.getBounds()); 
		}
	
		
		
		buttonComposite = new Composite(firstComposite, SWT.None); 
		buttonComposite.setLayout(new GridLayout(2,true));
		gridData = new GridData(); 
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = SWT.CENTER; 
		buttonComposite.setLayoutData(gridData); 
		
		btnOk = new Button(buttonComposite, SWT.None); 
		btnOk.setText("Ok"); 
		
		btnCancel = new Button(buttonComposite, SWT.None); 
		btnCancel.setText("Cancel"); 
		
		btnOk.addListener(SWT.Selection, okListener); 
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
		return null;
	}

	

}