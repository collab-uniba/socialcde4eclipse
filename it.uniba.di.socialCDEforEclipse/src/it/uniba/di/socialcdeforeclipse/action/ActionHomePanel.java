package it.uniba.di.socialcdeforeclipse.action;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.xml.ws.Dispatch;

import it.uniba.di.socialCDEforEclipse.SharedLibrary.WOAuthData;
import it.uniba.di.socialCDEforEclipse.SharedLibrary.WService;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.popup.PinPanel;
import it.uniba.di.socialcdeforeclipse.popup.SettingServicePanel;
import it.uniba.di.socialcdeforeclipse.popup.SocialMessageBox;

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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
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
			
		case "labelSettings":
			if(eventType == SWT.PUSH){
				System.out.println("Labelsettings azione"); 
				Controller.selectDynamicWindow(1);
			}
		case "btnServices":
			if(eventType == SWT.Selection)
			{
		
		 WService service = (WService) widget.getData("service"); 
				if(service.Registered){
					System.out.println("Servizio registrato");
					SettingServicePanel serviceSetting = new SettingServicePanel(); 
					serviceSetting.setxCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x); 
					serviceSetting.setyCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y); 
					serviceSetting.setService(service); 
					serviceSetting.start(); 
					
				}
				else
				{
					System.out.println("Servizio non registrato");
					if(service.RequireOAuth)
					{
						System.out.println("OAuth richiesto");
						
						 
						 pinWindow.setxCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x); 
						 pinWindow.setyCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y); 
						 pinWindow.setService(service); 
						 pinWindow.setOauthVersion(service.OAuthVersion);
						
						oauthData = Controller.getProxy().GetOAuthData(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(),pinWindow.getService().Id);
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
						pinWindow.start(); 	
						
						try {
						 	PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("it.uniba.di.socialcdeforeclipse.views.SocialCDEviewBrowser"); 
						
						} catch (PartInitException e1) {
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
		pinWindow.getDisplay().dispose();
		
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
				SocialMessageBox msgBox = new SocialMessageBox();
        		msgBox.setMessage("Something was wrong, try again"); 
        		msgBox.setIcon(SWT.ICON_ERROR); 
        		msgBox.start(); 
			}
		}
	}
	
	
	private void ServiceOkClick()
	{
		oauthData = Controller.getProxy().GetOAuthData(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(),pinWindow.getService().Id);
		
		
		
		switch (pinWindow.getOauthVersion())
        {
            case 1:
            	if(Controller.getProxy().Authorize(Controller.getCurrentUser().Username,Controller.getCurrentUserPassword(), pinWindow.getService().Id , pinWindow.getTxtPin().getText(), oauthData.AccessToken, oauthData.AccessSecret))
            	{
            		pinWindow.getService().Registered = true;
            		oauthData = null;
            		pinWindow.getDisplay().dispose();
            	}
            	else
            	{
            		SocialMessageBox msgBox = new SocialMessageBox();
            		msgBox.setMessage("Something was wrong, try again"); 
            		msgBox.setIcon(SWT.ICON_ERROR); 
            		msgBox.start(); 
            		System.out.println("Autorizzazione non confermata"); 
            	}
            	break;
            case 2:
            	//System.out.println("Location " + Controller.temporaryInformation.get("AccessToken").toString());
            	try {
            		
            		if(Controller.getProxy().Authorize(Controller.getCurrentUser().Username,Controller.getCurrentUserPassword(), pinWindow.getService().Id , null, Controller.temporaryInformation.get("AccessToken").toString(),null))
                	{
                		pinWindow.getService().Registered = true; 
                		oauthData = null;
                		
                		for(int i=0;i< ((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().getViewReferences().length;i++)
                		{
                			System.out.println("View n. " + i + " partname " + ((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().getViewReferences()[i].getPartName());
                			System.out.println("View n. " + i + " id " + ((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().getViewReferences()[i].getId());
                			System.out.println("View n. " + i + " title " + ((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().getViewReferences()[i].getTitle());
                		}
                		
                		((IWorkbenchWindow) Controller.temporaryInformation.get("Workbench")).getActivePage().findView("it.uniba.di.socialcdeforeclipse.views.SocialCDEview").setFocus(); 
                		pinWindow.getDisplay().dispose();
                	}
                	else
                	{
                		pinWindow.getDisplay().dispose();
                		MessageBox messageBox = new MessageBox(Controller.getWindow().getShell(),SWT.ICON_ERROR);
                	    messageBox.setMessage("Something was wrong, try again");
                	    messageBox.open();
                	    
                		System.out.println("Autorizzazione non confermata"); 
                	}
				} catch (Exception e) {
					
					pinWindow.getDisplay().dispose();
					
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
