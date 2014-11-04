package management;

import interfaces.*;

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
import musicLibrary.MusicLibrary;
import musicLibrary.Track;
import commands.CommandProcessor;
import output.DisplaySystem;

public class ManagementSystem implements Listener {
	private Library musicLibrary;
    private String storage = "storage/.";
    private static ManagementSystem instance;

	public ManagementSystem(){
        MusicLibrary library = new MusicLibrary(loadGenres(storage));
        library.AddListener(this);
        this.musicLibrary = library;
    }

    public static synchronized ManagementSystem getInstance(){
        if (instance == null) {
            instance = new ManagementSystem();
        }
        return instance;
    }
    
    public List<RecordsList> loadGenres(String storage){
    	List<RecordsList> genres = new ArrayList<>();
    	try {
			File dir = new File(storage);
			for(String path : dir.list()){
				if((path.substring(path.lastIndexOf('.'), path.length()).equals(".bin")))
					genres.add(new Genre(path.substring(0, path.lastIndexOf('.')), (HashSet)deserialize("storage/"+ path, HashSet.class)));
			}
		} catch (ClassNotFoundException | IOException e) {
			DisplaySystem.getInstance().DisplayMessage(e.getMessage());
		} 
		return genres;
    }
    
    public void printAllTracksTitle(){
    	/*for (Record track:))
    		System.out.println(track.getTrackTitle());*/
        DisplaySystem.getInstance().DisplayList(musicLibrary.getAllRecords());
    }

	public void getTracksTitles(String genreName){
    	try{
    		for(Record track:musicLibrary.getRecordsList(genreName).getRecords())
                DisplaySystem.getInstance().DisplayMessage(track.getTrackTitle());
    	} catch (IllegalArgumentException e){
            DisplaySystem.getInstance().DisplayMessage(e.getMessage());
    	}
    }
    
    public void moveRecordAnotherSet(String trackTitle, String genreName){
    	try{
    		Record currentTrack = musicLibrary.getRecord(trackTitle);
        	String oldGenre = currentTrack.getGenre();
        	currentTrack.setGenre(genreName);
        	musicLibrary.getRecordsList(genreName).insertRecord(currentTrack);
        	musicLibrary.getRecordsList(oldGenre).removeRecord(currentTrack);
        	System.out.println("Track " + trackTitle +" now belongs to the genre " + genreName);
    	} catch (IllegalArgumentException e){
            DisplaySystem.getInstance().DisplayMessage(e.getMessage());
    	}
    }

    public void printTrackInfo(String trackTitle){
    	try{
    		Record track = musicLibrary.getRecord(trackTitle);
            DisplaySystem.getInstance().DisplayMessage(track.toString());
    	} catch (IllegalArgumentException e){
            DisplaySystem.getInstance().DisplayMessage(e.getMessage());
    	}
    }
	
	public void insertTrack(String ... args) {
		try{
            DisplaySystem.getInstance().DisplayMessage("Inserting track...");
			Record newTrack = new Track(args[0], args[1], args[2], args[3], args[4]);
			musicLibrary.insertRecord(newTrack);
			serialize("storage/"+newTrack.getGenre()+".bin", musicLibrary.getRecordsList(newTrack.getGenre()).getRecords());
            DisplaySystem.getInstance().DisplayMessage("Successfully placed in storage: " + musicLibrary.getRecordsList(newTrack.getGenre()).getRecordsListName());
		}catch (ArrayIndexOutOfBoundsException e){
            DisplaySystem.getInstance().DisplayMessage("Don't skip parameters, if don't no info - type \"-\"");
		}catch (IOException e) {
			DisplaySystem.getInstance().DisplayMessage(e.getMessage());
		}
	}
	
	public void setTrack(String trackTitle, String ... args) {
        DisplaySystem.getInstance().DisplayMessage("Adding track...");
		Record oldTrack = musicLibrary.getRecord(trackTitle);
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
		musicLibrary.setRecord(trackTitle, newTrack);
		try{
			serialize("storage/"+newTrack.getGenre()+".bin", musicLibrary.getRecordsList(newTrack.getGenre()).getRecords());
            DisplaySystem.getInstance().DisplayMessage("Successfully placed in storage: " + musicLibrary.getRecordsList(newTrack.getGenre()).getRecordsListName());
		}catch (IOException e) {
            DisplaySystem.getInstance().DisplayMessage(e.getMessage());
		}
	}
	
    public void removeRecord(String trackTitle, String genreName){
    	try{
            DisplaySystem.getInstance().DisplayMessage("Removing track...");
    		Record currentTrack = musicLibrary.getRecordsList(genreName).getRecord(trackTitle);
    		musicLibrary.removeRecord(genreName, currentTrack);
			serialize("storage/"+genreName+".bin", musicLibrary.getRecordsList(genreName).getRecords());
            DisplaySystem.getInstance().DisplayMessage("Track " + trackTitle + " has been removed ");
    	} catch (IllegalArgumentException | IOException e){
            DisplaySystem.getInstance().DisplayMessage(e.getMessage());
    	}
    }

    /*
     * Not sure about Collection<SearchableRecord>
     *     may be separate search in genres/tracks???
     */
    public Collection<SearchableRecord> searchItems(String mask)
    {
        Collection<SearchableRecord> fits = new ArrayList<SearchableRecord>();
        Iterator<Record> trackIterator = musicLibrary.getAllRecords().iterator();
        Iterator<RecordsList> genreIterator = musicLibrary.getRecordsLists().iterator();
        while (trackIterator.hasNext())
        {
            SearchableRecord checked = (Track)trackIterator.next();
            if (checked.fitsMask(mask)) fits.add(checked);
        }
        while (genreIterator.hasNext())
        {
            SearchableRecord checked = (Genre)genreIterator.next();
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
        DisplaySystem.getInstance().DisplayMessage("Welcome to the information system \"Music Library\" \r\n"
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

    @Override
    public void doEvent(Object arg) {
       if (arg.getClass().equals(String.class)) DisplaySystem.getInstance().DisplayMessage((String)arg);
       if (arg instanceof Exception) DisplaySystem.getInstance().DisplayError((Exception) arg);
    }
}
