package it.uniba.di.socialcdeforeclipse.dynamicView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.uniba.di.socialcdeforeclipse.controller.Controller;

import org.eclipse.swt.widgets.Text;

public class InterceptingFilter {

	public static boolean verifyText(Text txt)
	{
		return (txt.getText() == "" ? false : true); 
		
	}
	
	public static boolean verifyMail(String mail)
	{
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(mail);
		return  m.matches();
	}
	
	public static boolean verifyRegistrationPanel()
	{
		String alert = "";
		
		if(verifyText(Controller.getRegistrationPanel().getTxtUsername()))
		{
			alert = (Controller.isUsernameAvailable(Controller.getRegistrationPanel().getTxtUsername().getText()) ? "" : "Username not available. Please choose another one."); 
		}
		else
		{
			alert = "Please enter an username valid!";
		}
		
		if((!verifyText(Controller.getRegistrationPanel().getTxtPassword())) && alert == "")
		{
			alert = "Please enter a password valid!"; 
		}
		
		if((!verifyText(Controller.getRegistrationPanel().getTxtPassword2())) && alert == "")
		{
			alert = "Please rewrite the password in the appropriate field!"; 
		}
		else if(verifyText(Controller.getRegistrationPanel().getTxtPassword2()) && verifyText(Controller.getRegistrationPanel().getTxtPassword()) && !Controller.getRegistrationPanel().getTxtPassword2().getText().equals(Controller.getRegistrationPanel().getTxtPassword().getText()))
		{
			alert = "Passwords do not match!"; 
		}
			
		if((!verifyMail(Controller.getRegistrationPanel().getTxtMail().getText()))&& alert == "")
		{
			alert = "Please enter an email valid!"; 
		}
		
		if((!verifyText(Controller.getRegistrationPanel().getTxtInvitationCode())) && alert == "")
		{
			alert = "Please enter an invitation code valid!";
		}
		
		
		return (alert == "" ? true : false); 
		
		
	}
	
	
}
