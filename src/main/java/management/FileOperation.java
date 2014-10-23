package management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import musicLibrary.Genre;
import musicLibrary.Track;

public class FileOperation {
    private static Logger log = Logger.getAnonymousLogger();


	public static void serialized(String fileName, Object obj) throws IOException{
		ObjectOutputStream objectOutStream = null;
		try {
			objectOutStream = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
			objectOutStream.writeObject(obj);
			System.out.println("Wrote object using serialization, file name: " + fileName);    		 
		}
		catch (Exception e) {
			log.info("Caught exception while processing object: " + e.getMessage());
		}finally{
			objectOutStream.close();
		}
	}
	
	public static <classType> Object deserialized(String fileName, Class classType) throws IOException{
		ObjectInputStream objectInStream = null;
		Object obj = null;
		try {
			objectInStream = new ObjectInputStream(new FileInputStream(new File(fileName)));
			obj = (classType) objectInStream.readObject();
			System.out.println("Read object using serialization... ");
		}catch (Exception e) {
			log.info("Caught exception while processing object: " + e.getMessage());
		}finally{
			objectInStream.close();
		}
		return obj;
	}
	
	public static void main(String[] args) throws IOException {
		Collection<Track> tracks = new HashSet<Track>();
		List<Genre> genres = new ArrayList<>();
		
		Track track = new Track("Power Metal", "Whispers of the Great Mother", "MaterDea", "A Rose for Egeria", "7.06");
		tracks.add(track);
		Genre genre = new Genre(track.getGenre());
		genres.add(genre);
		Track track2 = new Track("Symphonic Metal", "500 letters", "Tarja Turunen", "Colours in the dark", "4.22");
		tracks.add(track2);
		Genre genre2 = new Genre(track2.getGenre());
		genres.add(genre2);
		
		serialized("D:/traksFile.bin", tracks);
		serialized("D:/genresFile.bin", genres);
		Collection<Track> reTracks = (HashSet)deserialized("storage/traksFile.bin", HashSet.class);
		System.out.println(reTracks.size());
		
		List<Genre> reGenres = (ArrayList)deserialized("storage/genresFile.bin", ArrayList.class);
		System.out.println(reGenres.size());
	}
}


