package it.uniba.di.collab.socialcdeforeclipse.dynamic.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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
import it.uniba.di.collab.socialcdeforeclipse.action.ActionIterationTimeline;
import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.object.Panel;
import it.uniba.di.collab.socialcdeforeclipse.object.ProgressBarWindow;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.WPost;

public class DynamicIterationTimeline implements Panel {

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
	private static int totalField;
	private static ProgressBarWindow pbW; 
	private static long timerUpdate = 0; 

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

	private Image getUserImage(String link)
	{
		try {
			return resize(get_ImageStream(new URL(link).openStream()),48,48);
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			return resize(get_ImageStream(this.getClass()
					.getClassLoader().getResourceAsStream("images/DefaultAvatar.png")),48,48);
			
			
		} catch (NullPointerException e) {
			// TODO: handle exception
			return resize(get_ImageStream(this.getClass()
					.getClassLoader().getResourceAsStream("images/DefaultAvatar.png")),48,48);
		}
	}
	
	public Image get_ImageStream(InputStream stream) {
		return new Image(Controller.getWindow().getDisplay(), stream);
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

	public void updateTimeline()
	{
		
		if(userPostMaster.getChildren().length == 1)
		{
			posts = Controller.getProxy().GetIterationTimeline(
					Controller.getCurrentUser().Username,
					Controller.getCurrentUserPassword());
		}
		else
		{
			posts = Controller.getProxy().GetIterationTimeline(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword(),Integer.parseInt(userPostMaster.getChildren()[0].getData("IdPost").toString()),0);
		}
		if(posts.length > 0)
		{
		System.out.println(" numero post " + posts.length ); 
		System.out.println(" figli user post master " + userPostMaster.getChildren().length);
		System.out.println(" id ultimo post " + Integer.parseInt(userPostMaster.getChildren()[0].getData("IdPost").toString())); 
		}
		if(userPostMaster.getChildren()[0].getData("IdPost") == null)
		{
			System.out.println("id post � nullo "); 
		}
		else
		{
			System.out.println("id post non � nullo ");
		}
		
		Controller.temporaryInformation.put("CurrentComposite", userPostMaster.getChildren()[0]); 
		
		if(posts.length > 0 && posts[0].Id != Integer.parseInt(userPostMaster.getChildren()[0].getData("IdPost").toString()))
		{
			boolean flag = true; 
			
			for (int i = 0; i < posts.length; i++) {

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
						labelUserAvatar.setImage(resize(new Image(Display.getCurrent(), Controller.getUsersAvatar().get(posts[j].getUser().Username),SWT.IMAGE_COPY),48,48)); 

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
								// TODO Auto-generated method stub
								try {
									PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(event.text));
								} catch (PartInitException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (MalformedURLException e) {
									// TODO Auto-generated catch block
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

						Label messageDate = new Label(userPostComposite, SWT.WRAP);
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
				userPostComposite.moveAbove( (Composite) Controller.temporaryInformation.get("CurrentComposite")); 
				Controller.temporaryInformation.put("CurrentComposite", userPostComposite); 
			//	ActionIterationTimeline.setLastId(posts[i].Id);
			}
			}
		
		}
		
		userPostMaster.layout();
		//System.out.println("Aggiornamento completato"); 

		
		
	}
	
	
	@Override
	public void inizialize(Composite panel) {
		// TODO Auto-generated method stub
		System.out.println("Interaction timeline lanciato");
		
		timerUpdate = 0; 
		
		long firstCallsuperiorPart = System.currentTimeMillis();
		if(Controller.temporaryInformation.containsKey("LinuxProgressBar"))
		{
			pbW =  (ProgressBarWindow) Controller.temporaryInformation.get("LinuxProgressBar");  
		}
		
		GridData gridData;
		controlli = new ArrayList<Control>();
		azioni = new ActionGeneral();
		panel.setLayout(new GridLayout(1, false));

		superUserPostMaster = new ScrolledComposite(panel, SWT.V_SCROLL);
		superUserPostMaster.setLayout(new GridLayout(1, false));
		gridData = new GridData();
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
		userPostMaster.setLayoutData(gridData);

		userPostMaster.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));
		
		long endCallsuperiorPart = System.currentTimeMillis();
		
