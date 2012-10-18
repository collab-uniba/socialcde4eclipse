package it.uniba.di.socialcdeforeclipse.sharedLibrary;

public class WService
{
	/// <summary>
    /// Identifier of the service.
    /// </summary>
    public int Id;

    /// <summary>
    /// Name of the service.
    /// </summary>
    public String Name;

    /// <summary>
    /// Host of the service.
    /// </summary>
    public String Host;

    /// <summary>
    /// Service to the base of the service.
    /// </summary>
    public String BaseService;

    /// <summary>
    /// Image logo of the service.
    /// </summary>
    public String Image;

    /// <summary>
    /// True if the current user is registered to the service. False otherwise.
    /// </summary>
    public boolean Registered;

    /// <summary>
    /// True if the service require OAuth procedure, false otherwise.
    /// </summary>
    public boolean RequireOAuth;

    /// <summary>
    /// Version of OAuth procedure required.
    /// </summary>
    public int OAuthVersion;

    /// <summary>
    /// True if the service require TFS authetication procedure, false otherwise.
    /// </summary>
    public boolean RequireTFSAuthentication;

    /// <summary>
    /// True if the TFS authetication procedure require domain, false otherwise.
    /// </summary>
    public boolean RequireTFSDomain;
    public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getHost() {
		return Host;
	}

	public void setHost(String host) {
		Host = host;
	}

	public String getBaseService() {
		return BaseService;
	}

	public void setBaseService(String baseService) {
		BaseService = baseService;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public boolean isRegistered() {
		return Registered;
	}

	public void setRegistered(boolean registered) {
		Registered = registered;
	}

	public boolean isRequireOAuth() {
		return RequireOAuth;
	}

	public void setRequireOAuth(boolean requireOAuth) {
		RequireOAuth = requireOAuth;
	}

	public int getOAuthVersion() {
		return OAuthVersion;
	}

	public void setOAuthVersion(int oAuthVersion) {
		OAuthVersion = oAuthVersion;
	}

	public boolean isRequireTFSAuthentication() {
		return RequireTFSAuthentication;
	}

	public void setRequireTFSAuthentication(boolean requireTFSAuthentication) {
		RequireTFSAuthentication = requireTFSAuthentication;
	}

	public boolean isRequireTFSDomain() {
		return RequireTFSDomain;
	}

	public void setRequireTFSDomain(boolean requireTFSDomain) {
		RequireTFSDomain = requireTFSDomain;
	}


}
