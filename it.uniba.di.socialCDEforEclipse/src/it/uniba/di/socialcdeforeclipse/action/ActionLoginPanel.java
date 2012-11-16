package it.uniba.di.socialcdeforeclipse.action;






import java.io.InputStream;
import java.util.HashMap;

import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.dynamic.view.InterceptingFilter;
import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;
import it.uniba.di.socialcdeforeclipse.object.ProgressBarThread;
import it.uniba.di.socialcdeforeclipse.shared.library.*;
import it.uniba.di.socialcdeforeclipse.staticview.ProfilePanel;
import it.uniba.di.socialcdeforeclipse.staticview.RegistrationPanel;
import it.uniba.di.socialcdeforeclipse.views.SquareButtonService;



import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;

public class ActionLoginPanel {
	
	

	private ProgressBarThread pbWindow;
	private WUser user;
	private static Image IMAGE_NO;
	private static Image IMAGE_OK;
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
	
	public ActionLoginPanel(HashMap<String, Object> uiData)	{
		
		IMAGE_NO = Controller.getLoginPanel().getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/no_icon.png"));
		IMAGE_OK = Controller.getLoginPanel().getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/yes_icon.png"));
		
		String widgetName = uiData.get("ID_action").toString(); 
		int type = (int) uiData.get("Event_type"); 
		Event event = (Event)  uiData.get("Event"); 
		((Label) uiData.get("labelAlert")).setVisible(false);
		((Label) uiData.get("labelImageUsername")).setImage(null);
		((Label) uiData.get("labelImagePassword")).setImage(null);
		switch (widgetName) {
		
		case "loginPanel":
			Controller.setWindowHeight(Controller.getWindow().getSize().y); 
			Controller.setWindowWidth(Controller.getWindow().getSize().x);
			Controller.getWindow().setBackgroundImage(resize(getImageStream(PATH_WALLPAPER), Controller.getWindowWidth(), Controller.getWindowHeight()));
			Controller.getWindow().layout(); 
			break;
		case "btnLogin":
			if(type == SWT.Selection)	{
				System.out.println("------------------------------");
			    System.out.println("getBounds: " + Controller.getWindow().getBounds());
			    System.out.println("getLocation: " + Controller.getWindow().getLocation());
			    System.out.println("getSize: " + Controller.getWindow().getSize());
			    System.out.println("to display " + Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y));
			    System.out.println("label: " + ((Label) uiData.get("labelAlert")).getLocation());
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
							Controller.getProxy().setHost(((Text) uiData.get("txtProxyHost")).getText()); 
						} else	{
							Controller.getProxy().setHost(((Text) uiData.get("txtProxyHost")).getText());
						}
							if(Controller.getProxy().IsWebServiceRunning())  {
								System.out.println("step 2");
								user = Controller.getProxy().GetUser(((Text) uiData.get("txtUsername")).getText(), ((Text) uiData.get("txtPassword")).getText()); 
								
								if(user == null)  {
									
									
								        	    
									((Label) uiData.get("labelAlert")).setText("username or password not valid!");
									((Label) uiData.get("labelImageUsername")).setImage(IMAGE_NO);
									((Label) uiData.get("labelImagePassword")).setImage(IMAGE_NO);
									
									((Label) uiData.get("labelImagePassword")).setData("Image_ok", false);
									((Label) uiData.get("labelImagePassword")).setData("Image_no", true);
									
									((Label) uiData.get("labelImageUsername")).setData("Image_ok", false);
									((Label) uiData.get("labelImageUsername")).setData("Image_no", true);
									
									
									((Label) uiData.get("labelAlert")).setVisible(true); 
									
									//Controller.getWindow().layout(); 
									pbWindow.setStop(1); 
									pbWindow = null;
									 
									System.out.println("Utente non corretto"); 
									
								} else	{
									
									if(((Button) uiData.get("chkSavePassword")).getSelection()) {
										Controller.setPreferences("Password",((Text) uiData.get("txtPassword")).getText()); 
									}
									else
									{
										Controller.setPreferences("Password","");
									}
									
									if(((Button) uiData.get("chkAutologin")).getSelection())	{
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
									Controller.setPreferences("ProxyRoot", ((Text) uiData.get("txtProxyHost")).getText());
									Controller.setPreferences("Username", user.Username);
									Controller.setCurrentUserPassword(((Text) uiData.get("txtPassword")).getText());

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
								((Label) uiData.get("labelAlert")).setText("The connection with the Proxy failed"); 
								((Label) uiData.get("labelImageHost")).setImage(IMAGE_NO);
								
								((Label) uiData.get("labelImageHost")).setData("Image_no", true);
								((Label) uiData.get("labelImageHost")).setData("Image_ok", false);
								
								((Label) uiData.get("labelAlert")).setVisible(true); 
								pbWindow.setStop(1); 
								pbWindow = null;
								//Controller.getWindow().redraw(); 
								//Controller.getWindow().layout(); 
							}
			        	
				          
				        
			
				
			
			}
			
		
				
			
			break;
		case "labelRegistration":
			try {
			if(type == SWT.Paint)
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
									Controller.getProxy().setHost(((Text) uiData.get("txtProxyHost")).getText()); 
								} else	{
									Controller.getProxy().setHost(((Text) uiData.get("txtProxyHost")).getText());
								}
									if(Controller.getProxy().IsWebServiceRunning())  {
										System.out.println("step 2");
										user = Controller.getProxy().GetUser(((Text) uiData.get("txtUsername")).getText(), ((Text) uiData.get("txtPassword")).getText()); 
										
										if(user == null)  {
											
											
										        	    
											((Label) uiData.get("labelAlert")).setText("username or password not valid!");
											((Label) uiData.get("labelImageUsername")).setImage(IMAGE_NO);
											((Label) uiData.get("labelImagePassword")).setImage(IMAGE_NO);
											
											((Label) uiData.get("labelImageUsername")).setData("Image_no", true);
											((Label) uiData.get("labelImageUsername")).setData("Image_ok", false);
											
											((Label) uiData.get("labelImagePassword")).setData("Image_no", true);
											((Label) uiData.get("labelImagePassword")).setData("Image_ok", false);

											
											((Label) uiData.get("labelAlert")).setVisible(true); 
											
											Controller.getWindow().layout(); 
											pbWindow.setStop(1); 
											pbWindow = null;
											 
											System.out.println("Utente non corretto"); 
											
										} else	{
											
											if(((Button) uiData.get("chkSavePassword")).getSelection()) {
												Controller.setPreferences("Password",((Text) uiData.get("txtPassword")).getText()); 
											}
											
											if(((Button) uiData.get("chkAutologin")).getSelection())	{
												Controller.setPreferences("Autologin", "True"); 
											}
											System.out.println("Utente corretto!"); 
											Controller.setCurrentUser(user); 
											Controller.setPreferences("ProxyHost", Controller.getProxy().getHost());
											Controller.setPreferences("ProxyRoot", ((Text) uiData.get("txtProxyHost")).getText());
											Controller.setPreferences("Username", user.Username);
											Controller.setCurrentUserPassword(((Text) uiData.get("txtPassword")).getText());

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
										((Label) uiData.get("labelAlert")).setText("The connection with the Proxy failed"); 
										((Label) uiData.get("labelImageHost")).setImage(IMAGE_NO);
										
										((Label) uiData.get("labelImageHost")).setData("Image_no", true);
										((Label) uiData.get("labelImageHost")).setData("Image_ok", false);
										
										((Label) uiData.get("labelAlert")).setVisible(true); 
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
			
			if(type == SWT.FocusOut)
			{
				if(InterceptingFilter.verifyText(((Text) uiData.get("txtProxyHost")).getText()))
				{
					Controller.setProxy(new ProxyWrapper()); 
					Controller.getProxy().setHost(((Text) uiData.get("txtProxyHost")).getText()); 
					if(Controller.getProxy().IsWebServiceRunning())
					{
						
						((Label) uiData.get("labelImageHost")).setImage(IMAGE_OK); 
						((Label) uiData.get("labelAlert")).setVisible(false);
						
						((Label) uiData.get("labelImageHost")).setData("Image_ok", true);
						((Label) uiData.get("labelImageHost")).setData("Image_no", false);
						
						
					}
					else
					{
						
						Controller.setProxy(null); 
						((Label) uiData.get("labelAlert")).setText("Please insert a valid proxy!");
						((Label) uiData.get("labelAlert")).setVisible(true); 
						((Label) uiData.get("labelImageHost")).setImage(IMAGE_NO);
						
						((Label) uiData.get("labelImageHost")).setData("Image_no", true);
						((Label) uiData.get("labelImageHost")).setData("Image_ok", false);
						
						
						Controller.getWindow().layout(); 
					}
				}
				else
				{
					Controller.setProxy(null); 
					((Label) uiData.get("labelAlert")).setText("Please insert a valid proxy!");
					((Label) uiData.get("labelAlert")).setVisible(true); 
					((Label) uiData.get("labelImageHost")).setImage(IMAGE_NO);
					
					((Label) uiData.get("labelImageHost")).setData("Image_no", true);
					((Label) uiData.get("labelImageHost")).setData("Image_ok", false);
				}
			}
			
			
			break;
			
		case "txtUsername":
			
			if(type == SWT.FocusOut)
			{
				if(InterceptingFilter.verifyText(((Text) uiData.get("txtUsername")).getText()))
				{
					if(Controller.getProxy() != null)
					{
						if(Controller.getProxy().IsAvailable(((Text) uiData.get("txtUsername")).getText()))
						{
							
							((Label) uiData.get("labelImageUsername")).setImage(IMAGE_OK);
							((Label) uiData.get("labelAlert")).setVisible(false); 
							
							((Label) uiData.get("labelImageUsername")).setData("Image_ok", true);
							((Label) uiData.get("labelImageUsername")).setData("Image_no", false);
							
							
							Controller.getWindow().layout();
						}
						else
						{
							((Label) uiData.get("labelAlert")).setText("Please insert a valid username");  
							((Label) uiData.get("labelAlert")).setVisible(true); 
							((Label) uiData.get("labelImageUsername")).setImage(IMAGE_NO);
							
							((Label) uiData.get("labelImageUsername")).setData("Image_ok", false);
							((Label) uiData.get("labelImageUsername")).setData("Image_no", true);
							
							Controller.getWindow().layout(); 
						}
					}
					else
					{
						((Label) uiData.get("labelAlert")).setText("Please insert a valid proxy first!");  
						((Label) uiData.get("labelAlert")).setVisible(true); 
						
						((Label) uiData.get("labelImageUsername")).setImage(IMAGE_NO);
						((Label) uiData.get("labelImageHost")).setImage(IMAGE_NO);
						
						((Label) uiData.get("labelImageUsername")).setData("Image_ok", false);
						((Label) uiData.get("labelImageUsername")).setData("Image_no", true);
						
						((Label) uiData.get("labelImageHost")).setData("Image_ok", false);
						((Label) uiData.get("labelImageHost")).setData("Image_no", true);
						
						Controller.getWindow().layout();
					
					}
				}
				else
				{
					((Label) uiData.get("labelAlert")).setText("Please insert a valid username!");  
					((Label) uiData.get("labelAlert")).setVisible(true);
					((Label) uiData.get("labelImageUsername")).setImage(IMAGE_NO);
					
					((Label) uiData.get("labelImageUsername")).setData("Image_ok", false);
					((Label) uiData.get("labelImageUsername")).setData("Image_no", true);
					
					Controller.getWindow().layout(); 
				}
			}
			
			
			
			break;
			
		
			
		default:
			break;
		}
	}

	
}
