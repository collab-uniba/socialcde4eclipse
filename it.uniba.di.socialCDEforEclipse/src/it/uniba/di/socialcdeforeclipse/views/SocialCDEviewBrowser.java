/**
 * 
 */
package it.uniba.di.socialcdeforeclipse.views;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.shared.library.WService;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * @author Flo
 * 
 */
public class SocialCDEviewBrowser extends ViewPart {

	private Browser browser;
	private WService service;

	public WService getService() {
		return service;
	}

	public void setService(WService service) {
		this.service = service;
	}

	private Browser getBrowser() {
		return browser;
	}

	private void setBrowser(Browser browser) {
		this.browser = browser;
	}

	private final InputStream PATH_WALLPAPER = this.getClass().getClassLoader()
			.getResourceAsStream("images/Wallpaper.png");

	public Image getImageStream(InputStream stream) {
		return new Image(Controller.getWindow().getDisplay(), stream);
	}

	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Controller.getWindow().getDisplay()
				.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, image.getBounds().width,
				image.getBounds().height, 0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	public void createPartControl(final Composite parent) {
		// TODO Auto-generated method stub

		if (!Controller.temporaryInformation.containsKey("CurrentURL")) {
			this.getSite().getPart().dispose();
		}

		parent.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				// TODO Auto-generated method stub

				// sc.setMinSize(parent.getSize().x-100 ,
				// parent.getSize().y-100);
				getBrowser().setSize(
						parent.computeSize(parent.getSize().x,
								parent.getSize().y));

				parent.layout();
				getBrowser().layout();
			}
		});

		GridLayout layout = new GridLayout(1, false);
		parent.setLayout(layout);

		browser = new Browser(parent, SWT.None);
		service = (WService) Controller.temporaryInformation.get("Service");

		try {
			// browser.setUrl("www.google.it");
			browser.setUrl(Controller.temporaryInformation.get("CurrentURL")
					.toString());
		} catch (Exception e) {
			// TODO: handle exception

		}

		browser.addLocationListener(new LocationListener() {

			@Override
			public void changing(LocationEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void changed(LocationEvent event) {
				// TODO Auto-generated method stub

				if (event.location.contains("#")
						&& service.Name.equals("Facebook")) {

					Controller.temporaryInformation
							.put("AccessToken",
									event.location.split("#")[1].toString()
											.split("&")[0].toString()
											.split("=")[1].toString());
				} else if (event.location.contains("?")
						&& service.Name.equals("LinkedIn")) {

					Controller.temporaryInformation.put("AccessToken",
							event.location.split("=")[1].toString());
				} else if (event.location.contains("?")
						&& service.Name.equals("GitHub")) {
					URL query = null;
					try {
						query = new URL(event.location.toString());
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
					}

					Controller.temporaryInformation.put("AccessToken", query
							.toString().split("=")[1]);
				}

			}
		});
		// sc.layout();
		parent.layout();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
