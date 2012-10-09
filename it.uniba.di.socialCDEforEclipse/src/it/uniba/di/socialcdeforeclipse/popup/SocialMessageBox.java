package it.uniba.di.socialcdeforeclipse.popup;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class SocialMessageBox extends Thread{

	private String message; 
	private int icon; 
	
	

	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public int getIcon() {
		return icon;
	}



	public void setIcon(int icon) {
		this.icon = icon;
	}



	@Override
	public void run(){
	 Display display = new Display();
	 Shell	shell = new Shell(display,SWT.NO_REDRAW_RESIZE);
			shell.setSize(300,100);
			MessageBox messageBox = new MessageBox(shell,icon);
    	    messageBox.setMessage(message);
            int buttonID  = messageBox.open();
			
          System.out.println("Selezionato" + buttonID); 
            shell.layout(); 
    		shell.open(); 
    		
    		  while (!shell.isDisposed()) {
    			  if( buttonID == 32)
    	            {
    	            	display.dispose(); 
    	            }
    			  
    			  else if (!display.readAndDispatch()) {
    		        // If no more entries in event queue
    		        display.sleep();
    		      }
    		    }

    		    display.dispose();
            
	}
}
