package it.uniba.di.socialcdeforeclipse.action;

import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.print.attribute.standard.MediaSize.Other;

import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.popup.HideUserPanel;
import it.uniba.di.socialcdeforeclipse.popup.SettingServicePanel;
import it.uniba.di.socialcdeforeclipse.popup.SkillsPanel;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.WPost;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.WUser;
import it.uniba.di.socialcdeforeclipse.views.SquareButtonService;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Widget;

public class ActionDynamicUserTimeline {

	private final InputStream PATH_DEFAULT_AVATAR = this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png");
	
	public Image get_ImageStream(InputStream stream)
	{
		return  new Image(Controller.getWindow().getDisplay(),stream); 
	}
	
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
	
	
	public ActionDynamicUserTimeline(HashMap<String, Object> uiData)
	{
		String widgetName = uiData.get("ID_action").toString(); 
		int type = (int) uiData.get("Event_type"); 
		Event event = (Event)  uiData.get("Event");
		final WUser user_selected = ( (WUser) uiData.get("userSelected") );
		
		
		switch (widgetName) {
		case "labelBack":
			Controller.selectDynamicWindow(2); 
			break;
			
		case "labelFollow":
			 
			System.out.println(" Follow chiamato");
			if(Controller.getProxy().Follow(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), user_selected.Id))
			{
				uiData.put("error", false);
				System.out.println("Follow riuscito"); 
			  Label	labelUnfollow = new Label(((Label) uiData.get("labelFollow")).getParent()  ,SWT.RIGHT); 
				labelUnfollow.setImage(Controller.getDynamicUserWindow().get_ImageStream(this.getClass().getClassLoader().getResourceAsStream("images/UnFollow.png")));
				labelUnfollow.setCursor( new Cursor(Display.getCurrent(), SWT.CURSOR_HAND)); 
				labelUnfollow.setData("user", user_selected); 
				Rectangle dimensionLabel =  Controller.getDynamicUserWindow().getLabelFollow().getBounds();
				 Controller.getDynamicUserWindow().getLabelFollow().dispose(); 
				 Controller.getProfilePanel().getComposite_dinamic().redraw(dimensionLabel.x, dimensionLabel.y, dimensionLabel.width, dimensionLabel.height, true);
				 Controller.getProfilePanel().getComposite_dinamic().layout(); 
				 labelUnfollow.setBounds(dimensionLabel);
				 labelUnfollow.setData("ID_action", "labelUnfollow"); 
				 labelUnfollow.addListener(SWT.MouseDown,  ((Listener) uiData.get("action")));
				 uiData.put("imageUnFollow", true); 
				 uiData.put("imageFollow", false);	
				 Controller.getDynamicUserWindow().setLabelFollow(labelUnfollow); 
				Controller.getProfilePanel().getComposite_dinamic().redraw(dimensionLabel.x, dimensionLabel.y, dimensionLabel.width, dimensionLabel.height, true);
				Controller.getProfilePanel().getComposite_dinamic().layout(); 
			}
			else
			{
				uiData.put("error", true);
				MessageBox messageBox = new MessageBox(Controller.getWindow().getShell(), SWT.ICON_WARNING  | SWT.YES);
		        messageBox.setMessage("Something was wrong, please try again!");
		        messageBox.setText("SocialCDEforEclipse Message");
		        messageBox.open();
		         
			}
			break;
			
		case "labelUnfollow":
		
			System.out.println(" Unfollow chiamato");
			if(Controller.getProxy().UnFollow(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), user_selected.Id))
			{
				uiData.put("error", false);
				System.out.println("unFollow riuscito"); 
				Label labelFollow = new Label(((Label) uiData.get("labelFollow")).getParent(),SWT.RIGHT); 
				labelFollow.setImage(Controller.getDynamicUserWindow().get_ImageStream(this.getClass().getClassLoader().getResourceAsStream("images/Follow.png")));
				labelFollow.setCursor( new Cursor(Display.getCurrent(), SWT.CURSOR_HAND)); 
				labelFollow.setData("user", user_selected); 
				Rectangle dimensionLabel =  Controller.getDynamicUserWindow().getLabelFollow().getBounds();
				 Controller.getDynamicUserWindow().getLabelFollow().dispose(); 
				 Controller.getProfilePanel().getComposite_dinamic().redraw(dimensionLabel.x, dimensionLabel.y, dimensionLabel.width, dimensionLabel.height, true);
				 Controller.getProfilePanel().getComposite_dinamic().layout(); 
				 labelFollow.setBounds(dimensionLabel); 
				 labelFollow.setData("ID_action", "labelFollow");
				 labelFollow.addListener(SWT.MouseDown, ((Listener) uiData.get("action")));
				 //uiData.put("labelFollow", labelFollow); 
				 uiData.put("imageUnFollow", false); 
				 uiData.put("imageFollow", true);
				 Controller.getDynamicUserWindow().setLabelFollow(labelFollow); 
				 Controller.getProfilePanel().getComposite_dinamic().redraw(dimensionLabel.x, dimensionLabel.y, dimensionLabel.width, dimensionLabel.height, true);
				 Controller.getProfilePanel().getComposite_dinamic().layout(); 
			}
			else
			{
				uiData.put("error", true);
				MessageBox messageBox = new MessageBox(Controller.getWindow().getShell(), SWT.ICON_WARNING  | SWT.YES);
		        messageBox.setMessage("Something was wrong, please try again!");
		        messageBox.setText("SocialCDEforEclipse Message");
		        messageBox.open();
		         
			}
			
