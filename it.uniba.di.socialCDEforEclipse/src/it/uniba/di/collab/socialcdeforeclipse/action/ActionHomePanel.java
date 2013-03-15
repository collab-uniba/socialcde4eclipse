package it.uniba.di.collab.socialcdeforeclipse.action;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.ws.Dispatch;

import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.object.SquareButtonService;
import it.uniba.di.collab.socialcdeforeclipse.popup.ChooseAvatar;
import it.uniba.di.collab.socialcdeforeclipse.popup.PinPanel;
import it.uniba.di.collab.socialcdeforeclipse.popup.SettingServicePanel;
import it.uniba.di.collab.socialcdeforeclipse.popup.SkillsPanel;
import it.uniba.di.collab.socialcdeforeclipse.popup.TFSLogin;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.WOAuthData;
import it.uniba.di.collab.socialcdeforeclipse.shared.library.WService;
import it.uniba.di.collab.socialcdeforeclipse.staticview.ProfilePanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class ActionHomePanel {

	
	
	PinPanel pinWindow = new PinPanel();
	private final InputStream PATH_DEFAULT_AVATAR = this.getClass()
			.getClassLoader().getResourceAsStream("images/DefaultAvatar.png");
	private final String browserViewName =  "SocialCDEforEclipse.SocialCDEforEclipseBrowser"; 
	private final String firstViewName = "SocialCDEforEclipse.SocialCDEforEclipseView"; 
	
	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getCurrent(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, image.getBounds().width,
				image.getBounds().height, 0, 0, width, height);
		gc.dispose();
		image.dispose(); 
		return scaled;
	}
	
	public Image get_ImageStream(InputStream stream) {
		return new Image(Controller.getWindow().getDisplay(), stream);
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
	
	public ActionHomePanel(HashMap<String, Object> uiData) {

		String widgetName = uiData.get("ID_action").toString();
		int type = (int) uiData.get("Event_type");
		Event event = (Event) uiData.get("Event");
		IViewPart browser = null;

		switch (widgetName) {

		case "labelSkills":
			
			if(Controller.getProxy().IsWebServiceRunning())
			{
			final SkillsPanel skillPanel = new SkillsPanel();
			skillPanel.setxCoordinate(Controller.getWindow().toDisplay(
					Controller.getWindow().getLocation().x,
					Controller.getWindow().getLocation().y).x);
			skillPanel.setyCoordinate(Controller.getWindow().toDisplay(
					Controller.getWindow().getLocation().x,
					Controller.getWindow().getLocation().y).y);
			skillPanel.setxCoordinateWithOffset(Controller.getWindow()
					.toDisplay(Controller.getWindow().getLocation().x,
							Controller.getWindow().getLocation().y).x + 10);

			skillPanel.setyCoordinateWithOffset(Controller.getWindow()
					.toDisplay(Controller.getWindow().getLocation().x,
							Controller.getWindow().getLocation().y).y
					+ (Controller.getWindow().getBounds().height - 200) / 2);
			skillPanel.setUser_selected(Controller.getCurrentUser());
			skillPanel.setBackListener(new Listener() {

				@Override
				public void handleEvent(Event event) {
					// TODO Auto-generated method stub
					skillPanel.dispose(Controller.getWindow());
				}
			});
			skillPanel.inizialize(Controller.getWindow());
			}else
			{
				Controller.openConnectionLostPanel("Connection Lost! \n You will be redirected to Login page."); 
				
			}
			break;

		case "labelAvatar":
			if(Controller.getProxy().IsWebServiceRunning())
			{
			final ChooseAvatar available_avatar = new ChooseAvatar();
			available_avatar.setxCoordinate(Controller.getWindow().toDisplay(
					Controller.getWindow().getLocation().x,
					Controller.getWindow().getLocation().y).x);
			available_avatar.setyCoordinate(Controller.getWindow().toDisplay(
					Controller.getWindow().getLocation().x,
					Controller.getWindow().getLocation().y).y);
			available_avatar.setxCoordinateWithOffset(Controller.getWindow()
					.toDisplay(Controller.getWindow().getLocation().x,
							Controller.getWindow().getLocation().y).x
					+ (Controller.getWindow().getBounds().width - 300) / 2);
			available_avatar.setyCoordinateWithOffset(Controller.getWindow()
					.toDisplay(Controller.getWindow().getLocation().x,
							Controller.getWindow().getLocation().y).y
					+ (Controller.getWindow().getBounds().height - 200) / 2);
			available_avatar.setBackListener(new Listener() {

				@Override
				public void handleEvent(Event event) {
					// TODO Auto-generated method stub
					
					Controller.getUsersAvatar().get(Controller.getCurrentUser().Username).dispose(); 
					Controller.getUsersAvatar().put(Controller.getCurrentUser().Username, getUserImage());
					
							available_avatar.dispose(Controller.getWindow());
							Controller.selectDynamicWindow(0);
							
							
							if (Controller.getCurrentUser().Avatar == null
									|| Controller.getCurrentUser().Avatar.equals("")) {
								Controller
										.getProfilePanel()
										.getLabelAvatarProfile()
										.setImage(
												Controller.getProfilePanel()
														.get_ImageStream(
																PATH_DEFAULT_AVATAR));
								((Label) Controller.getHomeWindow().getData().get("labelAvatar")).setImage(
										Controller.getProfilePanel()
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
										
							} else {
								try {
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
									

								} catch (IOException e) {
									

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
			available_avatar.inizialize(Controller.getWindow());
			uiData.put("Avatar_Window", available_avatar);
			}else
			{
				Controller.openConnectionLostPanel("Connection Lost! \n  You will be redirected to Login page."); 	
			}
			break;
		case "labelSettings":
			if (type == SWT.MouseDown && Controller.getProxy().IsWebServiceRunning()) {

				Controller.selectDynamicWindow(1);
			}
			else
			{
				Controller.openConnectionLostPanel("Connection Lost! \n You will be redirected to Login page."); 	
			}
			break;
		case "btnServices":
			if (type == SWT.Selection && Controller.getProxy().IsWebServiceRunning()) {

				WService service = (WService) uiData.get("service");
				if (service.Registered) {

					final SettingServicePanel serviceSetting = new SettingServicePanel();
					serviceSetting.setxCoordinate(Controller.getWindow()
							.toDisplay(Controller.getWindow().getLocation().x,
									Controller.getWindow().getLocation().y).x);
					serviceSetting.setyCoordinate(Controller.getWindow()
							.toDisplay(Controller.getWindow().getLocation().x,
									Controller.getWindow().getLocation().y).y);
					if(Controller.OSisWindows())
					{
					serviceSetting.setxCoordinateWithOffset(Controller
							.getWindow().toDisplay(
									Controller.getWindow().getLocation().x,
									Controller.getWindow().getLocation().y).x - 30);
					}
					else
					{
						serviceSetting.setxCoordinateWithOffset(Controller
								.getWindow().toDisplay(
										Controller.getWindow().getLocation().x,
										Controller.getWindow().getLocation().y).x - 90);
					}
					serviceSetting.setyCoordinateWithOffset(Controller
							.getWindow().toDisplay(
									Controller.getWindow().getLocation().x,
									Controller.getWindow().getLocation().y).y
							+ (Controller.getWindow().getBounds().height - 200)
							/ 2);
					serviceSetting.setService(service);
					serviceSetting.setBtnUnsubscriveListener(new Listener() {

						@Override
						public void handleEvent(Event event) {
							MessageBox messageBox = new MessageBox(Controller
									.getWindow().getShell(), SWT.ICON_WARNING
									| SWT.YES | SWT.NO);
							messageBox
									.setMessage("Are you sure you want to unsubscribe?");
							messageBox.setText("SocialCDEforEclipse Message");
							int response = messageBox.open();

							switch (response) {
							case SWT.YES:
								if (!Controller
										.getProxy()
										.DeleteRegistredService(
												Controller.getCurrentUser().Username,
												Controller
														.getCurrentUserPassword(),
												serviceSetting.getService().Id)) {
									MessageBox messageBox2 = new MessageBox(
											Controller.getWindow().getShell(),
											SWT.ICON_ERROR | SWT.OK);
									messageBox2
											.setMessage("Something was wrong, please try again.");
									messageBox2
											.setText("SocialCDEforEclipse Message");
									messageBox2.open();
								}
								break;
							case SWT.NO:
							default:
								break;
							}
							serviceSetting.dispose(null);

							SquareButtonService.yCoordinateValue = 5;
							SquareButtonService.counterPosition = 0;

							Controller.selectDynamicWindow(0);

						}
					});

					serviceSetting.setBtnSaveListener(new Listener() {

						@Override
						public void handleEvent(Event event) {

							ArrayList<Button> btnCheckbox = serviceSetting
									.getCheckboxCreated();

							int counter = 0;
							for (int i = 0; i < btnCheckbox.size(); i++) {
								if (btnCheckbox.get(i).getSelection()) {
									counter += 1;
								}
							}

							String[] strFeature = new String[counter];
							counter = 0;
							for (int i = 0; i < btnCheckbox.size(); i++) {
								if (btnCheckbox.get(i).getSelection()) {
									strFeature[counter] = btnCheckbox.get(i)
											.getData("FeatureName").toString();
									counter += 1;
								}
							}

							if (Controller.getProxy().UpdateChosenFeatures(
									Controller.getCurrentUser().Username,
									Controller.getCurrentUserPassword(),
									serviceSetting.getService().Id, strFeature)) {
								serviceSetting.dispose(null);
							} else {
								MessageBox messageBox2 = new MessageBox(
										Controller.getWindow().getShell(),
										SWT.ICON_ERROR | SWT.OK);
								messageBox2
										.setMessage("Something was wrong, please try again.");
								messageBox2
										.setText("SocialCDEforEclipse Message");
								messageBox2.open();

							}
						}
					});

					serviceSetting.inizialize(Controller.getWindow());

				} else {

					if (service.RequireOAuth) {

						WOAuthData oauthData = Controller.getProxy()
								.GetOAuthData(
										Controller.getCurrentUser().Username,
										Controller.getCurrentUserPassword(),
										service.Id);
						/*System.out.println("OauthData access secret "
								+ oauthData.AccessSecret + " token "
								+ oauthData.AccessToken + " oauth link "
								+ oauthData.AuthorizationLink + " id "
								+ service.Id);*/
						pinWindow
								.setxCoordinate(Controller.getWindow()
										.toDisplay(
												Controller.getWindow()
														.getLocation().x,
												Controller.getWindow()
														.getLocation().y).x);
						pinWindow
								.setyCoordinate(Controller.getWindow()
										.toDisplay(
												Controller.getWindow()
														.getLocation().x,
												Controller.getWindow()
														.getLocation().y).y);
						pinWindow
								.setxCoordinateWithOffset(Controller
										.getWindow().toDisplay(
												Controller.getWindow()
														.getLocation().x,
												Controller.getWindow()
														.getLocation().y).x- 30);
						pinWindow
								.setyCoordinateWithOffset(Controller
										.getWindow().toDisplay(
												Controller.getWindow()
														.getLocation().x,
												Controller.getWindow()
														.getLocation().y).y
										+ (Controller.getWindow().getBounds().height - 200)
										/ 2);
						pinWindow.setService(service);
						pinWindow.setOauthData(oauthData);

						Controller.temporaryInformation.put("CurrentURL",
								oauthData.AuthorizationLink);

						Controller.temporaryInformation.put("Service", service);

						pinWindow.setOkListener(new Listener() {

							@Override
							public void handleEvent(Event event) {

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
							PlatformUI
									.getWorkbench()
									.getActiveWorkbenchWindow()
									.getActivePage()
									.showView(browserViewName);

						} catch (PartInitException e1) {

							e1.printStackTrace();
						}


					} else if (service.RequireTFSAuthentication) {
						final TFSLogin tfsPanel = new TFSLogin();
						tfsPanel.setxCoordinate(Controller.getWindow()
								.toDisplay(
										Controller.getWindow().getLocation().x,
										Controller.getWindow().getLocation().y).x);
						tfsPanel.setyCoordinate(Controller.getWindow()
								.toDisplay(
										Controller.getWindow().getLocation().x,
										Controller.getWindow().getLocation().y).y);
						tfsPanel.setxCoordinateWithOffset(Controller
								.getWindow().toDisplay(
										Controller.getWindow().getLocation().x,
										Controller.getWindow().getLocation().y).x - 30 );
						tfsPanel.setyCoordinateWithOffset(Controller
								.getWindow().toDisplay(
										Controller.getWindow().getLocation().x,
										Controller.getWindow().getLocation().y).y
								+ (Controller.getWindow().getBounds().height - 200)
								/ 2);
						tfsPanel.setService(service);
						tfsPanel.setOkListener(new Listener() {

							@Override
							public void handleEvent(Event event) {
								// TODO Auto-generated method stub
								HashMap<String, Object> tfsData = tfsPanel
										.getData();
								/*System.out.println("Domain "
										+ tfsData.get("textDomain").toString());
								System.out.println("Username "
										+ ((Text) tfsData.get("textUsername"))
												.getText());
								System.out.println("Password "
										+ ((Text) tfsData.get("textPassword"))
												.getText());*/

								if (Controller.getProxy().RecordService(
										Controller.getCurrentUser().Username,
										Controller.getCurrentUserPassword(),
										tfsPanel.getService().Id,
										((Text) tfsData.get("textUsername"))
												.getText(),
										((Text) tfsData.get("textPassword"))
												.getText(),
										tfsData.get("textDomain").toString())) {
									tfsPanel.dispose(null);
									Controller.selectDynamicWindow(0);

									final SettingServicePanel serviceSetting = new SettingServicePanel();
									serviceSetting
											.setxCoordinate(Controller
													.getWindow()
													.toDisplay(
															Controller
																	.getWindow()
																	.getLocation().x,
															Controller
																	.getWindow()
																	.getLocation().y).x);
									serviceSetting
											.setyCoordinate(Controller
													.getWindow()
													.toDisplay(
															Controller
																	.getWindow()
																	.getLocation().x,
															Controller
																	.getWindow()
																	.getLocation().y).y);
									serviceSetting
											.setxCoordinateWithOffset(Controller
													.getWindow()
													.toDisplay(
															Controller
																	.getWindow()
																	.getLocation().x,
															Controller
																	.getWindow()
																	.getLocation().y).x - 30);
									serviceSetting
											.setyCoordinateWithOffset(Controller
													.getWindow()
													.toDisplay(
															Controller
																	.getWindow()
																	.getLocation().x,
															Controller
																	.getWindow()
																	.getLocation().y).y
													+ (Controller.getWindow()
															.getBounds().height - 200)
													/ 2);
									serviceSetting.setSelectAllItems(true);
									serviceSetting.setService(tfsPanel
											.getService());
									serviceSetting
											.setBtnUnsubscriveListener(new Listener() {

												@Override
												public void handleEvent(
														Event event) {
													MessageBox messageBox = new MessageBox(
															Controller
																	.getWindow()
																	.getShell(),
															SWT.ICON_WARNING
																	| SWT.YES
																	| SWT.NO);
													messageBox
															.setMessage("Are you sure you want to unsubscribe?");
													messageBox
															.setText("SocialCDEforEclipse Message");
													int response = messageBox
															.open();

													switch (response) {
													case SWT.YES:
														if (!Controller
																.getProxy()
																.DeleteRegistredService(
																		Controller
																				.getCurrentUser().Username,
																		Controller
																				.getCurrentUserPassword(),
																		serviceSetting
																				.getService().Id)) {
															MessageBox messageBox2 = new MessageBox(
																	Controller
																			.getWindow()
																			.getShell(),
																	SWT.ICON_ERROR
																			| SWT.OK);
															messageBox2
																	.setMessage("Something was wrong, please try again.");
															messageBox2
																	.setText("SocialCDEforEclipse Message");
															messageBox2.open();
														}
														break;
													case SWT.NO:
													default:
														break;
													}
													serviceSetting
															.dispose(null);

													SquareButtonService.yCoordinateValue = 5;
													SquareButtonService.counterPosition = 0;

													Controller
															.selectDynamicWindow(0);

												}
											});

									serviceSetting
											.setBtnSaveListener(new Listener() {

												@Override
												public void handleEvent(
														Event event) {

													ArrayList<Button> btnCheckbox = serviceSetting
															.getCheckboxCreated();

													int counter = 0;
													for (int i = 0; i < btnCheckbox
															.size(); i++) {
														if (btnCheckbox.get(i)
																.getSelection()) {
															counter += 1;
														}
													}

													String[] strFeature = new String[counter];
													counter = 0;
													for (int i = 0; i < btnCheckbox
															.size(); i++) {
														if (btnCheckbox.get(i)
																.getSelection()) {
															strFeature[counter] = btnCheckbox
																	.get(i)
																	.getData(
																			"FeatureName")
																	.toString();
															counter += 1;
														}
													}

													if (Controller
															.getProxy()
															.UpdateChosenFeatures(
																	Controller
																			.getCurrentUser().Username,
																	Controller
																			.getCurrentUserPassword(),
																	serviceSetting
																			.getService().Id,
																	strFeature)) {
														serviceSetting
																.dispose(null);
													} else {
														MessageBox messageBox2 = new MessageBox(
																Controller
																		.getWindow()
																		.getShell(),
																SWT.ICON_ERROR
																		| SWT.OK);
														messageBox2
																.setMessage("Something was wrong, please try again.");
														messageBox2
																.setText("SocialCDEforEclipse Message");
														messageBox2.open();

													}
												}
											});

									serviceSetting.inizialize(Controller
											.getWindow());
								} else {
									MessageBox messageBox2 = new MessageBox(
											Controller.getWindow().getShell(),
											SWT.ICON_ERROR | SWT.OK);
									messageBox2
											.setMessage("Something was wrong, please try again.");
									messageBox2
											.setText("SocialCDEforEclipse Message");
									messageBox2.open();

								}

							}
						});
						tfsPanel.setCancelListener(new Listener() {

							@Override
							public void handleEvent(Event event) {
								tfsPanel.dispose(null);
							}
						});
						tfsPanel.inizialize(Controller.getWindow());
					}
				}

			}
			else
			{
				Controller.openConnectionLostPanel("Connection Lost! \n You will be redirected to Login page."); 	
			}
			break;

		default:
			break;
		}

	}

	private void ServiceCancelClick() {

		try {
			((IWorkbenchWindow) Controller.temporaryInformation
					.get("Workbench"))
					.getActivePage()
					.findView(browserViewName);
			((IWorkbenchWindow) Controller.temporaryInformation
					.get("Workbench"))
					.getActivePage()
					.findView(firstViewName)
					.setFocus();
			((IWorkbenchWindow) Controller.temporaryInformation
					.get("Workbench"))
					.getActivePage()
					.hideView(
							((IWorkbenchWindow) Controller.temporaryInformation
									.get("Workbench"))
									.getActivePage()
									.findView(browserViewName));

		} catch (Exception e) {
			try {
				((IWorkbenchWindow) Controller.temporaryInformation
						.get("Workbench"))
						.getActivePage()
						.showView(browserViewName);
				((IWorkbenchWindow) Controller.temporaryInformation
						.get("Workbench"))
						.getActivePage()
						.hideView(
								((IWorkbenchWindow) Controller.temporaryInformation
										.get("Workbench"))
										.getActivePage()
										.findView(browserViewName));
			} catch (Exception e1) {
				MessageBox messageBox2 = new MessageBox(Controller.getWindow()
						.getShell(), SWT.ICON_ERROR | SWT.OK);
				messageBox2
						.setMessage("Something was wrong, please try again.");
				messageBox2.setText("SocialCDEforEclipse Message");
				messageBox2.open();
			}
		}

		pinWindow.dispose(Controller.getWindow());
	}

	private void ServiceOkClick() {

		switch (pinWindow.getService().OAuthVersion) {
		case 1:

			if (Controller.getProxy().Authorize(
					Controller.getCurrentUser().Username,
					Controller.getCurrentUserPassword(),
					pinWindow.getService().Id, pinWindow.getTxtPin().getText(),
					pinWindow.getOauthData().AccessToken,
					pinWindow.getOauthData().AccessSecret)) {
				pinWindow.dispose(Controller.getWindow());
				pinWindow.getService().Registered = true;
				pinWindow.setOauthData(null);

				((IWorkbenchWindow) Controller.temporaryInformation
						.get("Workbench"))
						.getActivePage()
						.findView(firstViewName)
						.setFocus();
				Controller.selectDynamicWindow(0);
				

				final SettingServicePanel serviceSetting = new SettingServicePanel();
				serviceSetting.setxCoordinate(Controller.getWindow().toDisplay(
						Controller.getWindow().getLocation().x,
						Controller.getWindow().getLocation().y).x);
				serviceSetting.setyCoordinate(Controller.getWindow().toDisplay(
						Controller.getWindow().getLocation().x,
						Controller.getWindow().getLocation().y).y);
				serviceSetting.setxCoordinateWithOffset(Controller.getWindow()
						.toDisplay(Controller.getWindow().getLocation().x,
								Controller.getWindow().getLocation().y).x - 30);
				serviceSetting
						.setyCoordinateWithOffset(Controller.getWindow()
								.toDisplay(
										Controller.getWindow().getLocation().x,
										Controller.getWindow().getLocation().y).y
								+ (Controller.getWindow().getBounds().height - 200)
								/ 2);
				serviceSetting.setSelectAllItems(true);
				serviceSetting.setService(pinWindow.getService());
				serviceSetting.setBtnUnsubscriveListener(new Listener() {

					@Override
					public void handleEvent(Event event) {
						// TODO Auto-generated method stub
						MessageBox messageBox = new MessageBox(Controller
								.getWindow().getShell(), SWT.ICON_WARNING
								| SWT.YES | SWT.NO);
						messageBox
								.setMessage("Are you sure you want to unsubscribe?");
						messageBox.setText("SocialCDEforEclipse Message");
						int response = messageBox.open();

						switch (response) {
						case SWT.YES:
							if (!Controller.getProxy().DeleteRegistredService(
									Controller.getCurrentUser().Username,
									Controller.getCurrentUserPassword(),
									serviceSetting.getService().Id)) {
								MessageBox messageBox2 = new MessageBox(
										Controller.getWindow().getShell(),
										SWT.ICON_ERROR | SWT.OK);
								messageBox2
										.setMessage("Something was wrong, please try again.");
								messageBox2
										.setText("SocialCDEforEclipse Message");
								messageBox2.open();
							}
							break;
						case SWT.NO:
						default:
							break;
						}
						serviceSetting.dispose(null);
						

						SquareButtonService.yCoordinateValue = 5;
						SquareButtonService.counterPosition = 0;

						Controller.selectDynamicWindow(0);

					}
				});

				serviceSetting.setBtnSaveListener(new Listener() {

					@Override
					public void handleEvent(Event event) {
						

						ArrayList<Button> btnCheckbox = serviceSetting
								.getCheckboxCreated();

						int counter = 0;
						for (int i = 0; i < btnCheckbox.size(); i++) {
							if (btnCheckbox.get(i).getSelection()) {
								counter += 1;
							}
						}

						String[] strFeature = new String[counter];
						counter = 0;
						for (int i = 0; i < btnCheckbox.size(); i++) {
							if (btnCheckbox.get(i).getSelection()) {
								strFeature[counter] = btnCheckbox.get(i)
										.getData("FeatureName").toString();
								counter += 1;
							}
						}

						if (Controller.getProxy().UpdateChosenFeatures(
								Controller.getCurrentUser().Username,
								Controller.getCurrentUserPassword(),
								serviceSetting.getService().Id, strFeature)) {
							serviceSetting.dispose(null);
						} else {
							MessageBox messageBox2 = new MessageBox(Controller
									.getWindow().getShell(), SWT.ICON_ERROR
									| SWT.OK);
							messageBox2
									.setMessage("Something was wrong, please try again.");
							messageBox2.setText("SocialCDEforEclipse Message");
							messageBox2.open();

						}
					}
				});

				serviceSetting.inizialize(Controller.getWindow());
			} else {

				MessageBox messageBox2 = new MessageBox(Controller.getWindow()
						.getShell(), SWT.ICON_ERROR | SWT.OK);
				messageBox2
						.setMessage("Something was wrong, please try again.");
				messageBox2.setText("SocialCDEforEclipse Message");
				messageBox2.open();
				pinWindow.dispose(null);

			}

			try {
				((IWorkbenchWindow) Controller.temporaryInformation
						.get("Workbench"))
						.getActivePage()
						.findView(browserViewName);
				((IWorkbenchWindow) Controller.temporaryInformation
						.get("Workbench"))
						.getActivePage()
						.findView(browserViewName)
						.setFocus();
				((IWorkbenchWindow) Controller.temporaryInformation
						.get("Workbench"))
						.getActivePage()
						.hideView(
								((IWorkbenchWindow) Controller.temporaryInformation
										.get("Workbench"))
										.getActivePage()
										.findView(browserViewName));

			} catch (Exception e) {
				
				try {
					((IWorkbenchWindow) Controller.temporaryInformation
							.get("Workbench"))
							.getActivePage()
							.showView(browserViewName);
					((IWorkbenchWindow) Controller.temporaryInformation
							.get("Workbench"))
							.getActivePage()
							.hideView(
									((IWorkbenchWindow) Controller.temporaryInformation
											.get("Workbench"))
											.getActivePage()
											.findView(browserViewName));
				} catch (NullPointerException | SWTException
						| PartInitException e1) {
					
					MessageBox messageBox2 = new MessageBox(Controller
							.getWindow().getShell(), SWT.ICON_ERROR | SWT.OK);
					messageBox2
							.setMessage("Something was wrong, please try again.");
					messageBox2.setText("SocialCDEforEclipse Message");
					messageBox2.open();

				}

			}
			break;
		case 2:
			
			try {

				if (Controller.getProxy().Authorize(
						Controller.getCurrentUser().Username,
						Controller.getCurrentUserPassword(),
						pinWindow.getService().Id,
						null,
						Controller.temporaryInformation.get("AccessToken")
								.toString(), null)) {
					pinWindow.dispose(Controller.getWindow());
					pinWindow.getService().Registered = true;
					pinWindow.setOauthData(null);

					((IWorkbenchWindow) Controller.temporaryInformation
							.get("Workbench"))
							.getActivePage()
							.findView(firstViewName)
							.setFocus();
					Controller.selectDynamicWindow(0);

					final SettingServicePanel serviceSetting = new SettingServicePanel();
					serviceSetting.setxCoordinate(Controller.getWindow()
							.toDisplay(Controller.getWindow().getLocation().x,
									Controller.getWindow().getLocation().y).x);
					serviceSetting.setyCoordinate(Controller.getWindow()
							.toDisplay(Controller.getWindow().getLocation().x,
									Controller.getWindow().getLocation().y).y);
					serviceSetting.setxCoordinateWithOffset(Controller
							.getWindow().toDisplay(
									Controller.getWindow().getLocation().x,
									Controller.getWindow().getLocation().y).x - 30);
					serviceSetting.setyCoordinateWithOffset(Controller
							.getWindow().toDisplay(
									Controller.getWindow().getLocation().x,
									Controller.getWindow().getLocation().y).y
							+ (Controller.getWindow().getBounds().height - 200)
							/ 2);
					serviceSetting.setSelectAllItems(true);
					serviceSetting.setService(pinWindow.getService());
					serviceSetting.setBtnUnsubscriveListener(new Listener() {

						@Override
						public void handleEvent(Event event) {
							
							MessageBox messageBox = new MessageBox(Controller
									.getWindow().getShell(), SWT.ICON_WARNING
									| SWT.YES | SWT.NO);
							messageBox
									.setMessage("Are you sure you want to unsubscribe?");
							messageBox.setText("SocialCDEforEclipse Message");
							int response = messageBox.open();

							switch (response) {
							case SWT.YES:
								if (!Controller
										.getProxy()
										.DeleteRegistredService(
												Controller.getCurrentUser().Username,
												Controller
														.getCurrentUserPassword(),
												serviceSetting.getService().Id)) {
									MessageBox messageBox2 = new MessageBox(
											Controller.getWindow().getShell(),
											SWT.ICON_ERROR | SWT.OK);
									messageBox2
											.setMessage("Something was wrong, please try again.");
									messageBox2
											.setText("SocialCDEforEclipse Message");
									messageBox2.open();
								}
								break;
							case SWT.NO:
							default:
								break;
							}
							serviceSetting.dispose(null);
							

							SquareButtonService.yCoordinateValue = 5;
							SquareButtonService.counterPosition = 0;

							Controller.selectDynamicWindow(0);

						}
					});

					serviceSetting.setBtnSaveListener(new Listener() {

						@Override
						public void handleEvent(Event event) {
							

							ArrayList<Button> btnCheckbox = serviceSetting
									.getCheckboxCreated();

							int counter = 0;
							for (int i = 0; i < btnCheckbox.size(); i++) {
								if (btnCheckbox.get(i).getSelection()) {
									counter += 1;
								}
							}

							String[] strFeature = new String[counter];
							counter = 0;
							for (int i = 0; i < btnCheckbox.size(); i++) {
								if (btnCheckbox.get(i).getSelection()) {
									strFeature[counter] = btnCheckbox.get(i)
											.getData("FeatureName").toString();
									counter += 1;
								}
							}

							if (Controller.getProxy().UpdateChosenFeatures(
									Controller.getCurrentUser().Username,
									Controller.getCurrentUserPassword(),
									serviceSetting.getService().Id, strFeature)) {
								serviceSetting.dispose(null);
							} else {
								MessageBox messageBox2 = new MessageBox(
										Controller.getWindow().getShell(),
										SWT.ICON_ERROR | SWT.OK);
								messageBox2
										.setMessage("Something was wrong, please try again.");
								messageBox2
										.setText("SocialCDEforEclipse Message");
								messageBox2.open();

							}
						}
					});

					serviceSetting.inizialize(Controller.getWindow());

				} else {
					pinWindow.dispose(Controller.getWindow());
					MessageBox messageBox = new MessageBox(Controller
							.getWindow().getShell(), SWT.ICON_ERROR);
					messageBox.setMessage("Something was wrong, try again");
					messageBox.open();

					System.out.println("Autorizzazione non confermata");
				}
			} catch (Exception e) {

				pinWindow.dispose(Controller.getWindow());

				MessageBox messageBox2 = new MessageBox(Controller.getWindow()
						.getShell(), SWT.ICON_ERROR | SWT.OK);
				messageBox2
						.setMessage("Something was wrong, please try again.");
				messageBox2.setText("SocialCDEforEclipse Message");
				messageBox2.open();

			}

			try {
				((IWorkbenchWindow) Controller.temporaryInformation
						.get("Workbench"))
						.getActivePage()
						.findView(browserViewName);
				((IWorkbenchWindow) Controller.temporaryInformation
						.get("Workbench"))
						.getActivePage()
						.findView(browserViewName)
						.setFocus();
				((IWorkbenchWindow) Controller.temporaryInformation
						.get("Workbench"))
						.getActivePage()
						.hideView(
								((IWorkbenchWindow) Controller.temporaryInformation
										.get("Workbench"))
										.getActivePage()
										.findView(browserViewName));

			} catch (Exception e) {
				
				try {
					((IWorkbenchWindow) Controller.temporaryInformation
							.get("Workbench"))
							.getActivePage()
							.showView(browserViewName);
					((IWorkbenchWindow) Controller.temporaryInformation
							.get("Workbench"))
							.getActivePage()
							.hideView(
									((IWorkbenchWindow) Controller.temporaryInformation
											.get("Workbench"))
											.getActivePage()
											.findView(browserViewName));
				} catch (NullPointerException | SWTException
						| PartInitException e1) {
					
					MessageBox messageBox2 = new MessageBox(Controller
							.getWindow().getShell(), SWT.ICON_ERROR | SWT.OK);
					messageBox2
							.setMessage("Something was wrong, please try again.");
					messageBox2.setText("SocialCDEforEclipse Message");
					messageBox2.open();

				}

			}

			break;
		}
	}
}
