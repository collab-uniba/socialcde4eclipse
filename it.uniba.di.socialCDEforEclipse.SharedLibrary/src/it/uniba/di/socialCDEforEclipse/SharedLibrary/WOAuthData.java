package it.uniba.di.socialCDEforEclipse.SharedLibrary;

public class WOAuthData {
	/// <summary>
    /// Link to authorization page of the service instance.
    /// </summary>
    public String AuthorizationLink;
  /// <summary>
    /// Access Token of the service instance.
    /// </summary>
    public String AccessToken;

    /// <summary>
    /// AccessSecret of the service instance.
    /// </summary>
    public String AccessSecret;
    
    
    public String getAuthorizationLink() {
		return AuthorizationLink;
	}

	public void setAuthorizationLink(String authorizationLink) {
		AuthorizationLink = authorizationLink;
	}

	public String getAccessToken() {
		return AccessToken;
	}

	public void setAccessToken(String accessToken) {
		AccessToken = accessToken;
	}

	public String getAccessSecret() {
		return AccessSecret;
	}

	public void setAccessSecret(String accessSecret) {
		AccessSecret = accessSecret;
	}
}
