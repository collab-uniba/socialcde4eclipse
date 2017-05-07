package it.uniba.di.collab.socialcdeforeclipse.model;

import it.uniba.di.collab.socialcdeforeclipse.shared.library.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Fauzzi Floriano
 * 
 */
public class ProxyWrapper implements ISocialProxy {

	private static String host = null;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		ProxyWrapper.host = host + "/SocialTFSProxy.svc";

	}

	private int countOccurrences(String haystack, char needle) {
		int count = 0;
		for (int i = 0; i < haystack.length(); i++) {
			if (haystack.charAt(i) == needle) {
				count++;
			}
		}

		if (count == 0) {
			count += 1;
		}

		return count;
	}

	public boolean IsAvailable(String username) {

		String output = "";
		try {

			URL url = new URL(host + "/IsAvailable?username=" + username);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				output = br.readLine();

			}
		} catch (Exception e) {

			return false;
		}

		if (username.equals("") || username.equals(" ")) {
			return false;
		}

		if (output.equals("true")) {

			return true;

		} else {

			return false;
		}

	}

	public Boolean IsWebServiceRunning() {
		
		String output = "";

		try {

			URL url = new URL(host + "/IsWebServiceRunning");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				output = br.readLine();
			}
			
		} catch (Exception e) {

			return false;
		}
		if (output != null && output.equals("true")) {
			
			return true;
		} else {
			
			return false;
		}
		

	}

	public int SubscribeUser(String email, String password, String username) {
		String result = "";
		try {
			URL url = new URL(host + "/SubscribeUser");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"email\":\"" + email + "\" ,\"username\":\""
					+ username + "\", \"password\":\"" + password + "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;

				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

			}

			conn.disconnect();
		} catch (Exception e) {
			return -1;

		}

		return Integer.parseInt(result);

	}

	public Boolean UpdateChosenFeatures(String username, String password,
			int serviceInstanceId, String[] chosenFeatures) {
		String result = "";
		String features = "[";
		if (!(chosenFeatures == null | chosenFeatures.length == 0)) {
			for (int i = 0; i < chosenFeatures.length; i++) {
				if (i == chosenFeatures.length - 1) {
					features += " \"" + chosenFeatures[i] + "\" ";
				} else {
					features += " \"" + chosenFeatures[i] + "\" ,";
				}
			}
		}
		features += "]";

		try {
			URL url = new URL(host + "/UpdateChosenFeatures");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\" , \"serviceInstanceId\":\""
					+ serviceInstanceId + "\" , \"chosenFeatures\" : "
					+ features + "}");
			
			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;

				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

			}

			conn.disconnect();
		} catch (Exception e) {
			return false;

		}

		if (result.equals("true")) {

			return true;
		} else {
			return false;
		}

	}

	public boolean ChangePassword(String username, String oldPassword,
			String newPassword) {
		String result = "";
		try {
			URL url = new URL(host + "/ChangePassword");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username
					+ "\", \"oldPassword\":\"" + oldPassword
					+ "\" , \"newPassword\":\"" + newPassword + "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;

				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

			}

			conn.disconnect();
		} catch (Exception e) {
			return false;

		}

		if (result.equals("true")) {

			return true;
		} else {
			return false;
		}

	}

	public WService[] GetServices(String username, String password) {
		WService[] wservice;
		wservice = new WService[2];

		try {
			URL url = new URL(host + "/GetServices");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;
				String result = "";
				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

				wservice = new WService[countOccurrences(result, '{')];
				Gson gson = new Gson();
				wservice = gson.fromJson(result, WService[].class);
			}

			conn.disconnect();
		} catch (Exception e) {
			wservice = null;

		}

		return wservice;

	}

	public WUser GetUser(String username, String password) {
		WUser wuser = new WUser();

		try {
			URL url = new URL(host + "/GetUser");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;
				String result = "";
				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();
				
				Gson gson = new Gson();
				wuser = gson.fromJson(result, WUser.class);
			}

			conn.disconnect();
		} catch (Exception e) {
			wuser = null;

		}

		return wuser;

	}

	public WUser GetColleagueProfile(String username, String password,
			int colleagueId) {
		WUser wuser = new WUser();

		try {
			URL url = new URL(host + "/GetColleagueProfile");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\" , \"colleagueId\":\"" + colleagueId
					+ "\" }");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;
				String result = "";
				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

				Gson gson = new Gson();
				wuser = gson.fromJson(result, WUser.class);
			}

			conn.disconnect();
		} catch (Exception e) {
			wuser = null;

		}

		return wuser;

	}

	public WOAuthData GetOAuthData(String username, String password, int service) {
		WOAuthData woutAuthData = new WOAuthData();

		try {
			URL url = new URL(host + "/GetOAuth1Data");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\" , \"service\":\"" + service + "\" }");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;
				String result = "";
				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

				Gson gson = new Gson();
				woutAuthData = gson.fromJson(result, WOAuthData.class);
			}

			conn.disconnect();
		} catch (Exception e) {
			woutAuthData = null;

		}

		return woutAuthData;

	}

	/**
	 * Finish the OAuth version 1 authentication procedure
	 * <p>
	 * It can be accessed by a POST request
	 * "&lt;ServiceHost&gt;/AuthorizeOAuth1"
	 * 
	 * @param username
	 *            Name that identifies the user.
	 * @param password
	 *            Password to check user identity.
	 * @param service
	 *            Identifier of the service.
	 * @param verifier
	 *            Verifier pin provided by the service.
	 * @param accessToken
	 *            Access Token for the service instance.
	 * @param accessSecret
	 *            Access Secret for the service instance.
	 * @return True if the authentication is successful, false otherwise.
	 * */
	public boolean Authorize(String username, String password, int service,
			String verifier, String accessToken, String accessSecret) {

		String result = "";
		try {
			URL url = new URL(host + "/Authorize");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\" , \"service\":\"" + service
					+ "\", \"verifier\":\"" + verifier
					+ "\", \"accessToken\": \"" + accessToken
					+ "\", \"accessSecret\":\"" + accessSecret + "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;

				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

			}

			conn.disconnect();
		} catch (Exception e) {
			return false;

		}

		if (result.equals("true")) {

			return true;
		} else {
			return false;
		}

	}

	public boolean RecordService(String username, String password, int service,
			String usernameOnService, String passwordOnService, String domain) {
		String result = "";

		try {
			URL url = new URL(host + "/RecordService");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\" , \"service\":\"" + service
					+ "\", \"usernameOnService\":\"" + usernameOnService
					+ "\", \"passwordOnService\": \"" + passwordOnService
					+ "\", \"domain\":\"" + domain + "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;

				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

			}

			conn.disconnect();
		} catch (Exception e) {
			return false;

		}

		if (result.equals("true")) {

			return true;
		} else {
			return false;
		}
	}

	public boolean DeleteRegistredService(String username, String password,
			int service) {
		String result = "";

		try {
			URL url = new URL(host + "/DeleteRegistredService");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\" , \"service\":\"" + service + "\" }");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;

				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

			}

			conn.disconnect();
		} catch (Exception e) {
			return false;

		}

		if (result.equals("true")) {

			return true;
		} else {
			return false;
		}

	}

	public WPost[] GetHomeTimeline(String username, String password) {
		return GetHomeTimeline(username, password, 0, 0);

	}

	public WPost[] GetHomeTimeline(String username, String password,
			long since, long to) {
		WPost[] wpost;
		wpost = new WPost[2];

		try {
			URL url = new URL(host + "/GetHomeTimeline");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\" , \"since\":\"" + since + "\" , \"to\":\""
					+ to + "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream(), "UTF-8");
				BufferedReader br = new BufferedReader(in);
				String output;
				String result = "";
				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

				if (result.equals("[]")) {
					wpost = new WPost[0];
				} else {
					int num = countOccurrences(result, '{');
					wpost = new WPost[countOccurrences(result, '{')];
					Gson gson = new GsonBuilder().registerTypeAdapter(
							Calendar.class, new JsonDateDeserializer())
							.create();
					wpost = gson.fromJson(result, WPost[].class);
				}

			} else {
				wpost = new WPost[0];
			}

			conn.disconnect();
		} catch (Exception e) {
			wpost = new WPost[0];

		}

		return wpost;
	}

	public WPost[] GetUserTimeline(String username, String password,
			String ownerName) {
		return GetUserTimeline(username, password, ownerName, 0, 0);
	}

	public WPost[] GetUserTimeline(String username, String password,
			String ownerName, long since, long to) {
		WPost[] wpost;
		wpost = new WPost[2];

		try {
			URL url = new URL(host + "/GetUserTimeline");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\" , \"ownerName\":\"" + ownerName
					+ "\" , \"since\":\"" + since + "\" , \"to\":\"" + to
					+ "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream(), "UTF-8");
				BufferedReader br = new BufferedReader(in);
				String output;
				String result = "";
				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

				wpost = new WPost[countOccurrences(result, '{')];
				Gson gson = new GsonBuilder().registerTypeAdapter(
						Calendar.class, new JsonDateDeserializer()).create();
				wpost = gson.fromJson(result, WPost[].class);
			} else {
				wpost = new WPost[0];
			}

			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			wpost = new WPost[0];

		}

		return wpost;

	}

	public WPost[] GetIterationTimeline(String username, String password) {
		return GetIterationTimeline(username, password, 0, 0);
	}

	public WPost[] GetIterationTimeline(String username, String password,
			long since, long to) {
		WPost[] wpost;
		wpost = new WPost[2];

		try {
			URL url = new URL(host + "/GetIterationTimeline");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\" , \"since\":\"" + since + "\" , \"to\":\""
					+ to + "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream(), "UTF-8");
				BufferedReader br = new BufferedReader(in);
				String output;
				String result = "";
				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

				wpost = new WPost[countOccurrences(result, '{')];
				Gson gson = new GsonBuilder().registerTypeAdapter(
						Calendar.class, new JsonDateDeserializer()).create();
				wpost = gson.fromJson(result, WPost[].class);
			}

			conn.disconnect();
		} catch (Exception e) {
			wpost = null;

		}

		return wpost;
	}

	public WPost[] GetInteractiveTimeline(String username, String password,
			String collection, String interactiveObject, String objectType) {
		return GetInteractiveTimeline(username, password, collection,
				interactiveObject, objectType, 0, 0);
	}

	public WPost[] GetInteractiveTimeline(String username, String password,
			String collection, String interactiveObject, String objectType,
			long since, long to) {
		WPost[] wpost;
		wpost = new WPost[2];

		try {
			URL url = new URL(host + "/GetInteractiveTimeline");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\" ,  \"collection\":\"" + collection
					+ "\" , \"interactiveObject\":\"" + interactiveObject
					+ "\" ,  \"objectType\":\"" + objectType
					+ "\" ,  \"since\":\"" + since + "\" , \"to\":\"" + to
					+ "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream(), "UTF-8");
				BufferedReader br = new BufferedReader(in);
				String output;
				String result = "";
				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

				wpost = new WPost[countOccurrences(result, '{')];
				Gson gson = new GsonBuilder().registerTypeAdapter(
						Calendar.class, new JsonDateDeserializer()).create();
				wpost = gson.fromJson(result, WPost[].class);
			}

			conn.disconnect();
		} catch (Exception e) {
			wpost = null;

		}

		return wpost;
	}

	public boolean Post(String username, String password, String message) {
		String result = "";
		try {
			URL url = new URL(host + "/Post");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\" , \"message\":\"" + message + "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;

				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

			}

			conn.disconnect();
		} catch (Exception e) {
			return false;

		}

		if (result.equals("true")) {

			return true;
		} else {
			return false;
		}

	}

	public boolean Follow(String username, String password, int followId) {
		String result = "";
		try {

			if (followId != -1) {
				URL url = new URL(host + "/Follow");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setUseCaches(false);
				conn.setAllowUserInteraction(false);
				conn.setRequestProperty("Content-Type", "application/json");

				// Create the form content
				OutputStream out = conn.getOutputStream();
				Writer writer = new OutputStreamWriter(out, "UTF-8");
				writer.write("{ \"username\":\"" + username
						+ "\", \"password\":\"" + password
						+ "\" , \"followId\":\"" + followId + "\"}");

				writer.close();
				out.close();
				int status = conn.getResponseCode();

				if (status >= 200 && status <= 299) {
					InputStreamReader in = new InputStreamReader(
							conn.getInputStream());
					BufferedReader br = new BufferedReader(in);
					String output;

					while ((output = br.readLine()) != null) {
						result += output;

					}
					br.close();

				}

				conn.disconnect();
			}
		} catch (Exception e) {
			return false;

		}

		if (followId == -1) {
			return false;
		}

		if (result.equals("true")) {

			return true;
		} else {
			return false;
		}

	}

	public boolean UnFollow(String username, String password, int followId) {
		String result = "";
		try {
			URL url = new URL(host + "/UnFollow");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\" , \"followId\":\"" + followId + "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;

				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

			}

			conn.disconnect();
		} catch (Exception e) {
			return false;

		}

		if (result.equals("true")) {

			return true;
		} else {
			return false;
		}

	}

	public WUser[] GetFollowings(String username, String password) {
		WUser[] wuser = new WUser[2];

		try {
			URL url = new URL(host + "/GetFollowings");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;
				String result = "";
				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

				Gson gson = new Gson();
				wuser = new WUser[countOccurrences(result, '{')];
				wuser = gson.fromJson(result, WUser[].class);

			}

			conn.disconnect();
		} catch (Exception e) {
			wuser = null;

		}

		return wuser;

	}

	public WUser[] GetFollowers(String username, String password) {
		WUser[] wuser = new WUser[2];

		try {
			URL url = new URL(host + "/GetFollowers");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;
				String result = "";
				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

				Gson gson = new Gson();
				wuser = new WUser[countOccurrences(result, '{')];
				wuser = gson.fromJson(result, WUser[].class);
			}

			conn.disconnect();
		} catch (Exception e) {
			wuser = null;

		}

		return wuser;

	}

	public WUser[] GetSuggestedFriends(String username, String password) {
		WUser[] wuser = new WUser[2];

		try {
			URL url = new URL(host + "/GetSuggestedFriends");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;
				String result = "";
				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

				Gson gson = new Gson();
				wuser = new WUser[countOccurrences(result, '{')];
				wuser = gson.fromJson(result, WUser[].class);
			}

			conn.disconnect();
		} catch (Exception e) {
			wuser = null;

		}

		return wuser;
	}

	public String[] GetSkills(String username, String password, String ownerName) {
		String[] skills = new String[2];

		try {
			URL url = new URL(host + "/GetSkills");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\" , \"ownerName\":\"" + ownerName + "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;
				String result = "";
				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

				Gson gson = new Gson();
				skills = new String[countOccurrences(result, '{')];
				skills = gson.fromJson(result, String[].class);
			}

			conn.disconnect();
		} catch (Exception e) {
			skills = null;

		}

		return skills;
	}

	public WUser[] GetHiddenUsers(String username, String password) {
		WUser[] wuser = new WUser[2];

		try {
			URL url = new URL(host + "/GetHiddenUsers");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;
				String result = "";
				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

				Gson gson = new Gson();
				wuser = new WUser[countOccurrences(result, '{')];
				wuser = gson.fromJson(result, WUser[].class);
			}

			conn.disconnect();
		} catch (Exception e) {
			wuser = null;

		}

		return wuser;
	}

	public WHidden GetUserHideSettings(String username, String password,
			int userId) {
		WHidden whidden = new WHidden();

		try {
			URL url = new URL(host + "/GetUserHideSettings");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\" , \"userId\":\"" + userId + "\" }");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;
				String result = "";
				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

				Gson gson = new Gson();
				whidden = gson.fromJson(result, WHidden.class);
			}

			conn.disconnect();
		} catch (Exception e) {
			whidden = null;

		}

		return whidden;
	}

	public boolean UpdateHiddenUser(String username, String password,
			int userId, boolean suggestions, boolean dynamic,
			boolean interactive) {
		String result = "";
		try {
			URL url = new URL(host + "/UpdateHiddenUser");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\" , \"userId\":\"" + userId
					+ "\", \"suggestions\":\"" + suggestions
					+ "\" , \"dynamic\":\"" + dynamic
					+ "\" , \"interactive\":\"" + interactive + "\" }");
			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;

				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

			}

			conn.disconnect();
		} catch (Exception e) {
			return false;

		}

		if (result.equals("true")) {

			return true;
		} else {
			return false;
		}
	}

	public WFeature[] GetChosenFeatures(String username, String password,
			int serviceInstanceId) {
		WFeature[] wfeature;
		wfeature = new WFeature[2];

		try {
			URL url = new URL(host + "/GetChosenFeatures");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\" , \"serviceInstanceId\":\""
					+ serviceInstanceId + "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;
				String result = "";
				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

				wfeature = new WFeature[countOccurrences(result, '{')];
				Gson gson = new Gson();
				wfeature = gson.fromJson(result, WFeature[].class);
			}

			conn.disconnect();
		} catch (Exception e) {
			wfeature = null;

		}

		return wfeature;

	}

	public URI[] GetAvailableAvatars(String username, String password) {
		URI[] uri = new URI[0];

		try {
			URL url = new URL(host + "/GetAvailableAvatars");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();
			System.out.println("status " + status);
			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;
				String result = "";
				while ((output = br.readLine()) != null) {
					result += output;
					System.out.println("result1 " + result);

				}
				br.close();
				System.out.println("result " + result);
				Gson gson = new Gson();
				uri = new URI[countOccurrences(result, '{')];
				uri = gson.fromJson(result, URI[].class);
			}

			conn.disconnect();
		} catch (Exception e) {
			uri = null;

		}

		return uri;
	}

	public boolean SaveAvatar(String username, String password, URI avatar) {
		String result = "";
		try {
			URL url = new URL(host + "/SaveAvatar");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "application/json");

			// Create the form content
			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write("{ \"username\":\"" + username + "\", \"password\":\""
					+ password + "\" , \"avatar\":\"" + avatar.toString()
					+ "\"}");

			writer.close();
			out.close();
			int status = conn.getResponseCode();

			if (status >= 200 && status <= 299) {
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());
				BufferedReader br = new BufferedReader(in);
				String output;

				while ((output = br.readLine()) != null) {
					result += output;

				}
				br.close();

			}

			conn.disconnect();
		} catch (Exception e) {
			return false;

		}

		if (result.equals("true")) {

			return true;
		} else {
			return false;
		}

	}

}
