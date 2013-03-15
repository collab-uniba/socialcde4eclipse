package it.uniba.di.collab.socialcdeforeclipse.action;

import it.uniba.di.collab.socialcdeforeclipse.controller.Controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Widget;

public class ActionSettingPanel {
	public ActionSettingPanel(Widget widget, Event event) {

		String widgetName = widget.getData("ID_action").toString();

		switch (widgetName) {

		case "SettingBtnOk":
			if (event.type == SWT.Selection && Controller.getProxy().IsWebServiceRunning()) {

				if (!Controller.getCurrentUserPassword().equals(
						Controller.getSettingWindow().getTxtOldPassword()
								.getText())) {

					Controller.getSettingWindow().getLabelAlert()
							.setVisible(true);
					Controller
							.getSettingWindow()
							.getLabelAlert()
							.setText(
									"Old passord not match with current password");
					

				} else {

					MessageBox messageBoxWarning = new MessageBox(Controller
							.getWindow().getShell(), SWT.ICON_WARNING | SWT.YES
							| SWT.NO);

					messageBoxWarning.setText("Warning");
					messageBoxWarning
							.setMessage("Are you sure to change user password?");
					int buttonID = messageBoxWarning.open();
					switch (buttonID) {
					case SWT.YES:
						if (Controller.getProxy().ChangePassword(
								Controller.getCurrentUser().Username,
								Controller.getCurrentUserPassword(),
								Controller.getSettingWindow()
										.getTxtNewPassword().getText())) {

							MessageBox messageBox_ok = new MessageBox(
									Controller.getWindow().getShell(),
									SWT.ICON_INFORMATION | SWT.OK);
							messageBox_ok.setText("Information");
							messageBox_ok
									.setMessage("The new password is set up!");
							int response = messageBox_ok.open();

							if (response == SWT.OK) {

								Controller.setCurrentUserPassword(Controller
										.getSettingWindow().getTxtNewPassword()
										.getText());

								Controller.selectDynamicWindow(0);
								Controller.getProfilePanel()
										.getComposite_dinamic().layout();
							}

						}
						else
						{
							Controller.openConnectionLostPanel("Connection Lost! \n You will be redirected to Login page."); 	
						}
						break;
					default:
					case SWT.NO:
						MessageBox messageBox_no = new MessageBox(Controller
								.getWindow().getShell(), SWT.ICON_INFORMATION
								| SWT.YES);
						messageBox_no.setText("Information");
						messageBox_no.setMessage("Operation aborted!");
						messageBox_no.open();
						break;

					}

				}
			}

			break;

		case "SettingBtnCancel":
			if (event.type == SWT.Selection && Controller.getProxy().IsWebServiceRunning()) {
				Controller.getSettingWindow().getBtnOk().redraw();
				Controller.selectDynamicWindow(0);
				Controller.getProfilePanel().getComposite_dinamic().layout();
			}
			else
			{
				Controller.openConnectionLostPanel("Connection Lost! \n You will be redirected to Login page."); 	
			}

			break;

		default:
			break;
		}
	}
}