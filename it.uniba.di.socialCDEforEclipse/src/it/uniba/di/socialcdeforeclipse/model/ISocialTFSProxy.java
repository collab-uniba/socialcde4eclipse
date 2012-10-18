package it.uniba.di.socialcdeforeclipse.model;

import it.uniba.di.socialcdeforeclipse.sharedLibrary.WFeature;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.WHidden;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.WOAuthData;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.WPost;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.WService;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.WUser;

import java.net.URI;

public interface ISocialTFSProxy {

	public boolean IsAvailable(String username); 
	public Boolean IsWebServiceRunning();
	public int SubscribeUser(String email, String password, String username);
	public Boolean UpdateChosenFeatures(String username, String password, int serviceInstanceId, String[] chosenFeatures);
	public boolean ChangePassword(String username, String oldPassword, String newPassword);
	public  WService[] GetServices(String username, String password);
	public WUser GetUser(String username, String password);
	public WUser GetColleagueProfile(String username, String password, int colleagueId);
	public WOAuthData GetOAuthData(String username, String password, int service);
	public boolean Authorize(String username, String password, int service, String verifier, String accessToken, String accessSecret);
	public  boolean RecordService(String username, String password, int service, String usernameOnService, String passwordOnService, String domain);
	public  boolean DeleteRegistredService(String username, String password, int service); 
	public  WPost[] GetHomeTimeline(String username, String password);
	public  WPost[] GetHomeTimeline(String username, String password, long since, long to); 
	public WPost[] GetUserTimeline(String username, String password, String ownerName);
	public  WPost[] GetUserTimeline(String username, String password, String ownerName, long since, long to); 
	public  WPost[] GetIterationTimeline(String username, String password);
	public  WPost[] GetIterationTimeline(String username, String password, long since, long to);
	public  WPost[] GetInteractiveTimeline(String username, String password, String collection, String interactiveObject, String objectType);
	public  WPost[] GetInteractiveTimeline(String username, String password, String collection, String interactiveObject, String objectType, long since, long to);
	public boolean Post(String username, String password, String message); 
	public boolean Follow(String username, String password, int followId);
	public boolean UnFollow(String username, String password, int followId); 
	public  WUser[] GetFollowings(String username, String password); 
	public   WUser[] GetFollowers(String username, String password);
	public WUser[] GetSuggestedFriends(String username, String password);
	public  String[] GetSkills(String username, String password, String ownerName); 
	public WUser[] GetHiddenUsers(String username, String password); 
	public WHidden GetUserHideSettings(String username, String password, int userId); 
	public boolean UpdateHiddenUser(String username, String password, int userId, boolean suggestions, boolean dynamic, boolean interactive);
	public WFeature[] GetChosenFeatures(String username, String password, int serviceInstanceId);
	public URI[] GetAvailableAvatars(String username, String password);
	public boolean SaveAvatar(String username, String password, URI avatar);
	
}
