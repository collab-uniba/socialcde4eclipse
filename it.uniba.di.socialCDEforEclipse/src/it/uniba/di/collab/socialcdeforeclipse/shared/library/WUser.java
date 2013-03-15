package it.uniba.di.collab.socialcdeforeclipse.shared.library;

public class WUser {

	// / <summary>
	// / Identifier of the user.
	// / </summary>
	public int Id;

	// / <summary>
	// / Identification name of the user.
	// / </summary>
	public String Username;

	// / <summary>
	// / Email address of the user.
	// / </summary>
	public String Email;

	// / <summary>
	// / Image avatar of the user.
	// / </summary>
	public String Avatar;

	// / <summary>
	// / Number of statuses written by the user stored in the database.
	// / </summary>
	public int Statuses;

	// / <summary>
	// / Number of followings of the user.
	// / </summary>
	public int Followings;

	// / <summary>
	// / Number of followers of the user.
	// / </summary>
	public int Followers;

	// / <summary>
	// / True if current user follow this user, false otherwise.
	// / </summary>
	public boolean Followed;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		this.Id = id;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		this.Username = username;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		this.Email = email;
	}

	public String getAvatar() {
		return Avatar;
	}

	public void setAvatar(String avatar) {
		this.Avatar = avatar;
	}

	public int getStatuses() {
		return Statuses;
	}

	public void setStatuses(int statuses) {
		this.Statuses = statuses;
	}

	public int getFollowings() {
		return Followings;
	}

	public void setFollowings(int followings) {
		this.Followings = followings;
	}

	public int getFollowers() {
		return Followers;
	}

	public void setFollowers(int followers) {
		this.Followers = followers;
	}

	public boolean isFollowed() {
		return Followed;
	}

	public void setFollowed(boolean followed) {
		this.Followed = followed;
	}

}
