package musicLibrary;

import java.io.Serializable;

import management.SearchableRecord;

public class Genre implements SearchableRecord, Serializable {

	private static final long serialVersionUID = 1L;
	private String genreName;
	
	public Genre(String genreName) {
		this.genreName = genreName;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

    @Override
    public boolean fitsMask(String mask) {
    //TODO realization
        return false;
    }
}
