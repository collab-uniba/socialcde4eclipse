package it.uniba.di.collab.socialcdeforeclipse.popup;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.object.GeneralButton;
import it.uniba.di.collab.socialcdeforeclipse.object.Panel;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.WUser;

public class SkillsPanel implements Panel {

	private Shell shadow;
	private Shell shell;
	private int xCoordinate;
	private int yCoordinate;
	private int xCoordinateWithOffset;
	private int yCoordinateWithOffset;
	private WUser user_selected;
	private Listener backListener;
	private Composite firstComposite; 

	@Override
	public void inizialize(Composite panel) {
		// TODO Auto-generated method stub

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
		shell.setBackground(new Color(Display.getCurrent(), 255, 255, 255));
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		shell.setLayoutData(gridData);

		final ScrolledComposite sc = new ScrolledComposite(shell, SWT.None
				| SWT.V_SCROLL );
		sc.setLayout(new GridLayout(1, false));
		sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		firstComposite = new Composite(sc, SWT.None);
		firstComposite.setLayout(new GridLayout(1, false));
		firstComposite.setBackground(new Color(Display.getCurrent(), 255,
				255, 255));
		firstComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		Label labelSkills = new Label(firstComposite, SWT.WRAP);
		labelSkills.setText("Skills");
		labelSkills.setFont(new Font(shell.getDisplay(), "Calibri", 12,
				SWT.BOLD));
		gridData = new GridData();
		gridData.widthHint = 300;
		labelSkills.setLayoutData(gridData);

		String[] skills = Controller.getProxy().GetSkills(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword(), user_selected.Username);

		if (skills.length > 20) {
			sc.addControlListener(new ControlListener() {

				@Override
				public void controlResized(ControlEvent e) {
					// TODO Auto-generated method stub

					Rectangle r = sc.getClientArea();
					sc.setMinSize(shell.computeSize(r.width, SWT.DEFAULT));

				}

				@Override
				public void controlMoved(ControlEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}
		
		if (skills.length > 0) {
			String stringSkills = "";

			for (int i = 0; i < skills.length; i++) {
				if (i == skills.length - 1) {
					stringSkills += skills[i];
				} else {
					stringSkills += skills[i] + ", ";
				}
			}

			Label skillsFounded = new Label(firstComposite, SWT.WRAP );
			gridData = new GridData();
			gridData.widthHint = 250;
			skillsFounded.setLayoutData(gridData);
			skillsFounded.setText(stringSkills);

		} else {
			Label noSkills = new Label(firstComposite, SWT.WRAP);
			gridData = new GridData();
			gridData.widthHint = 250;
			noSkills.setLayoutData(gridData); 
			noSkills.setText("There are no skills or there are no \"Get your skills\" \n feature selected.\n Try again later.");
		}

		Composite secondComposite = new Composite(firstComposite, SWT.None); 
		secondComposite.setLayout(new GridLayout(1,false)); 
		gridData = new GridData(); 
		gridData.widthHint = 100; 
		gridData.heightHint = 100; 
		gridData.horizontalAlignment = GridData.CENTER; 
		secondComposite.setLayoutData(gridData); 
		
		GeneralButton btnBack = new GeneralButton(secondComposite, SWT.None);
		btnBack.setText("Back");
		btnBack.setWidth(80);
		btnBack.setHeight(30);
		btnBack.setxCoordinate(0);
		btnBack.setyCoordinate(0);
		btnBack.setDefaultColors(new Color(panel.getDisplay(), 179, 180, 168),
				new Color(panel.getDisplay(), 179, 180, 168), null, null);
		btnBack.setClickedColors(new Color(panel.getDisplay(), 179, 180, 168),
				new Color(panel.getDisplay(), 179, 180, 168), null, null);
		btnBack.setHoverColors(new Color(panel.getDisplay(), 179, 180, 168),
				new Color(panel.getDisplay(), 179, 180, 168), null, null);
		btnBack.setSelectedColors(new Color(panel.getDisplay(), 179, 180, 168),
				new Color(panel.getDisplay(), 179, 180, 168), null, null);
		btnBack.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 12, SWT.BOLD));
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
		
		sc.setContent(firstComposite);
		sc.setMinSize(265, 165);

		// Expand both horizontally and vertically
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);

		sc.layout();

		shell.layout();
		shell.open();

	}

	public WUser getUser_selected() {
		return user_selected;
	}

	public void setUser_selected(WUser user_selected) {
		this.user_selected = user_selected;
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
	public HashMap<String, Object> getData() {
		// TODO Auto-generated method stub
		return null;
	}

}
