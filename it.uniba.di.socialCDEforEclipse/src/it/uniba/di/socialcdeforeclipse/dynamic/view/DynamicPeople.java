package it.uniba.di.socialcdeforeclipse.dynamic.view;

import it.uniba.di.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.shared.library.WUser;
import it.uniba.di.socialcdeforeclipse.object.ButtonAvatar;
import it.uniba.di.socialcdeforeclipse.views.Panel;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
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
import org.eclipse.ui.progress.PendingUpdateAdapter;

public class DynamicPeople implements Panel {

	private ArrayList<Control> controlli;
	private Label labelsuggestion = null;
	private ArrayList<Composite> userSuggested = null;
	private Label labelFollowings = null;
	private ArrayList<Composite> userFollowings = null;

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

	public Image getImageStream(InputStream stream) {
		return new Image(Controller.getWindow().getDisplay(), stream);
	}

	@Override
	public void inizialize(Composite panel2) {
		// TODO Auto-generated method stub

		/*
		 * final WUser[] hiddenUsers1 =
		 * Controller.getProxy().GetHiddenUsers(Controller
		 * .getCurrentUser().Username, Controller.getCurrentUserPassword());
		 * final WUser[] followers1 =
		 * Controller.getProxy().GetFollowers(Controller
		 * .getCurrentUser().Username, Controller.getCurrentUserPassword());
		 * final WUser[] following1 =
		 * Controller.getProxy().GetFollowings(Controller
		 * .getCurrentUser().Username, Controller.getCurrentUserPassword());
		 * final WUser[] suggestion1 =
		 * Controller.getProxy().GetSuggestedFriends(
		 * Controller.getCurrentUser().Username,
		 * Controller.getCurrentUserPassword());
		 * 
		 * System.out.println("Total link 1 " + (hiddenUsers1.length +
		 * followers1.length + following1.length + suggestion1.length));
		 * 
		 * if( (hiddenUsers1.length + followers1.length + following1.length +
		 * suggestion1.length) > 5 ) { System.out.println(" altezza pre " +
		 * Controller.getWindowHeight());
		 * Controller.setScrollHeight(Controller.getWindowHeight() +
		 * ((hiddenUsers1.length + followers1.length + following1.length +
		 * suggestion1.length) * 70));
		 * Controller.setWindowHeight(Controller.getWindowHeight() +
		 * ((hiddenUsers1.length + followers1.length + following1.length +
		 * suggestion1.length) * 70)); ((ScrolledComposite)
		 * Controller.getWindow(
		 * ).getParent()).setMinSize(Controller.getWindowWidth(),
		 * Controller.getScrollHeight());
		 * Controller.getWindow().getParent().getParent().redraw();
		 * System.out.println(" altezza post " + Controller.getScrollHeight());
		 * } else { Controller.setScrollHeight(Controller.getWindowHeight());
		 * ((ScrolledComposite)
		 * Controller.getWindow().getParent()).setMinSize(Controller
		 * .getWindowWidth(), Controller.getScrollHeight()); }
		 */
		Image imageAvatar;

		GridData grid = new GridData();
		GridData gridData;

		controlli = new ArrayList<Control>();
		final Listener azioni = new ActionGeneral();
		panel2.setLayout(new GridLayout(1, true));

		final ScrolledComposite superUserPostMaster = new ScrolledComposite(
				panel2, SWT.V_SCROLL);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL_HORIZONTAL;
		gridData.heightHint = Controller.getWindowHeight() - 55;
		superUserPostMaster.setLayoutData(gridData);
		controlli.add(superUserPostMaster);

		Composite userPostMaster = new Composite(superUserPostMaster, SWT.None);
		userPostMaster.setBackgroundMode(SWT.INHERIT_DEFAULT);
		superUserPostMaster.setContent(userPostMaster);
		superUserPostMaster.setExpandVertical(true);
		superUserPostMaster.setExpandHorizontal(true);
		superUserPostMaster.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));

		controlli.add(userPostMaster);

		userPostMaster.setLayout(new GridLayout(1, false));
		gridData = new GridData();
		gridData.widthHint = Controller.getWindowWidth() - 50;
		userPostMaster.setLayoutData(gridData);
		userPostMaster.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_WHITE));

		Label labelSuggestionTitle = new Label(userPostMaster, SWT.NONE);
		labelSuggestionTitle.setText("Suggestions:");
		labelSuggestionTitle.setFont(new Font(Controller.getWindow()
				.getDisplay(), "Calibri", 14, SWT.BOLD));
		grid = new GridData();
		grid.horizontalSpan = 3;
		grid.grabExcessHorizontalSpace = true;
		grid.horizontalAlignment = GridData.FILL;
		labelSuggestionTitle.setLayoutData(grid);
		controlli.add(labelSuggestionTitle);

		final WUser[] suggestion = Controller.getProxy().GetSuggestedFriends(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword());
		// WUser[] suggestion =
		// Controller.getProxy().GetFollowings(Controller.getCurrentUser().Username,
		// Controller.getCurrentUserPassword());
		if (suggestion.length == 0) {
			labelsuggestion = new Label(userPostMaster, SWT.WRAP);
			labelsuggestion
					.setText("We have no suggestion for you.\n Please try again soon.");
			grid = new GridData();
			grid.horizontalSpan = 3;
			grid.grabExcessHorizontalSpace = true;
			grid.horizontalAlignment = GridData.FILL;

			labelsuggestion.setLayoutData(grid);
			controlli.add(labelsuggestion);

		} else {

			userSuggested = new ArrayList<Composite>();

			for (int i = 0; i < suggestion.length; i++) {
				final Composite compositeSuggestionUser = new Composite(
						userPostMaster, SWT.None);
				final int j = i;
				Display.getCurrent().syncExec(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						compositeSuggestionUser.setLayout(new GridLayout(5,
								false));
						GridData grid = new GridData();
						grid.grabExcessHorizontalSpace = true;
						grid.horizontalAlignment = GridData.FILL;
						compositeSuggestionUser.setLayoutData(grid);

						compositeSuggestionUser.addListener(SWT.MouseMove,
								new Listener() {

									@Override
									public void handleEvent(Event event) {
										// TODO Auto-generated method stub

										Composite cmp = (Composite) event.widget;
										cmp.setBackground(new Color(Display
												.getCurrent(), 153, 204, 0));
										cmp.setForeground(Display.getCurrent()
												.getSystemColor(
														SWT.COLOR_DARK_BLUE));
										cmp.redraw();
										cmp.layout();
										Controller.getWindow().redraw(
												cmp.getLocation().x,
												cmp.getLocation().y,
												cmp.getSize().x,
												cmp.getSize().y, true);
										Controller.getWindow().layout();

									}
								});

						compositeSuggestionUser.addListener(SWT.MouseExit,
								new Listener() {

									@Override
									public void handleEvent(Event event) {
										// TODO Auto-generated method stub

										Composite cmp = (Composite) event.widget;
										cmp.setBackground(Display
												.getCurrent()
												.getSystemColor(SWT.COLOR_WHITE));
										cmp.setForeground(Display.getCurrent()
												.getSystemColor(
														SWT.COLOR_DARK_BLUE));
										cmp.redraw();
										cmp.layout();
										Controller.getWindow().redraw(
												cmp.getLocation().x,
												cmp.getLocation().y,
												cmp.getSize().x,
												cmp.getSize().y, true);
										Controller.getWindow().layout();
									}
								});
						controlli.add(compositeSuggestionUser);
						userSuggested.add(compositeSuggestionUser);

						Label labelUserImage = new Label(
								compositeSuggestionUser, SWT.None);
						try {
							labelUserImage
									.setImage(resize(
											getImageStream(new URL(
													suggestion[j].Avatar)
													.openStream()), 48, 48));
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							Image imageAvatar = getImageStream(this
									.getClass()
									.getClassLoader()
									.getResourceAsStream(
											"images/DefaultAvatar.png"));
							labelUserImage
									.setImage(resize(imageAvatar, 48, 48));
							imageAvatar.dispose();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							Image imageAvatar = getImageStream(this
									.getClass()
									.getClassLoader()
									.getResourceAsStream(
											"images/DefaultAvatar.png"));
							labelUserImage
									.setImage(resize(imageAvatar, 48, 48));
							imageAvatar.dispose();
						}
						controlli.add(labelUserImage);

						Label labelUserText = new Label(
								compositeSuggestionUser, SWT.None);
						labelUserText.setText(suggestion[j].Username);
						controlli.add(labelUserText);

						Label labelHidden = new Label(compositeSuggestionUser,
								SWT.NONE);
						labelHidden.setText("");
						labelHidden.setVisible(false);
						controlli.add(labelHidden);

						Label labelNext = new Label(compositeSuggestionUser,
								SWT.None);
						labelNext
								.setImage(getImageStream(this
										.getClass()
										.getClassLoader()
										.getResourceAsStream(
												"images/Toolbar/Next.png")));
						grid = new GridData();
						grid.grabExcessHorizontalSpace = true;
						grid.horizontalAlignment = GridData.END;
						labelNext.setLayoutData(grid);
						controlli.add(labelNext);

						labelHidden = new Label(compositeSuggestionUser,
								SWT.NONE);
						labelHidden.setText("");
						labelHidden.setVisible(false);
						controlli.add(labelHidden);

						compositeSuggestionUser.addListener(SWT.MouseDown,
								azioni);
						compositeSuggestionUser.setData("ID_action",
								"User_selected");
						compositeSuggestionUser.setData("User_type",
								"Suggested");
						compositeSuggestionUser.setData("User_data",
								suggestion[j]);
					}
				});

			}

		}

		Label labelFollowingsTitle = new Label(userPostMaster, SWT.NONE);
		labelFollowingsTitle.setText("Followings:");
		labelFollowingsTitle.setFont(new Font(Controller.getWindow()
				.getDisplay(), "Calibri", 14, SWT.BOLD));
		grid = new GridData();
		grid.horizontalSpan = 3;
		grid.grabExcessHorizontalSpace = true;
		grid.horizontalAlignment = GridData.FILL;
		labelFollowingsTitle.setLayoutData(grid);
		controlli.add(labelFollowingsTitle);

		final WUser[] following = Controller.getProxy().GetFollowings(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword());

		if (following.length == 0) {
			labelFollowings = new Label(userPostMaster, SWT.WRAP);
			labelFollowings.setText("You are following no one.");
			grid = new GridData();
			grid.horizontalSpan = 3;
			labelFollowings.setLayoutData(grid);
			controlli.add(labelFollowings);

		} else {
			userFollowings = new ArrayList<Composite>();

			for (int i = 0; i < following.length; i++) {
				final Composite compositeFollowingUser = new Composite(
						userPostMaster, SWT.None);
				final int j = i;
				Display.getCurrent().syncExec(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						compositeFollowingUser.setLayout(new GridLayout(4,
								false));
						GridData grid2 = new GridData();
						grid2.grabExcessHorizontalSpace = true;
						grid2.horizontalAlignment = GridData.FILL;
						compositeFollowingUser.setLayoutData(grid2);

						compositeFollowingUser.addListener(SWT.MouseMove,
								new Listener() {

									@Override
									public void handleEvent(Event event) {
										// TODO Auto-generated method stub

										Composite cmp = (Composite) event.widget;
										cmp.setBackground(new Color(Display
												.getCurrent(), 153, 204, 0));
										cmp.setForeground(Display.getCurrent()
												.getSystemColor(
														SWT.COLOR_DARK_BLUE));
										cmp.redraw();
										cmp.layout();
										Controller.getWindow().redraw(
												cmp.getLocation().x,
												cmp.getLocation().y,
												cmp.getSize().x,
												cmp.getSize().y, true);
										Controller.getWindow().layout();

									}
								});

						compositeFollowingUser.addListener(SWT.MouseExit,
								new Listener() {

									@Override
									public void handleEvent(Event event) {
										// TODO Auto-generated method stub

										Composite cmp = (Composite) event.widget;
										cmp.setBackground(Display
												.getCurrent()
												.getSystemColor(SWT.COLOR_WHITE));
										cmp.setForeground(Display.getCurrent()
												.getSystemColor(
														SWT.COLOR_DARK_BLUE));
										cmp.redraw();
										cmp.layout();
										Controller.getWindow().redraw(
												cmp.getLocation().x,
												cmp.getLocation().y,
												cmp.getSize().x,
												cmp.getSize().y, true);
										Controller.getWindow().layout();
									}
								});
						controlli.add(compositeFollowingUser);
						userFollowings.add(compositeFollowingUser);

						Label labelUserImage = new Label(
								compositeFollowingUser, SWT.None);
						try {
							labelUserImage.setImage(resize(
									getImageStream(new URL(following[j].Avatar)
											.openStream()), 48, 48));
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							Image imageAvatar = getImageStream(this
									.getClass()
									.getClassLoader()
									.getResourceAsStream(
											"images/DefaultAvatar.png"));
							labelUserImage
									.setImage(resize(imageAvatar, 48, 48));
							imageAvatar.dispose();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							Image imageAvatar = getImageStream(this
									.getClass()
									.getClassLoader()
									.getResourceAsStream(
											"images/DefaultAvatar.png"));
							labelUserImage
									.setImage(resize(imageAvatar, 48, 48));
							imageAvatar.dispose();
						}

						controlli.add(labelUserImage);

						Label labelUserText = new Label(compositeFollowingUser,
								SWT.None);
						labelUserText.setText(following[j].Username);
						controlli.add(labelUserText);
						/*
						 * Label labelHidden = new Label(compositeFollowingUser,
						 * SWT.NONE); labelHidden.setText("");
						 * labelHidden.setVisible(false);
						 * controlli.add(labelHidden);
						 */
						Label labelNext = new Label(compositeFollowingUser,
								SWT.None);
						labelNext
								.setImage(getImageStream(this
										.getClass()
										.getClassLoader()
										.getResourceAsStream(
												"images/Toolbar/Next.png")));
						grid2 = new GridData();
						grid2.grabExcessHorizontalSpace = true;
						grid2.horizontalAlignment = GridData.END;
						labelNext.setLayoutData(grid2);
						controlli.add(labelNext);

						compositeFollowingUser.addListener(SWT.MouseDown,
								azioni);
						compositeFollowingUser.setData("ID_action",
								"User_selected");
						compositeFollowingUser
								.setData("User_type", "Following");
						compositeFollowingUser.setData("User_data",
								following[j]);
					}
				});

			}
		}

		Label labelFollowers = new Label(userPostMaster, SWT.NONE);
		labelFollowers.setText("Followers: ");
		labelFollowers.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 14, SWT.BOLD));
		grid = new GridData();
		grid.horizontalSpan = 3;
		grid.grabExcessHorizontalSpace = true;
		grid.horizontalAlignment = GridData.FILL;
		labelFollowers.setLayoutData(grid);
		controlli.add(labelFollowers);

		final WUser[] followers = Controller.getProxy().GetFollowers(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword());

		if (followers.length == 0) {
			Label labelFollowersText = new Label(userPostMaster, SWT.WRAP);
			labelFollowersText.setText("No one is following you.");
			grid = new GridData();
			grid.horizontalSpan = 3;
			labelFollowersText.setLayoutData(grid);
			controlli.add(labelFollowersText);

		} else {

			for (int i = 0; i < followers.length; i++) {

				final Composite compositeFollowersUser = new Composite(
						userPostMaster, SWT.None);
				final int j = i;

				Display.getCurrent().syncExec(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						compositeFollowersUser.setLayout(new GridLayout(4,
								false));
						GridData grid2 = new GridData();
						grid2.grabExcessHorizontalSpace = true;
						grid2.horizontalAlignment = GridData.FILL;
						compositeFollowersUser.setLayoutData(grid2);

						compositeFollowersUser.addListener(SWT.MouseMove,
								new Listener() {

									@Override
									public void handleEvent(Event event) {
										// TODO Auto-generated method stub

										Composite cmp = (Composite) event.widget;
										cmp.setBackground(new Color(Display
												.getCurrent(), 153, 204, 0));
										cmp.setForeground(Display.getCurrent()
												.getSystemColor(
														SWT.COLOR_DARK_BLUE));
										cmp.redraw();
										cmp.layout();
										Controller.getWindow().redraw(
												cmp.getLocation().x,
												cmp.getLocation().y,
												cmp.getSize().x,
												cmp.getSize().y, true);
										Controller.getWindow().layout();

									}
								});

						compositeFollowersUser.addListener(SWT.MouseExit,
								new Listener() {

									@Override
									public void handleEvent(Event event) {
										// TODO Auto-generated method stub

										Composite cmp = (Composite) event.widget;
										cmp.setBackground(Display
												.getCurrent()
												.getSystemColor(SWT.COLOR_WHITE));
										cmp.setForeground(Display.getCurrent()
												.getSystemColor(
														SWT.COLOR_DARK_BLUE));
										cmp.redraw();
										cmp.layout();
										Controller.getWindow().redraw(
												cmp.getLocation().x,
												cmp.getLocation().y,
												cmp.getSize().x,
												cmp.getSize().y, true);
										Controller.getWindow().layout();
									}
								});
						controlli.add(compositeFollowersUser);

						Label labelUserImage = new Label(
								compositeFollowersUser, SWT.None);
						try {
							labelUserImage.setImage(resize(
									getImageStream(new URL(followers[j].Avatar)
											.openStream()), 48, 48));
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							Image imageAvatar = getImageStream(this
									.getClass()
									.getClassLoader()
									.getResourceAsStream(
											"images/DefaultAvatar.png"));
							labelUserImage
									.setImage(resize(imageAvatar, 48, 48));
							imageAvatar.dispose();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							Image imageAvatar = getImageStream(this
									.getClass()
									.getClassLoader()
									.getResourceAsStream(
											"images/DefaultAvatar.png"));
							labelUserImage
									.setImage(resize(imageAvatar, 48, 48));
							imageAvatar.dispose();
						}
						controlli.add(labelUserImage);

						Label labelUserText = new Label(compositeFollowersUser,
								SWT.None);
						labelUserText.setText(followers[j].Username);
						grid2 = new GridData();
						grid2.horizontalAlignment = GridData.FILL;
						labelUserText.setLayoutData(grid2);
						controlli.add(labelUserText);
						/*
						 * Label labelHidden = new Label(compositeFollowersUser,
						 * SWT.NONE); labelHidden.setText("");
						 * labelHidden.setVisible(false);
						 * controlli.add(labelHidden);
						 */
						Label labelNext = new Label(compositeFollowersUser,
								SWT.None);
						labelNext
								.setImage(getImageStream(this
										.getClass()
										.getClassLoader()
										.getResourceAsStream(
												"images/Toolbar/Next.png")));
						grid2 = new GridData();
						grid2.grabExcessHorizontalSpace = true;
						grid2.horizontalAlignment = GridData.END;
						labelNext.setLayoutData(grid2);
						controlli.add(labelNext);

						compositeFollowersUser.addListener(SWT.MouseDown,
								azioni);

						compositeFollowersUser.setData("ID_action",
								"User_selected");
						compositeFollowersUser
								.setData("User_type", "Followers");
						compositeFollowersUser.setData("User_data",
								followers[j]);
					}
				});

			}
		}

		Label labelHidden = new Label(userPostMaster, SWT.NONE);
		labelHidden.setText("Hidden:");
		labelHidden.setFont(new Font(Controller.getWindow().getDisplay(),
				"Calibri", 14, SWT.BOLD));
		grid.horizontalSpan = 3;
		controlli.add(labelHidden);

		final WUser[] hiddenUsers = Controller.getProxy().GetHiddenUsers(
				Controller.getCurrentUser().Username,
				Controller.getCurrentUserPassword());

		if (hiddenUsers.length == 0) {
			Label labelHiddenUsersText = new Label(userPostMaster, SWT.WRAP);
			labelHiddenUsersText.setText("You have hidden no one.");
			grid = new GridData();
			grid.horizontalSpan = 3;
			labelHiddenUsersText.setLayoutData(grid);
			controlli.add(labelHiddenUsersText);

		} else {

			for (int i = 0; i < hiddenUsers.length; i++) {

				final Composite compositeHiddenUser = new Composite(
						userPostMaster, SWT.None);
				final int j = i;
				Display.getCurrent().syncExec(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						compositeHiddenUser.setLayout(new GridLayout(4, false));
						GridData grid2 = new GridData();
						grid2.grabExcessHorizontalSpace = true;
						grid2.horizontalAlignment = GridData.FILL;
						compositeHiddenUser.setLayoutData(grid2);

						compositeHiddenUser.addListener(SWT.MouseMove,
								new Listener() {

									@Override
									public void handleEvent(Event event) {
										// TODO Auto-generated method stub

										Composite cmp = (Composite) event.widget;
										cmp.setBackground(new Color(Display
												.getCurrent(), 153, 204, 0));
										cmp.setForeground(Display.getCurrent()
												.getSystemColor(
														SWT.COLOR_DARK_BLUE));
										cmp.redraw();
										cmp.layout();
										Controller.getWindow().redraw(
												cmp.getLocation().x,
												cmp.getLocation().y,
												cmp.getSize().x,
												cmp.getSize().y, true);
										Controller.getWindow().layout();

									}
								});

						compositeHiddenUser.addListener(SWT.MouseExit,
								new Listener() {

									@Override
									public void handleEvent(Event event) {
										// TODO Auto-generated method stub

										Composite cmp = (Composite) event.widget;
										cmp.setBackground(Display
												.getCurrent()
												.getSystemColor(SWT.COLOR_WHITE));
										cmp.setForeground(Display.getCurrent()
												.getSystemColor(
														SWT.COLOR_DARK_BLUE));
										cmp.redraw();
										cmp.layout();
										Controller.getWindow().redraw(
												cmp.getLocation().x,
												cmp.getLocation().y,
												cmp.getSize().x,
												cmp.getSize().y, true);
										Controller.getWindow().layout();
									}
								});
						controlli.add(compositeHiddenUser);

						Label labelUserImage = new Label(compositeHiddenUser,
								SWT.None);
						try {
							labelUserImage.setImage(resize(
									getImageStream(new URL(
											hiddenUsers[j].Avatar).openStream()),
									48, 48));
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							Image imageAvatar = getImageStream(this
									.getClass()
									.getClassLoader()
									.getResourceAsStream(
											"images/DefaultAvatar.png"));
							labelUserImage
									.setImage(resize(imageAvatar, 48, 48));
							imageAvatar.dispose();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							Image imageAvatar = getImageStream(this
									.getClass()
									.getClassLoader()
									.getResourceAsStream(
											"images/DefaultAvatar.png"));
							labelUserImage
									.setImage(resize(imageAvatar, 48, 48));
							imageAvatar.dispose();
						}
						controlli.add(labelUserImage);

						Label labelUserText = new Label(compositeHiddenUser,
								SWT.None);
						labelUserText.setText(hiddenUsers[j].Username);
						grid2 = new GridData();
						grid2.horizontalAlignment = GridData.FILL;
						labelUserText.setLayoutData(grid2);
						controlli.add(labelUserText);
						/*
						 * Label labelHidden = new Label(compositeFollowersUser,
						 * SWT.NONE); labelHidden.setText("");
						 * labelHidden.setVisible(false);
						 * controlli.add(labelHidden);
						 */
						Label labelNext = new Label(compositeHiddenUser,
								SWT.None);
						labelNext
								.setImage(getImageStream(this
										.getClass()
										.getClassLoader()
										.getResourceAsStream(
												"images/Toolbar/Next.png")));
						grid2 = new GridData();
						grid2.grabExcessHorizontalSpace = true;
						grid2.horizontalAlignment = GridData.END;
						labelNext.setLayoutData(grid2);
						controlli.add(labelNext);

						compositeHiddenUser.addListener(SWT.MouseDown, azioni);

						compositeHiddenUser.setData("ID_action",
								"User_selected");
						compositeHiddenUser.setData("User_type", "Hidden");
						compositeHiddenUser
								.setData("User_data", hiddenUsers[j]);
					}
				});

			}
		}

		superUserPostMaster.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {

				superUserPostMaster.setMinSize(
						Controller.getWindowWidth() - 10,
						100 * (suggestion.length + following.length
								+ followers.length + hiddenUsers.length));

			}
		});
		superUserPostMaster
				.setMinSize(Controller.getWindowWidth() - 10,
						100 * (suggestion.length + following.length
								+ followers.length + hiddenUsers.length));
		Controller.setWindowName("People");

		/*
		 * panel2.pack(); panel2.layout(); panel2.update(); panel2.redraw();
		 */

		Controller.getWindow().redraw();
		Controller.getWindow().pack();
		Controller.getWindow().layout();

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
		// panel.setLayout(null);

	}

	@Override
	public HashMap<String, Object> getData() {
		// TODO Auto-generated method stub
		HashMap<String, Object> uiData = new HashMap<String, Object>();

		if (labelsuggestion != null) {
			uiData.put("labelsuggestionText", labelsuggestion);
		} else {
			uiData.put("userSuggested", userSuggested);
		}

		if (labelFollowings != null) {
			uiData.put("labelFollowings", labelFollowings);
		} else {
			uiData.put("userFollowings", userFollowings);
		}

		return uiData;
	}

}
