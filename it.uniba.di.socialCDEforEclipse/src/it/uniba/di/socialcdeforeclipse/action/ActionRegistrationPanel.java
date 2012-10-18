package it.uniba.di.socialcdeforeclipse.action;

import java.io.InputStream;

import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.dynamicView.InterceptingFilter;

import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;
import it.uniba.di.socialcdeforeclipse.staticView.LoginPanel;
import it.uniba.di.socialcdeforeclipse.staticView.ProgressBarThread;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Widget;

public class ActionRegistrationPanel {
	  
	private ProgressBarThread pbWindow; 
	private final static Image IMAGE_OK = Controller.getRegistrationPanel().getImageStream(Controller.getRegistrationPanel().getPathIconOk());
	private final static Image IMAGE_NO = Controller.getRegistrationPanel().getImageStream(Controller.getRegistrationPanel().getPathIconError());
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
	
	
	public ActionRegistrationPanel(Widget widget, int eventType)
	{
		String widgetName = widget.getData("ID_action").toString(); 
		
		switch (widgetName) {
		
		case "registrationPanel":
			
			Controller.setWindowHeight(Controller.getWindow().getSize().y); 
			Controller.setWindowWidth(Controller.getWindow().getSize().x);
			Controller.getWindow().setBackgroundImage(resize(getImageStream(PATH_WALLPAPER), Controller.getWindowWidth(), Controller.getWindowHeight()));
			Controller.getWindow().layout(); 

			
			break;
		
		case "txtProxyHost":

			if(eventType == SWT.FocusOut)
			{
				if(InterceptingFilter.verifyText(Controller.getRegistrationPanel().getTxtProxyHost()))
				{
					Controller.setProxy(new ProxyWrapper()); 
					Controller.getProxy().setHost(Controller.getRegistrationPanel().getTxtProxyHost().getText()); 
					if(Controller.getProxy().IsWebServiceRunning())
					{
						
						Controller.getRegistrationPanel().getLabelImageHost().setImage(IMAGE_OK); 
						Controller.getRegistrationPanel().getLabelAlert().setVisible(false);
					}
					else
					{
						
						Controller.setProxy(null); 
						Controller.getRegistrationPanel().getLabelAlert().setText("Please insert a valid proxy!");
						Controller.getRegistrationPanel().getLabelAlert().setVisible(true); 
						Controller.getRegistrationPanel().getLabelImageHost().setImage(IMAGE_NO);
						Controller.getWindow().layout(); 
					}
				}
			}
			
			break;
		
		case "txtUsername":
			
			if(eventType == SWT.FocusOut)
			{
				if(InterceptingFilter.verifyText(Controller.getRegistrationPanel().getTxtUsername()))
				{
					if(Controller.getProxy() != null)
					{
						if(!Controller.getProxy().IsAvailable(Controller.getRegistrationPanel().getTxtUsername().getText()))
						{
							
							

							Controller.getRegistrationPanel().getLabelAlert().setText("Please insert a valid username");  
							Controller.getRegistrationPanel().getLabelAlert().setVisible(true); 
							Controller.getRegistrationPanel().getLabelImageUsername().setImage(IMAGE_NO);
							Controller.getWindow().layout(); 
						}
						else
						{
							
							Controller.getRegistrationPanel().getLabelImageUsername().setImage(IMAGE_OK);
							Controller.getRegistrationPanel().getLabelAlert().setVisible(false); 
							Controller.getWindow().layout(); 
						}
					}
					else
					{
						Controller.getRegistrationPanel().getLabelAlert().setText("Please insert a valid proxy first!");  
						Controller.getRegistrationPanel().getLabelAlert().setVisible(true); 
						 

						
						Controller.getRegistrationPanel().getLabelImageUsername().setImage(IMAGE_NO);
						Controller.getRegistrationPanel().getLabelImageHost().setImage(IMAGE_NO);
						Controller.getWindow().layout();
					
					}
				}
				else
				{
					Controller.getRegistrationPanel().getLabelAlert().setText("Please insert a valid username!");  
					Controller.getRegistrationPanel().getLabelAlert().setVisible(true);
					Controller.getRegistrationPanel().getLabelImageUsername().setImage(IMAGE_NO);
					Controller.getWindow().layout(); 
				}
			}
			
			break;
		case "txtMail":
			if(eventType == SWT.FocusOut){
				if(InterceptingFilter.verifyMail(Controller.getRegistrationPanel().getTxtMail().getText())){
					Controller.getRegistrationPanel().getLabelImageMail().setImage(IMAGE_OK);
					Controller.getRegistrationPanel().getLabelAlert().setVisible(false); 
					Controller.getWindow().layout(); 
		
				} else {
					Controller.getRegistrationPanel().getLabelAlert().setText("Please insert a valid mail!");  
					Controller.getRegistrationPanel().getLabelAlert().setVisible(true);
					Controller.getRegistrationPanel().getLabelImageMail().setImage(IMAGE_NO);
					Controller.getWindow().layout(); 
					
				}
			}
			
			break;
			
		case "btnRegister":
			
			if(eventType == SWT.Selection)
			{
				System.out.println("Scatta evento registrazione"); 
				pbWindow = new ProgressBarThread(); 
				pbWindow.setLabelTxt("Login in progress..");
				pbWindow.setxCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x); 
				pbWindow.setyCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y); 
				//pbThread.setWidth(Controller.getWindow().getSize().x);
				//pbThread.setHeight(Controller.getWindow().getSize().y); 
				pbWindow.start(); 
				
				Controller.getWindow().getDisplay().asyncExec(new Runnable() {
			        public void run() {
			        	
			        	if(InterceptingFilter.verifyRegistrationPanel())
						{
							if(Controller.getProxy() != null)
							{
								System.out.println("Proxy corretto"); 
								//registra
							 int res = Controller.getProxy().SubscribeUser(Controller.getRegistrationPanel().getTxtMail().getText(), Controller.getRegistrationPanel().getTxtInvitationCode().getText(), Controller.getRegistrationPanel().getTxtUsername().getText());
							 System.out.println("Valore di res "+ res);
							 switch (res) {
							case -1:
								Controller.getRegistrationPanel().getLabelAlert().setText("There's a problem. Check your connection and try again");
								Controller.getWindow().layout();
								pbWindow.setStop(1);  
								pbWindow = null;
								break;
							case 0:
								
								boolean password = Controller.getProxy().ChangePassword(Controller.getRegistrationPanel().getTxtUsername().getText(), Controller.getRegistrationPanel().getTxtInvitationCode().getText(),Controller.getRegistrationPanel().getTxtPassword().getText()); 
								System.out.println("Valore di password "+ password); 
								if(password)
								{
									Controller.setPreferences("ProxyHost", Controller.getProxy().getHost()); 
									Controller.setPreferences("Username", Controller.getRegistrationPanel().getTxtUsername().getText()); 
									Controller.setPreferences("ProxyRoot", Controller.getRegistrationPanel().getTxtProxyHost().getText()); 
									Controller.setPreferences("Password", Controller.getRegistrationPanel().getTxtPassword().getText()); 
									Controller.setPreferences("Email", Controller.getRegistrationPanel().getTxtMail().getText()); 
								}
								else
								{
									Controller.getRegistrationPanel().getLabelAlert().setText("There's a problem. Check your connection and try again");
									Controller.getRegistrationPanel().getLabelAlert().setVisible(true);
									Controller.getWindow().layout();
									pbWindow.setStop(1); 
									pbWindow = null;
								}
								
								break;
							case 1: // if e-mail address does not exist in the database
								Controller.getRegistrationPanel().getLabelAlert().setText("Please insert the email on which you recived the invite");
								Controller.getRegistrationPanel().getLabelAlert().setVisible(true); 
								Controller.getWindow().layout();
								pbWindow.setStop(1); 
								pbWindow = null;
								break;
							case 2:
								// if password does not match with the e-mail address sent
			                    Controller.getRegistrationPanel().getLabelAlert().setText("Please insert the invitation code that you recived in the invite");
			                    Controller.getRegistrationPanel().getLabelAlert().setVisible(true);
			                    Controller.getWindow().layout();
			                    pbWindow.setStop(1); 
								pbWindow = null;
			                    break;
			                case 3: // if username is already used by another user
			                    Controller.getRegistrationPanel().getLabelAlert().setText("The Username chosen is not aviable");
			                    Controller.getRegistrationPanel().getLabelAlert().setVisible(true);
			                    Controller.getWindow().layout();
			                    pbWindow.setStop(1); 
								pbWindow = null;
			                    break;
							default:
								Controller.getRegistrationPanel().getLabelAlert().setText("Response not valid from the server"); 
								Controller.getRegistrationPanel().getLabelAlert().setVisible(true);
								Controller.getWindow().layout();
								pbWindow.setStop(1); 
								pbWindow = null;
								break;
							}
							 
							 if(res == 0)
							 {
								
								 Controller.getRegistrationPanel().dispose(Controller.getWindow());
								 Controller.setRegistration_panel(null); 
								 Controller.setWindowName("Login"); 
								 Controller.setLoginPanel(new LoginPanel()); 
								 Controller.getLoginPanel().inizialize(Controller.getWindow()); 
								 pbWindow.setStop(1); 
								 pbWindow = null;
							 }
							 
							}
							else
							{
								Controller.getRegistrationPanel().getLabelAlert().setText("Please enter a valid proxy!"); 
								Controller.getRegistrationPanel().getLabelAlert().setVisible(true);
								Controller.getWindow().layout(); 
								pbWindow.setStop(1); 
								pbWindow = null;
							}
						}
						else
						{
							Controller.getRegistrationPanel().getLabelAlert().setText("Please compile all field correctly!");
							Controller.getRegistrationPanel().getLabelAlert().setVisible(true);
							Controller.getWindow().layout(); 
							pbWindow.setStop(1); 
							pbWindow = null;
						}
			        	
			        	
			        }});
				
				
			}
			
			break;
		case "labelLogin":
			Controller.getRegistrationPanel().dispose(Controller.getWindow());
			Controller.setRegistration_panel(null);
			Controller.setLoginPanel(new LoginPanel()); 
			Controller.setWindowName("Login");  
			Controller.getLoginPanel().inizialize(Controller.getWindow()); 
			Controller.getWindow().layout(); 
			break;
			
		default:
			break;
		}	
		
	}

}
