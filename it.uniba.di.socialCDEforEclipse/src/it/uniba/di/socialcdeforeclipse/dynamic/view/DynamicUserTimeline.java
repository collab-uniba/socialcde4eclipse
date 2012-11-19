package it.uniba.di.socialcdeforeclipse.dynamic.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.junit.Test.None;

import it.uniba.di.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.shared.library.WPost;
import it.uniba.di.socialcdeforeclipse.shared.library.WUser;
import it.uniba.di.socialcdeforeclipse.views.Panel;

public class DynamicUserTimeline implements Panel {


	private ArrayList<Control> controlli;
	private Label labelAvatar; 
	private Label labelHidden; 
	private Label labelFollow; 
	private Label labelSkills; 
	private Label labelHide; 
	private Label username;
	private Composite postComposite; 
	private Label labelPost; 
	private Label verticalSeparator; 
	private Label labelFollowing; 
	private Label labelFollowers; 
	private Composite buttonComposite; 
	private Composite userPostMaster; 
	private Composite otherPostWarning; 
	private Listener azioni;
	private WUser user; 
	private String userType; 
	private Link otherPostAvailable; 
	 
	private static long firstPostId = 0; 
	private static long lastPostId = 0; 
	
	
	private final InputStream PATH_SKILLS = this.getClass().getClassLoader().getResourceAsStream("images/skills.png");
	private final InputStream PATH_FOLLOW = this.getClass().getClassLoader().getResourceAsStream("images/follow.png");
	private final InputStream PATH_UNFOLLOW = this.getClass().getClassLoader().getResourceAsStream("images/unfollow.png");
	private final InputStream PATH_HIDE = this.getClass().getClassLoader().getResourceAsStream("images/hide.png");
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
	
	
	

	public Listener getAzioni() {
		return azioni;
	}

	public void setAzioni(Listener azioni) {
		this.azioni = azioni;
	}

	public InputStream getPATH_UNFOLLOW() {
		return PATH_UNFOLLOW;
	}

	public InputStream getPATH_FOLLOW() {
		return PATH_FOLLOW;
	}

	public Label getLabelFollow() {
		return labelFollow;
	}

