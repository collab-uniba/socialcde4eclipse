package it.uniba.di.socialcdeforeclipse.sharedLibrary;

public class WHidden {
     
    public boolean Suggestions;

    /// <summary>
    /// Is hidden in dynamic timeline.
    /// </summary>
     
    public boolean Dynamic;

    /// <summary>
    /// Is hidden in interactive timeline.
    /// </summary>
     
    public boolean Interactive;

	public boolean isSuggestions() {
		return Suggestions;
	}

	public void setSuggestions(boolean suggestions) {
		Suggestions = suggestions;
	}

	public boolean isDynamic() {
		return Dynamic;
	}

	public void setDynamic(boolean dynamic) {
		Dynamic = dynamic;
	}

	public boolean isInteractive() {
		return Interactive;
	}

	public void setInteractive(boolean interactive) {
		Interactive = interactive;
	}
    

}
