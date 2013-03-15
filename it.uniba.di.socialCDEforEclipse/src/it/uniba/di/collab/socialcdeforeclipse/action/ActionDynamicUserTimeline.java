package it.uniba.di.collab.socialcdeforeclipse.action;

import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.print.attribute.standard.MediaSize.Other;

import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.object.SquareButtonService;
import it.uniba.di.collab.socialcdeforeclipse.popup.HideUserPanel;
import it.uniba.di.collab.socialcdeforeclipse.popup.SettingServicePanel;
import it.uniba.di.collab.socialcdeforeclipse.popup.SkillsPanel;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.WPost;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.WUser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class ActionDynamicUserTimeline {

	private static long lastId;
	private final InputStream PATH_DEFAULT_AVATAR = this.getClass()
			.getClassLoader().getResourceAsStream("images/DefaultAvatar.png");

	public static long getLastId() {
		return lastId;
	}

	public static void setLastId(long lastId) {
		ActionDynamicUserTimeline.lastId = lastId;
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

	public ActionDynamicUserTimeline(final HashMap<String, Object> uiData) {
		String widgetName = uiData.get("ID_action").toString();
		int type = (int) uiData.get("Event_type");
		Event event = (Event) uiData.get("Event");
		final WUser user_selected = ((WUser) uiData.get("userSelected"));

		switch (widgetName) {
		case "labelBack":
			if(Controller.getProxy().IsWebServiceRunning())
			{
				Controller.selectDynamicWindow(2);
			}
			else
			{
				Controller.openConnectionLostPanel("Connection Lost! You will be redirected to Login page."); 	
			}
			break;

		case "labelFollow":
			if(Controller.getProxy().IsWebServiceRunning())
			{
			if (Controller.getProxy().Follow(
					Controller.getCurrentUser().Username,
					Controller.getCurrentUserPassword(), user_selected.Id)) {
				uiData.put("error", false);

				Label labelUnfollow = new Label(
						((Label) uiData.get("labelFollow")).getParent(),
						SWT.RIGHT);
				labelUnfollow.setImage(Controller.getDynamicUserWindow()
						.get_ImageStream(
								this.getClass()
										.getClassLoader()
										.getResourceAsStream(
												"images/Unfollow.png")));
				labelUnfollow.setCursor(new Cursor(Display.getCurrent(),
						SWT.CURSOR_HAND));
				labelUnfollow.setData("user", user_selected);
				Rectangle dimensionLabel = Controller.getDynamicUserWindow()
						.getLabelFollow().getBounds();
				Controller.getDynamicUserWindow().getLabelFollow().dispose();
				Controller
						.getProfilePanel()
						.getComposite_dinamic()
						.redraw(dimensionLabel.x, dimensionLabel.y,
								dimensionLabel.width, dimensionLabel.height,
								true);
				Controller.getProfilePanel().getComposite_dinamic().layout();
				labelUnfollow.setBounds(dimensionLabel);
				labelUnfollow.setData("ID_action", "labelUnfollow");
				labelUnfollow.addListener(SWT.MouseDown,
						((Listener) uiData.get("action")));
				uiData.put("imageUnFollow", true);
				uiData.put("imageFollow", false);
				Controller.getDynamicUserWindow().setLabelFollow(labelUnfollow);
				Controller
						.getProfilePanel()
						.getComposite_dinamic()
						.redraw(dimensionLabel.x, dimensionLabel.y,
								dimensionLabel.width, dimensionLabel.height,
								true);
				Controller.getProfilePanel().getComposite_dinamic().layout();
			} else {
				uiData.put("error", true);
				MessageBox messageBox = new MessageBox(Controller.getWindow()
						.getShell(), SWT.ICON_WARNING | SWT.YES);
				messageBox.setMessage("Something was wrong, please try again!");
				messageBox.setText("SocialCDEforEclipse Message");
				messageBox.open();

			}
			}
			else
			{
				Controller.openConnectionLostPanel("Connection Lost! You will be redirected to Login page."); 	
			}
			break;

		case "labelUnfollow":
			if(Controller.getProxy().IsWebServiceRunning())
			{
				if (Controller.getProxy().UnFollow(
						Controller.getCurrentUser().Username,
						Controller.getCurrentUserPassword(), user_selected.Id)) {
					uiData.put("error", false);
	
					Label labelFollow = new Label(
							((Label) uiData.get("labelFollow")).getParent(),
							SWT.RIGHT);
					labelFollow.setImage(Controller.getDynamicUserWindow()
							.get_ImageStream(
									this.getClass()
											.getClassLoader()
											.getResourceAsStream(
													"images/Follow.png")));
					labelFollow.setCursor(new Cursor(Display.getCurrent(),
							SWT.CURSOR_HAND));
					labelFollow.setData("user", user_selected);
					Rectangle dimensionLabel = Controller.getDynamicUserWindow()
							.getLabelFollow().getBounds();
					Controller.getDynamicUserWindow().getLabelFollow().dispose();
					Controller
							.getProfilePanel()
							.getComposite_dinamic()
							.redraw(dimensionLabel.x, dimensionLabel.y,
									dimensionLabel.width, dimensionLabel.height,
									true);
					Controller.getProfilePanel().getComposite_dinamic().layout();
					labelFollow.setBounds(dimensionLabel);
					labelFollow.setData("ID_action", "labelFollow");
					labelFollow.addListener(SWT.MouseDown,
							((Listener) uiData.get("action")));
					
					uiData.put("imageUnFollow", false);
					uiData.put("imageFollow", true);
					Controller.getDynamicUserWindow().setLabelFollow(labelFollow);
					Controller
							.getProfilePanel()
							.getComposite_dinamic()
							.redraw(dimensionLabel.x, dimensionLabel.y,
									dimensionLabel.width, dimensionLabel.height,
									true);
					Controller.getProfilePanel().getComposite_dinamic().layout();
				} else {
					uiData.put("error", true);
					MessageBox messageBox = new MessageBox(Controller.getWindow()
							.getShell(), SWT.ICON_WARNING | SWT.YES);
					messageBox.setMessage("Something was wrong, please try again!");
					messageBox.setText("SocialCDEforEclipse Message");
					messageBox.open();
	
				}
			}
			else
			{
				Controller.openConnectionLostPanel("Connection Lost! You will be redirected to Login page."); 	
			}
			break;

		case "labelSkills":
			if(Controller.getProxy().IsWebServiceRunning())
			{
			final SkillsPanel skillPanel = new SkillsPanel();
			skillPanel.setxCoordinate(Controller.getWindow().toDisplay(
					Controller.getWindow().getLocation().x,
					Controller.getWindow().getLocation().y).x);
			skillPanel.setyCoordinate(Controller.getWindow().toDisplay(
					Controller.getWindow().getLocation().x,
					Controller.getWindow().getLocation().y).y);
			skillPanel.setxCoordinateWithOffset(Controller.getWindow()
					.toDisplay(Controller.getWindow().getLocation().x,
							Controller.getWindow().getLocation().y).x + 10);
			
			skillPanel.setyCoordinateWithOffset(150);
			skillPanel.setUser_selected(user_selected);
			skillPanel.setBackListener(new Listener() {

				@Override
				public void handleEvent(Event event) {
					
					skillPanel.dispose(Controller.getWindow());
				}
			});
			skillPanel.inizialize(Controller.getWindow());
			}
			else
			{
				Controller.openConnectionLostPanel("Connection Lost! You will be redirected to Login page."); 	
			}
			break;

		case "labelHide":
			if(Controller.getProxy().IsWebServiceRunning())
			{
			final HideUserPanel hideUserSetting = new HideUserPanel();
			hideUserSetting.setxCoordinate(Controller.getWindow().toDisplay(
					Controller.getWindow().getLocation().x,
					Controller.getWindow().getLocation().y).x);
			hideUserSetting.setyCoordinate(Controller.getWindow().toDisplay(
					Controller.getWindow().getLocation().x,
					Controller.getWindow().getLocation().y).y);
			hideUserSetting.setxCoordinateWithOffset(Controller.getWindow()
							.toDisplay(Controller.getWindow().getLocation().x,
									Controller.getWindow().getLocation().y).x + 10);
			hideUserSetting.setyCoordinateWithOffset(150);
			hideUserSetting.setUser_selected(user_selected);
			
			hideUserSetting.setBtnCancelListener(new Listener() {

				@Override
				public void handleEvent(Event event) {
					

					hideUserSetting.dispose(null);
					

				}
			});

			hideUserSetting.setBtnSaveListener(new Listener() {

				@Override
				public void handleEvent(Event event) {
					

					HashMap<String, Button> btnCheckbox = hideUserSetting
							.getCheckboxCreated();

					if (!Controller.getProxy().UpdateHiddenUser(
							Controller.getCurrentUser().Username,
							Controller.getCurrentUserPassword(),
							user_selected.Id,
							((Button) btnCheckbox.get("suggestion"))
									.getSelection(),
							((Button) btnCheckbox.get("iteration"))
									.getSelection(),
							((Button) btnCheckbox.get("interactive"))
									.getSelection())) {
						MessageBox messageBox = new MessageBox(Controller
								.getWindow().getShell(), SWT.ICON_WARNING
								| SWT.YES);
						messageBox
								.setMessage("Something was wrong, please try again!");
						messageBox.setText("SocialCDEforEclipse Message");
						messageBox.open();
					}

					hideUserSetting.dispose(null);

				}
			});
			uiData.put("HideUserPanel", hideUserSetting);
			hideUserSetting.inizialize(Controller.getWindow());
			}
			else
			{
				Controller.openConnectionLostPanel("Connection Lost! You will be redirected to Login page."); 	
			}
			break;
		case "otherPostAvailable":
			if(Controller.getProxy().IsWebServiceRunning())
			{
			((Label) uiData.get("labelDownloadPost")).setVisible(true);
			((Label) uiData.get("labelDownloadPost")).redraw();
			Display.getCurrent().update();
			((ProgressBar) uiData.get("pbar")).setVisible(true);

			final WPost[] posts = Controller.getProxy().GetUserTimeline(
					Controller.getCurrentUser().Username,
					Controller.getCurrentUserPassword(),
					((WUser) uiData.get("userSelected")).Username, 0,
					getLastId());

			if (posts.length > 0) {
				((Composite) uiData.get("userPostMaster")).getChildren()[((Composite) uiData
						.get("userPostMaster")).getChildren().length - 1]
						.dispose();
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
					
						userPostComposite.setLayout(new GridLayout(2, false));
						userPostComposite.setBackground(Display.getCurrent()
								.getSystemColor(SWT.COLOR_WHITE));
						userPostComposite
								.setBackgroundMode(SWT.INHERIT_DEFAULT);
						GridData gridData = new GridData();
						gridData.grabExcessHorizontalSpace = true;
						gridData.horizontalAlignment = GridData.FILL;
						userPostComposite.setLayoutData(gridData);

						Label labelUserAvatar = new Label(userPostComposite,
								SWT.NONE);
						gridData = new GridData();
						gridData.verticalSpan = 3;
						labelUserAvatar.setLayoutData(gridData);
						labelUserAvatar.setData("ID_action", "labelAvatar");
						
						if(Controller.getUsersAvatar().get(posts[j].getUser().Username) == null)
						{
							Controller.getUsersAvatar().put(posts[j].getUser().Username, getUserImage(posts[j].getUser().Avatar));
						}
						
						labelUserAvatar.setImage(resize(new Image(Display.getCurrent(),Controller.getUsersAvatar().get(posts[j].getUser().Username),SWT.IMAGE_COPY), 75, 75)); 

						Label username = new Label(userPostComposite, SWT.None);
						username.setText(posts[j].getUser().Username);
						username.setFont(new Font(Controller.getWindow()
								.getDisplay(), "Calibri", 15, SWT.BOLD));
						username.setForeground(new Color(Display.getCurrent(),
								97, 91, 91));
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

						/*Label labelhidden = new Label(((Composite) uiData
								.get("userPostMaster")), SWT.None);
						labelhidden.setText("prova");
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

				setLastId(posts[i].Id);

				//System.out.println("getLastId aggiornamento " + getLastId());

			}

			((Label) uiData.get("labelDownloadPost")).setVisible(false);
		
			((ProgressBar) uiData.get("pbar")).setVisible(false);
			((ProgressBar) uiData.get("pbar")).setSelection(0);
		
			WPost[] newPosts = Controller.getProxy().GetUserTimeline(
					Controller.getCurrentUser().Username,
					Controller.getCurrentUserPassword(),
					((WUser) uiData.get("userSelected")).Username, 0,
					getLastId());

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
				noPostAvailable.setText("There are no older post available.");
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

		default:
			break;
		}

	}

}
