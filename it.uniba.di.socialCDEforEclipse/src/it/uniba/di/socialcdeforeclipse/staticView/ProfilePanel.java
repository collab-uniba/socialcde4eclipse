package it.uniba.di.socialcdeforeclipse.staticView;

import it.uniba.di.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.views.Panel;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

public class ProfilePanel implements Panel{

	private Listener azioni; 
	private Label labelAvatar; 
	private Composite composite_static; 
	private Label labelPeople; 
	private Label labelHomeTimeline;
	private Label labelInteractionTimeline; 
	private Label labelInteractiveTimeline; 
	private Composite composite_dinamic; 
	private Label labelHidden;  
	private Label labelLogout; 
	private ArrayList<Control> controlli; 
	
	private final InputStream PATH_DEFAULT_AVATAR = this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png"); 
	private final InputStream PATH_BALOON = this.getClass().getClassLoader().getResourceAsStream("images/Baloon.png"); 
	private final InputStream PATH_BACK = this.getClass().getClassLoader().getResourceAsStream("images/Toolbar/Back.png"); 
	private final InputStream PATH_FOLLOW = this.getClass().getClassLoader().getResourceAsStream("images/Toolbar/Follow.png");
	private final InputStream PATH_HIDE = this.getClass().getClassLoader().getResourceAsStream("images/Toolbar/Hide.png");
	private final InputStream PATH_HOME = this.getClass().getClassLoader().getResourceAsStream("images/Toolbar/Home.png");
	private final InputStream PATH_INTERACTIVE_TIMELINE = this.getClass().getClassLoader().getResourceAsStream("images/Toolbar/InteractiveTimeline.png");
	private final InputStream PATH_INTERACTION_TIMELINE = this.getClass().getClassLoader().getResourceAsStream("images/Toolbar/IterationTimeline.png");
	private final InputStream PATH_LOGOUT = this.getClass().getClassLoader().getResourceAsStream("images/Toolbar/Logout.png");
	private final InputStream PATH_PEOPLE = this.getClass().getClassLoader().getResourceAsStream("images/Toolbar/People.png");
	private final InputStream PATH_WALLPAPER = this.getClass().getClassLoader().getResourceAsStream("images/Wallpaper.png");


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
	
	public Image get_ImageStream(InputStream stream)
	{
		return  new Image(Controller.getWindow().getDisplay(),stream); 
	}
	
