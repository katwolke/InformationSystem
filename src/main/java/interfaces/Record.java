package interfaces;

public interface Record {
	public String getGenre();
	public void setGenre(String genre);
	public String getTrackTitle();
	public void setTrackTitle(String trackTitle);
	public String getSinger();
	public void setSinger(String singer);
	public String getAlbum();
	public void setAlbum(String album);
	public String getRecordLength();
	public void setRecordLength(String recordLength);
    public boolean fitsMask(String KeyField, String mask);
}
