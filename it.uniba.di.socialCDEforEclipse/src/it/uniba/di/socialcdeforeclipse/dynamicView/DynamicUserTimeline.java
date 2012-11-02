package it.uniba.di.socialcdeforeclipse.dynamicView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;

import it.uniba.di.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.WUser;
import it.uniba.di.socialcdeforeclipse.views.Panel;

public class DynamicUserTimeline implements Panel {


	private ArrayList<Control> controlli;
	private Label labelAvatar; 
	private Label labelHidden; 
	private Label labelSkills; 
	private Label labelSettings; 
	private Label username;
	private Composite postComposite; 
	private Label labelPost; 
	private Label verticalSeparator; 
	private Label labelFollowing; 
	private Label labelFollowers; 
	private Composite buttonComposite; 
	private int idProfile; 
	
	
	private final InputStream PATH_SKILLS = this.getClass().getClassLoader().getResourceAsStream("images/Toolbar/Skills.png");
	private final InputStream PATH_SETTINGS = this.getClass().getClassLoader().getResourceAsStream("images/Toolbar/Settings.png");
	private final InputStream PATH_DEFAULT_AVATAR = this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png");

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
	
	public Image get_ImageStream(InputStream stream)
	{
		return  new Image(Controller.getWindow().getDisplay(),stream); 
	}
	

	public int getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(int idProfile) {
		this.idProfile = idProfile;
	}

	@Override
	public void inizialize(Composite panel) {
		// TODO Auto-generated method stub
		GridData gridData; 
		WUser userSelected = Controller.getProxy().GetColleagueProfile(Controller.getCurrentUser().Username , Controller.getCurrentUserPassword(), getIdProfile()); 
		
		controlli = new ArrayList<Control>();
		Listener azioni = new ActionGeneral();
		GridLayout layout = new GridLayout(4, true);
		panel.setLayout(layout);
		 
		Label labelBack = new Label(panel, SWT.None); 
		labelBack.setImage(get_ImageStream(this.getClass().getClassLoader().getResourceAsStream("images/Toolbar/Back.png"))); 
		gridData = new GridData(); 
		gridData.horizontalSpan = 3; 
		labelBack.setLayoutData(labelBack); 
		
		labelAvatar = new Label(panel,SWT.NONE); 
		gridData = new GridData();
		gridData.verticalSpan = 2; 
		labelAvatar.setLayoutData(gridData); 
		labelAvatar.setData("ID_action", "labelAvatar");
		 
		
			try {
				labelAvatar.setImage(get_ImageStream(new URL(Controller.getCurrentUser().Avatar).openStream()));
				labelAvatar.setImage(resize(labelAvatar.getImage(), 75, 75)); 
				 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//System.out.println("Eccezione lanciata"); 
				labelAvatar.setImage(get_ImageStream(PATH_DEFAULT_AVATAR));
				labelAvatar.setImage(resize(labelAvatar.getImage(), 75, 75));
				//e.printStackTrace();
			} 
			catch (NullPointerException e) {
				// TODO: handle exception
				labelAvatar.setImage(get_ImageStream(PATH_DEFAULT_AVATAR)); 
				labelAvatar.setImage(resize(labelAvatar.getImage(), 75, 75));
			}
		
		controlli.add(labelAvatar); 
		
		username = new Label(panel, SWT.NONE); 
		username.setText(userSelected.Username); 
		username.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 15, SWT.NONE)); 
		
		labelHidden = new Label(panel,SWT.NONE); 
		labelHidden.setVisible(false); 
		
		buttonComposite = new Composite(panel, SWT.NONE); 
		Layout rowLayout = new RowLayout(); 
		buttonComposite.setLayout(rowLayout); 
		
		labelSkills = new Label(buttonComposite,SWT.RIGHT); 
		labelSkills.setImage(get_ImageStream(PATH_SKILLS));
		labelSkills.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND)); 
		labelSkills.setData("ID_action", "labelSkills");
		
		labelSettings = new Label(buttonComposite,SWT.RIGHT); 
		labelSettings.setImage(get_ImageStream(PATH_SETTINGS));
		labelSettings.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND)); 
		labelSettings.setData("ID_action", "labelSettings");
		
		postComposite = new Composite(panel, SWT.NONE); 
		
		GridLayout gridlayout = new GridLayout(5,false); 
		postComposite.setLayout(gridlayout); 
		gridData = new GridData(); 
		gridData.horizontalSpan = 3; 
		postComposite.setLayoutData(gridData); 
		
		
		
		labelPost = new Label(postComposite,SWT.WRAP | SWT.CENTER); 
		labelPost.setText("Posts\r\n" + userSelected.getStatuses()); 
		labelPost.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.BOLD ));
		labelPost.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true)); 
		
		verticalSeparator = new Label(postComposite, SWT.SEPARATOR | SWT.VERTICAL); 
		gridData = new GridData(); 
		gridData.heightHint = 25; 
		verticalSeparator.setLayoutData(gridData);
		controlli.add(verticalSeparator); 
		
		labelFollowing = new Label(postComposite,SWT.WRAP | SWT.CENTER); 
		labelFollowing.setText("Following\r\n" + userSelected.getFollowings()); 
		labelFollowing.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.BOLD ));
		labelFollowing.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true)); 
		
		verticalSeparator = new Label(postComposite, SWT.SEPARATOR | SWT.VERTICAL);
		gridData = new GridData(); 
		gridData.heightHint = 25; 
		verticalSeparator.setLayoutData(gridData);
		
		labelFollowers = new Label(postComposite, SWT.WRAP | SWT.CENTER); 
		labelFollowers.setText("Followers\r\n" + userSelected.getFollowers()); 
		labelFollowers.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.BOLD ));
		labelFollowers.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));

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
	public HashMap<String, String> getInput() {
		// TODO Auto-generated method stub
		return null;
	}

}
