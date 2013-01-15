package it.uniba.di.socialcdeforeclipse.views;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.object.ProgressBarThread;
import it.uniba.di.socialcdeforeclipse.object.SquareButtonService;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFilterMatcherDescriptor;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNatureDescriptor;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.window.Window.IExceptionHandler;
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
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.IViewDescriptor;
import org.eclipse.ui.views.IViewRegistry;

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
				if(Controller.temporaryInformation.containsKey("ProgressBarThread"))
				{
				ProgressBarThread thread = (ProgressBarThread) Controller.temporaryInformation.get("ProgressBarThread"); 
				thread.setStop(1);
				}
			}
		});

		Controller.setWindow(parent);
		

		
		Controller.selectWindow(parent);
		
/*
		IViewReference[] reg = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getViewReferences();

		for (int i = 0; i < reg.length; i++) {
			System.out.println("Num " + i + " valore " + reg[i].getTitle()
					+ " id " + reg[i].getId());
		}
		IEditorReference[] editor = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();

		for (int i = 0; i < editor.length; i++) {
			System.out.println("editor " + editor[i].getTitle() + " tooltip "
					+ editor[i].getTitleToolTip());

		}
		IWorkspace work = ResourcesPlugin.getWorkspace(); 
		IProjectNatureDescriptor[] filter=  work.getNatureDescriptors();
		 
		System.out.println("num filtri " + filter.length); 
		
		for(int i=0;i< filter.length;i++ )
		{
			System.out.println(" filtro n. " + filter[i].getLabel());
		}
		
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		// IPath srcPath = ResourceUtils.getJavaSourceLocation(projects[0]);
		
		IResource[] risorseRoot = null;
		try {
			risorseRoot = ResourcesPlugin.getWorkspace().getRoot().members(IContainer.INCLUDE_TEAM_PRIVATE_MEMBERS);
			
		} catch (CoreException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 
		
		for(int i=0;i< risorseRoot.length; i++)
		{
			System.out.println("risorsa principale n. " + i + " val "
					+ risorseRoot[i].getName() + " tipo " + risorseRoot[i].getType());
		}
		
		System.out
				.println("progetto n. "
						+ 0
						+ " val "
						+ projects[0].getName()
						+ " est "
						+ projects[0].getFileExtension()
						+ " tipo "
						+ (projects[0].getType() == org.eclipse.core.resources.IResource.PROJECT)
						+ " open " + projects[0].isOpen());
		IContainer contenuto = projects[0];
		IResource[] risorse = null;
		try {
			risorse = contenuto.members();
		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < risorse.length; i++) {
			System.out.println("risorsa n. " + i + " val "
					+ risorse[i].getName() + " tipo " + risorse[i].getType());
			if (risorse[i].getName().equals(".project")) {
				IFile progetto = projects[0].getFile(risorse[i].getFullPath());

			}

		}
		*/
		/*
		 * IWorkspaceRoot pr = (IWorkspaceRoot) projects[0];
		 * System.out.println("Descrizione " +
		 * pr.getWorkspace().getDescription());
		 */
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		// viewer.getControl().setFocus();
	}
}
