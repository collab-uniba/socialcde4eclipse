package it.uniba.di.collab.socialcdeforeclipse.object;

import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;

import java.io.InputStream;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

public class ProgressBarWindow implements Panel{
	private ProgressBar bar;
	private int max;
	private Shell shell;
	
	private String labelTxt;
	private int xCoordinate;
	private int xCoordinateWithOffset;
	private int yCoordinate;
	private int yCoordinateWithOffset;
	

	private final InputStream PATH_WALLPAPER = this.getClass().getClassLoader()
			.getResourceAsStream("images/Wallpaper.png");

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

	public String getLabelTxt() {
		return labelTxt;
	}

	public void setLabelTxt(String labelTxt) {
		this.labelTxt = labelTxt;
	}




	@Override
	public void inizialize(Composite panel) {
		// TODO Auto-generated method stub

		shell = new Shell(panel.getShell(), SWT.NO_TRIM | SWT.ON_TOP);
		//shell.setBackgroundMode(SWT.INHERIT_DEFAULT); 
		shell.setSize(Controller.getWindowWidth(), Controller.getWindowHeight());

		shell.setBounds(Controller.getProgressBarPositionX(),
				Controller.getProgressBarPositionY(),
				Controller.getWindowWidth(), Controller.getWindowHeight());
		GridLayout layout = new GridLayout(1, false);
		shell.setLayout(layout);
		// shell.setBackgroundImage(resize(getImageStream(PATH_WALLPAPER),Controller.getWindowWidth(),
		// Controller.getWindowHeight()));
		
		shell.setBackground(new Color(Display.getCurrent(), 255, 255, 255));

		Composite first_composite = new Composite(shell, SWT.None);
		first_composite.setLayout(layout);
		first_composite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)); 
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.CENTER;
		gridData.verticalAlignment = GridData.CENTER;
		first_composite.setLayoutData(gridData);

		Label labelPogress = new Label(first_composite, SWT.CENTER);
		labelPogress.setText(labelTxt);
		labelPogress.setFont(new Font(shell.getDisplay(), "Calibri", 15,
				SWT.BOLD));
		labelPogress.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)); 
		GridData grid = new GridData();
		grid.horizontalAlignment = GridData.CENTER;
		labelPogress.setLayoutData(grid);
		bar = new ProgressBar(first_composite, SWT.CENTER);
		bar.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		max = 100;
		bar.setSelection(0); 
		shell.open();
		
		
		
	}

	public void updateProgressBar()
	{
		int count = (bar.getSelection() == 100 ? 0 : bar.getSelection() + 10);
		bar.setSelection(count); 
		shell.update();
		shell.redraw(); 
	}
	
	@Override
	public void dispose(Composite panel) {
		// TODO Auto-generated method stub
		shell.dispose();
	}
	
	public boolean isDisposed()
	{
		return shell.isDisposed(); 
	}

	@Override
	public HashMap<String, Object> getData() {
		// TODO Auto-generated method stub
		return null;
	}

}