			break;
			
		case "labelSkills":
			
			final SkillsPanel skillPanel = new SkillsPanel(); 
			skillPanel.setxCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x); 
			skillPanel.setyCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y); 
			skillPanel.setxCoordinateWithOffset(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x + (Controller.getWindow().getBounds().width - 300) / 2); 
			skillPanel.setyCoordinateWithOffset(150); 
			skillPanel.setUser_selected(user_selected); 
			skillPanel.setBackListener(new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					// TODO Auto-generated method stub
					skillPanel.dispose(Controller.getWindow());
				}
			}); 
			skillPanel.inizialize(Controller.getWindow()); 
			
			break;
			
		case "labelHide":
			
			final HideUserPanel hideUserSetting = new HideUserPanel(); 
			hideUserSetting.setxCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x ); 
			hideUserSetting.setyCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y ); 
			hideUserSetting.setxCoordinateWithOffset(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x  - 30 + (Controller.getWindow().getBounds().width - 300) / 2); 
			hideUserSetting.setyCoordinateWithOffset(150);
			hideUserSetting.setUser_selected(user_selected);
			//SquareButtonService.flagDimension = false; 
			hideUserSetting.setBtnCancelListener(new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					// TODO Auto-generated method stub
					
			       hideUserSetting.dispose(null); 
			       //SquareButtonService.flagDimension = false; 
			       
			       
			       
			       
			       
			       
				}
			});
			
			hideUserSetting.setBtnSaveListener(new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					// TODO Auto-generated method stub
				
					HashMap<String, Button> btnCheckbox =	hideUserSetting.getCheckboxCreated(); 
					
					if(!Controller.getProxy().UpdateHiddenUser(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), user_selected.Id, ((Button) btnCheckbox.get("suggestion")).getSelection(), ((Button) btnCheckbox.get("iteration")).getSelection(), ((Button) btnCheckbox.get("interactive")).getSelection())  )
					{
						MessageBox messageBox = new MessageBox(Controller.getWindow().getShell(), SWT.ICON_WARNING  | SWT.YES);
				        messageBox.setMessage("Something was wrong, please try again!");
				        messageBox.setText("SocialCDEforEclipse Message");
				        messageBox.open();
					}
					
					 hideUserSetting.dispose(null); 
					
				}
			});
			
			hideUserSetting.inizialize(Controller.getWindow()); 
			 
			break;
		case "otherPostAvailable":
			
			 System.out.println("Evento otherPostAvailable lanciato " + ((Composite)  uiData.get("userPostMaster")).getChildren().length); 
			 
			
			 
			 WPost[] posts = Controller.getProxy().GetUserTimeline(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), ( (WUser) uiData.get("userSelected") ).Username,((long)  uiData.get("lastPostId")),0);
			 
			 
			 System.out.println("number post " +  posts.length); 
			 
			 
			 uiData.put("firstPostId", uiData.get("lastPostId")); 
				
				
				for(int i=0;i< posts.length; i++)
				{

					
					Composite userPostComposite = new Composite(((Composite)  uiData.get("userPostMaster")), SWT.BORDER);
					userPostComposite.setLayout(new GridLayout(2, false)); 
					GridData gridData = new GridData(); 
					gridData.grabExcessHorizontalSpace = true; 
					gridData.horizontalAlignment = GridData.FILL; 
					userPostComposite.setLayoutData(gridData); 
					
					Label labelUserAvatar = new Label(userPostComposite,SWT.NONE); 
					gridData = new GridData();
					gridData.verticalSpan = 2; 
					labelUserAvatar.setLayoutData(gridData); 
					labelUserAvatar.setData("ID_action", "labelAvatar");
					 
					
						try {
							labelUserAvatar.setImage(get_ImageStream(new URL( posts[i].getUser().Avatar).openStream()));
							labelUserAvatar.setImage(resize(labelUserAvatar.getImage(), 75, 75)); 
							 
						} catch (IOException e) {
							// TODO Auto-generated catch block
							//System.out.println("Eccezione lanciata"); 
							labelUserAvatar.setImage(get_ImageStream(this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png")));
							labelUserAvatar.setImage(resize(labelUserAvatar.getImage(), 75, 75));
							//e.printStackTrace();
						} 
						catch (NullPointerException e) {
							// TODO: handle exception
							labelUserAvatar.setImage(get_ImageStream(this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png"))); 
							labelUserAvatar.setImage(resize(labelUserAvatar.getImage(), 75, 75));
						}
						
					Label username = new Label(userPostComposite, SWT.None); 
					username.setText(posts[i].getUser().Username); 
					username.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 15, SWT.BOLD ));  
					gridData = new GridData(); 
					gridData.grabExcessHorizontalSpace = false; 
					gridData.horizontalAlignment = GridData.FILL;
					username.setLayoutData(gridData); 
					
					Label message = new Label(userPostComposite, SWT.None | SWT.WRAP); 
					message.setText(posts[i].getMessage()); 
					gridData = new GridData(); 
					gridData.grabExcessHorizontalSpace = true; 
					gridData.horizontalAlignment = GridData.FILL; 
					gridData.horizontalSpan = 2; 
					gridData.widthHint = 100; 
					message.setLayoutData(gridData); 
					
					Calendar nowDate = Calendar.getInstance(); 
					Calendar dateSelected = posts[i].getCreateAt(); 
					long millisDiff = nowDate.getTime().getTime() - dateSelected.getTime().getTime();
					
					int seconds = (int) (millisDiff / 1000 % 60);
					int minutes = (int) (millisDiff / 60000 % 60);
					int hours = (int) (millisDiff / 3600000 % 24);
					int days = (int) (millisDiff / 86400000);
					
					Label messageDate = new Label(userPostComposite, SWT.None); 
					
					if(days > 1 && days < 30)
					{
						messageDate.setText("About " + days + " days ago from " + posts[i].getService().getName());
					}
					else if(days > 30)
					{
						messageDate.setText("More than one month ago from " + posts[i].getService().getName());
					}
					else if(days == 1)
					{
						messageDate.setText("About " + days + " day ago from " + posts[i].getService().getName());
					}
					else
					{
						if( hours > 1)
						{
							messageDate.setText("About " + hours + " hours ago from " + posts[i].getService().getName());
						}
						else if(hours == 1)
						{
							messageDate.setText("About " + hours + " hour ago from " + posts[i].getService().getName());
						}
						else
						{

							if( minutes > 1)
							{
								messageDate.setText("About " + minutes + " minutes ago from " + posts[i].getService().getName());
							}
							else if(minutes == 1)
							{
								messageDate.setText("About " + minutes + " minute ago from " + posts[i].getService().getName());
							}
							else
							{

								if( seconds > 1)
								{
									messageDate.setText("About " + seconds + " seconds ago from " + posts[i].getService().getName());
								}
								else if(seconds == 1)
								{
									messageDate.setText("About " + seconds + " second ago from " + posts[i].getService().getName());
								}
								else
								{
									messageDate.setText("Few seconds ago from " + posts[i].getService().getName());
								}
							}
						}
					}
					 
					messageDate.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 8, SWT.ITALIC ));
					gridData = new GridData(); 
					gridData.grabExcessHorizontalSpace = true; 
					gridData.horizontalAlignment = GridData.END; 
					gridData.horizontalSpan = 2; 
					messageDate.setLayoutData(gridData); 
					
					
				 uiData.put("lastPostId",posts[i].Id);
				}
				System.out.println("Altezza impostata " + Controller.getWindowHeight() + (150 * ((Composite)  uiData.get("userPostMaster")).getChildren().length) ); 
				Controller.setScrollHeight(Controller.getWindowHeight() + (250 * ((Composite)  uiData.get("userPostMaster")).getChildren().length)  );
				((ScrolledComposite)	Controller.getWindow().getParent()).setMinSize(Controller.getWindowWidth()-50, Controller.getScrollHeight());

				 WPost[] newPosts = Controller.getProxy().GetUserTimeline(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), ( (WUser) uiData.get("userSelected") ).Username,((long)  uiData.get("lastPostId")),0);
				 System.out.println("Nuovi post disponibili " + newPosts.length);
			if( newPosts.length == 0)
			{
				System.out.println("Other post 0 rilevato"); 
				((Link)  uiData.get("otherPostAvailable")).setVisible(false);
				Label noPostAvailable = new Label(((Composite)  uiData.get("otherPostWarning")),SWT.NONE); 
				noPostAvailable.setText("There are no older post available.");
				noPostAvailable.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.None));
				GridData  gridData = new GridData();
				gridData.grabExcessHorizontalSpace = true; 
				gridData.horizontalAlignment = GridData.CENTER;
				noPostAvailable.setLayoutData(gridData); 
			}
			
			((Composite)  uiData.get("userPostMaster")).layout();
			((Composite)  uiData.get("userPostMaster")).redraw();
			((Composite)  uiData.get("otherPostWarning")).layout();
			((Composite)  uiData.get("otherPostWarning")).redraw();
			Controller.getProfilePanel().getComposite_dinamic().layout(); 
			Controller.getProfilePanel().getComposite_dinamic().redraw();
			((ScrolledComposite)	Controller.getWindow().getParent()).layout(); 
			((ScrolledComposite)	Controller.getWindow().getParent()).redraw(); 
			
			
			System.out.println("N. figli " + ((Composite)  uiData.get("userPostMaster")).getChildren().length ); 
			 
			break;

		default:
			break;
		}
		
	}
	
}
