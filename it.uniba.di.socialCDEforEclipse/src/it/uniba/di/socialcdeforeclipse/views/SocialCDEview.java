package it.uniba.di.socialcdeforeclipse.views;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;

import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.model.*;
import it.uniba.di.socialcdeforeclipse.popup.PinPanel;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.ViewPart;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class SocialCDEview extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "it.uniba.di.socialcdeforeclipse.views.SocialCDEview";


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
	public void createPartControl(final Composite parent2) {
		
		
		
		final ScrolledComposite scrollComposite = new ScrolledComposite(parent2, SWT.V_SCROLL | SWT.H_SCROLL);
		Composite parent = new Composite(scrollComposite, SWT.NONE);
		scrollComposite.setContent(parent);
		
		
		scrollComposite.setExpandVertical(true);
		scrollComposite.setExpandHorizontal(true);
		
		
		scrollComposite.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle r = scrollComposite.getClientArea();
				scrollComposite.setMinSize(parent2.computeSize(r.width, SWT.DEFAULT));
			}
		});

	
	/*	
		parent.getVerticalBar().setMinimum(0);  
		parent.getHorizontalBar().setMinimum(0); 
		 */
		
		 parent.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		Controller.setWindow(parent); 
		
		 
		
		System.out.println(Controller.getWindow().getSize());
		System.out.println(Controller.getWindow().getShell().getSize());
		System.out.println(Controller.getWindow().toDisplay(Controller.getWindow().getShell().getSize()));

		final PaintListener paintEvent = new PaintListener() {

		
			
			
			public void paintControl(PaintEvent e) {
				
				System.out.println("Evento paint attivato");
				System.out.println("Dimensioni window " +  Controller.getWindow().getSize());
				/*
				System.out.println("Dimensioni shell " + Controller.getWindow().getShell().getSize());
				System.out.println("Dimensioni window parent " +  Controller.getWindow().getParent().getSize());
				System.out.println("Dimensioni window height impostate "+ Controller.getWindowHeight()); 
				*/		
				SquareButtonService.yCoordinateValue = 5; 
				SquareButtonService.counterPosition = 0; 
				
				
				if (Controller.getWindowHeight() != Controller.getWindow().getParent().getSize().y || Controller.getWindowWidth() != Controller.getWindow().getParent().getSize().x ) {
					
				System.out.println("Confronto altezza (" + Controller.getWindowHeight() + " con " + Controller.getWindow().getParent().getSize().y + ") e larghezza (" + Controller.getWindowWidth() + " con " + Controller.getWindow().getParent().getSize().x + ")" );	
					
					if (Controller.getWindow().getParent().getSize().x == 0	&& Controller.getWindow().getParent().getSize().y == 0) {
						
						Controller.getWindow().setBackground(new Color(Display.getCurrent(),249,250,237));
						//Controller.getWindow().setBackgroundImage(	 getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/Wallpaper.png")));
					} else {
						System.out.println("Inizio "	+ Controller.getWindow().getParent().getSize());
						Controller.setWindowHeight(Controller.getWindow().getParent().getSize().y);
						Controller.setWindowWidth(Controller.getWindow().getParent().getSize().x);
						Controller.getWindow().setBackground(new Color(Display.getCurrent(),249,250,237));
						/*
						Controller.getWindow().setBackgroundImage(
								resize( getImageStream(this.getClass().getClassLoader()
										.getResourceAsStream("images/Wallpaper.png")),
										Controller.getWindowWidth(),
										Controller.getWindowHeight()));
						*/
						/*
						try {
							this.getClass().getClassLoader()
							.getResourceAsStream("images/Wallpaper.png").close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					*/
						
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
				if(Controller.getPreferences("Autologin").equals("True"))
				{
					Controller.setPreferences("FlagAutologin", "True"); 
				}
				else
				{
					Controller.setPreferences("FlagAutologin", "False");
				}
				System.out.println("Finestra chiusa"); 
			}
		});
		
		//Controller.setPreferences("Autologin", "");
		Controller.setWindow(parent);
		Controller.selectWindow(parent);

		// Controller.getWindow().removePaintListener(paintEvent);

		/*
		 * 
		 * Proxy = new ProxyWrapper(); URI[] servizi =
		 * Proxy.GetAvailableAvatars("floriano", "pamela2781983"); String s =
		 * "ciao";
		 * 
		 * Preferences prefs = Preferences.userRoot().node(
		 * this.getClass().getName()); String ID1 = "Test1"; String ID2 =
		 * "Test2"; String ID3 = "Test3";
		 */

		// First we will get the values
		// Define a boolean value
		/*
		 * System.out.println(prefs.getBoolean(ID1, true)); // Define a string
		 * with default "Hello World System.out.println(prefs.get(ID2,
		 * "Hello World")); // Define a integer with default 50
		 * System.out.println(prefs.getInt(ID3, 50));
		 */
		// Now set the values
		/*
		 * prefs.putBoolean(ID1, false); prefs.put(ID2, "Hello Europa");
		 * prefs.putInt(ID3, 45);
		 * 
		 * // Delete the preference settings for the first value
		 * prefs.remove(ID1);
		 */
		// System.out.println(prefs.get(ID2, ""));
		// This happens in the method which creates the user interface
		// parent is the composite which is available
		/*
		 * GridLayout layout = new GridLayout(2, false);
		 * parent.setLayout(layout); Label label = new Label(parent, SWT.NONE);
		 * 
		 * 
		 * 
		 * label.setText("Select a person:" ); IPreferencesService service =
		 * Platform.getPreferencesService();
		 * 
		 * boolean value = service.getBoolean(this.getSite().getPluginId(),
		 * "MyPreference", true, null);
		 * 
		 * final ComboViewer viewer = new ComboViewer(parent, SWT.READ_ONLY); //
		 * ArrayContentProvider does not store state, // therefore you can
		 * re-use instances
		 * viewer.setContentProvider(ArrayContentProvider.getInstance());
		 * 
		 * viewer.setLabelProvider(new LabelProvider() {
		 * 
		 * 
		 * @Override public String getText(Object element) { if (element
		 * instanceof Persons) { Persons person = (Persons) element; return
		 * person.getFirstName(); } return super.getText(element); } });
		 */
		/*
		 * Persons[] persons = new Persons[] { new Persons("Lars", "Vogel"), new
		 * Persons("Tim", "Taler"), new Persons("Jim", "Knopf") };
		 */
		// Set set the input to the Viewer.
		// This input will be send to the
		// content provider
		/*
		 * viewer.setInput(persons); // React to the selection of the viewer //
		 * Note that the viewer return the real object and not just a string //
		 * representation viewer.addSelectionChangedListener(new
		 * ISelectionChangedListener() {
		 * 
		 * @Override public void selectionChanged(SelectionChangedEvent event) {
		 * IStructuredSelection selection = (IStructuredSelection)
		 * event.getSelection(); } });
		 */
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {

		// viewer.getControl().setFocus();
	}
}