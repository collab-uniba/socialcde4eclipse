package it.uniba.di.collab.socialcdeforeclipse.dynamic.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;


import it.uniba.di.collab.socialcdeforeclipse.action.ActionDynamicUserTimeline;
import it.uniba.di.collab.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.collab.socialcdeforeclipse.action.ActionHomeTimeline;
import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.object.Panel;
import it.uniba.di.collab.socialcdeforeclipse.object.ProgressBarWindow;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.WPost;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.WUser;

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
	private Label labelDownloadPost;
	private ProgressBar pbar;
	private WPost[] posts;
	private ScrolledComposite superUserPostMaster;
	private static int totalField; 
	private static ProgressBarWindow pbW; 
	
	private final InputStream PATH_SKILLS = this.getClass().getClassLoader()
			.getResourceAsStream("images/skills.png");
	private final InputStream PATH_FOLLOW = this.getClass().getClassLoader()
			.getResourceAsStream("images/Follow.png");
	private final InputStream PATH_UNFOLLOW = this.getClass().getClassLoader()
			.getResourceAsStream("images/Unfollow.png");
	private final InputStream PATH_HIDE = this.getClass().getClassLoader()
			.getResourceAsStream("images/Hide.png");
	private final InputStream PATH_DEFAULT_AVATAR = this.getClass()
			.getClassLoader().getResourceAsStream("images/DefaultAvatar.png");

	private Image getUserImage(String link)
	{
		try {
			return resize(get_ImageStream(new URL(link).openStream()),48,48);
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			return resize(get_ImageStream(this.getClass()
					.getClassLoader().getResourceAsStream("images/DefaultAvatar.png")),48,48);
			
			
		} catch (NullPointerException e) {
			// TODO: handle exception
			return resize(get_ImageStream(this.getClass()
					.getClassLoader().getResourceAsStream("images/DefaultAvatar.png")),48,48);
		}
	}

	
	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, image.getBounds().width,
				image.getBounds().height, 0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
	}

	public Image get_ImageStream(InputStream stream) {
		return new Image(Controller.getWindow().getDisplay(), stream);
	}
	
	private String findLink(String message)
	{
		String[] subsequences = message.split(" "); 
		String result = ""; 
		for(int i=0;i<subsequences.length; i++)
		{
			if(result.equals(""))
			{
				if(subsequences[i].contains("http"))
				{
					result = "<a href=\" " + subsequences[i] + "\" > " + subsequences[i] + "</a> "; 
				}
				else
				{
					result = subsequences[i] + " "; 
				}
			}
			else
			{
				if(subsequences[i].contains("http"))
				{
					result += "<a href=\" " + subsequences[i] + "\" > " + subsequences[i] + "</a> "; 
				}
				else
				{
					result += subsequences[i] + " "; 
				}
			}
		}
		
		return result; 
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
		 
	
		if(Controller.temporaryInformation.containsKey("LinuxProgressBar"))
		{
			pbW =  (ProgressBarWindow) Controller.temporaryInformation.get("LinuxProgressBar");  
		}
		
		WUser userSelected = Controller.getProxy()
				.GetColleagueProfile(
						Controller.getCurrentUser().Username,
						Controller.getCurrentUserPassword(),
						((WUser) Controller.temporaryInformation
								.get("User_selected")).Id);
		azioni = new ActionGeneral();
		controlli = new ArrayList<Control>();

		GridLayout layout = new GridLayout(5, false);
		panel.setLayout(layout);

		Composite compositeBack = new Composite(panel, SWT.None);
		compositeBack.setLayout(new GridLayout(1, false));
		compositeBack.setBackground(new Color(Display.getCurrent(), 192, 192,
				186));
		gridData = new GridData();
		gridData.verticalSpan = 2;
		compositeBack.setLayoutData(gridData);

		Label labelBack = new Label(compositeBack, SWT.None);
		labelBack.setCursor(new Cursor(panel.getDisplay(), SWT.CURSOR_HAND));
		labelBack.setImage(get_ImageStream(this.getClass().getClassLoader()
				.getResourceAsStream("images/Toolbar/Back.png")));
		labelBack.setData("ID_action", "labelBack");

		labelAvatar = new Label(panel, SWT.NONE);
		gridData = new GridData();
		gridData.verticalSpan = 2;
		labelAvatar.setLayoutData(gridData);
		labelAvatar.setData("ID_action", "labelAvatar");
		
		if(Controller.getUsersAvatar().get(user.Username) == null)
		{
			Controller.getUsersAvatar().put(user.Username, getUserImage(user.Avatar));
		}
		
		labelAvatar.setImage(resize(new Image(Display.getCurrent(),Controller.getUsersAvatar().get(user.Username),SWT.IMAGE_COPY), 75, 75)); 
		
		

		controlli.add(labelAvatar);

		username = new Label(panel, SWT.NONE);
		username.setText(userSelected.Username);
		username.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 15, SWT.BOLD));
		gridData = new GridData();
		gridData.horizontalIndent = 10;
		username.setLayoutData(gridData);

		labelHidden = new Label(panel, SWT.None);
		labelHidden.setVisible(false);
		gridData = new GridData();
		gridData.horizontalIndent = 10;
		labelHidden.setLayoutData(gridData);

		
		postComposite = new Composite(panel, SWT.NONE);

		GridLayout gridlayout = new GridLayout(5, false);
		postComposite.setLayout(gridlayout);
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		postComposite.setLayoutData(gridData);

		labelPost = new Label(postComposite, SWT.WRAP | SWT.CENTER);
		labelPost.setText("Posts\r\n" + userSelected.getStatuses());
		labelPost.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 10, SWT.BOLD));
		labelPost
				.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));

		verticalSeparator = new Label(postComposite, SWT.SEPARATOR
				| SWT.VERTICAL);
		gridData = new GridData();
		gridData.heightHint = 25;
		verticalSeparator.setLayoutData(gridData);
		controlli.add(verticalSeparator);

		if(Controller.OSisUnix())
		{
			pbW.updateProgressBar(); 
		}
		
		labelFollowing = new Label(postComposite, SWT.WRAP | SWT.CENTER);
		labelFollowing.setText("Following\r\n" + userSelected.getFollowings());
		labelFollowing.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 10, SWT.BOLD));
		labelFollowing.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true,
				true));

		verticalSeparator = new Label(postComposite, SWT.SEPARATOR
				| SWT.VERTICAL);
		gridData = new GridData();
		gridData.heightHint = 25;
		verticalSeparator.setLayoutData(gridData);

		labelFollowers = new Label(postComposite, SWT.WRAP | SWT.CENTER);
		labelFollowers.setText("Followers\r\n" + userSelected.getFollowers());
		labelFollowers.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 10, SWT.BOLD));
		labelFollowers.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true,
				true));
		
		buttonComposite = new Composite(panel, SWT.None);
		buttonComposite.setLayout(new GridLayout(3, false));
		gridData = new GridData();
		gridData.horizontalSpan = 4; 
		gridData.horizontalAlignment = GridData.CENTER; 
		buttonComposite.setLayoutData(gridData);
		
		if (userType == "Suggested") {
			labelFollow = new Label(buttonComposite, SWT.RIGHT);
			labelFollow.setImage(get_ImageStream(PATH_FOLLOW));
			labelFollow.setCursor(new Cursor(panel.getDisplay(),
					SWT.CURSOR_HAND));
			labelFollow.setData("ID_action", "labelFollow");
			labelFollow
					.setToolTipText("Follow this user in SocialCDE for Eclipse");
			labelFollow.setData("user", getUser());

			labelSkills = new Label(buttonComposite, SWT.RIGHT);
			labelSkills.setImage(get_ImageStream(PATH_SKILLS));
			labelSkills.setCursor(new Cursor(panel.getDisplay(),
					SWT.CURSOR_HAND));
			labelSkills.setData("ID_action", "labelSkills");
			labelSkills.setData("user", getUser());
			labelSkills.setToolTipText("View his/her skills");

			labelHide = new Label(buttonComposite, SWT.RIGHT);
			labelHide.setImage(get_ImageStream(PATH_HIDE));
			labelHide
					.setCursor(new Cursor(panel.getDisplay(), SWT.CURSOR_HAND));
			labelHide.setData("ID_action", "labelHide");
			labelHide.setData("user", getUser());
			labelHide
					.setToolTipText("Hide this user from SocialCDE for Eclipse");

			labelFollow.addListener(SWT.MouseDown, azioni);
			labelHide.addListener(SWT.MouseDown, azioni);
			labelSkills.addListener(SWT.MouseDown, azioni);
		} else if (userType == "Following") {
			labelFollow = new Label(buttonComposite, SWT.RIGHT);
			labelFollow.setImage(get_ImageStream(PATH_UNFOLLOW));
			labelFollow.setCursor(new Cursor(panel.getDisplay(),
					SWT.CURSOR_HAND));
			labelFollow.setData("ID_action", "labelUnfollow");
			labelFollow.setData("user", getUser());

			labelSkills = new Label(buttonComposite, SWT.RIGHT);
			labelSkills.setImage(get_ImageStream(PATH_SKILLS));
			labelSkills.setCursor(new Cursor(panel.getDisplay(),
					SWT.CURSOR_HAND));
			labelSkills.setData("ID_action", "labelSkills");
			labelSkills.setData("user", getUser());

			labelHide = new Label(buttonComposite, SWT.RIGHT);
			labelHide.setImage(get_ImageStream(PATH_HIDE));
			labelHide
					.setCursor(new Cursor(panel.getDisplay(), SWT.CURSOR_HAND));
			labelHide.setData("ID_action", "labelHide");
			labelHide.setData("user", getUser());

			labelFollow.addListener(SWT.MouseDown, azioni);
			labelHide.addListener(SWT.MouseDown, azioni);
			labelSkills.addListener(SWT.MouseDown, azioni);
		} else if (userType == "Followers" || userType == "Hidden") {
			labelFollow = new Label(buttonComposite, SWT.RIGHT);
			labelFollow.setImage(get_ImageStream(PATH_FOLLOW));
			labelFollow.setCursor(new Cursor(panel.getDisplay(),
					SWT.CURSOR_HAND));
			labelFollow.setData("ID_action", "labelFollow");
			labelFollow.setData("user", getUser());

			labelSkills = new Label(buttonComposite, SWT.RIGHT);
			labelSkills.setImage(get_ImageStream(PATH_SKILLS));
			labelSkills.setCursor(new Cursor(panel.getDisplay(),
					SWT.CURSOR_HAND));
			labelSkills.setData("ID_action", "labelSkills");
			labelSkills.setData("user", getUser());

			labelHide = new Label(buttonComposite, SWT.RIGHT);
			labelHide.setImage(get_ImageStream(PATH_HIDE));
			labelHide
					.setCursor(new Cursor(panel.getDisplay(), SWT.CURSOR_HAND));
			labelHide.setData("ID_action", "labelHide");
			labelHide.setData("user", getUser());

			labelFollow.addListener(SWT.MouseDown, azioni);
			labelHide.addListener(SWT.MouseDown, azioni);
			labelSkills.addListener(SWT.MouseDown, azioni);
		}
		
		if(Controller.OSisUnix())
		{
			pbW.updateProgressBar(); 
		}

		superUserPostMaster = new ScrolledComposite(panel, SWT.V_SCROLL);
		superUserPostMaster.setLayout(new GridLayout(1, false));
		gridData = new GridData();
		gridData.widthHint = Controller.getWindowWidth() - 27;
		gridData.heightHint = Controller.getWindowHeight() - 240;
		gridData.horizontalSpan = 5;
		superUserPostMaster.setLayoutData(gridData);
		controlli.add(superUserPostMaster);

		userPostMaster = new Composite(superUserPostMaster, SWT.None);

		superUserPostMaster.setContent(userPostMaster);
		superUserPostMaster.setExpandVertical(true);
		superUserPostMaster.setExpandHorizontal(true);
		superUserPostMaster.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));

		controlli.add(userPostMaster);


		userPostMaster.setLayout(new GridLayout(1, false));
		gridData = new GridData();
		gridData.widthHint = Controller.getWindowWidth() - 100;
		gridData.heightHint = Controller.getWindowHeight() - 220;
		userPostMaster.setLayoutData(gridData);

		userPostMaster.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));

		posts = Controller.getProxy().GetUserTimeline(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword(), userSelected.Username);

		//System.out.println("numero di post " + posts.length);

		ActionDynamicUserTimeline.setLastId(0);

		for (int i = 0; i < posts.length; i++) {

			final Composite userPostComposite = new Composite(userPostMaster,
					SWT.None);
			final int j = i;

			Display.getCurrent().syncExec(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					if(Controller.OSisUnix())
					{
						pbW.updateProgressBar(); 
					}
					
					userPostComposite.setLayout(new GridLayout(2, false));
					userPostComposite.setBackground(Display.getCurrent()
							.getSystemColor(SWT.COLOR_WHITE));
					userPostComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
					GridData gridData = new GridData();
					gridData.grabExcessHorizontalSpace = true;
					gridData.horizontalAlignment = GridData.FILL;
					userPostComposite.setLayoutData(gridData);

					controlli.add(userPostComposite);

					Label labelUserAvatar = new Label(userPostComposite,
							SWT.NONE);
					gridData = new GridData();
					gridData.verticalSpan = 3;
					labelUserAvatar.setLayoutData(gridData);
					labelUserAvatar.setData("ID_action", "labelAvatar");
					
					if(Controller.getUsersAvatar().get(posts[j].getUser().Username) == null)
					{
						Controller.getUsersAvatar().put(posts[j].getUser().Username, getUserImage(posts[j].getUser().Avatar));
					}
					
					labelUserAvatar.setImage(resize(new Image(Display.getCurrent(),Controller.getUsersAvatar().get(posts[j].getUser().Username),SWT.IMAGE_COPY), 75, 75)); 


				
				

					Label username = new Label(userPostComposite, SWT.None);
					username.setText(posts[j].getUser().Username);
					username.setFont(new Font(Controller.getWindow()
							.getDisplay(), "Calibri", 15, SWT.BOLD));
					username.setForeground(new Color(Display.getCurrent(), 97,
							91, 91));
					gridData = new GridData();
					gridData.grabExcessHorizontalSpace = false;
					gridData.horizontalAlignment = GridData.BEGINNING;
					username.setLayoutData(gridData);

					Link message = new Link(userPostComposite,  SWT.WRAP);
					message.setText( findLink( posts[j].getMessage() ) );
					message.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)); 
					message.addListener(SWT.Selection, new Listener() {
						
						@Override
						public void handleEvent(Event event) {
							// TODO Auto-generated method stub
							try {
								PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(event.text));
							} catch (PartInitException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
						}
					}); 
					gridData = new GridData();
					gridData.widthHint = Controller.getWindowWidth() - 150;
					message.setLayoutData(gridData);
					message.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)); 
					
					Calendar nowDate = Calendar.getInstance();
					Calendar dateSelected = posts[j].getCreateAt();
					long millisDiff = nowDate.getTime().getTime()
							- dateSelected.getTime().getTime();

					int seconds = (int) (millisDiff / 1000 % 60);
					int minutes = (int) (millisDiff / 60000 % 60);
					int hours = (int) (millisDiff / 3600000 % 24);
					int days = (int) (millisDiff / 86400000);

					Label messageDate = new Label(userPostComposite, SWT.None);
					messageDate.setForeground(new Color(Display.getCurrent(),
							140, 140, 140));

					if (days > 1 && days < 30) {
						messageDate.setText("About " + days + " days ago from "
								+ posts[j].getService().getName());
					} else if (days > 30) {
						messageDate.setText("More than one month ago from "
								+ posts[j].getService().getName());
					} else if (days == 1) {
						messageDate.setText("About " + days + " day ago from "
								+ posts[j].getService().getName());
					} else {
						if (hours > 1) {
							messageDate.setText("About " + hours
									+ " hours ago from "
									+ posts[j].getService().getName());
						} else if (hours == 1) {
							messageDate.setText("About " + hours
									+ " hour ago from "
									+ posts[j].getService().getName());
						} else {

							if (minutes > 1) {
								messageDate.setText("About " + minutes
										+ " minutes ago from "
										+ posts[j].getService().getName());
							} else if (minutes == 1) {
								messageDate.setText("About " + minutes
										+ " minute ago from "
										+ posts[j].getService().getName());
							} else {

								if (seconds > 1) {
									messageDate.setText("About " + seconds
											+ " seconds ago from "
											+ posts[j].getService().getName());
								} else if (seconds == 1) {
									messageDate.setText("About " + seconds
											+ " second ago from "
											+ posts[j].getService().getName());
								} else {
									messageDate.setText("Few seconds ago from "
											+ posts[j].getService().getName());
								}
							}
						}
					}

					messageDate.setFont(new Font(Controller.getWindow()
							.getDisplay(), "Calibri", 8, SWT.ITALIC));
					gridData = new GridData();
					gridData.horizontalAlignment = GridData.BEGINNING;
					gridData.widthHint = Controller.getWindowWidth() - 150;
					messageDate.setLayoutData(gridData);

					/*Label labelhidden = new Label(userPostMaster, SWT.None);
					labelhidden.setText("");
					labelhidden.setVisible(false);*/

					Label barSeparator = new Label(userPostComposite,
							SWT.BORDER);
					gridData = new GridData();
					gridData.widthHint = 100;
					gridData.heightHint = 1;
					gridData.horizontalSpan = 2;
					gridData.horizontalAlignment = GridData.CENTER;
					barSeparator.setLayoutData(gridData);
				}
			});

			ActionDynamicUserTimeline.setLastId(posts[i].Id);

		}

		
		otherPostWarning = new Composite(userPostMaster, SWT.None);
		otherPostWarning.setLayout(new GridLayout(1, false));
		otherPostWarning.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		otherPostWarning.setLayoutData(gridData);

		WPost[] newPost = Controller.getProxy().GetUserTimeline(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword(), userSelected.Username,
				ActionDynamicUserTimeline.getLastId(), 0);

		if (newPost == null) {
			newPost = new WPost[0];
		}

		if (newPost.length > 0) {
			otherPostAvailable = new Link(otherPostWarning, SWT.NONE);
			otherPostAvailable.setCursor(new Cursor(panel.getDisplay(),
					SWT.CURSOR_HAND));
			otherPostAvailable.setFont(new Font(Controller.getWindow()
					.getDisplay(), "Calibri", 10, SWT.UNDERLINE_LINK));
			otherPostAvailable.setText("<a>Click to view older posts</a>");
			otherPostAvailable.setBackground(Display.getCurrent()
					.getSystemColor(SWT.COLOR_WHITE));
			gridData = new GridData();
			gridData.grabExcessHorizontalSpace = true;
			gridData.horizontalAlignment = GridData.CENTER;
			otherPostAvailable.setLayoutData(gridData);

			otherPostAvailable.addListener(SWT.Selection, azioni);
			otherPostAvailable.setData("ID_action", "otherPostAvailable");

		} else {
			Label noPostAvailable = new Label(otherPostWarning, SWT.NONE);
			noPostAvailable.setText("There are no older post available.");
			noPostAvailable.setBackground(Display.getCurrent().getSystemColor(
					SWT.COLOR_WHITE));
			noPostAvailable.setFont(new Font(Controller.getWindow()
					.getDisplay(), "Calibri", 10, SWT.None));
			gridData = new GridData();
			gridData.grabExcessHorizontalSpace = true;
			gridData.horizontalAlignment = GridData.CENTER;
			noPostAvailable.setLayoutData(gridData);
		}

		

		controlli.add(userPostMaster);

		Composite DownloadOlderPosts = new Composite(panel, SWT.None); 
		DownloadOlderPosts.setLayout(new GridLayout(2,false)); 
		gridData = new GridData();
		gridData.horizontalSpan = 5; 
		DownloadOlderPosts.setLayoutData(gridData); 
		
		labelDownloadPost = new Label(DownloadOlderPosts, SWT.None);
		labelDownloadPost.setText("Download older posts..");
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.CENTER;
		gridData.widthHint = 170;
		labelDownloadPost.setLayoutData(gridData);
		labelDownloadPost.setVisible(false);
		pbar = new ProgressBar(DownloadOlderPosts, SWT.None);
		pbar.setVisible(false);
		pbar.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));

		controlli.add(DownloadOlderPosts); 
		
		labelBack.addListener(SWT.MouseDown, azioni);

		Controller.setWindowName("userTimeline");

		userPostMaster.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				// TODO Auto-generated method stub
			
			Composite sc = (Composite)	e.widget; 
			 
			Control[] controls = sc.getChildren(); 
			 totalField = 0; 
			for(int i=0;i<controls.length; i++)
			{
				Control con = controls[i]; 
				totalField += 	con.getSize().y; 
				
			}
			 System.out.println("Lunghezza parte 1 " + sc.getChildren().length); 
			 System.out.println("Lunghezza parte 2 " + totalField/3);
		/*	if(sc.getChildren().length > 50)
			{
				superUserPostMaster.setMinSize(Controller.getWindowWidth(),(totalField - ((sc.getChildren().length / 50) * 100)) );
			}
			else
			{
				superUserPostMaster.setMinSize(Controller.getWindowWidth(),totalField-100);
			}*/
			
			 if(sc.getChildren().length > 100)
				{
					if((sc.getChildren().length/100) > 1)
					{
						superUserPostMaster.setMinSize(Controller.getWindowWidth(),totalField + (2000 * (int) (sc.getChildren().length/150))); 
					}
					else
					{
						System.out.println("Chiamata valore aggiunto "); 
						superUserPostMaster.setMinSize(Controller.getWindowWidth(),totalField + 1600);
					}
						
				}
				 else if(sc.getChildren().length > 40)
				{
					superUserPostMaster.setMinSize(Controller.getWindowWidth(),totalField + 900);
				}
				
				else if(sc.getChildren().length > 20)
				{
					 superUserPostMaster.setMinSize(Controller.getWindowWidth(),totalField + 500);
				}
				else
				{
					superUserPostMaster.setMinSize(Controller.getWindowWidth(),totalField + 100);
				}

				
			
			}
		});  
		
		panel.layout();

	}

	@Override
	public void dispose(Composite panel) {
		// TODO Auto-generated method stub
		for (int i = 0; i < controlli.size(); i++) {
			final int j = i;
			Display.getCurrent().syncExec(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					controlli.get(j).dispose();
				}
			});

		}

	}

	@Override
	public HashMap<String, Object> getData() {
		// TODO Auto-generated method stub
		HashMap<String, Object> uiData = new HashMap<String, Object>();
		uiData.put("userType", userType);
		uiData.put("labelFollow", labelFollow);
		uiData.put("labelSkills", labelSkills);
		uiData.put("labelHide", labelHide);
		uiData.put("userPostMaster", userPostMaster);
		uiData.put("otherPostWarning", otherPostWarning);
		uiData.put("userSelected", user);
		uiData.put("action", azioni);
		uiData.put("otherPostAvailable", otherPostAvailable);
		uiData.put("labelDownloadPost", labelDownloadPost);
		uiData.put("pbar", pbar);

		uiData.put("superUserPostMaster", superUserPostMaster);
		uiData.put("userPostMaster", userPostMaster);

		if (userType == "Suggested" || userType == "Followers"
				|| userType == "Hidden") {
			uiData.put("imageUnFollow", false);
			uiData.put("imageFollow", true);
		} else if (userType == "Following") {
			uiData.put("imageUnFollow", false);
			uiData.put("imageFollow", true);
		}

		return uiData;
	}

}
