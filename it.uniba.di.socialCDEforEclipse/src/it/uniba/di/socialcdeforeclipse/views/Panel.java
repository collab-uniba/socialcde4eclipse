package it.uniba.di.socialcdeforeclipse.views;

import java.util.HashMap;

import org.eclipse.swt.widgets.Composite;

public interface Panel {
	
	public void inizialize(Composite panel);

	public void dispose(Composite panel);
	
	public HashMap<String, String> getInput(); 
	
	 
}
