package it.uniba.di.socialcdeforeclipse.dynamicView;

import it.uniba.di.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.WUser;
import it.uniba.di.socialcdeforeclipse.views.ButtonAvatar;
import it.uniba.di.socialcdeforeclipse.views.Panel;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

public class DynamicPeople implements Panel{

	
	
	private ArrayList<Control> controlli;
	  
	
	 
	
	
	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0,
		image.getBounds().width, image.getBounds().height,
		0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
		}
	
	public Image getImageStream(InputStream stream)
	{
		return  new Image(Controller.getWindow().getDisplay(),stream); 
	}
	
	@Override
	public void inizialize(Composite panel2) {
		// TODO Auto-generated method stub
		
	 
		
		 Image imageAvatar;
		  
	
	
		
		
		
		GridData grid = new GridData(); 
		
		
		controlli = new ArrayList<Control>();
		Listener azioni = new ActionGeneral();
		panel2.setLayout(new GridLayout(1, true));
		
		
		 
		Label labelSuggestion = new Label(panel2, SWT.NONE); 
		labelSuggestion.setText("Suggestions:"); 
		labelSuggestion.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 14, SWT.BOLD )); 
		grid = new GridData(); 
		grid.horizontalSpan = 3; 
		grid.grabExcessHorizontalSpace = true;
		grid.horizontalAlignment = GridData.FILL; 
		labelSuggestion.setLayoutData(grid); 
		controlli.add(labelSuggestion);
		
		WUser[] suggestion = Controller.getProxy().GetSuggestedFriends(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		//WUser[] suggestion = Controller.getProxy().GetFollowings(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword());
		if(suggestion.length == 0)
		{
			Label labelsuggestionText = new Label(panel2, SWT.WRAP); 
			labelsuggestionText.setText("We have no suggestion for you.\n Please try again soon."); 
			grid = new GridData(); 
			grid.horizontalSpan = 3; 
			grid.grabExcessHorizontalSpace = true;
			grid.horizontalAlignment = GridData.FILL; 
			 
			
			labelsuggestionText.setLayoutData(grid);
			controlli.add(labelsuggestionText);
			
		}
		else
		{
			System.out.println("Persone suggerite " + suggestion.length);
			 
			
			
			for(int i=0;i< suggestion.length;i++)
			{
				Composite compositeSuggestionUser = new Composite(panel2, SWT.BORDER); 
				compositeSuggestionUser.setLayout(new GridLayout(4, false));
				grid = new GridData(); 
				grid.grabExcessHorizontalSpace = true; 
				grid.horizontalAlignment = GridData.FILL; 
				compositeSuggestionUser.setLayoutData(grid); 
				
				compositeSuggestionUser.addListener(SWT.MouseMove, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						// TODO Auto-generated method stub
						System.out.println("Mouse move attivato"); 
						Composite cmp = (Composite)  event.widget; 
						cmp.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GREEN)); 
						cmp.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE)); 
						cmp.redraw(); 
						cmp.layout(); 
						Controller.getWindow().redraw(cmp.getLocation().x, cmp.getLocation().y, cmp.getSize().x, cmp.getSize().y, true); 
						Controller.getWindow().layout(); 
						
					}
				});
				
				compositeSuggestionUser.addListener(SWT.MouseExit, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						// TODO Auto-generated method stub
						System.out.println("Mouse exit attivato");
						Composite cmp = (Composite)  event.widget; 
						cmp.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND)); 
						cmp.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE)); 
						cmp.redraw(); 
						cmp.layout(); 
						Controller.getWindow().redraw(cmp.getLocation().x, cmp.getLocation().y, cmp.getSize().x, cmp.getSize().y, true); 
						Controller.getWindow().layout(); 
					}
				});
				controlli.add(compositeSuggestionUser); 
				
				Label labelUserImage = new Label(compositeSuggestionUser, SWT.None); 
				try {
					labelUserImage.setImage(resize(getImageStream(new URL(suggestion[i].Avatar).openStream()),80,80));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					imageAvatar = getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png"));
					labelUserImage.setImage(resize(imageAvatar,80,80)); 
					imageAvatar.dispose(); 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					imageAvatar = getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png"));
					labelUserImage.setImage(resize(imageAvatar,80,80)); 
					imageAvatar.dispose();  
				} 
				controlli.add(labelUserImage); 
				
				Label labelUserText = new Label(compositeSuggestionUser, SWT.None); 
				labelUserText.setText(suggestion[i].Username); 
				controlli.add(labelUserText); 

			    Label labelHidden = new Label(compositeSuggestionUser, SWT.NONE);
				labelHidden.setText("");
				labelHidden.setVisible(false);
				controlli.add(labelHidden);
				
				Label labelNext = new Label(compositeSuggestionUser, SWT.None); 
				labelNext.setImage(getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/Toolbar/Next.png")));
				grid = new GridData(); 
				grid.grabExcessHorizontalSpace = true; 
				grid.horizontalAlignment = GridData.END; 
				labelNext.setLayoutData(grid); 
				controlli.add(labelNext); 
				
				compositeSuggestionUser.addListener(SWT.MouseDown, azioni); 
				compositeSuggestionUser.setData("ID_action", "User_selected"); 
				compositeSuggestionUser.setData("User_type", "Suggested"); 
				compositeSuggestionUser.setData("User_data",suggestion[i]); 
				
			}
			
			
		}
		
		Label labelFollowings = new Label(panel2, SWT.NONE); 
		labelFollowings.setText("Followings:"); 
		labelFollowings.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 14, SWT.BOLD )); 
		grid = new GridData(); 
		grid.horizontalSpan = 3; 
		grid.grabExcessHorizontalSpace = true; 
		grid.horizontalAlignment = GridData.FILL; 
		labelFollowings.setLayoutData(grid); 
		controlli.add(labelFollowings);
		
		WUser[] following = Controller.getProxy().GetFollowings(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		
		if(following.length == 0)
		{
			Label labelFollowingText = new Label(panel2, SWT.WRAP); 
			labelFollowingText.setText("You are following no one."); 
			grid = new GridData(); 
			grid.horizontalSpan = 3; 
			labelFollowingText.setLayoutData(grid);
			controlli.add(labelFollowingText);
			
		}
		else
		{
			
			GridData grid2; 
			
			for(int i=0;i< following.length;i++)
			{
				
				Composite compositeFollowingUser = new Composite(panel2, SWT.BORDER); 
				compositeFollowingUser.setLayout(new GridLayout(4, false));
				grid2 = new GridData(); 
				grid2.grabExcessHorizontalSpace = true; 
				grid2.horizontalAlignment = GridData.FILL; 
				compositeFollowingUser.setLayoutData(grid2); 
				
				
				compositeFollowingUser.addListener(SWT.MouseMove, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						// TODO Auto-generated method stub
						System.out.println("Mouse move attivato"); 
						Composite cmp = (Composite)  event.widget; 
						cmp.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GREEN)); 
						cmp.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE)); 
						cmp.redraw(); 
						cmp.layout(); 
						Controller.getWindow().redraw(cmp.getLocation().x, cmp.getLocation().y, cmp.getSize().x, cmp.getSize().y, true); 
						Controller.getWindow().layout(); 
						
					}
				});
				
				compositeFollowingUser.addListener(SWT.MouseExit, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						// TODO Auto-generated method stub
						System.out.println("Mouse exit attivato");
						Composite cmp = (Composite)  event.widget; 
						cmp.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND)); 
						cmp.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE)); 
						cmp.redraw(); 
						cmp.layout(); 
						Controller.getWindow().redraw(cmp.getLocation().x, cmp.getLocation().y, cmp.getSize().x, cmp.getSize().y, true); 
						Controller.getWindow().layout(); 
					}
				});
				controlli.add(compositeFollowingUser); 
				
				Label labelUserImage = new Label(compositeFollowingUser, SWT.None); 
				try {
					labelUserImage.setImage(resize(getImageStream(new URL(following[i].Avatar).openStream()),80,80));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					imageAvatar = getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png"));
					labelUserImage.setImage(resize(imageAvatar,80,80)); 
					imageAvatar.dispose(); 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					imageAvatar = getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png"));
					labelUserImage.setImage(resize(imageAvatar,80,80)); 
					imageAvatar.dispose();  
				} 
				
				controlli.add(labelUserImage); 
				
				Label labelUserText = new Label(compositeFollowingUser, SWT.None); 
				labelUserText.setText(following[i].Username); 
				controlli.add(labelUserText);
