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
	private static DisplaySystem ds;
    private static ManagementSystem instance;

	public ManagementSystem(){
        MusicLibrary library = new MusicLibrary(loadGenres(storage));
        library.AddListener(this);
        this.musicLibrary = library;
        this.ds = DisplaySystem.getInstance();
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
			ds.DisplayError(e);
		} 
		return genres;
    }
    
    public void printAllTracksTitle(){
    	ds.DisplayList(musicLibrary.getAllRecords());
    }

	public void getTracksTitles(String genreName){
    	try{
    		ds.DisplayList(musicLibrary.getRecordsList(genreName).getRecords());
    	} catch (IllegalArgumentException e){
    		ds.DisplayError(e);
    	}
    }
    
    public void moveRecordAnotherSet(String trackTitle, String genreName){
    	try{
    		Record currentTrack = musicLibrary.getRecord(trackTitle);
        	String oldGenre = currentTrack.getGenre();
        	currentTrack.setGenre(genreName);
        	musicLibrary.insertRecord(currentTrack);
        	musicLibrary.getRecordsList(oldGenre).removeRecord(currentTrack);
        	serialize("storage/"+genreName+".bin", musicLibrary.getRecordsList(genreName).getRecords());
        	serialize("storage/"+oldGenre+".bin", musicLibrary.getRecordsList(oldGenre).getRecords());
        	ds.DisplayMessage("Track " + trackTitle +" now belongs to the genre " + genreName);
    	} catch (IllegalArgumentException | IOException e){
    		ds.DisplayError(e);
    	}
    }

    public void printTrackInfo(String trackTitle){
    	try{
    		Record track = musicLibrary.getRecord(trackTitle);
    		ds.DisplayMessage(track.toString());
    	} catch (IllegalArgumentException e){
    		ds.DisplayError(e);
    	}
    }
	
	public void insertTrack(String ... args) {
		try{
			ds.DisplayMessage("Inserting track...");
			Record newTrack = new Track(args[0], args[1], args[2], args[3], args[4]);
			musicLibrary.insertRecord(newTrack);
			serialize("storage/"+newTrack.getGenre()+".bin", musicLibrary.getRecordsList(newTrack.getGenre()).getRecords());
			ds.DisplayMessage("Successfully placed in storage: " + musicLibrary.getRecordsList(newTrack.getGenre()).getRecordsListName());
		}catch (ArrayIndexOutOfBoundsException e){
			ds.DisplayMessage("Don't skip parameters, if don't no info - type \"-\"");
		}catch (IOException e) {
			ds.DisplayError(e);
		}
	}
	
	public void setTrack(String trackTitle, String ... args) {
		ds.DisplayMessage("Adding track...");
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
			ds.DisplayMessage("Successfully placed in storage: " + musicLibrary.getRecordsList(newTrack.getGenre()).getRecordsListName());
		}catch (IOException e) {
			ds.DisplayError(e);
		}
	}
	
    public void removeRecord(String trackTitle, String genreName){
    	try{
    		ds.DisplayMessage("Removing track...");
    		Record currentTrack = musicLibrary.getRecordsList(genreName).getRecord(trackTitle);
    		musicLibrary.removeRecord(genreName, currentTrack);
			serialize("storage/"+genreName+".bin", musicLibrary.getRecordsList(genreName).getRecords());
			ds.DisplayMessage("Track " + trackTitle + " has been removed ");
    	} catch (IllegalArgumentException | IOException e){
    		ds.DisplayError(e);
    	}
    }

    public void setRecordsList(String newGenreName) {
		try{
			musicLibrary.setRecordsList(newGenreName);
			serialize("storage/"+newGenreName+".bin", musicLibrary.getRecordsList(newGenreName).getRecords());
			ds.DisplayMessage("Successfully create new storage: " + musicLibrary.getRecordsList(newGenreName));
		}catch (IllegalArgumentException | IOException e) {
			ds.DisplayError(e);
		}
	}
    
    public void removeRecordsList(String genreName){
    	try{
    		musicLibrary.getRecordsList("Unsorted");
    	}catch (IllegalArgumentException e){
    		musicLibrary.setRecordsList("Unsorted");
    	}
    	combineRecordsLists("Unsorted", genreName, "Unsorted");
    	musicLibrary.removeRecordsList(genreName);
    	try{
			serialize("storage/Unsorted.bin", musicLibrary.getRecordsList("Unsorted").getRecords());
			File file = new File("storage/"+genreName+".bin");
			if(file.exists()) {
				file.delete();
			}
			ds.DisplayMessage("Successfully remove storage: " + genreName);
		}catch (IllegalArgumentException | IOException e) {
			ds.DisplayError(e);
		}
    }
    
    public void moveAllRecordsAnotherSet(String genreNameFrom, String genreNameTo){
    	RecordsList genreTo = musicLibrary.getRecordsList(genreNameTo);
    	for(Record record:musicLibrary.getRecordsList(genreNameFrom).getRecords()){
    		record.setGenre(genreNameTo);
    		genreTo.insertRecord(record);
    	}
    }
    
    public void combineRecordsLists(String genreName1, String genreName2, String newGenreName) {
    	if(newGenreName.equalsIgnoreCase(genreName1))
    		moveAllRecordsAnotherSet(genreName2, genreName1);
    	else if(newGenreName.equalsIgnoreCase(genreName2))
    		moveAllRecordsAnotherSet(genreName1, genreName2);
    	else 
    		try{
    			musicLibrary.getRecordsList(newGenreName);
    			moveAllRecordsAnotherSet(genreName1, newGenreName);
    			moveAllRecordsAnotherSet(genreName2, newGenreName);
    		}catch (IllegalArgumentException e){
    			musicLibrary.setRecordsList(newGenreName);
    			moveAllRecordsAnotherSet(genreName1, newGenreName);
    			moveAllRecordsAnotherSet(genreName2, newGenreName);
    		}
		try{
			serialize("storage/"+newGenreName+".bin", musicLibrary.getRecordsList(newGenreName).getRecords());
			ds.DisplayMessage("Successfully combined in storage: " + musicLibrary.getRecordsList(newGenreName));
		}catch (IOException e) {
			ds.DisplayError(e);
		}
	}

    public void getRecordsListsName(){
    	for (RecordsList genre: musicLibrary.getRecordsLists())
    		ds.DisplayMessage(genre.getRecordsListName());
    }
    
    /*
     * Not sure about Collection<SearchableRecord>
     *     may be separate search in genres/tracks???
     */
    public void searchItems(String keyField, String mask)
    {
        Collection<Record> fits = new ArrayList<Record>();
        Iterator<Record> trackIterator = musicLibrary.getAllRecords().iterator();
        while (trackIterator.hasNext())
        {
            Record checked = trackIterator.next();
            if (checked.fitsMask(keyField, mask)) fits.add(checked);
        }
        DisplaySystem.getInstance().DisplayList(fits);
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
    	getInstance();
    	System.setProperty("file.encoding","UTF-8");
    	System.setProperty("console.encoding","Cp866");
    	ds.DisplayMessage("Welcome to the information system \"Music Library\" \r\n"
                + "To get instructions on how to use enter command \"help\"");
    	
    	String consoleEncoding = System.getProperty("console.encoding");
    	if (consoleEncoding != null) {
    	    try {
    	        System.setOut(new PrintStream(System.out, true, consoleEncoding));
    	    } catch (java.io.UnsupportedEncodingException ex) {
    	    	ds.DisplayMessage("Unsupported encoding set for console: "+consoleEncoding);
    	    }
    	}
    	CommandProcessor cp = new CommandProcessor(ds, System.getProperty("console.encoding"));
        cp.execute();
    }

    @Override
    public void doEvent(Object arg) {
       if (arg.getClass().equals(String.class)) ds.DisplayMessage((String)arg);
       if (arg instanceof Exception) ds.DisplayError((Exception) arg);
    }
}
