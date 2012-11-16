package it.uniba.di.socialcdeforeclipse.shared.library;

public class WFeature {
        
    public String Name;

    /// <summary>
    /// Description of the Feature.
    /// </summary>
        
    public String Description;

    /// <summary>
    /// True if the current user have chosed the feature, false otherwise.
    /// </summary>
        
    public boolean IsChosen;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public boolean isIsChosen() {
		return IsChosen;
	}

	public void setIsChosen(boolean isChosen) {
		IsChosen = isChosen;
	}
    
    
    

}
