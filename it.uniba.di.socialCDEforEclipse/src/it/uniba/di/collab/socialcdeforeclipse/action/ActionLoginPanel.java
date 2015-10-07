package it.uniba.di.collab.socialcdeforeclipse.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.model.ProxyWrapper;
import it.uniba.di.collab.socialcdeforeclipse.object.ProgressBarThread;
import it.uniba.di.collab.socialcdeforeclipse.object.ProgressBarWindow;
import it.uniba.di.collab.socialcdeforeclipse.object.SquareButtonService;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.*;
import it.uniba.di.collab.socialcdeforeclipse.staticview.ProfilePanel;
import it.uniba.di.collab.socialcdeforeclipse.staticview.RegistrationPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

public class ActionLoginPanel {

	private ProgressBarThread pbWindow;
	private ProgressBarWindow pbNewW;
	private WUser user;
	private final InputStream PATH_WALLPAPER = this.getClass().getClassLoader()
			.getResourceAsStream("images/Wallpaper.png");

	public Image getImageStream(InputStream stream) {
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
		image.dispose(); 
		return scaled;
	}

	public ActionLoginPanel() {

	}

	public ActionLoginPanel(final HashMap<String, Object> uiData) {

		

		String widgetName = uiData.get("ID_action").toString();
		int type = (int) uiData.get("Event_type");
		Event event = (Event) uiData.get("Event");
		((Label) uiData.get("labelAlert")).setVisible(false);
		//((Label) uiData.get("labelImageUsername")).setImage(null);
		//((Label) uiData.get("labelImagePassword")).setImage(null);
		switch (widgetName) {

		case "loginPanel":
			Controller.setWindowHeight(Controller.getWindow().getSize().y);
			Controller.setWindowWidth(Controller.getWindow().getSize().x);
			Controller.getWindow().setBackgroundImage(
					resize(getImageStream(PATH_WALLPAPER),
							Controller.getWindowWidth(),
							Controller.getWindowHeight()));
			Controller.getWindow().layout();
			break;
		case "btnLogin":
			if (type == SWT.Selection) {
				
				Controller.setProgressBarPositionX(Controller.getWindow()
						.toDisplay(Controller.getWindow().getLocation().x,
								Controller.getWindow().getLocation().y).x);
				Controller.setProgressBarPositionY(Controller.getWindow()
						.toDisplay(Controller.getWindow().getLocation().x,
								Controller.getWindow().getLocation().y).y);


				if(Controller.OSisWindows())
				{
					pbWindow = new ProgressBarThread();
					pbWindow.setLabelTxt("Login in progress..");
					pbWindow.setxCoordinate(Controller.getWindow().toDisplay(
							Controller.getWindow().getLocation().x,
							Controller.getWindow().getLocation().y).x);
					pbWindow.setyCoordinate(Controller.getWindow().toDisplay(
							Controller.getWindow().getLocation().x,
							Controller.getWindow().getLocation().y).y);
					pbWindow.setxCoordinateWithOffset(Controller.getWindow()
							.toDisplay(Controller.getWindow().getLocation().x,
									Controller.getWindow().getLocation().y).x
							+ (Controller.getWindow().getBounds().width - 300) / 2);
					pbWindow.setyCoordinateWithOffset(Controller.getWindow()
							.toDisplay(Controller.getWindow().getLocation().x,
									Controller.getWindow().getLocation().y).y
							+ (Controller.getWindow().getBounds().height - 200) / 2);
					pbWindow.start();
					
					Controller.temporaryInformation.put("ProgressBarThread", pbWindow);

				}
				else if(Controller.OSisUnix())
				{
					pbNewW = new ProgressBarWindow();
					pbNewW.setLabelTxt("Login in progress..");
					pbNewW.setxCoordinate(Controller.getWindow().toDisplay(
							Controller.getWindow().getLocation().x,
							Controller.getWindow().getLocation().y).x);
					pbNewW.setyCoordinate(Controller.getWindow().toDisplay(
							Controller.getWindow().getLocation().x,
							Controller.getWindow().getLocation().y).y);
					pbNewW.setxCoordinateWithOffset(Controller.getWindow()
							.toDisplay(Controller.getWindow().getLocation().x,
									Controller.getWindow().getLocation().y).x
							+ (Controller.getWindow().getBounds().width - 300) / 2);
					pbNewW.setyCoordinateWithOffset(Controller.getWindow()
							.toDisplay(Controller.getWindow().getLocation().x,
									Controller.getWindow().getLocation().y).y
							+ (Controller.getWindow().getBounds().height - 200) / 2);
					
					
					 
					pbNewW.inizialize(Controller.getWindow());
					
				}
				
				
				 
				
				
				
				
				if (Controller.getProxy() == null) {
					Controller.setProxy(new ProxyWrapper());
					if(Controller.OSisUnix())
					{
						pbNewW.updateProgressBar(); 
					} 
					Controller.getProxy().setHost(
							((Text) uiData.get("txtProxyHost")).getText());
					if(Controller.OSisUnix())
					{
						pbNewW.updateProgressBar(); 
					} 
				} else {
					Controller.getProxy().setHost(
							((Text) uiData.get("txtProxyHost")).getText());
					if(Controller.OSisUnix())
					{
						pbNewW.updateProgressBar(); 
					} 
				}
				if(Controller.OSisUnix())
				{
					pbNewW.updateProgressBar(); 
				} 
				
				if (Controller.getProxy().IsWebServiceRunning()) {
					
					if(Controller.OSisUnix())
					{
						pbNewW.updateProgressBar(); 
					} 

					user = Controller.getProxy().GetUser(
							((Text) uiData.get("txtUsername")).getText(),
							((Text) uiData.get("txtPassword")).getText());
					
					if(Controller.OSisUnix())
					{
						pbNewW.updateProgressBar(); 
					} 
					
					if (user == null) {
						
						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						
						((Label) uiData.get("labelAlert"))
								.setText("username or password not valid!");
						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						
						GridData tempGrid = (GridData)  ((Label) uiData.get("labelImageUsernameNo")).getLayoutData(); 
						tempGrid.exclude = false; 
						((Label) uiData.get("labelImageUsernameNo")).setVisible(true); 
						
						tempGrid = (GridData)  ((Label) uiData.get("labelImageUsernameOk")).getLayoutData(); 
						tempGrid.exclude = true;
						((Label) uiData.get("labelImageUsernameOk")).setVisible(false); 
						 
						tempGrid = (GridData)  ((Label) uiData.get("labelHiddenUsername")).getLayoutData(); 
						tempGrid.exclude = true; 
						((Label) uiData.get("labelHiddenUsername")).setVisible(false); 	
						
						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						
						tempGrid = (GridData)  ((Label) uiData.get("labelImagePasswordNo")).getLayoutData(); 
						tempGrid.exclude = false; 
						((Label) uiData.get("labelImagePasswordNo")).setVisible(true); 
						
						tempGrid = (GridData)  ((Label) uiData.get("labelImagePasswordOk")).getLayoutData();
						tempGrid.exclude = true; 
						((Label) uiData.get("labelImagePasswordOk")).setVisible(false); 
						
						tempGrid = (GridData)  ((Label) uiData.get("labelHiddenPassword")).getLayoutData(); 
						tempGrid.exclude = true; 
						((Label) uiData.get("labelHiddenPassword")).setVisible(false); 
						
						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						
						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						

						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						
						
						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						

						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						
						((Label) uiData.get("labelAlert")).setVisible(true);

						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						
						if(Controller.OSisWindows())
						{
							pbWindow.setStop(1);
							pbWindow = null;
						}
						else if(Controller.OSisUnix())
						{
							pbNewW.dispose(null);
							pbNewW = null;
						}
						
						Controller.getWindow().layout(); 
						

					} else {
						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						
						 if (((Button) uiData.get("chkSavePassword"))
								.getSelection()) {
						
							 if(Controller.OSisUnix())
							 {
									pbNewW.updateProgressBar(); 
							  } 
							 
							 Controller.setPreferences("password",
									((Text) uiData.get("txtPassword"))
											.getText());
						} else {
							
							if(Controller.OSisUnix())
							{
								pbNewW.updateProgressBar(); 
							} 
							
							Controller.setPreferences("password", "");
						}
						
						 if(Controller.OSisUnix())
						 {
								pbNewW.updateProgressBar(); 
						 } 
						
						if (((Button) uiData.get("chkAutologin"))
								.getSelection()) {
							Controller.setPreferences("autoLogin", "true");
							
							if(Controller.OSisUnix())
							{
								pbNewW.updateProgressBar(); 
							} 
							
							Controller.setPreferences("FlagAutologin", "False");
							if(Controller.OSisUnix())
							{
								pbNewW.updateProgressBar(); 
							} 
						} else {
							Controller.setPreferences("autoLogin", "false");
							if(Controller.OSisUnix())
							{
								pbNewW.updateProgressBar(); 
							} 
							Controller.setPreferences("FlagAutologin", "False");
							if(Controller.OSisUnix())
							{
								pbNewW.updateProgressBar(); 
							} 
						}

						Controller.setCurrentUser(user);
						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						Controller.setCurrentUserPassword(((Text) uiData
								.get("txtPassword")).getText());
						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						Controller.setPreferences("proxyHost", ((Text) uiData.get("txtProxyHost")).getText());
						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						Controller.setPreferences("proxyRoot",
								((Text) uiData.get("txtProxyHost")).getText());
						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						Controller.setPreferences("username", user.Username);
						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						Controller.setWindowName("Profile");
						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						Controller.getLoginPanel().dispose(null);
						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						Controller.setLoginPanel(null);
						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						SquareButtonService.yCoordinateValue = 5;
						SquareButtonService.counterPosition = 0;
						Controller.setProfilePanel(new ProfilePanel());
						if(Controller.OSisUnix())
						{
							pbNewW.updateProgressBar(); 
						} 
						Controller.getProfilePanel().inizialize(
								Controller.getWindow());
						
						if(Controller.OSisWindows())
						{
							pbWindow.setStop(1);
							pbWindow = null;
						}
						else if(Controller.OSisUnix())
						{
							pbNewW.dispose(null);
							pbNewW = null;
						}						
						

					}
				} else {
					if(Controller.OSisUnix())
					{
						pbNewW.updateProgressBar(); 
					} 
					((Label) uiData.get("labelAlert"))
							.setText("The connection with the Proxy failed");

					GridData tempGrid = (GridData) ((Label) uiData.get("labelImageHostNo")).getLayoutData(); 
					tempGrid.exclude = false;
					((Label) uiData.get("labelImageHostNo")).setVisible(true); 
					
					tempGrid = (GridData) ((Label) uiData.get("labelImageHostOk")).getLayoutData();
					tempGrid.exclude = true;  
					((Label) uiData.get("labelImageHostOk")).setVisible(false); 
					
					tempGrid = (GridData)  ((Label) uiData.get("labelHiddenProxyHost")).getLayoutData(); 
					tempGrid.exclude = true; 
					((Label) uiData.get("labelHiddenProxyHost")).setVisible(false); 
					
					((Label) uiData.get("labelAlert")).setVisible(true);

					if(Controller.OSisWindows())
					{
						pbWindow.setStop(1);
						pbWindow = null;
					}
					else if(Controller.OSisUnix())
					{
						pbNewW.dispose(null);
						pbNewW = null;
					}					
					
					Controller.getWindow().layout(); 
					
				}

			}

			break;
		case "labelRegistration":
			
			if(type == SWT.Selection)
			{
					Controller.setWindowName("Registration");
					Controller.getLoginPanel().dispose(Controller.getWindow());

					Controller.setRegistration_panel(new RegistrationPanel());
					Controller.getRegistrationPanel().inizialize(
							Controller.getWindow());
					Controller.setLoginPanel(null);
					Controller.getWindow().layout();
			}
				
			break;

		/*case "txtProxyHost":

			if (type == SWT.FocusOut) {
				setProxyHost(uiData); 
			}

			break;

		case "txtUsername":

			if (type == SWT.FocusOut) {
				if (InterceptingFilter.verifyText(((Text) uiData
						.get("txtUsername")).getText())) {
					if (Controller.getProxy() != null) {
						
						Display.getDefault().asyncExec(new Runnable() {
			                @Override
			                public void run() {		     
			                	if (!Controller.getProxy().IsAvailable(
										((Text) uiData.get("txtUsername")).getText())) {

									GridData tempGrid = (GridData) ((Label) uiData.get("labelImageUsernameOk")).getLayoutData(); 
									tempGrid.exclude = false; 
									((Label) uiData.get("labelImageUsernameOk")).setVisible(true); 
									
									tempGrid = (GridData) ((Label) uiData.get("labelImageUsernameNo")).getLayoutData(); 
									tempGrid.exclude = true; 
									((Label) uiData.get("labelImageUsernameNo")).setVisible(false); 
									
									tempGrid = (GridData)  ((Label) uiData.get("labelHiddenUsername")).getLayoutData(); 
									tempGrid.exclude = true; 
									((Label) uiData.get("labelHiddenUsername")).setVisible(false); 
									
									((Label) uiData.get("labelAlert"))
											.setVisible(false);

									Controller.getWindow().layout();
								} else {
									((Label) uiData.get("labelAlert"))
											.setText("Please insert a valid username");
									((Label) uiData.get("labelAlert")).setVisible(true);
									GridData tempGrid = (GridData)  ((Label) uiData.get("labelImageUsernameNo")).getLayoutData(); 
									tempGrid.exclude = false; 
									((Label) uiData.get("labelImageUsernameNo")).setVisible(true); 
									
									tempGrid = (GridData)  ((Label) uiData.get("labelImageUsernameOk")).getLayoutData();
									tempGrid.exclude = true; 
									((Label) uiData.get("labelImageUsernameOk")).setVisible(false); 
									
									tempGrid = (GridData)  ((Label) uiData.get("labelHiddenUsername")).getLayoutData(); 
									tempGrid.exclude = true; 
									((Label) uiData.get("labelHiddenUsername")).setVisible(false); 

									Controller.getWindow().layout();
								}
			                		
			               }
			            }); 
						
					} else {
						
						setProxyHost(uiData); 
						
						if(Controller.getProxy() == null)
						{
						
						((Label) uiData.get("labelAlert"))
								.setText("Please insert a valid proxy first!");
						((Label) uiData.get("labelAlert")).setVisible(true);

						GridData tempGrid = (GridData) ((Label) uiData.get("labelImageUsernameNo")).getLayoutData(); 
						tempGrid.exclude = false; 
						((Label) uiData.get("labelImageUsernameNo")).setVisible(true); 
						
						tempGrid = (GridData) ((Label) uiData.get("labelImageUsernameOk")).getLayoutData(); 
						tempGrid.exclude = true; 
						((Label) uiData.get("labelImageUsernameOk")).setVisible(false); 
						
						tempGrid = (GridData)  ((Label) uiData.get("labelHiddenUsername")).getLayoutData(); 
						tempGrid.exclude = true; 
     					((Label) uiData.get("labelHiddenUsername")).setVisible(false); 


						
						tempGrid = (GridData)  ((Label) uiData.get("labelImageHostNo")).getLayoutData(); 
						tempGrid.exclude = false; 
						((Label) uiData.get("labelImageHostNo")).setVisible(true); 
						
						tempGrid = (GridData)  ((Label) uiData.get("labelImageHostOk")).getLayoutData(); 
						tempGrid.exclude = true; 
						((Label) uiData.get("labelImageHostOk")).setVisible(false); 
						
						tempGrid = (GridData)  ((Label) uiData.get("labelHiddenProxyHost")).getLayoutData(); 
						tempGrid.exclude = true; 
						((Label) uiData.get("labelHiddenProxyHost")).setVisible(false); 
							

						Controller.getWindow().layout();
						}
					}
				} else {
					((Label) uiData.get("labelAlert"))
							.setText("Please insert a valid username!");
					((Label) uiData.get("labelAlert")).setVisible(true);
					GridData tempgrid = (GridData) ((Label) uiData.get("labelImageUsernameNo")).getLayoutData(); 
					tempgrid.exclude = false;
					((Label) uiData.get("labelImageUsernameNo")).setVisible(true); 
					
					tempgrid = (GridData) ((Label) uiData.get("labelImageUsernameOk")).getLayoutData(); 
					tempgrid.exclude = true;
					((Label) uiData.get("labelImageUsernameOk")).setVisible(false); 
					
					tempgrid = (GridData)  ((Label) uiData.get("labelHiddenUsername")).getLayoutData(); 
					tempgrid.exclude = true; 
					((Label) uiData.get("labelHiddenUsername")).setVisible(false); 
					

					Controller.getWindow().layout();
				}
			}

			break;
*/
		default:
			break;
		}
	}
	/*
	private void setProxyHost( final HashMap<String, Object> uiData )
	{
		Display.getDefault().asyncExec(new Runnable() {

            @Override
            public void run() {
            	
				if (InterceptingFilter.verifyText(((Text) uiData
						.get("txtProxyHost")).getText())) {
					Controller.setProxy(new ProxyWrapper());
					Controller.getProxy().setHost(
							((Text) uiData.get("txtProxyHost")).getText());
 	
                	if (Controller.getProxy().IsWebServiceRunning()) {

        				GridData tempGrid = (GridData) ((Label) uiData.get("labelImageHostOk")).getLayoutData(); 
        				tempGrid.exclude = false; 
        				((Label) uiData.get("labelImageHostOk")).setVisible(true); 
        				
        				tempGrid = (GridData) ((Label) uiData.get("labelImageHostNo")).getLayoutData(); 
        				tempGrid.exclude = true; 
        				((Label) uiData.get("labelImageHostNo")).setVisible(false); 
        				
        				tempGrid = (GridData)  ((Label) uiData.get("labelHiddenProxyHost")).getLayoutData(); 
        				tempGrid.exclude = true; 
        				((Label) uiData.get("labelHiddenProxyHost")).setVisible(false); 
        				((Label) uiData.get("labelAlert")).setVisible(false);
        				
        				Controller.getWindow().layout(); 
        				

        			} else {

        				Controller.setProxy(null);
        				((Label) uiData.get("labelAlert"))
        						.setText("Please insert a valid proxy!");
        				((Label) uiData.get("labelAlert")).setVisible(true);
        				
        				GridData tempGrid = (GridData) ((Label) uiData.get("labelImageHostNo")).getLayoutData(); 
        				tempGrid.exclude = false; 
        				((Label) uiData.get("labelImageHostNo")).setVisible(true); 
        				
        				tempGrid = (GridData) ((Label) uiData.get("labelImageHostOk")).getLayoutData(); 
        				tempGrid.exclude = true; 
        				 ((Label) uiData.get("labelImageHostOk")).setVisible(false); 
        				 
        				tempGrid = (GridData)  ((Label) uiData.get("labelHiddenProxyHost")).getLayoutData(); 
        				tempGrid.exclude = true; 
        				((Label) uiData.get("labelHiddenProxyHost")).setVisible(false); 
        				
        						

        				Controller.getWindow().layout();
        			}
                		
                
				} else {
					Controller.setProxy(null);
					((Label) uiData.get("labelAlert"))
							.setText("Please insert a valid proxy!");
					((Label) uiData.get("labelAlert")).setVisible(true);
					GridData tempGrid = (GridData) ((Label) uiData.get("labelImageHostNo")).getLayoutData(); 
					tempGrid.exclude = false;
					((Label) uiData.get("labelImageHostNo")).setVisible(true); 
					
					tempGrid = (GridData) ((Label) uiData.get("labelImageHostOk")).getLayoutData(); 
					tempGrid.exclude = true;
					((Label) uiData.get("labelImageHostOk")).setVisible(false); 
					
					tempGrid = (GridData)  ((Label) uiData.get("labelHiddenProxyHost")).getLayoutData(); 
					tempGrid.exclude = true; 
					((Label) uiData.get("labelHiddenProxyHost")).setVisible(false); 
					
					
					Controller.getWindow().layout(); 
				}
		
		
            }
	
		});
	}*/
}
