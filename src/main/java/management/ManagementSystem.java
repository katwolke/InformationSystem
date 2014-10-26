package management;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import musicLibrary.*;

public class ManagementSystem {
	private Library library;
    private String storage = "storage/.";
    private static Logger log = Logger.getAnonymousLogger();
    
    private static ManagementSystem instance;

    public ManagementSystem() {
        this.library = new Library(loadGenres(storage));
    }

    public static synchronized ManagementSystem getInstance() {
        if (instance == null) {
            instance = new ManagementSystem();
        }
        return instance;
    }
    
    public List<Genre> loadGenres(String storage){
    	List<Genre> genres = new ArrayList<>();
		try {
			File dir = new File(storage);
			for(String path : dir.list()) {
				genres.add(new Genre(path.substring(0, path.lastIndexOf('.')), (HashSet)FileOperation.deserialized("storage/"+ path, HashSet.class)));
			}
		} catch (IOException e) {
			log.info("Caught exception while processing file: " + e.getMessage());
		}
		return genres;
    }
    
    public void printAllTracksTitle(){
    	for (Track track:library.getAllTracks())
    		System.out.println(track.getTrackTitle());
    }
    
    public void getTracksTitles(String genreName){
    	try{
    		for(Track track:library.getGenre(genreName).getTracks())
        		System.out.println(track.getTrackTitle());
    	} catch (IllegalArgumentException e){
    		System.out.println(e.getMessage());
    	}
    }
    
    public void moveTrackAnotherGenre(String trackTitle, String genreName){
    	try{
        	Track currentTrack = library.getTrack(trackTitle);
        	String oldGenre = currentTrack.getGenre();
        	currentTrack.setGenre(genreName);
        	library.getGenre(genreName).insertTrack(currentTrack);
        	library.getGenre(oldGenre).removeTrack(currentTrack);
        	System.out.println("Track " + trackTitle +" now belongs to the genre " + genreName);
    	} catch (IllegalArgumentException e){
    		System.out.println(e.getMessage());
    	}
    }

    public void printTrackInfo(String trackTitle){
    	try{
    		Track track = library.getTrack(trackTitle);
    		System.out.println(track.toString());
    	} catch (IllegalArgumentException e){
    		System.out.println(e.getMessage());
    	}
    }
	
	public void insertTrack(String ... args) {
		try{
			Track newTrack = new Track(args[0], args[1], args[2], args[3], args[4]);
			library.insertTrack(newTrack);
			FileOperation.serialized("storage/"+newTrack.getGenre()+".bin", library.getGenre(newTrack.getGenre()).getTracks());
			System.out.println("Successfully placed in storage: " + library.getGenre(newTrack.getGenre()).getGenreName());
		}catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Don't skip parameters, if don't no info - type \"-\"");
		}catch (IOException e) {
			System.out.println("Caught exception while writing changes: " + e.getMessage() );
		}
	}
	
	public void setTrack(String trackTitle, String ... args) {
		Track oldTrack = library.getTrack(trackTitle);
		if(args[0].equals("-"))
			args[0] = oldTrack.getGenre();
		if(args[1].equals("-"))
			args[1] = oldTrack.getTrackTitle();
		if(args[2].equals("-"))
			args[2] = oldTrack.getSinger();
		if(args[3].equals("-"))
			args[3] = oldTrack.getAlbum();
		if(args[4].equals("-"))
			args[4] = oldTrack.getRecordLength();

		Track newTrack = new Track(args[0], args[1], args[2], args[3], args[4]);
		library.setTrack(trackTitle, newTrack);
		try{
			FileOperation.serialized("storage/"+newTrack.getGenre()+".bin", library.getGenre(newTrack.getGenre()).getTracks());
			System.out.println("Successfully placed in storage: " + library.getGenre(newTrack.getGenre()).getGenreName());
		}catch (IOException e) {
			System.out.println("Caught exception while writing changes: " + e.getMessage() );
		}
	}
	
    public void removeTrack(String trackTitle, String genreName){
    	try{
        	Track currentTrack = library.getGenre(genreName).getTrack(trackTitle);
        	library.getGenre(genreName).removeTrack(currentTrack);
			FileOperation.serialized("storage/"+library.getGenre(genreName)+".bin", library.getGenre(genreName).getTracks());
        	System.out.println("Track " + trackTitle +" has been removed ");
    	} catch (IllegalArgumentException | IOException e){
    		System.out.println(e.getMessage());
    	}
    }

    /*
     * Not sure about Collection<SearchableRecord>
     *     may be separate search in genres/tracks???
     */
    public Collection<SearchableRecord> searchItems(String mask)
    {
        Collection<SearchableRecord> fits = new ArrayList<SearchableRecord>();
        Iterator<Track> trackIterator = library.getAllTracks().iterator();
        Iterator<Genre> genreIterator = library.getGenres().iterator();
        while (trackIterator.hasNext())
        {
            SearchableRecord checked = trackIterator.next();
            if (checked.fitsMask(mask)) fits.add(checked);
        }
        while (genreIterator.hasNext())
        {
            SearchableRecord checked = genreIterator.next();
            if (checked.fitsMask(mask)) fits.add(checked);
        }
        return fits;
    }

}
