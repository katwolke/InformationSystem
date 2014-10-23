package management;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import musicLibrary.Genre;
import musicLibrary.Track;

public class ManagementSystem {
    private List<Genre> genres;
    private static Collection<Track> tracks;
    private String genresFile = "storage/genresFile.bin";
    private String tracksFile = "storage/traksFile.bin";
    private static Logger log = Logger.getAnonymousLogger();
    
    private static ManagementSystem instance;

    public ManagementSystem() {
        loadGenres(genresFile);
        loadTracks(tracksFile);
    }
 

    public static synchronized ManagementSystem getInstance() {
        if (instance == null) {
            instance = new ManagementSystem();
        }
        return instance;
    }


    public void loadGenres(String genresFile){
		try {
			genres = (ArrayList)FileOperation.deserialized(genresFile, ArrayList.class);
		} catch (IOException e) {
			log.info("Caught exception while processing file: " + e.getMessage());
		}
    }
      
    public static void loadTracks(String tracksFile) {
		try {
			tracks = (HashSet) FileOperation.deserialized(tracksFile, HashSet.class);
		} catch (IOException e) {
			log.info("Caught exception while processing file: " + e.getMessage());
		}
	}
    
    public List<Genre> getGenres(){
		return genres;
    	
    }
    public Collection<Track> getAllTracks(){
		return tracks;
    }
    
    public void printAllTracksTitle(){
    	for (Track track:tracks)
    		System.out.println(track.getTrackName());
    }
    
    public Collection<Track> getTracksFromGenre(Genre genre){
    	Collection<Track> tracksFromGenre = new HashSet<Track>();
    	for(Track track:tracks)
    		if(track.getGenre().equals(genre.getGenreName()))
    			tracksFromGenre.add(track);
		return tracksFromGenre;
    }
    
    public void moveTrackAnotherGenre(String trackName, String genreName){
    	Track currentTrack = getTrack(trackName);
    	currentTrack.setGenre(genreName);
    	try {
			FileOperation.serialized("storage/traksFile.bin", tracks);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public Track getTrack(String trackName){
    	Track track = null;
    	for(Track currentTrack:tracks)
    		if(currentTrack.getTrackName().equals(trackName)){
    			track = currentTrack;
    			break;
    		}
    	if (track == null)
    		throw new IllegalArgumentException("Wrong title of track, or you forgot to use quotation marks");
    	return track;
    }

    public void printTrackInfo(String trackName){
    	Track track = getTrack(trackName);
    	System.out.println(track.toString());
    }
    
	public void setTrack(String trackName, String ...args){
		for (Track track:tracks)
			if(track.getTrackName() == trackName){
				track.setGenre(args[0]);
				track.setTrackName(args[1]);
				track.setSinger(args[2]);
				track.setAlbum(args[3]);
				track.setRecordLength(args[4]);
				break;
			}
		try{
			FileOperation.serialized("storage/traksFile.bin", tracks);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Doesn't it need a check?
	 * > I don't think so, compiler have to check it when adds element into HashSet
	 * >> fixed my  mistake in method equals, no need extra check 
	 */
	public void insertTrack(String ...args){
		Track track = new Track(args[0], args[1], args[2], args[3], args[4]);
        /*Iterator<Track> checkIfAlreadyThere = tracks.iterator();
        boolean ifAlreadyThere = false;
        while (checkIfAlreadyThere.hasNext())
        {
            if(checkIfAlreadyThere.next().equals(track)) ifAlreadyThere = true;
        }
		if (!ifAlreadyThere)*/ 
		tracks.add(track);
		try{
			FileOperation.serialized("storage/traksFile.bin", tracks);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /*
     * Genres has only ArrayList, check is needed
     */
    public void insertGenre(Genre genre){
        boolean ifAlreadyThere = false;
        for(Genre alreadyThere:genres){
            if(alreadyThere.equals(genre)) ifAlreadyThere = true;
        }
        if(!ifAlreadyThere) genres.add(genre);
        try{
            FileOperation.serialized("storage/genresFile.bin", genres);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void removeTrack(Track track){
		tracks.remove(track);
		try {
			FileOperation.serialized("storage/traksFile.bin", tracks);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /*
     * Not sure about Collection<SearchableRecord>
     *     may be separate search in genres/tracks???
     */
    public Collection<SearchableRecord> searchTracks(String mask) {
        Collection<SearchableRecord> fits = new ArrayList<SearchableRecord>();
        Iterator<Genre> genreIterator = genres.iterator();
        for (SearchableRecord checked : tracks) {
            if (checked.fitsMask(mask)) fits.add(checked);
        }
        return fits;
    }

    public Collection<SearchableRecord> searchGenres(String mask) {
        Collection<SearchableRecord> fits = new ArrayList<SearchableRecord>();
        for (SearchableRecord checked : genres) {
            if (checked.fitsMask(mask)) fits.add(checked);
        }
        return fits;
    }


    public void addTracks(String fileName) {
        Collection<Track> records;
        try {
            records = (HashSet) FileOperation.deserialized(fileName, HashSet.class);
            tracks.addAll(records);
        } catch (IOException e) {
            log.info("Caught exception while processing file: " + e.getMessage());
        }
    }
    /*
     * Not the best realization, may be HashSet for Genres too?
     */
    public void addGenres(String fileName) {
        Collection<Genre> records;
        try {
            records = (ArrayList) FileOperation.deserialized(fileName, ArrayList.class);;
            for(Genre addedGenre: records)
            {
                boolean ifAlreadyThere = false;
                for(Genre alreadyThere:genres){
                    if(alreadyThere.equals(addedGenre)) ifAlreadyThere = true;
                }
                if(!ifAlreadyThere) genres.add(addedGenre);
            }
        } catch (IOException e) {
            log.info("Caught exception while processing file: " + e.getMessage());
        }
    }

}
