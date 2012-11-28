package it.uniba.di.socialcdeforeclipse.dynamic.view;


import it.uniba.di.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.socialcdeforeclipse.action.ActionProfile;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.shared.library.WService;
import it.uniba.di.socialcdeforeclipse.views.Panel;
import it.uniba.di.socialcdeforeclipse.object.SquareButtonService;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;

public class DynamicHome implements Panel {
	
	
	private ArrayList<Control> controlli;
	private Label labelAvatar; 
	private Label labelHidden; 
	private Label labelSkills; 
	private Label labelSettings; 
	private Label username;
	private Composite postComposite; 
	private Label labelPost; 
	private Label verticalSeparator; 
	private Label labelFollowing; 
	private Label labelFollowers; 
	private Composite buttonComposite; 
	private Composite serviceComposite; 
	
	private Label service;
	
	
	//public static Boolean setServiceCompositeHeight = false; 
	
	private final InputStream PATH_SKILLS = this.getClass().getClassLoader().getResourceAsStream("images/skills.png");
	private final InputStream PATH_SETTINGS = this.getClass().getClassLoader().getResourceAsStream("images/settings.png");
	private final InputStream PATH_DEFAULT_AVATAR = this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png");

	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
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
		
		
		
		controlli = new ArrayList<Control>();
		final Listener azioni = new ActionGeneral();
		GridLayout layout = new GridLayout(4, true);
		panel.setLayout(layout);
		 
		labelAvatar = new Label(panel,SWT.NONE); 
	    GridData gridData = new GridData();
		gridData.verticalSpan = 2; 
		labelAvatar.setLayoutData(gridData); 
		labelAvatar.setData("ID_action", "labelAvatar");
		Controller.setCurrentUser(Controller.getProxy().GetUser(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword())); 
		
			try {
				labelAvatar.setImage(get_ImageStream(new URL(Controller.getCurrentUser().Avatar).openStream()));
				labelAvatar.setImage(resize(labelAvatar.getImage(), 75, 75)); 
				 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//System.out.println("Eccezione lanciata"); 
				labelAvatar.setImage(get_ImageStream(PATH_DEFAULT_AVATAR));
				labelAvatar.setImage(resize(labelAvatar.getImage(), 75, 75));
				//e.printStackTrace();
			} 
			catch (NullPointerException e) {
				// TODO: handle exception
				labelAvatar.setImage(get_ImageStream(PATH_DEFAULT_AVATAR)); 
				labelAvatar.setImage(resize(labelAvatar.getImage(), 75, 75));
			}
		
		controlli.add(labelAvatar); 
		
