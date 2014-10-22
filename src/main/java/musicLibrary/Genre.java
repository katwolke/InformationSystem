package musicLibrary;

public class Genre implements SearchableRecord {
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
