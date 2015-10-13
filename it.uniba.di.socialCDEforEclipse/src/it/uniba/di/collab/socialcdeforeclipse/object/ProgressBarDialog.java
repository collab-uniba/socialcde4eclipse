package it.uniba.di.collab.socialcdeforeclipse.object;

import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;

import java.io.InputStream;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

public class ProgressBarDialog extends Dialog {
	
	private ProgressBar bar;
	private String labelTxt = "";

	public Image getImageStream(InputStream stream) {
		return new Image(Controller.getWindow().getDisplay(), stream);

	}


	public ProgressBarDialog(Shell parentShell) {
		super(parentShell);

		setShellStyle(SWT.NO_TRIM);
	}

	
	@Override
	protected Control createDialogArea(Composite parent) {
		GridLayout layout = new GridLayout(1, false);
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(layout);
		
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = gridData.CENTER;
		gridData.verticalAlignment = gridData.CENTER;
		
		container.setLayoutData(gridData);
		
	    Label label = new Label(container, SWT.CENTER);
	    label.setText(getLabelTxt());

		bar = new ProgressBar(container, SWT.CENTER);

	    return container;
	}

	protected Button createButton(Composite arg0, int arg1, String arg2,
			boolean arg3) {
		
		// Retrun null so that no default buttons like 'OK' and 'Cancel' will be
		// created
		return null;
	}

	// overriding this methods allows you to set the
	// title of the custom dialog
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setBounds(Controller.getProgressBarPositionX(),
				Controller.getProgressBarPositionY(),
				Controller.getWindowWidth(), Controller.getWindowHeight());
	}

	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	public ProgressBar getBar() {
		return bar;
	}
	
	public String getLabelTxt() {
		return labelTxt;
	}

	public void setLabelTxt(String labelTxt) {
		this.labelTxt = labelTxt;
	}
}