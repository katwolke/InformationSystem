package musicLibrary;

import interfaces.Record;
import interfaces.SearchableRecord;

import java.io.Serializable;

public class Track implements SearchableRecord, Serializable, Record {

	private static final long serialVersionUID = 1L;
	private String trackTitle;
	private String singer;
	private String album;
	private String recordLength;
	private String genre;

	public Track(String genre, String trackTitle, String singer, String album, String recordLength) {
		this.setGenre(genre);
		this.setTrackTitle(trackTitle);
		this.setSinger(singer);
		this.setAlbum(album);
		this.setRecordLength(recordLength);
	}
	
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		if ((genre == null)||(genre.equals("-")))
			this.genre = "Unsorted";
		else
			this.genre = genre;
	}
	
	public String getTrackTitle() {
		return trackTitle;
	}

	public void setTrackTitle(String trackTitle) {
		if (trackTitle == null)
			throw new IllegalArgumentException("Can't restore track without title");
		this.trackTitle = trackTitle;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		if ((singer == null)||(singer.equals("-")))
			this.singer = "unknown";
		else
			this.singer = singer;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		if ((album == null)||(album.equals("-")))
			this.album = "unknown";
		else
			this.album = album;
	}

	public String getRecordLength() {
		return recordLength;
	}

	public void setRecordLength(String recordLength) {
		if ((recordLength == null)||(recordLength.equals("-")))
			this.recordLength = "unknown";
		else
			this.recordLength = recordLength;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
	       return sb.append("Title: \"").append(getTrackTitle()).append("\", Singer: \"").append(getSinger())
	    		   .append("\", Album: \"").append(getAlbum()).append("\", Record length: ").append(getRecordLength())
	    		   .append(", Genre: \"").append(getGenre()).append("\"").toString();
	}

	@Override
	 public boolean equals(Object obj){
	     Track track = (Track)obj;
	     if (track == this) 
	         return true;
	     if (obj == null){
	         return false;
	     }
	     return trackTitle.equalsIgnoreCase(track.getTrackTitle()) &&
	    		 singer.equalsIgnoreCase(track.getSinger()) &&
	    		 album.equalsIgnoreCase(track.getAlbum()) && 
	    		 recordLength.equalsIgnoreCase(track.getRecordLength()) &&
	    		 genre.equalsIgnoreCase(track.getGenre());
	 }
	 
	@Override
	 public int hashCode(){
		 StringBuilder sb = new StringBuilder();
		 sb.append(trackTitle).append(album).append(recordLength).append(singer);
			return sb.toString().hashCode();
	 }

    @Override
    public boolean fitsMask(String mask) {
    //TODO realization
        return false;
    }
}
