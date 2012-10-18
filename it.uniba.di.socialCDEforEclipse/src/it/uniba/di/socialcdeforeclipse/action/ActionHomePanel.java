package it.uniba.di.socialcdeforeclipse.action;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.ws.Dispatch;

import it.uniba.di.socialCDEforEclipse.SharedLibrary.WOAuthData;
import it.uniba.di.socialCDEforEclipse.SharedLibrary.WService;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.popup.ChooseAvatar;
import it.uniba.di.socialcdeforeclipse.popup.PinPanel;
import it.uniba.di.socialcdeforeclipse.popup.SettingServicePanel;
import it.uniba.di.socialcdeforeclipse.popup.SocialMessageBox;
import it.uniba.di.socialcdeforeclipse.views.SquareButtonService;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;

public class ActionHomePanel {

	WOAuthData oauthData; 
    
    PinPanel pinWindow = new PinPanel();
		
	
	


	public ActionHomePanel(Widget widget, int eventType)	{
	
		String widgetName = widget.getData("ID_action").toString(); 
	    IViewPart browser = null; 
		
		switch (widgetName) {
		
		
		case "labelAvatar":
			System.out.println("Evento labelAvatar attivato"); 
			final ChooseAvatar available_avatar = new ChooseAvatar(); 
			 available_avatar.setxCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x); 
			 available_avatar.setyCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y); 
			 available_avatar.setxCoordinateWithOffset(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x + (Controller.getWindow().getBounds().width - 300) / 2); 
			 available_avatar.setyCoordinateWithOffset(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y + (Controller.getWindow().getBounds().height - 200) / 2); 
			 available_avatar.setBackListener(new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					// TODO Auto-generated method stub
					available_avatar.dispose(Controller.getWindow()); 
				}
			});
			 available_avatar.inizialize(Controller.getWindow());
			break;
		case "labelSettings":
			if(eventType == SWT.PUSH){
				System.out.println("Labelsettings azione"); 
				Controller.selectDynamicWindow(1);
			}
			break;
		case "btnServices":
			if(eventType == SWT.Selection)
			{
		
		 WService service = (WService) widget.getData("service"); 
				if(service.Registered){
					System.out.println("Servizio registrato");
					final SettingServicePanel serviceSetting = new SettingServicePanel(); 
					serviceSetting.setxCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x ); 
					serviceSetting.setyCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y ); 
					serviceSetting.setxCoordinateWithOffset(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x + (Controller.getWindow().getBounds().width - 300) / 2); 
					serviceSetting.setyCoordinateWithOffset(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y + (Controller.getWindow().getBounds().height - 200) / 2);
					serviceSetting.setService(service); 
					SquareButtonService.yCoordinateValue = 5;
					serviceSetting.setBtnUnsubscriveListener(new Listener() {
						
						@Override
						public void handleEvent(Event event) {
							// TODO Auto-generated method stub
							MessageBox messageBox = new MessageBox(Controller.getWindow().getShell(), SWT.ICON_WARNING  | SWT.YES | SWT.NO);
					        messageBox.setMessage("Are you sure you want to unsubscribe?");
					        messageBox.setText("SocialCDEforEclipse Message");
					       int response = messageBox.open();
					       
					       switch (response) {
						case SWT.YES:
							if(!Controller.getProxy().DeleteRegistredService(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), serviceSetting.getService().Id))
							{
								MessageBox messageBox2 = new MessageBox(Controller.getWindow().getShell(), SWT.ICON_ERROR  | SWT.OK);
						        messageBox2.setMessage("Something was wrong, please try again.");
						        messageBox2.setText("SocialCDEforEclipse Message");
						        messageBox2.open();
							}
							break;
						case SWT.NO:
						default:
							break;
						}
					       serviceSetting.dispose(null); 
					       SquareButtonService.yCoordinateValue = 5;
					       Controller.selectDynamicWindow(0); 
					       
					       
						}
					});
					
					serviceSetting.setBtnSaveListener(new Listener() {
						
						@Override
						public void handleEvent(Event event) {
							// TODO Auto-generated method stub
						
							ArrayList<Button> btnCheckbox =	serviceSetting.getCheckboxCreated(); 
							
							int counter = 0; 
							for(int i=0;i<btnCheckbox.size();i++)
							{
								if(btnCheckbox.get(i).getSelection())
								{
									counter +=1;
								}
							}
							
							String[] strFeature = new String[counter];
							counter = 0; 
							for(int i=0;i< btnCheckbox.size(); i++)
							{
								if(btnCheckbox.get(i).getSelection())
								{
									strFeature[counter] = btnCheckbox.get(i).getData("FeatureName").toString(); 
									counter +=1;
								}
							}
							
							if(Controller.getProxy().UpdateChosenFeatures(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), serviceSetting.getService().Id, strFeature))
							{
								serviceSetting.dispose(null);
							}
							else
							{
								MessageBox messageBox2 = new MessageBox(Controller.getWindow().getShell(), SWT.ICON_ERROR  | SWT.OK);
						        messageBox2.setMessage("Something was wrong, please try again.");
						        messageBox2.setText("SocialCDEforEclipse Message");
						        messageBox2.open();
			            		
							}
						}
					});
					
					serviceSetting.inizialize(Controller.getWindow()); 
					
				}
				else
				{
					System.out.println("Servizio non registrato");
					if(service.RequireOAuth)
					{
						 //int x = bounds.x + (Controller.getWindow().getShell().getBounds().width - 300) / 2;
						 //int y = bounds.y + (Controller.getWindow().getShell().getBounds().height - 200) / 2;
						
					    
						 pinWindow.setxCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x); 
						 pinWindow.setyCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y); 
						 pinWindow.setxCoordinateWithOffset(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x + (Controller.getWindow().getBounds().width - 300) / 2); 
						 pinWindow.setyCoordinateWithOffset(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y + (Controller.getWindow().getBounds().height - 200) / 2);
						 pinWindow.setService(service); 
						 pinWindow.setOauthVersion(service.OAuthVersion);
						
						oauthData = Controller.getProxy().GetOAuthData(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(),service.Id);
						System.out.println(oauthData.AuthorizationLink); 
						Controller.temporaryInformation.put("CurrentURL", oauthData.AuthorizationLink); 
						
						//Controller.temporaryInformation.put("CurrentURL","http://www.google.it");
						Controller.temporaryInformation.put("Service", service); 
						
						pinWindow.setOkListener(new Listener() {
							
							@Override
							public void handleEvent(Event event) {
								// TODO Auto-generated method stub
								
					           ServiceOkClick(); 
					           
							}
						}); 
						pinWindow.setCancelListener(new Listener() {
							
							@Override
							public void handleEvent(Event event) {
								// TODO Auto-generated method stub
								ServiceCancelClick(); 
							}
						}); 
						
							pinWindow.inizialize(Controller.getWindow()); 
						
						
						
						try {
						 	PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("it.uniba.di.socialcdeforeclipse.views.SocialCDEviewBrowser"); 
						
						} catch (PartInitException e1) {
							System.out.println("Eccezione browser lanciata"); 
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						
								
					
						
						// Popup oauth
						
					}
					else if (service.RequireTFSAuthentication)
					{
						//tfs login
					}
				}
			
				System.out.println("Servizio richiesto"); 
			}
			break;

		default:
			break;
		}
		
	}
	
	private void ServiceCancelClick()
	{
		
		
		try {
    		((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEviewBrowser"); 
    		((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview").setFocus(); 
    		((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().hideView(((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEviewBrowser")); 

		} catch (Exception e) {
			// TODO: handle exception
			try {
				((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().showView("it.uniba.di.socialcdeforeclipse.views.SocialCDEviewBrowser");
				((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().hideView(((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEviewBrowser"));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				MessageBox messageBox2 = new MessageBox(Controller.getWindow().getShell(), SWT.ICON_ERROR  | SWT.OK);
		        messageBox2.setMessage("Something was wrong, please try again.");
		        messageBox2.setText("SocialCDEforEclipse Message");
		        messageBox2.open();
				/*
				SocialMessageBox msgBox = new SocialMessageBox();
        		msgBox.setMessage("Something was wrong, try again"); 
        		msgBox.setIcon(SWT.ICON_ERROR); 
        		msgBox.start(); 
				*/
			}
		}
		
		pinWindow.dispose(Controller.getWindow());
	}
	
	
	private void ServiceOkClick()
	{
		oauthData = Controller.getProxy().GetOAuthData(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(),pinWindow.getService().Id);
		
		switch (pinWindow.getOauthVersion())
        {
            case 1:
            	if(Controller.getProxy().Authorize(Controller.getCurrentUser().Username,Controller.getCurrentUserPassword(), pinWindow.getService().Id , pinWindow.getTxtPin().getText(), oauthData.AccessToken, oauthData.AccessSecret))
            	{
            		
            		//oauthData = null;
            		System.out.println("Ok pinwindow premuto");
            		//Controller.selectDynamicWindow(0); 
            	}
            	else
            	{
            		/*
            		SocialMessageBox msgBox = new SocialMessageBox();
            		msgBox.setMessage("Something was wrong, try again"); 
            		msgBox.setIcon(SWT.ICON_ERROR); 
            		msgBox.start();
            		*/
            		MessageBox messageBox2 = new MessageBox(Controller.getWindow().getShell(), SWT.ICON_ERROR  | SWT.OK);
    		        messageBox2.setMessage("Something was wrong, please try again.");
    		        messageBox2.setText("SocialCDEforEclipse Message");
    		        messageBox2.open();
    		        pinWindow.dispose(null); 
            		System.out.println("Autorizzazione non confermata"); 
            	}
            	
            	try {
            		((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEviewBrowser"); 
            		((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview").setFocus(); 
            		((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().hideView(((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEviewBrowser")); 

				} catch (Exception e) {
					// TODO: handle exception
					try {
						((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().showView("it.uniba.di.socialcdeforeclipse.views.SocialCDEviewBrowser");
						((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().hideView(((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEviewBrowser"));
					} catch (NullPointerException|SWTException|PartInitException e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
						SocialMessageBox msgBox = new SocialMessageBox();
	            		msgBox.setMessage("Something was wrong, try again"); 
	            		msgBox.setIcon(SWT.ICON_ERROR); 
	            		msgBox.start(); 
					}
					
					
				}
            	break;
            case 2:
            	//System.out.println("Location " + Controller.temporaryInformation.get("AccessToken").toString());
            	try {
            		
            		if(Controller.getProxy().Authorize(Controller.getCurrentUser().Username,Controller.getCurrentUserPassword(), pinWindow.getService().Id , null, Controller.temporaryInformation.get("AccessToken").toString(),null))
                	{
            			pinWindow.dispose(Controller.getWindow()); 
                		pinWindow.getService().Registered = true; 
                		oauthData = null;
                		
                		for(int i=0;i< ((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().getViewReferences().length;i++)
                		{
                			System.out.println("View n. " + i + " partname " + ((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().getViewReferences()[i].getPartName());
                			System.out.println("View n. " + i + " id " + ((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().getViewReferences()[i].getId());
                			System.out.println("View n. " + i + " title " + ((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().getViewReferences()[i].getTitle());
                		}
                		
                		((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview").setFocus(); 
                		Controller.selectDynamicWindow(0); 
                		
                	}
                	else
                	{
                		pinWindow.dispose(Controller.getWindow()); 
                		MessageBox messageBox = new MessageBox(Controller.getWindow().getShell(),SWT.ICON_ERROR);
                	    messageBox.setMessage("Something was wrong, try again");
                	    messageBox.open();
                	    
                	    
                		System.out.println("Autorizzazione non confermata"); 
                	}
				} catch (Exception e) {
					
					pinWindow.dispose(Controller.getWindow()); 
					
					SocialMessageBox msgBox = new SocialMessageBox();
            		msgBox.setMessage("Something was wrong, try again"); 
            		msgBox.setIcon(SWT.ICON_ERROR); 
            		msgBox.start(); 
            		
					
					
				}
            	
            	
            	
            	try {
            		((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEviewBrowser"); 
            		((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview").setFocus(); 
            		((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().hideView(((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEviewBrowser")); 

				} catch (Exception e) {
					// TODO: handle exception
					try {
						((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().showView("it.uniba.di.socialcdeforeclipse.views.SocialCDEviewBrowser");
						((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().hideView(((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEviewBrowser"));
					} catch (NullPointerException|SWTException|PartInitException e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
						SocialMessageBox msgBox = new SocialMessageBox();
	            		msgBox.setMessage("Something was wrong, try again"); 
	            		msgBox.setIcon(SWT.ICON_ERROR); 
	            		msgBox.start(); 
					}
					
					
				}
            	
            	
            	
            	
            	
			
            	 
            	
                break;
        }
	}
}
