package it.uniba.di.socialcdeforeclipse.staticView;

import java.io.InputStream;

import it.uniba.di.socialcdeforeclipse.controller.Controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

public class ProgressBarThread extends Thread {

	private Display display;
	private ProgressBar bar;
	private int max;
	private Shell shell;
	private int stop = 0;
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

	public void setStop(int stop) {
		this.stop = stop;
	}

	public void run() {
		display = new Display();

		shell = new Shell(display, SWT.NO_TRIM | SWT.ON_TOP);
		System.out.println("Utilizzo dimensioni " + Controller.getWindowWidth() + " altezza " + Controller.getWindowHeight()); 
		shell.setSize(Controller.getWindowWidth(), Controller.getWindowHeight());

		shell.setBounds(xCoordinate, yCoordinate, Controller.getWindowWidth(),
				Controller.getWindowHeight());
		GridLayout layout = new GridLayout(1, false);
		shell.setLayout(layout);
		//shell.setBackgroundImage(resize(getImageStream(PATH_WALLPAPER),Controller.getWindowWidth(), Controller.getWindowHeight()));
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		shell.setBackground(new Color(Display.getCurrent(),255,255,255)); 
		
		
		Composite first_composite = new Composite(shell,   SWT.NO_BACKGROUND);
		first_composite.setLayout(layout); 
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true; 
		gridData.horizontalAlignment = gridData.CENTER;
		gridData.verticalAlignment = gridData.CENTER; 
		first_composite.setLayoutData(gridData);

		Label labelPogress = new Label(first_composite, SWT.CENTER);
		labelPogress.setText(labelTxt);
		labelPogress.setFont(new Font(shell.getDisplay(), "Calibri", 15,
				SWT.BOLD));
		GridData grid = new GridData();
		grid.horizontalAlignment = grid.CENTER;
		labelPogress.setLayoutData(grid);
		bar = new ProgressBar(first_composite, SWT.CENTER);
		max = 100; 
		shell.open();
		while (stop != 1) {
			try {
				Thread.sleep(100);
			} catch (Throwable th) {
			}

			if (bar.getSelection() == (max - 1)) {
				bar.setSelection(0);
			} else {
				bar.setSelection(bar.getSelection() + 1);
				display.update();
			}
		}
		if (stop == 1) {
			shell.dispose();
		}
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}