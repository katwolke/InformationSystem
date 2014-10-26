package musicLibrary;

import java.io.Serializable;
import java.util.Collection;

import management.SearchableRecord;

public class Genre implements SearchableRecord, Serializable{

	private static final long serialVersionUID = 1L;
    private Collection<Track> tracks;
	private String genreName;
	
	public Genre(String genreName, Collection<Track> tracks) {
		this.tracks = tracks;
		this.genreName = genreName;
	}
	
	public Genre(String genreName) {
		this.genreName = genreName;
	}
	
	public Collection<Track> getTracks(){
		return tracks;
	}
	
	public Track getTrack(String trackTitle){
    	Track track = null;
    	for(Track currentTrack:tracks)
    		if(currentTrack.getTrackTitle().equalsIgnoreCase(trackTitle)){
    			track = currentTrack;
    			break;
    		}
    	return track;
    }
	
	public void setTrack(String trackTitle, Track newTrack){
		for(Track track:tracks)
			if(track.getTrackTitle().equalsIgnoreCase(trackTitle)){
				track.setGenre(newTrack.getGenre());
				track.setSinger(newTrack.getSinger());
				track.setAlbum(newTrack.getAlbum());
				track.setRecordLength(newTrack.getRecordLength());
				break;
			}
	}
	
	public void insertTrack(Track newTrack){
		tracks.add(newTrack);
	}
	
	public void removeTrack(Track track){
		tracks.remove(track);
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
