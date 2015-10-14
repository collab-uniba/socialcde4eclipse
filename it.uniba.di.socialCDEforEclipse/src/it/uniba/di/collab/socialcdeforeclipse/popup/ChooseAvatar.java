package it.uniba.di.collab.socialcdeforeclipse.popup;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.object.ButtonAvatar;
import it.uniba.di.collab.socialcdeforeclipse.object.Panel;

public class ChooseAvatar implements Panel {
	private Shell shell;
	private Shell shadow;
	private int xCoordinate;
	private int yCoordinate;
	private int xCoordinateWithOffset;
	private int yCoordinateWithOffset;
	private Composite firstComposite;
	private Button btnAvatarBack;
	private Listener backListener;
	private ArrayList<ButtonAvatar> allAvatar;
	private Label noAvatarMessage = null;
	private URI[] uriAvatar;

	private final InputStream PATH_WALLPAPER = this.getClass().getClassLoader()
			.getResourceAsStream("images/Wallpaper.png");
	private final InputStream PATH_ECLIPSE_ICON = this.getClass()
			.getClassLoader().getResourceAsStream("icons/sample.gif");
	private final InputStream PATH_DEFAULT_AVATAR = this.getClass()
			.getClassLoader().getResourceAsStream("images/DefaultAvatar.png");

	public Listener getBackListener() {
		return backListener;
	}

	public void setBackListener(Listener backListener) {
		this.backListener = backListener;
	}

	public int getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public int getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public int getxCoordinateWithOffset() {
		return xCoordinateWithOffset;
	}

	public void setxCoordinateWithOffset(int xCoordinateWithOffset) {
		this.xCoordinateWithOffset = xCoordinateWithOffset;
	}

	public int getyCoordinateWithOffset() {
		return yCoordinateWithOffset;
	}

	public void setyCoordinateWithOffset(int yCoordinateWithOffset) {
		this.yCoordinateWithOffset = yCoordinateWithOffset;
	}


