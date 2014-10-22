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

public class ManagementSystem {
    private List<Genre> genres;
    private static Collection<Track> tracks;
    private String genresFile = "storage/genresFile.txt";
    private String tracksFile = "storage/tracksFile.txt";
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
			scanner.useDelimiter(Pattern.compile("[;\\s*\r]+"));
			try {
			//	String line = reader.readLine();
			//	while (line != null) {
					while (scanner.hasNext()) {
						String trackName = scanner.next();
						String singer = scanner.next();
						String album = scanner.next();
						String recordLength = scanner.next();
						String genre = scanner.next();
						tracks.add(new Track(genre, trackName, singer, album, recordLength));
	
					}
			//		line = reader.readLine();
			//	}
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
    
    public void ptintAllTracks(){
    	for (Track track:tracks)
    		System.out.println(track.getTrackName() + " - " +track.getSinger() + " - " + track.getAlbum() + " - " +track.getRecordLength() + " - " +track.getGenre() );
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
    	//add method to write changes 
    	System.out.println("Done");
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
    
    public void getTrackInfo(String trackName){
    	Track track = getTrack(trackName);
    	System.out.println("Track title - " + track.getTrackName() + "\r" +
    			"Singer - " + track.getSinger() + "\r" +
    			"Album - " + track.getAlbum() + "\r" +
    			"Record length - " + track.getRecordLength() + "\r" +
    	    	"Genre - " + track.getGenre() );
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
