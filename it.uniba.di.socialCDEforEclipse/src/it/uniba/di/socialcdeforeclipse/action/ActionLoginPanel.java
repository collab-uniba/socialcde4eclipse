package it.uniba.di.socialcdeforeclipse.action;






import java.io.InputStream;

import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.dynamicView.InterceptingFilter;
import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.*;
import it.uniba.di.socialcdeforeclipse.staticView.ProfilePanel;
import it.uniba.di.socialcdeforeclipse.staticView.ProgressBarThread;
import it.uniba.di.socialcdeforeclipse.staticView.RegistrationPanel;
import it.uniba.di.socialcdeforeclipse.views.SquareButtonService;



import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.*;

public class ActionLoginPanel {
	
	

	private ProgressBarThread pbWindow;
	private WUser user;
	private final static Image IMAGE_NO = Controller.getLoginPanel().getImageStream(Controller.getLoginPanel().getPathIconError());
	private final static Image IMAGE_OK = Controller.getLoginPanel().getImageStream(Controller.getLoginPanel().getPathIconOk());
	private final InputStream PATH_WALLPAPER = this.getClass().getClassLoader().getResourceAsStream("images/Wallpaper.png");
	
	public Image getImageStream(InputStream stream)
	{ 	
		return  new Image(Controller.getWindow().getDisplay(),stream);
		
	}
	
	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Controller.getWindow().getDisplay().getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0,image.getBounds().width, image.getBounds().height,	0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
		}
	
	public ActionLoginPanel()
	{
		
	}
	
	public ActionLoginPanel(Widget widget, Event event)	{
		
		String widgetName = widget.getData("ID_action").toString(); 
		
		((Label) widget.getData("labelAlert")).setVisible(false);
		((Label) widget.getData("labelImageUsername")).setImage(null);
		((Label) widget.getData("labelImagePassword")).setImage(null);
		switch (widgetName) {
		
		case "loginPanel":
			Controller.setWindowHeight(Controller.getWindow().getSize().y); 
			Controller.setWindowWidth(Controller.getWindow().getSize().x);
			Controller.getWindow().setBackgroundImage(resize(getImageStream(PATH_WALLPAPER), Controller.getWindowWidth(), Controller.getWindowHeight()));
			Controller.getWindow().layout(); 
			break;
		case "btnLogin":
			if(event.type == SWT.Selection)	{
				System.out.println("------------------------------");
			    System.out.println("getBounds: " + Controller.getWindow().getBounds());
			    System.out.println("getLocation: " + Controller.getWindow().getLocation());
			    System.out.println("getSize: " + Controller.getWindow().getSize());
			    System.out.println("to display " + Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y));
			    System.out.println("label: " + ((Label) widget.getData("labelAlert")).getLocation());
			    pbWindow = new ProgressBarThread(); 
				pbWindow.setLabelTxt("Login in progress..");
				Controller.setProgressBarPositionX(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x); 
				Controller.setProgressBarPositionY(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y); 
				pbWindow.setxCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x); 
				pbWindow.setyCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y); 
				 pbWindow.setxCoordinateWithOffset(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x + (Controller.getWindow().getBounds().width - 300) / 2); 
				 pbWindow.setyCoordinateWithOffset(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y + (Controller.getWindow().getBounds().height - 200) / 2);
				 pbWindow.start();  
				System.out.println("step 1");
			    
			    
			        	if(Controller.getProxy() == null)	{
							Controller.setProxy(new ProxyWrapper()); 
							Controller.getProxy().setHost(((Text) widget.getData("txtProxyHost")).getText()); 
						} else	{
							Controller.getProxy().setHost(((Text) widget.getData("txtProxyHost")).getText());
						}
							if(Controller.getProxy().IsWebServiceRunning())  {
								System.out.println("step 2");
								user = Controller.getProxy().GetUser(((Text) widget.getData("txtUsername")).getText(), ((Text) widget.getData("txtPassword")).getText()); 
								
								if(user == null)  {
									
									
								        	    
									((Label) widget.getData("labelAlert")).setText("username or password not valid!");
									((Label) widget.getData("labelImageUsername")).setImage(IMAGE_NO);
									((Label) widget.getData("labelImagePassword")).setImage(IMAGE_NO);
									((Label) widget.getData("labelAlert")).setVisible(true); 
									
									//Controller.getWindow().layout(); 
									pbWindow.setStop(1); 
									pbWindow = null;
									 
									System.out.println("Utente non corretto"); 
									
								} else	{
									
									if(((Button) widget.getData("chkSavePassword")).getSelection()) {
										Controller.setPreferences("Password",((Text) widget.getData("txtPassword")).getText()); 
									}
									else
									{
										Controller.setPreferences("Password","");
									}
									
									if(((Button) widget.getData("chkAutologin")).getSelection())	{
										Controller.setPreferences("Autologin", "True"); 
										Controller.setPreferences("FlagAutologin", "False");
									}
									else
									{
										Controller.setPreferences("Autologin", "False"); 
										Controller.setPreferences("FlagAutologin", "False");
									}
									System.out.println("Utente corretto!"); 
									Controller.setCurrentUser(user); 
									Controller.setPreferences("ProxyHost", Controller.getProxy().getHost());
									Controller.setPreferences("ProxyRoot", ((Text) widget.getData("txtProxyHost")).getText());
									Controller.setPreferences("Username", user.Username);
									Controller.setCurrentUserPassword(((Text) widget.getData("txtPassword")).getText());

									Controller.setWindowName("Profile");
									Controller.setProfilePanel(new ProfilePanel()); 
								
									Controller.getLoginPanel().dispose(Controller.getWindow()); 
									Controller.setLoginPanel(null); 
									SquareButtonService.yCoordinateValue = 5;
									SquareButtonService.counterPosition = 0; 
									Controller.getProfilePanel().inizialize(Controller.getWindow()); 
									
									pbWindow.setStop(1); 
									pbWindow = null; 
									//Controller.getWindow().getShell().forceFocus(); 
									 
									
								   
									
								}
							}  else	{
								((Label) widget.getData("labelAlert")).setText("The connection with the Proxy failed"); 
								((Label) widget.getData("labelImageHost")).setImage(IMAGE_NO);
								((Label) widget.getData("labelAlert")).setVisible(true); 
								pbWindow.setStop(1); 
								pbWindow = null;
								//Controller.getWindow().redraw(); 
								//Controller.getWindow().layout(); 
							}
			        	
				          
				        
			
				
			
			}
			
		
				
			
			break;
		case "labelRegistration":
			try {
			if(event.type == SWT.Paint)
			{
				System.out.println("Evento paint label attivato");
				
					
				
				if(!Controller.getPreferences("Autologin").equals("") && Controller.getPreferences("FlagAutologin").equals("True"))
				{
					
					 pbWindow = new ProgressBarThread(); 
						pbWindow.setLabelTxt("Login in progress..");
						pbWindow.setxCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x); 
						pbWindow.setyCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y); 
						 pbWindow.setxCoordinateWithOffset(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x + (Controller.getWindow().getBounds().width - 300) / 2); 
						 pbWindow.setyCoordinateWithOffset(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y + (Controller.getWindow().getBounds().height - 200) / 2);
							Controller.setProgressBarPositionX(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x); 
							Controller.setProgressBarPositionY(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y); 
						 pbWindow.start();  
						System.out.println("step 1");
					    
					    
					        	if(Controller.getProxy() == null)	{
									Controller.setProxy(new ProxyWrapper()); 
									Controller.getProxy().setHost(((Text) widget.getData("txtProxyHost")).getText()); 
								} else	{
									Controller.getProxy().setHost(((Text) widget.getData("txtProxyHost")).getText());
								}
									if(Controller.getProxy().IsWebServiceRunning())  {
										System.out.println("step 2");
										user = Controller.getProxy().GetUser(((Text) widget.getData("txtUsername")).getText(), ((Text) widget.getData("txtPassword")).getText()); 
										
										if(user == null)  {
											
											
										        	    
											((Label) widget.getData("labelAlert")).setText("username or password not valid!");
											((Label) widget.getData("labelImageUsername")).setImage(IMAGE_NO);
											((Label) widget.getData("labelImagePassword")).setImage(IMAGE_NO);
											((Label) widget.getData("labelAlert")).setVisible(true); 
											
											Controller.getWindow().layout(); 
											pbWindow.setStop(1); 
											pbWindow = null;
											 
											System.out.println("Utente non corretto"); 
											
										} else	{
											
											if(((Button) widget.getData("chkSavePassword")).getSelection()) {
												Controller.setPreferences("Password",((Text) widget.getData("txtPassword")).getText()); 
											}
											
											if(((Button) widget.getData("chkAutologin")).getSelection())	{
												Controller.setPreferences("Autologin", "True"); 
											}
											System.out.println("Utente corretto!"); 
											Controller.setCurrentUser(user); 
											Controller.setPreferences("ProxyHost", Controller.getProxy().getHost());
											Controller.setPreferences("ProxyRoot", ((Text) widget.getData("txtProxyHost")).getText());
											Controller.setPreferences("Username", user.Username);
											Controller.setCurrentUserPassword(((Text) widget.getData("txtPassword")).getText());

											Controller.setWindowName("Profile");
											Controller.setProfilePanel(new ProfilePanel()); 
										
											Controller.getLoginPanel().dispose(Controller.getWindow()); 
											Controller.setLoginPanel(null); 
											Controller.getProfilePanel().inizialize(Controller.getWindow()); 
											
											pbWindow.setStop(1); 
											pbWindow = null; 
											//Controller.getWindow().getShell().forceFocus(); 
											 
											
										   
											
										}
									}  else	{
										((Label) widget.getData("labelAlert")).setText("The connection with the Proxy failed"); 
										((Label) widget.getData("labelImageHost")).setImage(IMAGE_NO);
										((Label) widget.getData("labelAlert")).setVisible(true); 
										pbWindow.setStop(1); 
										pbWindow = null;
										Controller.getWindow().layout(); 
									}
					        	
						          
						        
				}
				
			}
			else
			{
				Controller.setWindowName("Registration");
				Controller.getLoginPanel().dispose(Controller.getWindow()); 
				
				Controller.setRegistration_panel(new RegistrationPanel()); 
				Controller.getRegistrationPanel().inizialize(Controller.getWindow()); 
				Controller.setLoginPanel(null); 
				Controller.getWindow().layout(); 
			}
			
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
			
		case "txtProxyHost":
			System.out.println("evento scattato " + (event.detail == SWT.TRAVERSE_TAB_NEXT ||
					event.detail == SWT.TRAVERSE_TAB_PREVIOUS)); 
			if(event.type == SWT.FocusOut)
			{
				if(InterceptingFilter.verifyText(((Text) widget.getData("txtProxyHost")).getText()))
				{
					Controller.setProxy(new ProxyWrapper()); 
					Controller.getProxy().setHost(((Text) widget.getData("txtProxyHost")).getText()); 
					if(Controller.getProxy().IsWebServiceRunning())
					{
						
						((Label) widget.getData("labelImageHost")).setImage(IMAGE_OK); 
						((Label) widget.getData("labelAlert")).setVisible(false);
					}
					else
					{
						
						Controller.setProxy(null); 
						((Label) widget.getData("labelAlert")).setText("Please insert a valid proxy!");
						((Label) widget.getData("labelAlert")).setVisible(true); 
						((Label) widget.getData("labelImageHost")).setImage(IMAGE_NO);
						Controller.getWindow().layout(); 
					}
				}
			}
			
			
			break;
			
		case "txtUsername":
			
			if(event.type == SWT.FocusOut)
			{
				if(InterceptingFilter.verifyText(((Text) widget.getData("txtUsername")).getText()))
				{
					if(Controller.getProxy() != null)
					{
						if(Controller.getProxy().IsAvailable(((Text) widget.getData("txtUsername")).getText()))
						{
							
							((Label) widget.getData("labelImageUsername")).setImage(IMAGE_OK);
							((Label) widget.getData("labelAlert")).setVisible(false); 
							Controller.getWindow().layout();
						}
						else
						{
							((Label) widget.getData("labelAlert")).setText("Please insert a valid username");  
							((Label) widget.getData("labelAlert")).setVisible(true); 
							((Label) widget.getData("labelImageUsername")).setImage(IMAGE_NO);
							Controller.getWindow().layout(); 
						}
					}
					else
					{
						((Label) widget.getData("labelAlert")).setText("Please insert a valid proxy first!");  
						((Label) widget.getData("labelAlert")).setVisible(true); 
						 

						
						((Label) widget.getData("labelImageUsername")).setImage(IMAGE_NO);
						((Label) widget.getData("labelImageHost")).setImage(IMAGE_NO);
						Controller.getWindow().layout();
					
					}
				}
				else
				{
					((Label) widget.getData("labelAlert")).setText("Please insert a valid username!");  
					((Label) widget.getData("labelAlert")).setVisible(true);
					((Label) widget.getData("labelImageUsername")).setImage(IMAGE_NO);
					Controller.getWindow().layout(); 
				}
			}
			
			
			
			break;
			
		
			
		default:
			break;
		}
	}

	
}
