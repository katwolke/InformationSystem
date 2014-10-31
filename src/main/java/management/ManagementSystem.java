package management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import musicLibrary.Genre;
import musicLibrary.Library;
import musicLibrary.Track;

import commands.CommandProcessor;

public class ManagementSystem {
	private Library library;
    private String storage = "storage/.";
    private static ManagementSystem instance;

	public ManagementSystem(){
        this.library = new Library(loadGenres(storage));
    }

    public static synchronized ManagementSystem getInstance(){
        if (instance == null) {
            instance = new ManagementSystem();
        }
        return instance;
    }
    
    public List<Genre> loadGenres(String storage){
    	List<Genre> genres = new ArrayList<>();
    	try {
			File dir = new File(storage);
			for(String path : dir.list()){
				if((path.substring(path.lastIndexOf('.'), path.length()).equals(".bin")))
					genres.add(new Genre(path.substring(0, path.lastIndexOf('.')), (HashSet)deserialize("storage/"+ path, HashSet.class)));
			}
		} catch (ClassNotFoundException | IOException e) {
			System.out.println(e.getMessage());
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
			serialize("storage/"+newTrack.getGenre()+".bin", library.getGenre(newTrack.getGenre()).getTracks());
			System.out.println("Successfully placed in storage: " + library.getGenre(newTrack.getGenre()).getGenreName());
		}catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Don't skip parameters, if don't no info - type \"-\"");
		}catch (IOException e) {
			System.out.println(e.getMessage());
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
			serialize("storage/"+newTrack.getGenre()+".bin", library.getGenre(newTrack.getGenre()).getTracks());
			System.out.println("Successfully placed in storage: " + library.getGenre(newTrack.getGenre()).getGenreName());
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
    public void removeTrack(String trackTitle, String genreName){
    	try{
        	Track currentTrack = library.getGenre(genreName).getTrack(trackTitle);
        	library.getGenre(genreName).removeTrack(currentTrack);
			serialize("storage/"+genreName+".bin", library.getGenre(genreName).getTracks());
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
    
    private static void serialize(String fileName, Object obj) throws IOException{
		try (ObjectOutputStream objectOutStream = new ObjectOutputStream(new FileOutputStream(new File(fileName)))){		
			objectOutStream.writeObject(obj);
		}
	}
	
    private static <classType> Object deserialize(String fileName, Class classType) throws IOException, ClassNotFoundException{
		Object obj = null;
		try (ObjectInputStream objectInStream = new ObjectInputStream(new FileInputStream(new File(fileName)));) {
			obj = (classType) objectInStream.readObject();
		}
		return obj;
	}

    public static void main(String[] args){
    	System.setProperty("file.encoding","UTF-8");
    	System.setProperty("console.encoding","Cp866");
    	System.out.println("Welcome to the information system \"Music Library\" \r\n"
				+ "To get instructions on how to use enter command \"help\"");
    	getInstance();
    	
    	String consoleEncoding = System.getProperty("console.encoding");
    	if (consoleEncoding != null) {
    	    try {
    	        System.setOut(new PrintStream(System.out, true, consoleEncoding));
    	    } catch (java.io.UnsupportedEncodingException ex) {
    	        System.err.println("Unsupported encoding set for console: "+consoleEncoding);
    	    }
    	}
    	CommandProcessor cp = new CommandProcessor(System.getProperty("console.encoding"));
        cp.execute();
    }
    
}