	public void setLabelFollow(Label labelFollow) {
		this.labelFollow = labelFollow;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public WUser getUser() {
		return user;
	}

	public void setUser(WUser user) {
		this.user = user;
	}

	

	@Override
	public void inizialize(final Composite panel) {
		// TODO Auto-generated method stub
		GridData gridData; 
		WUser userSelected =  Controller.getProxy().GetColleagueProfile(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), ((WUser) Controller.temporaryInformation.get("User_selected")).Id) ; 
		 azioni = new ActionGeneral();
		controlli = new ArrayList<Control>();
		
		GridLayout layout = new GridLayout(4, false);
		panel.setLayout(layout);
		 
		Composite compositeBack = new Composite(panel, SWT.None);
		compositeBack.setLayout(new GridLayout(1, false)); 
		compositeBack.setBackground(new Color(Display.getCurrent(), 192,192,186));
		gridData = new GridData(); 
		gridData.horizontalSpan = 4; 
		compositeBack.setLayoutData(gridData); 

		
		
		Label labelBack = new Label(compositeBack, SWT.None); 
		labelBack.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND)); 
		labelBack.setImage(get_ImageStream(this.getClass().getClassLoader().getResourceAsStream("images/Toolbar/Back.png"))); 
		labelBack.setData("ID_action", "labelBack");
		
		labelAvatar = new Label(panel,SWT.NONE); 
		gridData = new GridData();
		gridData.verticalSpan = 2; 
		labelAvatar.setLayoutData(gridData); 
		labelAvatar.setData("ID_action", "labelAvatar");
		 
		
			try {
				labelAvatar.setImage(get_ImageStream(new URL(user.Avatar).openStream()));
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
		gridData = new GridData(); 
		gridData.horizontalIndent = 10; 
		username.setLayoutData(gridData); 
		
		labelHidden = new Label(panel, SWT.None); 
		labelHidden.setVisible(false); 
		gridData = new GridData(); 
		gridData.horizontalIndent = 10; 
		labelHidden.setLayoutData(gridData); 
		
		
		buttonComposite = new Composite(panel, SWT.None); 
		buttonComposite.setLayout(new GridLayout(3, false)); 
		gridData = new GridData(); 
		gridData.horizontalIndent = 50; 
		buttonComposite.setLayoutData(gridData); 
		
		
		
	
		if(userType == "Suggested" )
		{
			labelFollow = new Label(buttonComposite,SWT.RIGHT); 
			labelFollow.setImage(get_ImageStream(PATH_FOLLOW));
			labelFollow.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND)); 
			labelFollow.setData("ID_action", "labelFollow");
			labelFollow.setToolTipText("Follow this user in SocialCDE for Eclipse"); 
			labelFollow.setData("user", getUser()); 
			
			labelSkills = new Label(buttonComposite,SWT.RIGHT); 
			labelSkills.setImage(get_ImageStream(PATH_SKILLS));
			labelSkills.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND)); 
			labelSkills.setData("ID_action", "labelSkills");
			labelSkills.setData("user", getUser()); 
			labelSkills.setToolTipText("View his/her skills");
			
			labelHide = new Label(buttonComposite,SWT.RIGHT); 
			labelHide.setImage(get_ImageStream(PATH_HIDE));
			labelHide.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND)); 
			labelHide.setData("ID_action", "labelHide");
			labelHide.setData("user", getUser()); 
			labelHide.setToolTipText("Hide this user from SocialCDE for Eclipse"); 

			labelFollow.addListener(SWT.MouseDown, azioni); 
			labelHide.addListener(SWT.MouseDown, azioni); 
			labelSkills.addListener(SWT.MouseDown, azioni); 
		}
		else if(userType == "Following")
		{
			labelFollow = new Label(buttonComposite,SWT.RIGHT); 
			labelFollow.setImage(get_ImageStream(PATH_UNFOLLOW));
			labelFollow.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND)); 
			labelFollow.setData("ID_action", "labelUnfollow");
			labelFollow.setData("user", getUser()); 
			
			labelSkills = new Label(buttonComposite,SWT.RIGHT); 
			labelSkills.setImage(get_ImageStream(PATH_SKILLS));
			labelSkills.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND)); 
			labelSkills.setData("ID_action", "labelSkills");
			labelSkills.setData("user", getUser()); 
			
			labelHide = new Label(buttonComposite,SWT.RIGHT); 
			labelHide.setImage(get_ImageStream(PATH_HIDE));
			labelHide.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND)); 
			labelHide.setData("ID_action", "labelHide");
			labelHide.setData("user", getUser()); 

			labelFollow.addListener(SWT.MouseDown, azioni); 
			labelHide.addListener(SWT.MouseDown, azioni); 
			labelSkills.addListener(SWT.MouseDown, azioni); 
		}
		else if(userType == "Followers" || userType == "Hidden")
		{
			labelFollow = new Label(buttonComposite,SWT.RIGHT); 
			labelFollow.setImage(get_ImageStream(PATH_FOLLOW));
			labelFollow.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND)); 
			labelFollow.setData("ID_action", "labelFollow");
			labelFollow.setData("user", getUser()); 
			
			labelSkills = new Label(buttonComposite,SWT.RIGHT); 
			labelSkills.setImage(get_ImageStream(PATH_SKILLS));
			labelSkills.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND)); 
			labelSkills.setData("ID_action", "labelSkills");
			labelSkills.setData("user", getUser()); 
			
			labelHide = new Label(buttonComposite,SWT.RIGHT); 
			labelHide.setImage(get_ImageStream(PATH_HIDE));
			labelHide.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND)); 
			labelHide.setData("ID_action", "labelHide");
			labelHide.setData("user", getUser()); 

			labelFollow.addListener(SWT.MouseDown, azioni); 
			labelHide.addListener(SWT.MouseDown, azioni); 
			labelSkills.addListener(SWT.MouseDown, azioni); 
		}
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
		
		WPost[] posts = Controller.getProxy().GetUserTimeline(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), user.Username); 
		
		if(posts == null)
		{
			posts = new WPost[0]; 
		}
		
		System.out.println("Post ottenuti " + posts.length); 

		userPostMaster = new Composite(panel, SWT.None); 
		userPostMaster.setLayout(new GridLayout(1,false)); 
		gridData = new GridData(); 
		gridData.horizontalSpan = 4;
		gridData.grabExcessHorizontalSpace = true; 
		gridData.horizontalAlignment = GridData.FILL; 
		userPostMaster.setLayoutData(gridData); 
		
		if(posts.length > 5)
		{
			Controller.setScrollHeight(Controller.getWindowHeight() + (200 * posts.length)  );
			((ScrolledComposite)	Controller.getWindow().getParent()).setMinSize(Controller.getWindowWidth()-50, Controller.getScrollHeight());
		}
		else
		{
			Controller.setScrollHeight(Controller.getWindowHeight()); 
			((ScrolledComposite)	Controller.getWindow().getParent()).setMinSize(Controller.getWindowWidth()-50, Controller.getScrollHeight());
		}
		
		
		
		firstPostId = 0; 
		
		
		for(int i=0;i< posts.length; i++)
		{
			
			Composite userPostComposite = new Composite(userPostMaster, SWT.BORDER);
			userPostComposite.setLayout(new GridLayout(2, false)); 
			gridData = new GridData(); 
			gridData.grabExcessHorizontalSpace = true; 
			gridData.horizontalAlignment = GridData.FILL; 
			userPostComposite.setLayoutData(gridData); 
			
			Label labelUserAvatar = new Label(userPostComposite,SWT.NONE); 
			gridData = new GridData();
			gridData.verticalSpan = 2; 
			labelUserAvatar.setLayoutData(gridData); 
			labelUserAvatar.setData("ID_action", "labelAvatar");
			 
			
				try {
					labelUserAvatar.setImage(get_ImageStream(new URL(posts[i].getUser().Avatar).openStream()));
					labelUserAvatar.setImage(resize(labelUserAvatar.getImage(), 75, 75)); 
					 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//System.out.println("Eccezione lanciata"); 
					labelUserAvatar.setImage(resize(get_ImageStream(this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png")),75,75));
					
					//e.printStackTrace();
				} 
				catch (NullPointerException e) {
					// TODO: handle exception
					labelUserAvatar.setImage(get_ImageStream(this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png"))); 
					labelUserAvatar.setImage(resize(labelUserAvatar.getImage(), 75, 75));
				}
				
			Label username = new Label(userPostComposite, SWT.None); 
			username.setText(posts[i].getUser().Username); 
			username.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 15, SWT.BOLD ));  
			gridData = new GridData(); 
			gridData.grabExcessHorizontalSpace = false; 
			gridData.horizontalAlignment = GridData.FILL;
			username.setLayoutData(gridData); 
			
			Label message = new Label(userPostComposite, SWT.None | SWT.WRAP); 
			message.setText(posts[i].getMessage()); 
			gridData = new GridData(); 
			gridData.grabExcessHorizontalSpace = true; 
			gridData.horizontalAlignment = GridData.FILL; 
			gridData.horizontalSpan = 2; 
			gridData.widthHint = 100; 
			message.setLayoutData(gridData); 
			
			Calendar nowDate = Calendar.getInstance(); 
			Calendar dateSelected = posts[i].getCreateAt(); 
			long millisDiff = nowDate.getTime().getTime() - dateSelected.getTime().getTime();
			
			int seconds = (int) (millisDiff / 1000 % 60);
			int minutes = (int) (millisDiff / 60000 % 60);
			int hours = (int) (millisDiff / 3600000 % 24);
			int days = (int) (millisDiff / 86400000);
			
			Label messageDate = new Label(userPostComposite, SWT.None); 
			
			if(days > 1 && days < 30)
			{
				messageDate.setText("About " + days + " days ago from " + posts[i].getService().getName());
			}
			else if(days > 30)
			{
				messageDate.setText("More than one month ago from " + posts[i].getService().getName());
			}
			else if(days == 1)
			{
				messageDate.setText("About " + days + " day ago from " + posts[i].getService().getName());
			}
			else
			{
				if( hours > 1)
				{
					messageDate.setText("About " + hours + " hours ago from " + posts[i].getService().getName());
				}
				else if(hours == 1)
				{
					messageDate.setText("About " + hours + " hour ago from " + posts[i].getService().getName());
				}
				else
				{

					if( minutes > 1)
					{
						messageDate.setText("About " + minutes + " minutes ago from " + posts[i].getService().getName());
					}
					else if(minutes == 1)
					{
						messageDate.setText("About " + minutes + " minute ago from " + posts[i].getService().getName());
					}
					else
					{

						if( seconds > 1)
						{
							messageDate.setText("About " + seconds + " seconds ago from " + posts[i].getService().getName());
						}
						else if(seconds == 1)
						{
							messageDate.setText("About " + seconds + " second ago from " + posts[i].getService().getName());
						}
						else
						{
							messageDate.setText("Few seconds ago from " + posts[i].getService().getName());
						}
					}
				}
			}
			 
			messageDate.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 8, SWT.ITALIC ));
			gridData = new GridData(); 
			gridData.grabExcessHorizontalSpace = true; 
			gridData.horizontalAlignment = GridData.END; 
			gridData.horizontalSpan = 2; 
			messageDate.setLayoutData(gridData); 
			
			
			lastPostId = posts[i].Id;
		}
		
		otherPostWarning = new Composite(panel, SWT.None); 
		otherPostWarning.setLayout(new GridLayout(1,false)); 
		gridData = new GridData(); 
		gridData.horizontalSpan = 4;
		gridData.grabExcessHorizontalSpace = true; 
		gridData.horizontalAlignment = GridData.FILL; 
		otherPostWarning.setLayoutData(gridData);
		
		WPost[] newPost = Controller.getProxy().GetUserTimeline(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword(), userSelected.Username,lastPostId,0);
		
		if(newPost == null)
		{
			newPost = new WPost[0]; 
		}
	
		System.out.println("Nuovi post disponibili " + newPost.length);
		
		if(newPost.length > 0)
		{
			otherPostAvailable = new Link(otherPostWarning, SWT.NONE); 
			otherPostAvailable.setCursor( new Cursor(panel.getDisplay(), SWT.CURSOR_HAND));
			otherPostAvailable.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.UNDERLINE_LINK));
			otherPostAvailable.setText("<a>Click to view older posts</a>"); 
			gridData = new GridData();
			gridData.grabExcessHorizontalSpace = true; 
			gridData.horizontalAlignment = GridData.CENTER;
			otherPostAvailable.setLayoutData(gridData); 
			
			otherPostAvailable.addListener(SWT.Selection, azioni); 
			otherPostAvailable.setData("ID_action", "otherPostAvailable");
			
			
			
		}
		else
		{
			Label noPostAvailable = new Label(otherPostWarning,SWT.NONE); 
			noPostAvailable.setText("There are no older post available.");
			noPostAvailable.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.None));
			gridData = new GridData();
			gridData.grabExcessHorizontalSpace = true; 
			gridData.horizontalAlignment = GridData.CENTER;
			noPostAvailable.setLayoutData(gridData); 
		}
		
		labelBack.addListener(SWT.MouseDown,azioni); 
		
		Controller.setWindowName("userTimeline"); 
		
		panel.layout(); 
		
		System.out.println("Lettura caso 3 terminata"); 

	}

	@Override
	public void dispose(Composite panel) {
		// TODO Auto-generated method stub
		for(int i=0; i < controlli.size();i++)
		{
		 controlli.get(i).dispose(); 
			
		}
		 
	}

	@Override
	public HashMap<String, Object> getData() {
		// TODO Auto-generated method stub
		HashMap<String, Object> uiData = new HashMap<String, Object>();
		uiData.put("userType", userType); 
		uiData.put("labelFollow",labelFollow); 
		uiData.put("labelSkills", labelSkills); 
		uiData.put("labelHide", labelHide); 
		uiData.put("userPostMaster", userPostMaster); 
		uiData.put("otherPostWarning", otherPostWarning); 
		uiData.put("userSelected", user);
		uiData.put("action",azioni); 
		uiData.put("otherPostAvailable", otherPostAvailable);
		uiData.put("firstPostId", firstPostId);
		uiData.put("lastPostId", lastPostId);
		
		if(userType == "Suggested" || userType == "Followers" || userType == "Hidden")
		{
			uiData.put("imageUnFollow", false); 
			 uiData.put("imageFollow", true);
		}
		else if(userType == "Following")
		{
			uiData.put("imageUnFollow", false); 
			 uiData.put("imageFollow", true);
		}
			
		
		return uiData;
	}

	

}
