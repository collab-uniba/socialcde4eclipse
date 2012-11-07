package it.uniba.di.socialcdeforeclipse.views;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

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
		String myUrl = "http:\\/\\/www.example.com\\/example";
		String  tmpString =  myUrl.replace("/","");
		 

		System.out.println( "Original = " + myUrl );
		System.out.println( "Result   = " + tmpString );
		
		final ScrolledComposite scrollComposite = new ScrolledComposite(parent2, SWT.V_SCROLL | SWT.H_SCROLL);
		Composite parent = new Composite(scrollComposite, SWT.NONE);
		scrollComposite.setContent(parent);
		
		
		scrollComposite.setExpandVertical(true);
		scrollComposite.setExpandHorizontal(true);
		
		
		scrollComposite.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle r = scrollComposite.getClientArea();
				System.out.println("Dimensioni scrollheight "); 
				
				if(Controller.getWindowName().equals("Login") || Controller.getWindowName().equals("Registration"))
				{
					scrollComposite.setMinSize(Controller.getWindowWidth(),Controller.getWindowHeight());
				}
				else
				{
					System.out.println("Pannello dinamico, dimensioni larghezza " + Controller.getWindowWidth() + " altezza " + ( Controller.getProfilePanel().getComposite_static().getSize().y + Controller.getProfilePanel().getComposite_dinamic().getSize().y)); 
					scrollComposite.setMinSize(Controller.getWindowWidth(),Controller.getScrollHeight()  );
				}
				
				
				
				
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
						
						Controller.getWindow().setBackground(new Color(Display.getCurrent(),255,255,255));
						//Controller.getWindow().setBackgroundImage(	 getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/Wallpaper.png")));
					} else {
						System.out.println("Inizio "	+ Controller.getWindow().getParent().getSize());
						Controller.setWindowHeight(Controller.getWindow().getParent().getSize().y);
						Controller.setWindowWidth(Controller.getWindow().getParent().getSize().x);
						Controller.getWindow().setBackground(new Color(Display.getCurrent(),255,255,255));
						
						
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