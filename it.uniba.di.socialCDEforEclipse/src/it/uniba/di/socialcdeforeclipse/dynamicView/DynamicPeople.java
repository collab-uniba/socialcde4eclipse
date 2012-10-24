package it.uniba.di.socialcdeforeclipse.dynamicView;

import it.uniba.di.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.WUser;
import it.uniba.di.socialcdeforeclipse.views.ButtonPerson;
import it.uniba.di.socialcdeforeclipse.views.Panel;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

public class DynamicPeople implements Panel{

	private final InputStream PATH_DEFAULT_AVATAR = this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png");
	
	private ArrayList<Control> controlli;
	 
	private Composite combo;
	
	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Controller.getWindow().getDisplay().getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0,
		image.getBounds().width, image.getBounds().height,
		0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
		}
	
	public Image getImageStream(InputStream stream)
	{
		return  new Image(Controller.getWindow().getDisplay(),stream); 
	}
	
	@Override
	public void inizialize(Composite panel2) {
		// TODO Auto-generated method stub
		/*
		panel.setLayout(new GridLayout(1,false)); 
		panel.setBackgroundMode(SWT.INHERIT_DEFAULT);
		Composite panel2 = new Composite(panel, SWT.None); 
		*/
		GridData grid = new GridData(); 
		
		
		controlli = new ArrayList<Control>();
		Listener azioni = new ActionGeneral();
		panel2.setLayout(new GridLayout(3, true));
		
		
		 
		Label labelSuggestion = new Label(panel2, SWT.NONE); 
		labelSuggestion.setText("Suggestions:"); 
		labelSuggestion.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 14, SWT.BOLD )); 
		grid = new GridData(); 
		grid.horizontalSpan = 3; 
		grid.grabExcessHorizontalSpace = true;
		grid.horizontalAlignment = grid.FILL; 
		labelSuggestion.setLayoutData(grid); 
		
		
		WUser[] suggestion = Controller.getProxy().GetSuggestedFriends(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		//WUser[] suggestion = Controller.getProxy().GetFollowings(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword());
		if(suggestion.length == 0)
		{
			Label labelsuggestionText = new Label(panel2, SWT.WRAP); 
			labelsuggestionText.setText("We have no suggestion for you.\n Please try again soon."); 
			grid = new GridData(); 
			grid.horizontalSpan = 3; 
			grid.grabExcessHorizontalSpace = true;
			grid.horizontalAlignment = grid.FILL; 
			 
			
			labelsuggestionText.setLayoutData(grid);
			
		}
		else
		{
			System.out.println("Persone suggerite " + suggestion.length);
			 
			
			for (int i = 0; i < suggestion.length ; i++) {

				 combo = new Composite(panel2, SWT.BORDER); 
				 combo.setLayout(new GridLayout(1,false)); 
				combo.setLayoutData(new GridData(100, 120)); 
				
				
				ButtonPerson person = new ButtonPerson(combo, SWT.BORDER); 
				person.setxCoordinate(0); 
				 
				person.setyCoordinate(0); 
				person.setWidth(100);
				person.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.BOLD )); 
				person.setHeight(120); 
			
				person.setDefaultColors(new Color(Controller.getWindow().getDisplay(), 81,179,225), new Color(Controller.getWindow().getDisplay(), 81,179,225), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)); 
				person.setHoverColors(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				person.setClickedColors(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				person.setSelectedColors(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				try {
					person.setImage(getImageStream(new URL(suggestion[1].Avatar).openStream()));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				person.setText(suggestion[1].Username);
				
				
				 
			}
		}
		
		Label labelFollowings = new Label(panel2, SWT.NONE); 
		labelFollowings.setText("Followings:"); 
		labelFollowings.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 14, SWT.BOLD )); 
		grid.horizontalSpan = 3; 
		labelFollowings.setLayoutData(grid); 
		 
		WUser[] following = Controller.getProxy().GetFollowings(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		
		if(following.length == 0)
		{
			Label labelFollowingText = new Label(panel2, SWT.WRAP); 
			labelFollowingText.setText("You are following no one."); 
			grid = new GridData(); 
			grid.horizontalSpan = 3; 
			labelFollowingText.setLayoutData(grid);
			
		}
		else
		{
			
			System.out.println("Persone suggerite " + following.length);
			
			for (int i = 0; i < following.length; i++) {

				 combo = new Composite(panel2, SWT.None); 
				 combo.setLayout(new GridLayout(1,false)); 
				grid = new GridData(); 
				grid.widthHint = 100;
				grid.heightHint = 120;  
				combo.setLayoutData(grid);
				
				ButtonPerson person = new ButtonPerson(combo, SWT.BORDER); 
				person.setxCoordinate(0); 
				person.setyCoordinate(0); 
				person.setWidth(100);
				person.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.BOLD )); 
				person.setHeight(120); 
			
				person.setDefaultColors(new Color(Controller.getWindow().getDisplay(), 81,179,225), new Color(Controller.getWindow().getDisplay(), 81,179,225), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)); 
				person.setHoverColors(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				person.setClickedColors(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				person.setSelectedColors(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				try {
					person.setImage(getImageStream(new URL(following[i].Avatar).openStream()));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				person.setText(following[i].Username);
			}
		}
		
		
		WUser[] followers = Controller.getProxy().GetFollowers(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		
		
	}

	@Override
	public void dispose(Composite panel) {
		// TODO Auto-generated method stub
		/*
		for(int i=0; i < controlli.size();i++)
		{
		 controlli.get(i).dispose(); 
			
		}
		panel.setLayout(null); 
		*/
	}

	@Override
	public HashMap<String, String> getInput() {
		// TODO Auto-generated method stub
		return null;
	}

}
