package it.uniba.di.collab.socialcdeforeclipse.dynamic.view;

import java.awt.font.TextMeasurer;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import it.uniba.di.collab.socialcdeforeclipse.action.ActionDynamicUserTimeline;
import it.uniba.di.collab.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.collab.socialcdeforeclipse.action.ActionHomeTimeline;
import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.object.Panel;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.WPost;

public class DynamicHomeTimeline implements Panel {

	private WPost[] posts;
	private ArrayList<Control> controlli;
	private Listener azioni;
	private ScrolledComposite superUserPostMaster;
	private Composite otherPostWarning;
	private Link otherPostAvailable;
	private static Composite userPostMaster;
	private Text textMessage;
	private Label labelDownloadPost;
	private ProgressBar pbar;
	private Composite DownloadOlderPosts; 
	private Composite controlToPost; 
	private static int totalField = 0; 
	private static long timerUpdate = 0; 

	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getCurrent(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, image.getBounds().width,
				image.getBounds().height, 0, 0, width, height);
		gc.dispose();
		image.dispose(); 
		return scaled;
	}

	private Image getUserImage(String link)
	{
		try {
			return resize(get_ImageStream(new URL(link).openStream()),48,48);
			

		} catch (IOException e) {
			
			
			return resize(get_ImageStream(this.getClass()
					.getClassLoader().getResourceAsStream("images/DefaultAvatar.png")),48,48);
			
			
		} catch (NullPointerException e) {
		
			return resize(get_ImageStream(this.getClass()
					.getClassLoader().getResourceAsStream("images/DefaultAvatar.png")),48,48);
		}
	}

	
	public Image get_ImageStream(InputStream stream) {
		return new Image(Controller.getWindow().getDisplay(), stream);
	}

	public void updateTimeline()
	{
		
		if(userPostMaster.getChildren().length == 1)
		{
			posts = Controller.getProxy().GetHomeTimeline(
					Controller.getCurrentUser().Username,
					Controller.getCurrentUserPassword());
		}
		else
		{
			posts = Controller.getProxy().GetHomeTimeline(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword(),Integer.parseInt(userPostMaster.getChildren()[0].getData("IdPost").toString()),0);
		}
		for (WPost element : posts) {
			System.out.println("Elemento con id " + element.Id + " inviato da " + element.User.Username + " il " + element.CreateAt.getTime().toString());
		}
		
		Controller.temporaryInformation.put("CurrentComposite", userPostMaster.getChildren()[0]); 
		
		if(posts.length > 0 &&  posts[0].Id != Integer.parseInt(userPostMaster.getChildren()[0].getData("IdPost").toString()))
		{
			boolean flag = true; 
			
			for (int i = 0; i < posts.length; i++) {
				System.out.println("Numero posts nuovi  " + posts.length);
				System.out.println("numero figli master " + userPostMaster.getChildren().length);
				System.out.println((userPostMaster.getChildren()[0].getData("IdPost") == null?  "id post è nullo " : "id post non è nullo"));
				System.out.println("post n. " + i + " valore " +  posts[i].Message + " id " + posts[i].Id); 
				System.out.println(" confronto con post n. 0 valore " +  userPostMaster.getChildren()[0].getData("IdPost").toString()); 
				
				if(posts[i].Id == Integer.parseInt(userPostMaster.getChildren()[0].getData("IdPost").toString()))
				{
					flag = false; 
				}
				
				if(flag)
				{
				final Composite userPostComposite = new Composite(userPostMaster,
						SWT.None);
				final int j = i;

				Display.getCurrent().syncExec(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						userPostComposite.setData("IdPost", posts[j].Id); 
						userPostComposite.setLayout(new GridLayout(2, false));
						userPostComposite.setBackground(Display.getCurrent()
								.getSystemColor(SWT.COLOR_WHITE));
						userPostComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
						GridData gridData = new GridData(); 
						gridData.widthHint = Controller.getWindowWidth() - 10; 
						userPostComposite.setLayoutData(gridData);

						controlli.add(userPostComposite);

						final Label labelUserAvatar = new Label(userPostComposite,
								SWT.NONE);
						gridData = new GridData();
						gridData.verticalSpan = 3;
						gridData.verticalAlignment = SWT.BEGINNING;
						labelUserAvatar.setLayoutData(gridData);
						
						if(Controller.getUsersAvatar().get(posts[j].getUser().Username) == null)
						{
							Controller.getUsersAvatar().put(posts[j].getUser().Username, getUserImage(posts[j].getUser().Avatar)); 
						}

						labelUserAvatar.setImage(resize(new Image(Display.getCurrent(),Controller.getUsersAvatar().get(posts[j].getUser().Username),SWT.IMAGE_COPY),48,48));
	
						if (!posts[j].getUser().Username.equals(Controller
								.getCurrentUser().Username)) {
							labelUserAvatar.setCursor(new Cursor(Display
									.getCurrent(), SWT.CURSOR_HAND));
							labelUserAvatar.setToolTipText("View "
									+ posts[j].getUser().Username + " Timeline");
							labelUserAvatar.setData("User_data", posts[j].getUser());
							labelUserAvatar.setData("ID_action", "labelAvatarLink");
							labelUserAvatar.addListener(SWT.MouseDown, azioni);
						}

					//	System.out.println("Fuori");

						Label username = new Label(userPostComposite, SWT.None);
						username.setText(posts[j].getUser().Username);
						if (!posts[j].getUser().Username.equals(Controller
								.getCurrentUser().Username)) {
							username.setForeground(new Color(Display.getCurrent(),
									56, 149, 184));
							username.setCursor(new Cursor(Display.getCurrent(),
									SWT.CURSOR_HAND));
							username.setToolTipText("View "
									+ posts[j].getUser().Username + " Timeline");
							username.setData("User_data", posts[j].getUser());
							username.setData("ID_action", "usernameLink");
							username.addListener(SWT.MouseDown, azioni);
						} else {
							username.setForeground(new Color(Display.getCurrent(),
									97, 91, 91));
						}
						username.setFont(new Font(Controller.getWindow()
								.getDisplay(), "Calibri", 15, SWT.BOLD));
						gridData = new GridData();
						gridData.grabExcessHorizontalSpace = false;
						gridData.horizontalAlignment = GridData.BEGINNING;
						username.setLayoutData(gridData);

						Link message = new Link(userPostComposite,  SWT.WRAP);
						message.setText( findLink( posts[j].getMessage() ) );
						message.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)); 
						message.addListener(SWT.Selection, new Listener() {
							
							@Override
							public void handleEvent(Event event) {
								
								try {
									PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(event.text));
								} catch (PartInitException e) {
									
									e.printStackTrace();
								} catch (MalformedURLException e) {
									
									e.printStackTrace();
								} 
							}
						}); 
						gridData = new GridData();
						gridData.widthHint = Controller.getWindowWidth() - 150;
						message.setLayoutData(gridData);

						Calendar nowDate = Calendar.getInstance();
						Calendar dateSelected = posts[j].getCreateAt();
						long millisDiff = nowDate.getTime().getTime()
								- dateSelected.getTime().getTime();

						int seconds = (int) (millisDiff / 1000 % 60);
						int minutes = (int) (millisDiff / 60000 % 60);
						int hours = (int) (millisDiff / 3600000 % 24);
						int days = (int) (millisDiff / 86400000);

						Label messageDate = new Label(userPostComposite, SWT.None);
						messageDate.setForeground(new Color(Display.getCurrent(),
								140, 140, 140));

						if (days > 1 && days < 30) {
							messageDate.setText("About " + days + " days ago from "
									+ posts[j].getService().getName());
						} else if (days > 30) {
							messageDate.setText("More than one month ago from "
									+ posts[j].getService().getName());
						} else if (days == 1) {
							messageDate.setText("About " + days + " day ago from "
									+ posts[j].getService().getName());
						} else {
							if (hours > 1) {
								messageDate.setText("About " + hours
										+ " hours ago from "
										+ posts[j].getService().getName());
							} else if (hours == 1) {
								messageDate.setText("About " + hours
										+ " hour ago from "
										+ posts[j].getService().getName());
							} else {

								if (minutes > 1) {
									messageDate.setText("About " + minutes
											+ " minutes ago from "
											+ posts[j].getService().getName());
								} else if (minutes == 1) {
									messageDate.setText("About " + minutes
											+ " minute ago from "
											+ posts[j].getService().getName());
								} else {

									if (seconds > 1) {
										messageDate.setText("About " + seconds
												+ " seconds ago from "
												+ posts[j].getService().getName());
									} else if (seconds == 1) {
										messageDate.setText("About " + seconds
												+ " second ago from "
												+ posts[j].getService().getName());
									} else {
										messageDate.setText("Few seconds ago from "
												+ posts[j].getService().getName());
									}
								}
							}
						}

						messageDate.setFont(new Font(Controller.getWindow()
								.getDisplay(), "Calibri", 8, SWT.ITALIC));
						gridData = new GridData();
						gridData.horizontalAlignment = GridData.BEGINNING;
						gridData.widthHint = Controller.getWindowWidth() - 150;
						messageDate.setLayoutData(gridData);

						Label labelhidden = new Label(userPostMaster, SWT.None);
						labelhidden.setText("");
						labelhidden.setVisible(false);

						Label barSeparator = new Label(userPostComposite,
								SWT.BORDER);
						gridData = new GridData();
						gridData.widthHint = 100;
						gridData.heightHint = 1;
						gridData.horizontalSpan = 2;
						gridData.horizontalAlignment = GridData.CENTER;
						barSeparator.setLayoutData(gridData);
					}
				});
				userPostComposite.moveAbove( (Composite) Controller.temporaryInformation.get("CurrentComposite")); 
				Controller.temporaryInformation.put("CurrentComposite", userPostComposite); 
				//ActionHomeTimeline.setLastId(posts[i].Id);
				 
			}
			}
		
		}
		userPostMaster.layout();
		
		//System.out.println("Aggiornamento completato"); 

		
		
	}
	
	public void insertTimeline(Composite panel)
	{
		superUserPostMaster = new ScrolledComposite(panel, SWT.V_SCROLL );
		GridData gridData = new GridData();
		
		gridData.heightHint = Controller.getWindowHeight() - 185 - 27;
		gridData.widthHint = Controller.getWindowWidth() - 35; 
		superUserPostMaster.setLayoutData(gridData);
		controlli.add(superUserPostMaster);

		userPostMaster = new Composite(superUserPostMaster, SWT.None);
		
		superUserPostMaster.setContent(userPostMaster);
		superUserPostMaster.setExpandVertical(true);
		superUserPostMaster.setExpandHorizontal(true);
		superUserPostMaster.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));

		controlli.add(userPostMaster);

		userPostMaster.setLayout(new GridLayout(1, false));
		gridData = new GridData(); 
		gridData.widthHint = Controller.getWindowWidth() - 10; 
		//gridData.heightHint = Controller.getWindowHeight() - 85 - 27;
		userPostMaster.setLayoutData(gridData); 
		
		userPostMaster.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));

		long postStartTime = System.currentTimeMillis();

		posts = Controller.getProxy().GetHomeTimeline(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword());

		long postEndTime = System.currentTimeMillis();
		
		

		ActionHomeTimeline.setLastId(0);

		for (int i = 0; i < posts.length; i++) {

			final Composite userPostComposite = new Composite(userPostMaster,
					SWT.None);
			final int j = i;

			Display.getCurrent().syncExec(new Runnable() {

				@Override
				public void run() {
				
					userPostComposite.setData("IdPost", posts[j].Id); 
					userPostComposite.setLayout(new GridLayout(2, false));
					userPostComposite.setBackground(Display.getCurrent()
							.getSystemColor(SWT.COLOR_WHITE));
					userPostComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
					GridData gridData = new GridData(); 
					gridData.widthHint = Controller.getWindowWidth() - 10; 
					userPostComposite.setLayoutData(gridData);

					controlli.add(userPostComposite);

					Label labelUserAvatar = new Label(userPostComposite,
							SWT.NONE);
					gridData = new GridData();
					gridData.verticalSpan = 3;
					gridData.verticalAlignment = SWT.BEGINNING;
					labelUserAvatar.setLayoutData(gridData);
					
					if(Controller.getUsersAvatar().get(posts[j].getUser().Username) == null)
					{
						Controller.getUsersAvatar().put(posts[j].getUser().Username, getUserImage(posts[j].getUser().Avatar)); 
					}

					labelUserAvatar.setImage(resize(new Image(Display.getCurrent(),Controller.getUsersAvatar().get(posts[j].getUser().Username),SWT.IMAGE_COPY),48,48));

					if (!posts[j].getUser().Username.equals(Controller
							.getCurrentUser().Username)) {
						labelUserAvatar.setCursor(new Cursor(Display
								.getCurrent(), SWT.CURSOR_HAND));
						labelUserAvatar.setToolTipText("View "
								+ posts[j].getUser().Username + " Timeline");
						labelUserAvatar.setData("User_data", posts[j].getUser());
						labelUserAvatar.setData("ID_action", "labelAvatarLink");
						labelUserAvatar.addListener(SWT.MouseDown, azioni);
					}

					//System.out.println("Fuori");

					Label username = new Label(userPostComposite, SWT.None);
					username.setText(posts[j].getUser().Username);
					if (!posts[j].getUser().Username.equals(Controller
							.getCurrentUser().Username)) {
						username.setForeground(new Color(Display.getCurrent(),
								56, 149, 184));
						username.setCursor(new Cursor(Display.getCurrent(),
								SWT.CURSOR_HAND));
						username.setToolTipText("View "
								+ posts[j].getUser().Username + " Timeline");
						username.setData("User_data", posts[j].getUser());
						username.setData("ID_action", "usernameLink");
						username.addListener(SWT.MouseDown, azioni);
					} else {
						username.setForeground(new Color(Display.getCurrent(),
								97, 91, 91));
					}
					username.setFont(new Font(Controller.getWindow()
							.getDisplay(), "Calibri", 15, SWT.BOLD));
					gridData = new GridData();
					gridData.grabExcessHorizontalSpace = false;
					gridData.horizontalAlignment = GridData.BEGINNING;
					username.setLayoutData(gridData);

					Link message = new Link(userPostComposite,  SWT.WRAP);
					message.setText( findLink( posts[j].getMessage() ) );
					message.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)); 
					message.addListener(SWT.Selection, new Listener() {
						
						@Override
						public void handleEvent(Event event) {
							
							try {
								PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(event.text));
							} catch (PartInitException e) {
							
								e.printStackTrace();
							} catch (MalformedURLException e) {
							
								e.printStackTrace();
							} 
						}
					}); 
			 
					gridData = new GridData();
					gridData.widthHint = Controller.getWindowWidth() - 150;
					message.setLayoutData(gridData);

					Calendar nowDate = Calendar.getInstance();
					Calendar dateSelected = posts[j].getCreateAt();
					long millisDiff = nowDate.getTime().getTime()
							- dateSelected.getTime().getTime();

					int seconds = (int) (millisDiff / 1000 % 60);
					int minutes = (int) (millisDiff / 60000 % 60);
					int hours = (int) (millisDiff / 3600000 % 24);
					int days = (int) (millisDiff / 86400000);

					Label messageDate = new Label(userPostComposite, SWT.None);
					messageDate.setForeground(new Color(Display.getCurrent(),
							140, 140, 140));

					if (days > 1 && days < 30) {
						messageDate.setText("About " + days + " days ago from "
								+ posts[j].getService().getName());
					} else if (days > 30) {
						messageDate.setText("More than one month ago from "
								+ posts[j].getService().getName());
					} else if (days == 1) {
						messageDate.setText("About " + days + " day ago from "
								+ posts[j].getService().getName());
					} else {
						if (hours > 1) {
							messageDate.setText("About " + hours
									+ " hours ago from "
									+ posts[j].getService().getName());
						} else if (hours == 1) {
							messageDate.setText("About " + hours
									+ " hour ago from "
									+ posts[j].getService().getName());
						} else {

							if (minutes > 1) {
								messageDate.setText("About " + minutes
										+ " minutes ago from "
										+ posts[j].getService().getName());
							} else if (minutes == 1) {
								messageDate.setText("About " + minutes
										+ " minute ago from "
										+ posts[j].getService().getName());
							} else {

								if (seconds > 1) {
									messageDate.setText("About " + seconds
											+ " seconds ago from "
											+ posts[j].getService().getName());
								} else if (seconds == 1) {
									messageDate.setText("About " + seconds
											+ " second ago from "
											+ posts[j].getService().getName());
								} else {
									messageDate.setText("Few seconds ago from "
											+ posts[j].getService().getName());
								}
							}
						}
					}

					messageDate.setFont(new Font(Controller.getWindow()
							.getDisplay(), "Calibri", 8, SWT.ITALIC));
					gridData = new GridData();
					gridData.horizontalAlignment = GridData.BEGINNING;
					gridData.widthHint = Controller.getWindowWidth() - 150;
					messageDate.setLayoutData(gridData);

					/*Label labelhidden = new Label(userPostMaster, SWT.None);
					labelhidden.setText("");
					labelhidden.setVisible(false);*/

					Label barSeparator = new Label(userPostComposite,
							SWT.BORDER);
					gridData = new GridData();
					gridData.widthHint = 100;
					gridData.heightHint = 1;
					gridData.horizontalSpan = 2;
					gridData.horizontalAlignment = GridData.CENTER;
					barSeparator.setLayoutData(gridData);
				}
			});
			
			ActionHomeTimeline.setLastId(posts[i].Id);
			 

		}
	}
	
	@Override
	public void inizialize(final Composite panel) {
		
		
		timerUpdate = 0;
		
	//	System.out.println("Home timeline lanciato");

		GridData gridData;
		controlli = new ArrayList<Control>();
		azioni = new ActionGeneral();
		panel.setLayout(new GridLayout(1, false));
	
		insertTimeline(panel); 
		

		//System.out.println("step1");
		otherPostWarning = new Composite(userPostMaster, SWT.None);
		otherPostWarning.setLayout(new GridLayout(1, false));
		otherPostWarning.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		otherPostWarning.setLayoutData(gridData);
		
		long secondCallpostStartTime = System.currentTimeMillis();
		
		WPost[] newPost = Controller.getProxy().GetHomeTimeline(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword(),
				ActionHomeTimeline.getLastId(), 0);

		long secondCallpostEndTime = System.currentTimeMillis();
		
		System.out.println("seconda chiamata post " + (secondCallpostEndTime - secondCallpostStartTime) + " milliseconds");
		
		if (newPost == null || newPost.length == 2) {
			newPost = new WPost[0];
		}

		if (newPost.length > 0) {
			otherPostAvailable = new Link(otherPostWarning, SWT.NONE);
			otherPostAvailable.setCursor(new Cursor(panel.getDisplay(),
					SWT.CURSOR_HAND));
			otherPostAvailable.setFont(new Font(Controller.getWindow()
					.getDisplay(), "Calibri", 10, SWT.UNDERLINE_LINK));
			otherPostAvailable.setText("<a>Click to view older posts</a>");
			otherPostAvailable.setBackground(Display.getCurrent()
					.getSystemColor(SWT.COLOR_WHITE));
			gridData = new GridData();
			gridData.grabExcessHorizontalSpace = true;
			gridData.horizontalAlignment = GridData.CENTER;
			otherPostAvailable.setLayoutData(gridData);

			otherPostAvailable.addListener(SWT.Selection, azioni);
			otherPostAvailable.setData("ID_action", "otherPostAvailable");

		} else {
			Label noPostAvailable = new Label(otherPostWarning, SWT.NONE);
			noPostAvailable.setText("There are no post in the cache.\n Please try again in two minutes.");
			noPostAvailable.setBackground(Display.getCurrent().getSystemColor(
					SWT.COLOR_WHITE));
			noPostAvailable.setFont(new Font(Controller.getWindow()
					.getDisplay(), "Calibri", 10, SWT.None));
			gridData = new GridData();
			gridData.grabExcessHorizontalSpace = true;
			gridData.horizontalAlignment = GridData.CENTER;
			noPostAvailable.setLayoutData(gridData);
		}

	//	System.out.println("Step2");

		controlli.add(userPostMaster);

		DownloadOlderPosts = new Composite(panel, SWT.None); 
		DownloadOlderPosts.setLayout(new GridLayout(2,false)); 
		
		labelDownloadPost = new Label(DownloadOlderPosts, SWT.None);
		labelDownloadPost.setText("Download older posts..");
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.widthHint = 170;
		labelDownloadPost.setLayoutData(gridData);
		labelDownloadPost.setVisible(false);
		pbar = new ProgressBar(DownloadOlderPosts, SWT.None);
		pbar.setVisible(false);
		pbar.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));

		controlli.add(DownloadOlderPosts); 

		controlToPost = new Composite(panel, SWT.None);
		controlToPost.setLayout(new GridLayout(2, false));
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.BEGINNING;
		controlToPost.setLayoutData(gridData);
		controlli.add(controlToPost);

		textMessage = new Text(controlToPost, SWT.MULTI | SWT.WRAP | SWT.BORDER);
		gridData = new GridData();
		gridData.heightHint = 75;
		gridData.widthHint = Controller.getWindowWidth() - 100;

		textMessage.setLayoutData(gridData);

		Label btnSendMessage = new Label(controlToPost, SWT.None);
		btnSendMessage.setImage(resize(get_ImageStream(this.getClass()
				.getClassLoader()
				.getResourceAsStream("images/send_message.png")), 48, 48));
		btnSendMessage
				.setCursor(new Cursor(panel.getDisplay(), SWT.CURSOR_HAND));
		btnSendMessage.setToolTipText("Send message");
		btnSendMessage.setData("ID_action", "btnSendMessage");
		btnSendMessage.addListener(SWT.MouseDown, azioni);

		System.out.println("Step3");
		
		
		
		userPostMaster.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				// TODO Auto-generated method stub
			System.out.println("paint chiamato"); 
			Composite sc = (Composite)	e.widget; 
			 
			Control[] controls = sc.getChildren(); 
			 totalField = 0; 
			for(int i=0;i<controls.length; i++)
			{
				Control con = controls[i]; 
				totalField += 	con.getSize().y; 
				
			}
			 System.out.println("Lunghezza parte 1 " + totalField + " con " + controls.length + " figli "); 
			 System.out.println("Lunghezza parte 2 " + ((totalField - ((sc.getChildren().length / 50) * 100)) - 100));
			//p2
			// superUserPostMaster.setMinSize(panel.computeSize(SWT.DEFAULT, totalField + 100));
			 if(sc.getChildren().length > 100)
				{
					if((sc.getChildren().length/100) > 1)
					{
						superUserPostMaster.setMinSize(Controller.getWindowWidth(),totalField + (2000 * (int) (sc.getChildren().length/150))); 
					}
					else
					{
						System.out.println("Chiamata valore aggiunto "); 
						superUserPostMaster.setMinSize(Controller.getWindowWidth(),totalField + 1600);
					}
						
				}
				 else if(sc.getChildren().length > 40)
				{
					superUserPostMaster.setMinSize(Controller.getWindowWidth(),totalField + 900);
				}
				
				else if(sc.getChildren().length > 20)
				{
					 superUserPostMaster.setMinSize(Controller.getWindowWidth(),totalField + 500);
				}
				else
				{
					superUserPostMaster.setMinSize(Controller.getWindowWidth(),totalField + 100);
				}
			
			
			 System.out.println("Lunghezza minima impostata " + superUserPostMaster.getMinHeight());
			}
		}); 

		panel.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				// TODO Auto-generated method stub
				/*System.out.println("dimensione " + e.width + " altezza "
						+ e.height);
				System.out.println("message imp "
						+ textMessage.getBounds().width + " altezza "
						+ textMessage.getBounds().height);*/
				System.out.println(" Size super user " + superUserPostMaster.getSize() + " \n size older post " + DownloadOlderPosts.getSize() + " \n size message " + controlToPost.getSize()); 
				System.out.println(" dimensione window " + Controller.getWindowHeight() + "\n valore consigliato " + (Controller.getWindowHeight() - DownloadOlderPosts.getSize().y - controlToPost.getSize().y)); 
				System.out.println(" UserPostMaster size " + userPostMaster.getSize()); 
				//userPostMaster.setSize(Controller.getWindowWidth() - 90, totalField); 
				//350 both
				/*superUserPostMaster.setSize(Controller.getWindow().computeSize(
						Controller.getWindowWidth() - 20, (Controller.getWindowHeight() - DownloadOlderPosts.getSize().y - controlToPost.getSize().y)));
				userPostMaster.setSize(Controller.getWindow().computeSize(
						Controller.getWindowWidth() - 70, (Controller.getWindowHeight() - DownloadOlderPosts.getSize().y - controlToPost.getSize().y)-100));*/
				/*textMessage.setBackgroundImage(resize(
						get_ImageStream(this.getClass().getClassLoader()
								.getResourceAsStream("images/Baloon.png")),
						textMessage.getBounds().width,
						textMessage.getBounds().height));*/

			}
		});

		Controller.setWindowName("homeTimeline");
		
		//panel.layout();
		
		
		Controller.getWindow().layout();
		 
		
		
		final int time = 10000;
	    final Runnable timer = new Runnable() {
	      public void run() {
	      
	    if(!userPostMaster.isDisposed())
	      {
	    	if(timerUpdate == 0)
	    	{
	    	System.out.println("Chiamata eseguita " + Calendar.getInstance().getTime().toString()); 
					 updateTimeline();  	 
					 	userPostMaster.redraw(); 
			 timerUpdate = Calendar.getInstance().getTimeInMillis(); 
			 System.out.println("Acquisito " + Calendar.getInstance().getTime().toString());
	    	}
	    	else
	    	{
	    		long tempTimer = Calendar.getInstance().getTimeInMillis();
	    		 System.out.println("Acquisito 2" + Calendar.getInstance().getTime().toString());
	    		System.out.println("Confronto " +  (tempTimer - timerUpdate) ); 
	    		if( (tempTimer - timerUpdate) < 10010 && (tempTimer - timerUpdate) > 9990)
	    		{
	    			System.out.println("Chiamata eseguita parte 2 " + Calendar.getInstance().getTime().toString()); 
					 updateTimeline();  	 
					 	userPostMaster.layout(); 
					 	superUserPostMaster.layout(); 
					 	
					 	timerUpdate = System.currentTimeMillis(); 
	    		}
	    		timerUpdate = tempTimer; 
	    	}
	    	
	        Display.getCurrent().timerExec(time, this);
	      }
	      
	      }
	    };
	    Display.getCurrent().timerExec(time, timer);
	    
	   userPostMaster.addDisposeListener(new DisposeListener() {
		
		@Override
		public void widgetDisposed(DisposeEvent e) {
			// TODO Auto-generated method stub
			Display.getCurrent().timerExec(-1,timer);
		}
	});
	    
	}

	@Override
	public void dispose(Composite panel) {
	
		for (int i = 0; i < controlli.size(); i++) {
			final int j = i;

			Display.getCurrent().syncExec(new Runnable() {

				@Override
				public void run() {
						controlli.get(j).dispose();
				}
			});

		}
	}

	@Override
	public HashMap<String, Object> getData() {
		
		HashMap<String, Object> uiData = new HashMap<String, Object>();

		uiData.put("superUserPostMaster", superUserPostMaster);
		uiData.put("userPostMaster", userPostMaster);
		uiData.put("otherPostWarning", otherPostWarning);
		uiData.put("userPostMaster", userPostMaster);
		uiData.put("textMessage", textMessage);
		uiData.put("labelDownloadPost", labelDownloadPost);
		uiData.put("pbar", pbar);
		uiData.put("action", azioni);

		return uiData;
	}
	
	private String findLink(String message)
	{
		String[] subsequences = message.split(" "); 
		String result = ""; 
		for(int i=0;i<subsequences.length; i++)
		{
			if(result.equals(""))
			{
				if(subsequences[i].contains("http"))
				{
					result = "<a href=\" " + subsequences[i] + "\" > " + subsequences[i] + "</a> "; 
				}
				else
				{
					result = subsequences[i] + " "; 
				}
			}
			else
			{
				if(subsequences[i].contains("http"))
				{
					result += "<a href=\" " + subsequences[i] + "\" > " + subsequences[i] + "</a> "; 
				}
				else
				{
					result += subsequences[i] + " "; 
				}
			}
		}
		
		return result; 
	}

}
