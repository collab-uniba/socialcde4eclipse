package it.uniba.di.collab.socialcdeforeclipse.shared.library;

import java.util.Calendar;
import java.util.Date;

public class WPost {
	// / <summary>
	// / Identifier of the post.
	// / </summary>
	public long Id;

	// / <summary>
	// / Name of the author of the post.
	// / </summary>
	public WUser User;

	// / <summary>
	// / Name of the service.
	// / </summary>
	public WService Service;

	// / <summary>
	// / Message of the post.
	// / </summary>
	public String Message;

	// / <summary>
	// / Creation date of the post.
	// / </summary>
	public Calendar CreateAt;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public WUser getUser() {
		return User;
	}

	public void setUser(WUser user) {
		User = user;
	}

	public WService getService() {
		return Service;
	}

	public void setService(WService service) {
		Service = service;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public Calendar getCreateAt() {
		return CreateAt;
	}

	public void setCreateAt(Calendar createAt) {
		CreateAt = createAt;
	}
}
