package it.uniba.di.socialcdeforeclipse.action;






import java.io.InputStream;

import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.dynamicView.InterceptingFilter;
import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.*;
import it.uniba.di.socialcdeforeclipse.staticView.ProfilePanel;
import it.uniba.di.socialcdeforeclipse.staticView.ProgressBarThread;
import it.uniba.di.socialcdeforeclipse.staticView.RegistrationPanel;



import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Widget;

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
	
	public ActionLoginPanel(Widget widget, int eventType)	{
		
		String widgetName = widget.getData("ID_action").toString(); 
		
		Controller.getLoginPanel().getLabelAlert().setVisible(false);
		Controller.getLoginPanel().getLabelImageUsername().setImage(null);
		Controller.getLoginPanel().getLabelImagePassword().setImage(null);
		switch (widgetName) {
		
		case "loginPanel":
			Controller.setWindowHeight(Controller.getWindow().getSize().y); 
			Controller.setWindowWidth(Controller.getWindow().getSize().x);
			Controller.getWindow().setBackgroundImage(resize(getImageStream(PATH_WALLPAPER), Controller.getWindowWidth(), Controller.getWindowHeight()));
			Controller.getWindow().layout(); 
			break;
		case "btnLogin":
			if(eventType == SWT.Selection)	{
				System.out.println("------------------------------");
			    System.out.println("getBounds: " + Controller.getWindow().getBounds());
			    System.out.println("getLocation: " + Controller.getWindow().getLocation());
			    System.out.println("getSize: " + Controller.getWindow().getSize());
			    System.out.println("to display " + Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y));
			    System.out.println("label: " + Controller.getLoginPanel().getLabelAlert().getLocation());
			    pbWindow = new ProgressBarThread(); 
				pbWindow.setLabelTxt("Login in progress..");
				pbWindow.setxCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x); 
				pbWindow.setyCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y); 
				 pbWindow.setxCoordinateWithOffset(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x + (Controller.getWindow().getBounds().width - 300) / 2); 
				 pbWindow.setyCoordinateWithOffset(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y + (Controller.getWindow().getBounds().height - 200) / 2);
				 pbWindow.start();  
				System.out.println("step 1");
			    
			    
			        	if(Controller.getProxy() == null)	{
							Controller.setProxy(new ProxyWrapper()); 
							Controller.getProxy().setHost(Controller.getLoginPanel().getTxtProxyHost().getText()); 
						} else	{
							Controller.getProxy().setHost(Controller.getLoginPanel().getTxtProxyHost().getText());
						}
							if(Controller.getProxy().IsWebServiceRunning())  {
								System.out.println("step 2");
								user = Controller.getProxy().GetUser(Controller.getLoginPanel().getTxtUsername().getText(), Controller.getLoginPanel().getTxtPassword().getText()); 
								
								if(user == null)  {
									
									
								        	    
									Controller.getLoginPanel().getLabelAlert().setText("username or password not valid!");
									Controller.getLoginPanel().getLabelImageUsername().setImage(IMAGE_NO);
									Controller.getLoginPanel().getLabelImagePassword().setImage(IMAGE_NO);
									Controller.getLoginPanel().getLabelAlert().setVisible(true); 
									
									Controller.getWindow().layout(); 
									pbWindow.setStop(1); 
									pbWindow = null;
									 
									System.out.println("Utente non corretto"); 
									
								} else	{
									
									if(Controller.getLoginPanel().getChkSavepassword().getSelection()) {
										Controller.setPreferences("Password",Controller.getLoginPanel().getTxtPassword().getText()); 
									}
									else
									{
										Controller.setPreferences("Password","");
									}
									
									if(Controller.getLoginPanel().getChkAutologin().getSelection())	{
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
									Controller.setPreferences("ProxyRoot", Controller.getLoginPanel().getTxtProxyHost().getText());
									Controller.setPreferences("Username", user.Username);
									Controller.setCurrentUserPassword(Controller.getLoginPanel().getTxtPassword().getText());

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
								Controller.getLoginPanel().getLabelAlert().setText("The connection with the Proxy failed"); 
								Controller.getLoginPanel().getLabelImageHost().setImage(IMAGE_NO);
								Controller.getLoginPanel().getLabelAlert().setVisible(true); 
								pbWindow.setStop(1); 
								pbWindow = null;
								//Controller.getWindow().redraw(); 
								//Controller.getWindow().layout(); 
							}
			        	
				          
				        
			
				
			
			}
			
		
				
			
			break;
		case "labelRegistration":
			try {
			if(eventType == SWT.Paint)
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
						 pbWindow.start();  
						System.out.println("step 1");
					    
					    
					        	if(Controller.getProxy() == null)	{
									Controller.setProxy(new ProxyWrapper()); 
									Controller.getProxy().setHost(Controller.getLoginPanel().getTxtProxyHost().getText()); 
								} else	{
									Controller.getProxy().setHost(Controller.getLoginPanel().getTxtProxyHost().getText());
								}
									if(Controller.getProxy().IsWebServiceRunning())  {
										System.out.println("step 2");
										user = Controller.getProxy().GetUser(Controller.getLoginPanel().getTxtUsername().getText(), Controller.getLoginPanel().getTxtPassword().getText()); 
										
										if(user == null)  {
											
											
										        	    
											Controller.getLoginPanel().getLabelAlert().setText("username or password not valid!");
											Controller.getLoginPanel().getLabelImageUsername().setImage(IMAGE_NO);
											Controller.getLoginPanel().getLabelImagePassword().setImage(IMAGE_NO);
											Controller.getLoginPanel().getLabelAlert().setVisible(true); 
											
											Controller.getWindow().layout(); 
											pbWindow.setStop(1); 
											pbWindow = null;
											 
											System.out.println("Utente non corretto"); 
											
										} else	{
											
											if(Controller.getLoginPanel().getChkSavepassword().getSelection()) {
												Controller.setPreferences("Password",Controller.getLoginPanel().getTxtPassword().getText()); 
											}
											
											if(Controller.getLoginPanel().getChkAutologin().getSelection())	{
												Controller.setPreferences("Autologin", "True"); 
											}
											System.out.println("Utente corretto!"); 
											Controller.setCurrentUser(user); 
											Controller.setPreferences("ProxyHost", Controller.getProxy().getHost());
											Controller.setPreferences("ProxyRoot", Controller.getLoginPanel().getTxtProxyHost().getText());
											Controller.setPreferences("Username", user.Username);
											Controller.setCurrentUserPassword(Controller.getLoginPanel().getTxtPassword().getText());

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
										Controller.getLoginPanel().getLabelAlert().setText("The connection with the Proxy failed"); 
										Controller.getLoginPanel().getLabelImageHost().setImage(IMAGE_NO);
										Controller.getLoginPanel().getLabelAlert().setVisible(true); 
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
			if(eventType == SWT.FocusOut)
			{
				if(InterceptingFilter.verifyText(Controller.getLoginPanel().getTxtProxyHost()))
				{
					Controller.setProxy(new ProxyWrapper()); 
					Controller.getProxy().setHost(Controller.getLoginPanel().getTxtProxyHost().getText()); 
					if(Controller.getProxy().IsWebServiceRunning())
					{
						
						Controller.getLoginPanel().getLabelImageHost().setImage(IMAGE_OK); 
						Controller.getLoginPanel().getLabelAlert().setVisible(false);
					}
					else
					{
						
						Controller.setProxy(null); 
						Controller.getLoginPanel().getLabelAlert().setText("Please insert a valid proxy!");
						Controller.getLoginPanel().getLabelAlert().setVisible(true); 
						Controller.getLoginPanel().getLabelImageHost().setImage(IMAGE_NO);
						Controller.getWindow().layout(); 
					}
				}
			}
			if(eventType == SWT.TAB)
			{
				System.out.println("Stampa " + Controller.getLoginPanel().getTxtProxyHost().getText().toCharArray().toString());
				Controller.getLoginPanel().getTxtUsername().forceFocus(); 
				Controller.getLoginPanel().getTxtProxyHost().setText(Controller.getLoginPanel().getTxtProxyHost().getText().replace("\\t", ""));
				Controller.getWindow().layout(); 
			}
			
			break;
			
		case "txtUsername":
			
			if(eventType == SWT.FocusOut)
			{
				if(InterceptingFilter.verifyText(Controller.getLoginPanel().getTxtUsername()))
				{
					if(Controller.getProxy() != null)
					{
						if(Controller.getProxy().IsAvailable(Controller.getLoginPanel().getTxtUsername().getText()))
						{
							
							Controller.getLoginPanel().getLabelImageUsername().setImage(IMAGE_OK);
							Controller.getLoginPanel().getLabelAlert().setVisible(false); 
							Controller.getWindow().layout();
						}
						else
						{
							Controller.getLoginPanel().getLabelAlert().setText("Please insert a valid username");  
							Controller.getLoginPanel().getLabelAlert().setVisible(true); 
							Controller.getLoginPanel().getLabelImageUsername().setImage(IMAGE_NO);
							Controller.getWindow().layout(); 
						}
					}
					else
					{
						Controller.getLoginPanel().getLabelAlert().setText("Please insert a valid proxy first!");  
						Controller.getLoginPanel().getLabelAlert().setVisible(true); 
						 

						
						Controller.getLoginPanel().getLabelImageUsername().setImage(IMAGE_NO);
						Controller.getLoginPanel().getLabelImageHost().setImage(IMAGE_NO);
						Controller.getWindow().layout();
					
					}
				}
				else
				{
					Controller.getLoginPanel().getLabelAlert().setText("Please insert a valid username!");  
					Controller.getLoginPanel().getLabelAlert().setVisible(true);
					Controller.getLoginPanel().getLabelImageUsername().setImage(IMAGE_NO);
					Controller.getWindow().layout(); 
				}
			}
			
			if(eventType == SWT.TAB)
			{
				Controller.getLoginPanel().getTxtPassword().forceFocus(); 
				
			}
			
			break;
			
		case "txtPassword":
			if(eventType == SWT.TAB)
			{
				Controller.getLoginPanel().getBtnLogin().forceFocus(); 
				
			}
			break;
			
		default:
			break;
		}
	}

	
}
