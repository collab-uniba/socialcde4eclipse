package it.uniba.di.socialcdeforeclipse.popup;

import java.io.InputStream;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.WService;
import it.uniba.di.socialcdeforeclipse.views.GeneralButton;
import it.uniba.di.socialcdeforeclipse.views.Panel;

public class SkillsPanel implements Panel {

	private Shell shadow;
	private Shell shell;
	private int xCoordinate;
	private int yCoordinate;
	private int xCoordinateWithOffset;
	private int yCoordinateWithOffset;
	private Listener backListener; 
	
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
	
	@Override
	public void inizialize(Composite panel) {
		// TODO Auto-generated method stub
		Image imgWallpaper = resize(getImageStream(PATH_WALLPAPER), 300, 200);

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

		shell.setBounds(xCoordinateWithOffset, yCoordinateWithOffset, 300, 200);
		GridLayout layout = new GridLayout(1, false);
		shell.setLayout(layout);
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		shell.setBackgroundImage(imgWallpaper);
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = gridData.FILL;
		shell.setLayoutData(gridData);

		Label labelSkills = new Label(shell, SWT.WRAP);
		labelSkills.setText("Skills");
		labelSkills.setFont(new Font(shell.getDisplay(), "Calibri", 12,
				SWT.BOLD));
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.widthHint = 300;
		labelSkills.setLayoutData(gridData);
		
		String[] skills = Controller.getProxy().GetSkills(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), Controller.getCurrentUser().Username);
		
		if(skills.length > 0)
		{
			String stringSkills = ""; 
			
			for(int i=0;i<skills.length;i++)
			{
				if(i== skills.length -1)
				{
					stringSkills += skills[i]; 
				}
				else
				{
					stringSkills += skills[i] + ", "; 
				}
			}
			
			Label skillsFounded = new Label(shell, SWT.WRAP); 
			gridData = new GridData(); 
			gridData.widthHint = 200;
			gridData.heightHint = 120;
			skillsFounded.setLayoutData(gridData); 
			skillsFounded.setText(stringSkills); 
			
			
		}
		else
		{
			Label noSkills = new Label(shell, SWT.None); 
			noSkills.setText("There are no skills or there are no \"Get your skills\" feature selected.\n Try again later."); 
		}
		
	GeneralButton	btnBack = new GeneralButton(shell, SWT.None); 
		btnBack.setText("Back"); 
		btnBack.setWidth(80);
		btnBack.setHeight(30); 
		btnBack.setxCoordinate(116);
		btnBack.setyCoordinate(161); 
		btnBack.setDefaultColors(new Color(panel.getDisplay(), 152, 210, 227), new Color(panel.getDisplay(), 211, 217, 223) , null, null);
		btnBack.setClickedColors(new Color(panel.getDisplay(), 152, 210, 227), new Color(panel.getDisplay(), 211, 217, 223) , null, null);
		btnBack.setHoverColors(new Color(panel.getDisplay(), 152, 210, 227), new Color(panel.getDisplay(), 211, 217, 223) , null, null);
		btnBack.setSelectedColors(new Color(panel.getDisplay(), 152, 210, 227), new Color(panel.getDisplay(), 211, 217, 223) , null, null);
		btnBack.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 12, SWT.BOLD )); 
		btnBack.addListener(SWT.Selection, backListener);
		
	
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

	
	
	public Listener getBackListener() {
		return backListener;
	}

	public void setBackListener(Listener backListener) {
		this.backListener = backListener;
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