	@Override
	public void inizialize(Composite panel) {
		// TODO Auto-generated method stub
		
		 azioni = new ActionGeneral();
		 controlli = new ArrayList<Control>();
		GridLayout layout = new GridLayout(1, false);
		
		panel.setLayout(layout);
		panel.setBackgroundImage(resize(get_ImageStream(PATH_WALLPAPER), Controller.getWindowWidth(), Controller.getWindowHeight())); 
		panel.setData("ID_action","profilePanel");
		
		GridLayout grid_static = new GridLayout(15,false);
		grid_static.makeColumnsEqualWidth = true; 
		
		composite_static = new Composite( panel, SWT.NONE );
		composite_static.setLayout( grid_static );
		GridData gridData = new GridData(); 
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = gridData.FILL; 
		composite_static.setLayoutData(gridData); 
		
		labelAvatar = new Label(composite_static, SWT.NONE);
		System.out.println("Proxy " + Controller.getProxy().getHost()); 
		System.out.println("Avatar" + Controller.getCurrentUser().Avatar); 
		System.out.println("Username " + Controller.getPreferences("Username").toString()); 
		System.out.println("Password " + Controller.getPreferences("Password").toString());
		System.out.println("Utente " + Controller.getCurrentUser().Id); 
		if(Controller.getCurrentUser().Avatar == null || Controller.getCurrentUser().Avatar.equals(""))
		{
			labelAvatar.setImage(get_ImageStream(PATH_DEFAULT_AVATAR)); 
			labelAvatar.setImage(resize(labelAvatar.getImage(), 14, 14));
		}
		else
		{
			try {
				labelAvatar.setImage(get_ImageStream(new URL(Controller.getCurrentUser().Avatar).openStream()));
				labelAvatar.setImage(resize(labelAvatar.getImage(), 14, 14)); 
				 
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Eccezione lanciata"); 
				labelAvatar.setImage(get_ImageStream(PATH_DEFAULT_AVATAR));
				labelAvatar.setImage(resize(labelAvatar.getImage(), 14, 14));
				//e.printStackTrace();
			} 
		}
		labelAvatar.setToolTipText("Profile"); 
		controlli.add(labelAvatar); 
		
		labelPeople = new Label(composite_static, SWT.PUSH);
		labelPeople.setImage(get_ImageStream(PATH_PEOPLE)); 
		labelPeople.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND)); 
		labelPeople.setToolTipText("People"); 
		controlli.add(labelPeople); 
		
		labelHomeTimeline = new Label(composite_static, SWT.PUSH);
		labelHomeTimeline.setImage(get_ImageStream(PATH_HOME)); 
		labelHomeTimeline.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND));
		labelHomeTimeline.setToolTipText("Home Timeline"); 
		controlli.add(labelHomeTimeline); 
		
		labelInteractionTimeline = new Label(composite_static,SWT.PUSH);
		labelInteractionTimeline.setImage(get_ImageStream(PATH_INTERACTION_TIMELINE)); 
		labelInteractionTimeline.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND));
		labelInteractionTimeline.setToolTipText("Interation Timeline"); 
		controlli.add(labelInteractionTimeline); 
		
		 labelInteractiveTimeline = new Label(composite_static,SWT.PUSH); 
		labelInteractiveTimeline.setImage(get_ImageStream(PATH_INTERACTIVE_TIMELINE)); 
		labelInteractiveTimeline.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND));
		labelInteractiveTimeline.setToolTipText("Interactive Timeline"); 
		controlli.add(labelInteractiveTimeline); 
		
		labelLogout = new Label(composite_static, SWT.TRAIL); 
		labelLogout.setImage(get_ImageStream(PATH_LOGOUT));
		labelLogout.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND));
		labelLogout.setData("ID_action", "labelLogout");
		labelLogout.setToolTipText("Logout"); 
		 gridData = new GridData(); 
		gridData.grabExcessHorizontalSpace = true; 
		labelLogout.setLayoutData(gridData); 
		
		controlli.add(labelLogout);
		
		
		
		composite_static.layout(); 
		
		Controller.selectDynamicWindow(0); 
		
		controlli.add(composite_static);
		controlli.add(composite_dinamic); 

		panel.layout(); 
		
		panel.addListener(SWT.Resize, azioni); 
		labelLogout.addListener(SWT.PUSH, azioni); 
		
	}

	@Override
	public void dispose(Composite panel) {
		// TODO Auto-generated method stub
		for(int i=0; i < controlli.size();i++)
		{
			controlli.get(i).dispose(); 
			
		}
		panel.setLayout(null); 
	}

	@Override
	public HashMap<String, String> getInput() {
		// TODO Auto-generated method stub
		return null; 
	}

	public Composite getComposite_static() {
		return composite_static;
	}

	public void setComposite_static(Composite composite_static) {
		this.composite_static = composite_static;
	}

	public Label getLabelPeople() {
		return labelPeople;
	}

	public void setLabelPeople(Label labelPeople) {
		this.labelPeople = labelPeople;
	}

	public Label getLabelHomeTimeline() {
		return labelHomeTimeline;
	}

	public void setLabelHomeTimeline(Label labelHomeTimeline) {
		this.labelHomeTimeline = labelHomeTimeline;
	}

	public Label getLabelInteractiveTimeline() {
		return labelInteractiveTimeline;
	}

	public void setLabelInteractiveTimeline(Label labelInteractiveTimeline) {
		this.labelInteractiveTimeline = labelInteractiveTimeline;
	}

	public Composite getComposite_dinamic() {
		return composite_dinamic;
	}

	public void setComposite_dinamic(Composite composite_dinamic) {
		this.composite_dinamic = composite_dinamic;
	}

	

}