/*
			    Label labelHidden = new Label(compositeFollowingUser, SWT.NONE);
				labelHidden.setText("");
				labelHidden.setVisible(false);
				controlli.add(labelHidden);
*/				
				Label labelNext = new Label(compositeFollowingUser, SWT.None); 
				labelNext.setImage(getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/Toolbar/Next.png")));
				grid2 = new GridData(); 
				grid2.grabExcessHorizontalSpace = true; 
				grid2.horizontalAlignment = GridData.END; 
				labelNext.setLayoutData(grid2); 
				controlli.add(labelNext);
				
				compositeFollowingUser.addListener(SWT.MouseDown, azioni); 
				compositeFollowingUser.setData("ID_action", "User_selected"); 
				compositeFollowingUser.setData("User_type", "Following"); 
				compositeFollowingUser.setData("User_data",following[i]); 
				
			}
		}
		
		Label labelFollowers = new Label(panel2, SWT.NONE); 
		labelFollowers.setText("Followers: "); 
		labelFollowers.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 14, SWT.BOLD )); 
		grid = new GridData(); 
		grid.horizontalSpan = 3; 
		grid.grabExcessHorizontalSpace = true; 
		grid.horizontalAlignment = GridData.FILL;  
		labelFollowers.setLayoutData(grid); 
		controlli.add(labelFollowers);
		
		WUser[] followers = Controller.getProxy().GetFollowers(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		
		if(followers.length == 0)
		{
			Label labelFollowersText = new Label(panel2, SWT.WRAP); 
			labelFollowersText.setText("No one is following you."); 
			grid = new GridData(); 
			grid.horizontalSpan = 3; 
			labelFollowersText.setLayoutData(grid);
			controlli.add(labelFollowersText);
			
		}
		else
		{
			GridData grid2; 
			for(int i=0;i< followers.length;i++)
			{
				
				Composite compositeFollowersUser = new Composite(panel2, SWT.BORDER); 
				compositeFollowersUser.setLayout(new GridLayout(4, false));
				grid2 = new GridData(); 
				grid2.grabExcessHorizontalSpace = true; 
				grid2.horizontalAlignment = GridData.FILL; 
				compositeFollowersUser.setLayoutData(grid2); 
				
				
				compositeFollowersUser.addListener(SWT.MouseMove, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						// TODO Auto-generated method stub
						System.out.println("Mouse move attivato"); 
						Composite cmp = (Composite)  event.widget; 
						cmp.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GREEN)); 
						cmp.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE)); 
						cmp.redraw(); 
						cmp.layout(); 
						Controller.getWindow().redraw(cmp.getLocation().x, cmp.getLocation().y, cmp.getSize().x, cmp.getSize().y, true); 
						Controller.getWindow().layout(); 
						
					}
				});
				
				compositeFollowersUser.addListener(SWT.MouseExit, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						// TODO Auto-generated method stub
						System.out.println("Mouse exit attivato");
						Composite cmp = (Composite)  event.widget; 
						cmp.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND)); 
						cmp.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE)); 
						cmp.redraw(); 
						cmp.layout(); 
						Controller.getWindow().redraw(cmp.getLocation().x, cmp.getLocation().y, cmp.getSize().x, cmp.getSize().y, true); 
						Controller.getWindow().layout(); 
					}
				});
				controlli.add(compositeFollowersUser); 
				
				Label labelUserImage = new Label(compositeFollowersUser, SWT.None); 
				try {
					labelUserImage.setImage(resize(getImageStream(new URL(followers[i].Avatar).openStream()),80,80));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					imageAvatar = getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png"));
					labelUserImage.setImage(resize(imageAvatar,80,80)); 
					imageAvatar.dispose(); 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					imageAvatar = getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png"));
					labelUserImage.setImage(resize(imageAvatar,80,80)); 
					imageAvatar.dispose();  
				} 
				controlli.add(labelUserImage); 
				
				Label labelUserText = new Label(compositeFollowersUser, SWT.None); 
				labelUserText.setText(followers[i].Username); 
				grid2 = new GridData(); 
				grid2.horizontalAlignment = GridData.FILL; 
				labelUserText.setLayoutData(grid2); 
				controlli.add(labelUserText); 