	@Override
	public void inizialize(Composite panel) {
		// TODO Auto-generated method stub
		GridData gridData;

		shadow = new Shell(panel.getShell(), SWT.NO_TRIM);
		shadow.setSize(Controller.getWindowWidth(),
				Controller.getWindowHeight());
		shadow.setBounds(xCoordinate, yCoordinate, Controller.getWindowWidth(),
				Controller.getWindowHeight());
		shadow.setLayout(new GridLayout(1, false));
		shadow.setAlpha(100);
		shadow.layout();
		shadow.open();

		shadow.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				shell.forceFocus();
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				shell.forceFocus();
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				shell.forceFocus();
			}
		});

		shell = new Shell(panel.getShell(), SWT.TITLE);

		shell.setBounds(xCoordinateWithOffset, yCoordinateWithOffset, 300, 200);
		shell.setImage(getImageStream(PATH_ECLIPSE_ICON));

		shell.setLayout(new GridLayout(1, false));

		Composite compositeBack = new Composite(shell, SWT.None);
		compositeBack.setLayout(new GridLayout(1, false));
		compositeBack.setBackground(new Color(Display.getCurrent(), 192, 192,
				186));
		gridData = new GridData();
		compositeBack.setLayoutData(gridData);

		Label labelBack = new Label(compositeBack, SWT.None);
		labelBack.setCursor(new Cursor(panel.getDisplay(), SWT.CURSOR_HAND));
		labelBack.setImage(getImageStream(this.getClass().getClassLoader()
				.getResourceAsStream("images/Toolbar/Back.png")));
		labelBack.setData("ID_action", "labelBack");
		labelBack.addListener(SWT.MouseDown, backListener);
		
		uriAvatar = Controller.getProxy().GetAvailableAvatars(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword());
		System.out.println(" avatar " + uriAvatar.length);
		if (uriAvatar.length > 0) {

			final ScrolledComposite sc = new ScrolledComposite(shell, SWT.None
					| SWT.H_SCROLL | SWT.V_SCROLL);
			sc.setLayout(new GridLayout(1, false));
			sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

			firstComposite = new Composite(sc, SWT.None);
			firstComposite.setLayout(new GridLayout(2, false));
			firstComposite.setBackground(new Color(Display.getCurrent(), 255,
					255, 255));

			if (uriAvatar.length > 2) {
				sc.addControlListener(new ControlListener() {

					@Override
					public void controlResized(ControlEvent e) {
						// TODO Auto-generated method stub

						Rectangle r = sc.getClientArea();
						sc.setMinSize(shell.computeSize(r.width, SWT.DEFAULT));

					}

					@Override
					public void controlMoved(ControlEvent e) {
						// TODO Auto-generated method stub

					}
				});
			}
			allAvatar = new ArrayList<ButtonAvatar>();

			for (int i = 0; i < uriAvatar.length; i++) {

				Boolean flag = true;
				System.out.println("uri  " + i + " n. "
						+ uriAvatar[i].toString());
				try {

					getImageStream(uriAvatar[i].toURL().openStream());

				} catch (Exception e) {
					// TODO: handle exception
					flag = false;
				}
				System.out.println("Avatars flag " + flag);
				if (flag) {

					Composite service1 = new Composite(firstComposite, SWT.None);
					service1.setLayout(new GridLayout(1, false));
					service1.setBackground(Display.getCurrent().getSystemColor(
							SWT.COLOR_WHITE));

					ButtonAvatar avatarImage = new ButtonAvatar(service1,
							SWT.NONE);
					avatarImage.setRoundedCorners(false);

					if (Controller.getCurrentUser().getAvatar() != null &&  Controller.getCurrentUser().getAvatar()
							.equals(uriAvatar[i].toString())) {

						avatarImage.setDefaultColors(
								new Color(Display.getCurrent(), 230, 230, 223),
								null,
								Display.getCurrent().getSystemColor(
										SWT.COLOR_DARK_GREEN), null);
						avatarImage.setSelectedColors(
								new Color(Display.getCurrent(), 230, 230, 223),
								null,
								Display.getCurrent().getSystemColor(
										SWT.COLOR_DARK_GREEN), null);
					} else {
						avatarImage.setDefaultColors(
								new Color(Display.getCurrent(), 230, 230, 223),
								null, null, null);
						avatarImage.setSelectedColors(
								new Color(Display.getCurrent(), 230, 230, 223),
								null, null, null);
					}

					avatarImage.setClickedColors(new Color(
							Display.getCurrent(), 230, 230, 223), null, Display
							.getCurrent().getSystemColor(SWT.COLOR_RED), null);
					avatarImage.setHoverColors(new Color(Display.getCurrent(),
							230, 230, 223), null, Display.getCurrent()
							.getSystemColor(SWT.COLOR_CYAN), null);

					avatarImage.borderWidth = 3;
					avatarImage.setText("");
					avatarImage.setData("ID_action", "btnAvatar");
					avatarImage.setData("URI", uriAvatar[i]);
					final int j = i;
					avatarImage.addListener(SWT.Selection, new Listener() {

						@Override
						public void handleEvent(Event event) {
							// TODO Auto-generated method stub

							if (!Controller.getProxy().SaveAvatar(
									Controller.getCurrentUser().Username,
									Controller.getCurrentUserPassword(),
									uriAvatar[j])) {

								MessageBox messageBox = new MessageBox(
										firstComposite.getShell(),
										SWT.ICON_ERROR | SWT.OK);
								messageBox
										.setMessage("Something was wrong, please try again.");
								messageBox
										.setText("SocialCDEforEclipse Message");
								messageBox.open();
							} else {
								try {					
									ChooseAvatar.this.dispose(Controller.getWindow());
									Controller.getUsersAvatar().get(Controller.getCurrentUser().Username).dispose(); 
									Controller.getUsersAvatar().put(Controller.getCurrentUser().Username, getUserImage());
									
									Controller.selectDynamicWindow(0);
									
									Image avatarImage = resize(Controller
											.getProfilePanel()
											.get_ImageStream(
													new URL(
															Controller
																	.getCurrentUser().Avatar)
															.openStream()),75,75);
									Controller.getUsersAvatar().put(Controller.getCurrentUser().Username, getUserImage()); 
									
									Controller
										.getProfilePanel()
										.getLabelAvatarProfile()
										.setImage(resize(new Image(Display.getCurrent(), avatarImage, SWT.IMAGE_COPY) ,32,32));
									
									((Label) Controller.getHomeWindow().getData().get("labelAvatar")).setImage(	avatarImage);
									
								} catch(IOException e) {
									Controller
									.getProfilePanel()
									.getLabelAvatarProfile()
									.setImage(
											Controller
													.getProfilePanel()
													.get_ImageStream(
															PATH_DEFAULT_AVATAR));
									
									((Label) Controller.getHomeWindow().getData().get("labelAvatar")).setImage(
											Controller
											.getProfilePanel()
											.get_ImageStream(
													PATH_DEFAULT_AVATAR));
									
									Controller
											.getProfilePanel()
											.getLabelAvatarProfile()
											.setImage(
													Controller
															.getProfilePanel()
															.resize(Controller
																	.getProfilePanel()
																	.getLabelAvatarProfile()
																	.getImage(), 32, 32));
									((Label) Controller.getHomeWindow().getData().get("labelAvatar")).setImage(
											Controller
											.getProfilePanel()
											.resize(Controller
													.getProfilePanel()
													.getLabelAvatarProfile()
													.getImage(), 75, 75));
								}
								
							}

						}
					});

					try {

						avatarImage.setImage(resize(getImageStream(uriAvatar[i]
								.toURL().openStream()), 70, 70));

					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					allAvatar.add(avatarImage);
				}

			}

			sc.setContent(firstComposite);
			sc.setMinSize(265, 165);

			// Expand both horizontally and vertically
			sc.setExpandHorizontal(true);
			sc.setExpandVertical(true);

			sc.layout();

		} else {

			shell.setBackground(Display.getCurrent().getSystemColor(
					SWT.COLOR_WHITE));

			firstComposite = new Composite(shell, SWT.None);
			firstComposite.setLayout(new GridLayout(1, false));
			firstComposite.setBackground(Display.getCurrent().getSystemColor(
					SWT.COLOR_WHITE));
			gridData = new GridData();
			gridData.grabExcessHorizontalSpace = true;
			gridData.grabExcessVerticalSpace = true;
			gridData.horizontalAlignment = gridData.CENTER;
			gridData.verticalAlignment = gridData.CENTER;
			firstComposite.setLayoutData(gridData);

			noAvatarMessage = new Label(firstComposite, SWT.None);
			noAvatarMessage.setText("There are no avatars available.");
			noAvatarMessage.setBackground(Display.getCurrent().getSystemColor(
					SWT.COLOR_WHITE));
			noAvatarMessage.setFont(new Font(shell.getDisplay(), "Calibri", 12,
					SWT.BOLD));

		}

		firstComposite.layout();

		shell.layout();
		shell.open();

	}

	@Override
	public void dispose(Composite panel) {
		// TODO Auto-generated method stub
		shadow.dispose();
		shell.dispose();
	}

	@Override
	public HashMap<String, Object> getData() {
		// TODO Auto-generated method stub
		HashMap<String, Object> uiData = new HashMap<String, Object>();

		if (uriAvatar.length > 0) {
			for (int i = 0; i < allAvatar.size(); i++) {
				uiData.put("Avatar" + i, allAvatar.get(i));
			}
		}
		uiData.put("btnAvatarBack", btnAvatarBack);
		uiData.put("noAvatarMessage", noAvatarMessage);

		return uiData;
	}
	
	private Image getUserImage()
	{
		try {
			return resize(get_ImageStream(new URL(Controller
					.getCurrentUser().Avatar).openStream()),48,48);
			

		} catch (IOException e) {
			
			
			return resize(get_ImageStream(PATH_DEFAULT_AVATAR),48,48);
			
			
		} catch (NullPointerException e) {
			
			return resize(get_ImageStream(PATH_DEFAULT_AVATAR),48,48);
		}
	}
	
	private Image get_ImageStream(InputStream stream) {
		return new Image(Display.getCurrent(), stream);
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

	private Image getImageStream(InputStream stream) {
		return new Image(Controller.getWindow().getDisplay(), stream);
	}

}
