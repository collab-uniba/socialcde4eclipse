package it.uniba.di.collab.socialcdeforeclipse.action;

import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.object.*;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.WPost;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.WUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class ActionIterationTimeline {
	private static long lastId;
	private final InputStream PATH_DEFAULT_AVATAR = this.getClass()
			.getClassLoader().getResourceAsStream("images/DefaultAvatar.png");

	private WUser userSelected; 
	private WUser[] userCategory; 
	
	public static long getLastId() {
		return lastId;
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
	
	public static void setLastId(long lastId) {
		ActionIterationTimeline.lastId = lastId;
	}
	
	private String findLink(String message) {
		String[] singleLine = message.split("\n");
		
		List<String> singleWords = new ArrayList<String>();
		for (String s: singleLine) {
			for (String ss : s.split(" ")) {
				singleWords.add(ss);
			}
		}
		
		String [] subsequences = new String[singleWords.size()];
		singleWords.toArray(subsequences);
		String result = "";
		for (int i = 0; i < subsequences.length; i++) {
			if (result.equals("")) {
				if (subsequences[i].contains("http")) {
					result = "<a href=\" " + subsequences[i] + "\" > "
							+ subsequences[i] + "</a> ";
				} else {
					result = subsequences[i] + " ";
				}
			} else {
				if (subsequences[i].contains("http")) {
					result += "<a href=\" " + subsequences[i] + "\" > "
							+ subsequences[i] + "</a> ";
				} else {
					result += subsequences[i] + " ";
				}
			}
		}

		return result;
	}

	public Image get_ImageStream(InputStream stream) {
		return new Image(Controller.getWindow().getDisplay(), stream);
	}

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

	public ActionIterationTimeline(final HashMap<String, Object> uiData) {
		String widgetName = uiData.get("ID_action").toString();
		int type = (int) uiData.get("Event_type");
		Event event = (Event) uiData.get("Event");
		final Listener azioni = new ActionGeneral();
		System.out.println("Iteration Timeline invocato");
		switch (widgetName) {

		case "usernameLink":
			if(Controller.getProxy().IsWebServiceRunning())
			{
			userSelected = (WUser) uiData.get("User_data");

			 userCategory = Controller.getProxy().GetSuggestedFriends(
					Controller.getCurrentUser().Username,
					Controller.getCurrentUserPassword());

			for (int i = 0; i < userCategory.length; i++) {
				if (userCategory[i].equals(userSelected)) {
					Controller.temporaryInformation.put("User_type",
							"Suggested");
				}
			}

			userCategory = null;

			userCategory = Controller.getProxy().GetFollowings(
					Controller.getCurrentUser().Username,
					Controller.getCurrentUserPassword());

			for (int i = 0; i < userCategory.length; i++) {
				if (userCategory[i].equals(userSelected)) {
					Controller.temporaryInformation.put("User_type",
							"Following");
				}
			}

			userCategory = null;

			userCategory = Controller.getProxy().GetFollowers(
					Controller.getCurrentUser().Username,
					Controller.getCurrentUserPassword());

			for (int i = 0; i < userCategory.length; i++) {
				if (userCategory[i].equals(userSelected)) {
					Controller.temporaryInformation.put("User_type",
							"Followers");
				}
			}

			userCategory = null;

			userCategory = Controller.getProxy().GetHiddenUsers(
					Controller.getCurrentUser().Username,
					Controller.getCurrentUserPassword());

			for (int i = 0; i < userCategory.length; i++) {
				if (userCategory[i].equals(userSelected)) {
					Controller.temporaryInformation.put("User_type", "Hidden");
				}
			}

			userCategory = null;

			Controller.temporaryInformation.put("User_selected", userSelected);
			Controller.selectDynamicWindow(3);
			}
			else
			{
				Controller.openConnectionLostPanel("Connection Lost! \n You will be redirected to Login page."); 	
			}
			break;

		case "labelAvatarLink":
			if(Controller.getProxy().IsWebServiceRunning())
			{
			userSelected = (WUser) uiData.get("User_data");

			userCategory = Controller.getProxy().GetSuggestedFriends(
					Controller.getCurrentUser().Username,
					Controller.getCurrentUserPassword());

			for (int i = 0; i < userCategory.length; i++) {
				if (userCategory[i].equals(userSelected)) {
					Controller.temporaryInformation.put("User_type",
							"Suggested");
				}
			}

			userCategory = null;

			userCategory = Controller.getProxy().GetFollowings(
					Controller.getCurrentUser().Username,
					Controller.getCurrentUserPassword());

			for (int i = 0; i < userCategory.length; i++) {
				if (userCategory[i].equals(userSelected)) {
					Controller.temporaryInformation.put("User_type",
							"Following");
				}
			}

			userCategory = null;

			userCategory = Controller.getProxy().GetFollowers(
					Controller.getCurrentUser().Username,
					Controller.getCurrentUserPassword());

			for (int i = 0; i < userCategory.length; i++) {
				if (userCategory[i].equals(userSelected)) {
					Controller.temporaryInformation.put("User_type",
							"Followers");
				}
			}

			userCategory = null;

			userCategory = Controller.getProxy().GetHiddenUsers(
					Controller.getCurrentUser().Username,
					Controller.getCurrentUserPassword());

			for (int i = 0; i < userCategory.length; i++) {
				if (userCategory[i].equals(userSelected)) {
					Controller.temporaryInformation.put("User_type", "Hidden");
				}
			}

			userCategory = null;

			Controller.temporaryInformation.put("User_selected", userSelected);
			Controller.selectDynamicWindow(3);
			}
			else
			{
				Controller.openConnectionLostPanel("Connection Lost! \n You will be redirected to Login page."); 	
			}
			break;

		case "otherPostAvailable":
			if(Controller.getProxy().IsWebServiceRunning())
			{
			((Label) uiData.get("labelDownloadPost")).setVisible(true);
			((Label) uiData.get("labelDownloadPost")).redraw();
			Display.getCurrent().update();
			((ProgressBar) uiData.get("pbar")).setVisible(true);

			 final WPost[] posts =
			 Controller.getProxy().GetIterationTimeline(Controller.getCurrentUser().Username,
			 Controller.getCurrentUserPassword(),0,getLastId());
			
			if (posts.length > 0) {
				for (Control element : ((Composite) uiData.get("userPostMaster")).getChildren()) {
					try {
						if( ((Composite) element).getChildren().length < 3 )
						{
							element.dispose(); 
							break;
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
				Display.getCurrent().update();
			}
			final int max = 100;
			for (int i = 0; i < posts.length; i++) {

				final Composite userPostComposite = new Composite(
						((Composite) uiData.get("userPostMaster")), SWT.None);
				final int j = i;

				Display.getCurrent().syncExec(new Runnable() {

					@Override
					public void run() {

						if (((ProgressBar) uiData.get("pbar")).getSelection() == (max - 1)) {
							((ProgressBar) uiData.get("pbar")).setSelection(0);
						} else {
							((ProgressBar) uiData.get("pbar"))
									.setSelection(((ProgressBar) uiData
											.get("pbar")).getSelection() + 1);
							((ProgressBar) uiData.get("pbar")).redraw();
							
						}
						
						userPostComposite.setData("IdPost", posts[j].Id); 
						userPostComposite.setLayout(new GridLayout(2, false));
						userPostComposite.setBackground(Display.getCurrent()
								.getSystemColor(SWT.COLOR_WHITE));
						userPostComposite
								.setBackgroundMode(SWT.INHERIT_DEFAULT);
						GridData gridData = new GridData();
						gridData.widthHint = Controller.getWindowWidth() -10;
						userPostComposite.setLayoutData(gridData);

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
						labelUserAvatar.setImage(resize(new Image(Display.getCurrent(), Controller.getUsersAvatar().get(posts[j].getUser().Username),SWT.IMAGE_COPY),48,48)); 


						if (!posts[j].getUser().Username.equals(Controller
								.getCurrentUser().Username)) {
							labelUserAvatar.setCursor(new Cursor(Display
									.getCurrent(), SWT.CURSOR_HAND));
							labelUserAvatar.setToolTipText("View "
									+ posts[j].getUser().Username + " Timeline");
							labelUserAvatar.setData("User_data",
									posts[j].getUser().Username);
							labelUserAvatar.setData("ID_action",
									"labelAvatarLink");
							labelUserAvatar.addListener(SWT.MouseDown, azioni);
						}

						Label username = new Label(userPostComposite, SWT.None);
						username.setText(posts[j].getUser().Username);
						username.setFont(new Font(Controller.getWindow()
								.getDisplay(), "Calibri", 15, SWT.BOLD));
						if (!posts[j].getUser().Username.equals(Controller
								.getCurrentUser().Username)) {
							username.setForeground(new Color(Display
									.getCurrent(), 56, 149, 184));
							username.setCursor(new Cursor(Display.getCurrent(),
									SWT.CURSOR_HAND));
							username.setToolTipText("View "
									+ posts[j].getUser().Username + " Timeline");
							username.setData("User_data", posts[j].getUser());
							username.setData("ID_action", "usernameLink");
							username.addListener(SWT.MouseDown, azioni);
						} else {
							username.setForeground(new Color(Display
									.getCurrent(), 97, 91, 91));
						}
						gridData = new GridData();
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

						Label messageDate = new Label(userPostComposite,
								SWT.None);
						messageDate.setForeground(new Color(Display
								.getCurrent(), 140, 140, 140));

						if (days > 1 && days < 30) {
							messageDate.setText("About " + days
									+ " days ago from "
									+ posts[j].getService().getName());
						} else if (days > 30) {
							messageDate.setText("More than one month ago from "
									+ posts[j].getService().getName());
						} else if (days == 1) {
							messageDate.setText("About " + days
									+ " day ago from "
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
										messageDate.setText("About "
												+ seconds
												+ " seconds ago from "
												+ posts[j].getService()
														.getName());
									} else if (seconds == 1) {
										messageDate.setText("About "
												+ seconds
												+ " second ago from "
												+ posts[j].getService()
														.getName());
									} else {
										messageDate
												.setText("Few seconds ago from "
														+ posts[j].getService()
																.getName());
									}
								}
							}
						}

						messageDate.setFont(new Font(Controller.getWindow()
								.getDisplay(), "Calibri", 8, SWT.ITALIC));
						gridData = new GridData();
						gridData.grabExcessHorizontalSpace = true;
						gridData.horizontalAlignment = GridData.BEGINNING;
						messageDate.setLayoutData(gridData);

						Label labelhidden = new Label(((Composite) uiData
								.get("userPostMaster")), SWT.None);
						labelhidden.setText("prova");
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

				setLastId(posts[i].Id);

				System.out.println("getLastId aggiornamento " + getLastId());

			}

			((Label) uiData.get("labelDownloadPost")).setVisible(false);
			
			((ProgressBar) uiData.get("pbar")).setVisible(false);
			((ProgressBar) uiData.get("pbar")).setSelection(0);
			
			 WPost[] newPosts =
			 Controller.getProxy().GetIterationTimeline(Controller.getCurrentUser().Username,
			 Controller.getCurrentUserPassword(),0,getLastId());
			
			/*System.out.println("Nuovi Post trovati " + newPosts.length
					+ " con id " + getLastId());*/
			Composite otherPostWarning = new Composite(
					((Composite) uiData.get("userPostMaster")), SWT.None);
			otherPostWarning.setLayout(new GridLayout(1, false));
			otherPostWarning.setBackground(Display.getCurrent().getSystemColor(
					SWT.COLOR_WHITE));
			GridData gridData = new GridData();
			gridData.horizontalSpan = 2;
			gridData.grabExcessHorizontalSpace = true;
			gridData.horizontalAlignment = GridData.FILL;
			otherPostWarning.setLayoutData(gridData);

			if (newPosts == null) {
				newPosts = new WPost[0];
			}

			if (newPosts.length > 0) {
				Link otherPostAvailable = new Link(otherPostWarning, SWT.NONE);
				otherPostAvailable.setCursor(new Cursor(Display.getCurrent(),
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

				otherPostAvailable.addListener(SWT.Selection,
						((Listener) uiData.get("action")));
				otherPostAvailable.setData("ID_action", "otherPostAvailable");

			} else {
				Label noPostAvailable = new Label(otherPostWarning, SWT.NONE);
				noPostAvailable.setText("There are no post in the cache.\n Please try again in two minutes.");
				noPostAvailable.setBackground(Display.getCurrent()
						.getSystemColor(SWT.COLOR_WHITE));
				noPostAvailable.setFont(new Font(Controller.getWindow()
						.getDisplay(), "Calibri", 10, SWT.None));
				gridData = new GridData();
				gridData.grabExcessHorizontalSpace = true;
				gridData.horizontalAlignment = GridData.CENTER;
				noPostAvailable.setLayoutData(gridData);
			}

			((ScrolledComposite) uiData.get("superUserPostMaster")).layout();
			((ScrolledComposite) uiData.get("superUserPostMaster")).redraw();
		
			((Composite) uiData.get("userPostMaster")).layout();
			((Composite) uiData.get("userPostMaster")).redraw();
			
			}
			else
			{
				Controller.openConnectionLostPanel("Connection Lost! \n You will be redirected to Login page."); 	
			}
			break;

		case "btnSendMessage":
			if(Controller.getProxy().IsWebServiceRunning())
			{
			String userMessage = null;

			if (!InterceptingFilter.verifyText(((Text) uiData
					.get("textMessage")).getText())) {
				uiData.put("alert", "message empty");
				MessageBox messageBox2 = new MessageBox(Controller.getWindow()
						.getShell(), SWT.ICON_ERROR | SWT.OK);
				messageBox2
						.setMessage("The message is empty, please try again.");
				messageBox2.setText("SocialCDEforEclipse Message");
				messageBox2.open();
			} else {
				userMessage = ((Text) uiData.get("textMessage")).getText();

				//escaping special characters
				userMessage = StringEscapeUtils.escapeJava(userMessage);
				System.out.println("User Message: " + userMessage);
				
				
				if (!Controller.getProxy().Post(
						Controller.getCurrentUser().Username,
						Controller.getCurrentUserPassword(), userMessage)) {
					uiData.put("alert", "connection problem");
					MessageBox messageBox2 = new MessageBox(Controller
							.getWindow().getShell(), SWT.ICON_ERROR | SWT.OK);
					messageBox2
							.setMessage("Something was wrong, please try again.");
					messageBox2.setText("SocialCDEforEclipse Message");
					messageBox2.open();
				} else {
					uiData.put("alert", "");
					 WPost[]	iterationTimelinePost = Controller.getProxy().GetUserTimeline(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), Controller.getCurrentUser().Username); 
					    
					 ((Text) uiData.get("textMessage")).setText(""); 
					    
					 
					 System.out.println("Inizio creazione messaggio"); 
					 
						Composite userPostComposite = new Composite(
								((Composite) uiData.get("userPostMaster")),
								SWT.None);
						
					userPostComposite.setData("IdPost",iterationTimelinePost[0].Id); 
					userPostComposite.setLayout(new GridLayout(2, false));
					userPostComposite.setBackground(Display.getCurrent()
							.getSystemColor(SWT.COLOR_WHITE));
					userPostComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
					GridData  gridData = new GridData();
					gridData.grabExcessHorizontalSpace = true;
					gridData.horizontalAlignment = GridData.FILL;
					userPostComposite.setLayoutData(gridData);

					Label labelUserAvatar = new Label(userPostComposite,
							SWT.NONE);
					gridData = new GridData();
					gridData.verticalSpan = 3;
					gridData.verticalAlignment = SWT.BEGINNING;
					labelUserAvatar.setLayoutData(gridData);
					labelUserAvatar.setData("ID_action", "labelAvatar");
					
					if(Controller.getUsersAvatar().get(Controller.getCurrentUser().Username) == null)
					{
						Controller.getUsersAvatar().put(Controller.getCurrentUser().Username, getUserImage(Controller.getCurrentUser().Avatar)); 
					}
					labelUserAvatar.setImage(resize(new Image(Display.getCurrent(), Controller.getUsersAvatar().get(Controller.getCurrentUser().Username),SWT.IMAGE_COPY),48,48)); 

					Label username = new Label(userPostComposite, SWT.None);
					username.setForeground(new Color(Display.getCurrent(), 97,
							91, 91));
					username.setText(Controller.getCurrentUser().Username);
					username.setFont(new Font(Controller.getWindow()
							.getDisplay(), "Calibri", 15, SWT.BOLD));
					gridData = new GridData();
					gridData.grabExcessHorizontalSpace = false;
					gridData.horizontalAlignment = GridData.FILL;
					username.setLayoutData(gridData);

					Link message = new Link(userPostComposite, SWT.WRAP);
					
					//reading message unescaping the text for correct visualization
					message.setText(findLink(StringEscapeUtils.unescapeJava(userMessage)));
					
					gridData = new GridData();
					gridData.widthHint = Controller.getWindowWidth() - 150;
					message.setLayoutData(gridData);

					Label messageDate = new Label(userPostComposite, SWT.None);
					messageDate.setForeground(new Color(Display.getCurrent(),
							140, 140, 140));
					messageDate.setText("About one minutes ago from SocialTFS");

					messageDate.setFont(new Font(Controller.getWindow()
							.getDisplay(), "Calibri", 8, SWT.ITALIC));
					gridData = new GridData();
					gridData.grabExcessHorizontalSpace = true;
					gridData.horizontalAlignment = GridData.BEGINNING;
					messageDate.setLayoutData(gridData);

					/*Label labelhidden = new Label(
							((Composite) uiData.get("userPostMaster")),
							SWT.None);
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

					System.out.println("Fine creazione messaggio"); 
					
					userPostComposite.moveAbove(((Composite) uiData
							.get("userPostMaster")).getChildren()[0]);
					
					System.out.println("Fine spostamento"); 
					
					((Composite) uiData.get("userPostMaster")).redraw();
					((Composite) uiData.get("userPostMaster")).layout();
					Controller.getWindow().layout(); 
					
					System.out.println("Fine redraw");
				}
			}
			}
			else
			{
				Controller.openConnectionLostPanel("Connection Lost! \n You will be redirected to Login page."); 	
			}
			break;

		default:
			break;
		}
	}

}