		System.out.println("the time to define the top of the screen is " + (endCallsuperiorPart - firstCallsuperiorPart) + " milliseconds");

		long firstCallpostStartTime = System.currentTimeMillis();
		
		posts = Controller.getProxy().GetIterationTimeline(Controller.getCurrentUser().Username,Controller.getCurrentUserPassword());
		
		long firstCallpostEndTime = System.currentTimeMillis();
		
		System.out.println("the time needed to run a Rest call is " + (firstCallpostEndTime - firstCallpostStartTime) + " milliseconds");

		if(Controller.OSisUnix())
		{
			pbW.updateProgressBar(); 
		}
		
		

		ActionIterationTimeline.setLastId(0);

		for (int i = 0; i < posts.length; i++) {

			final Composite userPostComposite = new Composite(userPostMaster,
					SWT.None);
			final int j = i;

			Display.getCurrent().syncExec(new Runnable() {

				@Override
				public void run() {
					
					
					if(Controller.OSisUnix())
					{
						pbW.updateProgressBar(); 
					}
					long firstCallgeneralComposite = System.currentTimeMillis();
					
					userPostComposite.setData("IdPost", posts[j].Id); 
					userPostComposite.setLayout(new GridLayout(2, false));
					userPostComposite.setBackground(Display.getCurrent()
							.getSystemColor(SWT.COLOR_WHITE));
					userPostComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
					GridData gridData = new GridData();
					gridData.widthHint = Controller.getWindowWidth() -10;
					userPostComposite.setLayoutData(gridData);

					controlli.add(userPostComposite);
					
					long endCallgeneralComposite = System.currentTimeMillis();
					
					System.out.println("time needed to create the first composite is " + (endCallgeneralComposite - firstCallgeneralComposite) + " milliseconds");

					long firstCallLabelAvatar = System.currentTimeMillis();

				    Label labelUserAvatar = new Label(userPostComposite,
							SWT.NONE);
					gridData = new GridData();
					gridData.verticalSpan = 3;
					gridData.verticalAlignment = SWT.BEGINNING;
					labelUserAvatar.setLayoutData(gridData);
					labelUserAvatar.setData("ID_action", "labelAvatar");
					
					if(Controller.getUsersAvatar().get(posts[j].getUser().Username) == null)
					{
						Controller.getUsersAvatar().put(posts[j].getUser().Username, getUserImage(posts[j].getUser().Avatar)); 
					}
					labelUserAvatar.setImage(resize(new Image(Display.getCurrent(), Controller.getUsersAvatar().get(posts[j].getUser().Username),SWT.IMAGE_COPY),48, 48)); 
					

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

					long endCallLabelAvatar = System.currentTimeMillis();
					
					System.out.println("Time needed to create label Avatar is " + (endCallLabelAvatar - firstCallLabelAvatar) + " milliseconds");
					
					long firstCallLabelUsername = System.currentTimeMillis();
					
					Label username = new Label(userPostComposite, SWT.None);
					username.setText(posts[j].getUser().Username);
					username.setFont(new Font(Controller.getWindow()
							.getDisplay(), "Calibri", 15, SWT.BOLD));
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
					gridData = new GridData();
					gridData.grabExcessHorizontalSpace = false;
					gridData.horizontalAlignment = GridData.BEGINNING;
					username.setLayoutData(gridData);

					long endCallLabelUsername = System.currentTimeMillis();
					
					System.out.println("Time needed to create label username is " + (endCallLabelUsername - firstCallLabelUsername) + " milliseconds");
					
					long firstCallLinkMessage = System.currentTimeMillis();
					
					Link message = new Link(userPostComposite,  SWT.WRAP);
					message.setText( findLink( posts[j].getMessage() ) );
					message.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)); 
					message.addListener(SWT.Selection, new Listener() {
						
						@Override
						public void handleEvent(Event event) {
							// TODO Auto-generated method stub
							try {
								PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(event.text));
							} catch (PartInitException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
						}
					});  
					gridData = new GridData();
					gridData.widthHint = Controller.getWindowWidth() - 150;
					message.setLayoutData(gridData);

					long endCallLinkMessage = System.currentTimeMillis();
					
					System.out.println("Time needed to create label message is " + (endCallLinkMessage - firstCallLinkMessage) + " milliseconds");
					
					long firstCallMessageDate = System.currentTimeMillis();
					
					Calendar nowDate = Calendar.getInstance();
					Calendar dateSelected = posts[j].getCreateAt();
					System.out.println("Data scelta " + posts[j].CreateAt); 
					long millisDiff = nowDate.getTime().getTime()
							- dateSelected.getTime().getTime();

					int seconds = (int) (millisDiff / 1000 % 60);
					int minutes = (int) (millisDiff / 60000 % 60);
					int hours = (int) (millisDiff / 3600000 % 24);
					int days = (int) (millisDiff / 86400000);

					Label messageDate = new Label(userPostComposite, SWT.WRAP);
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

					long endCallMessageDate = System.currentTimeMillis();
					
					System.out.println("Time needed to create label messageDate is " + (endCallMessageDate - firstCallMessageDate) + " milliseconds");
					
					long firstCallBarSeparator = System.currentTimeMillis();
					
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
					
					long endCallBarSeparator = System.currentTimeMillis();
					
					System.out.println("Time needed to creare a bar sepataror is " + (endCallBarSeparator - firstCallBarSeparator) + " milliseconds");
				}
			});

			ActionIterationTimeline.setLastId(posts[i].Id);

		}

		System.out.println("step1");
		otherPostWarning = new Composite(userPostMaster, SWT.None);
		otherPostWarning.setLayout(new GridLayout(1, false));
		otherPostWarning.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		otherPostWarning.setLayoutData(gridData);

		long secondCallpostStartTime = System.currentTimeMillis();
		
		 WPost[] newPost = Controller.getProxy().GetIterationTimeline(Controller.getCurrentUser().Username,	 Controller.getCurrentUserPassword(),ActionIterationTimeline.getLastId(),0);
		
			long secondCallpostEndTime = System.currentTimeMillis();
			
			System.out.println("seconda post chiamata " + (secondCallpostEndTime - secondCallpostStartTime) + " milliseconds");

			long firstCallfinalPartComposite = System.currentTimeMillis();
		 
		 System.out.println("Nuovi posts " + newPost.length); 
		 
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

		controlli.add(userPostMaster);
		Composite DownloadOlderPosts = new Composite(panel, SWT.None); 
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

		Composite controlToPost = new Composite(panel, SWT.None);
		controlToPost.setLayout(new GridLayout(2, false));
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		controlToPost.setLayoutData(gridData);
		controlli.add(controlToPost);

		textMessage = new Text(controlToPost, SWT.MULTI |SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
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

		
		
		userPostMaster.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				// TODO Auto-generated method stub
			
			Composite sc = (Composite)	e.widget; 
			 
			Control[] controls = sc.getChildren(); 
			 totalField = 0; 
			for(int i=0;i<controls.length; i++)
			{
				Control con = controls[i]; 
				totalField += 	con.getSize().y; 
				
			}
			 System.out.println("Lunghezza parte 1 " + sc.getChildren().length); 
			 System.out.println("Lunghezza parte 2 " + totalField/3);
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
			}
		}); 

		Controller.setWindowName("iterationTimeline");

		
		panel.layout();

		
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
	    
		long endCallfinalPartComposite = System.currentTimeMillis();
		System.out.println("Time needed to create the last part of the window is  " + (endCallfinalPartComposite - firstCallfinalPartComposite) + " milliseconds");
		
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
		// TODO Auto-generated method stub
		for (int i = 0; i < controlli.size(); i++) {
			final int j = i;

			Display.getCurrent().syncExec(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					controlli.get(j).dispose();
				}
			});

		}
	}

	@Override
	public HashMap<String, Object> getData() {
		// TODO Auto-generated method stub
		HashMap<String, Object> uiData = new HashMap<String, Object>();

		uiData.put("superUserPostMaster", superUserPostMaster);
		uiData.put("userPostMaster", userPostMaster);
		uiData.put("textMessage", textMessage);
		uiData.put("labelDownloadPost", labelDownloadPost);
		uiData.put("pbar", pbar);
		uiData.put("action", azioni);
		uiData.put("otherPostWarning", otherPostWarning);

		return uiData;
	}

}
