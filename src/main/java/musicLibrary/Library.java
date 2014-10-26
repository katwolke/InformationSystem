package musicLibrary;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Library {
	private List<Genre> genres;

	public Library(List<Genre> genres) {
		this.genres = genres;
	}
	
	public List<Genre> getGenres(){
		return genres;
    	
    }
	
	 public Collection<Track> getAllTracks(){
		 Collection<Track> allTracks = new HashSet<>();
	    	for(Genre genre:genres){
	    		allTracks.addAll(genre.getTracks());
	    	}
			return allTracks;
	 }
	 
    public Genre getGenre(String genreName){
    	Genre genre = null;
    	for (Genre currentGenre:genres){
    		if(currentGenre.getGenreName().equalsIgnoreCase(genreName)){
    			genre = currentGenre;
    			break;
    		}
    	}
    	if (genre == null)
    		throw new IllegalArgumentException("Wrong name of genre, or you forgot to use quotation marks");
		return genre;
    }
    
    public Track getTrack(String trackTitle){
    	Track track = null;
    	for(Genre genre:genres)
    		for(Track currentTrack:genre.getTracks())
    			if(currentTrack.getTrackTitle().equalsIgnoreCase(trackTitle)){
    				track = currentTrack;
    				break;
    			}
    	if (track == null)
    		throw new IllegalArgumentException("Wrong title of track, or you forgot to use quotation marks");
    	return track;
    }
    
	public void setTrack(String trackTitle, Track newTrack){
		Track track = getTrack(trackTitle);
		Genre genre = getGenre(track.getGenre());
		genre.setTrack(trackTitle, newTrack);
	}
	
	public void insertTrack(Track newTrack){
		boolean added = false;
		for(Genre genre:genres)
			if(genre.getGenreName().equalsIgnoreCase(newTrack.getGenre())){
				genre.insertTrack(newTrack);
				added = true;

				break;
			}
		if(!added){
			Collection<Track> newGenreTracks = new HashSet<>();
			newGenreTracks.add(newTrack);
			genres.add(new Genre(newTrack.getGenre(), newGenreTracks));
		}
	}
	
	public void removeTrack(){
		
	}
}
