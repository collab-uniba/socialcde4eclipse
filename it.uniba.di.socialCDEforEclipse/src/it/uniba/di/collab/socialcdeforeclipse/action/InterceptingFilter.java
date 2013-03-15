package it.uniba.di.collab.socialcdeforeclipse.action;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InterceptingFilter {

	public static boolean verifyText(String txt) {
		return (txt == "" ? false : true);

	}

	public static boolean verifyMail(String mail) {
		if (mail.startsWith(".") || mail.startsWith("@")) {
			return false;
		} else {
			Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
			Matcher m = p.matcher(mail);
			return m.matches();
		}
	}

	public static boolean verifyRegistrationPanel(HashMap<String, String> values) {
		Boolean flag = true;

		if (!verifyText(values.get("Password1"))) {
			flag = false;
		}

		if (!verifyText(values.get("Username")) && flag) {
			flag = false;
		}

		if ((!verifyText(values.get("Password2"))) && flag) {
			flag = false;
		} else if (verifyText(values.get("Password1"))
				&& verifyText(values.get("Password2"))
				&& !(values.get("Password2").equals(values.get("Password1")))) {
			flag = false;
		}

		if ((!verifyMail(values.get("Email"))) && flag) {
			flag = false;
		}

		if ((!verifyText(values.get("InvitationCode"))) && flag) {
			flag = false;
		}

		return flag;

	}

}
