package it.uniba.di.collab.socialcdeforeclipse.dynamic.view;

import it.uniba.di.collab.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.collab.socialcdeforeclipse.action.ActionProfile;
import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.object.Panel;
import it.uniba.di.collab.socialcdeforeclipse.object.SquareButtonService;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.WService;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
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
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;

public class DynamicHome implements Panel {

	private ArrayList<Object> controlli;
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

	

	private final InputStream PATH_SKILLS = this.getClass().getClassLoader()
			.getResourceAsStream("images/skills.png");
	private final InputStream PATH_SETTINGS = this.getClass().getClassLoader()
			.getResourceAsStream("images/settings.png");
	private final InputStream PATH_DEFAULT_AVATAR = this.getClass()
			.getClassLoader().getResourceAsStream("images/DefaultAvatar.png");

	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, image.getBounds().width,
				image.getBounds().height, 0, 0, width, height);
		gc.dispose();
		image.dispose(); 
		return scaled;
	}

	public Image get_ImageStream(InputStream stream) {
		return new Image(Controller.getWindow().getDisplay(), stream);
	}

	private Image getServiceImage(String link)
	{
		try {

			return get_ImageStream(new URL(
					Controller.getPreferences("proxyRoot")
							+ link).openStream());

		} catch (MalformedURLException e) {
		
			e.printStackTrace();
			return null;
		} catch (IOException e) {
		
			e.printStackTrace();
			return null;
		}
	}
	
	private Image getUserImage(String link)
	{
		try {
			return resize(get_ImageStream(new URL(Controller
					.getCurrentUser().Avatar).openStream()),48,48);
			

		} catch (IOException e) {
			
			
			return resize(get_ImageStream(PATH_DEFAULT_AVATAR),48,48);
			
			
		} catch (NullPointerException e) {
			
			return resize(get_ImageStream(PATH_DEFAULT_AVATAR),48,48);
		}
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void inizialize(Composite panel) {
		
		
		controlli = new ArrayList<Object>();
		final Listener azioni = new ActionGeneral();
		GridLayout layout = new GridLayout(4, true);
		panel.setLayout(layout);

		labelAvatar = new Label(panel, SWT.NONE);
		GridData gridData = new GridData();
		gridData.verticalSpan = 2;
		labelAvatar.setLayoutData(gridData);
		labelAvatar.setData("ID_action", "labelAvatar");
		Controller.setCurrentUser(Controller.getProxy().GetUser(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword()));

		if(Controller.getUsersAvatar().get(Controller.getCurrentUser().Username) == null)
		{
			Controller.getUsersAvatar().put(Controller.getCurrentUser().Username, getUserImage(Controller.getCurrentUser().Avatar)); 
		}
		
		labelAvatar.setImage(resize(new Image(Display.getCurrent(),Controller.getUsersAvatar().get(Controller.getCurrentUser().Username),SWT.IMAGE_COPY),75,75)); 
		controlli.add(labelAvatar.getImage()); 
		controlli.add(labelAvatar);

		username = new Label(panel, SWT.NONE);
		username.setText(Controller.getCurrentUser().Username);
		username.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 15, SWT.NONE));

		
		buttonComposite = new Composite(panel, SWT.NONE);
		Layout rowLayout = new RowLayout();
		buttonComposite.setLayout(rowLayout);

		labelHidden = new Label(panel, SWT.NONE);
		labelHidden.setVisible(false);

	
		labelSkills = new Label(buttonComposite, SWT.RIGHT);
		labelSkills.setImage(get_ImageStream(PATH_SKILLS));
		labelSkills.setCursor(new Cursor(panel.getDisplay(), SWT.CURSOR_HAND));
		labelSkills.setData("ID_action", "labelSkills");
		labelSkills.setToolTipText("View skills");

		labelSettings = new Label(buttonComposite, SWT.RIGHT);
		labelSettings.setImage(get_ImageStream(PATH_SETTINGS));
		labelSettings.setToolTipText("Change password");
		labelSettings
				.setCursor(new Cursor(panel.getDisplay(), SWT.CURSOR_HAND));
		labelSettings.setData("ID_action", "labelSettings");

		postComposite = new Composite(panel, SWT.NONE);

		GridLayout gridlayout = new GridLayout(5, false);
		postComposite.setLayout(gridlayout);
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		postComposite.setLayoutData(gridData);

		labelPost = new Label(postComposite, SWT.WRAP | SWT.CENTER);
		labelPost.setText("Posts\r\n"
				+ Controller.getCurrentUser().getStatuses());
		labelPost.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 10, SWT.BOLD));
		labelPost
				.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));

		verticalSeparator = new Label(postComposite, SWT.SEPARATOR
				| SWT.VERTICAL);
		gridData = new GridData();
		gridData.heightHint = 25;
		verticalSeparator.setLayoutData(gridData);
		controlli.add(verticalSeparator);

		labelFollowing = new Label(postComposite, SWT.WRAP | SWT.CENTER);
		labelFollowing.setText("Following\r\n"
				+ Controller.getCurrentUser().getFollowings());
		labelFollowing.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 10, SWT.BOLD));
		labelFollowing.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true,
				true));

		verticalSeparator = new Label(postComposite, SWT.SEPARATOR
				| SWT.VERTICAL);
		gridData = new GridData();
		gridData.heightHint = 25;
		verticalSeparator.setLayoutData(gridData);

		labelFollowers = new Label(postComposite, SWT.WRAP | SWT.CENTER);
		labelFollowers.setText("Followers\r\n"
				+ Controller.getCurrentUser().getFollowers());
		labelFollowers.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 10, SWT.BOLD));
		labelFollowers.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true,
				true));

		final WService[] wService = Controller.getProxy().GetServices(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword());

		final ScrolledComposite superUserPostMaster = new ScrolledComposite(panel, SWT.V_SCROLL);
		superUserPostMaster.setLayout(new GridLayout(1, false));
		gridData = new GridData();
		gridData.horizontalSpan = 4;
		
		if(Controller.OSisWindows())
		{
			gridData.widthHint = Controller.getWindowWidth() -40; 
		}
		else
		{
			gridData.widthHint = Controller.getWindowWidth() -30; 
		}
		gridData.grabExcessVerticalSpace = true; 
		superUserPostMaster.setLayoutData(gridData);
		
		serviceComposite = new Composite(superUserPostMaster, SWT.None);
		
		superUserPostMaster.setContent(serviceComposite);
		superUserPostMaster.setExpandVertical(true);
		superUserPostMaster.setExpandHorizontal(true);
		superUserPostMaster.setBackgroundMode(SWT.INHERIT_DEFAULT); 
		superUserPostMaster.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));
		
		//GridLayout serviceGrid = new GridLayout(1, true);
		serviceComposite.setLayout(new RowLayout());
		

		if (wService.length > 0) {

			for (int i = 0; i < wService.length; i++) {
				
				final Composite bottleComposite = new Composite(
						serviceComposite, SWT.None);
				final int j = i;
				Display.getCurrent().syncExec(new Runnable() {

					@Override
					public void run() {
						bottleComposite.setLayout( new GridLayout(2, false));
						bottleComposite.setSize(bottleComposite.computeSize(
								100, 100));

						Composite service1 = new Composite(bottleComposite,
								SWT.None);
						service1.setLayout(new GridLayout(1, false));
						service1.setSize(service1.computeSize(40, 70));

						SquareButtonService services = new SquareButtonService(
								service1, SWT.NONE);
						services.setRoundedCorners(false);
						services.setDefaultColors(
								new Color(Display.getCurrent(), 230, 230, 223),
								null, null, null);
						services.setClickedColors(
								new Color(Display.getCurrent(), 230, 230, 223),
								null, null, null);
						services.setHoverColors(new Color(Display.getCurrent(),
								230, 230, 223), null, Display.getCurrent()
								.getSystemColor(SWT.COLOR_CYAN), null);
						services.setSelectedColors(
								new Color(Display.getCurrent(), 230, 230, 223),
								null, null, null);
						services.borderWidth = 3;
						services.setText("");
						services.setData("ID_action", "btnServices");
						services.setData("service", wService[j]);
						services.addListener(SWT.Selection, azioni);

						if(Controller.getServicesImage().get(wService[j].Name) == null)
						{
							Controller.getServicesImage().put(wService[j].Name, getServiceImage(wService[j].Image)); 
						}
						
						services.setImage(Controller.getServicesImage().get(wService[j].Name)); 
						
			

						GridData gridData = new GridData();
						gridData.verticalSpan = 3;
						service1.setLayoutData(gridData);

					}
				});

				labelHidden = new Label(bottleComposite, SWT.None);
				labelHidden.setVisible(false);

				Label serviceName = new Label(bottleComposite, SWT.None);
				serviceName.setText(wService[i].Name);

				Label serviceStatus = new Label(bottleComposite, SWT.None);
				if (wService[i].Registered) {
					serviceStatus.setText("Status: Registered");
					serviceStatus.setForeground(Display.getCurrent()
							.getSystemColor(SWT.COLOR_DARK_GREEN));
				} else {
					serviceStatus.setText("Status: Not registered");
					serviceStatus.setForeground(Display.getCurrent()
							.getSystemColor(SWT.COLOR_RED));
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
		Controller.temporaryInformation.put("Workbench", PlatformUI
				.getWorkbench().getActiveWorkbenchWindow());

		
		superUserPostMaster.setMinSize(Controller.getWindowWidth(),wService.length * 100 );
		panel.layout();
	
	}

	@Override
	public void dispose(Composite panel) {
	

		for (int i = 0; i < controlli.size(); i++) {
		  try {
			((Control)	controlli.get(i)).dispose();
		} catch (Exception e) {
			// TODO: handle exception
			((Image)	controlli.get(i)).dispose();
		}
			

		}
		
	}

	@Override
	public HashMap<String, Object> getData() {
		

		HashMap<String, Object> uiData = new HashMap<String, Object>();

		uiData.put("serviceComposite", serviceComposite);
		uiData.put("labelFollowers", labelFollowers);
		uiData.put("labelFollowing", labelFollowing);
		uiData.put("labelPost", labelPost);
		uiData.put("postComposite", postComposite);
		uiData.put("labelSettings", labelSettings);
		uiData.put("labelSkills", labelSkills);
		uiData.put("buttonComposite", buttonComposite);
		uiData.put("labelHidden", labelHidden);
		uiData.put("username", username);
		uiData.put("labelAvatar", labelAvatar);

		return uiData;
	}

}
