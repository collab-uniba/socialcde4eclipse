package it.uniba.di.socialcdeforeclipse.model;

import it.uniba.di.socialcdeforeclipse.shared.library.WFeature;
import it.uniba.di.socialcdeforeclipse.shared.library.WHidden;
import it.uniba.di.socialcdeforeclipse.shared.library.WOAuthData;
import it.uniba.di.socialcdeforeclipse.shared.library.WPost;
import it.uniba.di.socialcdeforeclipse.shared.library.WService;
import it.uniba.di.socialcdeforeclipse.shared.library.WUser;

import java.net.URI;

public interface ISocialTFSProxy {

	/**
	 * Check that the username has not been assigned to another user.
	 * <p>
	 * It can be accessed by a GET request
	 * "&lt;ServiceHost&gt;/IsAvailable?username={username}" and returns a JSON
	 * response.
	 * 
	 * @param username
	 *            Name that identifies the user.
	 * @return True if the username is not already assigned to another user,
	 *         false otherwise.
	 * */
	public boolean IsAvailable(String username);

	/**
	 * Check the state of the web service.
	 * <p>
	 * It can be accessed by a GET request
	 * "&lt;ServiceHost&gt;/IsWebSerciceRunning" and returns a JSON response.
	 * <p>
	 * 
	 * @return True if web service is running, false otherwise
	 * */
	public Boolean IsWebServiceRunning();

	/**
	 * Register a new user in the system.
	 * <p>
	 * It can be accessed by a POST request "&lt;ServiceHost&gt;/SubscribeUser"
	 * and returns a JSON response.
	 * 
	 * @param email
	 *            Email address to which the invitation was sent.
	 * @param password
	 *            Password sent in the invitation.
	 * @param username
	 *            New username chosen by the user.
	 * @return 0 if subscription is successful,1 if e-mail address does not
	 *         exist in the database,2 if password does not match with the
	 *         e-mail address sent,3 if username is already used by another
	 *         user.
	 * */
	public int SubscribeUser(String email, String password, String username);

	// no
	public Boolean UpdateChosenFeatures(String username, String password,
			int serviceInstanceId, String[] chosenFeatures);

	public boolean ChangePassword(String username, String oldPassword,
			String newPassword);

	// si

	/**
	 * Returns the data of all available services.
	 * <p>
	 * It can be accessed by a POST request "&lt;ServiceHost&gt;/GetServices"
	 * and returns a JSON response.
	 * 
	 * @param username
	 *            Name that identifies the user.
	 * @param password
	 *            Password to check user identity.
	 * @return All available services.
	 * */
	public WService[] GetServices(String username, String password);

	/**
	 * Returns the profile of a user.
	 * <p>
	 * It can be accessed by a Post request "&lt;ServiceHost&gt;/GetUser" and
	 * returns a JSON response.
	 * 
	 * @param username
	 *            Name that identifies the user.
	 * @param password
	 *            Password to check user identity.
	 * @return The profile of the user.
	 * */
	public WUser GetUser(String username, String password);

	/**
	 * Returns the profile of another user.
	 * <p>
	 * It can be accessed by a Post request
	 * "&lt;ServiceHost&gt;/GetColleagueProfile" and returns a JSON response.
	 * 
	 * @param username
	 *            Name that identifies the user.
	 * @param password
	 *            Password to check user identity.
	 * @param colleagueId
	 *            The id of the colleague.
	 * @return The profile of the user.
	 * */
	public WUser GetColleagueProfile(String username, String password,
			int colleagueId);

	/**
	 * Provides the data for OAuth version 1 authentication procedure.
	 * <p>
	 * It can be accessed by a POST request "&lt;ServiceHost&gt;/GetOAuthData"
	 * and returns a JSON response.
	 * 
	 * @param username
	 *            Name that identifies the user.
	 * @param password
	 *            Password to check user identity.
	 * @param service
	 *            Identifier of the service.
	 * @return The authorization link, the access token and the access secret.
	 * */
	public WOAuthData GetOAuthData(String username, String password, int service);

	// no
	public boolean Authorize(String username, String password, int service,
			String verifier, String accessToken, String accessSecret);

	public boolean RecordService(String username, String password, int service,
			String usernameOnService, String passwordOnService, String domain);

	public boolean DeleteRegistredService(String username, String password,
			int service);

	/**
	 * Provides up to 20 static timeline posts.
	 * <p>
	 * It can be accessed by a POST request
	 * "&lt;ServiceHost&gt;/GetStaticTimeline" and returns a JSON response.
	 * 
	 * @param username
	 *            Name that identifies the user.
	 * @param password
	 *            Password to check user identity.
	 * @param since
	 *            Id of last downloaded post.
	 * @param to
	 *            Id of first downloaded post.
	 * @return Static timeline.
	 * */
	public WPost[] GetHomeTimeline(String username, String password);

	public WPost[] GetHomeTimeline(String username, String password,
			long since, long to);

	/**
	 * Provides up to 20 user's timeline posts.
	 * <p>
	 * It can be accessed by a POST request
	 * "&lt;ServiceHost&gt;/GetUserTimeline" and returns a JSON response.
	 * 
	 * @param username
	 *            Name that identifies the user.
	 * @param password
	 *            Password to check user identity.
	 * @param ownerName
	 *            The owner of the timeline.
	 * @param since
	 *            Id of last downloaded post.
	 * @param to
	 *            Id of first downloaded post.
	 * @return User timeline.
	 * */
	public WPost[] GetUserTimeline(String username, String password,
			String ownerName);

	public WPost[] GetUserTimeline(String username, String password,
			String ownerName, long since, long to);

