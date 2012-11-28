package it.uniba.di.socialcdeforeclipse.views;

import java.io.InputStream;

import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.object.SquareButtonService;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;

public class SocialCDEview extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "it.uniba.di.socialcdeforeclipse.views.SocialCDEview";
	static int j = 0;

	/*
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
	public Image getImageStream(InputStream stream) {
		return new Image(Display.getCurrent(), stream);

	}

	/**
	 * The constructor.
	 */
	public SocialCDEview() {

	}

	public void dispose() {

	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(final Composite parent) {

		

		parent.setBackgroundMode(SWT.INHERIT_DEFAULT);

		Controller.setWindow(parent);

		final PaintListener paintEvent = new PaintListener() {

			public void paintControl(PaintEvent e) {
				/*
				 * System.out.println("Dimensioni shell " +
				 * Controller.getWindow().getShell().getSize());
				 * System.out.println("Dimensioni window parent " +
				 * Controller.getWindow().getParent().getSize());
				 * System.out.println("Dimensioni window height impostate "+
				 * Controller.getWindowHeight());
				 */
				SquareButtonService.yCoordinateValue = 5;
				SquareButtonService.counterPosition = 0;

				Controller.setProgressBarPositionX(Controller.getWindow()
						.toDisplay(Controller.getWindow().getLocation().x,
								Controller.getWindow().getLocation().y).x);
				Controller.setProgressBarPositionY(Controller.getWindow()
						.toDisplay(Controller.getWindow().getLocation().x,
								Controller.getWindow().getLocation().y).y);
				
				if (Controller.getWindowHeight() != Controller.getWindow()
						.getParent().getSize().y
						|| Controller.getWindowWidth() != Controller
								.getWindow().getParent().getSize().x) {

					if (Controller.getWindow().getParent().getSize().x == 0
							&& Controller.getWindow().getParent().getSize().y == 0) {

						Controller.getWindow().setBackground(
								new Color(Display.getCurrent(), 255, 255, 255));
						// Controller.getWindow().setBackgroundImage(
						// getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/Wallpaper.png")));
					} else {

						Controller.setWindowHeight(Controller.getWindow()
								.getParent().getSize().y);
						Controller.setWindowWidth(Controller.getWindow()
								.getParent().getSize().x);
						Controller.getWindow().setBackground(
								new Color(Display.getCurrent(), 255, 255, 255));

					}

					// TODO Auto-generated method stub
					// Controller.getWindow().setBackgroundImage(resize(getImageStream(PATH_WALLPAPER),100,100));
				}

			}
		};

		parent.addPaintListener(paintEvent);
		parent.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				// TODO Auto-generated method stub
				if (Controller.getPreferences("Autologin").equals("True")) {
					Controller.setPreferences("FlagAutologin", "True");
				} else {
					Controller.setPreferences("FlagAutologin", "False");
				}

			}
		});

		Controller.setWindow(parent);
		Controller.selectWindow(parent);

	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {

		// viewer.getControl().setFocus();
	}
}