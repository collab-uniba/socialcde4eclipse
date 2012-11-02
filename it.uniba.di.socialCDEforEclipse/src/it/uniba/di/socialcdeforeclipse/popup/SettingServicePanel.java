/**
 * 
 */
package it.uniba.di.socialcdeforeclipse.popup;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.*;
import it.uniba.di.socialcdeforeclipse.views.GeneralButton;
import it.uniba.di.socialcdeforeclipse.views.Panel;
import it.uniba.di.socialcdeforeclipse.views.SquareButtonService;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.util.ImageSupport;

/**
 * @author Flo
 * 
 */
public class SettingServicePanel implements Panel {

	private Shell shadow;
	private Shell shell;
	private int xCoordinate;
	private int yCoordinate;
	private WService service;
	private int xCoordinateWithOffset;
	private int yCoordinateWithOffset;
	private Listener btnSaveListener;
	private Listener btnUnsubscriveListener;
	private ArrayList<Button> checkboxCreated; 

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
	
	

	public ArrayList<Button> getCheckboxCreated() {
		return checkboxCreated;
	}

	public void setCheckboxCreated(ArrayList<Button> checkboxCreated) {
		this.checkboxCreated = checkboxCreated;
	}

	public Listener getBtnSaveListener() {
		return btnSaveListener;
	}

	public void setBtnSaveListener(Listener btnSaveListener) {
		this.btnSaveListener = btnSaveListener;
	}

	public Listener getBtnUnsubscriveListener() {
		return btnUnsubscriveListener;
	}

	public void setBtnUnsubscriveListener(Listener btnUnsubscriveListener) {
		this.btnUnsubscriveListener = btnUnsubscriveListener;
	}

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

		Image imgWallpaper = resize(getImageStream(PATH_WALLPAPER), 340, 200);

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
		shell.setBackground(new Color(Display.getCurrent(),249,250,237));
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = gridData.FILL;
		shell.setLayoutData(gridData);

		Label labelRegistration = new Label(shell, SWT.WRAP);
		labelRegistration.setText("Registration of service : " + service.Name);
		labelRegistration.setFont(new Font(shell.getDisplay(), "Calibri", 12,
				SWT.BOLD));
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.widthHint = 300;
		labelRegistration.setLayoutData(gridData);
		
		InputStream urlImageService = null;
		try {
			urlImageService = new URL(Controller.getPreferences("ProxyRoot")
					+ service.Image).openStream();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		final Image imgService = getImageStream(urlImageService);
		
		
		
		 Canvas btnImgServce = new Canvas(shell, SWT.None);
		 btnImgServce.setBackground(new Color(Display.getCurrent(), 230, 230, 223)); 
		 
		 
		 btnImgServce.addPaintListener(new PaintListener() {
		      public void paintControl(PaintEvent e) {
		      
		        e.gc.drawImage(imgService, 5, 5);
		        
		       
		      
		      }
		    });

	
		System.out.println("Dimensioni btnImgService " + imgService.getBounds()); 
		
		gridData = new GridData();
		gridData.verticalSpan = 4;
		btnImgServce.setLayoutData(gridData);
		
		WFeature[] featuresService = Controller.getProxy().GetChosenFeatures(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword(), service.Id);

		checkboxCreated = new ArrayList<Button>();
		
		for (int i = 0; i < featuresService.length; i++) {
			 
			Button featureService = new Button(shell, SWT.CHECK);
			featureService.setText(featuresService[i].Description);
			featureService.setData("FeatureName", featuresService[i].Name ); 
			if (featuresService[i].IsChosen) {
				featureService.setSelection(true);
				 
			}
			checkboxCreated.add(featureService);
			
		}

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

		Label labelHidden = new Label(shell, SWT.NONE);
		labelHidden.setText("");
		labelHidden.setVisible(false);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		labelHidden.setLayoutData(gridData);

		GeneralButton btnUnsubscribe = new GeneralButton(shell, SWT.None); 
		btnUnsubscribe.setText("Unsubcribe"); 
		btnUnsubscribe.setWidth(90);
		btnUnsubscribe.setHeight(30); 
		btnUnsubscribe.setxCoordinate(5);
		btnUnsubscribe.setyCoordinate(133); 
		btnUnsubscribe.setDefaultColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
		btnUnsubscribe.setClickedColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
		btnUnsubscribe.setHoverColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
		btnUnsubscribe.setSelectedColors(new Color(panel.getDisplay(), 179, 180, 168), new Color(panel.getDisplay(), 179, 180, 168) , null, null);
		btnUnsubscribe.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 12, SWT.BOLD )); 
		btnUnsubscribe.addListener(SWT.Selection, btnUnsubscriveListener);
		
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
