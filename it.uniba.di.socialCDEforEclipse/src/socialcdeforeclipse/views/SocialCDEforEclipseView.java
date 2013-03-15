package socialcdeforeclipse.views;

import java.io.File;

import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;
import it.uniba.di.collab.socialcdeforeclipse.object.ProgressBarThread;
import it.uniba.di.collab.socialcdeforeclipse.object.SquareButtonService;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;

public class SocialCDEforEclipseView extends ViewPart {

	IPartListener partListener; 
	Boolean flag;  
	
	public SocialCDEforEclipseView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		flag = false; 
		partListener = new IPartListener() {
			
			@Override
			public void partOpened(IWorkbenchPart part) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void partDeactivated(IWorkbenchPart part) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void partClosed(IWorkbenchPart part) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void partBroughtToTop(IWorkbenchPart part) {
				// TODO Auto-generated method stub
				System.out.println("parte top"  + part.getTitleToolTip());
				System.out.println("id "+ part.getSite().getId());
				if(Controller.getInteractiveTimelineWindow() != null && !part.getTitleToolTip().equals(Controller.getInteractiveTimelineWindow().getFileSelected()))
				{
					Display.getCurrent().asyncExec(new Runnable() {
						public void run() {
							Controller.selectDynamicWindow(6); 
						}
					}); 
					
				}
			}
			
			@Override
			public void partActivated(IWorkbenchPart part) {
				// TODO Auto-generated method stub
				
			}
		};
		
		parent.setBackgroundMode(SWT.INHERIT_DEFAULT);

		Controller.setWindow(parent);

		final PaintListener paintEvent = new PaintListener() {

			public void paintControl(PaintEvent e) {
				
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
					} else {

						Controller.setWindowHeight(Controller.getWindow()
								.getParent().getSize().y);
						Controller.setWindowWidth(Controller.getWindow()
								.getParent().getSize().x);
						Controller.getWindow().setBackground(
								new Color(Display.getCurrent(), 255, 255, 255));

					}

				}

			}
		};
		parent.addMouseMoveListener(new MouseMoveListener() {
			
			@Override
			public void mouseMove(MouseEvent e) {
				// TODO Auto-generated method stub
				if(!flag && PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences().length > 0)
				{
					flag = true; 
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().addPartListener(partListener);
				}
			}
		}); 
		
		
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
				if(Controller.temporaryInformation.containsKey("ProgressBarThread"))
				{
				ProgressBarThread thread = (ProgressBarThread) Controller.temporaryInformation.get("ProgressBarThread"); 
				thread.setStop(1);
				}
			}
		});
		
		
		
		
		
		 
			
		
		System.out.println("sistema " + System.getProperty("os.name").toLowerCase()); 
		Controller.setWindow(parent);
		
		
		Controller.selectWindow(parent);
	
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}