package musicLibrary;

import interfaces.Library;
import interfaces.Listener;
import interfaces.Record;
import interfaces.RecordsList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class MusicLibrary implements Library{
	private List<RecordsList> genres;
    private Collection<Listener> listeners;
	public MusicLibrary(List<RecordsList> genres) {
		this.genres = genres;
        this.listeners = new ArrayList<Listener>();
	}
	
	public List<RecordsList> getRecordsLists(){
		return genres;
    }

    public void AddListener(Listener listener)
    {
        this.listeners.add(listener);
    }

    private void notifyListeners(Object arg)
    {
        for(Listener listener: listeners)
        {
            listener.doEvent(arg);
        }
    }

    @Override
	public Collection<Record> getAllRecords(){
		 Collection<Record> allTracks = new HashSet<>();
	    	for(RecordsList genre:genres){
	    		allTracks.addAll(genre.getRecords());
	    	}
			return allTracks;
	 }
	
	@Override 
    public RecordsList getRecordsList(String genreName){
    	RecordsList genre = null;
    	for (RecordsList currentGenre:genres){
    		if(currentGenre.getRecordsListName().equalsIgnoreCase(genreName)){
    			genre = currentGenre;
    			break;
    		}
    	}
    	if (genre == null)
    		throw new IllegalArgumentException("Wrong name of genre, or you forgot to use quotation marks");
		return genre;
    }
    
	@Override
    public Record getRecord(String trackTitle){
    	Record track = null;
    	for(RecordsList genre:genres)
    		for(Record currentTrack:genre.getRecords())
    			if(currentTrack.getTrackTitle().equalsIgnoreCase(trackTitle)){
    				track = currentTrack;
    				break;
    			}
    	if (track == null)
    		throw new IllegalArgumentException("Wrong title of track, or you forgot to use quotation marks");
    	return track;
    }
    
	@Override
	public void setRecord(String trackTitle, Record newTrack){
		Record track = getRecord(trackTitle);
		RecordsList genre = getRecordsList(track.getGenre());
		genre.setRecord(trackTitle, newTrack);
        this.notifyListeners("Operation successful");
	}
	
	@Override
	public void insertRecord(Record newTrack){
		boolean added = false;
		for(RecordsList genre:genres)
			if(genre.getRecordsListName().equalsIgnoreCase(newTrack.getGenre())){
				genre.insertRecord(newTrack);
				added = true;

				break;
			}
		if(!added){
			Collection<Record> newGenreTracks = new HashSet<>();
			newGenreTracks.add(newTrack);
			genres.add(new Genre(newTrack.getGenre(), newGenreTracks));
		}
        this.notifyListeners("Operation successful");
	}

	@Override
	public void removeRecord(String genreName, Record currentTrack) {
		getRecordsList(genreName).removeRecord(currentTrack);
        this.notifyListeners("Operation successful");
	}
	
	@Override
	public void removeRecordsList(String genreName) {
		genres.remove(getRecordsList(genreName));
	}
	
	public void setRecordsList(String newGenreName){
		for(RecordsList genre:genres)
			if(genre.getRecordsListName().equalsIgnoreCase(newGenreName))
				throw new IllegalArgumentException("Genre, with name <" + newGenreName + "> already exist");
			
			Collection<Record> newGenreTracks = new HashSet<>();
			genres.add(new Genre(newGenreName, newGenreTracks));
	}
}
