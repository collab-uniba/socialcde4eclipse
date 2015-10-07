package it.uniba.di.collab.socialcdeforeclipse.object;

import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
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

public class ProgressBarThread extends Thread{

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
	
		display =  new Display();
		
		shell = new Shell(display);
		
		ProgressBarDialog pbd = new ProgressBarDialog(shell);
		pbd.setBlockOnOpen(false);
		pbd.setLabelTxt(getLabelTxt());
		pbd.open();
	
		max = 100;
		bar = pbd.getBar();

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
			pbd.close();
			shell.dispose();
			display.dispose();
		}
	}


}