	/**
	 * Get the timeline built with iteration friends.
	 * <p>
	 * It can be accessed by a POST request
	 * "&lt;ServiceHost&gt;/GetIterationTimeline" and returns a JSON response.
	 * 
	 * @param username
	 *            Name that identifies the user.
	 * @param password
	 *            Password to check user identity.
	 * @param since
	 *            Id of last downloaded post.
	 * @param to
	 *            Id of first downloaded post.
	 * @return Dynamic timeline.
	 * */
	public WPost[] GetIterationTimeline(String username, String password);

	public WPost[] GetIterationTimeline(String username, String password,
			long since, long to);

	/**
	 * Get the timeline built with all people involved with the panels with
	 * which the user is interacting to.
	 * <p>
	 * It can be accessed by a POST request
	 * "&lt;ServiceHost&gt;/GetInteractiveTimeline" and returns a JSON response.
	 * 
	 * @param username
	 *            Name that identifies the user.
	 * @param password
	 *            Password to check user identity.
	 * @param collection
	 *            Collection in which to try the interactive object reference.
	 * @param interactiveObject
	 *            The interactive object around which to create the network.
	 * @param interactiveObject
	 *            Array of all interacrive objects within user workspace.
	 * @param since
	 *            Id of last downloaded post.
	 * @param to
	 *            Id of first downloaded post.
	 * @return Interactive timeline.
	 * */
	public WPost[] GetInteractiveTimeline(String username, String password,
			String collection, String interactiveObject, String objectType);

	public WPost[] GetInteractiveTimeline(String username, String password,
			String collection, String interactiveObject, String objectType,
			long since, long to);

	public boolean Post(String username, String password, String message);

	public boolean Follow(String username, String password, int followId);

	public boolean UnFollow(String username, String password, int followId);

	/**
	 * Returns the users who the user follows.
	 * <p>
	 * It can be accessed by a POST request "&lt;ServiceHost&gt;/GetFollowings"
	 * and returns a JSON response.
	 * 
	 * @param username
	 *            Name that identifies the user.
	 * @param password
	 *            Password to check user identity.
	 * @return A list of following users.
	 * */
	public WUser[] GetFollowings(String username, String password);

	/**
	 * Returns the users who follow the user.
	 * <p>
	 * It can be accessed by a POST request "&lt;ServiceHost&gt;/GetFollowers"
	 * and returns a JSON response.
	 * 
	 * @param username
	 *            Name that identifies the user.
	 * @param password
	 *            Password to check user identity.
	 * @return A list of follower users.
	 * */
	public WUser[] GetFollowers(String username, String password);

	/**
	 * Returns the friends of the user that are in the same time in SocialCde
	 * and in an active microblog.
	 * <p>
	 * It can be accessed by a POST request
	 * "&lt;ServiceHost&gt;/GetSuggestedFriends" and returns a JSON response.
	 * 
	 * @param username
	 *            Name that identifies the user.
	 * @param password
	 *            Password to check user identity.
	 * @return A list of suggested friends.
	 * */
	public WUser[] GetSuggestedFriends(String username, String password);

	/**
	 * Returns the user's skills retrived from the social network that the user
	 * has actived
	 * <p>
	 * It can be accessed by a POST request "&lt;ServiceHost&gt;/GetSkills" and
	 * returns a JSON response.
	 * 
	 * @param username
	 *            Name that identifies the user.
	 * @param password
	 *            Password to check user identity.
	 * @param ownerName
	 *            The owner of the skills.
	 * @return A list of suggested friends.
	 * */
	public String[] GetSkills(String username, String password, String ownerName);

	/**
	 * Get all the users hidden by the current user.
	 * <p>
	 * It can be accessed by a POST request "&lt;ServiceHost&gt;/GetHiddenUsers"
	 * and returns a JSON response.
	 * 
	 * @param username
	 *            Name that identifies the user.
	 * @param password
	 *            Password to check user identity.
	 * @return users hidden by the user identify by username and password
	 * */
	public WUser[] GetHiddenUsers(String username, String password);

	/**
	 * Get the visibility of a user from the suggestions and automatic
	 * friendships of the current user.
	 * <p>
	 * It can be accessed by a POST request
	 * "&lt;ServiceHost&gt;/GetUserHideSettings" and returns a JSON response.
	 * 
	 * @param username
	 *            Name that identifies the user.
	 * @param password
	 *            Password to check user identity.
	 * @param user
	 *            User that current user want to get the visibility.
	 * @return Setting of hidden user
	 * */
	public WHidden GetUserHideSettings(String username, String password,
			int userId);

	// no
	public boolean UpdateHiddenUser(String username, String password,
			int userId, boolean suggestions, boolean dynamic,
			boolean interactive);

	/**
	 * Returns the features that a user have chosen for a specific service
	 * instance.
	 * <p>
	 * It can be accessed by a POST request
	 * "&lt;ServiceHost&gt;/GetChosenFeatures" and returns a JSON response.
	 * 
	 * @param username
	 *            Name that identifies the user.
	 * @param password
	 *            Password to check user identity.
	 * @param serviceInstanceId
	 *            The id of the service instance.
	 * @return A list of features.
	 * */
	public WFeature[] GetChosenFeatures(String username, String password,
			int serviceInstanceId);

	/**
	 * Returns the available avatars that a user have chosen for a specific
	 * service instance.
	 * <p>
	 * It can be accessed by a POST request
	 * "&lt;ServiceHost&gt;/GetAvailableAvatars" and returns a JSON response.
	 * 
	 * @param username
	 *            Name that identifies the user.
	 * @param password
	 *            Password to check user identity.
	 * @return A list of URI.
	 * */
	public URI[] GetAvailableAvatars(String username, String password);

	// no
	public boolean SaveAvatar(String username, String password, URI avatar);

}