		username = new Label(panel, SWT.NONE); 
		username.setText(Controller.getCurrentUser().Username); 
		username.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 15, SWT.NONE)); 
		
		labelHidden = new Label(panel,SWT.NONE); 
		labelHidden.setVisible(false); 
		
		buttonComposite = new Composite(panel, SWT.NONE); 
		Layout rowLayout = new RowLayout(); 
		buttonComposite.setLayout(rowLayout); 
		
		labelSkills = new Label(buttonComposite,SWT.RIGHT); 
		labelSkills.setImage(get_ImageStream(PATH_SKILLS));
		labelSkills.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND)); 
		labelSkills.setData("ID_action", "labelSkills");
		labelSkills.setToolTipText("View skills"); 
		
		labelSettings = new Label(buttonComposite,SWT.RIGHT); 
		labelSettings.setImage(get_ImageStream(PATH_SETTINGS));
		labelSettings.setToolTipText("Change password"); 
		labelSettings.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND)); 
		labelSettings.setData("ID_action", "labelSettings");
		
		postComposite = new Composite(panel, SWT.NONE); 
		
		GridLayout gridlayout = new GridLayout(5,false); 
		postComposite.setLayout(gridlayout); 
		gridData = new GridData(); 
		gridData.horizontalSpan = 3; 
		postComposite.setLayoutData(gridData); 
		
		
		
		labelPost = new Label(postComposite,SWT.WRAP | SWT.CENTER); 
		labelPost.setText("Posts\r\n" + Controller.getCurrentUser().getStatuses()); 
		labelPost.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.BOLD ));
		labelPost.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true)); 
		
		verticalSeparator = new Label(postComposite, SWT.SEPARATOR | SWT.VERTICAL); 
		gridData = new GridData(); 
		gridData.heightHint = 25; 
		verticalSeparator.setLayoutData(gridData);
		controlli.add(verticalSeparator); 
		
		labelFollowing = new Label(postComposite,SWT.WRAP | SWT.CENTER); 
		labelFollowing.setText("Following\r\n" + Controller.getCurrentUser().getFollowings()); 
		labelFollowing.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.BOLD ));
		labelFollowing.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true)); 
		
		verticalSeparator = new Label(postComposite, SWT.SEPARATOR | SWT.VERTICAL);
		gridData = new GridData(); 
		gridData.heightHint = 25; 
		verticalSeparator.setLayoutData(gridData);
		
		labelFollowers = new Label(postComposite, SWT.WRAP | SWT.CENTER); 
		labelFollowers.setText("Followers\r\n" + Controller.getCurrentUser().getFollowers()); 
		labelFollowers.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.BOLD ));
		labelFollowers.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		
		final  WService[] wService = Controller.getProxy().GetServices(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword());
		
		
		
		serviceComposite = new Composite(panel,SWT.None);
		//System.out.println("servicecomposite size " + serviceComposite.getBounds()); 
		//serviceComposite.setSize(serviceComposite.computeSize(serviceComposite.getSize().x, 100 * (wService.length/2)));
		//System.out.println("servicecomposite size after " + serviceComposite.getBounds()); 
		GridLayout serviceGrid = new GridLayout(2,true); 
		serviceComposite.setLayout(serviceGrid); 
		gridData = new GridData(); 
		gridData.horizontalSpan = 4;
		gridData.grabExcessHorizontalSpace = true; 
		gridData.grabExcessVerticalSpace = true; 
		gridData.verticalAlignment = GridData.FILL; 
		serviceComposite.setLayoutData(gridData); 
		
		//System.out.println("Servizi trovati " + wService.length); 
		
		
		
		
		 
		
	  // SquareButtonService services; 
		//Button services; 
		
		if(wService.length > 0)
		{
		
			for( int i=0;i<wService.length;i++)
			{ 
				final Composite bottleComposite = new Composite(serviceComposite, SWT.None); 
				final int j = i; 
				Display.getCurrent().syncExec(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						// TODO Auto-generated method stub
						bottleComposite.setLayout(new GridLayout(2,false)); 
						bottleComposite.setSize(bottleComposite.computeSize(100, 100)); 
						
						Composite service1 = new Composite(bottleComposite, SWT.None); 
						service1.setLayout(new GridLayout(1,false));
						service1.setSize(service1.computeSize(40, 70));
						
						SquareButtonService services = new SquareButtonService(service1, SWT.NONE);
						services.setRoundedCorners(false);
						services.setDefaultColors(new Color(Display.getCurrent(), 230, 230, 223),null,null,null);
						services.setClickedColors(new Color(Display.getCurrent(), 230, 230, 223), null, null,null);
						services.setHoverColors(new Color(Display.getCurrent(), 230, 230, 223), null, Display.getCurrent().getSystemColor(SWT.COLOR_CYAN),null);
						services.setSelectedColors(new Color(Display.getCurrent(), 230, 230, 223), null, null,null);
						services.borderWidth = 3;
						services.setText(""); 
						services.setData("ID_action","btnServices");
						services.setData("service" , wService[j]); 
						services.addListener(SWT.Selection, azioni); 
					
			
				
				 
				try {
					 
					services.setImage(get_ImageStream(new URL(Controller.getPreferences("ProxyRoot") +  wService[j].Image).openStream()));
					
					 
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 GridData gridData = new GridData(); 
				gridData.verticalSpan = 3; 
				service1.setLayoutData(gridData); 
				
					}
				}); 
					
				
				labelHidden = new Label(bottleComposite,SWT.None); 
				labelHidden.setVisible(false); 
				
				Label serviceName = new Label(bottleComposite, SWT.None); 
				serviceName.setText(wService[i].Name);
				
				Label serviceStatus = new Label(bottleComposite, SWT.None); 
				if(wService[i].Registered)
				{
					serviceStatus.setText("Status: Registered");
					serviceStatus.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN));
				}else
				{
					serviceStatus.setText("Status: Not registered");
					serviceStatus.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
				}
				
			}
		} else {
			service = new Label(serviceComposite, SWT.NONE);
			service.setText("There are no services available yet.\t\nPlease try again soon or contact your admin."); 
			gridData = new GridData(); 
			gridData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_CENTER; 
			gridData.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER; 
			service.setLayoutData(gridData); 
			controlli.add(service); 
		}
		
		
		
		labelSettings.addListener(SWT.MouseDown, azioni);
		labelSkills.addListener(SWT.MouseDown, azioni); 
		labelAvatar.addListener(SWT.MouseDown, azioni); 
		
		controlli.add(serviceComposite); 
		 controlli.add(labelFollowers); 
		 controlli.add(verticalSeparator); 
		 controlli.add(labelFollowing); 
		 controlli.add(labelPost); 
		 controlli.add(postComposite); 
		 controlli.add(labelSettings); 
		 controlli.add(labelSkills); 
		 controlli.add(buttonComposite);
		 controlli.add(labelHidden); 
		 controlli.add(username); 
		 controlli.add(labelAvatar); 
		Controller.setWindowName("Home"); 
		Controller.temporaryInformation.put("Workbench", PlatformUI.getWorkbench().getActiveWorkbenchWindow()); 
		
		serviceComposite.layout(); 
		serviceComposite.pack(); 
		
		panel.pack(); 
		panel.layout(); 
		
		
		Controller.getWindow().pack(); 
		Controller.getWindow().layout(); 
		
		 
		
	}
	
	

	@Override
	public void dispose(Composite panel) {
		// TODO Auto-generated method stub

		for(int i=0; i < controlli.size();i++)
		{
		 controlli.get(i).dispose(); 
			
		}
		//panel.setLayout(null); 
	}

	@Override
	public HashMap<String, Object> getData() {
		// TODO Auto-generated method stub
		
		HashMap<String, Object> uiData = new HashMap<String, Object>();
		
		uiData.put("serviceComposite",serviceComposite); 
		uiData.put("labelFollowers",labelFollowers); 
		uiData.put("labelFollowing",labelFollowing); 
		uiData.put("labelPost",labelPost); 
		uiData.put("postComposite",postComposite); 
		uiData.put("labelSettings",labelSettings);
		uiData.put("labelSkills",labelSkills); 
		uiData.put("buttonComposite",buttonComposite);
		uiData.put("labelHidden",labelHidden); 
		uiData.put("username",username); 
		uiData.put("labelAvatar",labelAvatar); 
		
		
		return uiData;
	}

	

}
