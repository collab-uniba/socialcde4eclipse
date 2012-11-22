package it.uniba.di.socialcdeforeclipse.action;

import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.dynamic.view.InterceptingFilter;
import it.uniba.di.socialcdeforeclipse.shared.library.WPost;
import it.uniba.di.socialcdeforeclipse.shared.library.WUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

public class ActionHomeTimeline {
	private static long lastId; 
	private final InputStream PATH_DEFAULT_AVATAR = this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png");
	
	public static long getLastId() {
		return lastId;
	}

	public static void setLastId(long lastId) {
		ActionHomeTimeline.lastId = lastId;
	}

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
	public ActionHomeTimeline(HashMap<String, Object> uiData)
	{
		String widgetName = uiData.get("ID_action").toString(); 
		int type = (int) uiData.get("Event_type"); 
		Event event = (Event)  uiData.get("Event");
		
		switch (widgetName) {
		case "otherPostAvailable":
			
			 System.out.println("Evento otherPostAvailable lanciato " + ((Composite)  uiData.get("userPostMaster")).getChildren().length + " lastid  " + getLastId()); 
			 
			
			 
			 WPost[] posts = Controller.getProxy().GetHomeTimeline(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(),0,getLastId());
			 
			 System.out.println("getLastId inizio " + getLastId());
			 System.out.println("number post " +  posts.length); 
			 
			 
			 
			   
			  ((Composite)  uiData.get("userPostMaster")).getChildren()[((Composite)  uiData.get("userPostMaster")).getChildren().length - 1].dispose(); 	
			  
			  
				for(int i=0;i< posts.length; i++)
				{

					
					Composite userPostComposite = new Composite(((Composite)  uiData.get("userPostMaster")), SWT.BORDER);
					userPostComposite.setLayout(new GridLayout(2, false)); 
					userPostComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)); 
					userPostComposite.setBackgroundMode(SWT.INHERIT_DEFAULT); 
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
					
					
				  setLastId(posts[i].Id);
				  
				  	Label labelhidden = new Label(((Composite)  uiData.get("userPostMaster")), SWT.None); 
					labelhidden.setText("prova"); 
					labelhidden.setVisible(false); 
				  
				  System.out.println("getLastId aggiornamento " + getLastId());
				}
				//System.out.println("Altezza impostata " + Controller.getWindowHeight() + (150 * ((Composite)  uiData.get("userPostMaster")).getChildren().length) ); 
				//Controller.setScrollHeight(Controller.getWindowHeight() + (250 * ((Composite)  uiData.get("userPostMaster")).getChildren().length)  );
				

				 WPost[] newPosts = Controller.getProxy().GetHomeTimeline(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(),0,getLastId());
				 
				 	Composite otherPostWarning = new Composite(((Composite)  uiData.get("userPostMaster")), SWT.None); 
					otherPostWarning.setLayout(new GridLayout(1,false)); 
					otherPostWarning.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
					GridData gridData = new GridData(); 
					gridData.horizontalSpan = 2;
					gridData.grabExcessHorizontalSpace = true; 
					gridData.horizontalAlignment = GridData.FILL; 
					otherPostWarning.setLayoutData(gridData);
					
					
					
					if(newPosts == null)
					{
						newPosts = new WPost[0]; 
					}
				
					System.out.println("Nuovi post disponibili " + newPosts.length);
					
