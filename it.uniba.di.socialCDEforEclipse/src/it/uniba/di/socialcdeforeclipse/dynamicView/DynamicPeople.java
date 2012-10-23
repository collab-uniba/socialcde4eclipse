package it.uniba.di.socialcdeforeclipse.dynamicView;

import it.uniba.di.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.WUser;
import it.uniba.di.socialcdeforeclipse.views.ButtonPerson;
import it.uniba.di.socialcdeforeclipse.views.Panel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

public class DynamicPeople implements Panel{

	private final InputStream PATH_DEFAULT_AVATAR = this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png");
	
	private ArrayList<Control> controlli;
	private Label labelAvatar; 
	
	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Controller.getWindow().getDisplay().getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0,
		image.getBounds().width, image.getBounds().height,
		0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
		}
	
	public Image getImageStream(InputStream stream)
	{
		return  new Image(Controller.getWindow().getDisplay(),stream); 
	}
	
	@Override
	public void inizialize(Composite panel) {
		// TODO Auto-generated method stub
		controlli = new ArrayList<Control>();
		Listener azioni = new ActionGeneral();
		GridLayout layout = new GridLayout(4, true);
		panel.setLayout(layout);
		
		WUser[] suggestion = Controller.getProxy().GetSuggestedFriends(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		
		
		
		ButtonPerson person = new ButtonPerson(panel, SWT.None); 
		person.setxCoordinate(5); 
		person.setyCoordinate(5); 
		person.setWidth(100); 
		person.setHeight(100); 
		person.setText("Prova"); 
		
		controlli.add(labelAvatar); 
	}

	@Override
	public void dispose(Composite panel) {
		// TODO Auto-generated method stub
		for(int i=0; i < controlli.size();i++)
		{
		 controlli.get(i).dispose(); 
			
		}
		panel.setLayout(null); 
	}

	@Override
	public HashMap<String, String> getInput() {
		// TODO Auto-generated method stub
		return null;
	}

}
