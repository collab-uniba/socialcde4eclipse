package it.uniba.di.collab.socialcdeforeclipse.popup;

import java.io.InputStream;
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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.object.GeneralButton;
import it.uniba.di.collab.socialcdeforeclipse.object.Panel;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.WOAuthData;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.WService;

public class ConnLostPanel implements Panel {

	private Shell shell;
	private Shell shadow;
	private int xCoordinate;
	private int yCoordinate;
	private int xCoordinateWithOffset;
	private int yCoordinateWithOffset;
	private Listener okListener;
	private String message; 
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Listener getOkListener() {
		return okListener;
	}

	public void setOkListener(Listener okListener) {
		this.okListener = okListener;
	}

	private final InputStream PATH_ECLIPSE_ICON = this.getClass()
			.getClassLoader().getResourceAsStream("icons/sample.gif");

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

	private Image getImageStream(InputStream stream) {
		return new Image(Controller.getWindow().getDisplay(), stream);
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

		shadow = new Shell(panel.getShell(), SWT.NO_TRIM);
		shadow.setSize(Controller.getWindowWidth(),
				Controller.getWindowHeight());
		shadow.setBounds(xCoordinate, yCoordinate, Controller.getWindowWidth(),
				Controller.getWindowHeight());
		shadow.setLayout(new GridLayout(1, false));
		shadow.setAlpha(100);
		shadow.layout();
		shadow.open();

		shell = new Shell(panel.getShell(), SWT.TITLE);
		shell.setSize(300, 200);

		shell.setBounds(xCoordinateWithOffset, yCoordinateWithOffset, 300, 100);
		shell.setImage(getImageStream(PATH_ECLIPSE_ICON));

		GridLayout layout = new GridLayout(1, false);
		shell.setLayout(layout);

		Composite firstComposite = new Composite(shell, SWT.None);
		firstComposite.setLayout(new GridLayout(1, false));
		firstComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		firstComposite.setBackground(new Color(Display.getCurrent(), 255, 255,
				255));

		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = gridData.FILL;
		gridData.verticalAlignment = gridData.FILL;
		firstComposite.setLayoutData(gridData);

		Label message = new Label(firstComposite, SWT.BOLD); 
		//message.setText("Connection lost!\n you will be redirected to login page."); 
		message.setText(getMessage()); 
		message.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 10, SWT.BOLD));
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.CENTER;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.minimumWidth = 150; 
		gridData.heightHint = 50;
		message.setLayoutData(gridData); 
		
		Button btnOk = new Button(firstComposite, SWT.None); 
		btnOk.setText("Ok"); 
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.CENTER;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.heightHint = 30; 
		gridData.widthHint = 90; 
		btnOk.setLayoutData(gridData);
		
		btnOk.addListener(SWT.Selection, okListener);
		

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