					if(newPosts.length > 0)
					{
					   Link	otherPostAvailable = new Link(otherPostWarning, SWT.NONE); 
						otherPostAvailable.setCursor( new Cursor(Display.getCurrent(), SWT.CURSOR_HAND));
						otherPostAvailable.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.UNDERLINE_LINK));
						otherPostAvailable.setText("<a>Click to view older posts</a>"); 
						otherPostAvailable.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)); 
						gridData = new GridData();
						gridData.grabExcessHorizontalSpace = true; 
						gridData.horizontalAlignment = GridData.CENTER;
						otherPostAvailable.setLayoutData(gridData); 
						
						otherPostAvailable.addListener(SWT.Selection, ((Listener)  uiData.get("azioni"))); 
						otherPostAvailable.setData("ID_action", "otherPostAvailable");
						
						
						
					}
					else
					{
						Label noPostAvailable = new Label(otherPostWarning,SWT.NONE); 
						noPostAvailable.setText("There are no older post available.");
						noPostAvailable.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)); 
						noPostAvailable.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.None));
						gridData = new GridData();
						gridData.grabExcessHorizontalSpace = true; 
						gridData.horizontalAlignment = GridData.CENTER;
						noPostAvailable.setLayoutData(gridData); 
					}
			
					System.out.println("precedente" + ((ScrolledComposite)	 uiData.get("superUserPostMaster")).getMinHeight());
			((ScrolledComposite)	 uiData.get("superUserPostMaster")).setMinSize(Controller.getWindowWidth()-50, (80 * ((Composite)	 uiData.get("userPostMaster")).getChildren().length));		
			((ScrolledComposite)  uiData.get("superUserPostMaster")).layout();
			((ScrolledComposite)  uiData.get("superUserPostMaster")).redraw();
			((ScrolledComposite)	 uiData.get("superUserPostMaster")).setMinSize(Controller.getWindowWidth()-50, (80 * ((Composite)	 uiData.get("userPostMaster")).getChildren().length));
			((Composite)  uiData.get("userPostMaster")).layout();
			((Composite)  uiData.get("userPostMaster")).redraw();
			((ScrolledComposite)	 uiData.get("superUserPostMaster")).setMinSize(Controller.getWindowWidth()-50, (80 * ((Composite)	 uiData.get("userPostMaster")).getChildren().length));
			System.out.println("successivo " + ((ScrolledComposite)	 uiData.get("superUserPostMaster")).getMinHeight());
			//((Composite)  uiData.get("otherPostWarning")).layout();
			//((Composite)  uiData.get("otherPostWarning")).redraw();
			//Controller.getProfilePanel().getComposite_dinamic().layout(); 
			//Controller.getProfilePanel().getComposite_dinamic().redraw();
			//((ScrolledComposite)	Controller.getWindow().getParent()).layout(); 
			//((ScrolledComposite)	Controller.getWindow().getParent()).redraw(); 
			
			
			System.out.println("N. figli " + ((Composite)  uiData.get("userPostMaster")).getChildren().length ); 
			 
			break;
			
		case "btnSendMessage":
			String userMessage = null;
			
			if(!InterceptingFilter.verifyText( ((Text) uiData.get("textMessage") ).getText()))
			{
				uiData.put("alert", "message empty");
				MessageBox messageBox2 = new MessageBox(Controller.getWindow().getShell(), SWT.ICON_ERROR  | SWT.OK);
		        messageBox2.setMessage("The message is empty, please try again.");
		        messageBox2.setText("SocialCDEforEclipse Message");
		        messageBox2.open();
			}
			else
			{
			userMessage = ((Text) uiData.get("textMessage") ).getText();
			
			if(!Controller.getProxy().Post(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), userMessage))
			{
				uiData.put("alert", "connection problem");
				MessageBox messageBox2 = new MessageBox(Controller.getWindow().getShell(), SWT.ICON_ERROR  | SWT.OK);
		        messageBox2.setMessage("Something was wrong, please try again.");
		        messageBox2.setText("SocialCDEforEclipse Message");
		        messageBox2.open();
			}
			else
			{
				uiData.put("alert","");
			Composite userPostComposite = new Composite(((Composite)  uiData.get("userPostMaster")), SWT.BORDER);
			userPostComposite.setLayout(new GridLayout(2, false)); 
			userPostComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)); 
			userPostComposite.setBackgroundMode(SWT.INHERIT_DEFAULT); 
		    gridData = new GridData(); 
			gridData.grabExcessHorizontalSpace = true; 
			gridData.horizontalAlignment = GridData.FILL; 
			userPostComposite.setLayoutData(gridData); 
			
			Label labelUserAvatar = new Label(userPostComposite,SWT.NONE); 
			gridData = new GridData();
			gridData.verticalSpan = 2; 
			labelUserAvatar.setLayoutData(gridData); 
			labelUserAvatar.setData("ID_action", "labelAvatar");
			 
			
				try {
					labelUserAvatar.setImage(get_ImageStream(new URL( Controller.getCurrentUser().Avatar).openStream()));
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
			username.setText(Controller.getCurrentUser().Username); 
			username.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 15, SWT.BOLD ));  
			gridData = new GridData(); 
			gridData.grabExcessHorizontalSpace = false; 
			gridData.horizontalAlignment = GridData.FILL;
			username.setLayoutData(gridData); 
			
			Label message = new Label(userPostComposite, SWT.None | SWT.WRAP); 
			message.setText(userMessage); 
			gridData = new GridData(); 
			gridData.grabExcessHorizontalSpace = true; 
			gridData.horizontalAlignment = GridData.FILL; 
			gridData.horizontalSpan = 2; 
			gridData.widthHint = 100; 
			message.setLayoutData(gridData); 
			
			
			
			
			Label messageDate = new Label(userPostComposite, SWT.None); 
			messageDate.setText("About one minutes ago from SocialTFS");
			
			 
			messageDate.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 8, SWT.ITALIC ));
			gridData = new GridData(); 
			gridData.grabExcessHorizontalSpace = true; 
			gridData.horizontalAlignment = GridData.END; 
			gridData.horizontalSpan = 2; 
			messageDate.setLayoutData(gridData); 
			
			
		
		  
		  	Label labelhidden = new Label(((Composite)  uiData.get("userPostMaster")), SWT.None); 
			labelhidden.setText("prova"); 
			labelhidden.setVisible(false); 
			
			userPostComposite.moveAbove(((Composite)  uiData.get("userPostMaster")).getChildren()[0]); 
			labelhidden.moveAbove(((Composite)  uiData.get("userPostMaster")).getChildren()[1]);
			((Composite)  uiData.get("userPostMaster")).redraw(); 
			((Composite)  uiData.get("userPostMaster")).layout(); 
			}
			}
			break;
			
		default:
			break;
		}
	}

}
