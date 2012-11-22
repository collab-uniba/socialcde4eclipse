package it.uniba.di.socialcdeforeclipse.action;

import java.io.InputStream;
import java.util.HashMap;


import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.dynamic.view.InterceptingFilter;

import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;
import it.uniba.di.socialcdeforeclipse.object.ProgressBarThread;
import it.uniba.di.socialcdeforeclipse.staticview.LoginPanel;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

public class ActionRegistrationPanel {
	  
	private ProgressBarThread pbWindow; 
	private static Image IMAGE_OK;
	private static Image IMAGE_NO; 
	
	

	
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
	
	
	public ActionRegistrationPanel()
	{
		
	}
	
	public ActionRegistrationPanel(HashMap<String, Object> uiData)
	{
		String widgetName = uiData.get("ID_action").toString(); 
		int type = (int) uiData.get("Event_type"); 
		Event event = (Event)  uiData.get("Event"); 
		IMAGE_NO = Controller.getRegistrationPanel().getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/no_icon.png"));
		IMAGE_OK = Controller.getRegistrationPanel().getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/yes_icon.png"));
       

		switch (widgetName) {
		
		
		
		case "txtProxyHost":

			if(type == SWT.FocusOut)
			{
				
				if(InterceptingFilter.verifyText( ((Text) uiData.get("ProxyHost")).getText()))
				{
					Controller.setProxy(new ProxyWrapper()); 
					Controller.getProxy().setHost(((Text) uiData.get("ProxyHost")).getText()); 
					if(Controller.getProxy().IsWebServiceRunning())
					{
						
						( (Label) uiData.get("LabelImageHost")).setImage(IMAGE_OK); 
						
						((Label) uiData.get("LabelImageHost")).setData("Image_ok", true);
						((Label) uiData.get("LabelImageHost")).setData("Image_no", false);
						
						( (Label) uiData.get("LabelImageHost")).setVisible(true);
					}
					else
					{
						
						Controller.setProxy(null); 
						
						( (Label) uiData.get("LabelAlert")).setText("Please insert a valid proxy!");
						( (Label) uiData.get("LabelAlert")).setVisible(true); 
						( (Label) uiData.get("LabelImageHost")).setImage(IMAGE_NO);
						
						((Label) uiData.get("LabelImageHost")).setData("Image_ok", false);
						((Label) uiData.get("LabelImageHost")).setData("Image_no", true);
						
						Controller.getWindow().layout(); 
					}
				}
				else
				{
					Controller.setProxy(null); 
					
					( (Label) uiData.get("LabelAlert")).setText("Please insert a valid proxy!");
					( (Label) uiData.get("LabelAlert")).setVisible(true); 
					( (Label) uiData.get("LabelImageHost")).setImage(IMAGE_NO);
					
					((Label) uiData.get("LabelImageHost")).setData("Image_ok", false);
					((Label) uiData.get("LabelImageHost")).setData("Image_no", true);
					
					Controller.getWindow().layout(); 
				}
			}
			
			break;
		
		case "txtUsername":
			
			if(type == SWT.FocusOut)
			{
				if(InterceptingFilter.verifyText( ((Text) uiData.get("Username")).getText()))
				{
					if(Controller.getProxy() != null)
					{
						if(!Controller.getProxy().IsAvailable(((Text) uiData.get("Username")).getText()))
						{
							
							

							( (Label) uiData.get("LabelAlert")).setText("Please insert a valid username!");  
							( (Label) uiData.get("LabelAlert")).setVisible(true); 
							( (Label) uiData.get("LabelImageUsername")).setImage(IMAGE_NO);
							
							((Label) uiData.get("LabelImageUsername")).setData("Image_ok", false);
							((Label) uiData.get("LabelImageUsername")).setData("Image_no", true);
							
							Controller.getWindow().layout(); 
						}
						else
						{
							
							( (Label) uiData.get("LabelImageUsername")).setImage(IMAGE_OK);
							
							((Label) uiData.get("LabelImageUsername")).setData("Image_ok", true);
							((Label) uiData.get("LabelImageUsername")).setData("Image_no", false);
							
							( (Label) uiData.get("LabelAlert")).setVisible(false); 
							Controller.getWindow().layout(); 
						}
					}
					else
					{
						( (Label) uiData.get("LabelAlert")).setText("Please insert a valid proxy first!");  
						( (Label) uiData.get("LabelAlert")).setVisible(true); 
						 

						
						( (Label) uiData.get("LabelImageUsername")).setImage(IMAGE_NO);
						( (Label) uiData.get("LabelImageHost")).setImage(IMAGE_NO);
						
						((Label) uiData.get("LabelImageUsername")).setData("Image_no", true);
						((Label) uiData.get("LabelImageUsername")).setData("Image_ok", false);
						
						((Label) uiData.get("LabelImageHost")).setData("Image_no", true);
						((Label) uiData.get("LabelImageHost")).setData("Image_ok", false);
						
						Controller.getWindow().layout();
					
					}
				}
				else
				{
					( (Label) uiData.get("LabelAlert")).setText("Please insert a valid username!");  
					( (Label) uiData.get("LabelAlert")).setVisible(true);
					( (Label) uiData.get("LabelImageUsername")).setImage(IMAGE_NO);
					
					((Label) uiData.get("LabelImageUsername")).setData("Image_no", true);
					((Label) uiData.get("LabelImageUsername")).setData("Image_ok", false);
					
					Controller.getWindow().layout(); 
				}
			}
			
			break;
		case "txtMail":
			if(type == SWT.FocusOut){
				if(InterceptingFilter.verifyMail(  ((Text) uiData.get("Email") ).getText()) ){
					( (Label) uiData.get("LabelImageMail")).setImage(IMAGE_OK);
					
					((Label) uiData.get("LabelImageMail")).setData("Image_ok", true);
					((Label) uiData.get("LabelImageMail")).setData("Image_no", false);
					
					( (Label) uiData.get("LabelAlert")).setVisible(false); 
					Controller.getWindow().layout(); 
		
				} else {
					( (Label) uiData.get("LabelAlert")).setText("Please insert a valid mail!");  
					( (Label) uiData.get("LabelAlert")).setVisible(true);
					( (Label) uiData.get("LabelImageMail")).setImage(IMAGE_NO);
					
					((Label) uiData.get("LabelImageMail")).setData("Image_no", true);
					((Label) uiData.get("LabelImageMail")).setData("Image_ok", false);
					
					Controller.getWindow().layout(); 
					
				}
			}
			
			
			
			break;
			
		case "btnRegister":
			
			if(type == SWT.Selection)
			{
				 
				pbWindow = new ProgressBarThread(); 
				pbWindow.setLabelTxt("Login in progress..");
				pbWindow.setxCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x); 
				pbWindow.setyCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y); 
				Controller.setProgressBarPositionX(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x); 
				Controller.setProgressBarPositionY(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y); 

				//pbThread.setWidth(Controller.getWindow().getSize().x);
				//pbThread.setHeight(Controller.getWindow().getSize().y); 
				pbWindow.start(); 
				HashMap<String, String> dataExtracted = new HashMap<String,String>(); 
				dataExtracted.put("Username", ((Text) uiData.get("Username")).getText());
				dataExtracted.put("Password1", ((Text) uiData.get("Password1")).getText());
				dataExtracted.put("Password2", ((Text) uiData.get("Password2")).getText());
				dataExtracted.put("InvitationCode", ((Text) uiData.get("InvitationCode")).getText()); 
				dataExtracted.put("Email", ((Text) uiData.get("Email")).getText());
				dataExtracted.put("ProxyHost", ((Text) uiData.get("ProxyHost")).getText());
				int res = actionRegister(dataExtracted);
				dataExtracted.clear(); 
				
				switch (res) {
				
				case -3:
					( (Label) uiData.get("LabelAlert")).setText("Please compile all field correctly!");
					( (Label) uiData.get("LabelAlert")).setVisible(true);
					break;
			
				case -2:
					( (Label) uiData.get("LabelAlert")).setText("Please enter a valid proxy!"); 
					( (Label) uiData.get("LabelAlert")).setVisible(true);
					break;
				
				case -1:
					( (Label) uiData.get("LabelAlert")).setText("There's a problem. Check your connection and try again");
					/*
					Controller.getWindow().layout();
					pbWindow.setStop(1);  
					pbWindow = null;
					*/
					break;
				case 0:
					
					boolean password = Controller.getProxy().ChangePassword(((Text) uiData.get("Username")).getText(), ((Text) uiData.get("InvitationCode")).getText(),((Text) uiData.get("Password1")).getText()); 
					 
					if(password)
					{
						Controller.setPreferences("ProxyHost", Controller.getProxy().getHost()); 
						Controller.setPreferences("Username", ((Text) uiData.get("Username")).getText()); 
						Controller.setPreferences("ProxyRoot",((Text) uiData.get("ProxyHost")).getText()); 
						Controller.setPreferences("Password", ((Text) uiData.get("Password1")).getText()); 
						Controller.setPreferences("Email",((Text) uiData.get("Email")).getText()); 
					}
					else
					{
						( (Label) uiData.get("LabelAlert")).setText("There's a problem. Check your connection and try again");
						( (Label) uiData.get("LabelAlert")).setVisible(true);
						/*
						Controller.getWindow().layout();
						pbWindow.setStop(1); 
						pbWindow = null;
						*/
					}
					
					break;
				case 1: // if e-mail address does not exist in the database
					( (Label) uiData.get("LabelAlert")).setText("Please insert the email on which you recived the invite");
					( (Label) uiData.get("LabelAlert")).setVisible(true); 
					/*
					Controller.getWindow().layout();
					pbWindow.setStop(1); 
					pbWindow = null;
					*/
					break;
				case 2:
					// if password does not match with the e-mail address sent
					( (Label) uiData.get("LabelAlert")).setText("Please insert the invitation code that you recived in the invite");
					( (Label) uiData.get("LabelAlert")).setVisible(true);
					/*
                    Controller.getWindow().layout();
                    pbWindow.setStop(1); 
					pbWindow = null;
					*/
                    break;
                case 3: // if username is already used by another user
                	( (Label) uiData.get("LabelAlert")).setText("The Username chosen is not aviable");
                	( (Label) uiData.get("LabelAlert")).setVisible(true);
                	/*
                    Controller.getWindow().layout();
                    pbWindow.setStop(1); 
					pbWindow = null;
					*/
                    break;
				default:
					( (Label) uiData.get("LabelAlert")).setText("Response not valid from the server"); 
					( (Label) uiData.get("LabelAlert")).setVisible(true);
					/*
					Controller.getWindow().layout();
					pbWindow.setStop(1); 
					pbWindow = null;
					*/
					break;
				}
				
				 if(res == 0)
				 {
					
					 Controller.getRegistrationPanel().dispose(Controller.getWindow());
					 Controller.setRegistration_panel(null); 
					 Controller.setWindowName("Login"); 
					 Controller.setLoginPanel(new LoginPanel()); 
					 Controller.getLoginPanel().inizialize(Controller.getWindow()); 
					 /*
					 pbWindow.setStop(1); 
					 pbWindow = null;
					 */
				 }
				
				Controller.getWindow().layout();
				pbWindow.setStop(1); 
				pbWindow = null;
				
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
	
	public  int actionRegister( HashMap<String, String> dataExtracted)
	{
		int res = -4;
	        	if(InterceptingFilter.verifyRegistrationPanel(dataExtracted))
				{
					if(Controller.getProxy() != null)
					{
						 
						//registra
					 res = Controller.getProxy().SubscribeUser( dataExtracted.get("Email"),  dataExtracted.get("InvitationCode"), dataExtracted.get("Username"));
					
					 
					 
					
					 
					}
					else
					{
						Controller.setProxy(new ProxyWrapper()); 
						Controller.getProxy().setHost(dataExtracted.get("ProxyHost")); 
						
						if(Controller.getProxy().IsWebServiceRunning())
						{
							 res = Controller.getProxy().SubscribeUser( dataExtracted.get("Email"),  dataExtracted.get("InvitationCode"), dataExtracted.get("Username"));
							
						}
						else
						{
							res = -2; 
						}
						/*
						Controller.getWindow().layout(); 
						pbWindow.setStop(1); 
						pbWindow = null;
						*/
					}
				}
				else
				{
					res = -3; 
					
					/*
					Controller.getWindow().layout(); 
					pbWindow.setStop(1); 
					pbWindow = null;
					*/
				}
	        	
	        	
	        	return res; 
	}
	
	
	

}