/*
			    Label labelHidden = new Label(compositeFollowersUser, SWT.NONE);
				labelHidden.setText("");
				labelHidden.setVisible(false);
				controlli.add(labelHidden);
				*/
				Label labelNext = new Label(compositeFollowersUser, SWT.None); 
				labelNext.setImage(getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/Toolbar/Next.png")));
				grid2 = new GridData(); 
				grid2.grabExcessHorizontalSpace = true; 
				grid2.horizontalAlignment = GridData.END; 
				labelNext.setLayoutData(grid2); 
				controlli.add(labelNext); 
				
				compositeFollowersUser.addListener(SWT.MouseDown, azioni); 
				
				compositeFollowersUser.setData("ID_action", "User_selected"); 
				compositeFollowersUser.setData("User_type", "Followers"); 
				compositeFollowersUser.setData("User_data",followers[i]); 
			}
		}
	
		Label labelHidden = new Label(panel2, SWT.NONE); 
		labelHidden.setText("Hidden:"); 
		labelHidden.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 14, SWT.BOLD )); 
		grid.horizontalSpan = 3;  
		controlli.add(labelHidden);	
		
	WUser[]	hiddenUsers = Controller.getProxy().GetHiddenUsers(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword());
	
	if(hiddenUsers.length == 0)
	{
		Label labelHiddenUsersText = new Label(panel2, SWT.WRAP); 
		labelHiddenUsersText.setText("You have hidden no one."); 
		grid = new GridData(); 
		grid.horizontalSpan = 3; 
		labelHiddenUsersText.setLayoutData(grid);
		controlli.add(labelHiddenUsersText);
		
	}
	else
	{
		
		GridData grid2; 
		for(int i=0;i< hiddenUsers.length;i++)
		{
			
			Composite compositeHiddenUser = new Composite(panel2, SWT.BORDER); 
			compositeHiddenUser.setLayout(new GridLayout(4, false));
			grid2 = new GridData(); 
			grid2.grabExcessHorizontalSpace = true; 
			grid2.horizontalAlignment = GridData.FILL; 
			compositeHiddenUser.setLayoutData(grid2); 
			
			
			compositeHiddenUser.addListener(SWT.MouseMove, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					// TODO Auto-generated method stub
					System.out.println("Mouse move attivato"); 
					Composite cmp = (Composite)  event.widget; 
					cmp.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GREEN)); 
					cmp.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE)); 
					cmp.redraw(); 
					cmp.layout(); 
					Controller.getWindow().redraw(cmp.getLocation().x, cmp.getLocation().y, cmp.getSize().x, cmp.getSize().y, true); 
					Controller.getWindow().layout(); 
					
				}
			});
			
			compositeHiddenUser.addListener(SWT.MouseExit, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					// TODO Auto-generated method stub
					System.out.println("Mouse exit attivato");
					Composite cmp = (Composite)  event.widget; 
					cmp.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND)); 
					cmp.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE)); 
					cmp.redraw(); 
					cmp.layout(); 
					Controller.getWindow().redraw(cmp.getLocation().x, cmp.getLocation().y, cmp.getSize().x, cmp.getSize().y, true); 
					Controller.getWindow().layout(); 
				}
			});
			controlli.add(compositeHiddenUser); 
			
			Label labelUserImage = new Label(compositeHiddenUser, SWT.None); 
			try {
				labelUserImage.setImage(resize(getImageStream(new URL(hiddenUsers[i].Avatar).openStream()),80,80));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				imageAvatar = getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png"));
				labelUserImage.setImage(resize(imageAvatar,80,80)); 
				imageAvatar.dispose(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				imageAvatar = getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png"));
				labelUserImage.setImage(resize(imageAvatar,80,80)); 
				imageAvatar.dispose();  
			} 
			controlli.add(labelUserImage); 
			
			Label labelUserText = new Label(compositeHiddenUser, SWT.None); 
			labelUserText.setText(hiddenUsers[i].Username); 
			grid2 = new GridData(); 
			grid2.horizontalAlignment = GridData.FILL; 
			labelUserText.setLayoutData(grid2); 
			controlli.add(labelUserText); 
/*
		    Label labelHidden = new Label(compositeFollowersUser, SWT.NONE);
			labelHidden.setText("");
			labelHidden.setVisible(false);
			controlli.add(labelHidden);
			*/
			Label labelNext = new Label(compositeHiddenUser, SWT.None); 
			labelNext.setImage(getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/Toolbar/Next.png")));
			grid2 = new GridData(); 
			grid2.grabExcessHorizontalSpace = true; 
			grid2.horizontalAlignment = GridData.END; 
			labelNext.setLayoutData(grid2); 
			controlli.add(labelNext); 
			
			compositeHiddenUser.addListener(SWT.MouseDown, azioni); 
			
			compositeHiddenUser.setData("ID_action", "User_selected"); 
			compositeHiddenUser.setData("User_type", "Hidden"); 
			compositeHiddenUser.setData("User_data",hiddenUsers[i]); 
			
		}
	}
	
	Controller.setWindowName("People"); 
	int totalLink = (suggestion.length + following.length + followers.length + hiddenUsers.length);  
	if( totalLink > 5 )
	{
		Controller.setScrollHeight(Controller.getWindowHeight() + (totalLink * 70)); 
	    ((ScrolledComposite)	Controller.getWindow().getParent()).setMinSize(Controller.getWindowWidth(), Controller.getScrollHeight());
	}
	else
	{
		Controller.setScrollHeight(Controller.getWindowHeight());
		((ScrolledComposite)	Controller.getWindow().getParent()).setMinSize(Controller.getWindowWidth(), Controller.getScrollHeight());
	}
	
	panel2.redraw(); 
	
	
	
	}

	@Override
	public void dispose(Composite panel) {
		// TODO Auto-generated method stub
		
		for(int i=0; i < controlli.size();i++)
		{
		 controlli.get(i).dispose(); 
			
		}
		panel.setLayout(null); 
		
	}

	@Override
	public HashMap<String, Object> getData() {
		// TODO Auto-generated method stub
		return null;
	}


	

}
