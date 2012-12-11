package it.uniba.di.socialcdeforeclipse.staticview;

import it.uniba.di.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.views.Panel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

public class ProfilePanel implements Panel {

	private Listener azioni;
	private Label labelAvatarProfile;
	private Composite composite_static;
	private Label labelPeople;
	private Label labelHomeTimeline;
	private Label labelInterationTimeline;
	private Label labelInteractiveTimeline;
	private Composite composite_dinamic;
	private Label labelHidden;
	private Label labelLogout;
	private ArrayList<Control> controlli;

	private final InputStream PATH_DEFAULT_AVATAR = this.getClass()
			.getClassLoader().getResourceAsStream("images/DefaultAvatar.png");
	private final InputStream PATH_BALOON = this.getClass().getClassLoader()
			.getResourceAsStream("images/Baloon.png");
	private final InputStream PATH_BACK = this.getClass().getClassLoader()
			.getResourceAsStream("images/Toolbar/Back.png");
	private final InputStream PATH_FOLLOW = this.getClass().getClassLoader()
			.getResourceAsStream("images/Toolbar/Follow.png");
	private final InputStream PATH_HIDE = this.getClass().getClassLoader()
			.getResourceAsStream("images/Toolbar/Hide.png");
	private final InputStream PATH_HOME = this.getClass().getClassLoader()
			.getResourceAsStream("images/Toolbar/Home.png");
	private final InputStream PATH_INTERACTIVE_TIMELINE = this.getClass()
			.getClassLoader()
			.getResourceAsStream("images/Toolbar/InteractiveTimeline.png");
	private final InputStream PATH_INTERACTION_TIMELINE = this.getClass()
			.getClassLoader()
			.getResourceAsStream("images/Toolbar/IterationTimeline.png");
	private final InputStream PATH_LOGOUT = this.getClass().getClassLoader()
			.getResourceAsStream("images/Toolbar/Logout.png");
	private final InputStream PATH_PEOPLE = this.getClass().getClassLoader()
			.getResourceAsStream("images/Toolbar/People.png");
	private final InputStream PATH_WALLPAPER = this.getClass().getClassLoader()
			.getResourceAsStream("images/Wallpaper.png");

