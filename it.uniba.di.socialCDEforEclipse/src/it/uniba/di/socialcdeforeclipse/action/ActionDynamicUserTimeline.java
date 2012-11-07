package it.uniba.di.socialcdeforeclipse.action;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.popup.HideUserPanel;
import it.uniba.di.socialcdeforeclipse.popup.SettingServicePanel;
import it.uniba.di.socialcdeforeclipse.popup.SkillsPanel;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.WUser;
import it.uniba.di.socialcdeforeclipse.views.SquareButtonService;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Widget;

public class ActionDynamicUserTimeline {

	public ActionDynamicUserTimeline(final Widget widget, Event event)
	{
		String widgetName = widget.getData("ID_action").toString();
		final WUser user_selected = (WUser)  widget.getData("user");
		
		
		switch (widgetName) {
		case "labelBack":
			Controller.selectDynamicWindow(2); 
			break;
			
		case "labelFollow":
			System.out.println(" Follow chiamato");
			if(Controller.getProxy().Follow(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), user_selected.Id))
			{
				System.out.println("Follow riuscito"); 
			  Label	labelUnfollow = new Label(((Label) widget).getParent()  ,SWT.RIGHT); 
				labelUnfollow.setImage(Controller.getDynamicUserWindow().get_ImageStream(this.getClass().getClassLoader().getResourceAsStream("images/UnFollow.png")));
				labelUnfollow.setCursor( new Cursor(Display.getCurrent(), SWT.CURSOR_HAND)); 
				labelUnfollow.setData("user", user_selected); 
				Rectangle dimensionLabel =  Controller.getDynamicUserWindow().getLabelFollow().getBounds();
				 Controller.getDynamicUserWindow().getLabelFollow().dispose(); 
				 Controller.getProfilePanel().getComposite_dinamic().redraw(dimensionLabel.x, dimensionLabel.y, dimensionLabel.width, dimensionLabel.height, true);
				 Controller.getProfilePanel().getComposite_dinamic().layout(); 
				 labelUnfollow.setBounds(dimensionLabel);
				 labelUnfollow.setData("ID_action", "labelUnfollow"); 
				 labelUnfollow.addListener(SWT.MouseDown, Controller.getDynamicUserWindow().getAzioni());
						 
					
				 Controller.getDynamicUserWindow().setLabelFollow(labelUnfollow); 
				Controller.getProfilePanel().getComposite_dinamic().redraw(dimensionLabel.x, dimensionLabel.y, dimensionLabel.width, dimensionLabel.height, true);
				Controller.getProfilePanel().getComposite_dinamic().layout(); 
			}
			break;
			
		case "labelUnfollow":
			System.out.println(" Unfollow chiamato");
			if(Controller.getProxy().UnFollow(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), user_selected.Id))
			{
				System.out.println("unFollow riuscito"); 
				Label labelFollow = new Label(((Label) widget).getParent(),SWT.RIGHT); 
				labelFollow.setImage(Controller.getDynamicUserWindow().get_ImageStream(this.getClass().getClassLoader().getResourceAsStream("images/Follow.png")));
				labelFollow.setCursor( new Cursor(Display.getCurrent(), SWT.CURSOR_HAND)); 
				labelFollow.setData("user", user_selected); 
				Rectangle dimensionLabel =  Controller.getDynamicUserWindow().getLabelFollow().getBounds();
				 Controller.getDynamicUserWindow().getLabelFollow().dispose(); 
				 Controller.getProfilePanel().getComposite_dinamic().redraw(dimensionLabel.x, dimensionLabel.y, dimensionLabel.width, dimensionLabel.height, true);
				 Controller.getProfilePanel().getComposite_dinamic().layout(); 
				 labelFollow.setBounds(dimensionLabel); 
				 labelFollow.setData("ID_action", "labelFollow");
				 labelFollow.addListener(SWT.MouseDown, Controller.getDynamicUserWindow().getAzioni());
				 
				 Controller.getDynamicUserWindow().setLabelFollow(labelFollow); 
				 Controller.getProfilePanel().getComposite_dinamic().redraw(dimensionLabel.x, dimensionLabel.y, dimensionLabel.width, dimensionLabel.height, true);
				 Controller.getProfilePanel().getComposite_dinamic().layout(); 
			}
			
			break;
			
		case "labelSkills":
			final SkillsPanel skillPanel = new SkillsPanel(); 
			skillPanel.setxCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x); 
			skillPanel.setyCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y); 
			skillPanel.setxCoordinateWithOffset(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x + (Controller.getWindow().getBounds().width - 300) / 2); 
			skillPanel.setyCoordinateWithOffset(150); 
			skillPanel.setUser_selected(user_selected); 
			skillPanel.setBackListener(new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					// TODO Auto-generated method stub
					skillPanel.dispose(Controller.getWindow());
				}
			}); 
			skillPanel.inizialize(Controller.getWindow()); 
			
			break;
			
		case "labelHide":
			
			final HideUserPanel hideUserSetting = new HideUserPanel(); 
			hideUserSetting.setxCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x ); 
			hideUserSetting.setyCoordinate(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).y ); 
			hideUserSetting.setxCoordinateWithOffset(Controller.getWindow().toDisplay(Controller.getWindow().getLocation().x, Controller.getWindow().getLocation().y).x  - 30 + (Controller.getWindow().getBounds().width - 300) / 2); 
			hideUserSetting.setyCoordinateWithOffset(150);
			hideUserSetting.setUser_selected(user_selected);
			//SquareButtonService.flagDimension = false; 
			hideUserSetting.setBtnCancelListener(new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					// TODO Auto-generated method stub
					
			       hideUserSetting.dispose(null); 
			       //SquareButtonService.flagDimension = false; 
			       
			       
			       
			       
			       
			       
				}
			});
			
			hideUserSetting.setBtnSaveListener(new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					// TODO Auto-generated method stub
				
					HashMap<String, Button> btnCheckbox =	hideUserSetting.getCheckboxCreated(); 
					
					if(!Controller.getProxy().UpdateHiddenUser(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), user_selected.Id, ((Button) btnCheckbox.get("suggestion")).getSelection(), ((Button) btnCheckbox.get("iteration")).getSelection(), ((Button) btnCheckbox.get("interactive")).getSelection())  )
					{
						MessageBox messageBox = new MessageBox(Controller.getWindow().getShell(), SWT.ICON_WARNING  | SWT.YES);
				        messageBox.setMessage("Something was wrong, please try again!");
				        messageBox.setText("SocialCDEforEclipse Message");
				        messageBox.open();
					}
					
					 hideUserSetting.dispose(null); 
					
				}
			});
			
			hideUserSetting.inizialize(Controller.getWindow()); 
			 
			break;
			

		default:
			break;
		}
		
	}
	
}
