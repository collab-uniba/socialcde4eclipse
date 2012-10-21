package it.uniba.di.socialcdeforeclipse.action;


import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.views.SquareButtonService;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Widget;

public class ActionSettingPanel {
	public  ActionSettingPanel(Widget widget, int eventType)	{
		
		String widgetName = widget.getData("ID_action").toString();
		
		switch (widgetName) {
			
		case "SettingBtnOk":
			if(eventType == SWT.Selection){
				System.out.println("Azione setting avviata"); 
				if(!Controller.getCurrentUserPassword().equals(Controller.getSettingWindow().getTxtOldPassword().getText()))
				{
					
					Controller.getSettingWindow().getLabelAlert().setVisible(true); 
					Controller.getSettingWindow().getLabelAlert().setText("Old passord not match with current password"); 
					//Controller.getWindow().layout(); 
				
				} else {
					
					  MessageBox messageBoxWarning = new MessageBox(Controller.getWindow().getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
				        
				        messageBoxWarning.setText("Warning");
				        messageBoxWarning.setMessage("Are you sure to change user password?");
				        int buttonID = messageBoxWarning.open();
				        switch(buttonID) {
				          case SWT.YES:
				        	if(Controller.getProxy().ChangePassword(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), Controller.getSettingWindow().getTxtNewPassword().getText())){
				        	
				        		  MessageBox messageBox_ok = new MessageBox(Controller.getWindow().getShell(), SWT.ICON_INFORMATION | SWT.OK);
				        		  messageBox_ok.setText("Information"); 
				        		  messageBox_ok.setMessage("The new password is set up!"); 
				        		  int response = messageBox_ok.open();
				        		  
				        		  if(response == SWT.OK)
				        		  {
				        			 
				        			  Controller.setCurrentUserPassword(Controller.getSettingWindow().getTxtNewPassword().getText()); 
								       SquareButtonService.yCoordinateValue = 5;
								       SquareButtonService.counterPosition = 0;
				        			  Controller.selectDynamicWindow(0); 
				        			  Controller.getProfilePanel().getComposite_dinamic().layout(); 
				        		  }
				        		 
				        		  
				        	} 
				        	break;
				          default:
				          case SWT.NO:
				        	  MessageBox messageBox_no = new MessageBox(Controller.getWindow().getShell(), SWT.ICON_INFORMATION | SWT.YES);
			        		  messageBox_no.setText("Information");
			        		  messageBox_no.setMessage("Operation aborted!"); 
			        		  messageBox_no.open(); 
				            break;
				        
				        }
					
						
						
					
					
				}
			}
			
			break;

		case "SettingBtnCancel":
			if(eventType == SWT.Selection){ 
					Controller.getSettingWindow().getBtnOk().redraw(); 
					Controller.selectDynamicWindow(0);
					Controller.getProfilePanel().getComposite_dinamic().layout(); 
			}
			
			break;

		default:
			break;
		}
	}
}