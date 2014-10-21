package management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import musicLibrary.Genre;
import musicLibrary.Track;

public class Controller {
    private List<Genre> genres;
    private static Collection<Track> tracks;
    private String genresFile = "storage/genresFile.txt";
    private String tracksFile = "storage/tracksFile.txt";
    private static Logger log = Logger.getAnonymousLogger();
    
    private static Controller instance;

    private Controller() {
        loadGenres(genresFile);
        loadTracks(tracksFile);
    }
 

    public static synchronized Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

	/*
	 * Serialization will be added here, when we implement a part with writeObject  
	 */   
    public void loadGenres(String genresFile){
    	genres = new ArrayList<>();
		try {
			InputStreamReader inputStream = new InputStreamReader(new FileInputStream(new File(genresFile)),"UTF-8");
			BufferedReader reader = new BufferedReader(inputStream);
			try {
				String line = reader.readLine();
				while (line != null) {
						genres.add(new Genre(line.trim()));
					line = reader.readLine();
				}
			} finally {
				reader.close();
				inputStream.close();
			}
		} catch (IOException e) {
			log.info("Caught exception while processing file: " + e.getMessage());
		}
    }
    
	/*
	 * Serialization will be added here, when we implement a part with writeObject  
	 */    
    public static void loadTracks(String tracksFile) {
		tracks = new HashSet<Track>();
		try {
			InputStreamReader inputStream = new InputStreamReader(new FileInputStream(new File(tracksFile)),"UTF-8");
			BufferedReader reader = new BufferedReader(inputStream);
			Scanner scanner = new Scanner(reader);
			scanner.useDelimiter(Pattern.compile("[;]+"));
			try {
				String line = reader.readLine();
				while (line != null) {
					if (scanner.hasNext()) {
						String trackName = scanner.next();
						String singer = scanner.next();
						String album = scanner.next();
						String recordLength = scanner.next();
						String genre = scanner.next();
						tracks.add(new Track(genre, trackName, singer, album, recordLength));
					}
					line = reader.readLine();
				}
			} finally {
				scanner.close();
				reader.close();
				inputStream.close();
			}
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
    
    public Collection<Track> getTracksFromGenre(Genre genre){
    	Collection<Track> tracksFromGenre = new HashSet<Track>();
    	for(Track track:tracks)
    		if(track.getGenre().equals(genre.getGenreName()))
    			tracksFromGenre.add(track);
		return tracksFromGenre;
    }
    
    public void moveTrackAnotherGenre(Genre oldGenre, Track track, Genre newGenre){
    	for (Track currentTrack : getTracksFromGenre(oldGenre))
    		if(currentTrack.getTrackName().equals(track.getTrackName())){
    			currentTrack.setGenre(newGenre.getGenreName());
    			break;
    		}
    }
    
    public Track getTrack(String trackName){
    	Track track = null;
    	for(Track currentTrack:tracks)
    		if(currentTrack.getTrackName().equals(trackName)){
    			track = currentTrack;
    			break;
    		}
    	return track;
    }
    
	/* 
	 *   Will be set if doesn't have duplicate. 
	 */
	public void setTrack(String trackName, Track newTrack){
		for (Track track:tracks)
			if(track.getTrackName() == trackName){
				track.setGenre(newTrack.getGenre());
				track.setTrackName(newTrack.getTrackName());
				track.setSinger(newTrack.getSinger());
				track.setAlbum(newTrack.getAlbum());
				track.setRecordLength(newTrack.getRecordLength());
				break;
			}
	}
	
	/*
	 * will be inserted if doesn't have duplicate.  
	 */
	public void insertTrack(Track track){
		tracks.add(track);
	}
	
	/*
	 * remove from collection, from file will be removed after write changes
	 */
	public void removeTrack(Track track){
		tracks.remove(track);
	}
}
