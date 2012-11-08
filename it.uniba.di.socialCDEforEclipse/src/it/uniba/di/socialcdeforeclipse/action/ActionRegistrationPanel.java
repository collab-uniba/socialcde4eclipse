package it.uniba.di.socialcdeforeclipse.action;

import java.io.InputStream;
import java.util.HashMap;


import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.dynamicView.InterceptingFilter;

import it.uniba.di.socialcdeforeclipse.model.ProxyWrapper;
import it.uniba.di.socialcdeforeclipse.staticView.LoginPanel;
import it.uniba.di.socialcdeforeclipse.staticView.ProgressBarThread;


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
	
	public ActionRegistrationPanel(final Widget widget, Event event)
	{
		String widgetName = widget.getData("ID_action").toString(); 
		IMAGE_OK = Controller.getRegistrationPanel().getImageStream(Controller.getRegistrationPanel().getPathIconOk());
		IMAGE_NO = Controller.getRegistrationPanel().getImageStream(Controller.getRegistrationPanel().getPathIconError());
       

		switch (widgetName) {
		
		
		
		case "txtProxyHost":

			if(event.type == SWT.FocusOut)
			{
				
				if(InterceptingFilter.verifyText( ((Text) widget.getData("ProxyHost")).getText()))
				{
					Controller.setProxy(new ProxyWrapper()); 
					Controller.getProxy().setHost(((Text) widget.getData("ProxyHost")).getText()); 
					if(Controller.getProxy().IsWebServiceRunning())
					{
						
						( (Label) widget.getData("LabelImageHost")).setImage(IMAGE_OK); 
						( (Label) widget.getData("LabelImageHost")).setVisible(true);
					}
					else
					{
						
						Controller.setProxy(null); 
						
						( (Label) widget.getData("LabelAlert")).setText("Please insert a valid proxy!");
						( (Label) widget.getData("LabelAlert")).setVisible(true); 
						( (Label) widget.getData("LabelImageHost")).setImage(IMAGE_NO);
						Controller.getWindow().layout(); 
					}
				}
			}
			
			break;
		
		case "txtUsername":
			
			if(event.type == SWT.FocusOut)
			{
				if(InterceptingFilter.verifyText( ((Text) widget.getData("Username")).getText()))
				{
					if(Controller.getProxy() != null)
					{
						if(!Controller.getProxy().IsAvailable(((Text) widget.getData("Username")).getText()))
						{
							
							

							( (Label) widget.getData("LabelAlert")).setText("Please insert a valid username");  
							( (Label) widget.getData("LabelAlert")).setVisible(true); 
							( (Label) widget.getData("LabelImageUsername")).setImage(IMAGE_NO);
							Controller.getWindow().layout(); 
						}
						else
						{
							
							( (Label) widget.getData("LabelImageUsername")).setImage(IMAGE_OK);
							( (Label) widget.getData("LabelAlert")).setVisible(false); 
							Controller.getWindow().layout(); 
						}
					}
					else
					{
						( (Label) widget.getData("LabelAlert")).setText("Please insert a valid proxy first!");  
						( (Label) widget.getData("LabelAlert")).setVisible(true); 
						 

						
						( (Label) widget.getData("LabelImageUsername")).setImage(IMAGE_NO);
						( (Label) widget.getData("LabelImageHost")).setImage(IMAGE_NO);
						Controller.getWindow().layout();
					
					}
				}
				else
				{
					( (Label) widget.getData("LabelAlert")).setText("Please insert a valid username!");  
					( (Label) widget.getData("LabelAlert")).setVisible(true);
					( (Label) widget.getData("LabelImageUsername")).setImage(IMAGE_NO);
					Controller.getWindow().layout(); 
				}
			}
			
			break;
		case "txtMail":
			if(event.type == SWT.FocusOut){
				if(InterceptingFilter.verifyMail(  ((Text) widget ).getText()) ){
					( (Label) widget.getData("LabelImageMail")).setImage(IMAGE_OK);
					( (Label) widget.getData("LabelAlert")).setVisible(false); 
					Controller.getWindow().layout(); 
		
				} else {
					( (Label) widget.getData("LabelAlert")).setText("Please insert a valid mail!");  
					( (Label) widget.getData("LabelAlert")).setVisible(true);
					( (Label) widget.getData("LabelImageMail")).setImage(IMAGE_NO);
					Controller.getWindow().layout(); 
					
				}
			}
			
			
			
			break;
			
		case "btnRegister":
			
			if(event.type == SWT.Selection)
			{
				System.out.println("Scatta evento registrazione"); 
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
				dataExtracted.put("Username", ((Text) widget.getData("Username")).getText());
				dataExtracted.put("Password1", ((Text) widget.getData("Password1")).getText());
				dataExtracted.put("Password2", ((Text) widget.getData("Password2")).getText());
				dataExtracted.put("InvitationCode", ((Text) widget.getData("InvitationCode")).getText()); 
				dataExtracted.put("Email", ((Text) widget.getData("Email")).getText());
				dataExtracted.put("ProxyHost", ((Text) widget.getData("ProxyHost")).getText());
				int res = actionRegister(dataExtracted);
				dataExtracted.clear(); 
				
				switch (res) {
				
				case -3:
					( (Label) widget.getData("LabelAlert")).setText("Please compile all field correctly!");
					( (Label) widget.getData("LabelAlert")).setVisible(true);
					break;
			
				case -2:
					( (Label) widget.getData("LabelAlert")).setText("Please enter a valid proxy!"); 
					( (Label) widget.getData("LabelAlert")).setVisible(true);
					break;
				
				case -1:
					( (Label) widget.getData("LabelAlert")).setText("There's a problem. Check your connection and try again");
					/*
					Controller.getWindow().layout();
					pbWindow.setStop(1);  
					pbWindow = null;
					*/
					break;
				case 0:
					
					boolean password = Controller.getProxy().ChangePassword(((Text) widget.getData("Username")).getText(), ((Text) widget.getData("InvitationCode")).getText(),((Text) widget.getData("Password1")).getText()); 
					System.out.println("Valore di password "+ password); 
					if(password)
					{
						Controller.setPreferences("ProxyHost", Controller.getProxy().getHost()); 
						Controller.setPreferences("Username", ((Text) widget.getData("Username")).getText()); 
						Controller.setPreferences("ProxyRoot",((Text) widget.getData("ProxyHost")).getText()); 
						Controller.setPreferences("Password", ((Text) widget.getData("Password1")).getText()); 
						Controller.setPreferences("Email",((Text) widget.getData("Email")).getText()); 
					}
					else
					{
						( (Label) widget.getData("LabelAlert")).setText("There's a problem. Check your connection and try again");
						( (Label) widget.getData("LabelAlert")).setVisible(true);
						/*
						Controller.getWindow().layout();
						pbWindow.setStop(1); 
						pbWindow = null;
						*/
					}
					
					break;
				case 1: // if e-mail address does not exist in the database
					( (Label) widget.getData("LabelAlert")).setText("Please insert the email on which you recived the invite");
					( (Label) widget.getData("LabelAlert")).setVisible(true); 
					/*
					Controller.getWindow().layout();
					pbWindow.setStop(1); 
					pbWindow = null;
					*/
					break;
				case 2:
					// if password does not match with the e-mail address sent
					( (Label) widget.getData("LabelAlert")).setText("Please insert the invitation code that you recived in the invite");
					( (Label) widget.getData("LabelAlert")).setVisible(true);
					/*
                    Controller.getWindow().layout();
                    pbWindow.setStop(1); 
					pbWindow = null;
					*/
                    break;
                case 3: // if username is already used by another user
                	( (Label) widget.getData("LabelAlert")).setText("The Username chosen is not aviable");
                	( (Label) widget.getData("LabelAlert")).setVisible(true);
                	/*
                    Controller.getWindow().layout();
                    pbWindow.setStop(1); 
					pbWindow = null;
					*/
                    break;
				default:
					( (Label) widget.getData("LabelAlert")).setText("Response not valid from the server"); 
					( (Label) widget.getData("LabelAlert")).setVisible(true);
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
						System.out.println("Proxy corretto"); 
						//registra
					 res = Controller.getProxy().SubscribeUser( dataExtracted.get("Email"),  dataExtracted.get("InvitationCode"), dataExtracted.get("Username"));
					 System.out.println("Valore di res "+ res);
					 
					 
					
					 
					}
					else
					{
						res = -2; 
						
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
