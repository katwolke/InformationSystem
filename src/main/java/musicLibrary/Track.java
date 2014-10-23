package musicLibrary;

import java.io.Serializable;

import management.SearchableRecord;

public class Track implements SearchableRecord, Serializable {

	private static final long serialVersionUID = 1L;
	private String trackName;
	private String singer;
	private String album;
	private String recordLength;
	private String genre;

	public Track(String genre, String trackName, String singer, String album, String recordLength) {
		this.genre = genre;
		this.trackName = trackName;
		this.singer = singer;
		this.album = album;
		this.recordLength = recordLength;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		if (trackName == null)
			throw new IllegalArgumentException("Can't restore track without name");
		this.trackName = trackName;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		if (singer == null)
			this.singer = "unknown";
		else
			this.singer = singer;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		if (album == null)
			this.album = "unknown";
		else
			this.album = album;
	}

	public String getRecordLength() {
		return recordLength;
	}

	public void setRecordLength(String recordLength) {
		if (recordLength == null)
			this.recordLength = "0.0";
		else
			this.recordLength = recordLength;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
	       return sb.append("Title: \"").append(getTrackName()).append("\", Singer: \"").append(getSinger())
	    		   .append("\", Album: \"").append(getAlbum()).append("\", Record length: ").append(getRecordLength())
	    		   .append(", Genre: \"").append(getGenre()).append("\"").toString();
}

	/*  
	 * Check on equals genres?..  
	 * If "Yes" - tracks differ only in genre can be inserted into the collection. No?
	 */
    //NO! track
	 @Override
	 public boolean equals(Object obj){
	     Track track = (Track)obj;
	     if (track == this) 
	         return true;
	     if (obj == null)
             //obj is already null on second check
	         return false;
	     return trackName == track.getTrackName()&&
	    		singer == track.getSinger() &&
	    		album == track.getAlbum()&&
	    		recordLength == track.getRecordLength();
	 }
	 
	@Override
	 public int hashCode(){
		 StringBuilder sb = new StringBuilder();
		 sb.append(trackName).append(album).append(recordLength).append(singer);
			return sb.toString().hashCode();
	 }

    @Override
    public boolean fitsMask(String mask) {
    //TODO realization
        return false;
    }
}
