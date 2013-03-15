package socialcdeforeclipse.views;



import it.uniba.di.collab.socialcdeforeclipse.action.InterceptingFilter;
import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;



import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import socialcdeforeclipse.Activator;

public class UserPreferences extends PreferencePage implements
		IWorkbenchPreferencePage {
		
		 
		private Text textUsername; 
		private Text textPassword; 
		private Text textProxyHost; 
		private Button btnAutologin; 
		private Button btnSavePassword; 
		final String prefix = "SocialCDE";  
		
	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Control createContents(Composite parent) {
		// TODO Auto-generated method stub
		GridData gridData; 
		
		Composite masterComposite = new Composite(parent, SWT.None); 
		masterComposite.setLayout(new GridLayout(2, false)); 
		
		Label labelProxyHost = new Label(masterComposite, SWT.None); 
		labelProxyHost.setText("Default proxy host: "); 
		gridData = new GridData(); 
		gridData.verticalSpan = 2; 
		labelProxyHost.setLayoutData(gridData); 
		
		textProxyHost = new Text(masterComposite, SWT.BORDER); 
		gridData = new GridData(); 
		gridData.grabExcessHorizontalSpace = true; 
		gridData.verticalSpan = 2; 
		gridData.widthHint = 170; 
		textProxyHost.setLayoutData(gridData); 
		
		if(Activator.getDefault().getPreferenceStore().contains(prefix + "proxyHost"))
		{
			textProxyHost.setText( Activator.getDefault().getPreferenceStore().getString(prefix + "proxyHost")); 
		}
		
		Label labelUsername = new Label(masterComposite, SWT.None); 
		labelUsername.setText("Default username: ");
		gridData = new GridData(); 
		gridData.verticalSpan = 2; 
		labelUsername.setLayoutData(gridData); 
		
		textUsername = new Text(masterComposite, SWT.BORDER);
		gridData = new GridData(); 
		gridData.grabExcessHorizontalSpace = true; 
		gridData.verticalSpan = 2; 
		gridData.widthHint = 170; 
		textUsername.setLayoutData(gridData); 
		
		if(Activator.getDefault().getPreferenceStore().contains(prefix + "username"))
		{
			textUsername.setText(Activator.getDefault().getPreferenceStore().getString(prefix + "username")); 
		}
		
		Label labelPassword = new Label(masterComposite, SWT.None);
		gridData = new GridData(); 
		gridData.verticalSpan = 2; 
		labelPassword.setLayoutData(gridData); 
		labelPassword.setText("Default password: "); 
		
		textPassword = new Text(masterComposite, SWT.PASSWORD | SWT.BORDER); 
		gridData = new GridData(); 
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalSpan = 2; 
		gridData.widthHint = 170;
		textPassword.setLayoutData(gridData); 
		
		if(Activator.getDefault().getPreferenceStore().contains(prefix + "password"))
		{
			textPassword.setText(Activator.getDefault().getPreferenceStore().getString(prefix + "password")); 
		}
		
		btnAutologin = new Button(masterComposite, SWT.CHECK); 
		gridData = new GridData(); 
		gridData.horizontalSpan  = 2;
		btnAutologin.setLayoutData(gridData); 
		btnAutologin.setText("Autologin"); 
		
		if(Activator.getDefault().getPreferenceStore().contains(prefix + "autoLogin"))
		{
			if(Activator.getDefault().getPreferenceStore().getString(prefix + "autoLogin").equals("true"))
			{
				btnAutologin.setSelection(true);
			}
			else
			{
				btnAutologin.setSelection(false); 
			}
		}
		
		btnSavePassword = new Button(masterComposite, SWT.CHECK); 
		gridData = new GridData(); 
		gridData.horizontalSpan  = 2;
		btnSavePassword.setLayoutData(gridData); 
		btnSavePassword.setText("Save password"); 
		
		if(Activator.getDefault().getPreferenceStore().contains(prefix + "savePassword"))
		{
			if(Activator.getDefault().getPreferenceStore().getString(prefix + "savePassword").equals("true"))
			{
				btnSavePassword.setSelection(true);
			}
			else
			{
				btnSavePassword.setSelection(false); 
			}
		}
		
		return masterComposite;
	}
	
	@Override
	public void performDefaults() 
	{
		textUsername.setText(""); 
		textPassword.setText(""); 
		btnAutologin.setSelection(false); 
		btnSavePassword.setSelection(false); 
		
	}
	
	@Override
	public void performApply()
	{
		performOk(); 
	}
	
	@Override
	public boolean performOk() 
	{
		Boolean result = true; 
	
		
		if(!InterceptingFilter.verifyText(textUsername.getText()))
		{
			result = false;
			MessageBox messageBox2 = new MessageBox(Controller
					.getWindow().getShell(), SWT.ICON_ERROR | SWT.OK);
			messageBox2
					.setMessage("Please compile all field correctly.");
			messageBox2.setText("SocialCDEforEclipse Message");
			messageBox2.open();
		}
		else if(!InterceptingFilter.verifyText(textPassword.getText()))
		{
			result = false; 
			MessageBox messageBox2 = new MessageBox(Controller
					.getWindow().getShell(), SWT.ICON_ERROR | SWT.OK);
			messageBox2
					.setMessage("Please compile all field correctly.");
			messageBox2.setText("SocialCDEforEclipse Message");
			messageBox2.open();
		}
		else if(!InterceptingFilter.verifyText(textProxyHost.getText()))
		{
			result = false;
			MessageBox messageBox2 = new MessageBox(Controller
					.getWindow().getShell(), SWT.ICON_ERROR | SWT.OK);
			messageBox2
					.setMessage("Please compile all field correctly.");
			messageBox2.setText("SocialCDEforEclipse Message");
			messageBox2.open();
		}
		else
		{
			Activator.getDefault().getPreferenceStore().setValue(prefix + "username", textUsername.getText()); 
			Activator.getDefault().getPreferenceStore().setValue(prefix + "password", textPassword.getText()); 
			Activator.getDefault().getPreferenceStore().setValue(prefix + "proxyHost", textProxyHost.getText()); 
			
			if(btnAutologin.getSelection())
			{
				Activator.getDefault().getPreferenceStore().setValue(prefix + "autoLogin", "true"); 
			}
			else
			{
				Activator.getDefault().getPreferenceStore().setValue(prefix + "autoLogin", "false");
			}
			
			if(btnSavePassword.getSelection())
			{
				Activator.getDefault().getPreferenceStore().setValue(prefix + "savePassword", "true"); 
			}
			else
			{
				Activator.getDefault().getPreferenceStore().setValue(prefix + "savePassword", "false");
			}
			
		}
		return result;
		
	}

}