	public Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getCurrent(), width, height);
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
		return new Image(Display.getCurrent(), stream);
	}

	@Override
	public void inizialize(Composite panel) {
		// TODO Auto-generated method stub
		Controller.writeOnText("inizio profile"); 
		azioni = new ActionGeneral();
		controlli = new ArrayList<Control>();
		GridLayout layout = new GridLayout(1, false);

		panel.setLayout(layout);
		panel.setBackground(new Color(Display.getCurrent(), 255, 255, 255));
		// panel.setBackgroundImage(resize(get_ImageStream(PATH_WALLPAPER),
		// Controller.getWindowWidth(), Controller.getWindowHeight()));
		// panel.setData("ID_action","profilePanel");

		GridLayout grid_static = new GridLayout(15, false);
		grid_static.makeColumnsEqualWidth = true;

		composite_static = new Composite(panel, SWT.NONE);
		composite_static.setLayout(grid_static);
		composite_static.setBackground(new Color(Display.getCurrent(), 192,
				192, 186));
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = gridData.FILL;
		composite_static.setLayoutData(gridData);

		labelAvatarProfile = new Label(composite_static, SWT.NONE);

		if (Controller.getCurrentUser().Avatar == null
				|| Controller.getCurrentUser().Avatar.equals("")) {
			Controller.writeOnText("Avatar nullo");
			labelAvatarProfile.setImage(get_ImageStream(PATH_DEFAULT_AVATAR));
			labelAvatarProfile.setImage(resize(labelAvatarProfile.getImage(),
					32, 32));
		} else {
			try {
				Controller.writeOnText("Inizio try");
				labelAvatarProfile.setImage(get_ImageStream(new URL(Controller
						.getCurrentUser().Avatar).openStream()));
				labelAvatarProfile.setImage(resize(
						labelAvatarProfile.getImage(), 32, 32));
				Controller.writeOnText("Fine try");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				 Controller.writeOnText(" message " + e.getMessage());
				labelAvatarProfile.setImage(resize(
						get_ImageStream(PATH_DEFAULT_AVATAR), 32, 32));
				 //e.printStackTrace();
				
			}
		}
		Controller.writeOnText("Avatar settato");
		labelAvatarProfile.setToolTipText("Profile");
		labelAvatarProfile.setData("ID_action", "labelAvatarProfile");
		labelAvatarProfile.setCursor(new Cursor(panel.getDisplay(),
				SWT.CURSOR_HAND));
		controlli.add(labelAvatarProfile);
		Controller.writeOnText("Fine label avatar");
		labelPeople = new Label(composite_static, SWT.PUSH);
		Controller.writeOnText("one station");
		labelPeople.setImage(get_ImageStream(PATH_PEOPLE));
		Controller.writeOnText("two station");
		labelPeople.setCursor(new Cursor(panel.getDisplay(), SWT.CURSOR_HAND));
		Controller.writeOnText("three station");
		labelPeople.setToolTipText("People");
		Controller.writeOnText("four station");
		labelPeople.setData("ID_action", "labelPeople");
		Controller.writeOnText(" step people");
		controlli.add(labelPeople);

		labelHomeTimeline = new Label(composite_static, SWT.PUSH);
		labelHomeTimeline.setImage(get_ImageStream(PATH_HOME));
		labelHomeTimeline.setCursor(new Cursor(panel.getDisplay(),
				SWT.CURSOR_HAND));
		labelHomeTimeline.setToolTipText("Home Timeline");
		labelHomeTimeline.setData("ID_action", "labelHomeTimeline");

		controlli.add(labelHomeTimeline);
		Controller.writeOnText(" step home timeline");
		labelInterationTimeline = new Label(composite_static, SWT.PUSH);
		labelInterationTimeline
				.setImage(get_ImageStream(PATH_INTERACTION_TIMELINE));
		labelInterationTimeline.setCursor(new Cursor(panel.getDisplay(),
				SWT.CURSOR_HAND));
		labelInterationTimeline.setToolTipText("Interation Timeline");
		labelInterationTimeline.setData("ID_action", "labelIterationTimeline");
		controlli.add(labelInterationTimeline);
		Controller.writeOnText("step interaction");
		labelInteractiveTimeline = new Label(composite_static, SWT.PUSH);
		labelInteractiveTimeline
				.setImage(get_ImageStream(PATH_INTERACTIVE_TIMELINE));
		labelInteractiveTimeline.setCursor(new Cursor(panel.getDisplay(),
				SWT.CURSOR_HAND));
		labelInteractiveTimeline.setToolTipText("Interactive Timeline");

		controlli.add(labelInteractiveTimeline);
		Controller.writeOnText(" step interactive");
		labelLogout = new Label(composite_static, SWT.BUTTON_MASK);
		labelLogout.setImage(get_ImageStream(PATH_LOGOUT));
		labelLogout.setCursor(new Cursor(panel.getDisplay(), SWT.CURSOR_HAND));
		labelLogout.setData("ID_action", "labelLogout");
		labelLogout.setToolTipText("Logout");
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;

		labelLogout.setLayoutData(gridData);
		Controller.writeOnText(" step logout");
		controlli.add(labelLogout);

		composite_static.layout();
		Controller.writeOnText("Inizio set dynamic");
		Controller.selectDynamicWindow(0);

		controlli.add(composite_static);
		controlli.add(composite_dinamic);

		panel.layout();

		// panel.addListener(SWT.Resize, azioni);
		labelAvatarProfile.addListener(SWT.MouseDown, azioni);
		labelHomeTimeline.addListener(SWT.MouseDown, azioni);
		labelInterationTimeline.addListener(SWT.MouseDown, azioni);
		labelLogout.addListener(SWT.MouseDown, azioni);
		labelPeople.addListener(SWT.MouseDown, azioni);
		
		Controller.writeOnText("fine profile"); 

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
		panel.setLayout(null);
	}

	public Composite getComposite_static() {
		return composite_static;
	}

	public void setComposite_static(Composite composite_static) {
		this.composite_static = composite_static;
	}

	public Label getLabelPeople() {
		return labelPeople;
	}

	public void setLabelPeople(Label labelPeople) {
		this.labelPeople = labelPeople;
	}

	public Label getLabelHomeTimeline() {
		return labelHomeTimeline;
	}

	public void setLabelHomeTimeline(Label labelHomeTimeline) {
		this.labelHomeTimeline = labelHomeTimeline;
	}

	public Label getLabelInteractiveTimeline() {
		return labelInteractiveTimeline;
	}

	public void setLabelInteractiveTimeline(Label labelInteractiveTimeline) {
		this.labelInteractiveTimeline = labelInteractiveTimeline;
	}

	public Composite getComposite_dinamic() {
		return composite_dinamic;
	}

	public Label getLabelAvatarProfile() {
		return labelAvatarProfile;
	}

	public void setLabelAvatarProfile(Label labelAvatarProfile) {
		this.labelAvatarProfile = labelAvatarProfile;
	}

	public void setComposite_dinamic(Composite composite_dinamic) {
		this.composite_dinamic = composite_dinamic;
	}

	@Override
	public HashMap<String, Object> getData() {
		// TODO Auto-generated method stub
		return null;
	}

}
